/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
public enum Lo3Severity implements Enumeratie {

    /** Info. */
    INFO((short) 1, "Info"),
    /** Warning. */
    WARNING((short) 2, "Warning"),
    /** Suppressed. */
    SUPPRESSED((short) 3, "Suppressed"),
    /** Error. */
    ERROR((short) 4, "Error"),
    /** Critical. */
    CRITICAL((short) 5, "Critical");

    /**
     * 
     */
    private static final EnumParser<Lo3Severity> PARSER = new EnumParser<>(Lo3Severity.class);
    /**
     * 
     */
    private final short id;
    /**
     * 
     */
    private final String naam;

    /**
     * Maak een nieuwe lo3 severity.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    Lo3Severity(final short id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /* (non-Javadoc)
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
     * @return lo3 severity
     */
    public static Lo3Severity parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Lo3Severity heeft geen code");
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
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
