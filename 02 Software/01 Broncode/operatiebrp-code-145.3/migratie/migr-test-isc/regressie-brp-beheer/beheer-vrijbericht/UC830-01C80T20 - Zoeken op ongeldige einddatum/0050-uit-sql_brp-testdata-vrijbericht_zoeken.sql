
update kern.partij set srt = null where naam = 'Gemeente Utrecht';
update kern.his_partij set srt =null where naam = 'Gemeente Utrecht';
update kern.partij set srt = null where naam = 'Gemeente ''s-Gravenhage/Landelijke Taken/Kiezers buiten Nederland (1)';
update kern.his_partij set srt =null where naam = 'Gemeente ''s-Gravenhage/Landelijke Taken/Kiezers buiten Nederland (1)';

delete from kern.srtpartij;
insert into kern.srtpartij (naam) VALUES ('Gemeente');
insert into kern.srtpartij (naam) VALUES ('Transporteur');

update kern.partij set srt = (select id from kern.srtpartij where naam = 'Gemeente') where naam = 'Gemeente Utrecht';
update kern.partij set srt = (select id from kern.srtpartij where naam = 'Transporteur') where naam = 'Gemeente ''s-Gravenhage/Landelijke Taken/Kiezers buiten Nederland (1)';

delete from beh.vrijberpartij;
delete from beh.vrijber;

insert into beh.vrijber(srtber, srt, tsreg, data) values(1, 2, '2017-01-31 01:15:00', 'Dit is een test vrij bericht');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Dit is een test vrij bericht') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(1, 3, '2017-02-01 01:00:00', 'Dit is nog een test vrij bericht');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Dit is nog een test vrij bericht') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(1, 3, '2017-02-20 01:00:00', 'Dit is een vrij bericht aan meerdere partijen');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Dit is een vrij bericht aan meerdere partijen') , 348);
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Dit is een vrij bericht aan meerdere partijen') , 396);


insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 1, '2017-02-21 01:00:00', 'Dit is ander test vrij bericht');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Dit is ander test vrij bericht') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data, indgelezen) values(2, 1, '2017-02-22 01:00:00', 'Dit is ander test vrij bericht haarlem', true);
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Dit is ander test vrij bericht haarlem') , 396);

insert into beh.vrijber(srtber, srt, tsreg, data, indgelezen) values(2, 1, '2017-02-22 21:30:15', 'Diakrieten test ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ', true);
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Diakrieten test ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ') , 396);

insert into beh.vrijber(srtber, srt, tsreg, data, indgelezen) values(2, 1, '2017-02-22 21:45:15', 'Partij met een qout in de naam om te kijken of je daar op kan zoeken', true);
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Partij met een qout in de naam om te kijken of je daar op kan zoeken') , 394);

insert into beh.vrijber(srtber, srt, tsreg, data) values(1, 1, '2017-02-22 21:45:15', 'Partij met lange naam');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'Partij met lange naam') , 6539);
