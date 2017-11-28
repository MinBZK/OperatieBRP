/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.brp.domain.leveringmodel.MetaGroep;

/**
 * Een literal expressie voor een {@link MetaGroep}.
 */
public final class MetaGroepLiteral implements Literal {

    private final MetaGroep metaGroep;

    /**
     * Constructor.
     *
     * @param metaGroep een {@link MetaGroep}
     */
    public MetaGroepLiteral(final MetaGroep metaGroep) {
        this.metaGroep = metaGroep;
    }

    public MetaGroep getMetaGroep() {
        return metaGroep;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BRP_METAGROEP;
    }

    @Override
    public String toString() {
        return "@" + metaGroep.getGroepElement().getNaam();
    }

}
