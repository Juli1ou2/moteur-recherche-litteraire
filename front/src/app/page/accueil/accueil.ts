import {Component} from '@angular/core';
import {ButtonModule} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'app-accueil',
  imports: [
    ButtonModule, InputTextModule, FormsModule
  ],
  templateUrl: './accueil.html',
  styleUrl: './accueil.scss',
})
export class Accueil {
  value: string | undefined;
}
