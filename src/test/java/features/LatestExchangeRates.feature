Feature: Latest Exchange Rates Test


@latestRates @RegTest
Scenario Outline: Verify if latest rates are being Succesfully returned using LatestRatesAPI
	Given Get the latest foreign exchange reference rates
	When user calls "latestAPI" with "GET" http request
	Then the API call got success with status code 200
	And "date" in response body should be the current date
	And "base" in response body is "<baseCurrency>"
	And verify currency exchange rate is available for "<currency>" in response body
	
Examples:
	|baseCurrency 	 |currency 	 |
	|EUR 			 |GBP 		 |
	|EUR 			 |INR 		 |
	
	
@latestRates @RegTest
Scenario Outline: Verify if latest rates are being Succesfully returned using LatestRatesAPI with symbols
	Given Get the latest foreign exchange reference rates
	When user calls "latestAPI" with "GET" http request and "<currency>" parameter
	Then the API call got success with status code 200
	And "date" in response body should be the current date
	And "base" in response body is "<baseCurrency>"
	And verify currency exchange rate is available for "<currency>" in response body
	
Examples:
	|baseCurrency 	 |currency 	 |
	|EUR 			 |GBP 		 |
	|EUR 			 |INR,USD 	 |
	
	
@latestRates @RegTest
Scenario Outline: Verify if latest rates are being Succesfully returned using LatestRatesAPI with symbols and base
	Given Get the latest foreign exchange reference rates
	When user calls "latestAPI" with "GET" http request and "<currency>" parameter and "<baseCurrency>" parameter
	Then the API call got success with status code 200
	And "date" in response body should be the current date
	And "base" in response body is "<baseCurrency>"
	And verify currency exchange rate is available for "<currency>" in response body
	
Examples:
	|baseCurrency 	 |currency 	 |
	|EUR 			 |GBP 		 |
	|INR 			 |INR,USD 	 |
	
@paseDatesRates @RegTest
Scenario Outline: Verify if latest rates are being Succesfully returned using PastRatesAPI with symbols and base
	Given Get the latest foreign exchange reference rates
	When user calls "pastDateAPI" with "GET" http request and "<date>" and "<currency>" parameter and "<baseCurrency>" parameter
	Then the API call got success with status code 200
	And "date" in response body is "<date>"
	And "base" in response body is "<baseCurrency>"
	And verify currency exchange rate is available for "<currency>" in response body
	
Examples:
	|date		|baseCurrency 	 |currency 	 |
	|2010-01-12	|EUR 			 |GBP 		 |
	|2018-01-12	|INR 			 |INR,USD 	 |