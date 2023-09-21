const removeLastSlash = (str) => {
    if (str) {
        return str[str.length - 1] == "/" ? str.slice(0, -1) : str
    }
    return str
} 
export default {
    START: new Date().valueOf(),
    HOST: removeLastSlash(__ENV.HOST),
    GRAFANA_HOST: removeLastSlash(__ENV.GRAFANA_HOST),
    OUTPUT_HTML_DIR: __ENV.OUTPUT_HTML_DIR || './results',
    DASHBOARD_DELAY: 1000 * 30 // 30 SEC
}