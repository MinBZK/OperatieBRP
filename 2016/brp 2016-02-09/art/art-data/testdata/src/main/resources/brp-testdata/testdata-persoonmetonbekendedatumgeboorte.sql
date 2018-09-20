
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/SierraTestdata-PersoonMetOnbekendeDatumGeboorte.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (admhnd)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('39301','37','993',null,'20000101','20000101');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('39302','30','302',null,'20020202','20020202');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('39303','30','347',null,'20121212','20121212');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (admhnd)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (brp_actie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('39301','7','39301','993','20000101');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('39302','18','39302','302','20020202');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('39303','18','39303','347','20121212');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (brp_actie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (pers)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('39301','1','39303','20020000','993','Zevenaar',null,null,'229',null,null,null,null,null,null,null,null,'1','19910013','1',null,null,'1','true',null,'Joop',null,null,null,'Perreboom',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Joop',null,null,null,'Perreboom','false','true','347','false','1','393908586','7067243794','20000101',null,null,'1','false',null);
INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('39302','2','39301','19800301',null,null,'Larashe',null,'125',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Truus',null,null,null,'Zoutenbier','false','true',null,null,'2',null,null,null,null,null,null,null,null);
INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('39303','2','39301','19800101',null,null,'Larashe',null,'125',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Piet',null,null,null,'Parkiet','false','true',null,null,'1',null,null,null,null,null,null,null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (pers)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persafgeleidadministrati)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('39301','39301','20000101','20020202','39301','39302',null,'39301','20000101','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('39302','39301','20020202','20121212','39302','39303',null,'39302','20020202','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('39303','39301','20121212',null,'39303',null,null,'39303','20121212','1','FALSE',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persafgeleidadministrati)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persids)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn,nadereaandverval) VALUES('39301','39301','20000101','20000101','39301','7067243794','393908586',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persids)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persgeslachtsaand)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('39301','39301','20020000',null,'20000101',null,'39301',null,null,'1',null);
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('39302','39302','19800301',null,'20000101',null,'39301',null,null,'2',null);
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('39303','39303','19800101',null,'20000101',null,'39301',null,null,'1',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persgeslachtsaand)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persgeslnaamcomp)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persgeslnaamcomp (id,pers,voorvoegsel,scheidingsteken,stam,predicaat,adellijketitel,volgnr) VALUES('39301','39301',null,null,'Perreboom',null,null,'1');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (persgeslnaamcomp)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persgeslnaamcomp)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persgeslnaamcomp (id,persgeslnaamcomp,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,voorvoegsel,scheidingsteken,stam,predicaat,adellijketitel,nadereaandverval) VALUES('39301','39301','20020000',null,'20000101',null,'39301',null,null,null,null,'Perreboom',null,null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persgeslnaamcomp)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_perssamengesteldenaam)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('39301','39301','20020000',null,'20000101',null,'39301',null,null,null,'Joop',null,null,null,'Perreboom','false','true',null);
INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('39302','39302','19800301',null,'20000101',null,'39301',null,null,null,'Truus',null,null,null,'Zoutenbier','false','true',null);
INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('39303','39303','19800101',null,'20000101',null,'39301',null,null,null,'Piet',null,null,null,'Parkiet','false','true',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_perssamengesteldenaam)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persnaamgebruik)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persnaamgebruik (id,pers,tsreg,tsverval,actieinh,actieverval,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,nadereaandverval) VALUES('39301','39301','20000101',null,'39301',null,'1','true',null,'Joop',null,null,null,'Perreboom',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persnaamgebruik)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persgeboorte)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('39301','39301','20000101',null,'39301',null,'20020000','993','Zevenaar',null,null,'229',null,null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('39302','39302','20000101',null,'39301',null,'19800301',null,null,'Larashe',null,'125',null,null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('39303','39303','20000101',null,'39301',null,'19800101',null,null,'Larashe',null,'125',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persgeboorte)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persadres)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persadres (id,pers,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres) VALUES('39301','39301','1','2',null,'20121212',null,null,'347','Vredenburg','Vredenburg','Noord','5','F','II','3511BA','Utrecht','by',null,null,null,null,null,null,null,'229',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (persadres)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persadres)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('39301','39301','20000101',null,'20000101','20020202','39301','39302',null,'1','2',null,'20000101',null,null,'993','Reijmerstokkerdorpsstraat','dorpsstraat',null,'108',null,null,'6274NH',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('39302','39301','20000101','20020202','20020202',null,'39301',null,'39302','1','2',null,'20000101',null,null,'993','Reijmerstokkerdorpsstraat','dorpsstraat',null,'108',null,null,'6274NH',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('39303','39301','20020202',null,'20020202','20121212','39302','39303',null,'1','1','3','20020202',null,null,'302','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('39304','39301','20020202','20121212','20121212',null,'39302',null,'39303','1','1','3','20020202',null,null,'302','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('39305','39301','20120000',null,'20121212',null,'39303',null,null,'1','1','3','20120000','0347012012000031','0347202012000031','347','Vredenburg','Vredenburg','Noord','3','F','II','3511BA','Utrecht','by',null,null,null,null,null,null,null,'229',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persadres)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (5)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (relatie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.relatie (id,srt,dataanv,gemaanv,wplnaamaanv,blplaatsaanv,blregioaanv,landgebiedaanv,omslocaanv,rdneinde,dateinde,gemeinde,wplnaameinde,blplaatseinde,blregioeinde,landgebiedeinde,omsloceinde) VALUES('39301','3',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (relatie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (betr)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('39301','39301','1','39301',null,null,null);
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('39302','39301','2','39302','true',null,null);
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('39303','39301','2','39303','true',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (betr)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (His_OuderOuderschap)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_ouderouderschap (id,betr,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder,indouderuitwiekindisgeboren,nadereaandverval) VALUES('39301','39302','19800301',null,'20000101',null,'39301',null,null,'true',null,null);
INSERT INTO kern.his_ouderouderschap (id,betr,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder,indouderuitwiekindisgeboren,nadereaandverval) VALUES('39302','39303','19800101',null,'20000101',null,'39301',null,null,'true',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (His_OuderOuderschap)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persvoornaam)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persvoornaam (naam,id,pers,volgnr) VALUES('Joop','39301','39301','1');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (persvoornaam)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persvoornaam)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persvoornaam (id,persvoornaam,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,naam) VALUES('39301','39301','20020000',null,'20000101',null,'39301',null,null,null,'Joop');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persvoornaam)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persnation)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persnation (rdnverlies,rdnverk,id,pers,nation) VALUES(null,'86','39301','39301','2');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (persnation)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persnation)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persnation (id,persnation,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,rdnverlies,rdnverk,nadereaandverval) VALUES('39301','39301','20000101',null,'20000101',null,'39301',null,null,null,'86',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persnation)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persinschr)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persinschr (id,pers,tsreg,tsverval,actieinh,actieverval,datinschr,versienr,nadereaandverval,dattijdstempel) VALUES('39301','39301','20000101',null,'39301',null,'20000101','1',null,'20000101');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persinschr)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persbijhouding)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('39301','39301','20000101',null,'20000101','20020202','39301','39302',null,null,'993','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('39302','39301','20000101','20020202','20020202',null,'39301',null,null,'39302','993','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('39303','39301','20020202',null,'20020202','20121212','39302','39303',null,null,'302','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('39304','39301','20020202','20121212','20121212',null,'39302',null,null,'39302','302','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('39305','39301','20120000',null,'20121212',null,'39303',null,null,null,'347','1','1','false');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persbijhouding)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (18)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (-end-)
--- Handler: SheetHandlerImplTimestamp
--------------------------------------------------

begin;

DELETE FROM test.ARTversion;
INSERT INTO test.ARTversion (ID, FullVersion, ReleaseVersion, BuildTimestamp, ExcelTimestamp) VALUES (1, 'TestdataGenerator ${project.version}-r${svnrevision}-b${buildnumber} (${timestamp})','${project.version}','${timestamp}','07-10-2015 14:15:10');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (-end-)
--- Handler: SheetHandlerImplTimestamp
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/SierraTestdata-PersoonMetOnbekendeDatumGeboorte.xls
--------------------------------------------------