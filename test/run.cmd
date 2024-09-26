@ECHO OFF
cd ..
gradlew build
gradlew run --args="-i E:\Git\risk-calculator\data\btc-usd-092024.csv"
