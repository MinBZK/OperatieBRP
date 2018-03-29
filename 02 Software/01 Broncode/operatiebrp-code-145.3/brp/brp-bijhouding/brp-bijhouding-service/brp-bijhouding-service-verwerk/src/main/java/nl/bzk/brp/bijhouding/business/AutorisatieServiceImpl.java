/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandelingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRolHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.dal.ToegangBijhoudingsautorisatieRepository;
import org.springframework.stereotype.Service;

/**
 * Autorisatie service voor de bijhouding.
 */
@Service
public final class AutorisatieServiceImpl implements AutorisatieService {
    private static final Logger LOG = LoggerFactory.getLogger();

    private final DynamischeStamtabelRepository stamtabelRepository;

    private final ToegangBijhoudingsautorisatieRepository toegangBijhoudingsautorisatieRepository;

    /**
     * constructor.
     * @param stamtabelRepository stamtabel repository
     * @param toegangBijhoudingsautorisatieRepository toegang bijhoudings repository
     */
    @Inject
    public AutorisatieServiceImpl(DynamischeStamtabelRepository stamtabelRepository,
                                  ToegangBijhoudingsautorisatieRepository toegangBijhoudingsautorisatieRepository) {
        this.stamtabelRepository = stamtabelRepository;
        this.toegangBijhoudingsautorisatieRepository = toegangBijhoudingsautorisatieRepository;
    }

    @Override
    public List<MeldingElement> autoriseer(final BijhoudingVerzoekBericht bericht) {
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                SoortAdministratieveHandeling.valueOf(bericht.getAdministratieveHandeling().getSoort().toString());
        final Partij zendendePartij = stamtabelRepository.getPartijByCode(bericht.getStuurgegevens().getZendendePartij().getWaarde());
        return autoriseerPartij(bericht, zendendePartij, soortAdministratieveHandeling);
    }

    @Bedrijfsregel(Regel.R2250)
    @Bedrijfsregel(Regel.R2268)
    @Bedrijfsregel(Regel.R2343)
    private List<MeldingElement> autoriseerPartij(
            final BijhoudingVerzoekBericht bericht,
            final Partij partijWaarvoorAutorisatieMoetBestaan,
            final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final List<String> meldingen = new ArrayList<>();

        boolean isOinGevuld = bericht.getOinWaardeOndertekenaar() != null && bericht.getOinWaardeTransporteur() != null;
        isOinGevuld = pasTijdelijkeOinPatchToe(bericht, partijWaarvoorAutorisatieMoetBestaan, isOinGevuld);

        final boolean isPartijWaarvoorAutorisatieMoetBestaanGeldig = isPartijGeldig(partijWaarvoorAutorisatieMoetBestaan);
        voegMeldingToeBijAutorisatieFout(
                meldingen,
                isPartijWaarvoorAutorisatieMoetBestaanGeldig,
                Regel.R2268,
                soortAdministratieveHandeling,
                partijWaarvoorAutorisatieMoetBestaan,
                null,
                null);

        final List<ToegangBijhoudingsautorisatie> gefilterdeAutorisaties = new ArrayList<>();
        if (isPartijWaarvoorAutorisatieMoetBestaanGeldig) {
            final List<ToegangBijhoudingsautorisatie> autorisatiesVoorPartij = new ArrayList<>();
            if (isOinGevuld && partijWaarvoorAutorisatieMoetBestaan.isBijhouder()) {
                autorisatiesVoorPartij.addAll(toegangBijhoudingsautorisatieRepository.findByGeautoriseerde(partijWaarvoorAutorisatieMoetBestaan));
            }

            voegMeldingToeBijAutorisatieFout(
                    meldingen,
                    !autorisatiesVoorPartij.isEmpty(),
                    Regel.R2250,
                    soortAdministratieveHandeling,
                    partijWaarvoorAutorisatieMoetBestaan,
                    null,
                    null);

            if (!autorisatiesVoorPartij.isEmpty()) {
                gefilterdeAutorisaties.addAll(filter(meldingen, bericht, autorisatiesVoorPartij, soortAdministratieveHandeling));
            }
        }

        if (gefilterdeAutorisaties.isEmpty()) {
            meldingen.forEach(LOG::info);
            return Collections.singletonList(MeldingElement.getInstance(Regel.R2343, bericht.getAdministratieveHandeling()));
        } else {
            return Collections.emptyList();
        }
    }

