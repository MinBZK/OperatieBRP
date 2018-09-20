/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging;

/**
 * Toegestane MDC velden.
 */
public enum MDCVeld {

    // ************* ALGEMEEN ***************
    /** Veld waarin de naam van de applicatie wordt gecommuniceerd ten behoeve van MDC logging. */
    MDC_APPLICATIE_NAAM("applicatie"),

    // ************* BERICHTAFHANDELING ***************
    /** Veld waarin de ID van de administratieve handeling wordt gecommuniceerd ten behoeve van MDC logging. */
    MDC_ADMINISTRATIEVE_HANDELING("administratieveHandeling"),
    /** Veld waarin de soort van de administratieve handeling wordt gecommuniceerd ten behoeve van MDC logging. */
    MDC_ADMINISTRATIEVE_HANDELING_SOORT("administratieveHandelingSoort"),
    /** Veld waarin de ID van het archiefbericht wordt vastgelegd. */
    MDC_BERICHT_ID("berichtArchiefId"),
    /** Veld waarin de Soort van het archiefbericht wordt vastgelegd. */
    MDC_BERICHT_SOORT("berichtSoort"),
    /** Veld waarin het referentienummer wordt vastgelegd voor logging. */
    MDC_REFERENTIE_NUMMER("referentienummer"),
    /** Veld met daarin de partijcode van de partij die het bericht heeft ingestuurd. */
    MDC_PARTIJ_CODE("partijcode"),
    /** Logging veld dat de leveringautorisatieId aangeeft. */
    MDC_LEVERINGAUTORISATIEID("leveringautorisatieId"),
    /** Logging veld dat de aangeroepen dienst aangeeft. */
    MDC_AANGEROEPEN_DIENST("dienst"),
    /** Logging veld dat de aangeroepen dienst aangeeft. */
    MDC_DIENST_CATEGORIE("dienstCategorie"),

    // ************* BIJHOUDING SPECIFIEK ***************
    /** Logging veld dat de actie die verwerkt wordt aangeeft. */
    MDC_ACTIE("actie"),
    /** Logging veld dat het aantal personen dat is bijgehouden wordt aangeeft. */
    MDC_PERSONEN_BIJGEHOUDEN("bijgehoudenPersonen"),

    // ************* BEVRAGING SPECIFIEK ***************

    // ************* LEVERING SPECIFIEK ***************
    /** Logging veld voor het toegang leveringsautorisatie id dat is gebruikt voor de levering. */
    MDC_TOEGANG_LEVERINGSAUTORISATIE_ID("toegangLeveringsautorisatie"),
    /** Logging veld dat het kanaal voor de levering aangeeft. */
    MDC_KANAAL("kanaal"),
    /** Logging veld dat het aantal personen voor het levering bericht aangeeft. */
    MDC_PERSONEN_GELEVERD("personenGeleverd");

    private final String veld;

    /**
     * Constructor.
     *
     * @param veld Het veld.
     */
    MDCVeld(final String veld) {
        this.veld = veld;
    }

    public String getVeld() {
        return this.veld;
    }
}
