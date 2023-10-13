import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import hooks from "./lib/hooks.js";
import { isSuccess, randomInt } from "./lib/helpers.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

const VU_COUNT = 200
export const options = {
  tags: {
    testid: `${__ENV.ENTRYPOINT}`
  },
  ext: {
    loadimpact: {
      apm: [
        {
          includeTestRunId: true,
        }
      ]
    }
  },
  scenarios: {
    contacts: {
      executor: 'per-vu-iterations',
      vus: VU_COUNT,
      iterations: 1,
      maxDuration: '1m', 
    },
  },
  
  thresholds: {
    http_req_failed: ['rate<0.01'], // http errors should be less than 1%
    http_req_duration: ['p(95)<300'], // 95% of requests should be below 300ms
  },
};

export default function () {
  const req = new Request()

  const ID = randomInt(1, 1000000)
  const user = generator.User(ID)
  const res = req.signin(user)
  if (!isSuccess(res)) {
    console.log(user, res.body)
  }
  check(res, {"Success SignIn": (r) => isSuccess(r) && r.json().Authorization});
}