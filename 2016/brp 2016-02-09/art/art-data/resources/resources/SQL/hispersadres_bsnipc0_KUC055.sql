select
  his_persadres.id 						id,
  his_persadres.persadres 				persadres,
  his_persadres.dataanvgel 				dataanvgel,
  his_persadres.dateindegel 			dateindegel,
  his_persadres.tsreg 					tsreg,
  his_persadres.tsverval 				tsverval,
  his_persadres.actieinh 				actieinh,
  his_persadres.actieverval 			actieverval,
  his_persadres.nadereaandverval 			nadereaandverval,
  his_persadres.actieaanpgel 			actieaanpgel,
  his_persadres.srt 					srt,
  his_persadres.rdnwijz 				rdnwijz,
  his_persadres.aangadresh 				aangadresh,
  his_persadres.dataanvadresh 			dataanvadresh,
  his_persadres.identcodeadresseerbaarobject 	identcodeadresseerbaarobject,
  his_persadres.identcodenraand 		identcodenraand,
  his_persadres.gem 					gem,
  his_persadres.nor 					nor,
  his_persadres.afgekortenor 			afgekortenor,
  his_persadres.gemdeel 				gemdeel,
  his_persadres.huisnr 					huisnr,
  his_persadres.huisletter 				huisletter,
  his_persadres.huisnrtoevoeging 		huisnrtoevoeging,
  his_persadres.postcode 				postcode,
  his_persadres.wplnaam 				wplnaam,
  his_persadres.loctenopzichtevanadres 			loctenopzichtevanadres,
  his_persadres.locoms 					locoms,
  his_persadres.bladresregel1 			bladresregel1,
  his_persadres.bladresregel2 			bladresregel2,
  his_persadres.bladresregel3   		bladresregel3,
  his_persadres.bladresregel4   		bladresregel4,
  his_persadres.bladresregel5  			bladresregel5,
  his_persadres.bladresregel6   		bladresregel6,
  his_persadres.landgebied            	landgebied,
  his_persadres.indpersaangetroffenopadres  indpersaangetroffenopadres
from
 kern.his_persadres,
 kern.persadres,
 kern.pers
where
 his_persadres.persadres = persadres.id and
 persadres.pers = pers.id and
 (pers.bsn=${DataSource Values#|bsn_rOO0|})
order by his_persadres.dataanvgel asc,  his_persadres.tsverval asc, his_persadres.dateindegel asc ;
