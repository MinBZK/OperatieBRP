/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Geslachtsaanduiding.
 */
public enum Geslachtsaanduiding implements Enumeratie {
    /** Man. */
    MAN((short) 1, "M", "Man"),
    /** Vrouw. */
    VROUW((short) 2, "V", "Vrouw"),
    /** Onbekend. */
    ONBEKEND((short) 3, "O", "Onbekend");

    private static final EnumParser<Geslachtsaanduiding> PARSER = new EnumParser<>(Geslachtsaanduiding.class);

    /**
     *
     */
    private final short id;

    /**
     *
     */
    private final String code;

    /**
     *
     */
    private String naam;

    /**
     * Maak een nieuwe geslachtsaanduiding.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    Geslachtsaanduiding(final short id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
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

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return geslachtsaanduiding
     */
    public static Geslachtsaanduiding parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return geslachtsaanduiding
     */
    public static Geslachtsaanduiding parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return true;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }
}
