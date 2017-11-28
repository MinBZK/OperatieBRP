/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Populatie;
import org.springframework.stereotype.Component;

/**
 * Bepaalt de soort synchronisatie op basis van de populatie, categorie dienst en de soort administratieve handeling.
 */
@Bedrijfsregel(Regel.R1338)
@Bedrijfsregel(Regel.R1348)
@Bedrijfsregel(Regel.R1333)
@Bedrijfsregel(Regel.R1334)
@Component
final class SoortSynchronisatieBepalerImpl implements SoortSynchronisatieBepaler {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public SoortSynchronisatie bepaalSoortSynchronisatie(final Populatie populatie, final SoortDienst soortDienst,
                                                         final SoortAdministratieveHandeling soortAdmHandeling) {
        final boolean isHandelingMetVolledigBericht = SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG == soortAdmHandeling;
        if (isHandelingMetVolledigBericht) {
            LOGGER.debug("GBA Bijhouding te complex voor mutatielevering, forceer volledigbericht");
        }
        //mutatielevering op afnemerindicatie levert mutatiebericht op tenzij gba bijhouding overig
        final boolean
                mutlevOpAfnemerindicatieMaarGeenHandelingMetVolledigBericht =
                !isHandelingMetVolledigBericht && SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == soortDienst;
        final SoortSynchronisatie soortSynchronisatie;
        if (mutlevOpAfnemerindicatieMaarGeenHandelingMetVolledigBericht) {
            soortSynchronisatie = SoortSynchronisatie.MUTATIE_BERICHT;
        } else {
            final boolean volledigBerichtPopulatie = populatie == null
                    || Populatie.BETREEDT == populatie;
            if (isHandelingMetVolledigBericht
                    || SoortDienst.ATTENDERING == soortDienst
                    || volledigBerichtPopulatie) {
                soortSynchronisatie = SoortSynchronisatie.VOLLEDIG_BERICHT;
            } else {
                soortSynchronisatie = SoortSynchronisatie.MUTATIE_BERICHT;
            }
        }
        return soortSynchronisatie;
    }

}
