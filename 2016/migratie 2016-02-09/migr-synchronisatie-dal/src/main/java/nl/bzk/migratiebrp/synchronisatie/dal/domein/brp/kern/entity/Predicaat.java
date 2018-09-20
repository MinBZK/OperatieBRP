/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Predicaat.
 */
public enum Predicaat implements Enumeratie {
    /** Zijne/Hare Koninklijke Hoogheid. */
    K((short) 1, "K", "Zijne Koninklijke Hoogheid", "Hare Koninklijke Hoogheid"),
    /** Zijne/Hare Hoogheid. */
    H((short) 2, "H", "Zijne Hoogheid", "Hare Hoogheid"), J(
    /** Jonkheer/jonkvrouw. */
    (short) 3, "J", "Jonkheer", "Jonkvrouw");

    /**
     *
     */
    private static final EnumParser<Predicaat> PARSER = new EnumParser<>(Predicaat.class);

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
     * Maak een nieuwe predicaat.
     *
     * @param id
     *            Het database-ID.
     * @param code
     *            code
     * @param naamMannelijk
     *            naam mannelijk
     * @param naamVrouwelijk
     *            naam vrouwelijk
     */
    Predicaat(final short id, final String code, final String naamMannelijk, final String naamVrouwelijk) {
        this.id = id;
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;
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

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return predicaat
     */
    public static Predicaat parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return predicaat
     */
    public static Predicaat parseCode(final String code) {
        return PARSER.parseCode(code);
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
