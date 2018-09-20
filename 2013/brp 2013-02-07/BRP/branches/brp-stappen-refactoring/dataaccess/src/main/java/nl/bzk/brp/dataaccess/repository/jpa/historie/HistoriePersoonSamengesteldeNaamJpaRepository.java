/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonSamengesteldeNaamGroepModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersSamengesteldeNaam.
 */
@Repository("historiePersoonSamengesteldeNaamRepository")
public class HistoriePersoonSamengesteldeNaamJpaRepository
        extends AbstractGroepMaterieleHistorieRepository<PersoonModel, PersoonSamengesteldeNaamGroepBasis,
        HisPersoonSamengesteldeNaamModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonSamengesteldeNaamModel> getCLaagDomainClass() {
        return HisPersoonSamengesteldeNaamModel.class;
    }

    @Override
    protected HisPersoonSamengesteldeNaamModel maakNieuwHistorieRecord(
            final PersoonModel objectType, final PersoonSamengesteldeNaamGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonSamengesteldeNaamModel(objectType, objectType.getSamengesteldeNaam());
        } else {
            return new HisPersoonSamengesteldeNaamModel(objectType, (PersoonSamengesteldeNaamGroepModel) groep);
        }
    }
}
