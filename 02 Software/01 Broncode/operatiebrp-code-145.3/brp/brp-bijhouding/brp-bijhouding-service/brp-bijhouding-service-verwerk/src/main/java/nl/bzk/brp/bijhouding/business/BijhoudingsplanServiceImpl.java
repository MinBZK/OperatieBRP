/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzonderingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.ActieMetOuderGegevens;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BronReferentieElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAdresActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieGeboreneActieElement;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.FiatteringsuitzonderingRepository;
import org.springframework.stereotype.Service;

/**
 * Implementatie om een bijhoudingsplan te maken.
 */
@Service
public final class BijhoudingsplanServiceImpl implements BijhoudingsplanService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final FiatteringsuitzonderingRepository fiatteringsuitzonderingRepository;

    /**
     * Implementatie van de {@link BijhoudingsplanService}.
     * @param fiatteringsuitzonderingRepository de repository waar de fiatterings uitzonderingen uit gelezen kunnen worden
     */
    @Inject
    public BijhoudingsplanServiceImpl(FiatteringsuitzonderingRepository fiatteringsuitzonderingRepository) {
        this.fiatteringsuitzonderingRepository = fiatteringsuitzonderingRepository;
    }

    @Override
    public BijhoudingsplanContext maakBijhoudingsplan(final BijhoudingVerzoekBericht bericht) {
        final BijhoudingsplanContext bijhoudingsplanContext = new BijhoudingsplanContext(bericht);
        final Partij zendendePartij = bericht.getZendendePartij();

        final AdministratieveHandelingElement administratieveHandeling = bericht.getAdministratieveHandeling();
        final List<BijhoudingPersoon> hoofdPersonen = administratieveHandeling.getHoofdActie().getHoofdPersonen();
        final List<Long> bezienVanuitPersonen = verzamelTechnischIds(bericht, administratieveHandeling.getBezienVanuitPersonen());
        final List<BijhoudingPersoon> alleBetrokkenPersonen = verzamelAlleBetrokkenPersonen(bericht, hoofdPersonen);

        bepaalStelselGeboortegemeente(administratieveHandeling, bijhoudingsplanContext);

        for (final BijhoudingPersoon persoon : alleBetrokkenPersonen) {
            final Partij bijhoudingsPartij = bepaalBijhoudingspartijVoorPersoon(persoon, administratieveHandeling);
            persoon.setBijhoudingspartijVoorBijhoudingsplan(bijhoudingsPartij);
            final BijhoudingSituatie bijhoudingSituatie;

            if (isIdentificatienummersNietAanwezigBijEersteInschrijving(administratieveHandeling)) {
                bijhoudingSituatie = BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN;
            } else if (isPersoonUitgesloten(administratieveHandeling, persoon, hoofdPersonen, bezienVanuitPersonen)) {
                bijhoudingSituatie = BijhoudingSituatie.UITGESLOTEN_PERSOON;
            } else if (isPersoonOverleden(bericht, persoon, hoofdPersonen)) {
                bijhoudingSituatie = BijhoudingSituatie.OPGESCHORT;
            } else if (isAdministratievehandelingAdresWijziging(administratieveHandeling, bijhoudingsPartij)) {
                bijhoudingSituatie = BijhoudingSituatie.MEDEDELING;
            } else if (isIndienerDeBijhoudingsPartij(zendendePartij, bijhoudingsPartij)) {
                bijhoudingSituatie = BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ;
            } else if (isBijhoudingsPartijInGbaStelsel(bijhoudingsPartij)) {
                bijhoudingSituatie = BijhoudingSituatie.GBA;
            } else if (isFiatInstellingHandmatig(bijhoudingsPartij)
                    || heeftFiatteringsUitzondering(zendendePartij, bijhoudingsPartij, administratieveHandeling)) {
                bijhoudingSituatie = BijhoudingSituatie.OPNIEUW_INDIENEN;
            } else {
                bijhoudingSituatie = BijhoudingSituatie.AUTOMATISCHE_FIAT;
            }

            bijhoudingsplanContext.addBijhoudingSituatieVoorPersoon(persoon, bijhoudingSituatie);
        }

        return bijhoudingsplanContext;
    }

    private boolean isPersoonUitgesloten(final AdministratieveHandelingElement administratieveHandeling, final BijhoudingPersoon persoon,
                                         final List<BijhoudingPersoon> hoofdPersonen, final List<Long> bezienVanuitPersonen) {
        return isPersoonGeenBezienVanuitPersoon(persoon.getId(), bezienVanuitPersonen) || isAdministratieveHandelingAsymmetrisch(
                administratieveHandeling, persoon, hoofdPersonen);
    }

    private void bepaalStelselGeboortegemeente(final AdministratieveHandelingElement administratieveHandeling,
                                               final BijhoudingsplanContext bijhoudingsplanContext) {
        // 21. Controleer of AH = "Geboorte in Nederland (met erkenning)"
        if (administratieveHandeling.isGeboorteHandeling()) {
            // 22. Bepaal stelsel geboortegemeente
            final RegistratieGeboreneActieElement actie =
                    (RegistratieGeboreneActieElement) administratieveHandeling.getActieBySoort(SoortActie.REGISTRATIE_GEBORENE);
            final Gemeente geboorteGemeente =
                    ApplicationContextProvider.getDynamischeStamtabelRepository()
                            .getGemeenteByGemeentecode(actie.getPersoonElementen().get(0).getGeboorte().getGemeenteCode().getWaarde());
            final Partij geboortePartij = geboorteGemeente.getPartij();
            if (isBijhoudingsPartijInGbaStelsel(geboortePartij)) {
                bijhoudingsplanContext.setGbaGeboortePartij(geboortePartij);
            }
        }
    }

    private boolean heeftFiatteringsUitzondering(
            final Partij zendendePartij,
            final Partij bijhoudingsPartij,
            final AdministratieveHandelingElement administratieveHandeling) {
        final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen =
                fiatteringsuitzonderingRepository.findBijhouderFiatteringsuitzonderingen(bijhoudingsPartij);

        if (!bijhouderFiatteringsuitzonderingen.isEmpty()) {
            final List<BijhouderFiatteringsuitzondering> filterList =
                    filterFiaterringsuitzonderingen(bijhouderFiatteringsuitzonderingen, administratieveHandeling, zendendePartij);
            if (!filterList.isEmpty()) {
                for (final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering : filterList) {
                    final BijhouderFiatteringsuitzonderingHistorie historie =
                            getActueelHistorieVoorkomenFiatteringsuitzondering(bijhouderFiatteringsuitzondering);
                    logBijhouderFiatteringsuitzondering(bijhouderFiatteringsuitzondering, historie);
                }
                return true;
            }
        }
        return false;
    }

    private void logBijhouderFiatteringsuitzondering(final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering,
                                                     final BijhouderFiatteringsuitzonderingHistorie historie) {
        final StringBuilder logregel =
                new StringBuilder("Fiateringsuitzondering gevonden voor bijhouder :").append(
                        bijhouderFiatteringsuitzondering.getBijhouder().getPartij().getCode());
        logregel.append(" ,met de volgende parameters:");
        logregel.append(" IndGeblokeerd :").append(historie.getIndicatieGeblokkeerd());
        logregel.append(" ,DatumIngang :").append(historie.getDatumIngang());
        logregel.append(" ,DatumEinde :").append(historie.getDatumEinde());
        logregel.append(" ,BijhouderBijhoudingsvoorstel :");
        logregel.append(
                historie.getBijhouderBijhoudingsvoorstel() != null ? historie.getBijhouderBijhoudingsvoorstel().getPartij().getCode() : null);
        logregel.append(" ,SoortAdministratieveHandeling :");
        logregel.append(historie.getSoortAdministratieveHandeling());
        logregel.append(" ,SoortDocument :");
        logregel.append(historie.getSoortDocument() != null ? historie.getSoortDocument().getNaam() : null);
        LOG.info(logregel.toString());
    }

    private List<BijhouderFiatteringsuitzondering> filterFiaterringsuitzonderingen(
            final List<BijhouderFiatteringsuitzondering> bijhouderFiatteringsuitzonderingen,
            final AdministratieveHandelingElement administratieveHandeling,
            final Partij zendendePartij) {
        final int systeemDatum = DatumUtil.vandaag();
        return bijhouderFiatteringsuitzonderingen.stream()
                .filter(BijhoudingsplanServiceImpl::isFiatteringsuitzonderingNietGeblokkeerd)
                .filter(
                        bijhouderFiatteringsuitzondering -> isFiatteringsuitzonderingGeldigVoorDatum(
                                bijhouderFiatteringsuitzondering,
                                systeemDatum))
                .filter(BijhoudingsplanServiceImpl::isFiatteringsuitzonderingVoorPartijInRolAlsBijhouder)
                .filter(
                        bijhouderFiatteringsuitzondering -> isPartijRolGeldig(
                                systeemDatum,
                                bijhouderFiatteringsuitzondering.getBijhouder()))
                .filter(
                        bijhouderFiatteringsuitzondering -> isPartijRolVanBijhouderBijhoudingsvoorstelGeldig(
                                systeemDatum,
                                bijhouderFiatteringsuitzondering))
                .filter(
                        bijhouderFiatteringsuitzondering -> isFiatteringsuitzonderingGeldigVoorBijhouderBijhoudingsvoorstel(
                                zendendePartij,
                                bijhouderFiatteringsuitzondering))
                .filter(
                        bijhouderFiatteringsuitzondering -> isFiatteringsuitzonderingGeldigVoorSoortAdministratieveHandeling(
                                administratieveHandeling,
                                bijhouderFiatteringsuitzondering))
                .filter(
                        bijhouderFiatteringsuitzondering -> isFiatteringsuitzonderingGeldigVoorSoortDocument(
                                administratieveHandeling,
                                bijhouderFiatteringsuitzondering))
                .collect(Collectors.toList());
    }

    private static BijhouderFiatteringsuitzonderingHistorie getActueelHistorieVoorkomenFiatteringsuitzondering(
            final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(fiatteringsuitzondering.getBijhouderFiatteringsuitzonderingHistorieSet());
    }

    private static boolean isFiatteringsuitzonderingNietGeblokkeerd(final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        final BijhouderFiatteringsuitzonderingHistorie actueelHistorieVoorkomenFiatteringsuitzondering =
                getActueelHistorieVoorkomenFiatteringsuitzondering(fiatteringsuitzondering);
        return !(actueelHistorieVoorkomenFiatteringsuitzondering != null
                && actueelHistorieVoorkomenFiatteringsuitzondering.getIndicatieGeblokkeerd() != null)
                || !actueelHistorieVoorkomenFiatteringsuitzondering.getIndicatieGeblokkeerd();
    }

    private static boolean isFiatteringsuitzonderingGeldigVoorDatum(
            final BijhouderFiatteringsuitzondering fiatteringsuitzondering,
            final Integer systeemDatum) {
        final BijhouderFiatteringsuitzonderingHistorie actueelHisFiatteringsuitzondering =
                getActueelHistorieVoorkomenFiatteringsuitzondering(fiatteringsuitzondering);
        return actueelHisFiatteringsuitzondering != null
                && DatumUtil.valtDatumBinnenPeriode(
                systeemDatum,
                actueelHisFiatteringsuitzondering.getDatumIngang(),
                actueelHisFiatteringsuitzondering.getDatumEinde());
    }

    private static boolean isFiatteringsuitzonderingVoorPartijInRolAlsBijhouder(final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        return fiatteringsuitzondering.getBijhouder().getPartij().isBijhouder();
    }

    private static boolean isPartijRolGeldig(final int systeemDatum, final PartijRol partijRol) {
        if (partijRol != null) {
            final PartijRolHistorie partijRolHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partijRol.getPartijBijhoudingHistorieSet());
            return partijRolHistorie != null
                    && DatumUtil.valtDatumBinnenPeriode(systeemDatum, partijRolHistorie.getDatumIngang(), partijRolHistorie.getDatumEinde());
        }
        return true;
    }

    private static boolean isPartijRolVanBijhouderBijhoudingsvoorstelGeldig(
            final int systeemDatum,
            final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        final BijhouderFiatteringsuitzonderingHistorie actueelHistorieVoorkomenFiatteringsuitzondering =
                getActueelHistorieVoorkomenFiatteringsuitzondering(fiatteringsuitzondering);
        return actueelHistorieVoorkomenFiatteringsuitzondering != null
                && isPartijRolGeldig(systeemDatum, actueelHistorieVoorkomenFiatteringsuitzondering.getBijhouderBijhoudingsvoorstel());
    }

    private static boolean isFiatteringsuitzonderingGeldigVoorBijhouderBijhoudingsvoorstel(
            final Partij zendendePartij,
            final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        final PartijRol bijhouderBijhoudingsvoorstel =
                getActueelHistorieVoorkomenFiatteringsuitzondering(fiatteringsuitzondering).getBijhouderBijhoudingsvoorstel();
        return bijhouderBijhoudingsvoorstel == null || bijhouderBijhoudingsvoorstel.getPartij().getCode().equals(zendendePartij.getCode());
    }

    private static boolean isFiatteringsuitzonderingGeldigVoorSoortAdministratieveHandeling(
            final AdministratieveHandelingElement administratieveHandeling,
            final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                getActueelHistorieVoorkomenFiatteringsuitzondering(fiatteringsuitzondering).getSoortAdministratieveHandeling();
        return soortAdministratieveHandeling == null
                || soortAdministratieveHandeling.equals(SoortAdministratieveHandeling.valueOf(administratieveHandeling.getSoort().name()));

    }

    private static boolean isFiatteringsuitzonderingGeldigVoorSoortDocument(
            final AdministratieveHandelingElement administratieveHandeling,
            final BijhouderFiatteringsuitzondering fiatteringsuitzondering) {
        final SoortDocument soortDocument = getActueelHistorieVoorkomenFiatteringsuitzondering(fiatteringsuitzondering).getSoortDocument();
        final List<BronReferentieElement> bronReferenties = administratieveHandeling.getHoofdActie().getBronReferenties();
        boolean result = false;
        if (soortDocument != null) {
            for (final BronReferentieElement bronReferentieElement : bronReferenties) {
                final SoortDocument soortDocumentBronRefElement = bronReferentieElement.getReferentie().getDocument().getSoortDocument();
                if (soortDocument.isInhoudelijkGelijkAan(soortDocumentBronRefElement)) {
                    result = true;
                    break;
                }
            }
        } else {
            result = true;
        }
        return result;
    }

    private boolean isFiatInstellingHandmatig(final Partij bijhoudingsPartij) {
        final Boolean indicatieAutomatischFiatteren = bijhoudingsPartij.getIndicatieAutomatischFiatteren();
        return indicatieAutomatischFiatteren != null && !indicatieAutomatischFiatteren;
    }

    private boolean isBijhoudingsPartijInGbaStelsel(final Partij bijhoudingsPartij) {
        final Integer datumOvergangNaarBrp = bijhoudingsPartij.getDatumOvergangNaarBrp();
        return datumOvergangNaarBrp == null || datumOvergangNaarBrp > DatumUtil.vandaag();
    }

    private boolean isIndienerDeBijhoudingsPartij(final Partij zendendePartij, final Partij bijhoudingsPartij) {
        return zendendePartij.getCode().equals(bijhoudingsPartij.getCode());
    }

    private boolean isPersoonOverleden(final BijhoudingVerzoekBericht bericht, final BijhoudingPersoon persoon, final List<BijhoudingPersoon> hoofdPersonen) {
        // Stap 70. Persoon is overleden EN feitdatum ligt na of op datum overlijden EN geen Hoofdpersoon
        final PersoonBijhoudingHistorie actueleBijhoudingHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet());
        final boolean persoonIsOverleden =
                actueleBijhoudingHistorie != null && NadereBijhoudingsaard.OVERLEDEN.equals(actueleBijhoudingHistorie.getNadereBijhoudingsaard());
        final boolean persoonIsHoofdPersoon = hoofdPersonen.contains(persoon);

        final PersoonOverlijdenHistorie actueleOverlijdenHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonOverlijdenHistorieSet());
        final Integer datumOverlijden = actueleOverlijdenHistorie == null ? null : actueleOverlijdenHistorie.getDatumOverlijden();
        final int peildatum = bericht.getAdministratieveHandeling().getPeilDatum().getWaarde();

        return persoonIsOverleden && !persoonIsHoofdPersoon && !DatumUtil.valtDatumBinnenPeriode(peildatum, null, datumOverlijden);
    }

    private List<Long> verzamelTechnischIds(final BijhoudingVerzoekBericht bericht, final List<PersoonGegevensElement> bezienVanuitPersonen) {
        final List<Long> resultaat = new ArrayList<>();
        for (final PersoonGegevensElement bezienVanuitPersoon : bezienVanuitPersonen) {
            resultaat.add(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, bezienVanuitPersoon.getObjectSleutel()).getId());
        }
        return resultaat;
    }

    private boolean isPersoonGeenBezienVanuitPersoon(final Long persoonId, final List<Long> bezienVanuitPersoonIds) {
        // Stap 60. BZVU-personen gevuld en persoon komt NIET voor in BZVU-personen?
        return !(bezienVanuitPersoonIds.isEmpty() || bezienVanuitPersoonIds.contains(persoonId));
    }

    private boolean isAdministratieveHandelingAsymmetrisch(final AdministratieveHandelingElement administratieveHandelingElement,
                                                           final BijhoudingPersoon persoon,
                                                           final List<BijhoudingPersoon> hoofdPersonen) {
        // Stap 65. Conroleer of AH altijd asymmetrisch is
        // We hoeven hier niet te controleren of de persoon een ingeschrevene is. Alleen ingeschrevene worden verzameld als gerelateerde personen
        return administratieveHandelingElement.isAsymmetrisch() && !hoofdPersonen.contains(persoon);
    }

    private boolean isIdentificatienummersNietAanwezigBijEersteInschrijving(final AdministratieveHandelingElement administratieveHandelingElement) {
        // 50. Controleer aanwezigheid BSN en A-nr voor eerste inschrijving
        return administratieveHandelingElement.isEersteInschrijving() && administratieveHandelingElement
                .getActieBySoort(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS) == null;
    }


    // 30. Bepaal onderhavige bijhoudingspartij van de persoon
    private Partij bepaalBijhoudingspartijVoorPersoon(final BijhoudingPersoon persoon, final AdministratieveHandelingElement administratieveHandeling) {
        Partij bijhoudingsPartij;
        if (administratieveHandeling.isGeboorteHandeling() && persoon.isEersteInschrijving()) {
            final RegistratieGeboreneActieElement geboreneActieElement =
                    (RegistratieGeboreneActieElement) administratieveHandeling.getActieBySoort(SoortActie.REGISTRATIE_GEBORENE);
            final BijhoudingPersoon ouwkig = geboreneActieElement.getOuwkig();
            bijhoudingsPartij =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(ouwkig.getPersoonBijhoudingHistorieSet(), geboreneActieElement.getPeilDatum().getWaarde())
                            .getPartij();
        } else {
            final Partij oudeBijhoudingspartij =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet()).getPartij();
            bijhoudingsPartij = oudeBijhoudingspartij;

            final boolean isGBAVerhuizingIntergemeentelijk = AdministratieveHandelingElementSoort.GBA_VERHUIZING_INTERGEMEENTELIJK_GBA_NAAR_BRP.equals(
                    administratieveHandeling.getSoort());
            final boolean isWijzigingGemeenteInfrastructureel = AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL.equals(
                    administratieveHandeling.getSoort());
            final RegistratieAdresActieElement adresActieElement =
                    (RegistratieAdresActieElement) administratieveHandeling.getActieBySoort(SoortActie.REGISTRATIE_ADRES);

            if ((isGBAVerhuizingIntergemeentelijk || isWijzigingGemeenteInfrastructureel) && adresActieElement != null) {
                final Partij nieuweBijhoudingspartij = ApplicationContextProvider.getDynamischeStamtabelRepository()
                        .getGemeenteByGemeentecode(adresActieElement.getAdres().getGemeenteCode().getWaarde()).getPartij();

                if (isWijzigingGemeenteInfrastructureel || (isBijhoudingsPartijInGbaStelsel(oudeBijhoudingspartij)
                        && !isBijhoudingsPartijInGbaStelsel(nieuweBijhoudingspartij))) {
                    bijhoudingsPartij = nieuweBijhoudingspartij;
                }
            }
        }
        return bijhoudingsPartij;
    }

    /* 10. Bepaal de personen die bij de administratieve handeling betrokken zijn */
    private List<BijhoudingPersoon> verzamelAlleBetrokkenPersonen(final BijhoudingVerzoekBericht bericht, final List<BijhoudingPersoon> hoofdPersonen) {
        final AdministratieveHandelingElement administratieveHandelingElement = bericht.getAdministratieveHandeling();
        final List<BijhoudingPersoon> alleBetrokkenPersonen = new ArrayList<>();
        alleBetrokkenPersonen.addAll(hoofdPersonen);
        for (final ActieElement actieElement : administratieveHandelingElement.getActies()) {
            for (final BijhoudingPersoon hoofdPersoon : hoofdPersonen) {
                // Als de actie invloed heeft op de gerelateerden, dan deze ook verzamelen.
                if (actieElement.heeftInvloedOpGerelateerden()) {
                    final List<BijhoudingPersoon> gerelateerden = hoofdPersoon.verzamelGerelateerden();
                    alleBetrokkenPersonen.addAll(gerelateerden);
                }
            }
        }

        if (wordenPersonenGerelateerdeGegevens(administratieveHandelingElement)) {
            voegOudersToeAlsBetrokkenen(alleBetrokkenPersonen,
                    (ActieMetOuderGegevens) administratieveHandelingElement.getActieBySoort(SoortActie.REGISTRATIE_GEBORENE));
            voegOudersToeAlsBetrokkenen(alleBetrokkenPersonen,
                    (ActieMetOuderGegevens) administratieveHandelingElement.getActieBySoort(SoortActie.REGISTRATIE_OUDER));
        }

        return alleBetrokkenPersonen;
    }

    private void voegOudersToeAlsBetrokkenen(final List<BijhoudingPersoon> alleBetrokkenPersonen, final ActieMetOuderGegevens actie) {
        if (actie != null) {
            actie.getOuders().stream()
                    .filter(persoon -> persoon.heeftPersoonEntiteit() && SoortPersoon.INGESCHREVENE.equals(persoon.getPersoonEntiteit().getSoortPersoon()))
                    .forEach(ouder -> alleBetrokkenPersonen.add(ouder.getPersoonEntiteit()));
        }
    }

    private boolean wordenPersonenGerelateerdeGegevens(final AdministratieveHandelingElement administratieveHandelingElement) {
        final boolean result;
        if (administratieveHandelingElement.isGeboorteHandeling()) {
            result = administratieveHandelingElement.getActieBySoort(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS) != null;
        } else {
            result = administratieveHandelingElement.getSoort() == AdministratieveHandelingElementSoort.ERKENNING;
        }
        return result;
    }

    /**
     * Bepaalt of de administratievehandeling een adreswijzing behelst.
     *
     * Dit is zo als:
     * <ul>
     * <li>AH is “Verhuizing intergemeentelijk” en het stelsel van de huidige bijhoudingspartij is “BRP”</li>
     * <li>AH is “Verhuizing intergemeentelijk GBA naar BRP”</li>
     * <li>AH is “Verhuizing naar buitenland</li>
     * </ul>
     * @param administratieveHandelingElement AdministratieveHandelingElement
     * @param bijhoudingsPartij Partij
     * @return true indien voldaan wordt aan een van de beschreven criteria.
     */
    private boolean isAdministratievehandelingAdresWijziging(final AdministratieveHandelingElement administratieveHandelingElement,
                                                             final Partij bijhoudingsPartij) {
        return (AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK.equals(administratieveHandelingElement.getSoort())
                && !isBijhoudingsPartijInGbaStelsel(bijhoudingsPartij))
                || AdministratieveHandelingElementSoort.GBA_VERHUIZING_INTERGEMEENTELIJK_GBA_NAAR_BRP.equals(administratieveHandelingElement.getSoort())
                || AdministratieveHandelingElementSoort.VERHUIZING_NAAR_BUITENLAND.equals(administratieveHandelingElement.getSoort());
    }

}
