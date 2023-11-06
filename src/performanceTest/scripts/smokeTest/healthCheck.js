import { check } from "k6";
import Request from "../lib/request.js";
import hooks from "../lib/hooks.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export default function () {
   const req = new Request()
   const res = req.helthCheck()
   check(res, { "status == 200": (r) => r.status == 200 });
   check(res, { "body == OK": (r) => r.body == "OK" });
}