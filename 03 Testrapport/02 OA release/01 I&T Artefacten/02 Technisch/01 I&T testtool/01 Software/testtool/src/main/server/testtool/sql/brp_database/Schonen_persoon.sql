-- functie voor het verwijderen van een PL en gerelateerden op anr

CREATE OR REPLACE FUNCTION WisPersoon(aPersID Integer)
 RETURNS void AS
   $$
   DECLARE lRec RECORD;
   DECLARE lActies bigint[];
   DECLARE lHandelingen bigint[];
   DECLARE lID bigint;   
   DECLARE lSrt Integer;
   BEGIN
   SELECT Srt FROM Kern.Pers WHERE ID = aPersID INTO lSrt;
   
   RAISE notice 'Wissen persoon %, srt %', aPersID, lSrt;

   
   DELETE FROM AutAut.His_PersAfnemerindicatie WHERE PersAfnemerindicatie IN (SELECT ID FROM AutAut.PersAfnemerindicatie WHERE Pers = aPersID);
   DELETE FROM AutAut.PersAfnemerindicatie WHERE Pers = aPersID;

   SELECT array(SELECT DISTINCT AdmHnd FROM Kern.His_PersAfgeleidAdministrati WHERE Pers = aPersID) INTO lHandelingen;

   IF lSrt = 1 THEN
      FOR lRec IN 
          SELECT B2.Pers 
          FROM Kern.Betr B 
               JOIN Kern.Betr B2 ON B2.Relatie = B.Relatie
               JOIN Kern.Pers P ON B2.Pers = P.ID
          WHERE 
             B.Pers = aPersID AND
             B2.Pers <> B.Pers AND
             P.Srt = 2
          LOOP
          PERFORM WisPersoon(lRec.Pers);
        END LOOP;
       
       
       FOR lRec IN SELECT DISTINCT Relatie FROM Kern.Betr WHERE Pers = aPersID LOOP
          DELETE FROM Kern.His_Betr WHERE Betr IN (SELECT ID FROM Kern.Betr WHERE Relatie = lRec.Relatie);
          DELETE FROM Kern.His_OuderOuderlijkGezag WHERE Betr IN (SELECT ID FROM Kern.Betr WHERE Relatie = lRec.Relatie);
          DELETE FROM Kern.His_OuderOuderschap WHERE Betr IN (SELECT ID FROM Kern.Betr WHERE Relatie = lRec.Relatie);
          DELETE FROM VerConv.LO3AandOuder WHERE Ouder IN (SELECT ID FROM Kern.Betr WHERE Relatie = lRec.Relatie);

          DELETE FROM Kern.Betr WHERE Relatie = lRec.Relatie;

          DELETE FROM IST.StapelRelatie WHERE Relatie = lRec.Relatie;

          DELETE FROM Kern.His_Relatie WHERE Relatie = lRec.Relatie;
          
          DELETE FROM Kern.Relatie WHERE ID = lRec.Relatie;
       END LOOP;
     END IF;
   -- IST
   DELETE FROM IST.StapelRelatie WHERE Stapel IN (SELECT ID FROM IST.Stapel WHERE Pers = aPersID);
   DELETE FROM IST.StapelVoorkomen WHERE Stapel IN (SELECT ID FROM IST.Stapel WHERE Pers = aPersID);
   
   DELETE FROM IST.Stapel WHERE Pers = aPersID;

   -- Onderzoek
   FOR lRec IN SELECT ID FROM Kern.Onderzoek WHERE Pers = aPersID LOOP
      DELETE FROM Kern.His_GegevenInOnderzoek WHERE GegevenInOnderzoek IN (SELECT ID FROM Kern.GegevenInOnderzoek WHERE Onderzoek = lRec.ID);
      DELETE FROM Kern.GegevenInOnderzoek WHERE Onderzoek = lRec.ID;
      DELETE FROM Kern.His_Onderzoek WHERE Onderzoek = lRec.ID;
   
      DELETE FROM Kern.Onderzoek WHERE ID = lRec.ID;
    END LOOP;

   DELETE FROM Kern.His_PersBijhouding WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersDeelnEUVerkiezingen WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersGeboorte WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersGeslachtsaand WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersIDs WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersInschr WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersMigratie WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersNaamgebruik WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersNrverwijzing WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersOverlijden WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersPK WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersAfgeleidAdministrati WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersSamengesteldeNaam WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersUitslKiesr WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersVerblijfsr WHERE Pers = aPersID;
   
   DELETE FROM Kern.His_PersAdres WHERE PersAdres IN (SELECT ID FROM Kern.PersAdres WHERE Pers = aPersID);
   DELETE FROM Kern.PersAdres WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersBLPersnr WHERE PersBLPersnr IN (SELECT ID FROM Kern.PersBLPersnr WHERE Pers = aPersID);
   DELETE FROM Kern.PersBLPersnr WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersGeslnaamcomp WHERE PersGeslnaamcomp IN (SELECT ID FROM Kern.PersGeslnaamcomp WHERE Pers = aPersID);
   DELETE FROM Kern.PersGeslnaamcomp WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersIndicatie WHERE PersIndicatie IN (SELECT ID FROM Kern.PersIndicatie WHERE Pers = aPersID);
   DELETE FROM Kern.PersIndicatie WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersNation WHERE PersNation IN (SELECT ID FROM Kern.PersNation WHERE Pers = aPersID);
   DELETE FROM Kern.PersNation WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersReisdoc WHERE PersReisdoc IN (SELECT ID FROM Kern.PersReisdoc WHERE Pers = aPersID);
   DELETE FROM Kern.PersReisdoc WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersVerificatie WHERE PersVerificatie IN (SELECT ID FROM Kern.PersVerificatie WHERE Geverifieerde = aPersID);
   DELETE FROM Kern.PersVerificatie WHERE Geverifieerde = aPersID;
   DELETE FROM Kern.His_PersVerstrbeperking WHERE PersVerstrbeperking IN (SELECT ID FROM Kern.PersVerstrbeperking WHERE Pers = aPersID);
   DELETE FROM Kern.PersVerstrbeperking WHERE Pers = aPersID;
   DELETE FROM Kern.His_PersVoornaam WHERE PersVoornaam IN (SELECT ID FROM Kern.PersVoornaam WHERE Pers = aPersID);
   DELETE FROM Kern.PersVoornaam WHERE Pers = aPersID;

   DELETE FROM Kern.PersCache WHERE Pers = aPersID;

   DELETE FROM VerConv.LO3Melding WHERE LO3Voorkomen IN (SELECT ID FROM VerConv.LO3Voorkomen WHERE LO3Ber IN (SELECT ID FROM VerConv.LO3Ber WHERE Pers = aPersID));
   DELETE FROM VerConv.LO3Voorkomen WHERE LO3Ber IN (SELECT ID FROM VerConv.LO3Ber WHERE Pers = aPersID);
   DELETE FROM VerConv.LO3Ber WHERE Pers = aPersID;

   -- Handeling
   FOREACH lID IN ARRAY lHandelingen LOOP
   raise notice 'handeling %', lid;
      DELETE FROM IST.StapelVoorkomen WHERE AdmHnd = lID;
      DELETE FROM Kern.ActieBron WHERE Actie IN (SELECT ID FROM Kern.Actie WHERE AdmHnd = lID);
      DELETE FROM Kern.ActieBron WHERE Actie IN (SELECT ID FROM Kern.Actie WHERE AdmHnd = lID); 
      DELETE FROM VerConv.LO3Melding WHERE LO3Voorkomen IN (SELECT ID FROM VerConv.LO3Voorkomen WHERE Actie IN (SELECT ID FROM Kern.Actie WHERE AdmHnd = lID));
      DELETE FROM VerConv.LO3Voorkomen WHERE Actie IN (SELECT ID FROM Kern.Actie WHERE AdmHnd = lID); 
      
      DELETE FROM Kern.Actie WHERE AdmHnd = lID;
      DELETE FROM Kern.AdmHndGedeblokkeerdeRegel WHERE AdmHnd = lID;

      UPDATE Kern.Pers SET AdmHnd = NULL WHERE ID = aPersID AND AdmHnd = lID;
      DELETE FROM Kern.AdmHnd WHERE ID = lID;
   END LOOP;

   
   DELETE FROM Kern.Pers WHERE AdmHnd = aPersID;
   
   END
   $$ LANGUAGE plpgsql;

