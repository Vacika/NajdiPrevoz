import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HelperService } from './helper.service';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class RideRequestService {
	readonly path = 'api/ride-requests';

	constructor(private _http: HttpClient,
							private _helper: HelperService) {}

	newRideRequest(tripId: number, requestedSeats: number, additionalDescription?: string):Observable<void> {
		const request = {
			tripId: tripId,
			requestedSeats:requestedSeats,
			additionalDescription:additionalDescription
		};
		return this._http.put<void>(`${this.path}/new`, request)
	}
}