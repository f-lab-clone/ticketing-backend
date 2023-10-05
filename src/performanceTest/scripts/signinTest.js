import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import hooks from "./lib/hooks.js";
import { isSuccess } from "./lib/helpers.js";
import exec from 'k6/execution';

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

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
      vus: 20,
      iterations: 1,
      maxDuration: '1m', 
    },
  },
  
  thresholds: {
    http_req_failed: ['rate<0.01'], // http errors should be less than 1%
    http_req_duration: ['p(95)<200'], // 95% of requests should be below 100ms
  },
};

export default function () {
  const req = new Request()

  const ID = exec.vu.idInTest
  const user = generator.User(ID)
  const res = req.signin(user)
  check(res, {"Success SignIn": (r) => isSuccess(r) && r.json().Authorization});
}