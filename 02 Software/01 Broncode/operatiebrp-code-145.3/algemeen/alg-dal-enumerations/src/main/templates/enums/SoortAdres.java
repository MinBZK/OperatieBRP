/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Soort adres.
 */
public enum SoortAdres implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || code
    || '", "'
    || naam
    || '")'
    FROM   kern.srtadres
    ORDER BY id
    */        

    /**
     *
     */
    private static final EnumParser<SoortAdres> PARSER = new EnumParser<>(SoortAdres.class);

    /**
     *
     */
    private final int id;

    /**
     *
     */
    private final String code;

    /**
     *
     */
    private final String naam;

    /**
     * Maak een nieuwe soort adres.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    SoortAdres(final int id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
    }

    /*
     * (non-Javadoc)
     * 
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
     * @return soort adres
     */
    public static SoortAdres parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return soort adres
     */
    public static SoortAdres parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
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
     * Geef de waarde van naam van SoortAdres.
     *
     * @return de waarde van naam van SoortAdres
     */
    public String getNaam() {
        return naam;
    }
}
