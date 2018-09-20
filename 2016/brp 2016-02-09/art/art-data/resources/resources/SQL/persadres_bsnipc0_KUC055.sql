select
  pa.srt 					srt,
  pa.rdnwijz                rdnwijz,
  pa.aangadresh 			aangadresh,
  pa.dataanvadresh          dataanvadresh,
  pa.identcodeadresseerbaarobject    identcodeadresseerbaarobject,
  pa.identcodenraand        identcodenraand,
  pa.gem 					gem,
  pa.nor                    nor,
  pa.afgekortenor           afgekortenor,
  pa.gemdeel                gemdeel,
  pa.huisnr 				huisnr,
  pa.huisletter 			huisletter,
  pa.huisnrtoevoeging 		huisnrtoevoeging,
  pa.postcode 				postcode,
  pa.wplnaam 					wplnaam,
  pa.loctenopzichtevanadres 			loctenopzichtevanadres,
  pa.locoms 				locoms,
  pa.bladresregel1 			bladresregel1,
  pa.bladresregel2 			bladresregel2,
  pa.bladresregel3 			bladresregel3,
  pa.bladresregel4 			bladresregel4,
  pa.bladresregel5 			bladresregel5,
  pa.bladresregel6 			bladresregel6,
  pa.landgebied 					landgebied,
  pa.indpersaangetroffenopadres  indpersaangetroffenopadres,
  pa.id 					id,
  pa.pers 					pers
from
  kern.persadres pa,
  kern.pers
where
  pa.pers = pers.id and
 (pers.bsn=${DataSource Values#|bsn_rOO0|});
