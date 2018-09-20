/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.web.AbstractAntwoordBericht;

/** Het antwoord bericht voor bevragingen. */
public class BevragingAntwoordBericht extends AbstractAntwoordBericht {

    private final Set<Persoon> gevondenPersonen;
    private final int          aantal;

    /**
     * Constructor die direct de velden initialiseert op basis van de waardes uit het opgegeven {@link
     * OpvragenPersoonResultaat}.
     *
     * @param opvragenPersoonResultaat het resultaat uit de business laag op basis waarvan de velden worden gezet.
     */
    public BevragingAntwoordBericht(final OpvragenPersoonResultaat opvragenPersoonResultaat) {
        super(opvragenPersoonResultaat.getMeldingen());

        gevondenPersonen = opvragenPersoonResultaat.getGevondenPersonen();
        if (gevondenPersonen != null) {
            aantal = gevondenPersonen.size();
        } else {
            aantal = 0;
        }
    }

    public Set<Persoon> getGevondenPersonen() {
        return gevondenPersonen;
    }

    /**
     * Retourneert het aantal gevonden personen.
     *
     * @return Het aantal gevonden personen op basis van het aantal elementen in gevondenPersonen.
     */
    public int getAantal() {
        return aantal;
    }
}
