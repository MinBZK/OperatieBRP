select id from autaut.dienst where id=$$bevragenAdresDienstId$$ AND (catalogusoptie = 7 OR catalogusoptie = 8) AND abonnement = (select id from autaut.abonnement where naam = '999971 Ad hoc');
