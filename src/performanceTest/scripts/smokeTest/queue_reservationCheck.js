import { check } from "k6";
import Request from "../lib/request.js";
import generator from "../lib/generator.js";
import hooks from "../lib/hooks.js";
import { getOneFromList, isRunningQueueTicket } from "../lib/helpers.js";

export const setup = hooks.setup
export const handleSummary = hooks.handleSummary

export default function () {
   const req = new Request()

   const USERID = 1
   const user = generator.User(USERID)
   req.signup(user)
   req.signin(user)
   const event = getOneFromList(req.getEvents().json().data)
   const res1 = req.createQueueTicket(event.id, USERID)

   check(res1, { "createQueueTicket status == 201": (r) => r.status == 201 });

   while (!isRunningQueueTicket(req.getQueueTicket(event.id, USERID))) {}

   const res2 = req.createReservation(generator.Reservation(event.id))
   console.log(res2)
   check(res2, { "createReservation status == 201": (r) => r.status == 201 });
   check(res2, { "createReservation.eventId == event.id": (r) => r.json().data.eventId == event.id });
}