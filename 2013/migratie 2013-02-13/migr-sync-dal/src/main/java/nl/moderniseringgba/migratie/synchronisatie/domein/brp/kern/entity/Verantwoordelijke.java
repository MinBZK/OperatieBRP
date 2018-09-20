/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;


/*
 * CHECKSTYLE:OFF Database-enum.
 */
/**
 *
 */
public enum Verantwoordelijke implements Enumeratie {
    C(1, "C", "College van burgemeester en wethouders"), M(2, "M", "Minister");

    /**
     * 
     */
    private static final EnumParser<Verantwoordelijke> PARSER = new EnumParser<Verantwoordelijke>(
            Verantwoordelijke.class);

    /**
     * 
     */
    private int id;
    /**
     * 
     */
    private String code;
    /**
     * 
     */
    private String naam;

    /**
     * Enum voor verwijzingen naar de BRP-tabel Verantwoordelijke.
     * 
     * @param id
     *            Het ID van de verantwoordelijke partij.
     * @param code
     *            De logische code.
     * @param naam
     *            De naam voor de bijhouding verantwoordelijke Partij.
     */
    Verantwoordelijke(final int id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
    }

    /**
     * @param id
     * @return
     */
    public static Verantwoordelijke parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static Verantwoordelijke parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    @Override
    public int getId() {
        return id;
    }

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

    public String getNaam() {
        return naam;
    }
}
