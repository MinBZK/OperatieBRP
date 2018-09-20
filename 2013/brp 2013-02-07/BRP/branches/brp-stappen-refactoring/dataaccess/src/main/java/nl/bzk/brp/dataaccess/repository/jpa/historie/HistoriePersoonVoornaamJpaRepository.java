/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonVoornaamStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamStandaardGroepModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonVoornaamModel} class.
 */
@Repository("historiePersoonVoornaamRepository")
public class HistoriePersoonVoornaamJpaRepository extends
        AbstractGroepMaterieleHistorieRepository<PersoonVoornaamModel, PersoonVoornaamStandaardGroepBasis,
                HisPersoonVoornaamModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonVoornaam";
    }

    @Override
    protected Class<HisPersoonVoornaamModel> getCLaagDomainClass() {
        return HisPersoonVoornaamModel.class;
    }

    @Override
    protected HisPersoonVoornaamModel maakNieuwHistorieRecord(
            final PersoonVoornaamModel objectType, final PersoonVoornaamStandaardGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonVoornaamModel(objectType, objectType.getStandaard());
        } else {
            return new HisPersoonVoornaamModel(objectType, (PersoonVoornaamStandaardGroepModel) groep);
        }
    }


}
