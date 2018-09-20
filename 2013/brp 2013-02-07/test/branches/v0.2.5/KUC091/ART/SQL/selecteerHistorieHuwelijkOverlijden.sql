SELECT 
  huwelijk.id,
  huwelijk.relatie,
  huwelijk.tsreg,
  huwelijk.tsverval,
  huwelijk.actieinh,
  huwelijk.actieverval,
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
  huwelijk.omsloceinde
FROM 
  kern.his_relatie huwelijk, 
  kern.betr,
  kern.pers 
WHERE 
  betr.relatie = huwelijk.relatie AND
  betr.pers = pers.id AND
 (pers.bsn = ${burgerservicenummer_ipr0})  
  order by betr.pers, huwelijk.dataanv ASC;