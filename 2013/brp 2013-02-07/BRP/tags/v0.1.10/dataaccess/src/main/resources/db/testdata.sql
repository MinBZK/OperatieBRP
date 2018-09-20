
-- Personen en adressen
INSERT INTO kern.pers (id, srt, bsn, idsstatushis, geslachtsaand, geslachtsaandstatushis, voornamen, geslnaam, samengesteldenaamstatushis, aanschrstatushis,
    datgeboorte, gemgeboorte, wplgeboorte, landgeboorte, geboortestatushis, overlijdenstatushis, verblijfsrstatushis, uitslnlkiesrstatushis,
    euverkiezingenstatushis, bijhverantwoordelijkheidstat, opschortingstatushis, bijhgem, datinschringem, bijhgemstatushis, pkstatushis,
    immigratiestatushis, datinschr, inschrstatushis)
  VALUES (1, 1, '123456789', 'A', 1, 'A', 'Jan Pieter', 'Testelaar', 'A', 'A', 19751123, 858, 45, 229, 'A', 'A', 'A', 'A', 'A', 'A', 'A', 858, 19751123, 'A', 'A',
    'A', 19751123, 'A');
INSERT INTO kern.pers (id, srt, bsn, idsstatushis, geslachtsaand, geslachtsaandstatushis, voornamen, geslnaam, samengesteldenaamstatushis, aanschrstatushis,
    datgeboorte, gemgeboorte, wplgeboorte, landgeboorte, geboortestatushis, overlijdenstatushis, verblijfsrstatushis, uitslnlkiesrstatushis,
    euverkiezingenstatushis, bijhverantwoordelijkheidstat, opschortingstatushis, bijhgem, datinschringem, bijhgemstatushis, pkstatushis,
    immigratiestatushis, datinschr, inschrstatushis)
  VALUES (2, 1, '234567890', 'A', 2, 'A', 'Femke', 'Probeer', 'A', 'A', 19810503, 521, 246, 229, 'A', 'A', 'A', 'A', 'A', 'A', 'A', 521, 19810503, 'A', 'A',
    'A', 19810503, 'A');

INSERT INTO kern.persadres (id, pers, srt, dataanvadresh, gem, nor, afgekortenor, gemdeel, huisnr, postcode, wpl, land, persadresstatushis)
  VALUES (1, 1, 1, 19751123, 858, 'Kerkstraat', 'Kerkstr', 'Berkel Enschot', '12', '5056AB', 45, 229, 'A');
INSERT INTO kern.persadres (id, pers, srt, dataanvadresh, gem, nor, afgekortenor, gemdeel, huisnr, postcode, wpl, land, persadresstatushis)
  VALUES (2, 2, 1, 19941010, 521, 'Plein', 'Plein', 'Centrum', '3', '2511CR', 246, 229, 'A');

INSERT INTO kern.his_persadres (id, persadres, dataanvgel, tsreg, srt, dataanvadresh, gem, nor, afgekortenor, gemdeel, huisnr, postcode, wpl, land)
  VALUES (1, 1, 19751123, '1975-11-24 10:23:54+01', 1, 19751123, 858, 'Kerkstraat', 'Kerkstr', 'Berkel Enschot', '12', '5056AB', 45, 229);
INSERT INTO kern.his_persadres (id, persadres, dataanvgel, tsreg, tsverval, srt, dataanvadresh, gem, nor, afgekortenor, gemdeel, huisnr, postcode, wpl, land)
  VALUES (2, 2, 19810503, '1981-05-03 16:11:33+01', '1994-10-11 12:12:12+01', 1, 19810503, 521, 'Spui', 'Spui', 'Centrum', '1', '2511BL', 246, 229);
INSERT INTO kern.his_persadres (id, persadres, dataanvgel, dateindegel, tsreg, srt, dataanvadresh, gem, nor, afgekortenor, gemdeel, huisnr, postcode, wpl, land)
  VALUES (3, 2, 19810503, 19941010, '1994-10-11 12:12:12+01', 1, 19810503, 521, 'Spui', 'Spui', 'Centrum', '1', '2511BL', 246, 229);
INSERT INTO kern.his_persadres (id, persadres, dataanvgel, tsreg, srt, dataanvadresh, gem, nor, afgekortenor, gemdeel, huisnr, postcode, wpl, land)
  VALUES (4, 2, 19941010, '1994-10-11 12:12:12+01', 1, 19941010, 521, 'Plein', 'Plein', 'Centrum', '3', '2511CR', 246, 229);

--
-- insert into Kern.his_PersAdres (TsReg, TsVerval, DatAanvGel, DatEindeGel, adresseerbaarObject, AfgekorteNOR, BLAdresRegel1, BLAdresRegel2, BLAdresRegel3, BLAdresRegel4,
--     BLAdresRegel5, BLAdresRegel6, DatAanvAdresh, DatVertrekUitNederland, Gem, Gemdeel, huisletter, Huisnr, Huisnrtoevoeging, IdentcodeNraand, Land, LocOms,
--     LoctovAdres, NOR, PersAdres, postcode, Srt, Wpl, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)];