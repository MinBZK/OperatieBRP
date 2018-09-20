/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van Indicaties.
.
 */
public enum SoortIndicatie {

    /** DUMMY. */
    DUMMY("", ""),
    /** Derde heeft gezag?. */
    DERDE_HEEFT_GEZAG("Derde heeft gezag?", "J"),
    /** Onder curatele?. */
    ONDER_CURATELE("Onder curatele?", "J"),
    /** Verstrekkingsbeperking?. */
    VERSTREKKINGSBEPERKING("Verstrekkingsbeperking?", "N"),
    /** Geprivilegieerde?. */
    GEPRIVILEGIEERDE("Geprivilegieerde?", "N"),
    /** Vastgesteld niet Nederlander?. */
    VASTGESTELD_NIET_NEDERLANDER("Vastgesteld niet Nederlander?", "J"),
    /** Behandeld als Nederlander?. */
    BEHANDELD_ALS_NEDERLANDER("Behandeld als Nederlander?", "J"),
    /** Belemmering verstrekking reisdocument?. */
    BELEMMERING_VERSTREKKING_REISDOCUMENT("Belemmering verstrekking reisdocument?", "N"),
    /** Bezit buitenlands reisdocument?. */
    BEZIT_BUITENLANDS_REISDOCUMENT("Bezit buitenlands reisdocument?", "J"),
    /** Statenloos?. */
    STATENLOOS("Statenloos?", "J");

    /** naam. */
    private String naam;
    /** indicatieMaterieleHistorieVanToepassing. */
    private String indicatieMaterieleHistorieVanToepassing;

    /**
     * Constructor.
     *
     * @param naam de naam
     * @param indicatieMaterieleHistorieVanToepassing de indicatieMaterieleHistorieVanToepassing
     *
     */
    private SoortIndicatie(final String naam, final String indicatieMaterieleHistorieVanToepassing) {
        this.naam = naam;
        this.indicatieMaterieleHistorieVanToepassing = indicatieMaterieleHistorieVanToepassing;
    }

    /**
     * Omschrijving van het soort indicator..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De indicatie die aangeeft of er voor indicaties van een bepaalde Soort wel of niet sprake kan zijn van het vastleggen van Materiële historie..
     * @return String
     */
    public String getIndicatieMaterieleHistorieVanToepassing() {
        return indicatieMaterieleHistorieVanToepassing;
    }

}
