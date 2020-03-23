import { Component, OnInit } from '@angular/core';
import { TripService } from '../../services/trip.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { City } from '../../interfaces/city.interface';
import { CityService } from '../../services/city.service';
import { Observable } from 'rxjs';

@Component({
	templateUrl: './create-trip.page.html',
	styleUrls: ['./create-trip.page.scss']
})
export class CreateTripPage implements OnInit {
	fromToForm: FormGroup;
	passengerInfoForm: FormGroup;
	preferencesForm: FormGroup;
	allCities: City[] = [];
	currentStep = new Observable<number>();
	dateNow: Date;

	constructor(private _service: TripService,
							private _cityService: CityService,
							private _route: ActivatedRoute,
							private formBuilder: FormBuilder) {

	};

	ngOnInit(): void {
		this._cityService.getAllCities().subscribe(cities => this.allCities = cities);
		this.fromToForm = this.formBuilder.group({
			fromLocation: new FormControl('', Validators.required),
			toLocation: new FormControl('', Validators.required)
		});
		this.currentStep = new Observable(observer => observer.next(1));
		this.dateNow = new Date();
	}

	submitFromTo() {
		if (this.getFromLocation.value != this.getToLocation.value) {
			this.fromToForm.disable();
			this.passengerInfoForm = this._passengerInfoFormDefinition();
			this.currentStep = new Observable(observer => observer.next(2));
		}
	}

	submitPassengerInfo() {
		this.passengerInfoForm.disable();
		this.preferencesForm = this._preferencesFormDefinition();
		this.currentStep = new Observable(observer => observer.next(3));
	}

	submitPreferences() {
		console.log(this.fromToForm.getRawValue());
		console.log(this.passengerInfoForm.getRawValue());
		console.log(this.preferencesForm.getRawValue());
		const formValues = {
			fromLocation: this.getFromLocation.value,
			destination: this.getToLocation.value,
			pricePerHead: this.getPricePerHead.value,
			passengerSeats: this.getPassengerSeats.value,
			smokingAllowed: this.getIsSmokingAllowed.value,
			petAllowed: this.getIsPetAllowed.value,
			hasAirCondition: this.getHasAirCondition.value,
			departureTime: this.getDepartureTime.value,
			maxTwoBackseats: this.getMaxTwoBackseat.value,
			driverId: 1 //TODO: CHANGE THIS !!!!!! FETCH FROM LOGGED USER ID
		};
		this._service.addNewTrip(formValues).subscribe()
	}

	private get getFromLocation() {
		return this.fromToForm.get('fromLocation');
	}

	private get getToLocation() {
		return this.fromToForm.get('toLocation');
	}

	private get getPassengerSeats() {
		return this.passengerInfoForm.get('totalSeatsOffered');
	}

	private get getPricePerHead() {
		return this.passengerInfoForm.get('pricePerHead');
	}

	private get getIsSmokingAllowed() {
		return this.preferencesForm.get('isSmokingAllowed');
	}

	private get getIsPetAllowed() {
		return this.preferencesForm.get('isPetAllowed');
	}

	private get getDepartureTime() {
		return this.preferencesForm.get('departureTime');
	}

	private get getHasAirCondition() {
		return this.preferencesForm.get('hasAirCondition');
	}

	private get getMaxTwoBackseat() {
		return this.preferencesForm.get('maxTwoBackseat');
	}

	private _passengerInfoFormDefinition() {
		return this.formBuilder.group({
			totalSeatsOffered: new FormControl(null, Validators.required),
			pricePerHead: new FormControl(null, Validators.required)
		});
	}

	private _preferencesFormDefinition() {
		return this.formBuilder.group({
			isSmokingAllowed: new FormControl(false, Validators.required),
			isPetAllowed: new FormControl(false, Validators.required),
			departureTime: new FormControl(null, Validators.required),
			hasAirCondition: new FormControl(false, Validators.required),
			maxTwoBackseat: new FormControl(false, Validators.required)
		});
	}

	getTimeDateNow() {
		return this.dateNow;
	}

	myDateTimeFilter = (d: Date): boolean => {
		return this.dateNow < d;
	};
}