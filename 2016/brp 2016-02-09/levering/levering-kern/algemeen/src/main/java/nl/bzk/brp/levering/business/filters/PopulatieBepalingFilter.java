/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Filter dat bepaalt of de levering door mag gaan op basis van de populatie bepaling van de
 * persoon ten opzichte van de populatieBeperking van een abonnemnt-dienst combinatie.
 * <p/>
 * Voor sommige {@link nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst}en zijn er voorwaarden waarop dit
 * niet mag.
 *
 * brp.bedrijfsregel VR00057
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Regels(Regel.VR00057)
public class PopulatieBepalingFilter implements LeverenPersoonFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final boolean magLeverenDoorgaan(final PersoonHisVolledig persoon, final Populatie populatie,
            final Leveringinformatie leveringinformatie, final AdministratieveHandelingModel administratieveHandeling)
    {
        final boolean resultaat;

        switch (leveringinformatie.getSoortDienst()) {
            case MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING:
                resultaat = Populatie.BUITEN != populatie;
                break;
            case MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE:
                resultaat = true;
                break;
            case ATTENDERING:
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
                break;
            default:
                throw new IllegalArgumentException("Kan niet bepalen of de persoon geleverd moet worden voor populatie"
                        + administratieveHandeling.getSoort() + " en catalogusoptie " + leveringinformatie.getSoortDienst());
        }

        if (!resultaat) {
            LOGGER.debug(
                "Persoon {} zal niet geleverd worden voor dienst {} vanwege populatie {} voor categorie dienst {}.",
                persoon.getID(), leveringinformatie.getDienst().getID(), populatie, leveringinformatie
                    .getSoortDienst());
        }
        return resultaat;
    }
}
