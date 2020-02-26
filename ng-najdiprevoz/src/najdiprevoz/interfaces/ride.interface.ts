import {UserShortInfo} from "./user-short-info.interface";

export interface Trip {
  id: number
  from: string
  to: string
  departureTime: string
  availableSeats: number
  pricePerHead: number
  driver: UserShortInfo
}
