/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("historieDocumentStandaardRepository")
public class HistorieDocumentStandaardJpaRepository
        extends AbstractGroepFormeleHistorieRepository<DocumentModel, HisDocumentModel>
{
    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "document";
    }

    @Override
    protected Class<HisDocumentModel> getCLaagDomainClass() {
        return HisDocumentModel.class;
    }

    @Override
    protected HisDocumentModel maakNieuwHistorieRecord(final DocumentModel objectType) {
        return new HisDocumentModel(objectType, objectType.getStandaard());
    }


}
