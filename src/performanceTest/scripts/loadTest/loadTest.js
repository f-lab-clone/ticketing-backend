import { check } from "k6";
import Request from "../lib/request.js";
import generator from "../lib/generator.js";
import hooks from "../lib/hooks.js";
import { isSuccess, getOneFromList } from "../lib/helpers.js";

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
  stages: [
    { duration: '3m', target: 200 }, // traffic ramp-up from 1 to ${n} users over ${n} minutes.
    { duration: '30s', target: 0 }, // ramp-down to 0 users
  ],
  
  thresholds: {
    http_req_failed: ['rate<0.01'], // http errors should be less than 1%
    http_req_duration: ['p(95)<200'], // 95% of requests should be below 100ms
  },
};

export default function () {
  const req = new Request()

  const getAvaliableReservation = () => {
    const events = req.getEvents()
    return getOneFromList(events.json())
  }

  const user = generator.User()
  req.signup(user)
  req.signin(user)
  check(req.access_token_info(), {"Success SignIn": isSuccess});

  const event = getAvaliableReservation()
  if (event) {
    const res = req.createReservation({
      eventId: event.id
    })
    check(res, {"Success Reservation": isSuccess});
  }
}