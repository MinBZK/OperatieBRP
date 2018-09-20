/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.impl;

import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.Bericht;

/**
 * Abstract bericht met een melding.
 */
public abstract class AbstractMeldingBericht extends AbstractBericht implements Bericht {

    private static final long serialVersionUID = 1L;

    private final String bericht;
    private final String melding;
    private final String berichtType;

    /**
     * Constructor.
     *
     * @param berichtType
     *            bericht type
     * @param bericht
     *            ongeparsed bericht
     * @param melding
     *            melding
     */
    protected AbstractMeldingBericht(final String berichtType, final String bericht, final String melding) {
        this.bericht = bericht;
        this.melding = melding;
        this.berichtType = berichtType;
    }

    /**
     * Geef de waarde van bericht.
     *
     * @return bericht
     */
    public final String getBericht() {
        return bericht;
    }

    /**
     * Geef de waarde van melding.
     *
     * @return melding
     */
    public final String getMelding() {
        return melding;
    }

    @Override
    public final String getBerichtType() {
        return berichtType;
    }

    @Override
    public final String getStartCyclus() {
        return null;
    }

    @Override
    public final String format() {
        return bericht;
    }

}
