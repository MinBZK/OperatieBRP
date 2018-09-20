/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.historie.GroepFormeleHistorieRepository;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;


/**
 * .
 */
@Repository
public class ActieJparepository implements ActieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager                                 em;

    @Inject
    private GroepFormeleHistorieRepository<DocumentModel> historieDocumentStandaardRepository;

    @Override
    public ActieModel opslaanNieuwActie(final ActieModel actieModel) {
        em.persist(actieModel);

        // bit tricky.
        // ActieModel -> 1:n Bronnen m: 1 <-- DocumentModel (gaat alles goed)
        // maar his_doc heeft referentie naar ActieModel (formele historie = actieInhoiud en actieEinde)
        // en als we nu meteen de history aanmaken, dan is de ActieModel op dat ogenblik nog niet gesaved.
        // en we kunnen de actieModel pas saven, nadat alle documenten zijn gesaved.
        // ------------------------
        if (CollectionUtils.isNotEmpty(actieModel.getBronnen())) {
            for (ActieBronModel bron : actieModel.getBronnen()) {
                if (null != bron.getDocument()) {
                    em.persist(bron.getDocument());
                    em.persist(bron);
                }
            }
        }
        werkDocumentHistoryBij(actieModel);

        return actieModel;
    }

    /**
     * Nog een keer door lopen om de documenten de historie nu bij te houden.
     *
     * @param actieModel .
     */
    private void werkDocumentHistoryBij(final ActieModel actieModel) {
        if (CollectionUtils.isNotEmpty(actieModel.getBronnen())) {
            for (ActieBronModel bron : actieModel.getBronnen()) {
                if (null != bron.getDocument()) {
                    if (null != bron.getDocument().getStandaard()) {
                        historieDocumentStandaardRepository.persisteerHistorie(bron.getDocument(), actieModel);
                    }
                }
            }
        }
    }
}
