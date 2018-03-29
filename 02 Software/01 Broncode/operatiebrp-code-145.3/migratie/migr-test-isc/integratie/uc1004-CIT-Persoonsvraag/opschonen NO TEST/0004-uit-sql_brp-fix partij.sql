update kern.partij set datovergangnaarbrp = '20000101' where code = '199902';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999972'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999972');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999972');
delete from kern.partij where code = '999972';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999973'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999973');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999973');
delete from kern.partij where code = '999973';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999974'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999974');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999974');
delete from kern.partij where code = '999974';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999975'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999975');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999975');
delete from kern.partij where code = '999975';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999976'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999976');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999976');
delete from kern.partij where code = '999976';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999977'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999977');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999977');
delete from kern.partij where code = '999977';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP','999971','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999971';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 3;
insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP(2)','999972','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999972';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999972';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999972';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999972') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999972') and rol = 3;
insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP(3)','999973','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999973';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999973';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999973';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999973') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999973') and rol = 3;
insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP(4)','999974','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999974';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999974';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999974';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999974') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999974') and rol = 3;
insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP(5)','999975','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999975';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999975';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999975';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999975') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999975') and rol = 3;
insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP(6)','999976','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999976';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999976';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999976';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999976') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999976') and rol = 3;
insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP(7)','999977','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999977';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999977';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999977';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999977') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999977') and rol = 3;
