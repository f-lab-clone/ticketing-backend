import { check } from "k6";
import Request from "./lib/request.js";
import hooks from "./lib/hooks.js";
import { isSuccess } from "./lib/helpers.js";
import exec from 'k6/execution';
import generator from "./lib/generator.js";

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
  const user = generator.User(ID)
  const res = req.signup(user)
  check(res, {"Success Signin": isSuccess});
}