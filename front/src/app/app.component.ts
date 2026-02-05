import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Accueil} from './page/accueil/accueil';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Accueil],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Shajudan Search';
}
