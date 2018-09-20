/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsaanduidingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersGeslachtsAand.
 */
@Repository("historiePersoonGeslachtsaanduidingRepository")
public class HistoriePersoonGeslachtsaanduidingJpaRepository
        extends
        AbstractGroepMaterieleHistorieRepository<PersoonModel, PersoonGeslachtsaanduidingGroepBasis, HisPersoonGeslachtsaanduidingModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonGeslachtsaanduidingModel> getCLaagDomainClass() {
        return HisPersoonGeslachtsaanduidingModel.class;
    }

    @Override
    protected HisPersoonGeslachtsaanduidingModel maakNieuwHistorieRecord(final PersoonModel objectType,
                                                                         final PersoonGeslachtsaanduidingGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonGeslachtsaanduidingModel(objectType, objectType.getGeslachtsaanduiding());
        } else {
            return new HisPersoonGeslachtsaanduidingModel(objectType, (PersoonGeslachtsaanduidingGroepModel) groep);
        }
    }
}
