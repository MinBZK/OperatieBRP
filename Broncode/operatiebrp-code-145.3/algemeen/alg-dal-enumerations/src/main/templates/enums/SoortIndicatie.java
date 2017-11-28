/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 *
 */
public enum SoortIndicatie implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           naam,
           '('
           || id
           || ', "'
           || naam
           || '", '
           || CASE WHEN indmaterielehistorievantoepa THEN 'true' ELSE 'false' END
           || ')'
    FROM   kern.srtindicatie
    ORDER BY id
    */    

    /**
     *
     */
    private static final EnumParser<SoortIndicatie> PARSER = new EnumParser<>(SoortIndicatie.class);

    /**
     * 
     */
    private final int id;
    /**
     * 
     */
    private final String omschrijving;
    /**
     * 
     */
    private final boolean materieleHistorieVanToepassing;

    /**
     * Maak een nieuwe soort indicatie.
     *
     * @param id
     *            id
     * @param omschrijving
     *            omschrijving
     * @param materieleHistorieVanToepassing
     *            materiele historie van toepassing
     */
    SoortIndicatie(final int id, final String omschrijving, final boolean materieleHistorieVanToepassing) {
        this.id = id;
        this.omschrijving = omschrijving;
        this.materieleHistorieVanToepassing = materieleHistorieVanToepassing;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return soort indicatie
     */
    public static SoortIndicatie parseId(final Integer id) {
        return PARSER.parseId(id);
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

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortIndicatie heeft geen code");
    }

    /*
     * (non-Javadoc)
     *
     * @see Enumeratie#getNaam()
     */
    @Override
    public String getNaam() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van omschrijving van SoortIndicatie.
     *
     * @return de waarde van omschrijving van SoortIndicatie
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Checks if is materiele historie van toepassing.
     *
     * @return true, if is materiele historie van toepassing
     */
    public boolean isMaterieleHistorieVanToepassing() {
        return materieleHistorieVanToepassing;
    }
}
