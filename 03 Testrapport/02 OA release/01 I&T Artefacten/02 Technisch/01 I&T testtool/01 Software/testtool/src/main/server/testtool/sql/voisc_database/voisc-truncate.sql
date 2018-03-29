UPDATE voisc.lo3_mailbox SET laatste_wijziging_pwd_dt=null;
update voisc.lo3_mailbox set laatste_msseqnumber = null;

truncate table voisc.bericht;
truncate table voisc.logboek;
