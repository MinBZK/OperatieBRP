/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandelingGedeblokkeerdeRegel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingPlanElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BmrAttribuut;
import nl.bzk.brp.bijhouding.bericht.model.GedeblokkeerdeMeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.NotificatieBijhoudingsplanElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.ResultaatElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.writer.VerwerkBijhoudingsplanBerichtWriter;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De business service voor het verwerken van het
 * {@link nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBerichtImpl}.
 */
@Service
public final class BijhoudingServiceImpl implements BijhoudingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String BRP_BIJHOUDING_TRANSACTION_MANAGER = "brpBijhoudingTransactionManager";

    private final AutorisatieService autorisatieService;
    private final ValidatieService validatieService;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final MutatieNotificatieService mutatieNotificatieService;
    private final BijhoudingsplanService bijhoudingsplanService;
    private final BijhoudingAntwoordBerichtService bijhoudingAntwoordBerichtService;
    private final OntrelateerService ontrelateerService;
    private final ArchiefService archiefService;

    /**
     * constructor.
     * @param autorisatieService {@link AutorisatieService}
     * @param validatieService {@link ValidatieService}
     * @param dynamischeStamtabelRepository {@link DynamischeStamtabelRepository}
     * @param mutatieNotificatieService {@link MutatieNotificatieService}
     * @param bijhoudingsplanService {@link BijhoudingsplanService}
     * @param bijhoudingAntwoordBerichtService {@link BijhoudingAntwoordBerichtService}
     * @param ontrelateerService {@link OntrelateerService}
     * @param archiefService {@link ArchiefService}
     */
    //
    @Inject
    public BijhoudingServiceImpl(final AutorisatieService autorisatieService,
                                 final ValidatieService validatieService,
                                 final DynamischeStamtabelRepository dynamischeStamtabelRepository,
                                 final MutatieNotificatieService mutatieNotificatieService,
                                 final BijhoudingsplanService bijhoudingsplanService,
                                 final BijhoudingAntwoordBerichtService bijhoudingAntwoordBerichtService,
                                 final OntrelateerService ontrelateerService,
                                 final ArchiefService archiefService) {
        this.autorisatieService = autorisatieService;
        this.validatieService = validatieService;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.mutatieNotificatieService = mutatieNotificatieService;
        this.bijhoudingsplanService = bijhoudingsplanService;
        this.bijhoudingAntwoordBerichtService = bijhoudingAntwoordBerichtService;
        this.ontrelateerService = ontrelateerService;
        this.archiefService = archiefService;
    }

    @Override
    @Transactional(transactionManager = BRP_BIJHOUDING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public BijhoudingAntwoordBericht verwerkBrpBericht(final BijhoudingVerzoekBericht bericht) {
        return verwerkBericht(bericht, true);
    }

    @Override
    @Transactional(transactionManager = BRP_BIJHOUDING_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    public BijhoudingAntwoordBericht verwerkGbaBericht(final BijhoudingVerzoekBericht bericht) {
        return verwerkBericht(bericht, false);
    }

    private BijhoudingAntwoordBericht verwerkBericht(final BijhoudingVerzoekBericht bericht, boolean autoriseerPartij) {
        LOGGER.info("Start verwerking bericht: " + bericht.getAdministratieveHandeling().getSoort());

        final List<MeldingElement> meldingen = autoriseerEnControleerReferenties(bericht, autoriseerPartij);

        if (meldingen.isEmpty()) {
            bericht.postConstruct();
        }

        archiveerInkomendBericht(bericht, meldingen.isEmpty());
        valideer(bericht, meldingen);
        final List<MeldingElement> gefilterdeMeldingen = filterGedeblokkeerdeMeldingen(bericht, meldingen);

        final BijhoudingsplanContext bijhoudingsplanContext;
        final AdministratieveHandeling administratieveHandeling;
        if (validatieService.kanVerwerkingDoorgaan(gefilterdeMeldingen)) {
            bijhoudingsplanContext = bijhoudingsplanService.maakBijhoudingsplan(bericht);

            if (!bericht.isPrevalidatie()) {
                ontrelateerService.ontrelateer(bijhoudingsplanContext, bericht);
                final List<BRPActie> acties = doeVerwerking(bericht);
                if (!acties.isEmpty()) {
                    administratieveHandeling = acties.iterator().next().getAdministratieveHandeling();
                    werkGroepAfgeleidAdministratiefBij(acties.iterator().next(), bijhoudingsplanContext);
                } else {
                    administratieveHandeling = null;
                }
                opslaanPersonen(bijhoudingsplanContext);
            } else {
                administratieveHandeling = null;
            }
        } else {
            administratieveHandeling = null;
            bijhoudingsplanContext = null;
        }

        final BijhoudingAntwoordBericht antwoordBericht =
                bijhoudingAntwoordBerichtService.maakAntwoordBericht(bericht, gefilterdeMeldingen, administratieveHandeling, bijhoudingsplanContext);

        if (!bericht.isPrevalidatie() && validatieService.kanVerwerkingDoorgaan(gefilterdeMeldingen)) {
            stuurBijhoudingsnotificatie(bericht, antwoordBericht, bijhoudingsplanContext);
        }

        // Archiveer berichten
        archiveerAntwoordBericht(antwoordBericht, bericht, administratieveHandeling, bijhoudingsplanContext);
        return antwoordBericht;
    }

    private void archiveerInkomendBericht(final BijhoudingVerzoekBericht bericht, final boolean moetPersonenWordenGearchiveerd) {
        final ArchiveringOpdracht opdracht = new ArchiveringOpdracht(Richting.INGAAND, DatumUtil.nuAlsZonedDateTimeInNederland());
        opdracht.setSoortBericht(SoortBericht.parseIdentifier(bericht.getSoort().getElementNaam()));
        vulStuurgegevensArchiveringsOpdracht(opdracht, bericht.getStuurgegevens(), bericht.getTijdstipOntvangst().getWaarde());
        opdracht.setVerwerkingswijze(Verwerkingswijze.parseNaam(bericht.getParameters().getVerwerkingswijze().getWaarde()));
        opdracht.setData(bericht.getXml());

        if (moetPersonenWordenGearchiveerd) {
            final Set<BijhoudingPersoon> bijhoudingPersonen = bericht.getTeArchiverenPersonen();
            for (final BijhoudingPersoon persoon : bijhoudingPersonen) {
                if (persoon.getId() != null && persoonIsGeenBezienVanuitPersoon(persoon, bericht.getAdministratieveHandeling().getBezienVanuitPersonen())) {
                    opdracht.addTeArchiverenPersoon(persoon.getId());
                }
            }
        }

        archiefService.archiveer(opdracht);
    }

    private boolean persoonIsGeenBezienVanuitPersoon(final BijhoudingPersoon persoon, final List<PersoonGegevensElement> bezienVanuitPersonen) {
        for (final PersoonGegevensElement bezienVanuitPersoon : bezienVanuitPersonen) {
            if (bezienVanuitPersoon.heeftPersoonEntiteit() && bezienVanuitPersoon.getPersoonEntiteit().getId().equals(persoon.getId())) {
                return false;
            }
        }
        return true;
    }

    private void vulStuurgegevensArchiveringsOpdracht(final ArchiveringOpdracht opdracht, final StuurgegevensElement stuurgegevens,
                                                      final ZonedDateTime tijdstipOntvangst) {
        // Stuurgegevens
        final Partij zendendePartij = dynamischeStamtabelRepository.getPartijByCode(stuurgegevens.getZendendePartij().getWaarde());
        opdracht.setZendendePartijId(zendendePartij == null ? null : zendendePartij.getId());
        opdracht.setZendendeSysteem(stuurgegevens.getZendendeSysteem().getWaarde());
        final Partij ontvangendePartij = dynamischeStamtabelRepository.getPartijByCode(BmrAttribuut.getWaardeOfNull(stuurgegevens.getOntvangendePartij()));
        opdracht.setOntvangendePartijId(ontvangendePartij == null ? null : ontvangendePartij.getId());
        opdracht.setReferentienummer(stuurgegevens.getReferentienummer().getWaarde());
        opdracht.setCrossReferentienummer(BmrAttribuut.getWaardeOfNull(stuurgegevens.getCrossReferentienummer()));
        opdracht.setTijdstipVerzending(stuurgegevens.getTijdstipVerzending().getWaarde());
        opdracht.setTijdstipOntvangst(tijdstipOntvangst);
    }

    private void archiveerAntwoordBericht(final BijhoudingAntwoordBericht antwoordbericht, final BijhoudingVerzoekBericht verzoekBericht,
                                          final AdministratieveHandeling administratieveHandeling, final BijhoudingsplanContext bijhoudingsplanContext) {
        final ArchiveringOpdracht opdracht = new ArchiveringOpdracht(Richting.UITGAAND, DatumUtil.nuAlsZonedDateTimeInNederland());
        opdracht.setSoortBericht(SoortBericht.parseIdentifier(antwoordbericht.getSoort().getElementNaam()));
        vulStuurgegevensArchiveringsOpdracht(opdracht, antwoordbericht.getStuurgegevens(), null);
        opdracht.setVerwerkingswijze(Verwerkingswijze.parseNaam(verzoekBericht.getParameters().getVerwerkingswijze().getWaarde()));

        final ResultaatElement resultaat = antwoordbericht.getResultaat();
        opdracht.setVerwerkingsresultaat(VerwerkingsResultaat.parseNaam(resultaat.getVerwerking().getWaarde()));
        opdracht.setBijhoudingResultaat(BijhoudingResultaat.parseNaam(BmrAttribuut.getWaardeOfNull(resultaat.getBijhouding())));
        opdracht.setHoogsteMeldingsNiveau(SoortMelding.parseNaam(resultaat.getHoogsteMeldingsniveau().getWaarde()));

        if (administratieveHandeling != null) {
            opdracht.setAdministratieveHandelingId(administratieveHandeling.getId());
        }
        opdracht.setData(antwoordbericht.getXml());
        if (bijhoudingsplanContext != null) {
            for (final BijhoudingPersoon persoon : bijhoudingsplanContext.getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden()) {
                opdracht.addTeArchiverenPersoon(persoon.getId());
            }
        }

        archiefService.archiveer(opdracht);
    }

    private void werkGroepAfgeleidAdministratiefBij(final BRPActie actie, final BijhoudingsplanContext bijhoudingsplanContext) {
        for (final BijhoudingPersoon persoonEntiteit : bijhoudingsplanContext.getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden()) {
            persoonEntiteit.werkGroepAfgeleidAdministratiefBij(actie);
        }
    }

    private void opslaanPersonen(final BijhoudingsplanContext bijhoudingsplanContext) {
        final List<BijhoudingPersoon> personenDieOpgeslagenMoetenWorden = new ArrayList<>();
        personenDieOpgeslagenMoetenWorden.addAll(bijhoudingsplanContext.getPersonenUitHetBijhoudingsplanDieVerwerktMoetenWorden());
        personenDieOpgeslagenMoetenWorden.addAll(bijhoudingsplanContext.getPersonenDieNietVerwerktMaarWelOntrelateerdZijn());
        for (final BijhoudingPersoon persoonEntiteit : personenDieOpgeslagenMoetenWorden) {
            ApplicationContextProvider.getPersoonRepository().slaPersoonOp(persoonEntiteit);
        }
    }

    @Bedrijfsregel(Regel.R1839)
    private List<MeldingElement> filterGedeblokkeerdeMeldingen(final BijhoudingVerzoekBericht bericht, final List<MeldingElement> meldingen) {
        final List<MeldingElement> result = new ArrayList<>();
        final MultiKeyMap gedeblokkeerdeRegelMap = new MultiKeyMap();

        for (final GedeblokkeerdeMeldingElement gedeblokkeerdeMelding : bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen()) {
            gedeblokkeerdeRegelMap.put(gedeblokkeerdeMelding.getReferentie(), gedeblokkeerdeMelding.getRegelCode().getWaarde(), gedeblokkeerdeMelding);
        }
        for (final MeldingElement melding : meldingen) {
            final GedeblokkeerdeMeldingElement
                    gedeblokkeerdeMeldingVoorGroep =
                    (GedeblokkeerdeMeldingElement) gedeblokkeerdeRegelMap.get(melding.getReferentie(), melding.getRegelCode().getWaarde());
            final boolean isDeblokeerbareMelding = SoortMelding.DEBLOKKEERBAAR.equals(melding.getRegel().getSoortMelding());
            if (isDeblokeerbareMelding
                    && gedeblokkeerdeMeldingVoorGroep != null
                    && melding.getRegelCode().equals(gedeblokkeerdeMeldingVoorGroep.getRegelCode())) {
                gedeblokkeerdeRegelMap.remove(melding.getReferentie(), melding.getRegelCode().getWaarde());
            } else {
                if (!(isDeblokeerbareMelding && bericht.getAdministratieveHandeling().getSoort().gbaAdministratieveHandeling())) {
                    result.add(melding);
                }
            }
        }
        for (final Object gedeblokkeerdeMeldingElement : gedeblokkeerdeRegelMap.values()) {
            // Voor alle gedeblokkeerde meldingen in het bericht die niet zijn opgetreden wordt een fout gerapporteerd
            result.add(MeldingElement.getInstance(Regel.R1839, (GedeblokkeerdeMeldingElement) gedeblokkeerdeMeldingElement));
        }

        return result;
    }

    private List<MeldingElement> autoriseerEnControleerReferenties(final BijhoudingVerzoekBericht bericht, final boolean autoriseerPartij) {
        final List<MeldingElement> result = new ArrayList<>();
        if (autoriseerPartij) {
            result.addAll(autorisatieService.autoriseer(bericht));
        }
        if (result.isEmpty()) {
            // voer controle op objectsleutels en referenties alleen uit wanneer er geen autorisatie fouten zijn
            result.addAll(bericht.laadEntiteitenVoorObjectSleutels());
            result.addAll(bericht.controleerReferentiesInBericht());
        }
        return result;
    }

    private void valideer(final BijhoudingVerzoekBericht bericht, final List<MeldingElement> meldingen) {
        if (meldingen.isEmpty()) {
            bericht.registreerPersoonElementenBijBijhoudingPersonen();
            // voer validatie alleen uit wanneer er geen objectsleutel of referentiefouten zijn
            meldingen.addAll(validatieService.valideer(bericht));
        }
    }

    private List<BRPActie> doeVerwerking(final BijhoudingVerzoekBericht bericht) {
        final List<BRPActie> results = new ArrayList<>();
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandeling(bericht);
        for (final ActieElement actieElement : bericht.getAdministratieveHandeling().getActies()) {
            final BRPActie brpActie = actieElement.verwerk(bericht, administratieveHandeling);
            if (brpActie != null) {
                results.add(brpActie);
            }
        }
        return results;
    }

    private AdministratieveHandeling maakAdministratieveHandeling(final BijhoudingVerzoekBericht bericht) {
        final AdministratieveHandelingElement administratieveHandelingElement = bericht.getAdministratieveHandeling();
        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(administratieveHandelingElement.getPartijCode().getWaarde());
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                SoortAdministratieveHandeling.valueOf(administratieveHandelingElement.getSoort().name());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, soortAdministratieveHandeling, bericht.getTijdstipOntvangst().toTimestamp());

        administratieveHandeling.setToelichtingOntlening(BmrAttribuut.getWaardeOfNull(administratieveHandelingElement.getToelichtingOntlening()));

        koppelGedeblokkeerdeMeldingen(administratieveHandeling, bericht);
        return administratieveHandeling;
    }

    private void koppelGedeblokkeerdeMeldingen(final AdministratieveHandeling administratieveHandeling, final BijhoudingVerzoekBericht verzoekBericht) {
        verzoekBericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen().forEach(
                gedeblokkeerdeMelding -> administratieveHandeling.addAdministratieveHandelingGedeblokkeerdeRegel(
                        new AdministratieveHandelingGedeblokkeerdeRegel(
                                administratieveHandeling,
                                Regel.parseCode(gedeblokkeerdeMelding.getRegelCode().getWaarde()))));
    }

    private void stuurBijhoudingsnotificatie(
            final BijhoudingVerzoekBericht bericht,
            final BijhoudingAntwoordBericht bijhoudingAntwoordBericht,
            final BijhoudingsplanContext bijhoudingsplanContext) {
        final NotificatieBijhoudingsplanElement notificatieBijhoudingsplanElement =
                bijhoudingsplanContext.maakBijhoudingsplanElementVoorBijhoudingNotificatieBericht(bijhoudingAntwoordBericht);
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                SoortAdministratieveHandeling.valueOf(bericht.getAdministratieveHandeling().getSoort().name());
        final AdministratieveHandelingPlanElement administratieveHandelingPlan =
                AdministratieveHandelingPlanElement.getInstance(
                        soortAdministratieveHandeling,
                        bericht.getZendendePartij().getCode(),
                        notificatieBijhoudingsplanElement);

        final String zendendePartij = bericht.getZendendePartij().getCode();
        for (final BijhoudingPersoon persoon : bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan()) {
            final boolean isGeboortePartijEenGbaPartij = bijhoudingsplanContext.getGbaGeboortePartij() != null;
            final Partij bijhoudingsPartij = persoon.getBijhoudingspartijVoorBijhoudingsplan();
            if (!zendendePartij.equals(bijhoudingsPartij.getCode()) || (isGeboortePartijEenGbaPartij && persoon.isEersteInschrijving())) {
                // er moet een notificatie verstuurd worden
                final String ontvangendeSysteem;
                if (BijhoudingSituatie.GBA.equals(persoon.getBijhoudingSituatie()) || isGeboortePartijEenGbaPartij) {
                    ontvangendeSysteem = "ISC";
                } else {
                    ontvangendeSysteem = "Bijhoudingsysteem";
                }

                final VerwerkBijhoudingsplanBerichtImpl verwerkBijhoudingsplanBericht =
                        new VerwerkBijhoudingsplanBerichtImpl(
                                new AbstractBmrGroep.AttributenBuilder().build(),
                                StuurgegevensElement.getInstance(
                                        Partij.PARTIJCODE_MINISTER,
                                        Stelsel.BRP.getNaam(),
                                        bijhoudingsPartij.getCode(),
                                        null,
                                        DatumUtil.nuAlsZonedDateTime()),
                                administratieveHandelingPlan);

                try {
                    final VerwerkBijhoudingsplanBerichtWriter writer = new VerwerkBijhoudingsplanBerichtWriter();
                    final StringWriter stringWriter = new StringWriter();
                    writer.write(verwerkBijhoudingsplanBericht, stringWriter);
                    final BijhoudingsplanNotificatieBericht notificatieBerichtgegegevens = new BijhoudingsplanNotificatieBericht()
                            .setOntvangendePartijCode(bijhoudingsPartij.getCode())
                            .setOntvangendePartijId(bijhoudingsPartij.getId())
                            .setOntvangendeSysteem(ontvangendeSysteem)
                            .setZendendePartijCode(verwerkBijhoudingsplanBericht.getStuurgegevens().getZendendePartij().getWaarde())
                            .setZendendeSysteem(verwerkBijhoudingsplanBericht.getStuurgegevens().getZendendeSysteem().getWaarde())
                            .setReferentieNummer(verwerkBijhoudingsplanBericht.getStuurgegevens().getReferentienummer().getWaarde())
                            .setCrossReferentieNummer(verwerkBijhoudingsplanBericht.getStuurgegevens().getCrossReferentienummer() == null ? null
                                    : verwerkBijhoudingsplanBericht.getStuurgegevens().getCrossReferentienummer().getWaarde())
                            .setTijdstipVerzending(verwerkBijhoudingsplanBericht.getStuurgegevens().getTijdstipVerzending().toTimestamp())
                            .setAdministratieveHandelingId(
                                    verwerkBijhoudingsplanBericht.getAdministratieveHandelingPlan().getBijhoudingsplan()
                                            .getBijhoudingsvoorstelResultaatBericht()
                                            .getBijhoudingAntwoordBericht().getAdministratieveHandelingID())
                            .setVerwerkBijhoudingsplanBericht(stringWriter.toString());
                    mutatieNotificatieService.verstuurBijhoudingsNotificatie(notificatieBerichtgegegevens);
                } catch (WriteException e) {
                    throw new IllegalStateException("De notificatie kan niet worden omgezet in XML.", e);
                }
            }
        }
    }
}
