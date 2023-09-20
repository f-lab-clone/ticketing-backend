import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";
import config from "./config.js";

export default {
    handleSummary: function(data) {
        const htmlPath = `${config.OUTPUT_HTML_DIR}/result.html`
        return {
          [htmlPath]: htmlReport(data),
          stdout: textSummary(data, { indent: " ", enableColors: true }),
        };
    }
}