import { check } from "k6";
import Request from "./lib/request.js";
import hooks from "./lib/hooks.js";

export const handleSummary = hooks.handleSummary

export const options = {
   vus: 10,
   duration: '5s',
 };

export default function () {
   const req = new Request()
   const res = req.helthCheck()
   check(res, { "status == 200": (r) => r.status == 200 });
   check(res, { "body == OK": (r) => r.body == "OK" });
}