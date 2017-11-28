/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DelegateEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.bijhouding.dal.PersoonCacheRepository;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de {@link nl.bzk.brp.bijhouding.dal.PersoonRepository} interface.
 */
@Repository
public final class PersoonRepositoryImpl extends AbstractKernRepository<Persoon, Long> implements PersoonRepository {

    private static final String SOORT_PERSOON_ID = "soortPersoonId";
    private static final String BSN = "burgerservicenummer";
    private static final String ANR = "administratienummer";

    private final PersoonCacheRepository persoonCacheRepository;

    /**
     * Maakt een nieuw PersoonRepositoryImpl object.
     * @param persoonCacheRepository persoon cache repository
     */
    @Inject
    public PersoonRepositoryImpl(final PersoonCacheRepository persoonCacheRepository) {
        super(Persoon.class);
        this.persoonCacheRepository = persoonCacheRepository;
    }

    @Override
    public boolean isPersoonGeblokkeerd(final Persoon persoon) {
        final Object result =
                getEntityManager().createNamedQuery("Persoon.isPersoonGeblokkeerd")
                        .setParameter("anummer", persoon.getAdministratienummer())
                        .getSingleResult();
        return (Boolean) result;
    }

    @Override
    public List<Persoon> zoekGerelateerdePseudoPersonen(final String administratienummer, final String burgerservicenummer) {
        if (administratienummer == null || burgerservicenummer == null) {
            return Collections.emptyList();
        }
        return getEntityManager().createNamedQuery("Persoon.zoekGerelateerdePseudoPersonen", Persoon.class)
                .setParameter(BSN, burgerservicenummer)
                .setParameter(ANR, administratienummer)
                .setParameter(SOORT_PERSOON_ID, SoortPersoon.PSEUDO_PERSOON.getId())
                .getResultList();
    }

    @Override
    public boolean komtBsnReedsVoor(final String burgerservicenummer) {
        final List<Persoon> resultList = getEntityManager().createNamedQuery("Persoon.zoekPersonenMetDezelfdeBsn", Persoon.class)
                .setParameter(BSN, burgerservicenummer)
                .setParameter("nadereBijhoudingsaardFoutId", NadereBijhoudingsaard.FOUT.getId())
                .setParameter("nadereBijhoudingsaardGewistId", NadereBijhoudingsaard.GEWIST.getId())
                .setParameter(SOORT_PERSOON_ID, SoortPersoon.INGESCHREVENE.getId())
                .getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public boolean komtAdministratienummerReedsVoor(final String administratieNummer) {
        final List<Persoon> resultList = getEntityManager().createNamedQuery("Persoon.zoekPersonenMetHetzelfdeAdministratienummer", Persoon.class)
                .setParameter(ANR, administratieNummer)
                .setParameter("nadereBijhoudingsaardFoutId", NadereBijhoudingsaard.FOUT.getId())
                .setParameter("nadereBijhoudingsaardGewistId", NadereBijhoudingsaard.GEWIST.getId())
                .setParameter(SOORT_PERSOON_ID, SoortPersoon.INGESCHREVENE.getId())
                .getResultList();
        return !resultList.isEmpty();
    }

    @Override
    public void slaPersoonOp(final Persoon persoon) {
        if (persoon instanceof DelegateEntiteit && !((DelegateEntiteit) persoon).isReadOnly()) {
            ((DelegateEntiteit<Persoon>) persoon).getDelegates().forEach(this::savePersoon);
        } else {
            savePersoon(persoon);
        }
    }

    private void savePersoon(final Persoon persoon) {
        ALaagAfleidingsUtil.vulALaag(persoon);
        if (persoon.getId() == null) {
            getEntityManager().persist(persoon);
        } else {
            //Voor de cache hebben we een opgeslagen persoon nodig zodat @versie is bijgewerkt
            getEntityManager().flush();
        }
        persoonCacheRepository.slaPersoonCacheOp(persoon);
    }
}
