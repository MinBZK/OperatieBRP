/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.support;

import nl.bzk.brp.business.stappen.verwerker.AbstractVerwerker;


/**
 * Minimale implementatie van een abstracte verwerker voor unit test doeleinden.
 */
public class BrpStappenVerwerker extends AbstractVerwerker<BrpStapOnderwerp, BrpStapContext, BrpStapResultaat, BrpStap> {
    @Override
    protected BrpStapResultaat creeerResultaat(final BrpStapOnderwerp onderwerp, final BrpStapContext context) {
        context.getResultaatId();
        return new BrpStapResultaat(null);
    }
}
