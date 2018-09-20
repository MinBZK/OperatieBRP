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
public enum RedenOpschorting implements Enumeratie {
    OVERLIJDEN(1, "O", "Overlijden"), MINISTERIEEL_BESLUIT(2, "M", "Ministerieel besluit"), FOUT(3, "F", "Fout"),
    ONBEKEND(4, "?", "Onbekend");

    /**
     * 
     */
    private static final EnumParser<RedenOpschorting> PARSER = new EnumParser<RedenOpschorting>(
            RedenOpschorting.class);

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
    private RedenOpschorting(final int id, final String code, final String naam) {
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
    public static RedenOpschorting parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static RedenOpschorting parseCode(final String code) {
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
