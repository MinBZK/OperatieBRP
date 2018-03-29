INSERT INTO autaut.dienst (dienstbundel, srt)
  SELECT
    (SELECT id
     FROM autaut.dienstbundel
     WHERE naam = 'Spontaan'),
    (SELECT id
     FROM autaut.srtdienst
     WHERE naam = 'Attendering');
