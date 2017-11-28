/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * De class voor de SoortElement database tabel.
 *
 */
public enum SoortElement implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '")'
    FROM   kern.srtelement
    ORDER BY id
    */        

    private static final EnumParser<SoortElement> PARSER = new EnumParser<>(SoortElement.class);

    private final int id;

    private final String naam;

    /**
     * Maak een nieuwe soort element.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    SoortElement(final int id, final String naam) {
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
     * Geef de waarde van naam van SoortElement.
     *
     * @return de waarde van naam van SoortElement
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort element
     */
    public static SoortElement parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortElement heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
