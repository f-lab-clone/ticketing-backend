import { check } from "k6";
import Request from "../lib/request.js";
import { encode } from "../lib/jwt.js";
import hooks from "../lib/hooks.js";
import generator from "../lib/generator.js";
import { isSuccess, randomInt, isAlreadReservedAll } from "../lib/helpers.js";

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
      vus: 1,
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
  req.setToken(encode(ID))

  const query = {
    size: 20,
    page: 0,
    sort: "id,asc"
  }
  for (let i = 0; i < 13; i++) {
    check(req.getEvents(query), {"Success Get Events": isSuccess});
    query.page = query.page + randomInt(1, 10)
  }

  const eventId = 98 // maxAttendees = 191
  check(req.getEvent(eventId), {"EVENT 98 maxAttendees = 191": (r) => r.json().data.maxAttendees === 191})
  
  const res = req.createReservation(generator.Reservation(eventId))
  console.log(res.status, res.json())
  check(res, {"Success Reservation": isSuccess});
  check(res, {"Already reserved": isAlreadReservedAll});
}