/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.dataaccess.repository.historie.HistorieHuwelijkGeregistreerdPartnerschapRepository;
import nl.bzk.brp.model.operationeel.kern.HisHuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import org.springframework.stereotype.Repository;


/**
 * JPA repository voor de tabel his_relatie.
 */
@Repository("historieHuwelijkGeregistreerdPartnerschapRepository")
public class HistorieHuwelijkGeregistreerdPartnerschapJpaRepository extends
        AbstractGroepFormeleHistorieRepository<HuwelijkGeregistreerdPartnerschapModel, HisHuwelijkGeregistreerdPartnerschapModel> implements
        HistorieHuwelijkGeregistreerdPartnerschapRepository
{

    @Override
    protected HisHuwelijkGeregistreerdPartnerschapModel maakNieuwHistorieRecord(final HuwelijkGeregistreerdPartnerschapModel objectType) {
        return new HisHuwelijkGeregistreerdPartnerschapModel(objectType, objectType.getStandaard());
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "relatie";
    }

    @Override
    protected Class<HisHuwelijkGeregistreerdPartnerschapModel> getCLaagDomainClass() {
        return HisHuwelijkGeregistreerdPartnerschapModel.class;
    }

}
