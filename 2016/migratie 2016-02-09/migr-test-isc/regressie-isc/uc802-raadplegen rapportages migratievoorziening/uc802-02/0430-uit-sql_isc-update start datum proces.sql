update mig_extractie_proces
set    wacht_startdatum = wacht_startdatum - INTERVAL '3 days'
where  process_instance_id = $$procesid$$
;
