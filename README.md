# risk-calculator
Computes the risk of investments using Value at Risk and Conditional Value at Risk metrics.

Built using gradle

Run at the command-line supporting the following options:

-i [file path]: Specify CSV data for investment to load. Must have a column with header "price"

-c [confidence]: Specify confidence level as a double greater than 0 and less than or equal to 1.
Default is 0.95, meaning 95%.

-p [position]: Specify position size in your currency as a double. The default is 1000.
