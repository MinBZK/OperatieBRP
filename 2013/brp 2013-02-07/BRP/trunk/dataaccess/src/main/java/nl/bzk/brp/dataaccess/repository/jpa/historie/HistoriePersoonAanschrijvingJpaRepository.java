/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonAanschrijvingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAanschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAanschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersIds.
 */
@Repository("historiePersoonAanschrijvingRepository")
public class HistoriePersoonAanschrijvingJpaRepository
        extends AbstractGroepFormeleHistorieRepository<PersoonModel, HisPersoonAanschrijvingModel>
{


    @Override
    protected HisPersoonAanschrijvingModel maakNieuwHistorieRecord(final PersoonModel objectType) {
        return new HisPersoonAanschrijvingModel(objectType, objectType.getAanschrijving());
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonAanschrijvingModel> getCLaagDomainClass() {
        return HisPersoonAanschrijvingModel.class;
    }


}
