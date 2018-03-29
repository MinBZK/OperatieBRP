/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 *
 */
public enum Lo3Severity implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '")'
    FROM   verconv.lo3severity
    ORDER BY id
    */        

    /**
     * 
     */
    private static final EnumParser<Lo3Severity> PARSER = new EnumParser<>(Lo3Severity.class);
    /**
     * 
     */
    private final int id;
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
    Lo3Severity(final int id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /* (non-Javadoc)
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return lo3 severity
     */
    public static Lo3Severity parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /* (non-Javadoc)
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Lo3Severity heeft geen code");
    }

    /**
     * Geef de waarde van naam van Lo3Severity.
     *
     * @return de waarde van naam van Lo3Severity
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
