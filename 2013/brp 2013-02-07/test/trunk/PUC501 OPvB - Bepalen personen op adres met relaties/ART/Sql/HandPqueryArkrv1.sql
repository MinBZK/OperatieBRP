SELECT 
  pers.bsn,
  huwelijk.dataanv, 
  huwelijk.gemaanv, 
  huwelijk.wplaanv, 
  huwelijk.blplaatsaanv, 
  huwelijk.blregioaanv, 
  huwelijk.landaanv, 
  huwelijk.omslocaanv, 
  huwelijk.rdneinde, 
  huwelijk.dateinde, 
  huwelijk.gemeinde, 
  huwelijk.wpleinde, 
  huwelijk.blplaatseinde, 
  huwelijk.blregioeinde, 
  huwelijk.landeinde, 
  huwelijk.omsloceinde, 
  huwelijk.relatiestatushis, 
  huwelijk.id, 
  huwelijk.srt, 
  betr.indouder, 
  betr.ouderschapstatushis, 
  betr.id, 
  betr.relatie, 
  betr.rol, 
  betr.pers, 
  betr.indouderheeftgezag, 
  betr.ouderlijkgezagstatushis,
  pers.id
FROM 
  kern.relatie huwelijk, 
  kern.betr,
  kern.pers 
WHERE 
  betr.relatie = huwelijk.id AND
  betr.pers = pers.id AND
  (pers.bsn = 305731828 or pers.bsn =  308853817)
  AND ( 
  huwelijk.srt = 1 or huwelijk.srt = 2)
  order by betr.pers, huwelijk.dataanv ASC;