import {Component} from '@angular/core';
import {Button} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'app-accueil',
  imports: [
    Button, InputTextModule, FormsModule
  ],
  templateUrl: './accueil.html',
  styleUrl: './accueil.scss',
})
export class Accueil {
  value1: string | undefined;
  value2: string | undefined;
  value3: string | undefined;
}
