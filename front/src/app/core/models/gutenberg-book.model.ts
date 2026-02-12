import {GutendexPerson} from './gutendex-person.model';

export interface GutendexBook {
  id: number;
  title: string;
  subjects: string[];
  authors: GutendexPerson[];
  summaries: string[];
  translators: GutendexPerson[];
  bookshelves: string[];
  languages: string[];
  copyright: boolean | null;
  media_type: string;
  formats: Record<string, string>;
  download_count: number;
}
