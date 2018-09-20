/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;


/*
 * CHECKSTYLE:OFF Database-enum.
 */
/**
 *
 */
public enum SoortIndicatie implements Enumeratie {
    DERDE_HEEFT_GEZAG(1, "Derde heeft gezag?", true), ONDER_CURATELE(2, "Onder curatele?", true),
    VERSTREKKINGSBEPERKING(3, "Verstrekkingsbeperking?", false), GEPRIVILEGIEERDE(4, "Geprivilegieerde?", false),
    VASTGESTELD_NIET_NEDERLANDER(5, "Vastgesteld niet Nederlander?", true), BEHANDELD_ALS_NEDERLANDER(6,
            "Behandeld als Nederlander?", true), BELEMMERING_VERSTREKKING_REISDOCUMENT(7,
            "Belemmering verstrekking reisdocument?", false), BEZIT_BUITENLANDS_REISDOCUMENT(8,
            "Bezit buitenlands reisdocument?", true), STATENLOOS(9, "Statenloos?", true);

    /**
     * 
     */
    private static final EnumParser<SoortIndicatie> PARSER = new EnumParser<SoortIndicatie>(SoortIndicatie.class);

    /**
     * 
     */
    private final int id;
    /**
     * 
     */
    private final String omschrijving;
    /**
     * 
     */
    private final boolean materieleHistorieVanToepassing;

    /**
     * @param id
     * @param omschrijving
     * @param materieleHistorieVanToepassing
     */
    private SoortIndicatie(final int id, final String omschrijving, final boolean materieleHistorieVanToepassing) {
        this.id = id;
        this.omschrijving = omschrijving;
        this.materieleHistorieVanToepassing = materieleHistorieVanToepassing;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id
     * @return
     */
    public static SoortIndicatie parseId(final Integer id) {
        return PARSER.parseId(id);
    }

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

    public String getOmschrijving() {
        return omschrijving;
    }

    public boolean isMaterieleHistorieVanToepassing() {
        return materieleHistorieVanToepassing;
    }
}
