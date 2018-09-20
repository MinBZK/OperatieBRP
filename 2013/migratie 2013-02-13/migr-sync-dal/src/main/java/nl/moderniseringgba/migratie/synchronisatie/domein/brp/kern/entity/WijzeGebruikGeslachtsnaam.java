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
public enum WijzeGebruikGeslachtsnaam implements Enumeratie {
    EIGEN(1, "E", "Eigen"), PARTNER(2, "P", "Partner"), PARTNER_VOOR_EIGEN(3, "V", "Partner, Eigen"),
    PARTNER_NA_EIGEN(4, "N", "Eigen, Partner");

    /**
     * 
     */
    private static final EnumParser<WijzeGebruikGeslachtsnaam> PARSER = new EnumParser<WijzeGebruikGeslachtsnaam>(
            WijzeGebruikGeslachtsnaam.class);

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private String naam;

    /**
     * @param id
     * @param code
     * @param naam
     */
    private WijzeGebruikGeslachtsnaam(final int id, final String code, final String naam) {
        this.id = id;
        this.code = code;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @param id
     * @return
     */
    public static WijzeGebruikGeslachtsnaam parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * @param code
     * @return
     */
    public static WijzeGebruikGeslachtsnaam parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    @Override
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return true;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }
}
