You need below package in R.


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


File kxk155230  contain all the data files inside "Data" folder which I have used for training my all the classifier.


In program I have given the files location as "setwd("C:\\Users\\kunal krishna\\Desktop\\spring_sem\\ML\\a3\\Data")"


you can put this folder in your Rstudio folder and need to put the location of the file in setwd()  function.


It will generate five report related to all the classifier.


note: 
If program takes too much time then you can remove one dataset from Data folder ,which is larger name (default of credit card clients1.csv) as it is having 30000 instances and 25 attributes  so it might slow down the program running.

