import {Component, OnInit} from '@angular/core';
import {GutendexBookDto} from '../../core/models/gutenberg-book.model';
import {Card} from 'primeng/card';
import {Button, ButtonDirective} from 'primeng/button';
import {Tag} from 'primeng/tag';
import {Badge} from 'primeng/badge';
import {Divider} from 'primeng/divider';
import {Image} from 'primeng/image';
import {ScrollPanel} from 'primeng/scrollpanel';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

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

export class BookDetail implements OnInit{

  gutendexBookDto!: GutendexBookDto;


  oneObject = {
    count: 1,
    next: null,
    previous: null,
    results: [
      {
        id: 9902,
        title: "The Middle of Things",
        authors: [
          {
            name: "Fletcher, J. S. (Joseph Smith)",
            birth_year: 1863,
            death_year: 1935
          }
        ],
        summaries: [
          "blableblabekla",
          "nladnkffc"
        ],
        editors: [],
        translators: [],
        subjects: ["Detective and mystery stories"],
        bookshelves: [
          "Category: British Literature",
          "Category: Crime, Thrillers and Mystery",
          "Category: Novels",
          "Detective Fiction"
        ],
        languages: ["en"],
        copyright: false,
        media_type: "Text",
        formats: {
          "image/jpeg": "https://www.gutenberg.org/cache/epub/9902/pg9902.cover.medium.jpg"
        },
        download_count: 289
      }
    ]
  };

  ngOnInit(): void {
    this.gutendexBookDto = this.oneObject.results[0];
  }

  get coverImageUrl(): string {
    return this.gutendexBookDto?.formats['image/jpeg'] ?? '';
  }


}
