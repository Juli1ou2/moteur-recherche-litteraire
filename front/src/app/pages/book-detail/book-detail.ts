import {Component, inject, signal} from '@angular/core';
import {GutendexBook} from '../../core/models/gutenberg-book.model';
import {Card} from 'primeng/card';
import {Button, ButtonDirective} from 'primeng/button';
import {Tag} from 'primeng/tag';
import {Badge} from 'primeng/badge';
import {Divider} from 'primeng/divider';
import {Image} from 'primeng/image';
import {ScrollPanel} from 'primeng/scrollpanel';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {BooksService} from '../../core/services/books.service';

@Component({
  selector: 'app-book-detail',
  imports: [
    Card,
    Tag,
    Badge,
    Button,
    Divider,
    Image,
    ScrollPanel,
    RouterModule,
    CommonModule,
    ButtonDirective,
  ],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.scss',
})

export class BookDetail {
  book!: GutendexBook;
  bookId = signal('');
  private activatedRoute = inject(ActivatedRoute);
  private booksService: BooksService = inject(BooksService);

  constructor() {
    this.activatedRoute.params.subscribe((params) => {
      console.log("Searching...");
      this.bookId.set(params['id']);
      this.booksService.getGutendexBookFromId(this.bookId()).subscribe({
        next: (data) => {
          console.log('Book :', data);
          this.book = data;
        },
        error: (err) => {
          console.error('Erreur:', err);
        }
      });
    });
  }

  get coverImageUrl(): string {
    return this.book?.formats['image/jpeg'] ?? '';
  }
}
