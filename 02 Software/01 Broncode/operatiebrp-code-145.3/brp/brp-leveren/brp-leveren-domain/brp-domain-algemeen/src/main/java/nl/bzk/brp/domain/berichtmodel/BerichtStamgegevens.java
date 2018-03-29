/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import nl.bzk.brp.domain.algemeen.StamtabelGegevens;

/**
 * BerichtStamgegevens. Bericht klasse voor synchronisatie stamgegevens.
 */
public final class BerichtStamgegevens {

    private StamtabelGegevens stamtabelGegevens;

    /**
     * Constructor.
     *
     * @param stamtabelGegevens de {@link StamtabelGegevens}
     */
    public BerichtStamgegevens(final StamtabelGegevens stamtabelGegevens) {
        this.stamtabelGegevens = stamtabelGegevens;
    }

    public StamtabelGegevens getStamtabelGegevens() {
        return stamtabelGegevens;
    }

}
