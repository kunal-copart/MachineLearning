require(random)
craps <- function() {
  field <- c(2,3,12)
  wins <- c(7,11)
  initialRoll <- as.integer(colSums(randomNumbers(2, 1, 6, 1)))
  if (initialRoll %in% field)
    out <- 0
  else if (initialRoll %in% wins)
    out <- 1
  else {
    point <- initialRoll
    # now run the game until you get 7 or point again
    roll <- 0
    while(roll!= point && roll!=7) {
      roll <- as.integer(colSums(randomNumbers(2, 1, 6, 1)))
    }
    if (roll == point)
      out <- 1
    else if (roll == 7)
      out <- 0
    out
  }
  
}
balancecheck<- function()
{
balance<-1000
bet<-100
i<-0
while(balance>0 && i<10)
{
  cat(i)
  i=i+1
  out=craps()
  if(out)
  {
    balance=balance+bet
    bet=100
  }
  else
  {
    balance=balance-bet
    bet=min(2*bet,balance)
    
  }
  cat(" ")
  cat(balance)
  cat(" ")
  
  
}
cat(balance)
}

