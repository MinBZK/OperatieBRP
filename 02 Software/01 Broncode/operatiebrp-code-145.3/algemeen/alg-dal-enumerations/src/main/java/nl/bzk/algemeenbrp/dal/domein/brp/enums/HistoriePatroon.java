/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Historie patroon.
 */
public enum HistoriePatroon implements Enumeratie {

    /**
     * Formele historie.
     */
    F(1, "F", "Formele historie"),
    /**
     * Formele historie en materiële historie.
     */
    F_M(2, "F+M", "Formele historie en materiële historie"),
    /**
     * Formele historie en materiële bestaansperiode.
     */
    F_M1(3, "F+M1", "Formele historie en materiële bestaansperiode"),
    /**
     * Formele bestaansperiode.
     */
    F1(4, "F1", "Formele bestaansperiode"),
    /**
     * Geen historie.
     */
    G(5, "G", "Geen historie"),
    /**
     * Bestaansperiode stamgegeven.
     */
    M1(6, "M1", "Bestaansperiode stamgegeven");

    /**
     *
     */
    private static final EnumParser<HistoriePatroon> PARSER = new EnumParser<>(HistoriePatroon.class);
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
     * Maak een nieuwe nadere bijhoudingsaard.
     *
     * @param id id
     * @param code code
     * @param naam naam
     */
    HistoriePatroon(final int id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
    }

    /*
     * (non-Javadoc)
     *
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return nadere bijhoudingsaard
     */
    public static HistoriePatroon parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code code
     * @return nadere bijhoudingsaard
     */
    public static HistoriePatroon parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /**
     * Geef de waarde van naam van NadereBijhoudingsaard.
     *
     * @return de waarde van naam van NadereBijhoudingsaard
     */
    @Override
    public String getNaam() {
        return naam;
    }

    /*
     * (non-Javadoc)
     *
     * @see Enumeratie#getCode()
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
