/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * De class voor de SoortElement database tabel.
 *
 */
public enum SoortElement implements Enumeratie {

    /** Objecttype. */
    OBJECTTYPE((short) 1, "Objecttype"),
    /** Groep. */
    GROEP((short) 2, "Groep"),
    /** Attribuut. */
    ATTRIBUUT((short) 3, "Attribuut");

    private static final EnumParser<SoortElement> PARSER = new EnumParser<>(SoortElement.class);

    private final short id;

    private final String naam;

    /**
     * Maak een nieuwe soort element.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    SoortElement(final short id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    @Override
    public short getId() {
        return id;
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
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort element
     */
    public static SoortElement parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortElement heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
