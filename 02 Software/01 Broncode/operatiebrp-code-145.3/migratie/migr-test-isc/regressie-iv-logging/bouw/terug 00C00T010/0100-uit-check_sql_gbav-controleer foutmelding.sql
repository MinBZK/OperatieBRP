SELECT count(1) AS aantal
FROM initvul.initvullingresult
WHERE foutmelding_terugconversie = 'Dit is niet goed gegaan';