    private boolean pasTijdelijkeOinPatchToe(BijhoudingVerzoekBericht bericht, Partij partijWaarvoorAutorisatieMoetBestaan, boolean isOinGevuld) {
        /*
         * ========== START: Tijdelijk toestaan van ontbrekende OIN t.b.v. release naar Proeftuin. ==========
         */
        if (!isOinGevuld && partijWaarvoorAutorisatieMoetBestaan != null) {
            final String oinVerzendePartij = partijWaarvoorAutorisatieMoetBestaan.getOin();
            LOG.warn("********************************** SECURITY WAARSCHUWING  **********************************");
            LOG.warn("OIN waardes ontbreken in HEADER: deze worden nu gevuld met de OIN van de verzendende partij: " + oinVerzendePartij);
            bericht.setOinWaardeOndertekenaar(oinVerzendePartij);
            bericht.setOinWaardeTransporteur(oinVerzendePartij);
            return oinVerzendePartij != null;
        } else {
            return isOinGevuld;
        }
        /*
         * ========== EINDE: Tijdelijk toestaan van ontbrekende OIN t.b.v. release naar Proeftuin. ==========
         */
    }

    private static boolean isPartijGeldig(final Partij partij) {
        if (partij == null) {
            return false;
        }
        final PartijHistorie actueelHistorieVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partij.getHisPartijen());
        return actueelHistorieVoorkomen != null
                && DatumUtil.valtDatumBinnenPeriode(
                DatumUtil.vandaag(),
                actueelHistorieVoorkomen.getDatumIngang(),
                actueelHistorieVoorkomen.getDatumEinde());

    }

    @Bedrijfsregel(Regel.R2299)
    private static boolean isAutorisatieGeldigVoorDatum(final List<String> meldingen, final int systeemDatum, final Bijhoudingsautorisatie autorisatie) {
        final BijhoudingsautorisatieHistorie actueelAutorisatieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(autorisatie.getBijhoudingsautorisatieHistorieSet());
        final boolean result =
                actueelAutorisatieVoorkomen != null
                        && DatumUtil.valtDatumBinnenPeriode(
                        systeemDatum,
                        actueelAutorisatieVoorkomen.getDatumIngang(),
                        actueelAutorisatieVoorkomen.getDatumEinde());
        if (!result) {
            meldingen.add(String.format("Autorisatie faalt voor regel: %s, %s", Regel.R2299.getCode(), Regel.R2299.getMelding()));
        }
        return result;
    }

    @Bedrijfsregel(Regel.R2248)
    private static boolean isToegangAutorisatieNietGeblokkeerd(final List<String> meldingen, final ToegangBijhoudingsautorisatie toegangAutorisatie) {
        final ToegangBijhoudingsautorisatieHistorie actueelAutorisatieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(toegangAutorisatie.getToegangBijhoudingsautorisatieHistorieSet());
        return voegMeldingToeBijAutorisatieFout(
                meldingen,
                actueelAutorisatieVoorkomen != null && !Boolean.TRUE.equals(actueelAutorisatieVoorkomen.getIndicatieGeblokkeerd()),
                Regel.R2248,
                null,
                null,
                toegangAutorisatie,
                null);
    }

    @Bedrijfsregel(Regel.R2247)
    private static boolean isToegangAutorisatieGeldigVoorDatum(
            final List<String> meldingen,
            final int systeemDatum,
            final ToegangBijhoudingsautorisatie toegangAutorisatie) {
        final ToegangBijhoudingsautorisatieHistorie actueelAutorisatieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(toegangAutorisatie.getToegangBijhoudingsautorisatieHistorieSet());
        final boolean result =
                actueelAutorisatieVoorkomen != null
                        && DatumUtil.valtDatumBinnenPeriode(
                        systeemDatum,
                        actueelAutorisatieVoorkomen.getDatumIngang(),
                        actueelAutorisatieVoorkomen.getDatumEinde());
        return voegMeldingToeBijAutorisatieFout(meldingen, result, Regel.R2247, null, null, toegangAutorisatie, null);
    }

    @Bedrijfsregel(Regel.R2115)
    private static boolean isAutorisatieNietGeblokkeerd(final List<String> meldingen, final Bijhoudingsautorisatie autorisatie) {
        final BijhoudingsautorisatieHistorie actueelAutorisatieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(autorisatie.getBijhoudingsautorisatieHistorieSet());
        return voegMeldingToeBijAutorisatieFout(
                meldingen,
                actueelAutorisatieVoorkomen != null && !Boolean.TRUE.equals(actueelAutorisatieVoorkomen.getIndicatieGeblokkeerd()),
                Regel.R2115,
                null,
                null,
                null,
                autorisatie);
    }

    @Bedrijfsregel(Regel.R2251)
    @Bedrijfsregel(Regel.R2269)
    private static boolean isToegangAutorisatieGeldigVoorOndertekenaar(
            final List<String> meldingen,
            final String oinOndertekenaar,
            final ToegangBijhoudingsautorisatie toegangAutorisatie) {
        final Partij ondertekenaar =
                toegangAutorisatie.getOndertekenaar() == null ? toegangAutorisatie.getGeautoriseerde().getPartij() : toegangAutorisatie.getOndertekenaar();

        return voegMeldingToeBijAutorisatieFout(
                meldingen,
                oinOndertekenaar.equals(ondertekenaar.getOin()),
                Regel.R2251,
                null,
                null,
                toegangAutorisatie,
                null) && voegMeldingToeBijAutorisatieFout(meldingen, isPartijGeldig(ondertekenaar), Regel.R2269, null, null, toegangAutorisatie, null);
    }

    @Bedrijfsregel(Regel.R2252)
    @Bedrijfsregel(Regel.R2270)
    private static boolean isToegangAutorisatieGeldigVoorTransporteur(
            final List<String> meldingen,
            final String oinTransporteur,
            final ToegangBijhoudingsautorisatie toegangAutorisatie) {
        final Partij transporteur =
                toegangAutorisatie.getTransporteur() == null ? toegangAutorisatie.getGeautoriseerde().getPartij() : toegangAutorisatie.getTransporteur();
        return voegMeldingToeBijAutorisatieFout(
                meldingen,
                oinTransporteur.equals(transporteur.getOin()),
                Regel.R2252,
                null,
                null,
                toegangAutorisatie,
                null) && voegMeldingToeBijAutorisatieFout(meldingen, isPartijGeldig(transporteur), Regel.R2270, null, null, toegangAutorisatie, null);
    }

    @Bedrijfsregel(Regel.R2106)
    private static boolean isGeautoriseerdVoorSoortAdministratieveHandeling(
            final List<String> meldingen,
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final ToegangBijhoudingsautorisatie toegangAutorisatie) {
        final List<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> actueleAutorisatiesVoorSoortHandeling =
                getActueleVoorkomens(toegangAutorisatie.getBijhoudingsautorisatie().getBijhoudingsautorisatieSoortAdministratieveHandelingSet());
        boolean result = false;
        for (final BijhoudingsautorisatieSoortAdministratieveHandelingHistorie geldigeAutorisatie : actueleAutorisatiesVoorSoortHandeling) {
            if (geldigeAutorisatie.getBijhoudingsautorisatieSoortAdministratieveHandeling()
                    .getSoortAdministratievehandeling()
                    .equals(soortAdministratieveHandeling)) {
                result = true;
                break;
            }
        }
        return voegMeldingToeBijAutorisatieFout(meldingen, result, Regel.R2106, soortAdministratieveHandeling, null, toegangAutorisatie, null);
    }

    @Bedrijfsregel(Regel.R2271)
    private static boolean isToegangAutorisatieGeldigVoorPartijRol(
            final List<String> meldingen,
            final int systeemDatum,
            final ToegangBijhoudingsautorisatie toegangAutorisatie) {
        final PartijRolHistorie partijRolHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(toegangAutorisatie.getGeautoriseerde().getPartijBijhoudingHistorieSet());
        return voegMeldingToeBijAutorisatieFout(
                meldingen,
                partijRolHistorie != null
                        && DatumUtil.valtDatumBinnenPeriode(systeemDatum, partijRolHistorie.getDatumIngang(), partijRolHistorie.getDatumEinde()),
                Regel.R2271,
                null,
                null,
                toegangAutorisatie,
                null);
    }

    private static List<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> getActueleVoorkomens(
            final Collection<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen) {
        final List<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> result = new ArrayList<>();
        for (BijhoudingsautorisatieSoortAdministratieveHandeling geautoriseerdeHandeling : geautoriseerdeHandelingen) {
            final Set<BijhoudingsautorisatieSoortAdministratieveHandelingHistorie> geautoriseerdeSoortHandelingen =
                    geautoriseerdeHandeling.getBijhoudingsautorisatieSoortAdministratieveHandelingHistorieSet();
            final BijhoudingsautorisatieSoortAdministratieveHandelingHistorie actueleGeautoriseerdeSoortHandeling =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(geautoriseerdeSoortHandelingen);
            if (actueleGeautoriseerdeSoortHandeling != null) {
                result.add(actueleGeautoriseerdeSoortHandeling);
            }
        }
        return result;
    }

    private List<ToegangBijhoudingsautorisatie> filter(
            final List<String> meldingen,
            final BijhoudingVerzoekBericht bericht,
            final List<ToegangBijhoudingsautorisatie> toegangAutorisaties,
            final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final int systeemDatum = DatumUtil.vandaag();
        return toegangAutorisaties.stream()
                .filter(toegangAutorisatie -> isToegangAutorisatieGeldigVoorDatum(meldingen, systeemDatum, toegangAutorisatie))
                .filter(
                        toegangAutorisatie -> isAutorisatieGeldigVoorDatum(
                                meldingen,
                                systeemDatum,
                                toegangAutorisatie.getBijhoudingsautorisatie()))
                .filter(toegangAutorisatie -> isToegangAutorisatieNietGeblokkeerd(meldingen, toegangAutorisatie))
                .filter(toegangAutorisatie -> isAutorisatieNietGeblokkeerd(meldingen, toegangAutorisatie.getBijhoudingsautorisatie()))
                .filter(
                        toegangAutorisatie -> isToegangAutorisatieGeldigVoorOndertekenaar(
                                meldingen,
                                bericht.getOinWaardeOndertekenaar(),
                                toegangAutorisatie))
                .filter(
                        toegangAutorisatie -> isToegangAutorisatieGeldigVoorTransporteur(
                                meldingen,
                                bericht.getOinWaardeTransporteur(),
                                toegangAutorisatie))
                .filter(
                        toegangAutorisatie -> isGeautoriseerdVoorSoortAdministratieveHandeling(
                                meldingen,
                                soortAdministratieveHandeling,
                                toegangAutorisatie))
                .filter(toegangAutorisatie -> isToegangAutorisatieGeldigVoorPartijRol(meldingen, systeemDatum, toegangAutorisatie))
                .collect(Collectors.toList());
    }

    private static boolean voegMeldingToeBijAutorisatieFout(
            final List<String> meldingen,
            final boolean autorisatieResultaat,
            final Regel regel,
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final Partij partijWaarvoorAutorisatieMoetBestaan,
            final ToegangBijhoudingsautorisatie toegangAutorisatie,
            final Bijhoudingsautorisatie autorisatie) {
        if (!autorisatieResultaat) {
            final StringBuilder logregel = new StringBuilder("Autorisatie faalt voor regel: ").append(regel.getCode());
            if (partijWaarvoorAutorisatieMoetBestaan != null) {
                logregel.append(" ,Partij :").append(partijWaarvoorAutorisatieMoetBestaan.getCode());
            }
            if (soortAdministratieveHandeling != null) {
                logregel.append(" ,AdmHnd :").append(soortAdministratieveHandeling.getNaam());
            }
            if (toegangAutorisatie != null) {
                logregel.append(" ,ToegangsAut :").append(toegangAutorisatie.getId());
            }
            if (autorisatie != null) {
                logregel.append(" ,BijhAut :").append(autorisatie.getId());
            }

            meldingen.add(logregel.append(" ,").append(regel.getMelding()).toString());
        }
        return autorisatieResultaat;
    }
}
