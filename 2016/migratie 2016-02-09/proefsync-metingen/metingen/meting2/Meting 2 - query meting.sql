select count(bericht_id) as aantal_berichten, status, indicatie_beheerder from meting.tussenResultaat group by status, indicatie_beheerder ORDER BY status, indicatie_beheerder;

