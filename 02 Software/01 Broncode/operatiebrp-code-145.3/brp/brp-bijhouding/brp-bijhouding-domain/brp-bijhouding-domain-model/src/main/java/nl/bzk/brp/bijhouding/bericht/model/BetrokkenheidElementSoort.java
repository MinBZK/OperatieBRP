/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;

/**
 * De verschillende soorten betrokkenheden elementen.
 */
public enum BetrokkenheidElementSoort {

    /** kind. */
    KIND("kind", SoortBetrokkenheid.KIND),
    /** ouder. */
    OUDER("ouder", SoortBetrokkenheid.OUDER),
    /** partner. */
    PARTNER("partner", SoortBetrokkenheid.PARTNER);

    private final String elementNaam;
    private final SoortBetrokkenheid soortBetrokkenheid;

    /**
     * Maakt een BetrokkenheidElementSoort object.
     *
     * @param elementNaam
     *            de naam van het element in het bericht
     * @param soortBetrokkenheid
     */
    BetrokkenheidElementSoort(final String elementNaam, final SoortBetrokkenheid soortBetrokkenheid) {
        this.elementNaam = elementNaam;
        this.soortBetrokkenheid = soortBetrokkenheid;
    }

    /**
     * De element naam.
     * 
     * @return element naam.
     */
    public String getElementNaam() {
        return elementNaam;
    }

    /**
     * Geef de waarde van soortBetrokkenheid.
     *
     * @return soortBetrokkenheid
     */
    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return soortBetrokkenheid;
    }

    /**
     * De lijst van element namen.
     *
     * @return element namen
     */
    public static List<String> getElementNamen() {
        final List<String> result = new ArrayList<>();
        for (BetrokkenheidElementSoort soort : values()) {
            result.add(soort.getElementNaam());
        }
        return result;
    }

    /**
     * Geeft het soort dat correspondeert met de gegeven element naam.
     * 
     * @param elementNaam de element naam
     * @return het soort
     * @throws OngeldigeWaardeException wanneer er geen betrokkenheid correspondeert met de gegeven elementnaam.
     */
    public static BetrokkenheidElementSoort parseElementNaam(final String elementNaam) throws OngeldigeWaardeException {
        for (final BetrokkenheidElementSoort soort : BetrokkenheidElementSoort.values()) {
            if (soort.getElementNaam().equals(elementNaam)) {
                return soort;
            }
        }
        throw new OngeldigeWaardeException(String.format("Het element '%s' correspondeert niet met een soort betrokkenheid.", elementNaam));
    }
}
