/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.DocumentHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.VerantwoordingRepository;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;

import org.springframework.stereotype.Repository;

/**
 * Repository voor entities die onderdeel uit maken van verantwoording in de BRP. Zoals Document en Rechtsgrond
 * (bronnen).
 */
@Repository
public final class VerantwoordingRepositoryImpl implements VerantwoordingRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Inject
    private DocumentHisVolledigRepository documentHisVolledigRepository;

    @Override
    public DocumentModel slaDocumentOp(final DocumentHisVolledigImpl documentHisVolledig) {
        documentHisVolledigRepository.saveAndFlush(documentHisVolledig);
        return em.find(DocumentModel.class, documentHisVolledig.getID());
    }

    @Override
    public void slaActieBronOp(final ActieModel actie, final DocumentModel document) {
        final ActieBronModel actieBron = new ActieBronModel(actie, document, null, null);
        //Zet de actiebron in de actie bronnen, omdat hiermee verder gewerkt wordt in bedrijfsregels.
        //M.a.w. de andere kant van de associatie goed zetten omdat die kant gebruikt wordt in BR's.
        actie.getBronnen().add(actieBron);
        em.persist(actieBron);
    }

    @Override
    public DocumentModel haalDocumentOp(final Long id) {
        return em.find(DocumentModel.class, id);
    }
}
