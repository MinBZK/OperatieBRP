/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.verwerker;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.basis.ObjectType;

/**
 * TODO: Add documentation
 */
public interface StappenVerwerker<O extends ObjectType, C extends StappenContext, R extends StappenResultaat> {

    /**
     * Standaard methode voor het verwerken van een bericht.
     *
     * @param onderwerp Het bericht dat verwerkt dient te worden.
     * @param context De context waarbinnen het bericht wordt verwerkt.
     * @return het resultaat van de bericht verwerking.
     */
    R verwerk(final O onderwerp, final C context);
}
