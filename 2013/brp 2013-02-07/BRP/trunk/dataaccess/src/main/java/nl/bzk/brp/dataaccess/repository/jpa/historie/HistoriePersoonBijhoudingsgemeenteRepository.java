/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsgemeenteGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsgemeenteModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.springframework.stereotype.Repository;


/**
 * JPA repository voor de tabel his_persbijhgem.
 */
@Repository("historiePersoonBijhoudingsgemeenteRepository")
public class HistoriePersoonBijhoudingsgemeenteRepository extends
        AbstractGroepMaterieleHistorieRepository<PersoonModel, PersoonBijhoudingsgemeenteGroepBasis,
                HisPersoonBijhoudingsgemeenteModel>
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "persoon";
    }

    @Override
    protected Class<HisPersoonBijhoudingsgemeenteModel> getCLaagDomainClass() {
        return HisPersoonBijhoudingsgemeenteModel.class;
    }

    @Override
    protected HisPersoonBijhoudingsgemeenteModel maakNieuwHistorieRecord(final PersoonModel objectType,
                                                                         final PersoonBijhoudingsgemeenteGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisPersoonBijhoudingsgemeenteModel(objectType, objectType.getBijhoudingsgemeente());
        } else {
            return new HisPersoonBijhoudingsgemeenteModel(objectType, (PersoonBijhoudingsgemeenteGroepModel) groep);
        }
    }
}
