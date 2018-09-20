/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.verwerker.StappenVerwerker;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;


/**
 * Interface voor de standaard bericht verwerking.
 *
 * @param <O> Bericht type.
 * @param <C> context om resultaten in op te slaan voor volgende stappen.
 * @param <R> Bericht verwerkingsresultaat type.
 */
public interface BerichtVerwerker<O extends BerichtBericht, C extends BerichtContext,
        R extends BerichtVerwerkingsResultaatImpl> extends StappenVerwerker<O, C, R>
{

    /**
     * Standaard methode voor het verwerken van een bericht.
     *
     * @param bericht Het bericht dat verwerkt dient te worden.
     * @param context De context waarbinnen het bericht wordt verwerkt.
     * @return het resultaat van de bericht verwerking.
     */
    R verwerkBericht(final O bericht, final C context);

}
