select id from autaut.dienst where id=$$onderhoudenAfnIndDienstId$$ AND catalogusoptie = 3 AND abonnement = (select id from autaut.abonnement where naam = '999971 Spontaan');
