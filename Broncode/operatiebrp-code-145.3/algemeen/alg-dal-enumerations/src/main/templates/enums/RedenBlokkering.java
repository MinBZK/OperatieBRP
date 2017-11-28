/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enum representatie van de migblok.RdnBlokkering tabel.
 */
public enum RedenBlokkering implements Enumeratie {

    /** QUERY:
    SELECT naam 
    || '. ',
    naam,
    '('
    || id
    || ', "'
    || naam
    || '")'
    FROM   migblok.rdnblokkering
    ORDER BY id
    */        

    /**
     *
     */
    private static final EnumParser<RedenBlokkering> PARSER = new EnumParser<>(RedenBlokkering.class);

    private final int id;

    private final String naam;

    /**
     * Maak een nieuwe reden blokkering.
     *
     * @param id
     *            the id
     * @param naam
     *            the naam
     */
    RedenBlokkering(final int id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return reden blokkering
     */
    public static RedenBlokkering parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * Geef de waarde van naam van RedenBlokkering.
     *
     * @return de waarde van naam van RedenBlokkering
     */
    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie RedenBlokkering heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
