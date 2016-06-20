#load require library
#install.packages("xlsReadWrite")
#library(xlsReadWrite)

#install.packages("e1071")
library(e1071)

# neural net library
library(neuralnet)
#install.packages("nnet")
library(nnet)

#decision tree
library(rpart)

#loading data in to the dataset

setwd("C:\\Users\\kunal krishna\\Desktop\\spring_sem\\ML\\a3\\Data")
my_files <- list.files(pattern = "*.csv")
n = length(my_files)

# Define the column and row names for result of the matrix.

rownames = c("DataSet1", "DataSet2", "DataSet3", "DataSet4","DataSet5")
colnames = c("FirstRun", "SecondRun","Number of attributes","Number of total instances","Percent split")

#create matrix for all the classifier to store the result

svm_accur <- matrix(c(1:5), nrow=5,ncol=5,byrow = TRUE,dimnames = list(rownames, colnames))
nn_accur <- matrix(c(1:5), nrow=5,ncol=5,byrow = TRUE,dimnames = list(rownames, colnames))
peceptron_accur <- matrix(c(1:5), nrow=5,ncol=5,byrow = TRUE,dimnames = list(rownames, colnames))
DT_accur <- matrix(c(1:5), nrow=5,ncol=5,byrow = TRUE,dimnames = list(rownames, colnames))
naiveB_accur <- matrix(c(1:5), nrow=5,ncol=5,byrow = TRUE,dimnames = list(rownames, colnames))


# create a list to store the all the data of csv files

Datarecord <-list()
for (i in 1:n) {
  Datarecord[[i]] <-  data.frame(read.csv(file = my_files[i], header = TRUE))
}


#loop through the all the dataset and algorithm of the classifier 

for(i in 1:n){
  for(j in 1:2){
    #Datarecord[[i]]$Fclass <- as.factor(Datarecord[[i]]$Fclass)
    sampledata <- sample(nrow(Datarecord[[i]]), floor(nrow(Datarecord[[i]]) * 0.9))
    train_data <- Datarecord[[i]][sampledata, ]
    test_data <- Datarecord[[i]][-sampledata, ]
    
    ############################################
    
    #testing for neural-net model
    ############################################
    
    
    train_data$Fclass <- as.factor(train_data$Fclass)
    #code to find Perceptron accuracy
    
    train_model_nn <- nnet(Fclass~., train_data, size=0, na.action = na.omit, skip = TRUE)
    prediction_nn <- predict(train_model_nn, test_data, type = "class")
    
    nn_accur[i,j] <- 100*sum(prediction_nn == test_data$Fclass)/length(prediction_nn)
    nn_accur[i,3] <- length(Datarecord[[i]])
    nn_accur[i,4] <- nrow(Datarecord[[i]])
    nn_accur[i,5] <- "90/10"
    
    
    ##########################################
    #testing for Decision tree
    
    ##################################
    train_data$Fclass <- as.factor(train_data$Fclass)
    dt<-rpart(Fclass~.,train_data,method="class",parms = list(split="information"))
    prediction <- predict(dt, test_data,type = 'class')
    DT_accur[i,j] <- 100*sum(prediction == test_data$Fclass)/length(prediction)
    DT_accur[i,3] <- length(Datarecord[[i]])
    DT_accur[i,4] <- nrow(Datarecord[[i]])
    DT_accur[i,5] <- "90/10"
    
    
    ###########################################
    #SINGLE layer perceptron 
    #############################
    train_data$Fclass <- as.factor(train_data$Fclass)
    #code to find Perceptron accuracy
    
    train_model_pr <- nnet(Fclass~., train_data, size=0, na.action = na.omit, skip = TRUE)
    prediction_per <- predict(train_model_pr, test_data, type = "class")

    peceptron_accur[i,j] <- 100*sum(prediction_per == test_data$Fclass)/length(prediction_per)
    peceptron_accur[i,3] <- length(Datarecord[[i]])
    peceptron_accur[i,4] <- nrow(Datarecord[[i]])
    peceptron_accur[i,5] <- "90/10"
    
    
    
    
    #############################################
    
    #testing for naive bayesan model
    
    ############################################
    
    train_data$Fclass <- as.factor(train_data$Fclass)
    train_model <- naiveBayes(train_data$Fclass~., train_data)
    prediction <- predict(train_model, test_data)
    naiveB_accur[i,j] <- 100*sum(prediction == test_data$Fclass)/length(prediction)
    naiveB_accur[i,3] <- length(Datarecord[[i]])
    naiveB_accur[i,4] <- nrow(Datarecord[[i]])
    naiveB_accur[i,5] <- "90/10"
    
    
    #testing for SVM model
    train_data$Fclass <- as.factor(train_data$Fclass)
    train_model <- svm(Fclass~., train_data, scale= FALSE)
    prediction <- predict(train_model, test_data, type="raw")
    svm_accur[i,j] <- 100*sum(prediction == test_data$Fclass)/length(prediction)
    svm_accur[i,3] <- length(Datarecord[[i]])
    svm_accur[i,4] <- nrow(Datarecord[[i]])
    svm_accur[i,5] <- "90/10"
    
    
  }
  
}
View(nn_accur)
View(naiveB_accur)
View(DT_accur)
View(peceptron_accur)
View(svm_accur)
