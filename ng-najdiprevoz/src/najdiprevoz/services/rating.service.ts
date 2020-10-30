import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RatingCustomResponse} from '../interfaces/rating.interface';
import {Observable} from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class RatingService {
	readonly path = 'api/ratings';

	constructor(private _http: HttpClient) {
	}

	getMyRatings(): Observable<RatingCustomResponse[]> {
		return this._http.get<RatingCustomResponse[]>(this.path);
	}

	submitRating(reservationRequestId: number, rating: number, note?: string): Observable<void> {
		const body = {
			reservationRequestId: reservationRequestId,
			rating: rating,
			note: note
		};
		return this._http.post<void>(`${this.path}/add`, body);
	}

	getRatingsForUser(userId: any): Observable<RatingCustomResponse[]> {
		return this._http.get<RatingCustomResponse[]>(`${this.path}/user/${userId}`);
	}
}
