const HOST = __ENV.HOST[__ENV.HOST.length - 1] == "/" ? __ENV.HOST.slice(0, -1) : __ENV.HOST

export default {
    HOST
}