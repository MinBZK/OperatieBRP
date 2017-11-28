/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Geslachtsaanduiding.
 */
public enum Geslachtsaanduiding implements Enumeratie {
    
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
    FROM   kern.geslachtsaand
    ORDER BY id
    */

    private static final EnumParser<Geslachtsaanduiding> PARSER = new EnumParser<>(Geslachtsaanduiding.class);

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
    private String naam;

    /**
     * Maak een nieuwe geslachtsaanduiding.
     *
     * @param id id
     * @param code code
     * @param naam naam
     */
    Geslachtsaanduiding(final int id, final String code, final String naam) {
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
     * @param id id
     * @return geslachtsaanduiding
     */
    public static Geslachtsaanduiding parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code code
     * @return geslachtsaanduiding
     */
    public static Geslachtsaanduiding parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /**
     * Bepaal een voorkomen bestaat voor de gegeven code.
     *
     * @param code
     *            code
     * @return true als het voorkomen bestaat anders false
     */
    public static boolean bestaatCode(final String code) {
        return PARSER.heeftCode(code);
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
     * Geef de waarde van naam van Geslachtsaanduiding.
     *
     * @return de waarde van naam van Geslachtsaanduiding
     */
    public String getNaam() {
        return naam;
    }
}
