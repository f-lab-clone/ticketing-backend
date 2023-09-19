# PerforanceTest

It runs on Docker Engine with [k6](https://k6.io/)

## How to Run

### 1. Start the Docker Engine on your host

### 2. Copy the `bastion.pem` into `/src/performanceTest/initdb` directory

This `betion.pem` is a public key file used to access AWS RDS to initialize the database.

### 3. Run the following commands


Move to the directory 

```sh
cd src/performanceTest
```


You can change the `ENTRYPOINT` variable to run different test files:


```shell 
# To run the `a_test.js` file
ENTRYPOINT=a_test.js docker-compose up

# To run the `b_test.js` file
ENTRYPOINT=b_test.js docker-compose up
```

## How to Test

If you only want to run the K6 script without initializing the database:

```sh
HOST=http://... k6 run scripts/healthCheck.js   
```