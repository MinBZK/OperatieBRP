/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Deze enumeratie geeft de waardes van de Koppelvlak statische stamtabel weer.
 */
public enum Koppelvlak implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '", '
           || stelsel
           || ')'
    FROM   kern.koppelvlak
    ORDER BY id
    */    

    private static final EnumParser<Koppelvlak> PARSER = new EnumParser<>(Koppelvlak.class);
    private final int id;
    private final String naam;
    private final Stelsel stelsel;

    /**
     * Maak een nieuw Koppelvlak.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param stelsel
     *            stelsel
     */
    Koppelvlak(final int id, final String naam, final int stelsel) {
        this.id = id;
        this.naam = naam;
        this.stelsel = Stelsel.parseId(stelsel);
    }

    /* (non-Javadoc)
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Bepaal een Koppelvlak op basis van id.
     *
     * @param id
     *            id
     * @return stelsel
     */
    public static Koppelvlak parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam van Koppelvlak.
     *
     * @return de waarde van naam van Koppelvlak
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van stelsel van Koppelvlak.
     *
     * @return de waarde van stelsel van Koppelvlak
     */
    public Stelsel getStelsel() {
        return stelsel;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Koppelvlak heeft geen code");
    }

    @Override
    public boolean heeftCode() {
        return false;
    }
}
