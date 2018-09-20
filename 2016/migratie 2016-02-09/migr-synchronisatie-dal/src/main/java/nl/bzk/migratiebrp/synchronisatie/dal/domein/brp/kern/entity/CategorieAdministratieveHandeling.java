/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
public enum CategorieAdministratieveHandeling implements Enumeratie {

    /** Actualisering. */
    ACTUALISERING((short) 1, "Actualisering"),
    /** Correctie. */
    CORRECTIE((short) 2, "Correctie"),
    /** Synchronisatie. */
    SYNCHRONISATIE((short) 3, "Synchronisatie"),
    /** GBA - Initiele vulling. */
    GBA_INITIELE_VULLING((short) 4, "GBA - Initiele vulling"),
    /** GBA - Synchronisatie. */
    GBA_SYNCHRONISATIE((short) 5, "GBA - Synchronisatie");

    /**
     *
     */
    private static final EnumParser<CategorieAdministratieveHandeling> PARSER = new EnumParser<>(CategorieAdministratieveHandeling.class);
    /**
     *
     */
    private final short id;
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
    CategorieAdministratieveHandeling(final short id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return categorie administratieve handeling
     */
    public static CategorieAdministratieveHandeling parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
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
