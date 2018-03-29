DO $$
DECLARE
	bijhouderPartij kern.partijrol;
	leveringsautorisatieId INTEGER;
	dienstbundelId INTEGER;
BEGIN
   FOR bijhouderPartij IN SELECT * FROM kern.partijrol WHERE partij in (select partij from kern.gem) and rol in (2,3,4) and dateinde is null LOOP
	RAISE NOTICE 'Toevoegen leveringsautorisatie';
	INSERT INTO autaut.levsautorisatie(
        	stelsel,
        	indmodelautorisatie,
        	naam,
        	protocolleringsniveau,
        	indaliassrtadmhndleveren,
        	datingang,
        	indag
    ) VALUES (
        	2,
        	false,
        	concat('Afnemersindicatie opvragen PL ', (select naam from kern.partij where partij.id = (select partij from kern.partijrol where partijrol.id = bijhouderPartij.id))),
        	1,
        	true,
        	bijhouderPartij.datingang,true
    ) RETURNING id INTO leveringsautorisatieId;
	RAISE NOTICE 'Toevoegen toegang voor autorisatie %', leveringsautorisatieId;
	INSERT INTO autaut.toeganglevsautorisatie(
        	geautoriseerde,
        	levsautorisatie,
        	datingang,
        	indag
    ) VALUES (
        	bijhouderPartij.id,
        	leveringsautorisatieId,
        	bijhouderPartij.datingang,
        	true);
    RAISE NOTICE 'Toevoegen dienstbundel voor autorisatie %', leveringsautorisatieId;
	INSERT INTO autaut.dienstbundel(
        	levsautorisatie,
        	naam,
        	naderepopulatiebeperking,
        	datingang,
        	indag
    ) VALUES (
        	leveringsautorisatieId,
        	'OpvragenPL',
        	'WAAR',
        	bijhouderPartij.datingang,
        	true
    ) RETURNING id INTO dienstbundelId;
    RAISE NOTICE 'Toevoegen dienst voor dienstbundel %', dienstbundelId;
    INSERT INTO autaut.dienst(
        	dienstbundel,
        	srt,
        	datingang,
        	indag
    ) VALUES (
        	dienstbundelId,
        	5,
        	bijhouderPartij.datingang,
        	true
    );
    RAISE NOTICE 'Toevoegen dienstbundello3rubrieken voor dienstbundel %', dienstbundelId;
    INSERT INTO autaut.dienstbundello3rubriek(
        	dienstbundel,
        	rubr
    ) select dienstbundelId, rubr.id from conv.convlo3rubriek rubr;
   END LOOP;
END; $$;
