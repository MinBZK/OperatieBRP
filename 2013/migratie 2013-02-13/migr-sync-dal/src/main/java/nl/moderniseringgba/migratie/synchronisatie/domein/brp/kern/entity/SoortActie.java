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
public enum SoortActie implements Enumeratie {
    /** Conversie GBA. */
    CONVERSIE_GBA(1, "Conversie GBA", CategorieSoortActie.CONVERSIE),
    /** Inschrijving Geboorte. */
    INSCHR_GEBOORTE(2, "Inschrijving Geboorte", CategorieSoortActie.CONVERSIE),
    /** Verhuizing. */
    VERHUIZING(3, "Verhuizing", CategorieSoortActie.VERHUIZING),
    /** Registratie Erkenning. */
    REG_ERKENNING(4, "Registratie Erkenning", CategorieSoortActie.FAM_RECHT_BETR),
    /** Registratie Huwelijk. */
    REG_HUWELIJK(5, "Registratie Huwelijk", CategorieSoortActie.HUWELIJK_GP),
    /** Wijziging Geslachtsnaamcomponent. */
    WIJZ_GESLACHTSNAAM(6, "Wijziging Geslachtsnaamcomponent", CategorieSoortActie.FAM_RECHT_BETR),
    /** Wijziging Naamgebruik. */
    WIJZ_NAAMGEBRUIK(7, "Wijziging Naamgebruik", CategorieSoortActie.HUWELIJK_GP),
    /** Correctie Adres Binnen NL. */
    CORRECTIE_ADRES(8, "Correctie Adres Binnen NL", CategorieSoortActie.VERHUIZING);

    /**
     * 
     */
    private static final EnumParser<SoortActie> PARSER = new EnumParser<SoortActie>(SoortActie.class);

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
    private final CategorieSoortActie categorieSoort;

    /**
     * @param id
     * @param naam
     * @param categorieSoort
     */
    private SoortActie(final int id, final String naam, final CategorieSoortActie categorieSoort) {
        this.id = id;
        this.naam = naam;
        this.categorieSoort = categorieSoort;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id
     * @return
     */
    public static SoortActie parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    public String getNaam() {
        return naam;
    }

    public CategorieSoortActie getCategorieSoort() {
        return categorieSoort;
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
