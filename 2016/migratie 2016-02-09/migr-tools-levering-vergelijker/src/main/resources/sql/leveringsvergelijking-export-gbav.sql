-- maak een kopie van de data, vergeet niet het pad aan te passen als dat nodig is
COPY (SELECT * FROM mig_leveringsvergelijking_berichtcorrelatie_gbav) TO '/tmp/berichtcorrelatie_gbav.csv';
