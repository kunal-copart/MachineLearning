##kunal krishna..
###############################
#Part 1 coding challange:
###############################

#Read the data set from the given URL
D2 <- read.csv("https://archive.ics.uci.edu/ml/machine-learning-databases/adult/adult.data",
               header=FALSE, stringsAsFactors=FALSE,sep=",", na.strings="NA", dec=".")[-c(1:10), ]

# Name the header for the file
names(D2) <-c("age","workclass","fnlwgt","education","education_num","marital-status",
              "occupation","relationship","race","sex","capital-gain","capital-loss","hours-per-week",
              "native-county","income")

# Dimension of File:

cat("Dimension of file",dim(D2))

cat("Number of col:::",ncol(D2))
cat("Number of row:::",nrow(D2))

# print first 5 row of the data
cat("First Five row of the data")
head(D2, n=5)


########################################
#part 2 coding challange:
#######################################


####### first filter
df1 <- D2[which(D2$education != ' Preschool'),]

x <- ((nrow(D2)-nrow(df1))/nrow(D2))*100
  
cat("percentage decrease in row after first filter:",x)

####### 2nd filter rows that fall within 1st and 3rd quartile
#############

df2 <- df1[which(df1$education_num >quantile(df1$education_num , 0.25 ) & df1$education_num <quantile(df1$education_num , 0.75 )),]

y <- ((nrow(D2)-nrow(df2))/nrow(D2))*100

cat("percentage decrease in row after 2nd filter:",y)

##############################################
############################################
##### 3rd part Grouping and visualization
############################################
## need to install data.table library.
library(data.table)

#install.packages(dplyr)
df3 <- data.table(df2)
df3 <- df3[, mean(age), by = occupation]
names(df3) <- c("occupation","age")


###Bar plot
## need to install ggplot2 library 

library(ggplot2)
ggplot(data=df3, aes(x=age,y=occupation)) +
  geom_bar(position="dodge",stat="identity") +
  coord_flip() +
  ggtitle("Bar chat avg Age Vs Occupation") +
  scale_fill_grey() +
  theme_bw()


###########################
### extra credit part#
##################
library(e1071)
train_data <- read.csv("https://s3-us-west-1.amazonaws.com/amirziai-accessfuel/nb_train.csv",
               header=FALSE, stringsAsFactors=FALSE,sep=",", na.strings="NA", dec=".")

test_data <- read.csv("https://s3-us-west-1.amazonaws.com/amirziai-accessfuel/nb_test.csv",
                       header=FALSE, stringsAsFactors=FALSE,sep=",", na.strings="NA", dec=".")

train_data$V2 <- as.factor(train_data$V2)
train_model <- naiveBayes(train_data$V2~., train_data)
prediction <- predict(train_model, test_data)

# will have the prediction
prediction

## to do part
###need to count the number of words in each row,
