import { check } from "k6";
import Request from "../lib/request.js";
import generator from "../lib/generator.js";
import hooks from "../lib/hooks.js";
import { getOneFromList } from "../lib/helpers.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export default function () {
   const req = new Request()

   const user = generator.User()
   req.signup(user)
   req.signin(user)
   const event = getOneFromList(req.getEvents().json().data)
   const res = req.createReservation(generator.Reservation(event.id))
   check(res, { "status == 201": (r) => r.status == 201 });
   check(res, { "created.eventId == event.id": (r) => r.json().data.eventId == event.id });
}