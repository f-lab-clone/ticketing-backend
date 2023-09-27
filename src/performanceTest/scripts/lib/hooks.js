import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";
import config from "./config.js";

export default {
    setup: function() {
        // https://stackoverflow.com/questions/73458542/k6-storing-data-between-setup-and-default-functions
        return {
            START: new Date().valueOf()
        }
    },

    handleSummary: function(data) {
        const htmlPath = `${config.OUTPUT_HTML_DIR}/result.html`
        const START = data.setup_data.START - config.DASHBOARD_DELAY
        const END = new Date().valueOf() + (config.DASHBOARD_DELAY * 2)

        let Dahboard = ''
        if (config.GRAFANA_HOST) {
            const timeRange = `from=${START}&to=${END}`
            const query = `&orgId=1&refresh=10s&${timeRange}`
            Dahboard = `
    ------------------------DASHBOARD----------------------------
    HTML   : ${htmlPath}

    K6     : ${config.GRAFANA_HOST}/d/01npcT44k/official-k6-test-result?${query}

    CLUSTER: ${config.GRAFANA_HOST}/d/85a562078cdf77779eaa1add43ccec1e/kubernetes-compute-resources-namespace-pods?${query}&var-datasource=default&var-cluster=&var-namespace=default

    NGINX  : ${config.GRAFANA_HOST}/d/nginx/nginx-ingress-controller?${query}

    SPRING : ${config.GRAFANA_HOST}/d/OS7-NUiGz/spring-boot-statistics?${query}

    MYSQL  : ${config.GRAFANA_HOST}/d/549c2bf8936f7767ea6ac47c47b00f2a/mysql-exporter?${query}
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