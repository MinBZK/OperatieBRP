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
public enum SoortRelatie implements Enumeratie {
    HUWELIJK(1, "H", "Huwelijk"), GEREGISTREERD_PARTNERSCHAP(2, "G", "Geregistreerd partnerschap"),
    FAMILIERECHTELIJKE_BETREKKING(3, "F", "Familierechtelijke betrekking");

    /**
     * 
     */
    private static final EnumParser<SoortRelatie> PARSER = new EnumParser<SoortRelatie>(SoortRelatie.class);

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
    private SoortRelatie(final int id, final String code, final String naam) {
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
    public static SoortRelatie parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static SoortRelatie parseCode(final String code) {
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
