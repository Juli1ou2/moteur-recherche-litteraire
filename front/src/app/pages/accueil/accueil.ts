import {Component, inject, OnInit} from '@angular/core';
import {ButtonModule} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {BooksService} from '../../core/services/books.service';
import {GutendexBook} from '../../core/models/gutenberg-book.model';
import {CardModule} from 'primeng/card';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-accueil',
  imports: [
    ButtonModule, InputTextModule, FormsModule, CardModule, ProgressSpinnerModule, RouterLink
  ],
  templateUrl: './accueil.html',
  styleUrl: './accueil.scss',
  standalone: true
})
export class Accueil implements OnInit {
  private booksService: BooksService = inject(BooksService);
  value: string | undefined;
  searchedBooks: GutendexBook[] = [];
  loading = false;

  ngOnInit(): void {
  }

  onSearch() {
    if (!this.value || this.value.trim() === '') {
      return;
    }
    this.getBooksFromSearch(this.value.trim());
  }

  getBooksFromSearch(searchValue: string) {
    console.log("Searching...");
    this.loading = true;
    this.booksService.getBooksFromSearch(searchValue).subscribe({
      next: (data) => {
        console.log('Résultats pour', searchValue, ': ', data);
        this.searchedBooks = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur :', err);
        this.loading = false;
      }
    });
  }
}
