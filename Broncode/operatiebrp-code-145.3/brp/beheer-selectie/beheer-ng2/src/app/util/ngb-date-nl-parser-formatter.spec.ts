import {NgbDateNlParserFormatter} from './ngb-date-nl-parser-formatter';

describe('NgbDateNlParserFormatter', () => {

    const parserFormatter = new NgbDateNlParserFormatter();

    describe('parsing', () => {
      it('should parse a date string into a structured object', () => {
        expect(parserFormatter.parse('31-01-2017')).toEqual({day: 31, month: 1, year: 2017});
      });

      it('should parse a null into null', () => {
        expect(parserFormatter.parse(null)).toBeFalsy(null);
      });
    });

    describe('formatting', () => {
        it('should format a date to formatted string', () => {
          expect(parserFormatter.format({year: 2017, month: 1, day: 31})).toBe('31-01-2017');
        });

        it('should format null to null', () => {
          expect(parserFormatter.format(null)).toBeFalsy(null);
        });
      }
    );
  }
);

