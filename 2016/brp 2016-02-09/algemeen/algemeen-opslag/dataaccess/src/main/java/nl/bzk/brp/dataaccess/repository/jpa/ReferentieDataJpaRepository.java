/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RechtsgrondCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Repository;


/**
 * Repository voor referentieData en standaard implementatie van de {@link nl.bzk.brp.dataaccess.repository.ReferentieDataRepository} class.
 */
@Repository
public final class ReferentieDataJpaRepository implements ReferentieDataRepository {

    private static final Logger LOGGER                                                  = LoggerFactory.getLogger();
    private static final String SELECT_PLAATS_FROM_PLAATS_PLAATS_WHERE_PLAATS_NAAM_NAAM =
        "SELECT plaats FROM Plaats plaats WHERE plaats.naam = :naam";
    private static final String NAAM                                                    = "naam";
    private static final String CODE                                                    = "code";
    private static final String OIN                                                     = "oin";
    private static final String RDN_CODE_CODE                                           = "rdn.code = :code";

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    /**
     * Basis functie om een Query object aan te maken. De flush mode wordt hier op COMMIT gezet omdat we willen voorkomen dat de BRP een flush doet bij het
     * ophalen van een stamgegeven. (Zie ROMEO-524)
     *
     * @param sql de sql string
     * @return JPA Query object.
     */
    private Query maakQuery(final String sql) {
        return em.createQuery(sql).setFlushMode(FlushModeType.COMMIT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isBestaandeWoonplaats(final NaamEnumeratiewaardeAttribuut woonplaatsnaam) {
        final String sql = SELECT_PLAATS_FROM_PLAATS_PLAATS_WHERE_PLAATS_NAAM_NAAM;
        return maakQuery(sql).setParameter(NAAM, woonplaatsnaam).getResultList().size() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats vindWoonplaatsOpNaam(final NaamEnumeratiewaardeAttribuut woonplaatsnaam) {
        final String sql = SELECT_PLAATS_FROM_PLAATS_PLAATS_WHERE_PLAATS_NAAM_NAAM;
        try {
            return (Plaats) maakQuery(sql).setParameter(NAAM, woonplaatsnaam).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende woonplaatsnaam '{}' niet gevonden.", woonplaatsnaam);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSNAAM,
                woonplaatsnaam, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeente vindGemeenteOpCode(final GemeenteCodeAttribuut code) {
        final String sql = "SELECT gemeente FROM Gemeente gemeente WHERE gemeente.code = :code";
        try {
            return (Gemeente) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende gemeentecode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, code, e);
        }
    }

    @Override
    public Gemeente findHuidigeGemeenteByPersoonID(final int persoonID) {
        final Query query =
            em.createQuery("SELECT adres FROM PersoonAdresModel adres" + " WHERE adres.persoon.id = :persoonID");
        query.setParameter("persoonID", persoonID);

        @SuppressWarnings("unchecked")
        final List<PersoonAdres> adressen = query.getResultList();
        Gemeente gemeente = null;
        if (adressen.size() > 0) {
            // Haal de huidige gemeente uit het 1e adres (er is hoogstens 1 adres)
            gemeente = adressen.get(0).getStandaard().getGemeente().getWaarde();
        }
        return gemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij vindPartijOpCode(final PartijCodeAttribuut code) {
        final String sql = "SELECT partij FROM Partij partij WHERE partij.code = :code";
        try {
            return (Partij) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende partijcode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PARTIJCODE, code, e);
        }
    }

    @Override
    public Partij vindPartijOpOIN(final OINAttribuut oin) {
        final String sql = "SELECT partij FROM Partij partij WHERE partij.oIN = :oin";
        return (Partij) maakQuery(sql).setParameter(OIN, oin).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebied vindLandOpCode(final LandGebiedCodeAttribuut code) {
        final String sql = "SELECT landGebied FROM LandGebied landGebied WHERE landGebied.code = :code";
        try {
            return (LandGebied) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende landcode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDGEBIEDCODE, code, e);
        }
    }

    @Override
    public Nationaliteit vindNationaliteitOpCode(final NationaliteitcodeAttribuut code) {
        final String sql = "SELECT nation FROM Nationaliteit nation WHERE nation.code = :code";
        try {
            return (Nationaliteit) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende nationaliteit code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE, code,
                e);
        }
    }

    @Override
    public SoortNederlandsReisdocument vindSoortReisdocumentOpCode(final SoortNederlandsReisdocumentCodeAttribuut code)
    {
        final String sql = "SELECT soort FROM SoortNederlandsReisdocument soort WHERE soort.code = :code";
        try {
            return (SoortNederlandsReisdocument) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende soort reisdocument code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.SOORTREISDOCUMENT, code, e);
        }
    }

    @Override
    public RedenWijzigingVerblijf vindRedenWijzingVerblijfOpCode(final RedenWijzigingVerblijfCodeAttribuut code) {
        final String sql =
            "SELECT rdnWijzAdres FROM RedenWijzigingVerblijf rdnWijzAdres WHERE " + " rdnWijzAdres.code = :code";
        try {
            return (RedenWijzigingVerblijf) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende RedenWijzigingVerblijf code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES, code, e);
        }
    }

    @Override
    public RedenEindeRelatie vindRedenEindeRelatieOpCode(final RedenEindeRelatieCodeAttribuut code) {
        final String sql =
            "SELECT rdnEindeRelatie FROM RedenEindeRelatie rdnEindeRelatie WHERE " + " rdnEindeRelatie.code = :code";
        try {
            return (RedenEindeRelatie) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende RedenEindeRelatie code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENEINDERELATIE, code, e);
        }
    }

