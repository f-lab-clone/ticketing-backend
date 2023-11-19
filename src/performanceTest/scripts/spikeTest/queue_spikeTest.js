import { check, sleep } from "k6";
import exec from 'k6/execution';
import Request from "../lib/request.js";
import { encode } from "../lib/jwt.js";
import hooks from "../lib/hooks.js";
import generator from "../lib/generator.js";
import { isSuccess, randomInt, isAlreadReservedAll, isRunningQueueTicket } from "../lib/helpers.js";

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
      vus: 2000,
      iterations: 1,
      maxDuration: '2m', 
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
  sleep(randomInt(0, 10))
  for (let i = 0; i < 5; i++) {
    check(req.getEvents(query), {"Success Get Events": isSuccess});
    query.page = query.page + 3
  }

  const eventId = 98 // maxAttendees = 191
  check(req.getEvent(eventId), {"EVENT 98 maxAttendees = 191": (r) => r.json().data.maxAttendees === 191})
  
  const res1 = req.createQueueTicket(eventId, ID)
  check(res1, { "createQueueTicket status == 201": (r) => r.status == 201 });

  while (!isRunningQueueTicket(req.getQueueTicket(eventId, ID))) {}


  const res = req.createReservation(generator.Reservation(eventId))
  check(res, {"Success Reservation": isSuccess});
  check(res, {"Already reserved": isAlreadReservedAll});
}