/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitStandaardGroepModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("historiePersoonNationaliteitStandaardRepository")
public class HistoriePersoonNationaliteitStandaardJpaRepository
        extends AbstractGroepMaterieleHistorieRepository<PersoonNationaliteitModel,
        PersoonNationaliteitStandaardGroepBasis, HisPersoonNationaliteitModel>
{
    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoonNationaliteit";
    }

    @Override
    protected Class<HisPersoonNationaliteitModel> getCLaagDomainClass() {
        return HisPersoonNationaliteitModel.class;
    }

    @Override
    protected HisPersoonNationaliteitModel maakNieuwHistorieRecord(final PersoonNationaliteitModel objectType,
                                                                   final PersoonNationaliteitStandaardGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonNationaliteitModel(objectType, objectType.getStandaard());
        } else {
            return new HisPersoonNationaliteitModel(objectType, (PersoonNationaliteitStandaardGroepModel) groep);
        }
    }
}
