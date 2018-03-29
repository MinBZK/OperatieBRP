/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;


/**
 * Filter dat bepaalt of de levering door mag gaan op basis van de populatie bepaling van de persoon ten opzichte van de populatieBeperking van een
 * abonnemnt-dienst combinatie.
 * <p>
 * Voor sommige {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst}en zijn er voorwaarden waarop dit niet mag.
 * <p>
 */
@Component("PersoonPopulatieFilter")
@Bedrijfsregel(Regel.R1333)
final class PersoonPopulatieFilterImpl implements Leveringfilter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean magLeveren(final Persoonslijst persoon, final Populatie populatie,
                              final Autorisatiebundel autorisatiebundel) {
        final boolean resultaat;

        switch (autorisatiebundel.getSoortDienst()) {
            case MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING:
                resultaat = Populatie.BUITEN != populatie;
                break;
            case MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE:
                resultaat = true;
                break;
            case ATTENDERING:
                resultaat = bepaalResultaatVoorAttendering(populatie);
                break;
            default:
                throw new IllegalArgumentException("Kan niet bepalen of de persoon geleverd moet worden voor populatie"
                        + populatie.getOmschrijving() + " en soort dienst " + autorisatiebundel.getSoortDienst());
        }

        if (!resultaat) {
            LOGGER.debug(
                    "Persoon {} zal niet geleverd worden voor dienst {} vanwege populatie {} voor categorie dienst {}.",
                    persoon.getMetaObject().getObjectsleutel(), autorisatiebundel.getDienst().getId(), populatie, autorisatiebundel
                            .getSoortDienst());
        }
        return resultaat;
    }

    private boolean bepaalResultaatVoorAttendering(final Populatie populatie) {
        final boolean resultaat;
        switch (populatie) {
            case BINNEN:
                resultaat = true;
                break;
            case BUITEN:
                resultaat = false;
                break;
            default:
                throw new IllegalArgumentException("Attendering kent alleen BINNEN en BUITEN populatie.");
        }
        return resultaat;
    }
}
