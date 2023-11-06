import { check } from "k6";
import Request from "./lib/request.js";
import hooks from "./lib/hooks.js";
import generator from "./lib/generator.js";
import { getUserIDFromExec, isSuccess } from "./lib/helpers.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

const VU_COUNT = 10
export const options = {
  scenarios: {
    contacts: {
      executor: 'per-vu-iterations',
      vus: VU_COUNT,
      iterations: 10,
      maxDuration: '10m', 
    },
  },
};

export default function () {
  const ID = getUserIDFromExec(VU_COUNT)
  const req = new Request()
  const user = generator.User(ID)
  const res = req.signup(user)
  check(res, {"Success Signin": isSuccess});
}