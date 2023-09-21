# PerforanceTest

You can refer to the [k6 documentation](https://k6.io/) for additional information.

## Getting Started

This performance test runs on Docker Engine.

### 1. Start Docker Engine on your host

### 2. Copy the `bastion.pem` File

Copy the `bastion.pem` file to the `/src/performanceTest/initdb` directory. 

This file is a public key used to access AWS RDS for database initialization.

###  Configure Environment Variables

Set the required environment variables, or create a `.env` file within the `/src/performanceTest/` directory.

Here is an example `.env` file with placeholders:

```dotenv
MYSQL_HOST=
MYSQL_PORT=
MYSQL_USERNAME=
MYSQL_PASSWORD=
MYSQL_SCHEMA=
BASTION_HOST=
BASTION_USERNAME=

HOST=

# Monitoring Configuration (Optional)
GRAFANA_HOST=
K6_PROMETHEUS_RW_SERVER_URL=
K6_PROMETHEUS_RW_USERNAME=
K6_PROMETHEUS_RW_PASSWORD=
```

### 4. Run the following commands


Move to the `src/performanceTest` directory

You can change the `ENTRYPOINT` variable to run different test files:


```shell 
# To run the `a_test.js` file
ENTRYPOINT=a_test.js docker-compose up

# To run the `b_test.js` file
ENTRYPOINT=b_test.js docker-compose up
```


## Running K6 Scripts Independently

If you want to run a K6 script **without Docker** and **without initializing the database**, you can use the following command:


```sh
# Install k6 on your host

HOST=http://... k6 run scripts/healthCheck.js
```