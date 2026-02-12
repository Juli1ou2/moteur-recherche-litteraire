import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {API_URL, GUTENDEX_API_URL} from '../utils/constants.utils';
import {Observable} from 'rxjs';
import {GutendexBookDto} from '../models/gutenberg-book.model';

@Injectable({
  providedIn: 'root',
})
export class BooksService {
  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);

  getBooksFromSearch(word: string): Observable<GutendexBookDto[]> {
    return this.http.get<GutendexBookDto[]>(API_URL + 'search/books/gutendex?word=' + word);
  }

  getBooksFromGutendexTest(id: number): Observable<GutendexBookDto[]> {
    return this.http.get<GutendexBookDto[]>(GUTENDEX_API_URL + 'books?ids=' + id);
  }
}
