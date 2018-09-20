/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersIds.
 */
@Repository("historiePersoonOverlijdenRepository")
public class HistoriePersoonOverlijdenJpaRepository
        extends AbstractGroepFormeleHistorieRepository<PersoonModel, HisPersoonOverlijdenModel>
{

    @Override
    protected HisPersoonOverlijdenModel maakNieuwHistorieRecord(final PersoonModel objectType) {
        return new HisPersoonOverlijdenModel(objectType, objectType.getOverlijden());
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonOverlijdenModel> getCLaagDomainClass() {
        return HisPersoonOverlijdenModel.class;
    }
}
