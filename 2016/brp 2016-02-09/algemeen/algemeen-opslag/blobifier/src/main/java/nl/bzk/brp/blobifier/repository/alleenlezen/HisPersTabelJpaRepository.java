/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

import org.springframework.stereotype.Repository;


/**
 * JPA implementatie van {@link HisPersTabelRepository}.
 */
@Repository
public final class HisPersTabelJpaRepository implements HisPersTabelRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String BESTAAT_PERSOON_SQL          = "SELECT 1 FROM kern.pers p WHERE p.id = :persoonId";
    private static final String GEEN_PERSOON_GEVONDEN_MET_ID = "Geen persoon gevonden met id: ";

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager emAlleenLezen;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager emLezenSchrijven;

    @Override
    public PersoonHisVolledigImpl leesGenormalizeerdModelVoorInMemoryBlob(final Integer id) {
        return leesGenormalizeerdModel(id, emAlleenLezen);
    }

    @Override
    public PersoonHisVolledigImpl leesGenormalizeerdModelVoorNieuweBlob(final Integer id) {
        return leesGenormalizeerdModel(id, emLezenSchrijven);
    }

    @Override
    public Integer zoekIdBijBSN(final BurgerservicenummerAttribuut bsn) throws NietUniekeBsnExceptie {
        final TypedQuery<Integer> tQuery =
            emAlleenLezen
                .createQuery(
                    "SELECT id FROM PersoonModel persoon WHERE persoon.identificatienummers.burgerservicenummer = :burgerservicenummer",
                    Integer.class);
        tQuery.setParameter("burgerservicenummer", bsn);


        try {
            return tQuery.getSingleResult();
        } catch (final NoResultException e) {
            throw new PersoonNietAanwezigExceptie("Geen persoon gevonden met BSN: " + bsn, e);
        } catch (final NonUniqueResultException e) {
            final String foutMelding = "Meerdere personen gevonden met BSN: " + bsn;
            LOGGER.warn(foutMelding);
            throw new NietUniekeBsnExceptie(foutMelding, e);
        }
    }

    @Override
    public Integer zoekIdBijBSNVoorActievePersoon(final BurgerservicenummerAttribuut bsn) throws NietUniekeBsnExceptie {
        final Query query =
            emAlleenLezen
                .createNativeQuery(
                    "SELECT id FROM kern.pers persoon WHERE persoon.bsn = :burgerservicenummerVoorActiefPersoon AND (persoon.naderebijhaard "
                        + "IS NULL OR persoon.naderebijhaard NOT IN (7, 8))");

        query.setParameter("burgerservicenummerVoorActiefPersoon", bsn.getWaarde());
        try {

            return (Integer) query.getSingleResult();
        } catch (final NoResultException e) {
            throw new PersoonNietAanwezigExceptie("Geen actief persoon gevonden met BSN: " + bsn, e);
        } catch (final NonUniqueResultException e) {
            final String foutMelding = "Meerdere actieve personen gevonden met BSN: " + bsn;
            LOGGER.warn(foutMelding);
            throw new NietUniekeBsnExceptie(foutMelding, e);
        }
    }

    @Override
    public Integer zoekIdBijAnummer(final AdministratienummerAttribuut anr) throws NietUniekeAnummerExceptie {
        final TypedQuery<Integer> tQuery =
            emAlleenLezen.createQuery("SELECT id FROM PersoonModel persoon"
                + " WHERE persoon.identificatienummers.administratienummer = :administratienummer", Integer.class);
        tQuery.setParameter("administratienummer", anr);

        try {
            return tQuery.getSingleResult();
        } catch (final NoResultException e) {
            throw new PersoonNietAanwezigExceptie("Geen persoon gevonden met A-nummer: " + anr, e);
        } catch (final NonUniqueResultException e) {
            final String foutMelding = "Meerdere personen gevonden met A-nummer: " + anr;
            LOGGER.warn(foutMelding);
            throw new NietUniekeAnummerExceptie(foutMelding, e);
        }
    }

    @Override
    public boolean bestaatPersoonMetId(final Integer technischId) {
        final Query query = emAlleenLezen.createNativeQuery(BESTAAT_PERSOON_SQL).setParameter("persoonId", technischId);

        return query.getResultList().size() == 1;
    }

    /**
     * Leest een genormalizeerd model.
     *
     * @param id            de persoon id
     * @param entityManager de entity manager waarmee de persoon opgehaald dient te worden
     * @return de persoon his volledig impl
     */
    private PersoonHisVolledigImpl leesGenormalizeerdModel(final Integer id, final EntityManager entityManager) {
        final PersoonHisVolledigImpl persoonHisVolledig = entityManager.find(PersoonHisVolledigImpl.class, id);
        if (persoonHisVolledig != null) {
            return persoonHisVolledig;
        } else {
            throw new PersoonNietAanwezigExceptie(GEEN_PERSOON_GEVONDEN_MET_ID + id);
        }
    }
}
