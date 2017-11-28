/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 *
 */
public enum NadereBijhoudingsaard implements Enumeratie {

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
           || '", "'
           || oms
           || '")'
    FROM   kern.naderebijhaard
    ORDER BY id
    */        

    /**
     *
     */
    private static final EnumParser<NadereBijhoudingsaard> PARSER = new EnumParser<>(NadereBijhoudingsaard.class);
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
     *
     */
    private final String omschrijving;

    /**
     * Maak een nieuwe nadere bijhoudingsaard.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     */
    NadereBijhoudingsaard(final int id, final String code, final String naam, final String omschrijving) {
        this.id = id;
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
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
     * @return nadere bijhoudingsaard
     */
    public static NadereBijhoudingsaard parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return nadere bijhoudingsaard
     */
    public static NadereBijhoudingsaard parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /**
     * Geef de waarde van naam van NadereBijhoudingsaard.
     *
     * @return de waarde van naam van NadereBijhoudingsaard
     */
    public String getNaam() {
        return naam;
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
     * Geeft de waarde van de omschrijving van de NadereBijhoudingsaard.
     *
     * @return de waarde van de omschrijving van de NadereBijhoudingsaard
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
