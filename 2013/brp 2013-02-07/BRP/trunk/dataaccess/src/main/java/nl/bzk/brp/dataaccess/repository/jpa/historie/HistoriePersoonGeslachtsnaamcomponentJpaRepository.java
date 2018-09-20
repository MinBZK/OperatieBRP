/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentStandaardGroepModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel} class.
 */
@Repository("historiePersoonGeslachtsnaamcomponentRepository")
public class HistoriePersoonGeslachtsnaamcomponentJpaRepository
        extends AbstractGroepMaterieleHistorieRepository<PersoonGeslachtsnaamcomponentModel,
        PersoonGeslachtsnaamcomponentStandaardGroepBasis, HisPersoonGeslachtsnaamcomponentModel>
{

    @Override
    protected HisPersoonGeslachtsnaamcomponentModel maakNieuwHistorieRecord(
            final PersoonGeslachtsnaamcomponentModel objectType,
            final PersoonGeslachtsnaamcomponentStandaardGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonGeslachtsnaamcomponentModel(objectType, objectType.getStandaard());
        } else {
            return new HisPersoonGeslachtsnaamcomponentModel(objectType,
                                                             (PersoonGeslachtsnaamcomponentStandaardGroepModel) groep);
        }
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonGeslachtsnaamcomponent";
    }

    @Override
    protected Class<HisPersoonGeslachtsnaamcomponentModel> getCLaagDomainClass() {
        return HisPersoonGeslachtsnaamcomponentModel.class;
    }

}
