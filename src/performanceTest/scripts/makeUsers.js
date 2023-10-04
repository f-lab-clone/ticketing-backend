import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import hooks from "./lib/hooks.js";
import { isSuccess, getOneFromList, randomInt } from "./lib/helpers.js";
import exec from 'k6/execution';

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

const VU_COUNT = 100
const ITERATION = 20
export const options = {
  scenarios: {
    contacts: {
      executor: 'per-vu-iterations',
      vus: VU_COUNT,
      iterations: ITERATION,
      maxDuration: '10m', 
    },
  },
};

export default function () {
  const ID = exec.vu.idInTest + (VU_COUNT * exec.vu.iterationInScenario)
  const req = new Request()
  const User = (prefix) => ({
      name: `${prefix}-${ID}-name`,
      email: `${prefix}-${ID}@email.com`,
      password: `${prefix}-${ID}-password`,
  });
  
  const user = User('K6')
  const res = req.signup(user)
  check(res, {"Success Signin": isSuccess});
}