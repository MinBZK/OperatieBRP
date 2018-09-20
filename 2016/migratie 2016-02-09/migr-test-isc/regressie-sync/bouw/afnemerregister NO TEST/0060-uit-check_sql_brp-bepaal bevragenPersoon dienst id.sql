select id from autaut.dienst where id=$$bevragenPersoonDienstId$$ AND (catalogusoptie = 6 OR catalogusoptie = 9) AND abonnement = (select id from autaut.abonnement where naam = '999971 Ad hoc');
