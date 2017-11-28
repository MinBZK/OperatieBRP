/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie van effectafnemerindicaties.
 */
public enum EffectAfnemerindicaties implements Enumeratie {

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
    FROM   autaut.effectafnemerindicaties
    ORDER BY id
    */  

    private static final EnumParser<EffectAfnemerindicaties> PARSER = new EnumParser<>(EffectAfnemerindicaties.class);

    private final int id;
    private final String naam;
    private final String omschrijving;

    /**
     * Maak een nieuw EffectAfnemerindicaties object.
     *
     * @param id
     *            id
     * @param naam
     *            code
     * @param omschrijving
     *            omschrijving
     */
    EffectAfnemerindicaties(final int id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
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
    public static EffectAfnemerindicaties parseId(final Integer id) {
        return PARSER.parseId(id);
    }
    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.enums.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie EffectAfnemerindicaties heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam van EffectAfnemerindicaties.
     *
     * @return de waarde van naam van EffectAfnemerindicaties
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van omschrijving van EffectAfnemerindicaties.
     *
     * @return de waarde van omschrijving van EffectAfnemerindicaties
     */
    public String getOmschrijving() {
        return omschrijving;
    }
}
