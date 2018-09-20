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
public enum SoortBetrokkenheid implements Enumeratie {
    PARTNER(1, "P", "partner"), OUDER(2, "O", "Ouder"), KIND(3, "K", "Kind");

    /**
     * 
     */
    private static final EnumParser<SoortBetrokkenheid> PARSER = new EnumParser<SoortBetrokkenheid>(
            SoortBetrokkenheid.class);

    /**
     * 
     */
    private final int id;
    /**
     * 
     */
    private final String code;
    /**
     * 
     */
    private final String naam;

    /**
     * @param id
     * @param code
     * @param naam
     */
    private SoortBetrokkenheid(final int id, final String code, final String naam) {
        this.id = id;
        this.code = code;
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
    public static SoortBetrokkenheid parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static SoortBetrokkenheid parseCode(final String code) {
        return PARSER.parseCode(code);
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
