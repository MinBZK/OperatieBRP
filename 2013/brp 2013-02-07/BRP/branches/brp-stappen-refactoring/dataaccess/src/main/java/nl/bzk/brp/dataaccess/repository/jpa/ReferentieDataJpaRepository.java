/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverAdreshoudingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor referentieData en standaard implementatie van de
 * {@link nl.bzk.brp.dataaccess.repository.ReferentieDataRepository} class.
 */
@Repository class ReferentieDataJpaRepository implements ReferentieDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentieDataJpaRepository.class);

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats vindWoonplaatsOpCode(final Woonplaatscode code) {
        final String sql = "SELECT plaats FROM Plaats plaats WHERE plaats.code = :code";
        try {
            return (Plaats) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende woonplaatscode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE,
                                                  code, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij vindGemeenteOpCode(final GemeenteCode code) {
        final String sql = "SELECT partij FROM Partij partij WHERE partij.code = :code";
        try {
            return (Partij) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende gemeentecode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE,
                                                  code, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land vindLandOpCode(final Landcode code) {
        final String sql = "SELECT land FROM Land land WHERE land.code = :code";
        try {
            return (Land) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende landcode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE,
                                                  code, e);
        }
    }

    @Override
    public Nationaliteit vindNationaliteitOpCode(final Nationaliteitcode code) {
        final String sql = "SELECT nation FROM Nationaliteit nation WHERE nation.code = :code";
        try {
            return (Nationaliteit) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende nationaliteit code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                                                  code, e);
        }
    }

    @Override
    public RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code) {
        final String sql = "SELECT rdnWijzAdres FROM RedenWijzigingAdres rdnWijzAdres WHERE "
                + " rdnWijzAdres.code = :code";
        try {
            return (RedenWijzigingAdres) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende RedenWijzigingAdres code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                                                  code, e);
        }
    }

    @Override
    public RedenBeeindigingRelatie vindRedenBeeindigingRelatieOpCode(final RedenBeeindigingRelatieCode code) {
        final String sql = "SELECT rdnBeeindRelatie FROM RedenBeeindigingRelatie rdnBeeindRelatie WHERE "
                + " rdnBeeindRelatie.code = :code";
        try {
            return (RedenBeeindigingRelatie) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende RedenBeeindigingRelatie code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENBEEINDIGINGRELATIE,
                                                  code, e);
        }
    }

    @Override
    public AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code) {
        final String sql = "SELECT aangAdresh FROM AangeverAdreshouding aangAdresh WHERE "
                + " aangAdresh.code = :code";
        try {
            return (AangeverAdreshouding) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende AangeverAdreshouding code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AAANGEVERADRESHOUDING,
                                                  code, e);
        }
    }

    @Override
    public AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCode code) {
        final String sql = "SELECT adellijkeTitel FROM AdellijkeTitel adellijkeTitel WHERE "
                + " adellijkeTitel.code = :code";
        try {
            return (AdellijkeTitel) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende AdellijkeTitel code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL,
                                                  code, e);
        }
    }

    @Override
    public Predikaat vindPredikaatOpCode(final PredikaatCode code) {
        final String sql = "SELECT predikaat FROM Predikaat predikaat WHERE "
                + " predikaat.code = :code";
        try {
            return (Predikaat) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende Predikaat code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PREDIKAAT,
                                                  code, e);
        }
    }

    @Override
    public RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
            final RedenVerkrijgingCode redenVerkrijgingCode)
    {
        try {
            final String sql = "SELECT rdn FROM RedenVerkrijgingNLNationaliteit rdn WHERE "
                    + "rdn.code = :code";
            return (RedenVerkrijgingNLNationaliteit) em.createQuery(sql).setParameter("code", redenVerkrijgingCode)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende Reden verkrijgen NL nationaliteit naam, '{}' niet gevonden.",
                        redenVerkrijgingCode.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERKRIJGENNLNATION,
                                                  redenVerkrijgingCode, e);
        }
    }

    @Override
    public RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpCode(final RedenVerliesCode code) {
        try {
            final String sql = "SELECT rdn FROM RedenVerliesNLNationaliteit rdn WHERE "
                    + "rdn.code = :code";
            return (RedenVerliesNLNationaliteit) em.createQuery(sql).setParameter("code", code)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende Reden verlies NL nationaliteit code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION,
                                                  code, e);
        }
    }

    @Override
    public SoortDocument vindSoortDocumentOpNaam(final NaamEnumeratiewaarde naam) {
        try {
            final String sql = "SELECT srtdoc FROM SoortDocument srtdoc WHERE "
                    + "srtdoc.naam = :naam";
            return em.createQuery(sql, SoortDocument.class).setParameter("naam", naam)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende Reden verlies NL nationaliteit naam, '{}' niet gevonden.", naam.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.SOORTDOCUMENT,
                                                  naam, e);
        }

    }
}
