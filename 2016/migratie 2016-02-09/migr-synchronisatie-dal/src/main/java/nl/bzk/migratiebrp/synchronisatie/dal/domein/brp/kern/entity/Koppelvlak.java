/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Deze enumeratie geeft de waardes van de Koppelvlak statische stamtabel weer.
 */
public enum Koppelvlak implements Enumeratie {

    /** Bijhouding. */
    BIJHOUDING((short) 1, "Bijhouding", Stelsel.BRP),
    /** Levering. */
    LEVERING((short) 2, "Levering", Stelsel.BRP),
    /** ISC. */
    ISC((short) 3, "ISC", Stelsel.BRP),
    /** GBA. */
    GBA((short) 4, "GBA", Stelsel.GBA);

    private static final EnumParser<Koppelvlak> PARSER = new EnumParser<>(Koppelvlak.class);
    private final short id;
    private final String naam;
    private final Stelsel stelsel;

    /**
     * Maak een nieuw Koppelvlak.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param stelsel
     *            stelsel
     */
    Koppelvlak(final short id, final String naam, final Stelsel stelsel) {
        this.id = id;
        this.naam = naam;
        this.stelsel = stelsel;
    }

    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een Koppelvlak op basis van id.
     *
     * @param id
     *            id
     * @return stelsel
     */
    public static Koppelvlak parseId(final Short id) {
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

    /**
     * Geeft de waarde van stelsel.
     * 
     * @return stelsel
     */
    public Stelsel getStelsel() {
        return stelsel;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Koppelvlak heeft geen code");
    }

    @Override
    public boolean heeftCode() {
        return false;
    }
}
