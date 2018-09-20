--------------------------------------------------
--- START Generated from: /var/lib/jenkins/workspace/BRP-Release/trunk/target/checkout/art/art-data/testdata/src/main/resources/brp-testdata/SierraTestdata-PersoonMetOnbekendeOuders.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (admhnd)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('37001','37','993',null,'20000101','20000101');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('37002','30','302',null,'20020202','20020202');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('37003','30','347',null,'20121212','20121212');

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

INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('37001','7','37001','993','20000101');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('37002','18','37002','302','20020202');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('37003','18','37003','347','20121212');

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

INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('37001','1','37003','19710717','993','Zevenaar',null,null,'229',null,null,null,null,null,null,null,null,'1','00000000','1',null,null,'1','true',null,'Joop',null,null,null,'Zoetemelk',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Sjefke',null,null,null,'Janssen','false','true','347','false','1','374852327','3701459389','20000101',null,null,'1','false',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (pers)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persafgeleidadministrati)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('37001','37001','20000101','20020202','37001','37002',null,'37001','20000101','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('37002','37001','20020202','20121212','37002','37003',null,'37002','20020202','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('37003','37001','20121212',null,'37003',null,null,'37003','20121212','1','FALSE',null);

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

INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn,nadereaandverval) VALUES('37001','37001','20000101','20000101','37001','3701459389','374852327',null);

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

INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('37001','37001','19710717',null,'20000101',null,'37001',null,null,'1',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persgeslachtsaand)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persgeslnaamcomp)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persgeslnaamcomp (id,pers,voorvoegsel,scheidingsteken,stam,predicaat,adellijketitel,volgnr) VALUES('37001','37001',null,null,'Zoetemelk',null,null,'1');

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

INSERT INTO kern.his_persgeslnaamcomp (id,persgeslnaamcomp,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,voorvoegsel,scheidingsteken,stam,predicaat,adellijketitel,nadereaandverval) VALUES('37001','37001','19710717',null,'20000101',null,'37001',null,null,null,null,'Zoetemelk',null,null,null);

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

INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('37001','37001','19710717',null,'20000101',null,'37001',null,null,null,'Sjefke',null,null,null,'Janssen','false','true',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_perssamengesteldenaam)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persnaamgebruik)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persnaamgebruik (id,pers,tsreg,tsverval,actieinh,actieverval,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,nadereaandverval) VALUES('37001','37001','20000101',null,'37001',null,'1','true',null,'Joop',null,null,null,'Zoetemelk',null);

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

INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('37001','37001','20000101',null,'37001',null,'19710717','993','Zevenaar',null,null,'229',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persgeboorte)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persoverlijden)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;


commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persoverlijden)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (0)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persadres)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persadres (id,pers,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres) VALUES('37001','37001','1','2',null,'20121212',null,null,'347','Vredenburg','Vredenburg','Noord','3','F','II','3511BA','Utrecht','by',null,null,null,null,null,null,null,'229',null);

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

INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('37001','37001','20000101',null,'20000101','20020202','37001','37002',null,'1','2',null,'20000101',null,null,'993','Reijmerstokkerdorpsstraat','dorpsstraat',null,'108',null,null,'6274NH',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('37002','37001','20000101','20020202','20020202',null,'37001',null,'37002','1','2',null,'20000101',null,null,'993','Reijmerstokkerdorpsstraat','dorpsstraat',null,'108',null,null,'6274NH',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('37003','37001','20020202',null,'20020202','20121212','37002','37003',null,'1','1','3','20020202',null,null,'302','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('37004','37001','20020202','20121212','20121212',null,'37002',null,'37003','1','1','3','20020202',null,null,'302','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('37005','37001','20121212',null,'20121212',null,'37003',null,null,'1','1','3','20121212','0347012012121231','0347202012121231','347','Vredenburg','Vredenburg','Noord','3','F','II','3511BA','Utrecht','by',null,null,null,null,null,null,null,'229',null,null);

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

INSERT INTO kern.relatie (id,srt,dataanv,gemaanv,wplnaamaanv,blplaatsaanv,blregioaanv,landgebiedaanv,omslocaanv,rdneinde,dateinde,gemeinde,wplnaameinde,blplaatseinde,blregioeinde,landgebiedeinde,omsloceinde) VALUES('37001','3',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (relatie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_relatie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;


commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_relatie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (5)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (betr)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('37001','37001','1','37001',null,null,null);
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('37002','37001','2',null,'true',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (betr)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (His_OuderOuderlijkGezag)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;


commit;


--------------------------------------------------
--- EIND
--- Sheet: (His_OuderOuderlijkGezag)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (0)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (His_OuderOuderschap)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_ouderouderschap (id,betr,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder,indouderuitwiekindisgeboren,nadereaandverval) VALUES('37002','37002','19710717',null,'20000101',null,'37001',null,null,'true',null,null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (His_OuderOuderschap)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persindicatie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;


commit;


--------------------------------------------------
--- EIND
--- Sheet: (persindicatie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (0)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persindicatie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;


commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persindicatie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (persvoornaam)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.persvoornaam (naam,id,pers,volgnr) VALUES('Joop','37001','37001','1');

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

INSERT INTO kern.his_persvoornaam (id,persvoornaam,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,naam) VALUES('37001','37001','19710717',null,'20000101',null,'37001',null,null,null,'Joop');

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

INSERT INTO kern.persnation (rdnverlies,rdnverk,id,pers,nation) VALUES(null,'86','37001','37001','2');

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

INSERT INTO kern.his_persnation (id,persnation,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,rdnverlies,rdnverk,nadereaandverval) VALUES('37001','37001','20000101',null,'20000101',null,'37001',null,null,null,'86',null);

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

INSERT INTO kern.his_persinschr (id,pers,tsreg,tsverval,actieinh,actieverval,datinschr,versienr,nadereaandverval,dattijdstempel) VALUES('37001','37001','20000101',null,'37001',null,'20000101','1',null,'20000101');

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

INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('37001','37001','20000101',null,'20000101','20020202','37001','37002',null,null,'993','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('37002','37001','20000101','20020202','20020202',null,'37001',null,null,'37002','993','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('37003','37001','20020202',null,'20020202','20121212','37002','37003',null,null,'302','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('37004','37001','20020202','20121212','20121212',null,'37002',null,null,'37002','302','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('37005','37001','20121212',null,'20121212',null,'37003',null,null,null,'347','1','1','false');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persbijhouding)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (18)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persmigratie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;


commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persmigratie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (44)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (-end-)
--- Handler: SheetHandlerImplTimestamp
--------------------------------------------------

begin;

DELETE FROM test.ARTversion;
INSERT INTO test.ARTversion (ID, FullVersion, ReleaseVersion, BuildTimestamp, ExcelTimestamp) VALUES (1, 'TestdataGenerator ${project.version}-r${svnrevision}-b${buildnumber} (${timestamp})','${project.version}','${timestamp}','07-10-2015 14:13:32');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (-end-)
--- Handler: SheetHandlerImplTimestamp
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /var/lib/jenkins/workspace/BRP-Release/trunk/target/checkout/art/art-data/testdata/src/main/resources/brp-testdata/SierraTestdata-PersoonMetOnbekendeOuders.xls
--------------------------------------------------