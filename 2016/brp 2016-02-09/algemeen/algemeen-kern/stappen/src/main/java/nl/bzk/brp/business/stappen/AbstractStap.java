/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.basis.BrpObject;


/**
 * Abstracte klasse om een stap in het stappen mechanisme te implementeren. Een process dat verschillende stappen
 * gebruikt, waarbij elke stap wordt
 * geimplementeerd middels een implementatie van deze klasse. De stappen voeren de methode voerStapUit uit, en kunnen
 * optioneel ook een
 * voerNabewerkingStapUit uitvoeren.
 *
 * @param <O> Type Objecttype waar deze stap voor wordt uitgevoerd
 * @param <C> Type Context waarbinnen de stap wordt uitgevoerd
 * @param <R> Type Resultaat waar deze stap eventuele resultaten en meldingen in opslaat
 */
public abstract class AbstractStap<O extends BrpObject, C extends StappenContext, R extends StappenResultaat>
        implements Stap<O, C, R>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public void voerNabewerkingStapUit(final O onderwerp, final C context, final R resultaat) {
        // Standaard is er geen nabewerkingstap, omdat dit niet verplicht is voor een stap.
        LOGGER.debug("Geen nabewerking voor stap {}", this.getClass().getSimpleName());
    }
}
