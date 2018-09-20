/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.administratievehandeling;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;

/**
 * TODO: Deze stap zal de administratieve handeling op de queue plaatsen
 */
public class PubliceerAdministratieveHandelingStap<O extends BerichtBericht,C extends StappenContext, R extends StappenResultaat> extends AbstractStap<O,C,R> {

    @Override
    public boolean voerStapUit(final O onderwerp, final C context, final R resultaat) {
        return false;
    }
}