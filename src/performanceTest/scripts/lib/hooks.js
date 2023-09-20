import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";
import config from "./config.js";

export default {
    handleSummary: function(data) {
        const htmlPath = `${config.OUTPUT_HTML_DIR}/result.html`


        let Dahboard = ''
        if (config.GRAFANA_SERVER_URL) {
            const DELAY = 1000 * 15 // 15 SEC

            const timeRange = `from=${config.START}&to=${new Date().valueOf() + DELAY}`
            const query = `&orgId=1&refresh=10s&${timeRange}`
            Dahboard = `
    ------------------------DASHBOARD----------------------------
    HTML   : ${htmlPath}

    K6     : ${config.GRAFANA_SERVER_URL}/d/01npcT44k/official-k6-test-result?${query}

    CLUSTER: ${config.GRAFANA_SERVER_URL}/d/85a562078cdf77779eaa1add43ccec1e/kubernetes-compute-resources-namespace-pods?${query}&var-datasource=default&var-cluster=&var-namespace=default

    SPRING : ${config.GRAFANA_SERVER_URL}/d/OS7-NUiGz/spring-boot-statistics?${query}

    MYSQL  : ${config.GRAFANA_SERVER_URL}/d/549c2bf8936f7767ea6ac47c47b00f2a/mysql-exporter?${query}
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