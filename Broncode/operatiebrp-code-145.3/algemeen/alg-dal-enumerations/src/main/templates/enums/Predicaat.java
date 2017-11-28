/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Predicaat.
 */
public enum Predicaat implements Enumeratie {
    
    /** QUERY:
    SELECT naammannelijk 
           || '/'
           || naamvrouwelijk
           || '. ',
           code,
           '('
           || id
           || ', "'
           || code
           || '", "'
           || naammannelijk
           || '", "'
           || naamvrouwelijk
           || '")'
    FROM   kern.predicaat
    ORDER BY id
    */        

    /**
     *
     */
    private static final EnumParser<Predicaat> PARSER = new EnumParser<>(Predicaat.class);

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
    private final String naamMannelijk;
    /**
     *
     */
    private final String naamVrouwelijk;

    /**
     * Maak een nieuwe predicaat.
     *
     * @param id
     *            Het database-ID.
     * @param code
     *            code
     * @param naamMannelijk
     *            naam mannelijk
     * @param naamVrouwelijk
     *            naam vrouwelijk
     */
    Predicaat(final int id, final String code, final String naamMannelijk, final String naamVrouwelijk) {
        this.id = id;
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;
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
     * @return predicaat
     */
    public static Predicaat parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return predicaat
     */
    public static Predicaat parseCode(final String code) {
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

    /*
     * (non-Javadoc)
     *
     * @see Enumeratie#getNaam()
     */
    @Override
    public String getNaam() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
    }

    /**
     * Geef de waarde van naam mannelijk van Predicaat.
     *
     * @return de waarde van naam mannelijk van Predicaat
     */
    public String getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Geef de waarde van naam vrouwelijk van Predicaat.
     *
     * @return de waarde van naam vrouwelijk van Predicaat
     */
    public String getNaamVrouwelijk() {
        return naamVrouwelijk;
    }
}
