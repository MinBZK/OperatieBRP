/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * De burgerzaken module.
 */
public enum BurgerzakenModule implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '", "'
           || oms
           || '")'
    FROM   kern.burgerzakenmodule
    ORDER BY id
    */            

    /**
     *
     */
    private static final EnumParser<BurgerzakenModule> PARSER = new EnumParser<>(BurgerzakenModule.class);

    /**
     *
     */
    private final int id;
    /**
     *
     */
    private final String naam;
    /**
     *
     */
    private final String omschrijving;

    /**
     * Maak een nieuwe burgerzaken module.
     *
     * @param id id
     * @param naam naam
     * @param omschrijving omschrijving
     */
    BurgerzakenModule(final int id, final String naam, final String omschrijving) {
        this.id = id;
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
     * @param id id
     * @return burgerzaken module
     */
    public static BurgerzakenModule parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam van BurgerzakenModule.
     *
     * @return de waarde van naam van BurgerzakenModule
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving van BurgerzakenModule.
     *
     * @return de waarde van omschrijving van BurgerzakenModule
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie BurgerzakenModule heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
