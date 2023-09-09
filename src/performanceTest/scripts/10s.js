import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
   stages: [
      { duration: "10s", target: 15 },
   ],
};

export default function () {
   let res = http.get("https://test-api.k6.io/public/crocodiles/1/");
   check(res, { "status was 200": (r) => r.status == 200 });
   sleep(1);
}