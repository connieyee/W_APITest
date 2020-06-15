# WeatherAPITest

# Introduction

Task 1 is a script for testing the API of Hong Kong Observatory Current Weather Report (rhrread).

API URL
https://data.weather.gov.hk/weatherAPI/opendata/weather.php

Request Example
https://data.weather.gov.hk/weatherAPI/opendata/weather.php?dataType=rhrread&lang=en

For API Test Report, Please access
```
${path}\WeatherAPITest-master\WeatherAPITest-master\APITestReport.html
```

Task 2 is a script for a fixed endpoint which return code is 200 upon success.


## Installation

1. Install Java [Click me](https://java.com/en/download/help/download_options.xml)
1. Install Maven [Click me](https://maven.apache.org/install.html)

## Usage

Task 1:
```
maven clean test -Dtest=APIRunner
```

Task 2:
```
maven clean test -Dtest=FixedEndpointTest -Dpath={path}
```