    @Override
    public Aangever vindAangeverAdreshoudingOpCode(final AangeverCodeAttribuut code) {
        final String sql = "SELECT aangAdresh FROM Aangever aangAdresh WHERE " + " aangAdresh.code = :code";
        try {
            return (Aangever) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende Aangever code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AANGEVERADRESHOUDING,
                code, e);
        }
    }

    @Override
    public AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCodeAttribuut code) {
        final String sql =
            "SELECT adellijkeTitel FROM AdellijkeTitel adellijkeTitel WHERE " + " adellijkeTitel.code = :code";
        try {
            return (AdellijkeTitel) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende AdellijkeTitel code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL, code, e);
        }
    }

    @Override
    public Predicaat vindPredicaatOpCode(final PredicaatCodeAttribuut code) {
        final String sql = "SELECT predicaat FROM Predicaat predicaat WHERE " + " predicaat.code = :code";
        try {
            return (Predicaat) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende Predicaat code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PREDICAAT, code, e);
        }
    }

    @Override
    public RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
        final RedenVerkrijgingCodeAttribuut redenVerkrijgingCode)
    {
        try {
            final String sql = "SELECT rdn FROM RedenVerkrijgingNLNationaliteit rdn WHERE " + RDN_CODE_CODE;
            return (RedenVerkrijgingNLNationaliteit) maakQuery(sql).setParameter(CODE, redenVerkrijgingCode)
                .getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende Reden verkrijgen NL nationaliteit naam, '{}' niet gevonden.",
                redenVerkrijgingCode.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERKRIJGENNLNATION,
                redenVerkrijgingCode, e);
        }
    }

    @Override
    public RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpCode(final RedenVerliesCodeAttribuut code) {
        try {
            final String sql = "SELECT rdn FROM RedenVerliesNLNationaliteit rdn WHERE " + RDN_CODE_CODE;
            return (RedenVerliesNLNationaliteit) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende Reden verlies NL nationaliteit code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION,
                code, e);
        }
    }

    @Override
    public AanduidingInhoudingVermissingReisdocument vindAanduidingInhoudingVermissingReisdocumentOpCode(
        final AanduidingInhoudingVermissingReisdocumentCodeAttribuut code)
    {
        try {
            final String sql = "SELECT rdn FROM AanduidingInhoudingVermissingReisdocument rdn WHERE rdn.code = :code";
            return (AanduidingInhoudingVermissingReisdocument) maakQuery(sql).setParameter(CODE, code)
                .getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende AanduidingInhoudingVermissingReisdocument code, '{}' niet gevonden.",
                code.getWaarde());
            throw new OnbekendeReferentieExceptie(
                OnbekendeReferentieExceptie.ReferentieVeld.REDENVERVALLENREISDOUCMENT, code, e);
        }
    }

    @Override
    public SoortDocument vindSoortDocumentOpNaam(final NaamEnumeratiewaardeAttribuut naam) {
        try {
            final String sql = "SELECT srtdoc FROM SoortDocument srtdoc WHERE " + "srtdoc.naam = :naam";
            return (SoortDocument) maakQuery(sql).setParameter(NAAM, naam).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende Reden verlies NL nationaliteit naam, '{}' niet gevonden.", naam.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.SOORTDOCUMENT, naam, e);
        }
    }

    @Override
    public LandGebied getNederland() {
        return this.vindLandOpCode(LandGebiedCodeAttribuut.NEDERLAND);
    }

    @Override
    public Leveringsautorisatie vindLeveringsAutorisatieOpNaam(final NaamEnumeratiewaardeAttribuut leveringsautorisatienaam) {
        final String sql = "SELECT leveringsAutorisatie FROM Leveringsautorisatie leveringsAutorisatie WHERE leveringsAutorisatie.naam = :naam";
        try {
            return (Leveringsautorisatie) maakQuery(sql).setParameter(NAAM, leveringsautorisatienaam).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende leveringsautorisatie '{}' niet gevonden.", leveringsautorisatienaam);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LEVERINGSAUTORISATIENAAM,
                leveringsautorisatienaam, e);
        }
    }

    @Override
    public Element vindElementOpNaam(final NaamEnumeratiewaardeLangAttribuut elementNaam) {
        final String sql = "SELECT element FROM Element element WHERE element.naam = :naam";
        try {
            return (Element) maakQuery(sql).setParameter(NAAM, elementNaam).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende elementNaam '{}' niet gevonden.", elementNaam);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ELEMENTNAAM,
                elementNaam, e);
        }
    }

    @Override
    public Rechtsgrond vindRechtsgrondOpCode(final RechtsgrondCodeAttribuut code) {
        final String sql = "SELECT rg FROM Rechtsgrond rg WHERE rg.code = :code";
        try {
            return (Rechtsgrond) maakQuery(sql).setParameter(CODE, code).getSingleResult();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende rechtsgrondCode '{}' niet gevonden.", code);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.RECHTSGRONDCODE,
                code, e);
        }
    }

    @Override
    public ActieModel vindActieModelOpSoortActie(final SoortActie soortActie) {
        final String sql = "SELECT actie FROM ActieModel actie WHERE actie.soort = :code";

        try {
            return (ActieModel) maakQuery(sql).setParameter(CODE, soortActie.ordinal()).getResultList().iterator().next();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende soortActie '{}' niet gevonden.", soortActie);
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.SOORT_ACTIE, soortActie, e);
        }
    }

    @Override
    public boolean bestaatVoorvoegselScheidingsteken(final String voorvoegsel, final String scheidingsteken) {
        final String sql =
            "SELECT voorvoegsel FROM Voorvoegsel voorvoegsel WHERE "
                + "voorvoegsel.voorvoegsel = :voorvoegsel AND voorvoegsel.scheidingsteken = :scheidingsteken";
        return maakQuery(sql).setParameter("voorvoegsel", new VoorvoegselAttribuut(voorvoegsel))
            .setParameter("scheidingsteken", new ScheidingstekenAttribuut(scheidingsteken)).getResultList().size() > 0;
    }

    public SoortDocument laadAnySoortDocument() {
        final String sql = "SELECT sd FROM SoortDocument sd";

        try {
            return (SoortDocument) maakQuery(sql).getResultList().iterator().next();
        } catch (final NoResultException e) {
            LOGGER.info("Onbekende soortActie '{}' niet gevonden.");
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AKTENUMMER, e);
        }
    }
}
