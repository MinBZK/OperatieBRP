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
import nl.bzk.brp.dataaccess.repository.ReferentieDataMdlRepository;
import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingNaam;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Repository voor referentieData en standaard implementatie van de {@link ReferentieDataMdlRepository} class.
 */
@Repository
class ReferentieDataMdlJpaRepository implements ReferentieDataMdlRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentieDataMdlJpaRepository.class);

    @PersistenceContext
    private EntityManager       em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats vindWoonplaatsOpCode(final PlaatsCode code) {
        final String sql = "SELECT plaats FROM PlaatsMdl plaats WHERE plaats.code = :code";
        try {
            return (Plaats) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende woonplaatscode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE,
                    code.getWaarde(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij vindGemeenteOpCode(final GemeenteCode code) {
        final String sql = "SELECT partij FROM PartijMdl partij WHERE partij.gemeenteCode = :code";
        try {
            return (Partij) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende gemeentecode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE,
                    code.getWaarde(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land vindLandOpCode(final LandCode code) {
        final String sql = "SELECT land FROM LandMdl land WHERE land.landCode = :code";
        try {
            return (Land) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende landcode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE,
                    code.getWaarde(), e);
        }
    }

    @Override
    public Nationaliteit vindNationaliteitOpCode(final NationaliteitCode code) {
        final String sql = "SELECT nation FROM NationaliteitMdl nation WHERE nation.nationaliteitCode = :code";
        try {
            return (Nationaliteit) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende nationaliteit code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                    code.getWaarde(), e);
        }
    }

    @Override
    public RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code) {
        final String sql = "SELECT rdnWijzAdres FROM RedenWijzigingAdres rdnWijzAdres WHERE "
                + " rdnWijzAdres.redenWijzigingAdresCode = :code";
        try {
            return (RedenWijzigingAdres) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende RedenWijzigingAdres code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                    code.getWaarde(), e);
        }
    }

    @Override
    public AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code) {
        final String sql = "SELECT aangAdresh FROM AangeverAdreshouding aangAdresh WHERE "
                + " aangAdresh.aangeverAdreshoudingCode = :code";
        try {
            return (AangeverAdreshouding) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende AangeverAdreshouding code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AAANGEVERADRESHOUDING,
                    code.getWaarde(), e);
        }
    }

    @Override
    public AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCode code) {
        final String sql = "SELECT adellijkeTitel FROM AdellijkeTitel adellijkeTitel WHERE "
                + " adellijkeTitel.adellijkeTitelCode = :code";
        try {
            return (AdellijkeTitel) em.createQuery(sql).setParameter("code", code).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende AdellijkeTitel code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL,
                    code.getWaarde(), e);
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
                    code.getWaarde(), e);
        }
    }

    @Override
    public RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpNaam(
            final RedenVerkrijgingNaam redenVerkrijgingNaam)
    {
        try {
            final String sql = "SELECT rdn FROM RedenVerkrijgingNLNationaliteit rdn WHERE "
                    + "rdn.redenVerkrijgingNaam = :naam";
            return (RedenVerkrijgingNLNationaliteit) em.createQuery(sql).setParameter("naam", redenVerkrijgingNaam)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende Reden verkrijgen NL nationaliteit naam, '{}' niet gevonden.",
                    redenVerkrijgingNaam.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERKRIJGENNLNATION,
                    redenVerkrijgingNaam.getWaarde(), e);
        }
    }

    @Override
    public RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpNaam(final RedenVerliesNaam naam) {
        try {
            final String sql = "SELECT rdn FROM RedenVerliesNLNationaliteitMdl rdn WHERE "
                    + "rdn.naam = :naam";
            return (RedenVerliesNLNationaliteit) em.createQuery(sql).setParameter("naam", naam)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Onbekende Reden verlies NL nationaliteit naam, '{}' niet gevonden.", naam.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION,
                    naam.getWaarde(), e);
        }
    }
}
