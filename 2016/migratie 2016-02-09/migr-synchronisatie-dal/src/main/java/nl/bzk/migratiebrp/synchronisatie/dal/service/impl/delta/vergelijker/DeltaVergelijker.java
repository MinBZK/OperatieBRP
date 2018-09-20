/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;

/**
 * Vergelijkers vergelijken twee objecten(oud en nieuw) en levert een VergelijkerResultaat met daarin de verschillen op.
 *
 */
public interface DeltaVergelijker {

    /**
     * Vergelijk oud en nieuw met elkaar.
     *
     * @param context
     *            de context voor deltabepaling
     * @param bestaandeEntiteit
     *            de bestaande entiteit
     * @param nieuweEntiteit
     *            de nieuwe entiteit
     * @return het vergelijker resultaat
     */
    VergelijkerResultaat vergelijk(DeltaBepalingContext context, DeltaRootEntiteit bestaandeEntiteit, DeltaRootEntiteit nieuweEntiteit);
}
