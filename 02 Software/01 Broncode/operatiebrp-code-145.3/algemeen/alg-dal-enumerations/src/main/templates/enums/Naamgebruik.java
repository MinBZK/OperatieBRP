/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 *
 */
public enum Naamgebruik implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           case when code = 'V' then 'Partner voor eigen'
                when code = 'N' then 'Partner na eigen'
                else naam
                end,
           '('
           || id
           || ', "'
           || code
           || '", "'
           || naam
           || '", "'
           || oms
           || '")'
    FROM   kern.naamgebruik
    ORDER BY id
    */        

    /**
     * 
     */
    private static final EnumParser<Naamgebruik> PARSER = new EnumParser<>(Naamgebruik.class);

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
     *
     */
    private String omschrijving;

    /**
     * Maak een nieuwe naamgebruik.
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
    Naamgebruik(final int id, final String code, final String naam, final String omschrijving) {
        this.id = id;
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
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
     * @return naamgebruik
     */
    public static Naamgebruik parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return naamgebruik
     */
    public static Naamgebruik parseCode(final String code) {
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

    /* (non-Javadoc)
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
     * Geef de waarde van naam van Naamgebruik.
     *
     * @return de waarde van naam van Naamgebruik
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving van Naamgebruik.
     *
     * @return de waarde van omschrijving van Naamgebruik
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
