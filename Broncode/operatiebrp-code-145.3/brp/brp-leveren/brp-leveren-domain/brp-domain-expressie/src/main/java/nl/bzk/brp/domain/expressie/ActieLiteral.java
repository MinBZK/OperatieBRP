/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.brp.domain.leveringmodel.Actie;

/**
 * Literal voor BRP Actie objecten.
 */
public final class ActieLiteral implements Literal {

    private Actie actie;

    /**
     * Constructor.
     *
     * @param actie de actie
     */
    public ActieLiteral(final Actie actie) {
        this.actie = actie;
    }

    /**
     * @return de actie
     */
    public Actie getActie() {
        return actie;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BRP_ACTIE;
    }

    @Override
    public String alsString() {
        return actie.toString();
    }

    @Override
    public String toString() {
        return '[' + alsString() + ']';
    }
}
