/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EnumParser;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie;

/**
 * Enumeratie van effectafnemerindicaties.
 */
public enum EffectAfnemerindicaties implements Enumeratie {

    /** Plaatsing. */
    PLAATSING((short) 1, "Plaatsing", "Als gevolg van de dienst is een (aantal) afnemerindicatie(s) geplaatst."),

    /** Verwijdering. */
    VERWIJDERING((short) 2, "Verwijdering", "Als gevolg van de dienst is een (aantal) afnemerindicatie(s) verwijderd.");

    private static final EnumParser<EffectAfnemerindicaties> PARSER = new EnumParser<>(EffectAfnemerindicaties.class);

    private final short id;
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
    EffectAfnemerindicaties(final short id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
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
     *            id
     * @return soort relatie
     */
    public static EffectAfnemerindicaties parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return soort relatie
     */
    public static EffectAfnemerindicaties parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
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
