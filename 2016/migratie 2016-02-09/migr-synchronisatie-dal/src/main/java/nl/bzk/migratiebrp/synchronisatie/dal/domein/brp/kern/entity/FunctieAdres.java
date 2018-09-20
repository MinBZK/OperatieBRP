/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Functie adres.
 */
public enum FunctieAdres implements Enumeratie {
    /** Woonadres. */
    WOONADRES((short) 1, "W", "Woonadres"),
    /** Briefadres. */
    BRIEFADRES((short) 2, "B", "Briefadres");

    /**
     *
     */
    private static final EnumParser<FunctieAdres> PARSER = new EnumParser<>(FunctieAdres.class);

    /**
     *
     */
    private final short id;

    /**
     *
     */
    private final String code;

    /**
     *
     */
    private final String naam;

    /**
     * Maak een nieuwe functie adres.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    FunctieAdres(final short id, final String code, final String naam) {
        this.id = id;
        this.code = code;
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
     * @return functie adres
     */
    public static FunctieAdres parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return functie adres
     */
    public static FunctieAdres parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }
}
