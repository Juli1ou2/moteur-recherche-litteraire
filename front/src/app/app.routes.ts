import {Routes} from '@angular/router';
import {Accueil} from './pages/accueil/accueil';
import {BookDetail} from './pages/book-detail/book-detail';

export const routes: Routes = [
  {path: "", component: Accueil, title: "Shajudan Search"},
  {path: "book-details/:id", component: BookDetail, title: "Book detail Gutendex"}
];
