import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {API_URL, GUTENDEX_API_URL} from '../utils/constants.utils';
import {Observable} from 'rxjs';
import {GutendexBook} from '../models/gutenberg-book.model';

@Injectable({
  providedIn: 'root',
})
export class BooksService {
  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);

  getBooksFromSearch(word: string): Observable<GutendexBook[]> {
    return this.http.get<GutendexBook[]>(API_URL + 'search/books/gutendex?word=' + word);
  }

  getGutendexBookFromId(id: string): Observable<GutendexBook> {
    return this.http.get<GutendexBook>(GUTENDEX_API_URL + 'books/' + id);
  }

  getBooksFromGutendexTest(id: number): Observable<GutendexBook[]> {
    return this.http.get<GutendexBook[]>(GUTENDEX_API_URL + 'books?ids=' + id);
  }
}
