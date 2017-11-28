/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;

/**
 * List view voor actie.
 */
public final class ActieListView implements ActieView {
    private final BRPActie actie;

    /**
     * Constructor.
     * 
     * @param actie
     *            actie
     */
    public ActieListView(final BRPActie actie) {
        this.actie = actie;
    }

    /**
     * geef actie terug.
     * @return brp actie
     */
    public BRPActie getActie() {
        return actie;
    }

}
