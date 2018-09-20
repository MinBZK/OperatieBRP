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
public enum CategorieSoortActie implements Enumeratie {
    /** Alle soorten acties voortvloeiend uit conversie. */
    CONVERSIE(1, "Conversie", "Alle soorten acties voortvloeiend uit conversie"),
    /** Alle soorten acties met betrekking tot leggen van familierechtelijke betrekking tussen ouder(s) en kind. */
    FAM_RECHT_BETR(2, "Familierechtelijke betrekking",
            "Alle soorten acties met betrekking tot leggen van familierechtelijke betrekking tussen ouder(s) en kind"),
    /** Alle soorten acties met betrekking tot verhuizingen. */
    VERHUIZING(3, "Verhuizing", "Alle soorten acties met betrekking tot verhuizingen"),
    /** Alle soorten acties met betrekking tot huwelijk/geregistreerd partnerschap. */
    HUWELIJK_GP(4, "Huwelijk / Geregistreerd Partnerschap",
            "Alle soorten acties met betrekking tot huwelijk/geregistreerd partnerschap");

    /**
     * 
     */
    private static final EnumParser<CategorieSoortActie> PARSER = new EnumParser<CategorieSoortActie>(
            CategorieSoortActie.class);
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
    private CategorieSoortActie(final int id, final String naam, final String omschrijving) {
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
    public static CategorieSoortActie parseId(final Integer id) {
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
        throw new UnsupportedOperationException("De enumeratie SoortActie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
