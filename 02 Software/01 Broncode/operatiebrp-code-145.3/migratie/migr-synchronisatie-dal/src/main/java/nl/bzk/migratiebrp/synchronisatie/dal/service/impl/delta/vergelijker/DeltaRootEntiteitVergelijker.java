/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;

/**
 * Probeert de verschillen tussen 2 personen te detecteren.
 */
public final class DeltaRootEntiteitVergelijker extends AbstractDeltaVergelijker {

    /**
     * Maakt een vergelijker met de {@link DeltaRootEntiteitModus#PERSOON} als modus.
     */
    public DeltaRootEntiteitVergelijker() {
        super(DeltaRootEntiteitModus.PERSOON);
    }

    /**
     * Maakt een vergelijker en zet de modus waar deze vergelijker in werkt.
     * @param modus een {@link DeltaRootEntiteitModus} waarmee aangegeven wordt voor welke {@link RootEntiteit} deze vergelijker werkt.
     */

    public DeltaRootEntiteitVergelijker(final DeltaRootEntiteitModus modus) {
        super(modus);
    }

    /**
     * Vergelijk een betaande en een nieuwe {@link RootEntiteit}. Als of de bestaande of de nieuwe entiteit null is, dan
     * wordt er een lege lijst terug gegeven. Er kan dan geen verschil worden bepaald.
     * @param context de context voor deltabepaling
     * @param bestaandeRootEntiteit De bestaande entiteit
     * @param nieuweRootEntiteit De nieuwe entiteit
     * @return een lijst met verschillen tussen de 2 opgegeven entiteiten
     */
    @Override
    public VergelijkerResultaat vergelijk(final DeltaBepalingContext context, final RootEntiteit bestaandeRootEntiteit, final RootEntiteit nieuweRootEntiteit) {
        return vergelijkEntiteiten(null, bestaandeRootEntiteit, nieuweRootEntiteit);
    }

    @Override
    public VergelijkerResultaat vergelijkEntiteiten(final RootEntiteit eigenaar, final Entiteit bestaandeEntiteit, final Entiteit nieuweEntiteit) {
        final VergelijkerResultaat result = new DeltaVergelijkerResultaat();
        try {
            zoekVerschillen(result, bestaandeEntiteit, nieuweEntiteit, eigenaar, null);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
