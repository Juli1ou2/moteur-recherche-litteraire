import {GutendexPersonDto} from './gutendex-person.model';

export interface GutendexBookDto {
  id: number;
  title: string;
  subjects: string[];
  authors: GutendexPersonDto[];
  summaries: string[];
  translators: GutendexPersonDto[];
  bookshelves: string[];
  languages: string[];
  copyright: boolean | null;
  media_type: string;
  formats: Record<string, string>;
  download_count: number;
}
