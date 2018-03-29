/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 *
 */
public enum StatusOnderzoek implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '", "'
    || oms
    || '")'
    FROM  kern.statusonderzoek
    ORDER BY id
    */    

    private static final EnumParser<StatusOnderzoek> PARSER = new EnumParser<>(StatusOnderzoek.class);

    private final int id;
    private final String naam;
    private final String omschrijving;

    /**
     * Maak een nieuwe status onderzoek.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     */
    StatusOnderzoek(final int id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return status onderzoek
     */
    public static StatusOnderzoek parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /* (non-Javadoc)
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie StatusOnderzoek heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam van StatusOnderzoek.
     *
     * @return de waarde van naam van StatusOnderzoek
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving van StatusOnderzoek.
     *
     * @return de waarde van omschrijving van StatusOnderzoek
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
