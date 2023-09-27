import {
  check
} from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import hooks from "./lib/hooks.js";
import {
  isSuccess
} from "./lib/helpers.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export const options = {
  stages: [{
      duration: '1m',
      target: 200
    }, // fast ramp-up to a high point
    {
      duration: '10s',
      target: 0
    }, // quick ramp-down to 0 users
  ],
};

export default function () {
  const req = new Request()

  const getAvaliableReservation = () => {
    const events = req.getEvents().json()

    for (const event of events) {
      if (event.currentReservationCount < event.maxAttendees) {
        return event.id
      }
    }

    return null
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