update voisc.bericht set 
tijdstip_verzonden = (SELECT date_trunc('day',LOCALTIMESTAMP - interval '75 hours') - interval '1 milliseconds')
;