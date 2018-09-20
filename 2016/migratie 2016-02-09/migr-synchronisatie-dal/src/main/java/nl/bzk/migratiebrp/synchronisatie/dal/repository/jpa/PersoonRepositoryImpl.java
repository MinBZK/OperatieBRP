/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Melding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoonOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-personen.
 *
 * NOTA: Omdat we nog noett precies weten wat de 'technische sleutel' is van een persoon gaan we er voorlopig van uit
 * dat dat het ID is.
 */
@Repository
public final class PersoonRepositoryImpl implements PersoonRepository {
    private static final String SOORT_PERSOON_NULL_MELDING = "soortPersoon mag niet null zijn";
    private static final String ADMINISTRATIENUMMER_NULL_MELDING = "administratienummer mag niet null zijn";
    private static final String TECHNISCHE_SLEUTEL_NULL_MELDING = "technische sleutel mag niet null zijn";
    private static final String SELECT_DEEL = "SELECT p FROM Persoon p ";
    private static final String ADMINISTRATIENUMMER_PARAM = "administratienummer";
    private static final String SOORT_PERSOON_ID_PARAM = "soortPersoonId";
    private static final String OPSCHORTREDEN = "nadereBijhoudingsaard";

    // Parameters bij JPA 2.0 query. Dit moeten de property namen zijn.
    private static final String PERSOON_ADMINISTRATIENUMMER_PROPERTY = ADMINISTRATIENUMMER_PARAM;
    private static final String PERSOON_BURGERSERVICENUMMER_PROPERTY = "burgerservicenummer";
    private static final String PERSOON_GESLACHTSNAAMSTAM_PROPERTY = "geslachtsnaamstam";
    private static final String PERSOON_BIJHOUDINGSPARTIJ_PROPERTY = "bijhoudingspartij.naam";
    private static final String PERSOON_GEBOORTEDATUM_PROPERTY = "datumGeboorte";
    private static final String PERSOON_GESLACHTSAANDUIDING_PROPERTY = "geslachtsaanduidingId";
    private static final String PERSOON_VOORNAMEN_PROPERTY = "voornamen";
    private static final String PERSOON_SOORTPERSOON_PROPERTY = SOORT_PERSOON_ID_PARAM;

    private static final String PERSOON_ID_HISTORIE_ADMINISTRATIENUMMER_PROPERTY = PERSOON_ADMINISTRATIENUMMER_PROPERTY;
    private static final String PERSOON_ID_HISTORIE_BURGERSERVICENUMMER_PROPERTY = PERSOON_BURGERSERVICENUMMER_PROPERTY;
    private static final String PERSOON_ID_HISTORIE_DATUMEINDEGELDIGHEID_PROPERTY = "datumEindeGeldigheid";

    private static final String PERSOON_SAMENGESTELDENAAM_HISTORIE_GESLACHTSNAAMSTAM_PROPERTY = PERSOON_GESLACHTSNAAMSTAM_PROPERTY;
    private static final String PERSOON_SAMENGESTELDENAAM_HISTORIE_DATUMEINDEGELDIGHEID_PROPERTY = PERSOON_ID_HISTORIE_DATUMEINDEGELDIGHEID_PROPERTY;
    private static final String PERSOON = "persoon";

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persoon> findByAdministratienummer(
        final long administratienummer,
        final SoortPersoon soortPersoon,
        final boolean indicatieFoutiefOpgeschortUitsluiten)
    {
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }

        final String standaardQuery = SELECT_DEEL + "where p.administratienummer = :administratienummer " + " and p.soortPersoonId = :soortPersoonId";

