/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp;

import java.io.Serializable;
import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;

/**
 * Basis Brp bericht.
 * @param <T> soort resultaat
 */
public abstract class AbstractBrpBericht<T extends Serializable> extends AbstractBericht implements BrpBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private final String berichtType;

    private T inhoud;

    /**
     * Constructor.
     * @param berichtType berichtType
     * @param inhoud inhoud van bericht
     */
    public AbstractBrpBericht(final String berichtType, final T inhoud) {
        this.berichtType = berichtType;
        this.inhoud = inhoud;
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
    public final GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Retourneert inhoud.
     * @return inhoud
     */
    public final T getInhoud() {
        return inhoud;
    }

}
