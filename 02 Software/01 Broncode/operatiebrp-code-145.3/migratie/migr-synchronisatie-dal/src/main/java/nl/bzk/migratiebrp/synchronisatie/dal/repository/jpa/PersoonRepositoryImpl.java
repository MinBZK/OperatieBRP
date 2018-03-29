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
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Melding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
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
    private static final String TECHNISCHE_SLEUTEL_PARAM = "technischeSleutel";
    private static final String SOORT_PERSOON_NULL_MELDING = "soortPersoon mag niet null zijn";
    private static final String ADMINISTRATIENUMMER_NULL_MELDING = "administratienummer mag niet null zijn";
    private static final String BURGERSERVICENUMMER_NULL_MELDING = "burgerservicenummer mag niet null zijn";
    private static final String TECHNISCHE_SLEUTEL_NULL_MELDING = "technische sleutel mag niet null zijn";
    private static final String SELECT_DEEL = "SELECT p FROM Persoon p ";
    private static final String ADMINISTRATIENUMMER_PARAM = "administratienummer";
    private static final String BURGERSERVICENUMMER_PARAM = "burgerservicenummer";
    private static final String SOORT_PERSOON_ID_PARAM = "soortPersoonId";

    private static final String PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID = "nadereBijhoudingsaardFoutId";
    private static final String PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID = "nadereBijhoudingsaardGewistId";

    // Parameters bij JPA 2.0 query. Dit moeten de property namen zijn.
    private static final String PERSOON_ADMINISTRATIENUMMER_PROPERTY = ADMINISTRATIENUMMER_PARAM;
    private static final String PERSOON_BURGERSERVICENUMMER_PROPERTY = BURGERSERVICENUMMER_PARAM;
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

    private static final String AND_NOT_PERSOON_OPGESCHORT =
            "and not(p.nadereBijhoudingsaardId in (:nadereBijhoudingsaardFoutId, :nadereBijhoudingsaardGewistId)) ";
    private static final String AND_NOT_HIS_PERSOON_OPGESCHORT =
            "and not(his.persoon.nadereBijhoudingsaardId in (:nadereBijhoudingsaardFoutId, :nadereBijhoudingsaardGewistId)) ";


    private static final String WHERE_CLAUSE_PERSOONCHACHE = "select pc from PersoonCache pc where pc.persoon = :persoon ";
    private static final String WHERE_CLAUSE_TECHNISCHE_SLEUTEL_EN_SOORT_PERSOON = "where p.id = :technischeSleutel and p.soortPersoonId = :soortPersoonId ";
    private static final String WHERE_CLAUSE_ANUMMER_EN_SOORT_PERSOON =
            "where p.administratienummer = :administratienummer " + " and p.soortPersoonId = :soortPersoonId ";
    private static final String AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NOT_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL =
            "and his.datumEindeGeldigheid is not null and his.datumTijdVerval is null ";
    private static final String AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID = "and his.persoon.soortPersoonId = :soortPersoonId ";
    private static final String SELECT_DISTINCT_HIS_PERSOON_FROM_PERSOON_NUMMERVERWIJZING_HISTORIE_HIS =
            "select distinct his.persoon from PersoonNummerverwijzingHistorie his ";
    private static final String AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL =
            "and his.datumEindeGeldigheid is null and his.datumTijdVerval is null ";
    private static final String WHERE_HIS_VORIGE_ADMINISTRATIENUMMER_ADMINISTRATIENUMMER = "where his.vorigeAdministratienummer = :administratienummer ";
    private static final String WHERE_HIS_VOLGENDE_ADMINISTRATIENUMMER_ADMINISTRATIENUMMER = "where his.volgendeAdministratienummer = :administratienummer ";


    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persoon> findByAdministratienummer(
            final String administratienummer,
            final SoortPersoon soortPersoon,
            final boolean indicatieFoutiefOpgeschortUitsluiten) {
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }

        if (indicatieFoutiefOpgeschortUitsluiten) {
            return em.createQuery(SELECT_DEEL
                            + WHERE_CLAUSE_ANUMMER_EN_SOORT_PERSOON
                            + AND_NOT_PERSOON_OPGESCHORT
                    , Persoon.class)
                    .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                    .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                    .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                    .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                    .getResultList();

        } else {
            return em.createQuery(SELECT_DEEL + WHERE_CLAUSE_ANUMMER_EN_SOORT_PERSOON, Persoon.class)
                    .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                    .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                    .getResultList();
        }
    }

    @Override
    public List<Persoon> findByBurgerServiceNummer(
            final String burgerServiceNummer) {

        if (burgerServiceNummer == null) {
            throw new NullPointerException(BURGERSERVICENUMMER_NULL_MELDING);
        }

        return em.createNamedQuery("Persoon.zoekPersonenMetDezelfdeBsn", Persoon.class).setParameter(PERSOON_BURGERSERVICENUMMER_PROPERTY, burgerServiceNummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, SoortPersoon.INGESCHREVENE.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();
    }

    @Override
    public List<Persoon> findByTechnischeSleutel(
            final Long technischeSleutel,
            final SoortPersoon soortPersoon,
            final boolean indicatieFoutiefOpgeschortUitsluiten) {
        if (technischeSleutel == null) {
            throw new NullPointerException(TECHNISCHE_SLEUTEL_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }

        if (indicatieFoutiefOpgeschortUitsluiten) {
            return em.createQuery(SELECT_DEEL
                            + WHERE_CLAUSE_TECHNISCHE_SLEUTEL_EN_SOORT_PERSOON
                            + AND_NOT_PERSOON_OPGESCHORT,
                    Persoon.class)
                    .setParameter(TECHNISCHE_SLEUTEL_PARAM, technischeSleutel)
                    .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                    .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                    .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                    .getResultList();
        } else {
            return em.createQuery(SELECT_DEEL + WHERE_CLAUSE_TECHNISCHE_SLEUTEL_EN_SOORT_PERSOON, Persoon.class)
                    .setParameter(TECHNISCHE_SLEUTEL_PARAM, technischeSleutel)
                    .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                    .getResultList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Persoon> findByAdministratienummerHistorisch(final String administratienummer, final SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                "select distinct his.persoon from PersoonIDHistorie his "
                        + "where his.administratienummer = :administratienummer "
                        + AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NOT_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL
                        + AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID,
                Persoon.class)
                .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
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
        ALaagAfleidingsUtil.vulALaag(persoon);
        if (persoon.getId() == null) {
            em.persist(persoon);
        } else {
            em.flush();
        }
        slaPersoonCacheOp(persoon);
    }

    /**
     * Sla de 'blob' cache van de persoon op in database.
     * @param persoon de persoon waarvan de blob opgeslagen moet worden
     */
    private void slaPersoonCacheOp(final Persoon persoon) {
        final byte[] persoonBlob;
        try {
            persoonBlob = Blobber.toJsonBytes(Blobber.maakBlob(persoon));
            PersoonCache persoonCache = findByPersoon(persoon);
            if (persoonCache == null) {
                persoonCache = new PersoonCache(persoon, (short) 1);
            }
            persoonCache.setPersoonHistorieVolledigGegevens(persoonBlob);
            em.persist(persoonCache);
        } catch (final BlobException e) {
            throw new IllegalStateException(e);
        }
    }

    private PersoonCache findByPersoon(final Persoon persoon) {
        final List<PersoonCache> resultaat = em.createQuery(WHERE_CLAUSE_PERSOONCHACHE, PersoonCache.class).setParameter(PERSOON, persoon).getResultList();
        return resultaat.isEmpty() ? null : resultaat.get(0);
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
            final String administratienummer,
            final String burgerservicenummer,
            final String geslachtsnaamstam,
            final String postcode) {
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
            final String administratienummer,
            final String burgerservicenummer,
            final String geslachtsnaamstam) {
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

    @Override
    public List<Persoon> findByBurgerservicenummerHistorisch(String burgerservicenummer, SoortPersoon soortPersoon) {
        if (burgerservicenummer == null) {
            throw new NullPointerException(BURGERSERVICENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                "select distinct his.persoon from PersoonIDHistorie his "
                        + "where his.burgerservicenummer = :burgerservicenummer "
                        + AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NOT_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL
                        + AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID
                        + AND_NOT_HIS_PERSOON_OPGESCHORT,
                Persoon.class)
                .setParameter(BURGERSERVICENUMMER_PARAM, burgerservicenummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();
    }

    @Override
    public List<Persoon> findByVolgendeAnummer(String administratienummer, SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                SELECT_DISTINCT_HIS_PERSOON_FROM_PERSOON_NUMMERVERWIJZING_HISTORIE_HIS
                        + WHERE_HIS_VOLGENDE_ADMINISTRATIENUMMER_ADMINISTRATIENUMMER
                        + AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL
                        + AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID
                        + AND_NOT_HIS_PERSOON_OPGESCHORT,
                Persoon.class)
                .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();
    }


    @Override
    public List<Persoon> findByVolgendeAnummerHistorisch(String administratienummer, SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                SELECT_DISTINCT_HIS_PERSOON_FROM_PERSOON_NUMMERVERWIJZING_HISTORIE_HIS
                        + WHERE_HIS_VOLGENDE_ADMINISTRATIENUMMER_ADMINISTRATIENUMMER
                        + AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NOT_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL
                        + AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID
                        + AND_NOT_HIS_PERSOON_OPGESCHORT,
                Persoon.class)
                .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();
    }

    @Override
    public List<Persoon> findByVorigeAnummer(String administratienummer, SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                SELECT_DISTINCT_HIS_PERSOON_FROM_PERSOON_NUMMERVERWIJZING_HISTORIE_HIS
                        + WHERE_HIS_VORIGE_ADMINISTRATIENUMMER_ADMINISTRATIENUMMER
                        + AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL
                        + AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID
                        + AND_NOT_HIS_PERSOON_OPGESCHORT,
                Persoon.class)
                .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();
    }

    @Override
    public List<Persoon> findByVorigeAnummerHistorisch(String administratienummer, SoortPersoon soortPersoon) {
        if (administratienummer == null) {
            throw new NullPointerException(ADMINISTRATIENUMMER_NULL_MELDING);
        }
        if (soortPersoon == null) {
            throw new NullPointerException(SOORT_PERSOON_NULL_MELDING);
        }
        return em.createQuery(
                SELECT_DISTINCT_HIS_PERSOON_FROM_PERSOON_NUMMERVERWIJZING_HISTORIE_HIS
                        + WHERE_HIS_VORIGE_ADMINISTRATIENUMMER_ADMINISTRATIENUMMER
                        + AND_HIS_DATUM_EINDE_GELDIGHEID_IS_NOT_NULL_AND_HIS_DATUM_TIJD_VERVAL_IS_NULL
                        + AND_HIS_PERSOON_SOORT_PERSOON_ID_SOORT_PERSOON_ID
                        + AND_NOT_HIS_PERSOON_OPGESCHORT,
                Persoon.class)
                .setParameter(ADMINISTRATIENUMMER_PARAM, administratienummer)
                .setParameter(SOORT_PERSOON_ID_PARAM, soortPersoon.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_FOUT_ID, NadereBijhoudingsaard.FOUT.getId())
                .setParameter(PARAM_NADERE_BIJHOUDINGINDSAARD_GEWIST_ID, NadereBijhoudingsaard.GEWIST.getId())
                .getResultList();
    }
}
