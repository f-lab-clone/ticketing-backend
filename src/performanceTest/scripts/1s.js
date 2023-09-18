import { check, sleep } from "k6";
import request from "./lib/request.js";


export default function () {
   const res = request.helthCheck()
   check(res, { "status was 200": (r) => r.status == 200 });
   sleep(1);
}