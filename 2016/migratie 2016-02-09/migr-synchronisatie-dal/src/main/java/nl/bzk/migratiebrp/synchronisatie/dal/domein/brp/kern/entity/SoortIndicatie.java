/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
public enum SoortIndicatie implements Enumeratie {

    /** Derde heeft gezag? */
    DERDE_HEEFT_GEZAG((short) 1, "Derde heeft gezag?", true),
    /** Onder curatele? */
    ONDER_CURATELE((short) 2, "Onder curatele?", true),
    /** Volledige verstrekkingsbeperking? */
    VOLLEDIGE_VERSTREKKINGSBEPERKING((short) 3, "Volledige verstrekkingsbeperking?", false),
    /** Vastgesteld niet Nederlander? */
    VASTGESTELD_NIET_NEDERLANDER((short) 4, "Vastgesteld niet Nederlander?", true),
    /** Behandeld als Nederlander? */
    BEHANDELD_ALS_NEDERLANDER((short) 5, "Behandeld als Nederlander?", true),
    /** Signalering met betrekking tot verstrekken reisdocument? */
    SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT((short) 6, "Signalering met betrekking tot verstrekken reisdocument?", false),
    /** Staatloos? (was Statenloos?) */
    STAATLOOS((short) 7, "Staatloos?", true),
    /** Bijzondere verblijfsrechtelijke positie? */
    BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE((short) 8, "Bijzondere verblijfsrechtelijke positie?", false);

    /**
     *
     */
    private static final EnumParser<SoortIndicatie> PARSER = new EnumParser<>(SoortIndicatie.class);

    /**
     * 
     */
    private final short id;
    /**
     * 
     */
    private final String omschrijving;
    /**
     * 
     */
    private final boolean materieleHistorieVanToepassing;

    /**
     * Maak een nieuwe soort indicatie.
     *
     * @param id
     *            id
     * @param omschrijving
     *            omschrijving
     * @param materieleHistorieVanToepassing
     *            materiele historie van toepassing
     */
    SoortIndicatie(final short id, final String omschrijving, final boolean materieleHistorieVanToepassing) {
        this.id = id;
        this.omschrijving = omschrijving;
        this.materieleHistorieVanToepassing = materieleHistorieVanToepassing;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort indicatie
     */
    public static SoortIndicatie parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortIndicatie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de materiele historie van toepassing.
     *
     * @return materiele historie van toepassing
     */
    public boolean isMaterieleHistorieVanToepassing() {
        return materieleHistorieVanToepassing;
    }
}
