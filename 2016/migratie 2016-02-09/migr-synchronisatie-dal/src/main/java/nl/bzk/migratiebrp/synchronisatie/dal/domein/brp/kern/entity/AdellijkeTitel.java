/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Adellijke titel.
 */

public enum AdellijkeTitel implements Enumeratie {
    /** Baron/barones. */
    B((short) 1, "B", "baron", "barones"),
    /** Graaf/gravin. */
    G((short) 2, "G", "graaf", "gravin"),
    /** Hertog/hertogin. */
    H((short) 3, "H", "hertog", "hertogin"),
    /** Markies/markiezin. */
    M((short) 4, "M", "markies", "markiezin"),
    /** Prins/prinses. */
    P((short) 5, "P", "prins", "prinses"),
    /** Ridder. */
    @SuppressWarnings("checkstyle:multiplestringliterals")
    R((short) 6, "R", "ridder", "ridder");

    /**
     *
     */
    private static final EnumParser<AdellijkeTitel> PARSER = new EnumParser<>(AdellijkeTitel.class);

    /**
     *
     */
    private final short id;
    /**
     *
     */
    private final String code;
    /**
     *
     */
    private final String naamMannelijk;
    /**
     *
     */
    private final String naamVrouwelijk;

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
    AdellijkeTitel(final short id, final String code, final String naamMannelijk, final String naamVrouwelijk) {
        this.id = id;
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return adellijke titel
     */
    public static AdellijkeTitel parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return adellijke titel
     */
    public static AdellijkeTitel parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
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

    /**
     * Geef de waarde van naam mannelijk.
     *
     * @return naam mannelijk
     */
    public String getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Geef de waarde van naam vrouwelijk.
     *
     * @return naam vrouwelijk
     */
    public String getNaamVrouwelijk() {
        return naamVrouwelijk;
    }
}
