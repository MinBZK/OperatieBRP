update mig_extractie_proces
set    startdatum = startdatum - INTERVAL '3 days'
where  process_instance_id = $$procesid$$
;
