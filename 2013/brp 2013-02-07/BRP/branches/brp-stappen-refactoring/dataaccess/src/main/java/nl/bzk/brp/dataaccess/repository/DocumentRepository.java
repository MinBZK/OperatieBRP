/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import org.springframework.data.jpa.repository.JpaRepository;

/** JPA Repository voor entity Document Model. */
public interface DocumentRepository extends JpaRepository<DocumentModel, Integer> {

//    /**
//     * Zoek alle documenten die gerefereerd worden voor een specifiek actie.
//     * @param actieId de actie id.
//     * @return een lijst van documenten (of leeg)
//     */
//    List<DocumentModel> findDocumentByActie(final Long actieId);
//
//    /**
//     * Haal op een document aan de hand van de id.
//     * @param id de id van het document.
//     * @return een documenten (of null)
//     */
//    DocumentModel findDocumentById(final Long id);
//
//    /**
//     * Sla een document op die bij deze actie hoort.
//     * @param document het document
//     * @param actie de actie
//     * @param datumAanvagGeldigheid de datum aanvang geldigheid
//     */
//    void opslaanNieuwDocument(final DocumentModel document, final ActieModel actie,
// final Datum datumAanvagGeldigheid);
}
