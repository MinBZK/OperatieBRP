/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.stappen.verwerker.StappenVerwerker;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Interface voor de standaard bericht verwerking.
 */
public interface BerichtVerwerker<O extends BerichtBericht, C extends BerichtContext, R extends BerichtVerwerkingsResultaat>
        extends StappenVerwerker<O, C, R>
{

    /**
     * Standaard methode voor het verwerken van een bericht.
     *
     * @param bericht Het bericht dat verwerkt dient te worden.
     * @param context De context waarbinnen het bericht wordt verwerkt.
     * @param <R> Type voor het bericht resultaat.
     * @return het resultaat van de bericht verwerking.
     */
     <R extends BerichtVerwerkingsResultaat> R verwerkBericht(final O bericht,
                                                              final C context);

}
