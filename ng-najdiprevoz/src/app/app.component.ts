import {Component} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Title} from "@angular/platform-browser";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	constructor(public translate: TranslateService,
							private titleService: Title) {
		translate.addLangs(['mk', 'al', 'en']);
		translate.setDefaultLang('mk');
	}

	changeLang(lang: string) {
		this.translate.use(lang);
		this.translate.get('SITE_TITLE').subscribe(title=>this.titleService.setTitle(title));
	}
}
