/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.logging;

import nl.bzk.algemeenbrp.util.common.logging.MDCVeld;

/**
 * Levering MDC velden.
 */
public enum LeveringVeld implements MDCVeld {

    /**
     * Veld waarin de naam van de applicatie wordt gecommuniceerd ten behoeve van MDC logging.
     */
    MDC_APPLICATIE_NAAM("applicatie"),
    /**
     * Veld waarin de Soort van het archiefbericht wordt vastgelegd.
     */
    MDC_BERICHT_SOORT("berichtSoort"),
    /**
     * Logging veld dat de aangeroepen dienst aangeeft.
     */
    MDC_DIENST_CATEGORIE("dienstCategorie"),
    /**
     * Veld waarin de ID van de administratieve handeling wordt gecommuniceerd ten behoeve van MDC logging.
     */
    MDC_ADMINISTRATIEVE_HANDELING("administratieveHandeling"),
    /**
     * Logging veld dat de leveringautorisatieId aangeeft.
     */
    MDC_LEVERINGAUTORISATIEID("leveringautorisatieId"),
    /**
     * Logging veld dat de aangeroepen dienst aangeeft.
     */
    MDC_AANGEROEPEN_DIENST("dienst"),
    /**
     * Veld waarin het referentienummer wordt vastgelegd voor logging.
     */
    MDC_REFERENTIE_NUMMER("referentienummer"),
    /**
     * Logging veld dat het kanaal voor de levering aangeeft.
     */
    MDC_KANAAL("kanaal"),
    /**
     * Logging veld voor het toegang leveringsautorisatie id dat is gebruikt voor de levering.
     */
    MDC_TOEGANG_LEVERINGSAUTORISATIE_ID("toegangLeveringsautorisatie"),
    /**
     * Logging veld voor het toegang bijhoudingsautorisatie id dat is gebruikt voor de levering.
     */
    MDC_BIJHOUDINGSAUTORISATIE_ID("toegangBijhoudingssautorisatie"),
    /**
     * Veld met daarin de partijcode van de partij die het bericht heeft ingestuurd.
     */
    MDC_PARTIJ_ID("partijId"),
    /**
     * Logging veld dat het aantal personen voor het levering bericht aangeeft.
     */
    MDC_PERSONEN_GELEVERD("personenGeleverd");

    private final String veld;

    /**
     * Constructor.
     * @param veld Het veld.
     */
    LeveringVeld(final String veld) {
        this.veld = veld;
    }

    @Override
    public String getVeld() {
        return this.veld;
    }
}
