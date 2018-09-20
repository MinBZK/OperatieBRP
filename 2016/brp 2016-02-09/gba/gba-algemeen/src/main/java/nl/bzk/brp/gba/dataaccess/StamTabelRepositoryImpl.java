/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de StamtabelRepository interface.
 */
@Repository("gbaStamTabelRepository")
public class StamTabelRepositoryImpl implements StamTabelRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager em;

    @Override
    public final Collection<Gemeente> findAllGemeente() {
        return em.createQuery("from Gemeente", Gemeente.class).getResultList();
    }

    @Override
    public final Collection<Short> findAllGemeenteCodes() {
        return em.createQuery("select code.waarde from Gemeente", Short.class).getResultList();
    }

    @Override
    public final Collection<Short> findAllLandCodes() {
        return em.createQuery("select code.waarde from LandGebied", Short.class).getResultList();
    }

    @Override
    public final Collection<Short> findAllNationaliteitCodes() {
        return em.createQuery("select code.waarde from Nationaliteit", Short.class).getResultList();
    }

    @Override
    public final List<AanduidingVerblijfsrecht> findAllAanduidingVerblijfsrecht() {
        return em.createQuery("FROM AanduidingVerblijfsrecht", AanduidingVerblijfsrecht.class).getResultList();
    }

    @Override
    public final List<RedenVerkrijgingNLNationaliteit> findAllVerkrijgingNLNationaliteit() {
        return em.createQuery("FROM RedenVerkrijgingNLNationaliteit", RedenVerkrijgingNLNationaliteit.class).getResultList();
    }

    @Override
    public final List<RedenVerliesNLNationaliteit> findAllVerliesNLNationaliteit() {
        return em.createQuery("FROM RedenVerliesNLNationaliteit", RedenVerliesNLNationaliteit.class).getResultList();
    }

    @Override
    public final List<Plaats> findAllPlaats() {
        return em.createQuery("FROM Plaats", Plaats.class).getResultList();
    }
}
