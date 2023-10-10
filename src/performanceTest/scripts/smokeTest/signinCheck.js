import { check } from "k6";
import Request from "../lib/request.js";
import generator from "../lib/generator.js";
import hooks from "../lib/hooks.js";
import { isSuccess } from "../lib/helpers.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export default function () {
   const req = new Request()

   const user = generator.User()
   
   req.signup(user)
   req.signin(user)
   
   const res = req.access_token_info()

   check(res, { "isSuccess": isSuccess });
   check(res, { "res has userId key": (r) => r.json().data.userId > 0 });
}
