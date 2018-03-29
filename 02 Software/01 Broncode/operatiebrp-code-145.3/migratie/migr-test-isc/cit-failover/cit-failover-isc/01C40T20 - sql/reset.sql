update mig_configuratie set waarde = '3' where configuratie = 'sync.herhalingen';
update mig_configuratie set waarde = '24 hours' where configuratie = 'sync.timeout';
commit;
