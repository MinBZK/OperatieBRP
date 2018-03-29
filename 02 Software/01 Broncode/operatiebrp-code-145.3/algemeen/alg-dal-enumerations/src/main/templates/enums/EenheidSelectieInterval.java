/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie van EenheidSelectieInterval.
 */
public enum EenheidSelectieInterval implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '")'
    FROM   autaut.EenheidSelinterval
    ORDER BY id
    */  

    private static final EnumParser<EenheidSelectieInterval> PARSER = new EnumParser<>(EenheidSelectieInterval.class);

    private final int id;
    private final String naam;

    /**
     * Maak een nieuw EenheidSelectieInterval object.
     *
     * @param id
     *            id
     * @param naam
     *            code
     */
    EenheidSelectieInterval(final int id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getId()
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
     * @return soort relatie
     */
    public static EenheidSelectieInterval parseId(final Integer id) {
        return PARSER.parseId(id);
    }
    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie EenheidSelectieInterval heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam van EenheidSelectieInterval.
     *
     * @return de waarde van naam van EenheidSelectieInterval
     */
    public String getNaam() {
        return naam;
    }
}
