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
public enum SoortPartij implements Enumeratie {
    WETGEVER(1, "Wetgever"), VERTEGENWOORDIGER_REGERING(2, "Vertegenwoordiger Regering"), GEMEENTE(3, "Gemeente"),
    OVERHEIDSORGAAN(4, "Overheidsorgaan"), DERDE(5, "Derde"), SAMENWERKINGSVERBAND(6, "Samenwerkingsverband");

    /**
     * 
     */
    private static final EnumParser<SoortPartij> PARSER = new EnumParser<SoortPartij>(SoortPartij.class);

    /**
     * 
     */
    private final int id;
    /**
     * 
     */
    private final String omschrijving;

    /**
     * @param id
     * @param omschrijving
     */
    private SoortPartij(final int id, final String omschrijving) {
        this.id = id;
        this.omschrijving = omschrijving;
    }

    /**
     * @param id
     * @return
     */
    public static SoortPartij parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortIndicatie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    public String getOmschrijving() {
        return omschrijving;
    }
}