        if (indicatieFoutiefOpgeschortUitsluiten) {
            return em.createQuery(standaardQuery + " and not(p.nadereBijhoudingsaard = :nadereBijhoudingsaard) ", Persoon.class)
                     .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                     .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                     .setParameter(OPSCHORTREDEN, NadereBijhoudingsaard.FOUT.getId())
                     .getResultList();

        } else {
            return em.createQuery(standaardQuery, Persoon.class)
                     .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                     .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                     .getResultList();
        }
    }

    @Override
    public List<Persoon> findByTechnischeSleutel(final Long technischeSleutel, final SoortPersoon soortPersoon) {
        if (technischeSleutel == null) {
            throw new NullPointerException(TECHNISCHE_SLEUTEL_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }

        return em.createQuery(SELECT_DEEL + "where p.id = :technischeSleutel " + "and p.soortPersoonId = :soortPersoonId ", Persoon.class)
                 .setParameter("technischeSleutel", technischeSleutel)
                 .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                 .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persoon> findByAdministratienummerHistorisch(final Long administratienummer, final SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                     "select distinct his.persoon from PersoonIDHistorie his "
                             + "where his.administratienummer = :administratienummer "
                             + "and his.datumEindeGeldigheid is not null and his.datumTijdVerval is null "
                             + "and his.persoon.soortPersoonId = :soortPersoonId",
                     Persoon.class)
                 .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                 .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                 .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Onderzoek> findOnderzoekenVoorPersoon(final Persoon persoon) {
        if (persoon == null) {
            throw new NullPointerException("persoon mag niet null zijn");
        }

        return em.createQuery(
                     "select distinct o from Onderzoek o, in(o.persoonOnderzoekSet) as po "
                             + "where po.persoon = :persoon "
                             + "and po.soortPersoonOnderzoekId = :soortPersoonOnderzoekId",
                     Onderzoek.class)
                 .setParameter(PERSOON, persoon)
                 .setParameter("soortPersoonOnderzoekId", SoortPersoonOnderzoek.DIRECT.getId())
                 .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach(final Persoon persoon) {
        em.detach(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final Persoon persoon) {
        em.remove(persoon);
        for (final Lo3Bericht lo3Bericht : persoon.getLo3Berichten()) {
            removeLo3Bericht(lo3Bericht);
        }
    }

    private void removeLo3Bericht(final Lo3Bericht lo3Bericht) {
        final Set<Lo3Melding> meldingen = new HashSet<>();
        for (final Lo3Voorkomen voorkomen : lo3Bericht.getVoorkomens()) {
            meldingen.addAll(voorkomen.getMeldingen());
        }
        final Set<Lo3Voorkomen> voorkomens = new HashSet<>();
        voorkomens.addAll(lo3Bericht.getVoorkomens());

        for (final Lo3Melding melding : meldingen) {
            em.remove(melding);
        }
        for (final Lo3Voorkomen voorkomen : voorkomens) {
            em.remove(voorkomen);
        }
        em.remove(lo3Bericht);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final Persoon persoon) {
        if (persoon.getId() == null) {
            em.persist(persoon);
        } else {
            em.flush();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCache(final Persoon persoon) {
        em.createQuery("delete from PersoonCache pc where pc.persoon = :persoon").setParameter(PERSOON, persoon).executeUpdate();
    }

    @Override
    public List<Persoon> zoekPersoon(final Persoon persoon) {

        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Persoon> criteria = builder.createQuery(Persoon.class);
        final Root<Persoon> persoonRoot = criteria.from(Persoon.class);
        criteria.select(persoonRoot);

        criteria.where(maakZoekPersoonWhereCondities(persoon, builder, persoonRoot));

        return em.createQuery(criteria).getResultList();

    }

    private Predicate[] maakZoekPersoonWhereCondities(final Persoon persoon, final CriteriaBuilder builder, final Root<Persoon> persoonRoot) {
        final Set<Predicate> whereCondities = new HashSet<>();

        if (persoon.getAdministratienummer() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_ADMINISTRATIENUMMER_PROPERTY), persoon.getAdministratienummer()));
        }

        if (persoon.getBijhoudingspartij() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_BIJHOUDINGSPARTIJ_PROPERTY), persoon.getBijhoudingspartij().getNaam()));
        }

        if (persoon.getBurgerservicenummer() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_BURGERSERVICENUMMER_PROPERTY), persoon.getBurgerservicenummer()));
        }

        if (persoon.getDatumGeboorte() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_GEBOORTEDATUM_PROPERTY), persoon.getDatumGeboorte()));
        }

        if (persoon.getGeslachtsaanduiding() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_GESLACHTSAANDUIDING_PROPERTY), persoon.getGeslachtsaanduiding().getId()));
        }

        if (persoon.getGeslachtsnaamstam() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_GESLACHTSNAAMSTAM_PROPERTY), persoon.getGeslachtsnaamstam()));
        }

        if (persoon.getVoornamen() != null) {
            whereCondities.add(builder.equal(persoonRoot.get(PERSOON_VOORNAMEN_PROPERTY), persoon.getVoornamen()));
        }

        return whereCondities.toArray(new Predicate[whereCondities.size()]);
    }

    @Override
    public List<Persoon> zoekPersonenOpActueleGegevens(
        final Long administratienummer,
        final Integer burgerservicenummer,
        final String geslachtsnaamstam,
        final String postcode)
    {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Persoon> persoonQuery = builder.createQuery(Persoon.class);
        final Root<Persoon> persoonRoot = persoonQuery.from(Persoon.class);
        persoonQuery.select(persoonRoot);

        final List<Predicate> restrictions = new ArrayList<>();
        if (administratienummer != null) {
            restrictions.add(builder.equal(persoonRoot.get(PERSOON_ADMINISTRATIENUMMER_PROPERTY), administratienummer));
        }
        if (burgerservicenummer != null) {
            restrictions.add(builder.equal(persoonRoot.get(PERSOON_BURGERSERVICENUMMER_PROPERTY), burgerservicenummer));
        }
        if (geslachtsnaamstam != null && !"".equals(geslachtsnaamstam)) {
            restrictions.add(builder.equal(persoonRoot.get(PERSOON_GESLACHTSNAAMSTAM_PROPERTY), geslachtsnaamstam));
        }
        if (postcode != null && !"".equals(postcode)) {
            // "id in (select pers from PersAdres where postcode = ?)"
            final Subquery<Persoon> persoonAdresSubquery = persoonQuery.subquery(Persoon.class);
            final Root<PersoonAdres> persoonAdresRoot = persoonAdresSubquery.from(PersoonAdres.class);
            persoonAdresSubquery.select(persoonAdresRoot.get(PERSOON).as(Persoon.class));
            persoonAdresSubquery.where(builder.equal(persoonAdresRoot.get("postcode"), postcode));

            restrictions.add(builder.in(persoonRoot).value(persoonAdresSubquery));
        }

        if (restrictions.isEmpty()) {
            throw new IllegalArgumentException("Minimaal een van a-nummer, bsn, geslachtsnaam of postcode moet gebruikt worden om te zoeken.");
        }

        // Default restrictie (type persoon = INGESCHREVENE)
        restrictions.add(builder.equal(persoonRoot.get(PERSOON_SOORTPERSOON_PROPERTY), SoortPersoon.INGESCHREVENE.getId()));

        persoonQuery.where(restrictions.toArray(new Predicate[restrictions.size()]));
        return em.createQuery(persoonQuery).getResultList();
    }

    @Override
    public List<Persoon> zoekPersonenOpHistorischeGegevens(
        final Long administratienummer,
        final Integer burgerservicenummer,
        final String geslachtsnaamstam)
    {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Persoon> persoonQuery = builder.createQuery(Persoon.class);
        final Root<Persoon> persoonRoot = persoonQuery.from(Persoon.class);
        persoonQuery.select(persoonRoot);

        final List<Predicate> restrictions = new ArrayList<>();
        if (administratienummer != null || burgerservicenummer != null) {
            // "id in (select pers from his_persids where anr = ? and bsn = ?)"
            final Subquery<Persoon> persoonIdHistorieSubquery = persoonQuery.subquery(Persoon.class);
            final Root<PersoonIDHistorie> persoonIdHistorieRoot = persoonIdHistorieSubquery.from(PersoonIDHistorie.class);
            persoonIdHistorieSubquery.select(persoonIdHistorieRoot.get(PERSOON).as(Persoon.class));

            final List<Predicate> subqueryRestrictions = new ArrayList<>();
            if (administratienummer != null) {
                subqueryRestrictions.add(builder.equal(persoonIdHistorieRoot.get(PERSOON_ID_HISTORIE_ADMINISTRATIENUMMER_PROPERTY), administratienummer));
            }
            if (burgerservicenummer != null) {
                subqueryRestrictions.add(builder.equal(persoonIdHistorieRoot.get(PERSOON_ID_HISTORIE_BURGERSERVICENUMMER_PROPERTY), burgerservicenummer));
            }
            subqueryRestrictions.add(builder.isNotNull(persoonIdHistorieRoot.get(PERSOON_ID_HISTORIE_DATUMEINDEGELDIGHEID_PROPERTY)));

            persoonIdHistorieSubquery.where(subqueryRestrictions.toArray(new Predicate[subqueryRestrictions.size()]));

            restrictions.add(builder.in(persoonRoot).value(persoonIdHistorieSubquery));
        }

        if (geslachtsnaamstam != null && !"".equals(geslachtsnaamstam)) {
            // "id in (select pers from his_perssamengesteldenaam where geslnaamstam = ?"
            final Subquery<Persoon> persoonNaamSubquery = persoonQuery.subquery(Persoon.class);
            final Root<PersoonSamengesteldeNaamHistorie> persoonNaamRoot = persoonNaamSubquery.from(PersoonSamengesteldeNaamHistorie.class);
            persoonNaamSubquery.select(persoonNaamRoot.get(PERSOON).as(Persoon.class));

            final List<Predicate> subqueryRestrictions = new ArrayList<>();
            subqueryRestrictions.add(builder.equal(persoonNaamRoot.get(PERSOON_SAMENGESTELDENAAM_HISTORIE_GESLACHTSNAAMSTAM_PROPERTY), geslachtsnaamstam));
            subqueryRestrictions.add(builder.isNotNull(persoonNaamRoot.get(PERSOON_SAMENGESTELDENAAM_HISTORIE_DATUMEINDEGELDIGHEID_PROPERTY)));

            persoonNaamSubquery.where(subqueryRestrictions.toArray(new Predicate[subqueryRestrictions.size()]));

            restrictions.add(builder.in(persoonRoot).value(persoonNaamSubquery));
        }

        if (restrictions.isEmpty()) {
            throw new IllegalArgumentException("Minimaal een van a-nummer, bsn of geslachtsnaam moet gebruikt worden om te zoeken.");
        }

        // Default restrictie (type persoon = INGESCHREVENE)
        restrictions.add(builder.equal(persoonRoot.get(PERSOON_SOORTPERSOON_PROPERTY), SoortPersoon.INGESCHREVENE.getId()));

        persoonQuery.where(restrictions.toArray(new Predicate[restrictions.size()]));
        return em.createQuery(persoonQuery).getResultList();

    }
}
