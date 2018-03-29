/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Soort actie.
 */
public enum SoortActie implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '")'
    FROM   kern.srtactie
    ORDER BY id
    */        

    private static final EnumParser<SoortActie> PARSER = new EnumParser<>(SoortActie.class);

    private final int id;
    private final String naam;

    /**
     * Maak een nieuwe soort actie.
     *
     * @param ident id van SoortActie
     * @param omschrijving omschrijving van SoortActie
     */
    SoortActie(final int ident, final String omschrijving) {
        id = ident;
        naam = omschrijving;
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
     * @param id id van SoortActie
     * @return de SoortActie die bij het id hoort.
     */
    public static SoortActie parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam van SoortActie.
     *
     * @return de waarde van naam van SoortActie
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
        throw new UnsupportedOperationException("De enumeratie SoortActie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
