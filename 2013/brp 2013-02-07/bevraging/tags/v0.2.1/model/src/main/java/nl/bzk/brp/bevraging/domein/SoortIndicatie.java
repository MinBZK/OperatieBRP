/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

/**
 * Het soort indicatie.
 */
public enum SoortIndicatie {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null),
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
    /**
     * Indicatie dat de betrokkene uitgesloten is van het actieve kiesrecht voor de verkiezingen zoals bedoeld in de
     * afdelingen II, III en IV van de Kieswet.
     */
    UITSLUITING_NL_KIESRECHT("Uitsluiting NL kiesrecht?"),
    /**
     * Indicator die aangeeft of de persoon deel kan nemen aan de verkiezing van de leden van het Europees parlement,
     * zoals beschreven in afdeling V van de Kieswet.
     */
    DEELNAME_EU_VERKIEZINGEN("Deelname EU verkiezingen?");

    private String naam;

    /**
     * Constructor voor de initialisatie van de enumeratie.
     *
     * @param naam naam.
     */
    private SoortIndicatie(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }
}
