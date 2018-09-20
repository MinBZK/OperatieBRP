/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;

/**
 *
 */
public enum Rol implements Enumeratie {
    /** Afnemer. */
    AFNEMER((short) 1, "Afnemer", new BrpDatum(0, null), new BrpDatum(99991231, null)),
    /** Bevoegdheidstoedeler. */
    BIJHOUDINGSORGAAN_COLLEGE((short) 2, "Bijhoudingsorgaan College", new BrpDatum(0, null), new BrpDatum(99991231, null)),
    /** Bijhoudingsorgaan Minister. */
    BIJHOUDINGSORGAAN_MINISTER((short) 3, "Bijhoudingsorgaan Minister", new BrpDatum(0, null), new BrpDatum(99991231, null));

    private static final EnumParser<Rol> PARSER = new EnumParser<>(Rol.class);
    private final short id;
    private final String naam;
    private final BrpDatum datumAanvangGeldigheid;
    private final BrpDatum datumEindeGeldigheid;

    /**
     * Maak een nieuwe rol.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param datumAanvangGeldigheid
     *            datum aanvang geldigheid
     * @param datumEindeGeldigheid
     *            datum einde geldigheid
     */
    Rol(final short id, final String naam, final BrpDatum datumAanvangGeldigheid, final BrpDatum datumEindeGeldigheid) {
        this.id = id;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /* (non-Javadoc)
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
     * @return rol
     */
    public static Rol parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Rol heeft geen code");
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
     * Geef de waarde van datum aanvang geldigheid.
     *
     * @return datum aanvang geldigheid
     */
    public BrpDatum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum einde geldigheid.
     *
     * @return datum einde geldigheid
     */
    public BrpDatum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }
}
