import { check } from "k6";
import Request from "./lib/request.js";
import generator from "./lib/generator.js";
import { getOneFromList } from "./lib/helpers.js";


export const handleSummary = hooks.handleSummary

export default function () {
   const req = new Request()

   const user = generator.User()
   req.signup(user)
   req.signin(user)
   
   const event = getOneFromList(req.getEvents().json())

   const res = req.createReservation({ eventId: event.id }) 
   check(res, { "status == 200": (r) => r.status == 200 });
   check(res, { "created.eventId == event.id": (r) => r.json().eventId == event.id });
}
