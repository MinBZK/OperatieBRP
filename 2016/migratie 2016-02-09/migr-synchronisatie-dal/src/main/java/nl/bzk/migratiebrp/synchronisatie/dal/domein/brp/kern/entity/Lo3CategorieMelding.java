/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;
/**
 *
 */
public enum Lo3CategorieMelding implements Enumeratie {

    /** Verwerking. */
    VERWERKING((short) 1, "Verwerking"),
    /** Preconditie. */
    PRECONDITIE((short) 2, "Preconditie"),
    /** Bijzondere situatie. */
    BIJZONDERE_SITUATIE((short) 3, "Bijzondere situatie"),
    /** Syntax. */
    SYNTAX((short) 4, "Syntax"),
    /** Structuur. */
    STRUCTUUR((short) 5, "Structuur");

    /**
     * 
     */
    private static final EnumParser<Lo3CategorieMelding> PARSER = new EnumParser<>(Lo3CategorieMelding.class);
    /**
     * 
     */
    private final short id;
    /**
     * 
     */
    private final String naam;

    /**
     * Maak een nieuwe lo3 categorie melding.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     */
    Lo3CategorieMelding(final short id, final String naam) {
        this.id = id;
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
     * @return lo3 categorie melding
     */
    public static Lo3CategorieMelding parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Lo3CategorieMelding heeft geen code");
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
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
