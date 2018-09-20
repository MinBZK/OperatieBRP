/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;

/**
 * Repository voor de {@link nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl} tabel.
 */
public interface HisAfnemerindicatieTabelRepository {

    /**
     * Maakt een nieuwe afnemerIndicatie.
     *
     * @param persoonId De persoonId
     * @param partijAttribuut Partij
     * @param laAttribuut Leveringsautorisatie
     * @return persoon afnemerindicatie his volledig impl
     */
    PersoonAfnemerindicatieHisVolledigImpl maakNieuweAfnemerIndicatie(final Integer persoonId, final PartijAttribuut partijAttribuut,
        final LeveringsautorisatieAttribuut laAttribuut);
}
