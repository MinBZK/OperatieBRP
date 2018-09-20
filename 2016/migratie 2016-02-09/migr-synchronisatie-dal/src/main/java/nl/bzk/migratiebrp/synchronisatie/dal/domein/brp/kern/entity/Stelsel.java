/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Deze enumeratie geeft de waardes van de Stelsel statische stamtabel weer.
 */
public enum Stelsel implements Enumeratie {

    /** BRP. */
    BRP((short) 1, "BRP"),
    /** GBA. */
    GBA((short) 2, "GBA");

    private static final EnumParser<Stelsel> PARSER = new EnumParser<>(Stelsel.class);
    private final short id;
    private final String naam;

    /**
     * Maak een nieuw Stelsel.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    Stelsel(final short id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een stelsel op basis van id.
     *
     * @param id
     *            id
     * @return stelsel
     */
    public static Stelsel parseId(final Short id) {
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

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Stelsel heeft geen code");
    }

    @Override
    public boolean heeftCode() {
        return false;
    }
}
