/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * StufAntwoordBericht.
 */
public final class StufAntwoordBericht extends Bericht {

    private BerichtStufTransformatieResultaat berichtStufVertaling;

    /**
     * Constructor.
     *
     * @param basisBerichtGegevens de basis berichtgegevens
     * @param berichtStufVertaling het resultaat van de stuf transformatie
     */
    public StufAntwoordBericht(final BasisBerichtGegevens basisBerichtGegevens, final BerichtStufTransformatieResultaat berichtStufVertaling) {
        super(basisBerichtGegevens);
        this.berichtStufVertaling = berichtStufVertaling;
    }

    /**
     * @return de stuf vertaling
     */
    public BerichtStufTransformatieResultaat getStufVertaling() {
        return berichtStufVertaling;
    }

}

