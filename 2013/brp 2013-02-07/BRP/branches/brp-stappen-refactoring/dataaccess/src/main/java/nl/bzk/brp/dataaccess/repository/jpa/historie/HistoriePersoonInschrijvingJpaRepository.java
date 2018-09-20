/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersIds.
 */
@Repository("historiePersoonInschrijvingRepository")
public class HistoriePersoonInschrijvingJpaRepository
        extends AbstractGroepFormeleHistorieRepository<PersoonModel, HisPersoonInschrijvingModel>
{

    @Override
    protected HisPersoonInschrijvingModel maakNieuwHistorieRecord(final PersoonModel objectType) {
        return new HisPersoonInschrijvingModel(objectType, objectType.getInschrijving());
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonInschrijvingModel> getCLaagDomainClass() {
        return HisPersoonInschrijvingModel.class;
    }
}
