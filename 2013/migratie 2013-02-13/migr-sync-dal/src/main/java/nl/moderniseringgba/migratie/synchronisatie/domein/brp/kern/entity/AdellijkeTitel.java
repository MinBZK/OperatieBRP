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
public enum AdellijkeTitel implements Enumeratie {
    B(1, "B", "baron", "barones"), G(2, "G", "graaf", "gravin"), H(3, "H", "hertog", "hertogin"), M(4, "M",
            "markies", "markiezin"), P(5, "P", "prins", "prinses"), R(6, "R", "ridder", "ridder");

    /**
     * 
     */
    private static final EnumParser<AdellijkeTitel> PARSER = new EnumParser<AdellijkeTitel>(AdellijkeTitel.class);

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
     * Enum voor verwijzingen naar de BRP-tabel AdellijkeTitel_VervangenDoorEnum.
     * 
     * @param id
     *            Het ID van de titel.
     * @param code
     *            De logische code.
     * @param naamMannelijk
     *            De mannelijke geslachtspecifieke naam.
     * @param naamVrouwelijk
     *            De vrouwelijke geslachtspecifieke naam.
     */
    AdellijkeTitel(final int id, final String code, final String naamMannelijk, final String naamVrouwelijk) {
        this.id = id;
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;
    }

    /**
     * @param id
     * @return
     */
    public static AdellijkeTitel parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static AdellijkeTitel parseCode(final String code) {
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

    public String getNaamMannelijk() {
        return naamMannelijk;
    }

    public String getNaamVrouwelijk() {
        return naamVrouwelijk;
    }
}
