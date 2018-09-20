/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 *
 */
public enum SoortPersoonOnderzoek implements Enumeratie {

    /** Direct. */
    DIRECT((short) 1, "Direct", "De persoon is zelfstandig onderwerp van het onderzoek."),
    /** Indirect. */
    INDIRECT((short) 2, "Indirect", "Een gegeven op de persoonslijst staat wel in onderzoek, maar niet vanuit de persoon zelf.");

    private static final EnumParser<SoortPersoonOnderzoek> PARSER = new EnumParser<>(SoortPersoonOnderzoek.class);

    private final short id;
    private final String naam;
    private final String omschrijving;

    /**
     * Maak een nieuwe soort persoon onderzoek.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     */
    SoortPersoonOnderzoek(final short id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort persoon onderzoek
     */
    public static SoortPersoonOnderzoek parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortPersoonOnderzoek heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
