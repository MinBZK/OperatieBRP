
--------------------------------------------------
--- START Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/SierraTestdata-PersoonOnderzoek.xls
--------------------------------------------------

--------------------------------------------------
--- START
--- Sheet: (admhnd)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27001','37','993',null,'20000101','20000101');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27002','30','302',null,'20020202','20020202');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27003','30','347',null,'20121212','20121212');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27004','77','347',null,'20121213','20121213');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27005','84','347',null,'20121216','20121216');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27006','83','347',null,'20121219','20121219');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27007','77','347',null,'20121224','20121224');
INSERT INTO kern.admhnd (id,srt,partij,toelichtingontlening,tsreg,tslev) VALUES('27008','77','302',null,'20121227','20121227');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (admhnd)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (8)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (brp_actie)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27001','7','27001','993','20000101');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27002','18','27002','302','20020202');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27003','18','27003','347','20121212');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27004','22','27004','347','20121213');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27005','51','27005','347','20121216');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27006','51','27006','347','20121219');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27007','22','27007','347','20121224');
INSERT INTO kern.actie (id,srt,admhnd,partij,tsreg) VALUES('27008','51','27008','302','20121227');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (brp_actie)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (8)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (pers)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('27001','1','27003','19710717','993','Zevenaar',null,null,'229',null,null,null,null,null,null,null,null,'1','20000101','1',null,null,'1','true',null,'Hennie',null,null,null,'Kuiper',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Hennie',null,null,null,'Kuiper','false','true','347','false','1','270012928','2701908038','20121227',null,null,'1','false',null);
INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('27002','2','27003','19400101',null,null,'Larashe',null,'125',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Thea',null,null,null,'Kuiper','false','true',null,null,'2',null,null,null,null,null,null,null,null);
INSERT INTO kern.pers (id,srt,admhnd,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,datoverlijden,gemoverlijden,wplnaamoverlijden,blplaatsoverlijden,blregiooverlijden,landgebiedoverlijden,omslocoverlijden,naderebijhaard,datinschr,versienr,vorigebsn,volgendebsn,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,gempk,indpkvollediggeconv,aandverblijfsr,srtmigratie,rdnwijzmigratie,aangmigratie,landgebiedmigratie,bladresregel1migratie,bladresregel2migratie,bladresregel3migratie,bladresregel4migratie,bladresregel5migratie,bladresregel6migratie,datmededelingverblijfsr,datvoorzeindeverblijfsr,inddeelneuverkiezingen,dataanlaanpdeelneuverkiezing,datvoorzeindeuitsleuverkiezi,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,bijhpartij,indonverwdocaanw,geslachtsaand,bsn,anr,tslaatstewijz,induitslkiesr,datvoorzeindeuitslkiesr,bijhaard,indonverwbijhvoorstelnieting,dattijdstempel) VALUES('27003','2','27003','19390101',null,null,'Larashe',null,'125',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'Bertus',null,null,null,'Kuiper','false','true',null,null,'1',null,null,null,null,null,null,null,null);

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

INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27001','27001','20000101','20020202','27001','27002',null,'27001','20000101','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27002','27001','20020202','20121212','27002','27003',null,'27002','20020202','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27003','27001','20121212','20121213','27003','27004',null,'27003','20121212','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27004','27001','20121213','20121216','27004','27005',null,'27004','20121213','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27005','27001','20121216','20121219','27005','27006',null,'27005','20121216','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27006','27001','20121219','20121224','27006','27007',null,'27006','20121219','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27007','27001','20121224','20121227','27007','27008',null,'27007','20121224','1','FALSE',null);
INSERT INTO kern.his_persafgeleidadministrati (id,pers,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd,tslaatstewijz,sorteervolgorde,indonverwbijhvoorstelnieting,tslaatstewijzgbasystematiek) VALUES('27008','27001','20121227',null,'27008',null,null,'27008','20121227','1','FALSE',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persafgeleidadministrati)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (8)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_persids)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_persids (id,pers,dataanvgel,tsreg,actieinh,anr,bsn,nadereaandverval) VALUES('27001','27001','20000101','20000101','27001','2701908038','270012928',null);

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

INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('27001','27001','19710717',null,'20000101',null,'27001',null,null,'1',null);
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('27002','27002','19400101',null,'20000101',null,'27001',null,null,'2',null);
INSERT INTO kern.his_persgeslachtsaand (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,geslachtsaand,nadereaandverval) VALUES('27003','27003','19390101',null,'20000101',null,'27001',null,null,'1',null);

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

INSERT INTO kern.persgeslnaamcomp (id,pers,voorvoegsel,scheidingsteken,stam,predicaat,adellijketitel,volgnr) VALUES('27001','27001',null,null,'Kuiper',null,null,'1');

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

INSERT INTO kern.his_persgeslnaamcomp (id,persgeslnaamcomp,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,voorvoegsel,scheidingsteken,stam,predicaat,adellijketitel,nadereaandverval) VALUES('27001','27001','19710717',null,'20000101',null,'27001',null,null,null,null,'Kuiper',null,null,null);

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

INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('27001','27001','19710717',null,'20000101',null,'27001',null,null,null,'Hennie',null,null,null,'Kuiper','false','true',null);
INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('27002','27002','19400101',null,'20000101',null,'27001',null,null,null,'Thea',null,null,null,'Kuiper','false','true',null);
INSERT INTO kern.his_perssamengesteldenaam (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,predicaat,voornamen,voorvoegsel,scheidingsteken,adellijketitel,geslnaamstam,indnreeks,indafgeleid,nadereaandverval) VALUES('27003','27003','19390101',null,'20000101',null,'27001',null,null,null,'Bertus',null,null,null,'Kuiper','false','true',null);

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

INSERT INTO kern.his_persnaamgebruik (id,pers,tsreg,tsverval,actieinh,actieverval,naamgebruik,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,adellijketitelnaamgebruik,geslnaamstamnaamgebruik,nadereaandverval) VALUES('27001','27001','20000101',null,'27001',null,'1','true',null,'Hennie',null,null,null,'Kuiper',null);

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

INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('27001','27001','20000101',null,'27001',null,'19710717','993','Zevenaar',null,null,'229',null,null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('27002','27002','20000101',null,'27001',null,'19400101',null,null,'Larashe',null,'125',null,null);
INSERT INTO kern.his_persgeboorte (id,pers,tsreg,tsverval,actieinh,actieverval,datgeboorte,gemgeboorte,wplnaamgeboorte,blplaatsgeboorte,blregiogeboorte,landgebiedgeboorte,omslocgeboorte,nadereaandverval) VALUES('27003','27003','20000101',null,'27001',null,'19390101',null,null,'Larashe',null,'125',null,null);

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

INSERT INTO kern.persadres (id,pers,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres) VALUES('27001','27001','1','2',null,'20121212',null,null,'347','Vredenburg','Vredenburg','Noord','3','F','II','3511BA','Utrecht','by',null,null,null,null,null,null,null,'229',null);

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

INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('27001','27001','20000101',null,'20000101','20020202','27001','27002',null,'1','2',null,'20000101',null,null,'993','Reijmerstokkerdorpsstraat','dorpsstraat',null,'108',null,null,'6274NH',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('27002','27001','20000101','20020202','20020202',null,'27001',null,'27002','1','2',null,'20000101',null,null,'993','Reijmerstokkerdorpsstraat','dorpsstraat',null,'108',null,null,'6274NH',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('27003','27001','20020202',null,'20020202','20121212','27002','27003',null,'1','1','3','20020202',null,null,'302','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('27004','27001','20020202','20121212','20121212',null,'27002',null,'27003','1','1','3','20020202',null,null,'302','Zonegge','Zonegge',null,'1',null,null,'6901KT',null,null,null,null,null,null,null,null,null,'229',null,null);
INSERT INTO kern.his_persadres (id,persadres,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,srt,rdnwijz,aangadresh,dataanvadresh,identcodeadresseerbaarobject,identcodenraand,gem,nor,afgekortenor,gemdeel,huisnr,huisletter,huisnrtoevoeging,postcode,wplnaam,loctenopzichtevanadres,locoms,bladresregel1,bladresregel2,bladresregel3,bladresregel4,bladresregel5,bladresregel6,landgebied,indpersaangetroffenopadres,nadereaandverval) VALUES('27005','27001','20121212',null,'20121212',null,'27003',null,null,'1','1','3','20121212','0347012012121231','0347202012121231','347','Vredenburg','Vredenburg','Noord','3','F','II','3511BA','Utrecht','by',null,null,null,null,null,null,null,'229',null,null);

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

INSERT INTO kern.relatie (id,srt,dataanv,gemaanv,wplnaamaanv,blplaatsaanv,blregioaanv,landgebiedaanv,omslocaanv,rdneinde,dateinde,gemeinde,wplnaameinde,blplaatseinde,blregioeinde,landgebiedeinde,omsloceinde) VALUES('27001','3',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);

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

INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('27001','27001','1','27001',null,null,null);
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('27002','27001','2','27002','true',null,null);
INSERT INTO kern.betr (id,relatie,rol,pers,indouder,indouderheeftgezag,indouderuitwiekindisgeboren) VALUES('27003','27001','2','27003','true',null,null);

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

INSERT INTO kern.his_ouderouderschap (id,betr,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder,indouderuitwiekindisgeboren,nadereaandverval) VALUES('27002','27002','19710717',null,'20000101',null,'27001',null,null,'true',null,null);
INSERT INTO kern.his_ouderouderschap (id,betr,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,indouder,indouderuitwiekindisgeboren,nadereaandverval) VALUES('27003','27003','19710717',null,'20000101',null,'27001',null,null,'true',null,null);

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

INSERT INTO kern.persvoornaam (naam,id,pers,volgnr) VALUES('Hennie','27001','27001','1');

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

INSERT INTO kern.his_persvoornaam (id,persvoornaam,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,naam) VALUES('27001','27001','19710717',null,'20000101',null,'27001',null,null,null,'Hennie');

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

INSERT INTO kern.persnation (rdnverlies,rdnverk,id,pers,nation) VALUES(null,'86','27001','27001','2');

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

INSERT INTO kern.his_persnation (id,persnation,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,actieaanpgel,rdnverlies,rdnverk,nadereaandverval) VALUES('27001','27001','20000101',null,'20000101',null,'27001',null,null,null,'86',null);

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

INSERT INTO kern.his_persinschr (id,pers,tsreg,tsverval,actieinh,actieverval,datinschr,versienr,nadereaandverval,dattijdstempel) VALUES('27001','27001','20000101',null,'27001',null,'20000101','1',null,'20000101');

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

INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('27001','27001','20000101',null,'20000101','20020202','27001','27002',null,null,'993','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('27002','27001','20000101','20020202','20020202',null,'27001',null,null,'27002','993','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('27003','27001','20020202',null,'20020202','20121212','27002','27003',null,null,'302','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('27004','27001','20020202','20121212','20121212',null,'27002',null,null,'27002','302','1','1','false');
INSERT INTO kern.his_persbijhouding (id,pers,dataanvgel,dateindegel,tsreg,tsverval,actieinh,actieverval,nadereaandverval,actieaanpgel,bijhpartij,bijhaard,naderebijhaard,indonverwdocaanw) VALUES('27005','27001','20121212',null,'20121212',null,'27003',null,null,null,'347','1','1','false');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_persbijhouding)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (18)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (onderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.onderzoek (id,admhnd,status,dataanv,dateinde,oms,verwachteafhandeldat) VALUES('27004','27004','3','20121213','20121219','Onderzoek dat gestart, gewijzigd en uiteindelijk beeindigd is.',null);
INSERT INTO kern.onderzoek (id,admhnd,status,dataanv,dateinde,oms,verwachteafhandeldat) VALUES('27007','27007','1','20121224',null,'Onderzoek dat gestart is',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (onderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (personderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.personderzoek (id,pers,onderzoek,rol) VALUES('27004','27001','27004','1');
INSERT INTO kern.personderzoek (id,pers,onderzoek,rol) VALUES('27007','27001','27007','1');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (personderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (partijonderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.partijonderzoek (id,partij,onderzoek,rol) VALUES('27004','347','27004','1');
INSERT INTO kern.partijonderzoek (id,partij,onderzoek,rol) VALUES('27007','347','27007','1');
INSERT INTO kern.partijonderzoek (id,partij,onderzoek,rol) VALUES('27008','302','27007','2');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (partijonderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (gegeveninonderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.gegeveninonderzoek (id,onderzoek,element,objectsleutelgegeven,voorkomensleutelgegeven) VALUES('27004','27004','3018',null,'27001');
INSERT INTO kern.gegeveninonderzoek (id,onderzoek,element,objectsleutelgegeven,voorkomensleutelgegeven) VALUES('27005','27007','3013',null,'27001');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (gegeveninonderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_onderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_onderzoek (id,onderzoek,status,dataanv,dateinde,oms,verwachteafhandeldat,tsreg,tsverval,actieinh,actieverval) VALUES('27004','27004','1','20121213',null,null,'20121217','20121213','20121216','27004','27005');
INSERT INTO kern.his_onderzoek (id,onderzoek,status,dataanv,dateinde,oms,verwachteafhandeldat,tsreg,tsverval,actieinh,actieverval) VALUES('27005','27004','1','20121213',null,null,'20121218','20121216','20121219','27005','27006');
INSERT INTO kern.his_onderzoek (id,onderzoek,status,dataanv,dateinde,oms,verwachteafhandeldat,tsreg,tsverval,actieinh,actieverval) VALUES('27006','27004','3','20121213','20121219',null,'20121218','20121219',null,'27006',null);
INSERT INTO kern.his_onderzoek (id,onderzoek,status,dataanv,dateinde,oms,verwachteafhandeldat,tsreg,tsverval,actieinh,actieverval) VALUES('27007','27007','1','20121224',null,null,'20130101','20121224','20121227','27007','27008');
INSERT INTO kern.his_onderzoek (id,onderzoek,status,dataanv,dateinde,oms,verwachteafhandeldat,tsreg,tsverval,actieinh,actieverval) VALUES('27008','27007','1','20121227',null,null,'20130101','20121227',null,'27008',null);

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_onderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (5)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_personderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_personderzoek (id,personderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,rol) VALUES('27004','27004','20121213',null,'27004',null,null,'1');
INSERT INTO kern.his_personderzoek (id,personderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,rol) VALUES('27007','27007','20121224',null,'27007',null,null,'1');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_personderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (2)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_partijonderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_partijonderzoek (id,partijonderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,rol) VALUES('27004','27004','20121213',null,'27004',null,null,'1');
INSERT INTO kern.his_partijonderzoek (id,partijonderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,rol) VALUES('27007','27007','20121224',null,'27007',null,null,'1');
INSERT INTO kern.his_partijonderzoek (id,partijonderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,rol) VALUES('27008','27008','20121227',null,'27008',null,null,'2');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_partijonderzoek)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (3)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (his_onderzoekafgeleidadminis)
--- Handler: SheetHandlerImplSimpleInsert
--------------------------------------------------

begin;

INSERT INTO kern.his_onderzoekafgeleidadminis (id,onderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd) VALUES('27004','27004','20121213','20121216','27004','27005',null,'27004');
INSERT INTO kern.his_onderzoekafgeleidadminis (id,onderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd) VALUES('27005','27004','20121216','20121219','27005','27006',null,'27005');
INSERT INTO kern.his_onderzoekafgeleidadminis (id,onderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd) VALUES('27006','27004','20121219',null,'27006',null,null,'27006');
INSERT INTO kern.his_onderzoekafgeleidadminis (id,onderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd) VALUES('27007','27007','20121224','20121227','27007','27008',null,'27007');
INSERT INTO kern.his_onderzoekafgeleidadminis (id,onderzoek,tsreg,tsverval,actieinh,actieverval,nadereaandverval,admhnd) VALUES('27008','27007','20121227',null,'27008',null,null,'27008');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (his_onderzoekafgeleidadminis)
--- Handler: SheetHandlerImplSimpleInsert
--- Aantal: (5)
--------------------------------------------------
--------------------------------------------------
--- START
--- Sheet: (-end-)
--- Handler: SheetHandlerImplTimestamp
--------------------------------------------------

begin;

DELETE FROM test.ARTversion;
INSERT INTO test.ARTversion (ID, FullVersion, ReleaseVersion, BuildTimestamp, ExcelTimestamp) VALUES (1, 'TestdataGenerator ${project.version}-r${svnrevision}-b${buildnumber} (${timestamp})','${project.version}','${timestamp}','14-11-2015 20:07:14');

commit;


--------------------------------------------------
--- EIND
--- Sheet: (-end-)
--- Handler: SheetHandlerImplTimestamp
--- Aantal: (1)
--------------------------------------------------
--------------------------------------------------
--- END Generated from: /media/truecrypt1/source/bmr_44/art/art-data/testdata/src/main/resources/brp-testdata/SierraTestdata-PersoonOnderzoek.xls
--------------------------------------------------