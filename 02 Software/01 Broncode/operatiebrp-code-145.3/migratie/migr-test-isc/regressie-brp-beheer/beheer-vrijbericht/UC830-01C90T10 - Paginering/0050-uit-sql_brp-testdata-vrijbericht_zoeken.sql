
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

-- berichten voor paginering...
insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:17:00', 'inkomend bericht 1');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 1') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:18:00', 'inkomend bericht 2');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 2') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:19:00', 'inkomend bericht 3');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 3') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:21:00', 'inkomend bericht 4');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 4') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:22:00', 'inkomend bericht 5');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 5') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:23:00', 'inkomend bericht 6');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 6') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:24:00', 'inkomend bericht 7');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 7') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:25:00', 'inkomend bericht 8');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 8') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:26:00', 'inkomend bericht 9');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 9') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:27:00', 'inkomend bericht 10');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 10') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:28:00', 'inkomend bericht 11');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 11') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:29:00', 'inkomend bericht 12');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 12') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:30:00', 'inkomend bericht 13');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 13') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:31:00', 'inkomend bericht 14');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 14') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:32:00', 'inkomend bericht 15');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 15') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:33:00', 'inkomend bericht 16');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 16') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:34:00', 'inkomend bericht 17');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 17') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:35:00', 'inkomend bericht 18');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 18') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:36:00', 'inkomend bericht 19');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 19') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:37:00', 'inkomend bericht 20');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 20') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:38:00', 'inkomend bericht 21');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 21') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:39:00', 'inkomend bericht 22');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 22') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:40:00', 'inkomend bericht 23');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 23') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:41:00', 'inkomend bericht 24');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 24') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:42:00', 'inkomend bericht 25');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 25') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:43:00', 'inkomend bericht 26');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 26') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:44:00', 'inkomend bericht 27');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 27') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:45:00', 'inkomend bericht 28');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 28') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:46:00', 'inkomend bericht 29');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 29') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:47:00', 'inkomend bericht 30');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 30') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:48:00', 'inkomend bericht 31');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 31') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:49:00', 'inkomend bericht 32');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 32') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:50:00', 'inkomend bericht 33');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 33') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:51:00', 'inkomend bericht 34');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 34') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:52:00', 'inkomend bericht 35');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 35') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:53:00', 'inkomend bericht 36');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 36') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:54:00', 'inkomend bericht 37');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 37') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:55:00', 'inkomend bericht 38');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 38') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:56:00', 'inkomend bericht 39');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 39') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:57:00', 'inkomend bericht 40');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 40') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:58:00', 'inkomend bericht 41');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 41') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 01:59:00', 'inkomend bericht 42');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 42') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:00:00', 'inkomend bericht 43');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 43') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:01:00', 'inkomend bericht 44');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 44') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:15:00', 'inkomend bericht 45');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 45') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:16:00', 'inkomend bericht 46');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 46') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:17:00', 'inkomend bericht 47');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 47') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:18:00', 'inkomend bericht 48');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 48') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:19:00', 'inkomend bericht 49');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 49') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:20:00', 'inkomend bericht 50');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 50') , 348);

insert into beh.vrijber(srtber, srt, tsreg, data) values(2, 2, '2017-01-31 02:21:00', 'inkomend bericht 51');
insert into beh.vrijberpartij(vrijber, partij) values ((select id from beh.vrijber where data = 'inkomend bericht 51') , 348);





