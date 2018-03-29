UPDATE kern.partijrol
SET rol=2
WHERE partij = (SELECT id FROM kern.partij WHERE naam = 'Gemeente Moerdijk Adhoc');