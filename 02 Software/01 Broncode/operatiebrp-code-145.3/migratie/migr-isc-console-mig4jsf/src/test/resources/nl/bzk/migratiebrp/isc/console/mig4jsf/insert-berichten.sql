insert into jbpm_processinstance(id_,version_)values(4321,0);

insert into mig_bericht(id,tijdstip,kanaal,richting,message_id,correlation_id,bericht,naam,process_instance_id,verzendende_partij,ontvangende_partij)
    values(42,now(),'VOISC', 'I', 'MSG-ID', 'CORR-ID', 'INHOUD', 'NAAM', 4321, '0518', '0519');

insert into mig_bericht(id,tijdstip,kanaal,richting,message_id,correlation_id,bericht,naam,process_instance_id,verzendende_partij,ontvangende_partij)
    values(43,now(),'MVI', 'U', 'MSG-ID2', 'CORR-ID2', 'BLABLA', 'ANDERS', 4321, '0654', '1233');

insert into mig_proces_gerelateerd(id,process_instance_id,soort_gegeven,gegeven)
    values(44,4321,'PTY','0518');

insert into mig_proces_gerelateerd(id,process_instance_id,soort_gegeven,gegeven)
    values(45,4321,'ANR','325254232');
