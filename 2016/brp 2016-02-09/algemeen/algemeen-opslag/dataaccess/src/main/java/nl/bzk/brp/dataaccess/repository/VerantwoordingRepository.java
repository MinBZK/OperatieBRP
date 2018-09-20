/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;

/**
 * Repository voor entities die onderdeel uit maken van verantwoording in de BRP. Zoals Document en Rechtsgrond
 * (bronnen).
 */
public interface VerantwoordingRepository {

    /**
     * Sla een document op inclusief history.
     *
     * @param documentHisVolledig het op te slaan document
     * @return opgeslagen document uit de database.
     */
    DocumentModel slaDocumentOp(final DocumentHisVolledigImpl documentHisVolledig);

    /**
     * Sla een link op tussen een actie en een bron.
     *
     * @param actie de actie
     * @param document bron, in dit geval een document
     */
    void slaActieBronOp(final ActieModel actie, final DocumentModel document);

    /**
     * Haal een document op.
     *
     * @param id database id van het op te halen document
     * @return Document uit de database
     */
    DocumentModel haalDocumentOp(final Long id);

}
