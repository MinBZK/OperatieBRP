/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.dataaccess.repository.historie.HistorieOuderOuderlijkGezagRepository;
import nl.bzk.brp.model.logisch.kern.basis.OuderOuderlijkGezagGroepBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel;
import org.springframework.stereotype.Repository;


/**
 * JPA repository voor de tabel his_betrouderlijkgezag.
 */
@Repository("historieOuderOuderlijkGezagRepository")
public class HistorieOuderOuderlijkGezagJpaRepository extends
        AbstractGroepMaterieleHistorieRepository<OuderModel, OuderOuderlijkGezagGroepBasis,
                        HisOuderOuderlijkGezagModel> implements HistorieOuderOuderlijkGezagRepository
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "betrokkenheid";
    }

    @Override
    protected Class<HisOuderOuderlijkGezagModel> getCLaagDomainClass() {
        return HisOuderOuderlijkGezagModel.class;
    }

    @Override
    protected HisOuderOuderlijkGezagModel maakNieuwHistorieRecord(final OuderModel objectType,
                                                                  final OuderOuderlijkGezagGroepBasis groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new HisOuderOuderlijkGezagModel(objectType, objectType.getOuderlijkGezag());
        } else {
            return new HisOuderOuderlijkGezagModel(objectType, (OuderOuderlijkGezagGroepModel) groep);
        }
    }
}
