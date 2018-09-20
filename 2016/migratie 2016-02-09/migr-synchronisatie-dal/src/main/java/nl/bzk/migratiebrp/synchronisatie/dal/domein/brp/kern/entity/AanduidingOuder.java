/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Enumeration voor {@link AanduidingOuder}.
 */
public enum AanduidingOuder implements Enumeratie {
    /** ouder 1. */
    OUDER_1(1),

    /** ouder 2. */
    OUDER_2(2);

    private static final EnumParser<AanduidingOuder> PARSER = new EnumParser<>(AanduidingOuder.class);

    private final short id;

    /**
     * constructor.
     * 
     * @param ouderNr
     *                  1 or 2
     **/
    AanduidingOuder(final int ouderNr) {
        id = (short) ouderNr;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id
     * @return lo3 berichten bron
     */
    public static AanduidingOuder parseId(final Short id) {
        return PARSER.parseId(id);
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

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public boolean heeftCode() {
        return false;
    }

}
