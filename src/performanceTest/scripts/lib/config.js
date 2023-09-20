const removeLastSlash = (str) => {
    if (str) {
        return str[str.length - 1] == "/" ? str.slice(0, -1) : str
    }
    return str
} 
export default {
    START: new Date().valueOf(),
    HOST: removeLastSlash(__ENV.HOST),
    GRAFANA_SERVER_URL: removeLastSlash(__ENV.GRAFANA_SERVER_URL || 'http://3.36.90.245:30000'),
    OUTPUT_HTML_DIR: __ENV.OUTPUT_HTML_DIR || './results',
}