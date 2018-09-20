/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;


/*
 * CHECKSTYLE:OFF Database-enum.
 */
/**
 *
 */
public enum SoortMultiRealiteitRegel implements Enumeratie {
    PERSOON(1, "Persoon", "Multirealiteit op persoonsgegevens, zoals naam en geboortegegevens."), RELATIE(2,
            "Relatie", "Multirealiteit op Relatie."), BETROKKENHEID(3, "Betrokkenheid",
            "Multirealiteit op Betrokkenheid.");

    /**
     * 
     */
    private static final EnumParser<SoortMultiRealiteitRegel> PARSER = new EnumParser<SoortMultiRealiteitRegel>(
            SoortMultiRealiteitRegel.class);
    /**
     * 
     */
    private final int id;
    /**
     * 
     */
    private final String naam;
    /**
     * 
     */
    private final String omschrijving;

    /**
     * @param id
     * @param naam
     * @param omschrijving
     */
    private SoortMultiRealiteitRegel(final int id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id
     * @return
     */
    public static SoortMultiRealiteitRegel parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortMultiRealiteitRegel heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
