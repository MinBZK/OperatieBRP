-- 999 komt mee vanuit onze GBAV mailbox en heeft geen wachtwoord
UPDATE voisc.lo3_mailbox set mailboxpwd='6&S+1AF+' WHERE instantiecode=999 and mailboxnr='999';

-- wachtwoorden updaten voor de BRP gemeentes
update voisc.lo3_mailbox set mailboxpwd='6&B+1RP+' where instantietype='G';
update voisc.lo3_mailbox set mailboxpwd=null where instantiecode=599;

