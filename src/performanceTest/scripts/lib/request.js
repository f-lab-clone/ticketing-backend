import http from "k6/http";
import { sleep } from "k6";
import config from "./config.js";
import { randomInt } from "./helpers.js";

function parseQuery(query) {
    if (query == null) {
        return ''
    }
    const keys = Object.keys(query)
    const queryList = []
    for (let i = 0; i < keys.length; i++) {
        const key = keys[i]
        const value = query[key]
        queryList.push(`${key}=${value}`)
    }
    return '?' + queryList.join('&')
}
export default class Request {
    constructor(baseURL = config.HOST) {
        this.baseURL = baseURL
        this.Token = null
        this.SLEEP = 2
    }

    beforeHook() {
        // do nothing
    }

    afterHook() {
        sleep(randomInt(this.SLEEP, this.SLEEP + 10))
    }

    setToken(token) {
        if (token && token.substring(0, 7) === 'Bearer ') {
            // set this.token = token without 'Bearer '
            this.Token = token.substring(7);
        }
        else {
            this.Token = token;
        }
    }
    getToken() {
        return this.Token;
    }
    
    getHeaders() {
        const headers = {
            'Content-Type': 'application/json',
        }
        if (this.getToken() != null) {
            headers['Authorization'] = `Bearer ${this.getToken()}`;
        }
        return headers
    }

    getParams(tags = null) {
        return {
            headers: this.getHeaders(),
            tags,
        }
    }


    helthCheck() {
        this.beforeHook()
        const res=  http.get(this.baseURL, this.getParams())
        this.afterHook()
        return res
    }

    getEvents(query) {
        this.beforeHook()
        const res =  http.get(`${this.baseURL}/events` + parseQuery(query), this.getParams());
        this.afterHook()
        return res
    }
    getEvent(id) {
        this.beforeHook()
        const res =  http.get(`${this.baseURL}/events/${id}`);
        this.afterHook()
        return res
    }
    signup(body) {
        this.beforeHook()
        const res =  http.post(`${this.baseURL}/users/signup`, JSON.stringify(body), this.getParams());
        this.afterHook()
        return res
    }
    signin(body) {
        this.beforeHook()
        const res =  http.post(`${this.baseURL}/users/signin`, JSON.stringify({ email: body.email, password: body.password }), this.getParams());
        if (res.body) {
            this.setToken(res.json().data.Authorization)
        }
        this.afterHook()
        return res
    }
    signout() {
        this.setToken(null)
    }
    access_token_info() {
        this.beforeHook()
        const res =  http.get(`${this.baseURL}/users/access_token_info`, this.getParams());
        this.afterHook()
        return res
    }
    createReservation(body) {
        this.beforeHook()
        const res =  http.post(`${this.baseURL}/reservations`, JSON.stringify(body), this.getParams());
        this.afterHook()
        return res
    }


    createQueueTicket(eventId, userId) {
        this.beforeHook()
        const res =  http.post(`${this.baseURL}/ticket`, JSON.stringify({eventId, userId}), this.getParams());
        this.afterHook()
        return res
    }

    getQueueTicket(eventId, userId) {
        this.beforeHook()
        const res =  http.get(`${this.baseURL}/ticket/${eventId}/${userId}`, this.getParams());
        this.afterHook()
        this.afterHook()
        this.afterHook()
        return res
    }
}