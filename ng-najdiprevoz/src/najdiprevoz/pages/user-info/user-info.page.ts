import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {UserProfileDetails} from "../../interfaces/user.interface";
import {UserService} from "../../services/user.service";

@Component({
	templateUrl: './user-info.page.html',
	styleUrls: ['./user-info.page.scss']
})
export class UserInfoPage implements OnInit {
	user: UserProfileDetails;

	constructor(private _service: UserService,
							private _router: Router,
							private _route: ActivatedRoute) {
	};

	ngOnInit(): void {
		let userId = this._route.snapshot.params.id;
		if (userId) {
			this._service.getUserDetails(userId).subscribe(response => this.user = response)
		}
	}

	navigateToRatings() {
		this._router.navigate(['ratings'], {relativeTo: this._route})
	}
}
