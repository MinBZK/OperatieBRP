/* geeft de aantal berichten per status */
select count(bericht_id) as aantal_berichten, status, indicatie_beheerder from meting.tussenResultaat group by status, indicatie_beheerder ORDER BY status, indicatie_beheerder;

/* selecteer de dubbele berichten in het resultaat */
select bericht_id from (select bericht_id, ROW_NUMBER() OVER(PARTITION BY bericht_id ORDER BY bericht_id asc) AS Row
FROM meting.tussenResultaat) meting where meting.Row > 1;

/* groene pijl (berichten die goed verwerkt zijn) */
select bericht_id from meting.tussenResultaat where status = '0'; 

/* oranje pijl (berichten die uitgevallen zijn) */
select bericht_id from meting.tussenResultaat where status != '0';
