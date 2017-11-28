/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Adellijke titel.
 */
public enum AdellijkeTitel implements Enumeratie {
    
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
    FROM   kern.adellijketitel
    ORDER BY id
    */    

    /**
     *
     */
    private static final EnumParser<AdellijkeTitel> PARSER = new EnumParser<>(AdellijkeTitel.class);

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
     * Enum voor verwijzingen naar de BRP-tabel AdellijkeTitel_VervangenDoorEnum.
     *
     * @param id
     *            Het ID van de titel.
     * @param code
     *            De logische code.
     * @param naamMannelijk
     *            De mannelijke geslachtspecifieke naam.
     * @param naamVrouwelijk
     *            De vrouwelijke geslachtspecifieke naam.
     */
    AdellijkeTitel(final int id, final String code, final String naamMannelijk, final String naamVrouwelijk) {
        this.id = id;
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return adellijke titel
     */
    public static AdellijkeTitel parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return adellijke titel
     */
    public static AdellijkeTitel parseCode(final String code) {
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
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
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
     * Geef de waarde van naam mannelijk van AdellijkeTitel.
     *
     * @return de waarde van naam mannelijk van AdellijkeTitel
     */
    public String getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Geef de waarde van naam vrouwelijk van AdellijkeTitel.
     *
     * @return de waarde van naam vrouwelijk van AdellijkeTitel
     */
    public String getNaamVrouwelijk() {
        return naamVrouwelijk;
    }
}
