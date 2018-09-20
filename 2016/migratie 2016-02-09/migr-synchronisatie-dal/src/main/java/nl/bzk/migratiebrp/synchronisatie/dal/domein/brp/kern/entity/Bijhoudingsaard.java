/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;
/**
 *
 */
public enum Bijhoudingsaard implements Enumeratie {

    /** Ingezetene. */
    INGEZETENE((short) 1, "I", "Ingezetene"),
    /** Niet ingezetene. */
    NIET_INGEZETENE((short) 2, "N", "Niet-ingezetene"),
    /** Onbekend. */
    ONBEKEND((short) 3, "?", "Onbekend");

    /**
     *
     */
    private static final EnumParser<Bijhoudingsaard> PARSER = new EnumParser<>(Bijhoudingsaard.class);
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
    private final String naam;

    /**
     * Maak een nieuwe bijhoudingsaard.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    Bijhoudingsaard(final short id, final String code, final String naam) {
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
     * @return bijhoudingsaard
     */
    public static Bijhoudingsaard parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return bijhoudingsaard
     */
    public static Bijhoudingsaard parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
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
}
