/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Categorisatie van Indicaties.
.
 * @version 1.0.18.
 */
public enum SoortIndicatie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
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

    /** Omschrijving van het soort indicator. */
    private final String naam;
    /** De indicatie die aangeeft of er voor indicaties van een bepaalde Soort wel of niet sprake kan zijn van het vastleggen van Materiële historie. */
    private final String indicatieMaterieleHistorieVanToepassing;

    /**
     * Constructor.
     *
     * @param naam Omschrijving van het soort indicator.
     * @param indicatieMaterieleHistorieVanToepassing De indicatie die aangeeft of er voor indicaties van een bepaalde Soort wel of niet sprake kan zijn van het vastleggen van Materiële historie.
     *
     */
    private SoortIndicatie(final String naam, final String indicatieMaterieleHistorieVanToepassing) {
        this.naam = naam;
        this.indicatieMaterieleHistorieVanToepassing = indicatieMaterieleHistorieVanToepassing;
    }

    /**
     * @return Omschrijving van het soort indicator.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De indicatie die aangeeft of er voor indicaties van een bepaalde Soort wel of niet sprake kan zijn van het vastleggen van Materiële historie.
     */
    public String getIndicatieMaterieleHistorieVanToepassing() {
        return indicatieMaterieleHistorieVanToepassing;
    }

}
