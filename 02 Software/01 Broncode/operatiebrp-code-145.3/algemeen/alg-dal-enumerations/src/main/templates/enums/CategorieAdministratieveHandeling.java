/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Categorie administratieve handeling.
 */
public enum CategorieAdministratieveHandeling implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '")'
    FROM   kern.categorieadmhnd
    ORDER BY id
    */            

    /**
     *
     */
    private static final EnumParser<CategorieAdministratieveHandeling> PARSER = new EnumParser<>(CategorieAdministratieveHandeling.class);
    /**
     *
     */
    private final int id;
    /**
     *
     */
    private final String naam;

    /**
     * Maak een nieuwe categorie administratieve handeling.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    CategorieAdministratieveHandeling(final int id, final String naam) {
        this.id = id;
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
     * @param id
     *            id
     * @return categorie administratieve handeling
     */
    public static CategorieAdministratieveHandeling parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam van CategorieAdministratieveHandeling.
     *
     * @return de waarde van naam van CategorieAdministratieveHandeling
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
        throw new UnsupportedOperationException("De enumeratie CategorieAdministratieveHandeling heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
