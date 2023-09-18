import http from "k6/http";
import CONFIG from "./config.js";

export default {
    helthCheck: function () {
        return http.get(CONFIG.HOST);
    }
}