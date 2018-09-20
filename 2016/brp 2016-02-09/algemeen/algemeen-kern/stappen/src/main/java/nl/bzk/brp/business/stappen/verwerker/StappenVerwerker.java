/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.verwerker;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.basis.BrpObject;


/**
 * Interface voor een stappenverwerker process waarin zowel een uitvoerstap als een optionele nabewerking stap op de weg
 * terug wordt uitgevoerd.
 *
 * @param <O> Type Objecttype waar deze stap voor wordt uitgevoerd
 * @param <C> Type Context waarbinnen de stap wordt uitgevoerd
 * @param <R> Type Resultaat waar deze stap eventuele resultaten en meldingen in opslaat
 */
public interface StappenVerwerker<O extends BrpObject, C extends StappenContext, R extends StappenResultaat> {

    /**
     * Standaard methode voor het verwerken van een bericht.
     *
     * @param onderwerp Het bericht dat verwerkt dient te worden
     * @param context   De context waarbinnen het bericht wordt verwerkt
     * @return het resultaat van de bericht verwerking
     */
    R verwerk(final O onderwerp, final C context);
}
