/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * De implementatie van de LO3 leveringsautorisatie verwerking context.
 */
public class Lo3LeveringsautorisatieVerwerkingContextImpl extends LeveringsautorisatieVerwerkingContextImpl
    implements Lo3LeveringsautorisatieVerwerkingContext
{
    private final ConversieCache conversieCache;

    /**
     * Constructor.
     *
     * @param administratieveHandeling administratieve handeling
     * @param personen                 personen
     * @param populatieMap             populatie
     * @param conversieCache           conversie cache
     */
    public Lo3LeveringsautorisatieVerwerkingContextImpl(final AdministratieveHandelingModel administratieveHandeling,
        final List<PersoonHisVolledig> personen,
        final Map<Integer, Populatie> populatieMap,
        final ConversieCache conversieCache)
    {
        super(administratieveHandeling, personen, populatieMap, null, null);
        this.conversieCache = conversieCache;
    }

    /**
     * Geeft de conversie cache.
     *
     * @return De conversie cache.
     */
    public final ConversieCache getConversieCache() {
        return conversieCache;
    }
}
