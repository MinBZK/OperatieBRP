/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
public enum SoortRelatie implements Enumeratie {

    /** Huwelijk. */
    HUWELIJK((short) 1, "H", "Huwelijk"),
    /** Geregistreerd partnerschap. */
    GEREGISTREERD_PARTNERSCHAP((short) 2, "G", "Geregistreerd partnerschap"),
    /** Familierechtelijke betrekking. */
    FAMILIERECHTELIJKE_BETREKKING((short) 3, "F", "Familierechtelijke betrekking"),
    /** Erkenning ongeboren vrucht. */
    ERKENNING_ONGEBOREN_VRUCHT((short) 4, "E", "Erkenning ongeboren vrucht"),
    /** Ontkenning ouderschap ongeboren vrucht. */
    ONTKENNING_OUDERSCHAP_ONGEBOREN_VRUCHT((short) 5, "O", "Ontkenning ouderschap ongeboren vrucht"),
    /** Naamskeuze ongeboren vrucht. */
    NAAMSKEUZE_ONGEBOREN_VRUCHT((short) 6, "N", "Naamskeuze ongeboren vrucht");

    /**
     * 
     */
    private static final EnumParser<SoortRelatie> PARSER = new EnumParser<>(SoortRelatie.class);

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
     * Maak een nieuwe soort relatie.
     *
     * @param id
     *            id
     * @param code
     *            code
     * @param naam
     *            naam
     */
    SoortRelatie(final short id, final String code, final String naam) {
        this.id = id;
        this.code = code;
        this.naam = naam;
    }

    /* (non-Javadoc)
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
     * @return soort relatie
     */
    public static SoortRelatie parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return soort relatie
     */
    public static SoortRelatie parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /* (non-Javadoc)
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }
}
