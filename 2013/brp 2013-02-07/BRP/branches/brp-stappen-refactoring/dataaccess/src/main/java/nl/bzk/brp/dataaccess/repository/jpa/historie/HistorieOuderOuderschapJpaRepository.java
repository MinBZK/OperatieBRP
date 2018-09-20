/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.dataaccess.repository.historie.HistorieOuderOuderschapRepository;
import nl.bzk.brp.model.logisch.kern.basis.OuderOuderschapGroepBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderschapGroepModel;
import org.springframework.stereotype.Repository;


/** JPA repository voor de tabel his_betrouderschap. */
@Repository("historieOuderOuderschapRepository")
public class HistorieOuderOuderschapJpaRepository extends
        AbstractGroepMaterieleHistorieRepository<OuderModel, OuderOuderschapGroepBasis,
                    HisOuderOuderschapModel> implements HistorieOuderOuderschapRepository
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "betrokkenheid";
    }

    @Override
    protected Class<HisOuderOuderschapModel> getCLaagDomainClass() {
        return HisOuderOuderschapModel.class;
    }

    @Override
    protected HisOuderOuderschapModel maakNieuwHistorieRecord(final OuderModel objectType,
        final OuderOuderschapGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisOuderOuderschapModel(objectType, objectType.getOuderschap());
        } else {
            return new HisOuderOuderschapModel(objectType, (OuderOuderschapGroepModel) groep);
        }
    }
}
