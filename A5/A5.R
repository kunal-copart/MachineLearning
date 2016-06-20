
#import libraries
library(rpart)
library(e1071)
library(lattice)
library(ggplot2)
library(caret)
library(class)
library("neuralnet")
require(adabag)
require(randomForest)

#library(bag)
#loading data in to the dataset

setwd("C:\\Users\\kunal krishna\\Desktop\\spring_sem\\ML\\A5\\kxk155230\\Data")
my_files <- list.files(pattern = "*.csv")
n = length(my_files)

#number of fold require for cross validation 
nfold <-2

# Define the column and row names for result of the matrix.

rownames = c("DataSet1", "DataSet2", "DataSet3", "DataSet4","DataSet5")
colnames = c("Random Forest", "Gradient_Boosting_Method","KNN","ADA_Boosting"," Bagging","Number of attributes","Number of total instances","How Many fold cross-validation")

#create matrix for all the classifier to store the result

RF_accur <- matrix(c(1:8), nrow=5,ncol=8,byrow = TRUE,dimnames = list(rownames, colnames))



# create a list to store the all the data of csv files

Datarecord <-list()
for (i in 1:n) {
  Datarecord[[i]] <-  data.frame(read.csv(file = my_files[i], header = TRUE))
}


#For doing the Normalization

normalize <- function(x){
  return((x-min(x))/(max(x)-min(x)))
}


#loop through the all the dataset and algorithm of the classifier 

for(i in 1:n){
  for(j in 1:1){
    
    d <- Datarecord[[i]]
    
    #Missing value handler
    for( k in 1: nrow(d)){
      for( l in 1:ncol(d)){
        if ( d[k,l] == '?'){
          d[k,l] <- gsub("?",0,d[k,l], fixed = TRUE)
        }
      }
    }
    
    
    Datarecord[[i]] <- d
    
    sampledata <- sample(nrow(Datarecord[[i]]), floor(nrow(Datarecord[[i]]) * 0.9))
    train_data <- Datarecord[[i]][sampledata, ]
    test_data <- Datarecord[[i]][-sampledata, ]
    
    
       #install.packages("bagging")
    
#################################################################################    
                         #using Random forest:
#################################################################################    
    
    train_data$Fclass <- as.factor(train_data$Fclass)
    RFmodel <- train(Fclass ~.,   # Fclass is a function of the variables we decided to include
                     data = train_data, # Use the trainSet dataframe as the training data
                     method = "rf",# Use the "random forest" algorithm
                     trControl = trainControl(method = "cv", # Use cross-validation
                                              number = nfold) # Use 10 folds for cross-validation
    )
    prediction_nn <- predict(RFmodel, newdata = test_data)
    #prediction_nn <- predict(RFmodel, test_data, type = "class")
    m <- 100*sum(prediction_nn == test_data$Fclass)/length(prediction_nn)
    
    RF_accur[i,1] <- 100*sum(prediction_nn == test_data$Fclass)/length(prediction_nn)
    RF_accur[i,6] <- length(Datarecord[[i]])
    RF_accur[i,7] <- nrow(Datarecord[[i]])
    RF_accur[i,8] <- "10"
    
#################################################################################    
    # using Gradiant boosting method
#################################################################################    
      
      train_data$Fclass <- as.factor(train_data$Fclass)
      fitControl <- trainControl( ## 10-fold CV
      method = "repeatedcv",    ##repeatedcv means it will repeat the cv
      number = nfold,    #number of fold
      ## repeated ten times to create 10 seprate 10-fold cross validation:
      repeats = 5)
    
    gbmmodel <- train(Fclass ~ ., data = train_data,
                      method = "gbm",
                      trControl = fitControl,
                      ## This last option is actually one
                      ## for gbm() that passes through
                      verbose = FALSE)
    
    # prediction_nn <- predict(gbmmodel, newdata = test_data)
    prediction_nn <- predict(gbmmodel, newdata = test_data)
    
    RF_accur[i,2] <- 100*sum(prediction_nn == test_data$Fclass)/length(prediction_nn)
    RF_accur[i,6] <- length(Datarecord[[i]])
    RF_accur[i,7] <- nrow(Datarecord[[i]])
    RF_accur[i,8] <- "10"  
    
    
    #################################################################################    
                           #KNN traning model:
    #################################################################################    
    
    knnTrain <- as.data.frame(lapply(train_data[,c(-ncol(train_data))], normalize))
    knnTest  <- as.data.frame(lapply(test_data[,c(-ncol(test_data))], normalize))
    
    trainClass <- train_data[, ncol(train_data)]
    trainClass <- as.factor(train_data$Fclass)
    
    testClass  <- test_data[, ncol(test_data)]
    testClass <- as.factor(testClass)
    
    centerScale <- preProcess(knnTrain)
    
    training <- predict(centerScale, knnTrain)
    testing <- predict(centerScale, knnTest)
    
    knnFit <- knn3(training, trainClass, k = 5)
    
    prediction_nn <-predict(knnFit, testing)
    
    RF_accur[i,3] <- 100*sum(prediction_nn == testClass)/length(prediction_nn)
    RF_accur[i,6] <- length(Datarecord[[i]])
    RF_accur[i,7] <- nrow(Datarecord[[i]])
    RF_accur[i,8] <- "10"
    
    
################################################################################
      # using ADA boosting method
################################################################################    

      #Using 10 folde cross validation 
    boost <- boosting.cv(Fclass ~.,data = train_data,mfinal = 20,v=nfold,coeflearn = "Breiman",control = rpart.control(maxdepth = 1))
    accuracy = 1 - boost$error
    
    sum_boost  <- 0
    state_boost <- 1;
    sum_boost <- sum_boost + accuracy
    
    if (state_boost == 1) {
      boost_sample <- accuracy
      state_boost <- 2 }else{
        boost_sample <- paste(boost_sample, accuracy, sep=",")
      }
    
    RF_accur[i,4] <- accuracy*100
    RF_accur[i,6] <- length(Datarecord[[i]])
    RF_accur[i,7] <- nrow(Datarecord[[i]])
    RF_accur[i,8] <- "10" 
    
    #####################################################
    #bagging
    ##############################################
      # using 10 fold cross validation
    bagger <- bagging.cv(Fclass~.,data = train_data,v=nfold,mfinal = 20,control = rpart.control(maxdepth = 1))
    
    accuracy = 1 - bagger$error
    sum_bag <-0
    state_bag <-1;
    
    sum_bag <- sum_bag + accuracy
    
    if (state_bag == 1) {
      bag_sample <- accuracy
      state_bag <- 2 }
    else{
        bag_sample <- paste(bag_sample, accuracy, sep=",")
      }
    
    RF_accur[i,5] <- 100*accuracy
    RF_accur[i,6] <- length(Datarecord[[i]])
    RF_accur[i,7] <- nrow(Datarecord[[i]])
    RF_accur[i,8] <- "10" 
    
    #############################################
    
    View(RF_accur)
    
  }
  
}  
