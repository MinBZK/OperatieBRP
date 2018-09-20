update mig_configuratie set waarde = '0' where configuratie = 'sync.herhalingen';
update mig_configuratie set waarde = '1 seconds' where configuratie = 'sync.timeout';
commit;
