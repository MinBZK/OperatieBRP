/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * GeldigheidUtil.
 */
@Bedrijfsregel(Regel.R2129)
final class GeldigheidUtil {

    private GeldigheidUtil() {
    }

    /**
     * @param aanvangDatum        aanvangDatum
     * @param eindeDatum          eindeDatum
     * @param historieVorm        historieVorm
     * @param materieelPeilmoment materieelPeilmoment
     * @return geldig
     */
    static boolean materieelGeldig(final Integer aanvangDatum, final Integer eindeDatum, final HistorieVorm historieVorm,
                                   final Integer materieelPeilmoment) {
        boolean materieelTotEnMetAkkoord = aanvangDatum == null || DatumUtil.valtDatumBinnenPeriode(materieelPeilmoment, aanvangDatum, null);
        // Als er een datum einde geldigheid bestaat en tov het peilmoment in het verleden ligt,
        // dan is dit een niet actueel geldig record, dus moet de historievorm inclusief materiele historie zijn.
        if (eindeDatum != null && !DatumUtil.valtDatumBinnenPeriode(materieelPeilmoment, aanvangDatum, eindeDatum)) {
            //zie regel R2129 voor geldigheid definitie
            materieelTotEnMetAkkoord &= historieVorm == HistorieVorm.MATERIEEL || historieVorm == HistorieVorm.MATERIEEL_FORMEEL;
        }
        return materieelTotEnMetAkkoord;
    }

    /**
     * @param registratieDatumTijd registratieDatumTijd
     * @param vervalDatumTijd      vervalDatumTijd
     * @param historieVorm         historieVorm
     * @param formeelPeilmoment    formeelPeilmoment
     * @return geldig
     */
    static boolean formeelGeldig(final ZonedDateTime registratieDatumTijd, final ZonedDateTime vervalDatumTijd,
                                 final HistorieVorm historieVorm, final ZonedDateTime formeelPeilmoment) {
        // Het record is formeel akkoord als de registratiedatum op of voor het peilmoment liggen
        // (oftewel het peilmoment na de registratiedatum).
        boolean formeleHistorieAkkoord = formeelPeilmoment.isAfter(registratieDatumTijd);
        // Als we te maken hebben met niet actuele formele historie (record is vervallen)
        // en tov het peilmoment in het verleden ligt, dan moet de historievorm inclusief formele historie zijn.
        if (vervalDatumTijd != null && formeelPeilmoment.isAfter(vervalDatumTijd)) {
            formeleHistorieAkkoord &= historieVorm == HistorieVorm.MATERIEEL_FORMEEL;
        }
        return formeleHistorieAkkoord;
    }
}
