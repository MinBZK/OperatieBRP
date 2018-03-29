Narrative:
Huwelijk
Juiste personen opgehaald via deze query:
    SELECT pp.id, pp.bsn
    FROM kern.pers pp
    WHERE pp.bsn IS NOT NULL
    AND id NOT IN (SELECT p.id from kern.pers p
                    JOIN kern.betr b ON (b.pers = p.id)
                    JOIN kern.relatie r ON (b.relatie = r.id)
                    WHERE r.srt IN (1,2) AND p.bsn IS NOT NULL)

Scenario: Huwelijk

Meta:
@status Klaar
@auteur anjaw

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
Given administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap
And testdata uit bestand huwelijk_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Automatisch synchroniseren populatie NIET 036101 is ontvangen en wordt bekeken
