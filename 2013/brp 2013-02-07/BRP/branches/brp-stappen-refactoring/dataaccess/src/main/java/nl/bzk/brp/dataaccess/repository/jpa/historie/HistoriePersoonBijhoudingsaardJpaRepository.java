/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsaardModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("historiePersoonBijhoudingsaardRepository")
public class HistoriePersoonBijhoudingsaardJpaRepository
        extends AbstractGroepMaterieleHistorieRepository<PersoonModel,
        PersoonBijhoudingsaardGroepBasis,
        HisPersoonBijhoudingsaardModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonBijhoudingsaardModel> getCLaagDomainClass() {
        return HisPersoonBijhoudingsaardModel.class;
    }

    @Override
    protected HisPersoonBijhoudingsaardModel maakNieuwHistorieRecord(final PersoonModel objectType,
                                                                                     final PersoonBijhoudingsaardGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonBijhoudingsaardModel(objectType,
                                                                      objectType.getBijhoudingsaard());
        } else {
            return new HisPersoonBijhoudingsaardModel(
                    objectType, (PersoonBijhoudingsaardGroepModel) groep);
        }
    }
}
