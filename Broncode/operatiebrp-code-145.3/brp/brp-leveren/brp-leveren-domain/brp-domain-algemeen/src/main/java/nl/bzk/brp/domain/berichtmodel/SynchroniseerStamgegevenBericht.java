/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

/**
 * StamgegevenBericht.
 */
public final class SynchroniseerStamgegevenBericht extends Bericht {

    private BerichtStamgegevens berichtStamgegevens;

    /**
     * Constructor.
     * @param basisBerichtGegevens de basis berichtgegevens
     * @param berichtStamgegevens de specifieke berichtgegevens
     */
    public SynchroniseerStamgegevenBericht(final BasisBerichtGegevens basisBerichtGegevens,
                                           final BerichtStamgegevens berichtStamgegevens) {
        super(basisBerichtGegevens);
        this.berichtStamgegevens = berichtStamgegevens;
    }

    public BerichtStamgegevens getBerichtStamgegevens() {
        return berichtStamgegevens;
    }


}
