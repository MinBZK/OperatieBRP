/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

/**
 * De soorten bedrijfsregel.
 */
public enum SoortTekst {
    /**
     * Definitie.
     */
    DEF("Definitie"),
    /**
     * Toelichting (op de definitie).
     */
    DEFT("Toelichting (op de definitie)"),
    /**
     * Populatie beschrijving.
     */
    POP("Populatie beschrijving"),
    /**
     * Modelleer- en ontwerpbeslissingen.
     */
    MOB("Modelleer- en ontwerpbeslissingen"),
    /**
     * Uitvoeringstoelichting.
     */
    UITT("Uitvoeringstoelichting"),
    /**
     * Conversie toelichting.
     */
    CONT("Conversie toelichting"),
    /**
     * Realisatie toelichting.
     */
    REAT("Realisatie toelichting"),
    /**
     * Aantekeningen.
     */
    AAN("Aantekeningen"),
    /**
     * Pseudo algoritme.
     */
    PSA("Pseudo algoritme"),
    /**
     * Logboek/verslag.
     */
    LOG("Logboek/verslag"),
    /**
     * Definitie.
     */
    XSD("XSD"),
    /**
     * XML.
     */
    XML("XML"),
    /**
     * Inhoud.
     */
    TUP("Inhoud"),
    /**
     * Voorbeeld.
     */
    VOR("Voorbeeld"),
    /**
     * Bedrijfs- en Gegevensregels.
     */
    BGR("Bedrijfs- en Gegevensregels");

    private String omschrijving;

    /**
     * Private constructor voor eenmalige instantiatie.
     *
     * @param omschrijving omschrijving.
     */
    private SoortTekst(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
