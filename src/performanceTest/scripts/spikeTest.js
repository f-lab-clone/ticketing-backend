import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import hooks from "./lib/hooks.js";
import { isSuccess, getOneFromList, randomInt } from "./lib/helpers.js";

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
      startTime: '15s',
      executor: 'per-vu-iterations',
      vus: 200,
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

  const getAvaliableReservation = () => {
    let count = 0
    while (count < randomInt(3, 5)) {
      req.getEvents()
      count++
    }
    const events = req.getEvents()
    return getOneFromList(events.json())
    
    
    // for (const event of events) {
    //   if (event.currentReservationCount < event.maxAttendees) {
    //     return event.id
    //   }
    // }

    // return null
  }

  const user = generator.User()
  req.signup(user)
  req.signin(user)
  check(req.access_token_info(), {"Success Login": isSuccess});

  const event = getAvaliableReservation()
  if (event) {
    const res = req.createReservation({
      eventId: event.id
    })
    check(res, {"Success Reservation": isSuccess});
  }
}