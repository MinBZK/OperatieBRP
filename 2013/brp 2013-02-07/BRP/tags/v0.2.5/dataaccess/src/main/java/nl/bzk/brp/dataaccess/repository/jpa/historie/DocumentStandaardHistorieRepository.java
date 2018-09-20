/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.DocumentStandaardHisModel;
import nl.bzk.brp.model.objecttype.operationeel.DocumentModel;
import org.springframework.stereotype.Repository;

/**
 * JPA repository voor de tabel His_PersBijhVerantw.
 */
@Repository("documentStandaardHistorieRepository")
public class DocumentStandaardHistorieRepository
    extends AbstractGroepFormeleHistorieRepository<DocumentModel, DocumentStandaardHisModel>
{
    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "document";
    }

    @Override
    protected Class<DocumentStandaardHisModel> getCLaagDomainClass() {
        return DocumentStandaardHisModel.class;
    }

    @Override
    protected DocumentStandaardHisModel maakNieuwHistorieRecord(final DocumentModel objectType) {
        return new DocumentStandaardHisModel(objectType.getStandaard(), objectType);
    }


}
