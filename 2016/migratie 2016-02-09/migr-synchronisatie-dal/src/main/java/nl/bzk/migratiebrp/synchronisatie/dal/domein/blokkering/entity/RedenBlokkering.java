/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EnumParser;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie;

/**
 * Enum representatie van de migblok.RdnBlokkering tabel.
 */
public enum RedenBlokkering implements Enumeratie {
    /** Verhuizend van LO3 naar BRP. */
    VERHUIZEND_VAN_LO3_NAAR_BRP((short) 1, "Verhuizend van LO3 naar BRP"),
    /** Verhuizend van BRP naar LO3 GBA. */
    VERHUIZEND_VAN_BRP_NAAR_LO3_GBA((short) 2, "Verhuizend van BRP naar LO3 GBA"),
    /** Verhuizend van BRP naar LO3 RNI. */
    VERHUIZEND_VAN_BRP_NAAR_LO3_RNI((short) 3, "Verhuizend van BRP naar LO3 RNI");

    /**
     *
     */
    private static final EnumParser<RedenBlokkering> PARSER = new EnumParser<>(RedenBlokkering.class);

    private final short id;

    private final String naam;

    /**
     * Maak een nieuwe reden blokkering.
     *
     * @param id
     *            the id
     * @param naam
     *            the naam
     */
    private RedenBlokkering(final short id, final String naam) {
        this.id = id;
        this.naam = naam;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return reden blokkering
     */
    public static RedenBlokkering parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param naam
     *            naam
     * @return reden blokkering
     */
    public static RedenBlokkering parseCode(final String naam) {
        return PARSER.parseCode(naam);
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie RedenBlokkering heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
