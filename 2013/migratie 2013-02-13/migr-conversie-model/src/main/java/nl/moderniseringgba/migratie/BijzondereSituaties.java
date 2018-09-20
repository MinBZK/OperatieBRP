/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Enumeratie van alle te implementeren BijzondereSituaties.
 * 
 */
public enum BijzondereSituaties {

    /**
     * Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied.
     */
    BIJZ_CONV_LB001("BIJZ_CONV_LB001",
            "Er is een geboorte geregistreerd in een onbekend land of een internationaal gebied."),

    /**
     * Er is een overlijden geregistreerd in een onbekend land of een internationaal gebied.
     */
    BIJZ_CONV_LB002("BIJZ_CONV_LB002",
            "Er is een overlijden geregistreerd in een onbekend land of een internationaal gebied."),
    /**
     * Er is een huwelijk gesloten/geregistreerd partnerschap aangegaan in een onbekend land of een internationaal
     * gebied.
     */
    BIJZ_CONV_LB003("BIJZ_CONV_LB003",
            "Er is een huwelijk gesloten/geregistreerd partnerschap aangegaan in een onbekend land of "
                    + "een internationaal gebied."),

    /**
     * Er is een huwelijk/geregistreerd partnerschap ontbonden in een onbekend land of een internationaal gebied.
     */
    BIJZ_CONV_LB004("BIJZ_CONV_LB004",
            "Er is een huwelijk/geregistreerd partnerschap ontbonden in een onbekend land "
                    + "of een internationaal gebied."),
    /**
     * Het A-nummer is gewijzigd omdat er verschillende personen waren met hetzelfde A-nummer (zie [HUP], par 7.8):.
     */
    BIJZ_CONV_LB005("BIJZ_CONV_LB005",
            "Het A-nummer is gewijzigd omdat er verschillende personen waren met hetzelfde A-nummer  (zie [HUP], "
                    + "par 7.8):"),

    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overblijvende PL.
     */
    BIJZ_CONV_LB006("BIJZ_CONV_LB006",
            "Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven "
                    + "met hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overblijvende PL."),

    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overbodige/vervallen PL.
     */
    BIJZ_CONV_LB007("BIJZ_CONV_LB007",
            "Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met"
                    + " hetzelfde A-nummer (zie [HUP], par 7.9). Het betreft hier de overbodige/vervallen PL."),

    /**
     * Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is ingeschreven met
     * verschillende A-nummers (zie [HUP], par 7.10). Het betreft hier de overbodige/vervallen PL.
     */
    BIJZ_CONV_LB008("BIJZ_CONV_LB008",
            "Er is sprake van een dubbele inschrijving, waarbij één persoon met verschillende PL'en is "
                    + "ingeschreven met verschillende A-nummers (zie [HUP], par 7.10). "
                    + "Het betreft hier de overbodige/vervallen PL."),

    /**
     * Er is sprake van een gedeeltelijke verstrekkingsbeperking.
     */
    BIJZ_CONV_LB009("BIJZ_CONV_LB009", "Er is sprake van een gedeeltelijke verstrekkingsbeperking."),

    /**
     * De persoonslijst is opgeschort met reden Onbekend.
     */
    BIJZ_CONV_LB010("BIJZ_CONV_LB010", "De persoonslijst is opgeschort met reden Onbekend."),

    /**
     * De persoonslijst is opgeschort met reden Fout.
     */
    BIJZ_CONV_LB011("BIJZ_CONV_LB011", "De persoonslijst is opgeschort met reden Fout."),

    /**
     * De persoon is geprivilegieerd.
     */
    BIJZ_CONV_LB012("BIJZ_CONV_LB012", "De persoon is geprivilegieerd."),

    /**
     * Er is sprake van een onbekende ouder.
     */
    BIJZ_CONV_LB013("BIJZ_CONV_LB013", "Er is sprake van een onbekende ouder."),

    /**
     * Er is sprake van juridisch gezien geen ouder.
     */
    BIJZ_CONV_LB014("BIJZ_CONV_LB014", "Er is sprake van juridisch gezien geen ouder."),

    /**
     * In één Huwelijk/geregistreerd partnerschap stapel zijn gegevens geregistreerd over zowel een huwelijk als een
     * geregistreerd partnerschap.
     */
    BIJZ_CONV_LB015("BIJZ_CONV_LB015",
            "In één Huwelijk/geregistreerd partnerschap stapel zijn gegevens geregistreerd over zowel een "
                    + "huwelijk als een geregistreerd partnerschap."),

    /**
     * In Ouder1 dan wel Ouder2 zijn meerdere personen opgenomen.
     */
    BIJZ_CONV_LB016("BIJZ_CONV_LB016", "In Ouder1 dan wel Ouder2 zijn meerdere personen opgenomen."),

    /**
     * De geldigheid van het Ouderlijk gezag loopt nog door terwijl de geldigheid van desbetreffende persoon al
     * beëindigd is.
     */
    BIJZ_CONV_LB017("BIJZ_CONV_LB017",
            "De geldigheid van het Ouderlijk gezag loopt nog door terwijl de geldigheid van desbetreffende "
                    + "persoon al beëindigd is."),
    /**
     * Bij historie conversie met algoritme CHP001-LB23 zijn in stap 5a gegevens genegeerd.
     */
    BIJZ_CONV_LB018("BIJZ_CONV_LB018",
            "Bij conversie naar BRP Groep %s is dit LO3-voorkomen genegeerd, omdat deze informatie al verwerkt is in "
                    + "combinatie met een eerder voorkomen."),
    /**
     * Bij historie conversie met algoritme CHP001-LB23 zijn in stap 5c gegevens genegeerd.
     */
    BIJZ_CONV_LB019("BIJZ_CONV_LB019",
            "Bij conversie naar BRP Groep %s is dit LO3-voorkomen genegeerd. De informatie van de akte/document is "
                    + "als het goed is gebruikt bij de conversie van dit voorkomen naar een andere BRP Groep."),
    /**
     * Bij historie conversie met algoritme CHP001-LB24 zijn in stap 3c gegevens genegeerd.
     */
    BIJZ_CONV_LB020("BIJZ_CONV_LB020",
            "Bij conversie naar BRP Groep %s is dit LO3-voorkomen genegeerd. Dit omdat dit voorkomen geen "
                    + "nieuwe informatie bevat."),
    /**
     * Zwakke adoptie.
     */
    BIJZ_CONV_LB021("BIJZ_CONV_LB021", "Zwakke adoptie."),
    /**
     * Gegevens zijn strijdig met de openbare orde.
     */
    BIJZ_CONV_LB022("BIJZ_CONV_LB022", "Gegevens zijn strijdig met de openbare orde");

    private String code;
    private String omschrijving;

    private BijzondereSituaties(final String code, final String omschrijving) {
        this.omschrijving = omschrijving;
        this.code = code;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("code", code)
                .append("omschrijving", omschrijving).toString();
    }
}
