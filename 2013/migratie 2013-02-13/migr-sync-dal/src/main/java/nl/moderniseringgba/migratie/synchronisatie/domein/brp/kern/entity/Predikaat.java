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
public enum Predikaat implements Enumeratie {
    K(1, "K", "Zijne Koninklijke Hoogheid", "Hare Koninklijke Hoogheid"),
    H(2, "H", "Zijne Hoogheid", "Hare Hoogheid"), J(3, "J", "Jonkheer", "Jonkvrouw");

    /**
     * 
     */
    private static final EnumParser<Predikaat> PARSER = new EnumParser<Predikaat>(Predikaat.class);

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
    private String naamMannelijk;
    /**
     * 
     */
    private String naamVrouwelijk;

    /**
     * 
     * 
     * @param id
     *            Het database-ID.
     * @param code
     * @param naamMannelijk
     * @param naamVrouwelijk
     */
    Predikaat(final int id, final String code, final String naamMannelijk, final String naamVrouwelijk) {
        this.id = id;
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * 
     */
    public static Predikaat parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * 
     */
    public static Predikaat parseCode(final String code) {
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

    public String getNaamMannelijk() {
        return naamMannelijk;
    }

    public String getNaamVrouwelijk() {
        return naamVrouwelijk;
    }
}
