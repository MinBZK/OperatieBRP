/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Categorisatie van rechtsgrond.
 */
public enum SoortRechtsgrond implements Enumeratie {

    /** BMR BEVAT GEEN WAARDEN!. */
    BMR_BEVAT_GEEN_WAARDEN((short) -1, "BMR bevat geen waarden!");

    // /** Verkrijging Nederlandse Nationaliteit. */
    // VERKRIJGING_NL_NATIONALITEIT((short) 1, "Verkrijging Nederlandse Nationaliteit"),
    // /** Verlies Nederlandse Nationaliteit. */
    // VERLIES_NL_NATIONALITEIT((short) 2, "Verlies Nederlandse Nationaliteit"),
    // /** Internationaal verdrag RNI. */
    // INTERNATIONAAL_VERDRAG_RNI((short) 3, "Internationaal verdrag RNI");

    /**
     *
     */
    private static final EnumParser<SoortRechtsgrond> PARSER = new EnumParser<>(SoortRechtsgrond.class);

    private final short id;
    private final String naam;

    /**
     * Maak een nieuwe soort rechtsgrond.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    SoortRechtsgrond(final short id, final String naam) {
        this.id = id;
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
     *            de id van de rechtsgrond
     * @return SoortRechtsgrond
     */
    public static SoortRechtsgrond parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortIndicatie heeft geen code");
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
}
