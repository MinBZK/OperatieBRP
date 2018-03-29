import {SelectieIntervalPipe} from './selectie-interval-pipe';

describe('SelectieIntervalPipe', () => {
  it('create an instance', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe).toBeTruthy();
  });

  it('should give a correct value for interval 1, eenheid Maand', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, 1, 'Maand')).toBe('Elke maand');
  });

  it('should give a correct value for interval 1, eenheid Dag', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, 1, 'Dag')).toBe('Elke dag');
  });

  it('should give a correct value for interval >1, eenheid Maand', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, 2, 'Maand')).toBe('Eens per 2 maanden');
  });

  it('should give a correct value for interval >1, eenheid Dag', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, 2, 'Dag')).toBe('Eens per 2 dagen');
  });

  it('should return null when interval is not null, eenheid is null', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, 2, null)).toBeFalsy(null);
  });

  it('should return null when interval is not null, eenheid is empty', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, 2, '')).toBeFalsy(null);
  });

  it('should return null when interval is null, eenheid is not null', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, null, 'Dag')).toBeFalsy(null);
  });

  it('should return null when interval is null, eenheid is null', () => {
    const pipe = new SelectieIntervalPipe();
    expect(pipe.transform(null, null, null)).toBeFalsy(null);
  });
});

