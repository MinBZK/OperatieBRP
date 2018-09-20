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
public enum Rol implements Enumeratie {
    AFNEMER(1, "Afnemer"), BEVOEGDHEIDSTOEDELER(2, "Bevoegdheidstoedeler"), BIJHOUDINGSORGAAN_COLLEGE(3,
            "Bijhoudingsorgaan College"), BIJHOUDINGSORGAAN_MINISTER(4, "Bijhoudingsorgaan Minister"),
    STELSELBEHEERDER(5, "Stelselbeheerder"), TOEZICHTHOUDER(6, "Toezichthouder");

    /**
     * 
     */
    private static final EnumParser<Rol> PARSER = new EnumParser<Rol>(Rol.class);

    /**
     * 
     */
    private final int id;
    /**
     * 
     */
    private final String naam;

    /**
     * @param id
     * @param naam
     */
    private Rol(final int id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id
     * @return
     */
    public static Rol parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Rol heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    public String getNaam() {
        return naam;
    }
}
