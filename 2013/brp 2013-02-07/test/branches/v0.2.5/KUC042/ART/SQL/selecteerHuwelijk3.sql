SELECT 
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
  (pers.bsn = ${DataSource Values#burgerservicenummer_ipp_1} AND pers.bsn= ${DataSource Values#burgerservicenummer_ipp_2}) 
  AND  
  huwelijk.srt = 1
  order by betr.pers ASC;