/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import org.springframework.stereotype.Repository;

/**
 * Created by operatiebrp on 21/10/15.
 */
@Repository
public interface DocumentModelRepository {


    /**
     * Slaat nieuw DocumentModel op.
     *
     * @param documentModel DocumentModel dat moet worden gepersisteerd.
     * @return gepersisteerde onderzoek.
     */
    DocumentModel slaNieuwDocumentModel(DocumentModel documentModel);
}
