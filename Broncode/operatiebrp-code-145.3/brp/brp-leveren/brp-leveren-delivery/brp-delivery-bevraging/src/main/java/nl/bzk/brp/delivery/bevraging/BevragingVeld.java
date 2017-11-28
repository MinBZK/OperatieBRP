/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import nl.bzk.algemeenbrp.util.common.logging.MDCVeld;

/**
 * BevragingVeld.
 */
enum BevragingVeld implements MDCVeld {

    /**
     * Veld waarin het referentienummer wordt vastgelegd voor logging.
     */
    MDC_REFERENTIE_NUMMER("referentienummer"),
    /**
     * Veld met daarin de partijcode van de partij die het bericht heeft ingestuurd.
     */
    MDC_PARTIJ_CODE("partijcode"),
    /**
     * Logging veld dat de leveringautorisatieId aangeeft.
     */
    MDC_LEVERINGAUTORISATIEID("leveringautorisatieId"),
    /**
     * Logging veld dat de aangeroepen dienst aangeeft.
     */
    MDC_AANGEROEPEN_DIENST("dienst");

    private final String veld;

    /**
     * Constructor.
     * @param veld Het veld.
     */
    BevragingVeld(final String veld) {
        this.veld = veld;
    }

    @Override
    public String getVeld() {
        return this.veld;
    }
}
