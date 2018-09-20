/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;


/**
 * Het _SoortPersoon van een BRP-persoon.
 */
/*
 * CHECKSTYLE:OFF Database-enum.
 */
public enum SoortPersoon implements Enumeratie {
    /**
     * Niet ingeschreven persoon.
     */
    NIET_INGESCHREVENE(2, "N", "Niet ingeschrevene", ""),

    /**
     * Ingeschreven persoon.
     */
    INGESCHREVENE(1, "I", "Ingeschrevene", ""),

    /**
     * Persoon-voorkomen t.b.v. een alternatieve realiteit.
     */
    ALTERNATIEVE_REALITEIT(3, "A", "Alternatieve realiteit", "");

    /**
     * 
     */
    private static final EnumParser<SoortPersoon> PARSER = new EnumParser<SoortPersoon>(SoortPersoon.class);

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
     * 
     */
    private String omschrijving;

    /**
     * @param id
     * @param code
     * @param naam
     * @param omschrijving
     */
    private SoortPersoon(final int id, final String code, final String naam, final String omschrijving) {
        this.id = (short) id;
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @param id
     * @return
     */
    public static SoortPersoon parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static SoortPersoon parseCode(final String code) {
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

    public String getOmschrijving() {
        return omschrijving;
    }
}
