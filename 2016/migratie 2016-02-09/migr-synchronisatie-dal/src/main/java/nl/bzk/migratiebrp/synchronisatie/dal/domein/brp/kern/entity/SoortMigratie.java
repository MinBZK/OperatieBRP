/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Database-enum voor soort migratie.
 */
public enum SoortMigratie implements Enumeratie {
    /** Immigratie. */
    IMMIGRATIE((short) 1, "I", "Immigratie"),
    /** Emigratie. */
    EMIGRATIE((short) 2, "E", "Emigratie");

    private static final EnumParser<SoortMigratie> PARSER = new EnumParser<>(SoortMigratie.class);

    private final short id;
    private final String code;
    private final String naam;

    /**
     * Maak een nieuwe soort migratie.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    SoortMigratie(final short id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
    }

    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort migratie
     */
    public static SoortMigratie parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return soort migratie
     */
    public static SoortMigratie parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    @Override
    public String getCode() {
        return code;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    @Override
    public boolean heeftCode() {
        return true;
    }
}
