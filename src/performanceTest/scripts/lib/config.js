const removeLastSlash = (str) => {
  if (str) {
      return str[str.length - 1] == "/" ? str.slice(0, -1) : str
  }
  return str
} 

const config =  {
  HOST: removeLastSlash(__ENV.HOST),
  GRAFANA_HOST: removeLastSlash(__ENV.GRAFANA_HOST),
  OUTPUT_HTML_DIR: __ENV.OUTPUT_HTML_DIR || './results',

  JWT_RAW_SECRET: __ENV.JWT_RAW_SECRET,
  JWT_ISSUER: __ENV.JWT_ISSUER,
}
export default config