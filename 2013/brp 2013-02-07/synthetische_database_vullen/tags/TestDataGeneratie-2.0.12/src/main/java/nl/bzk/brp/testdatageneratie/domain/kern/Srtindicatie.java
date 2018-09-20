/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.kern;

/**
 * Het soort indicatie.
 */
public enum Srtindicatie {

    /**
     * Placeholder met ordinal 0 voor de niet-gebruikte id=0 in de database.
     */
    ORDINAL_NUL_NIET_GEBRUIKEN("Niet gebruiken"),
    /**
     * Indicator die aangeeft dat een derde het gezag over de minderjarige persoon heeft.
     */
    DERDE_HEEFT_GEZAG("Derde heeft gezag?"),
    /**
     * Indicator die aangeeft dat de persoon onder curatele staat.
     */
    ONDER_CURATELE("Onder curatele?"),
    /**
     * Indicator die aangeeft dat de persoon heeft gekozen voor beperkte verstrekking van zijn/haar gegevens aan derden.
     */
    VERSTREKKINGSBEPERKING("Verstrekkingsbeperking?"),
    /**
     * Indicator die aangeeft dat de betrokken persoon een geprivilegieerde status heeft.
     */
    GEPRIVILEGIEERDE("Geprivilegieerde?"),
    /**
     * Indicator die aangeeft dat vastgesteld is dat de persoon geen Nederlander is.
     */
    VASTGESTELD_NIET_NEDERLANDER("Vastgesteld niet Nederlander?"),
    /**
     * Indicator die aangeeft dat de persoon wordt behandeld als Nederlander.
     */
    BEHANDELD_ALS_NEDERLANDER("Behandeld als Nederlander?"),
    /**
     * Indicator die aangeeft dat aan de persoon geen reisdocument verstrekt mag worden.
     */
    BELEMMERING_VERSTREKKING_REISDOCUMENT("Belemmering verstrekking reisdocument?"),
    /**
     * Indicator die aangeeft dat de ingeschrevene beschikt over één of meer buitenlandse reisdocumenten of is
     * bijgeschreven in een buitenlands reisdocument.
     */
    BEZIT_BUITENLANDS_REISDOCUMENT("Bezit buitenlands reisdocument?"),
    STATENLOOS("Statenloos?"),

    ;

    private String naam;

    /**
     * Constructor voor de initialisatie van de enumeratie.
     *
     * @param naam naam.
     */
    private Srtindicatie(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }
}
