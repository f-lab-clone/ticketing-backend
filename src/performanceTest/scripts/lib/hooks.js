import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";
import config from "./config.js";

export default {
    setup: function() {

        console.log(`setup: ${new Date().toISOString()}`)
        // https://stackoverflow.com/questions/73458542/k6-storing-data-between-setup-and-default-functions
        return {
            START: new Date().valueOf()
        }
    },

    handleSummary: function(data) {
        console.log(`end: ${new Date().toISOString()}`)

        const DELAY = 1000 * 60
        const htmlPath = `${config.OUTPUT_HTML_DIR}/result.html`
        const START = data.setup_data.START - DELAY
        const END = new Date().valueOf() + DELAY

        let Dahboard = ''
        if (config.GRAFANA_HOST) {
            const timeRange = `from=${START}&to=${END}`
            const query = `&orgId=1&refresh=10s&${timeRange}`
            Dahboard = `
    ------------------------DASHBOARD----------------------------

    [K6]
    HTML                         : ${htmlPath}
    K6 (Native Histogram)        : ${config.GRAFANA_HOST}/d/a3b2aaa8-bb66-4008-a1d8-16c49afedbf0/k6-prometheus-native-histograms?${query}&var-DS_PROMETHEUS=prometheus&var-testid=${__ENV.ENTRYPOINT}&var-quantile=0.95

    [Cluster Resources]
    CLUSTER                      : ${config.GRAFANA_HOST}/d/4b545447f/1-kubernetes-all-in-one-cluster-monitoring-kr??${query}&var-datasource=default&var-cluster=&var-namespace=default

    [Nginx]
    Official NGINX               : ${config.GRAFANA_HOST}/d/nginx/nginx-ingress-controller?${query}
    Request Handling Performance : ${config.GRAFANA_HOST}/d/4GFbkOsZk/request-handling-performance?${query}

    [Spring]
    Official SPRING              : ${config.GRAFANA_HOST}/d/OS7-NUiGz/spring-boot-statistics?${query}
    Spring Http                  : ${config.GRAFANA_HOST}/d/hKW8gvD4z/spring-http?${query}
    JVM                          : ${config.GRAFANA_HOST}/d/b4a44d59-1d62-4af7-a64d-3aa2a67427fb/jvm-micrometer?orgId?${query}

    [MySQL]
    MYSQL                        : ${config.GRAFANA_HOST}/d/549c2bf8936f7767ea6ac47c47b00f2a/mysql-exporter?${query}
    -------------------------------------------------------------


            `
        }
        return {
          [htmlPath]: htmlReport(data),
          stdout: `
${Dahboard}
${textSummary(data, { indent: " ", enableColors: true })}
          `,
        };
    }
}