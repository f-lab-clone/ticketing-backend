import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import hooks from "./lib/hooks.js";
import { isSuccess, getOneFromList } from "./lib/helpers.js";


export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export const options = {
  stages: [{
      duration: '1m',
      target: 20
    }, // fast ramp-up to a high point
    {
      duration: '10s',
      target: 0
    }, // quick ramp-down to 0 users
  ],


  thresholds: {
    http_req_failed: ['rate<0.01'], // http errors should be less than 1%
    http_req_duration: ['p(95)<100'], // 95% of requests should be below 100ms
  },
};

export default function () {
  const req = new Request()

  const getAvaliableReservation = () => {
    const events = req.getEvents().json()
    return getOneFromList(events)
    
    // for (const event of events) {
    //   if (event.currentReservationCount < event.maxAttendees) {
    //     return event.id
    //   }
    // }

    // return null
  }

  const user = generator.User()
  req.signup(user)

  check(req.signin(user), {"Success Login": isSuccess});

  const event = getAvaliableReservation()
  if (event) {
    const res = req.createReservation({
      eventId: event.id
    })
    check(res, {"Success Reservation": isSuccess});
  }
}