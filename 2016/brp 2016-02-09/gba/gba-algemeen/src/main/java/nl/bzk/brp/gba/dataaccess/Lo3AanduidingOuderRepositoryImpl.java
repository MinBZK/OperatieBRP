/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.verconv.LO3AanduidingOuderModel;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de Lo3AanduidingOuderRepository.
 */
@Repository("gbaLo3AanduidingOuderRepository")
public class Lo3AanduidingOuderRepositoryImpl implements Lo3AanduidingOuderRepository {

    private static final Logger LOG = LoggerFactory.getLogger();

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Override
    public final LO3SoortAanduidingOuder getOuderIdentificatie(final Integer ouderBetrokkenheidId) {
        LO3SoortAanduidingOuder result;
        try {
            final LO3AanduidingOuderModel dbResult =
                    (LO3AanduidingOuderModel) em.createNamedQuery("LO3AanduidingOuderModel.findByOuderBetrokkenheidId")
                                                .setParameter("betrokkenheidId", ouderBetrokkenheidId)
                                                .getSingleResult();
            if (dbResult.getSoort() != null) {
                LOG.info("Aanduiding ouder gevuld");
                result = dbResult.getSoort().getWaarde();
            } else {
                LOG.info("Aanduiding ouder Niet gevuld");
                result = null;
            }
        } catch (final NoResultException nre) {
            LOG.info("Aanduiding niet gevonden");
            result = null;
        }
        return result;
    }

    @Override
    public final void setAanduidingOuderBijOuderBetrokkenheid(final Integer ouderBetrokkenheidId, final LO3SoortAanduidingOuder aanduidingOuder) {
        final OuderModel ouderModel = em.getReference(OuderModel.class, ouderBetrokkenheidId);
        final LO3AanduidingOuderModel aanduidingOuderModel =
                new LO3AanduidingOuderModel(ouderModel, new LO3SoortAanduidingOuderAttribuut(aanduidingOuder));
        em.persist(aanduidingOuderModel);
    }

}
