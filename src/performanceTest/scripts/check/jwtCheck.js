import { check } from "k6";
import exec from 'k6/execution';
import Request from "../lib/request.js";
import hooks from "../lib/hooks.js";
import { encode } from "../lib/jwt.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export default function () {
   const req = new Request()

   const token = encode(1)
   req.setToken(token)
   
   const res = req.access_token_info()
   check(res, { "status == 200": (r) => r.status == 200 });
   check(res, { "res has userId key": (r) => r.json().userId > 0 });
}
