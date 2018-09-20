insert into dashboard.berichten (id, partij, bericht, berichtdetails, aantalmeldingen, tsverzonden, bzm, soortactie, indprevalidatie) values (1002, 'Rotterdam', 'Rotterdam heeft een geboorte geprevalideerd.', 'Zie politierapport inschrijving door woongemeente Rotterdam waarbij verwezen zal worden naar wereldvreemde moeder.', 1, timestamp '2012-01-15 10:09:07', 'ProcuraBZM', 'GEBOORTE', true);
insert into dashboard.berichten (id, partij, bericht, berichtdetails, aantalmeldingen, tsverzonden, bzm, soortactie, indprevalidatie) values (1001, 'Utrecht', 'Utrecht heeft een verhuizing geprevalideerd.', 'Johan (BSN 111222333) heeft per 1 mei 2012 als adres: Lange Vijverberg 11 (Den Haag).', 1, timestamp '2012-05-15 10:09:08', 'CentricBZM', 'VERHUIZING', true);

commit;
