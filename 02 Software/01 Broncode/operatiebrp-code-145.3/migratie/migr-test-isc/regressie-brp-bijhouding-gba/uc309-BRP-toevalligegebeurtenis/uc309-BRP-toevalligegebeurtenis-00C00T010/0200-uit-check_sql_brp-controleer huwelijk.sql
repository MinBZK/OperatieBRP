SELECT relatie.id
FROM   kern.relatie
JOIN   kern.betr ON betr.relatie = relatie.id
WHERE  relatie.srt = 1
AND    betr.pers = $$persoonid$$
