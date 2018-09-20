/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
public enum NadereBijhoudingsaard implements Enumeratie {

    /** Actueel. */
    ACTUEEL((short) 1, "A", "Actueel"),
    /** Rechtstreeks niet ingezetene. */
    RECHTSTREEKS_NIET_INGEZETENE((short) 2, "R", "Rechtstreeks niet-ingezetene"),
    /** Emigratie. */
    EMIGRATIE((short) 3, "E", "Emigratie"),
    /** Overleden. */
    OVERLEDEN((short) 4, "O", "Overleden"),
    /** Vertrokken onbekend waarheen. */
    VERTROKKEN_ONBEKEND_WAARHEEN((short) 5, "V", "Vertrokken onbekend waarheen"),
    /** Ministerieel besluit. */
    MINISTERIEEL_BESLUIT((short) 6, "M", "Ministerieel besluit"),
    /** Fout. */
    FOUT((short) 7, "F", "Fout"),
    /** Onbekend. */
    ONBEKEND((short) 8, "?", "Onbekend");

    /**
     *
     */
    private static final EnumParser<NadereBijhoudingsaard> PARSER = new EnumParser<>(NadereBijhoudingsaard.class);
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
    private final String naam;

    /**
     * Maak een nieuwe nadere bijhoudingsaard.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    NadereBijhoudingsaard(final short id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
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
     * @return nadere bijhoudingsaard
     */
    public static NadereBijhoudingsaard parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return nadere bijhoudingsaard
     */
    public static NadereBijhoudingsaard parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
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
}
