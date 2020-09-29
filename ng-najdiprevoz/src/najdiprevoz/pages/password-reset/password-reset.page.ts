import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { emailRegex } from '../../constants/regex.constants';
import { UINotificationsService } from '../../services/ui-notifications-service';
import { PasswordResetService } from '../../services/password-reset.service';
import { ActivatedRoute } from '@angular/router';

@Component({
	templateUrl: './password-reset.page.html',
	styleUrls: ['./password-reset.page.scss']
})
export class PasswordResetPage implements OnInit {
	newPasswordForm: FormGroup;
	token: string;
	isTokenValid: boolean = false;

	constructor(private formBuilder: FormBuilder,
							private route: ActivatedRoute,
							private passwordResetService: PasswordResetService,
							private notificationService: UINotificationsService) {
	}

	ngOnInit() {
		this.token = this.route.snapshot.queryParamMap.has('token') ? this.route.snapshot.queryParamMap.get('token') : null;
		if (this.token) {
			this.passwordResetService.isTokenValid(this.token)
				.subscribe(isValid => {
					this.isTokenValid = isValid;
					this.createPasswordForm();
				});
		}
	}

	createPasswordForm():void {
		this.newPasswordForm = this.formBuilder.group({
			newPassword: [null, [Validators.required, Validators.min(8)]]
		});
	}
	submit() {
		if (this.newPasswordForm.valid) {
			this.passwordResetService.handlePasswordReset(this.token, this.password.value)
				.subscribe(_ => this.notificationService.success('PW_RESET_SUCCESS'),
					_ => this.notificationService.error('PW_RESET_FAIL'));
		}
	}

	private get password(): AbstractControl {
		return this.newPasswordForm.controls['newPassword'];
	}
}
