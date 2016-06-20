library("quantmod")

stockAna <- function() {
  values<- new.env()
  
  stocks <- c("DJIA", "SPY", "AAPL", "BAC", "NFLX", "PCLN", "AMZN")
  getSymbols(stocks, env = values, src = "yahoo", from = '2000-01-01', auto.assign = T)
  
  chartSeries(values$DJIA)
  plot(addSMA(n = 200))
  
  chartSeries(values$SPY)
  plot(addSMA(n = 200))
  
  chartSeries(values$AAPL)
  plot(addSMA(n = 200))
  
  chartSeries(values$BAC)
  plot(addSMA(n = 200))
  
  chartSeries(values$NFLX)
  plot(addSMA(n = 200))
  
  chartSeries(values$PCLN)
  plot(addSMA(n = 200))
  
  chartSeries(values$AMZN)
  plot(addSMA(n = 200))
}
stockPlots()