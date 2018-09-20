/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering;

import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;

/**
 * De interface voor de LO3 leveringsautorisatie verwerking context.
 */
public interface Lo3LeveringsautorisatieVerwerkingContext extends LeveringsautorisatieVerwerkingContext {

    /**
     * Geeft de conversie cache.
     *
     * @return de conversie cache
     */
    ConversieCache getConversieCache();
}
