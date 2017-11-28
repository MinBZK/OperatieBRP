/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie van LeverwijzeSelectie.
 */
public enum LeverwijzeSelectie implements Enumeratie {

    /** QUERY:
    SELECT naam
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '")'
    FROM   autaut.LeverwijzeSel
    ORDER BY id
    */

    private static final EnumParser<LeverwijzeSelectie> PARSER = new EnumParser<>(LeverwijzeSelectie.class);

    private final int id;
    private final String naam;

    /**
     * Maak een nieuw LeverwijzeSelectie object.
     *
     * @param id
     *            id
     * @param naam
     *            code
     */
    LeverwijzeSelectie(final int id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort relatie
     */
    public static LeverwijzeSelectie parseId(final Integer id) {
        return PARSER.parseId(id);
    }
    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie LeverwijzeSelectie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam van LeverwijzeSelectie.
     *
     * @return de waarde van naam van LeverwijzeSelectie
     */
    public String getNaam() {
        return naam;
    }
}
