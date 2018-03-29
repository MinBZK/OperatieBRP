SELECT count(*) AS aantal
FROM prot.levsaantek
  JOIN prot.levsaantekpers ON prot.levsaantekpers.levsaantek = prot.levsaantek.id