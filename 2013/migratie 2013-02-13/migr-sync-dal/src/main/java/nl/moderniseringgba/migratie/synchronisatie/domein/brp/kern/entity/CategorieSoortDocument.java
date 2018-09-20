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
public enum CategorieSoortDocument implements Enumeratie {
    NEDERLANDSE_AKTE(1, "Nederlandse Akte", "Nederlandse Akten van de burgerlijke stand.");

    /**
     * 
     */
    private static final EnumParser<CategorieSoortDocument> PARSER = new EnumParser<CategorieSoortDocument>(
            CategorieSoortDocument.class);
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
    private CategorieSoortDocument(final int id, final String naam, final String omschrijving) {
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
    public static CategorieSoortDocument parseId(final Integer id) {
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
        throw new UnsupportedOperationException("De enumeratie CategorieSoortDocument heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
