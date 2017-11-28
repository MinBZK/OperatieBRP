/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Deze enumeratie geeft de waardes van de Stelsel statische stamtabel weer.
 */
public enum Stelsel implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '")'
    FROM  kern.stelsel
    ORDER BY id
    */    

    private static final EnumParser<Stelsel> PARSER = new EnumParser<>(Stelsel.class);
    private final int id;
    private final String naam;

    /**
     * Maak een nieuw Stelsel.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    Stelsel(final int id, final String naam) {
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
     * Bepaal een stelsel op basis van id.
     *
     * @param id
     *            id
     * @return stelsel
     */
    public static Stelsel parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam van Stelsel.
     *
     * @return de waarde van naam van Stelsel
     */
    public String getNaam() {
        return naam;
    }

    /* (non-Javadoc)
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Stelsel heeft geen code");
    }

    @Override
    public boolean heeftCode() {
        return false;
    }
}
