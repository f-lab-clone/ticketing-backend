import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import { getOneFromList } from "./lib/helpers.js";


export default function () {
   const req = new Request()

   const user = generator.User()
   
   req.signup(user)
   req.signin(user)
   
   const res = req.access_token_info().json()

   check(res, { "status == 200": (r) => r.status == 200 });
   check(res, { "res has userId key": (r) => r.json().userId > 0 });
}
