import {Component, inject, OnInit} from '@angular/core';
import {ButtonModule} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {BooksService} from '../../core/services/books.service';

@Component({
  selector: 'app-accueil',
  imports: [
    ButtonModule, InputTextModule, FormsModule
  ],
  templateUrl: './accueil.html',
  styleUrl: './accueil.scss',
  standalone: true
})
export class Accueil implements OnInit {
  value: string | undefined;
  private booksService: BooksService = inject(BooksService);

  ngOnInit(): void {
    this.booksService.getBooksFromSearch("dead").subscribe((books) => {
      console.log(books)
    });
  }
}
