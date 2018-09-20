/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonIdentificatienummersGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIdentificatienummersGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersIds.
 */
@Repository("historiePersoonIdentificatienummersRepository")
public class HistoriePersoonIdentificatienummersJpaRepository
        extends AbstractGroepMaterieleHistorieRepository<PersoonModel,
        PersoonIdentificatienummersGroepBasis,
        HisPersoonIdentificatienummersModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonIdentificatienummersModel> getCLaagDomainClass() {
        return HisPersoonIdentificatienummersModel.class;
    }

    @Override
    protected HisPersoonIdentificatienummersModel maakNieuwHistorieRecord(final PersoonModel objectType,
                                                                          final PersoonIdentificatienummersGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonIdentificatienummersModel(objectType, objectType.getIdentificatienummers());
        } else {
            return new HisPersoonIdentificatienummersModel(objectType, (PersoonIdentificatienummersGroepModel) groep);
        }
    }
}
