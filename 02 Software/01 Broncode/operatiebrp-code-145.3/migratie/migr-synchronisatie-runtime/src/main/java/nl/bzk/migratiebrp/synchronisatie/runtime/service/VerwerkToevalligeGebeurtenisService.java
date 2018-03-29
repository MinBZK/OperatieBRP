/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.util.List;
import java.util.function.Function;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3ToevalligeGebeurtenisParser;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.ParseException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigeToevalligeGebeurtenisException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.PersoonControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.RelatieVinder;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerzender;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp.AangaanGeregistreerdePartnerschapVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp.BeeindigingGeregistreerdPartnerschapVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp.OmzettingGeregistreerdPartnerschapInHuwelijkVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp.OntbindingHuwelijkVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp.VoltrekkingHuwelijkVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.overlijden.OverlijdenVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon.GeslachtsaanduidingVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon.GeslachtsnaamVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon.VoornaamVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk toevallige gebeurtenis service. Voert een bijhouding uit in de BRP.
 */
public final class VerwerkToevalligeGebeurtenisService
        implements SynchronisatieBerichtService<VerwerkToevalligeGebeurtenisVerzoekBericht, VerwerkToevalligeGebeurtenisAntwoordBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Lo3SyntaxControle syntaxControle;
    private final ToevalligeGebeurtenisVerwerker toevalligeGebeurtenisVerwerker;
    private final PersoonControle persoonControle;
    private final ToevalligeGebeurtenisVerzender toevalligeGebeurtenisVerzender;
    private final RelatieVinder relatieVinder;
    private final PreconditiesService preconditiesService;
    private final ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    private final BrpDalService brpDalService;
    private final BrpPersoonslijstService persoonslijstService;
    private final BrpAttribuutConverteerder brpAttribuurConverteerder;

    private final BerichtMaker berichtMaker;

    /**
     * constructor.
     * @param syntaxControle syntaxcontole
     * @param toevalligeGebeurtenisVerwerker toevallige gebeurtenis verwerken
     * @param persoonControle persoonscontrole
     * @param toevalligeGebeurtenisVerzender toevallige gebeurtenis verzender
     * @param relatieVinder relatie vinder
     * @param preconditiesService pre conditie service
     * @param converteerLo3NaarBrpService service om lo3 naar br te converteren
     * @param brpDalService brp data service
     * @param persoonslijstService persoonslijst service
     * @param brpAttribuurConverteerder brp attribuut converteerder
     * @param berichtMaker berichtmaker
     */
    @Inject
    public VerwerkToevalligeGebeurtenisService(final Lo3SyntaxControle syntaxControle,
                                               final ToevalligeGebeurtenisVerwerker toevalligeGebeurtenisVerwerker,
                                               final PersoonControle persoonControle,
                                               final ToevalligeGebeurtenisVerzender toevalligeGebeurtenisVerzender,
                                               final RelatieVinder relatieVinder,
                                               final PreconditiesService preconditiesService,
                                               final ConverteerLo3NaarBrpService converteerLo3NaarBrpService, BrpDalService brpDalService,
                                               final BrpPersoonslijstService persoonslijstService,
                                               final BrpAttribuutConverteerder brpAttribuurConverteerder,
                                               final BerichtMaker berichtMaker) {
        this.syntaxControle = syntaxControle;
        this.toevalligeGebeurtenisVerwerker = toevalligeGebeurtenisVerwerker;
        this.persoonControle = persoonControle;
        this.toevalligeGebeurtenisVerzender = toevalligeGebeurtenisVerzender;
        this.relatieVinder = relatieVinder;
        this.preconditiesService = preconditiesService;
        this.converteerLo3NaarBrpService = converteerLo3NaarBrpService;
        this.brpDalService = brpDalService;
        this.persoonslijstService = persoonslijstService;
        this.brpAttribuurConverteerder = brpAttribuurConverteerder;
        this.berichtMaker = berichtMaker;
    }

    @Override
    public Class<VerwerkToevalligeGebeurtenisVerzoekBericht> getVerzoekType() {
        return VerwerkToevalligeGebeurtenisVerzoekBericht.class;
    }

    private VerwerkToevalligeGebeurtenisAntwoordBericht maakFoutBericht(
            final FoutredenType foutredenType,
            final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek,
            final String bijhoudingsgemeente) {
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);
        antwoord.setBijhoudingGemeenteCode(bijhoudingsgemeente);
        antwoord.setFoutreden(foutredenType);
        antwoord.setLogging(Logging.getLogging().getRegels());
        antwoord.setCorrelationId(verzoek.getMessageId());

        return antwoord;
    }

    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public VerwerkToevalligeGebeurtenisAntwoordBericht verwerkBericht(final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek) {

        Logging.initContext();

        VerwerkToevalligeGebeurtenisAntwoordBericht antwoord;

        try {
            // Parse teletex string naar lo3 categorieen
            final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());

            // Controleer syntax obv lo3 categorieen
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);

            final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis =
                    new Lo3ToevalligeGebeurtenisParser().parse(
                            lo3InhoudNaSyntaxControle,
                            new Lo3String(verzoek.getAktenummer()),
                            new Lo3GemeenteCode(verzoek.getOntvangendeGemeente()),
                            new Lo3GemeenteCode(verzoek.getVerzendendeGemeente()));

            preconditiesService.verwerk(lo3ToevalligeGebeurtenis);
            final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis =
                    converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(lo3ToevalligeGebeurtenis);

            LOG.debug("Zoek persoon o.b.v. A-nummer.");
            final BrpPersoonslijst gevondenPersoon =
                    persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(
                            brpToevalligeGebeurtenis.getPersoon().getAdministratienummer().getWaarde());

            if (gevondenPersoon == null) {
                throw new ControleException(FoutredenType.G);
            }

            controleerBijhouder(brpToevalligeGebeurtenis, gevondenPersoon);
            controleerOpschorting(brpToevalligeGebeurtenis, gevondenPersoon);

            persoonControle.controleer(gevondenPersoon, brpToevalligeGebeurtenis);
            BrpRelatie brpRelatie = null;
            if (brpToevalligeGebeurtenis.getVerbintenis() != null
                    && (brpToevalligeGebeurtenis.getVerbintenis().getOmzetting() != null
                    || brpToevalligeGebeurtenis.getVerbintenis().getOntbinding() != null)) {
                brpRelatie = relatieVinder.vindRelatie(gevondenPersoon, brpToevalligeGebeurtenis);
            }

            // verwerken.
            final ObjecttypeBerichtBijhouding opdracht = verwerkBerichtInhoudelijk(brpToevalligeGebeurtenis, gevondenPersoon, brpRelatie);
            toevalligeGebeurtenisVerzender.verstuurBrpToevalligeGebeurtenisOpdracht(opdracht, verzoek.getMessageId());

            LOG.debug("Tb02 correct verwerkt.");
            antwoord = null;
        } catch (final
        BerichtSyntaxException
                | OngeldigePersoonslijstException
                | ParseException
                | OngeldigeToevalligeGebeurtenisException e) {
            antwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
            antwoord.setStatus(StatusType.AFGEKEURD);
            antwoord.setCorrelationId(verzoek.getMessageId());
            antwoord.setLogging(Logging.getLogging().getRegels());
            LOG.debug("Fout in toevalligeGEbeurtenisService", e);
        } catch (final ControleException ce) {
            antwoord = maakFoutBericht(ce.getFoutreden(), verzoek, ce.getBijhoudingsgemeente());
            LOG.debug("Fout in toevalligeGebeurtenisService", ce);
        } catch (final IllegalStateException ise) {
            // Meerdere personen gevonden voor het meegegeven A-nummer.
            antwoord = maakFoutBericht(FoutredenType.U, verzoek, null);
            LOG.debug("Meerdere personen gevonden voor het meegegeven A-nummer.", ise);
        } finally {
            Logging.destroyContext();
        }

        return antwoord;
    }

    private void controleerBijhouder(final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis, final BrpPersoonslijst gevondenPersoon)
            throws ControleException {
        // Bijhoudingspartij
        final BrpPartijCode bijhoudingsgemeentePersoonslijst = gevondenPersoon.getBijhoudingStapel().getActueel().getInhoud().getBijhoudingspartijCode();

        if (gevondenPersoon.getBijhoudingStapel().bevatActueel() && !bijhoudingsgemeentePersoonslijst.equals(brpToevalligeGebeurtenis.getDoelPartijCode())) {
            throw new ControleException(FoutredenType.V, brpAttribuurConverteerder.converteerGemeenteCode(bijhoudingsgemeentePersoonslijst).getWaarde());
        }

        final Blokkering blokkering = brpDalService.vraagOpBlokkering(brpToevalligeGebeurtenis.getPersoon().getAdministratienummer().getWaarde());

        if (blokkering != null) {
            throw new ControleException(FoutredenType.B, blokkering.getGemeenteCodeNaar() != null ?
                    brpAttribuurConverteerder.converteerGemeenteCode(new BrpPartijCode(blokkering.getGemeenteCodeNaar())).getWaarde() : null);
        }

    }

    /**
     * Controleer de status van de persoon:
     *
     * 'Geemigreerd' of 'Ministrieel besluit' resulteert in een foutcode 'B'. 'Overleden' resulteert in de foutcode 'O'
     * indien de persoonslijst is opgeschort voor de ingangsdatum gebeurtenis.
     * @throws ControleException bij controle fouten
     */
    private void controleerOpschorting(final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis, final BrpPersoonslijst gevondenPersoon)
            throws ControleException {
        final BrpGroep<BrpBijhoudingInhoud> bijhouding = gevondenPersoon.getBijhoudingStapel().getActueel();
        final BrpNadereBijhoudingsaardCode nadereBijhoudingsaard = bijhouding.getInhoud().getNadereBijhoudingsaardCode();

        // Geemigreerd of Ministrieel besluit
        if (BrpNadereBijhoudingsaardCode.EMIGRATIE.equals(nadereBijhoudingsaard)
                || BrpNadereBijhoudingsaardCode.BIJZONDERE_STATUS.equals(nadereBijhoudingsaard)) {
            throw new ControleException(FoutredenType.B);
        }

        // Overleden
        if (BrpNadereBijhoudingsaardCode.OVERLEDEN.equals(nadereBijhoudingsaard)) {
            // Controleer ingangsdatum tov opschortingsdatum
            final BrpDatum ingangsdatumRechtsfeit = brpToevalligeGebeurtenis.getDatumAanvang();
            final BrpDatum datumOpschorting = bijhouding.getHistorie().getDatumAanvangGeldigheid();
            if (ingangsdatumRechtsfeit.compareTo(datumOpschorting) >= 0) {
                throw new ControleException(FoutredenType.O);
            }
        }
    }

    private ObjecttypeBerichtBijhouding verwerkBerichtInhoudelijk(
            final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis,
            final BrpPersoonslijst brpPersoon,
            final BrpRelatie brpRelatie) throws ControleException {
        final ObjecttypeBerichtBijhouding opdracht;
        // Switch actie persoon/huwelijk/overlijden/adoptie o.b.v. wat gevuld is.
        if (brpToevalligeGebeurtenis.getVoornaam() != null) {
            // Voornaam (1M)
            LOG.info("Tb02: Verwerk voornaam.");
            controleerActualiteitPersoon(brpToevalligeGebeurtenis.getDatumAanvang(), brpPersoon);
            opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerNaamGeslachtBijhouding(
                    brpToevalligeGebeurtenis, brpPersoon, new VoornaamVerwerker(berichtMaker));
        } else if (brpToevalligeGebeurtenis.getGeslachtsnaam() != null) {
            // Geslachtsnaam (1H)
            LOG.info("Tb02: Verwerk geslachtsnaam.");
            controleerActualiteitPersoon(brpToevalligeGebeurtenis.getDatumAanvang(), brpPersoon);
            opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerNaamGeslachtBijhouding(
                    brpToevalligeGebeurtenis, brpPersoon, new GeslachtsnaamVerwerker(berichtMaker));
        } else if (brpToevalligeGebeurtenis.getGeslachtsaanduiding() != null) {
            // Geslachtsaanduiding (1S)
            LOG.info("Tb02: Verwerk geslachtsaanduiding.");
            controleerActualiteitPersoon(brpToevalligeGebeurtenis.getDatumAanvang(), brpPersoon);
            opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerNaamGeslachtBijhouding(
                    brpToevalligeGebeurtenis, brpPersoon, new GeslachtsaanduidingVerwerker(berichtMaker));
        } else if (brpToevalligeGebeurtenis.getOverlijden() != null) {
            // Overlijden
            LOG.info("Tb02: Verwerk overlijden persoon.");
            controleerActualiteitOverlijden(brpToevalligeGebeurtenis.getDatumAanvang(), brpPersoon);
            opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerOverlijdenBijhouding(
                    brpToevalligeGebeurtenis, brpPersoon, new OverlijdenVerwerker(berichtMaker));
        } else if (brpToevalligeGebeurtenis.getVerbintenis() != null) {
            // Huwelijk
            LOG.info("Tb02: Verwerk huwelijk of geregistreerd partnerschap.");
            controleerActualiteitVerbintenis(brpToevalligeGebeurtenis.getDatumAanvang(), brpPersoon);
            opdracht = verwerkHuwelijkOfGeregistreerdPartnerschap(brpToevalligeGebeurtenis, brpPersoon, brpRelatie);
        } else if (brpToevalligeGebeurtenis.getFamilierechtelijkeBetrekking() != null) {
            // Adoptie
            LOG.info("Tb02: Verwerk adoptie.");
            throw new IllegalArgumentException("Adoptie nog niet geimplementeerd");
        } else {
            throw new IllegalArgumentException("Toevallige gebeurtenis bevatte geen wijziging.");
        }
        return opdracht;
    }

    /**
     * Controleer dat er geen groep is in de gevonden persoon die 'komt uit categorie 01' die een actuele datum ingang
     * heeft die later is dan de datum aanvang van het rechtsfeit.
     * @param datumAanvang datum aanvang van het rechtsfeit
     * @param brpPersoon gevonden persoon
     * @throws ControleException wanneer de controle niet slaagt
     */
    private void controleerActualiteitPersoon(final BrpDatum datumAanvang, final BrpPersoonslijst brpPersoon) throws ControleException {
        for (final BrpStapel<BrpVoornaamInhoud> stapel : brpPersoon.getVoornaamStapels()) {
            controleerActualiteit(datumAanvang, stapel, VerwerkToevalligeGebeurtenisService::getDatumAanvangGeldigheid);
        }
        controleerActualiteit(datumAanvang, brpPersoon.getGeslachtsaanduidingStapel(), VerwerkToevalligeGebeurtenisService::getDatumAanvangGeldigheid);
        for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> stapel : brpPersoon.getGeslachtsnaamcomponentStapels()) {
            controleerActualiteit(datumAanvang, stapel, VerwerkToevalligeGebeurtenisService::getDatumAanvangGeldigheid);
        }
        controleerActualiteit(datumAanvang, brpPersoon.getSamengesteldeNaamStapel(), VerwerkToevalligeGebeurtenisService::getDatumAanvangGeldigheid);
    }

    /**
     * Controleer dat er geen groep is in de gevonden persoon die 'komt uit categorie 06' die een actuele datum ingang
     * heeft die later is dan de datum aanvang van het rechtsfeit.
     *
     * Controleer, als de persoonslijst is opgeschort dat de datum aanvang ligt op of na de datu opschorting van de
     * gevonden persoonslijst.
     * @param datumAanvang datum aanvang van het rechtsfeit
     * @param brpPersoon gevonden persoon
     * @throws ControleException wanneer de controle niet slaagt
     */
    private void controleerActualiteitOverlijden(final BrpDatum datumAanvang, final BrpPersoonslijst brpPersoon) throws ControleException {
        controleerActualiteit(datumAanvang, brpPersoon.getOverlijdenStapel(), VerwerkToevalligeGebeurtenisService::getDatumOverlijden);
        final BrpNadereBijhoudingsaardCode nadereBijhoudingsaard =
                brpPersoon.getBijhoudingStapel().getActueel().getInhoud().getNadereBijhoudingsaardCode();

        if (!BrpNadereBijhoudingsaardCode.ACTUEEL.equals(nadereBijhoudingsaard)) {
            final BrpGroep<BrpBijhoudingInhoud> bijhouding = brpPersoon.getBijhoudingStapel().getActueel();
            final BrpDatum datumOpschorting = bijhouding.getHistorie().getDatumAanvangGeldigheid();

            if (datumAanvang.compareTo(datumOpschorting) < 0) {
                throw new ControleException(FoutredenType.N);
            }
        }
    }

    /**
     * Controleer dat er geen groep is in de gevonden persoon die 'komt uit categorie 05' die een actuele datum ingang
     * heeft die later is dan de datum aanvang van het rechtsfeit.
     * @param datumAanvang datum aanvang van het rechtsfeit
     * @param brpPersoon gevonden persoon
     * @throws ControleException wanneer de controle niet slaagt
     */
    private void controleerActualiteitVerbintenis(final BrpDatum datumAanvang, final BrpPersoonslijst brpPersoon) throws ControleException {
        for (final BrpRelatie relatie : brpPersoon.getRelaties()) {
            if (BrpSoortRelatieCode.HUWELIJK.equals(relatie.getSoortRelatieCode())
                    || BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatieCode())) {
                controleerActualiteit(datumAanvang, relatie.getRelatieStapel(), VerwerkToevalligeGebeurtenisService::getDatumRelatie);
            }
        }
    }

    private <G extends BrpGroepInhoud> void controleerActualiteit(final BrpDatum datumAanvang, final BrpStapel<G> stapel,
                                                                  final Function<BrpGroep, BrpDatum> datumGetter)
            throws ControleException {
        if (stapel == null) {
            return;
        }

        final BrpGroep<G> actueleGroep = stapel.getActueel();
        if (actueleGroep == null) {
            return;
        }

        final BrpDatum datumActueleGroep = datumGetter.apply(actueleGroep);
        if (datumActueleGroep != null && datumAanvang.compareTo(datumActueleGroep) <= 0) {
            throw new ControleException(FoutredenType.N);
        }
    }

    private static BrpDatum getDatumAanvangGeldigheid(final BrpGroep<?> groep) {
        return groep.getHistorie().getDatumAanvangGeldigheid();
    }

    private static BrpDatum getDatumOverlijden(final BrpGroep<BrpOverlijdenInhoud> groep) {
        return groep.getInhoud().getDatum();
    }

    private static BrpDatum getDatumRelatie(final BrpGroep<BrpRelatieInhoud> groep) {
        final BrpRelatieInhoud inhoud = groep.getInhoud();

        return inhoud.getDatumEinde() == null ? inhoud.getDatumAanvang() : inhoud.getDatumEinde();
    }

    private ObjecttypeBerichtBijhouding verwerkHuwelijkOfGeregistreerdPartnerschap(
            final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis,
            final BrpPersoonslijst brpPersoon,
            final BrpRelatie brpRelatie) {
        final boolean omzettingGevuld = brpToevalligeGebeurtenis.getVerbintenis().getOmzetting() != null;
        final boolean ontbindingGevuld = brpToevalligeGebeurtenis.getVerbintenis().getOntbinding() != null;

        final boolean sluitingIsHuwelijk = BrpSoortRelatieCode.HUWELIJK.equals(brpToevalligeGebeurtenis.getVerbintenis().getSluiting().getRelatieCode());

        final ObjecttypeBerichtBijhouding opdracht;

        if (!omzettingGevuld && !ontbindingGevuld) {
            if (sluitingIsHuwelijk) {
                // Sluiting huwelijk (3A)
                opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                        brpToevalligeGebeurtenis, brpPersoon, brpRelatie, new VoltrekkingHuwelijkVerwerker(berichtMaker));
            } else {
                // Sluiting geregistreerd partnerschap (5A)
                opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                        brpToevalligeGebeurtenis, brpPersoon, brpRelatie, new AangaanGeregistreerdePartnerschapVerwerker(berichtMaker));
            }

        } else if (ontbindingGevuld) {
            if (sluitingIsHuwelijk) {
                // Ontbinding huwelijk (3B)
                opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                        brpToevalligeGebeurtenis, brpPersoon, brpRelatie, new OntbindingHuwelijkVerwerker(berichtMaker));
            } else {
                // Ontbinding geregistreerd partnerschap (5B)
                opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                        brpToevalligeGebeurtenis, brpPersoon, brpRelatie, new BeeindigingGeregistreerdPartnerschapVerwerker(berichtMaker));
            }
        } else {
            // Omzetting (3/5H)
            if (sluitingIsHuwelijk) {
                // Omzetting van huwelijk in geregistreed partnerschap (5H)
                throw new IllegalArgumentException("Omzetting van huwelijk in geregistreerde partnerschap niet ondersteund in BRP");
            } else {
                opdracht = toevalligeGebeurtenisVerwerker.verwerkRegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                        brpToevalligeGebeurtenis,
                        brpPersoon,
                        brpRelatie,
                        new OmzettingGeregistreerdPartnerschapInHuwelijkVerwerker(berichtMaker));
            }
        }
        return opdracht;
    }

    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

}
