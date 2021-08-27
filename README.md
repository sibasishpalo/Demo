# Description
Demo Spring-boot project with Caffeine cache

# Prerequisites
Before we start we need to have Docker running with ElasticSearch v7.9.2

1. Create an index ```reviews``` with below command:
    ```
    curl -XPUT http://localhost:9200/reviews
    ```
2. insertload some data into the created index
  ```
    curl --location --request POST 'http://localhost:9200/reviews/test/1' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "title": "Sample Review Tile",
      "reviewText": "Hello This is a sample review text",
      "reviewer":"User1"
  }'
  ```
  ```
    curl --location --request POST 'http://localhost:9200/reviews/test/2' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "title": "Sample Review Tile",
      "reviewText": "Hello This is a sample review text",
      "reviewer":"User2"
  }'
  ```
   ```
    curl --location --request POST 'http://localhost:9200/reviews/test/3' \
  --header 'Content-Type: application/json' \
  --data-raw '{
      "title": "Sample Review Tile",
      "reviewText": "Hello This is a sample review text",
      "reviewer":"User3"
  }'
  ```
3. Check if the documents are available in the index or not
  ```
  curl --location --request GET 'http://localhost:9200/reviews/_search'
  ```
  
# Using the App
1. Download and load this project into Intellij or any other IDE
2. First step should create a Run config automatically in the intellij
3. start the application
4. fire the requests using ```curl --location --request GET 'http://localhost:8080/test/query?reviewer=User1'```
5. Abvoe call should print a message on the console ```Fetching reviewer information from ES for Id User1```
6. we can verify by firing same request again and again to verify its loading from the cache(expiration duration is defined in src/main/resources/application.properties), in case of loading from cache it will not print the above log message in the console.
