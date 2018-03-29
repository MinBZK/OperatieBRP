/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de {@link LeveringsautorisatieValidatieService}.
 */
@Bedrijfsregel(Regel.R1257)
@Bedrijfsregel(Regel.R1258)
@Bedrijfsregel(Regel.R1261)
@Bedrijfsregel(Regel.R1262)
@Bedrijfsregel(Regel.R1263)
@Bedrijfsregel(Regel.R1264)
@Bedrijfsregel(Regel.R2052)
@Bedrijfsregel(Regel.R2053)
@Bedrijfsregel(Regel.R2054)
@Bedrijfsregel(Regel.R2055)
@Bedrijfsregel(Regel.R2056)
@Bedrijfsregel(Regel.R2057)
@Bedrijfsregel(Regel.R2120)
@Bedrijfsregel(Regel.R2121)
@Bedrijfsregel(Regel.R2122)
@Bedrijfsregel(Regel.R2130)
@Bedrijfsregel(Regel.R2239)
@Bedrijfsregel(Regel.R2242)
@Bedrijfsregel(Regel.R2243)
@Bedrijfsregel(Regel.R2244)
@Bedrijfsregel(Regel.R2245)
@Bedrijfsregel(Regel.R2524)
@Bedrijfsregel(Regel.R2585)
@Service
final class LeveringsautorisatieValidatieServiceImpl implements LeveringsautorisatieValidatieService {

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    @Inject
    private PartijService partijService;

    private LeveringsautorisatieValidatieServiceImpl() {

    }

    private static AutorisatieResultaat bepaalAutorisatieResultaat(final Partij geautoriseerdeVanToegang, final Partij ondertekenaarVanToegang,
                                                                   final Partij transporteurVanToegang,
                                                                   final Partij ondertekenaar, final Partij transporteur, final boolean isZelfOndertekenaar,
                                                                   final boolean isZelfTransporteur) {
        if (ondertekenaarVanToegang == null && transporteurVanToegang == null && isZelfOndertekenaar && isZelfTransporteur) {
            return AutorisatieResultaat.geautoriseerdResultaat();
        }

        final AutorisatieResultaat autorisatieResultaat;
        final boolean isOndertekenaarGeautoriseerd =
                isPartijGeautoriseerd(ondertekenaar, ondertekenaarVanToegang, isZelfOndertekenaar, geautoriseerdeVanToegang);
        final boolean isTransporteurGeautoriseerd =
                isPartijGeautoriseerd(transporteur, transporteurVanToegang, isZelfTransporteur, geautoriseerdeVanToegang);
        if (isOndertekenaarGeautoriseerd && isTransporteurGeautoriseerd) {
            autorisatieResultaat = AutorisatieResultaat.geautoriseerdResultaat();
        } else {
            autorisatieResultaat = new AutorisatieResultaat(isOndertekenaarGeautoriseerd, isTransporteurGeautoriseerd);
        }
        return autorisatieResultaat;
    }

    /**
     * Bepaalt of partij geautoriseerd, parameters hebben betrekking op ofwel combinatie [ondertekenaar,ondertekenaar van toegangleveringsautorisatie]
     * ofwel combinatie [transporteur, transporteur van toegangleveringsautorisatie]
     * @param teAutoriserenPartij ondertekenaar/transporteur
     * @param zendendePartij ondertekenaar van toegang/transporteur van toegangleveringsautorisatie
     * @param isZelfGeautoriseerdePartij is zelf ondertekenaar/is zelf transporteur
     * @param geautoriseerdeVanToegang geautoriseerde van toegangleveringsautorisatie
     */
    private static boolean isPartijGeautoriseerd(final Partij teAutoriserenPartij, final Partij zendendePartij,
                                                 final boolean isZelfGeautoriseerdePartij, final Partij geautoriseerdeVanToegang) {
        final boolean partijOinsZijnGelijk = zendendePartij != null && zendendePartij.getOin() != null
                && zendendePartij.getOin().equals(teAutoriserenPartij.getOin());

        final boolean zendendePartijOinIsCorrect = teAutoriserenPartij.getOin().equals(geautoriseerdeVanToegang.getOin()) ?
                zendendePartij == null : (zendendePartij != null && teAutoriserenPartij.getOin().equals(zendendePartij.getOin()));

        return (partijOinsZijnGelijk || (isZelfGeautoriseerdePartij && zendendePartij == null)) && zendendePartijOinIsCorrect;
    }


    private static void assertPartijNotNull(final Partij partij) throws AutorisatieException {
        if (partij == null) {
            //De gebruikte authenticatie is niet bekend.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2120);
        }
    }

    private static boolean isPartijGeldig(final Partij partij) {
        return partij != null && AutAutUtil.isGeldigOp(BrpNu.get().alsIntegerDatumNederland(), partij.getDatumIngang(), partij.getDatumEinde());
    }

    private static void verifieerAutorisatie(final ToegangLeveringsAutorisatie gevondenAutorisatie, final boolean isOndertekenaarGevonden, final boolean
            isTransporteurGevonden) throws AutorisatieException {
        if (gevondenAutorisatie == null && isOndertekenaarGevonden && isTransporteurGevonden) {
            //De combinatie ondertekenaar en transporteur is onjuist.
            AutorisatieUtil.rapporteerRegelFout(Regel.R1257);
        } else if (!isOndertekenaarGevonden) {
            //De ondertekenaar is onjuist.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2121);
        } else if (!isTransporteurGevonden) {
            //De transporteur is onjuist.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2122);
        }
    }

    private static void assertPartijRolGeldig(final PartijRol partijRol, final Rol rol) throws AutorisatieException {
        AutorisatieUtil.assertPartijRolGeldig(partijRol);

        if (rol != null && partijRol.getRol() != rol) {
            //De gebruikte authenticatie is niet bekend.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2120);
        }
    }

    @Override
    public Autorisatiebundel controleerAutorisatie(final AutorisatieParams autorisatieParams) throws AutorisatieException {
        final Partij zendendepartij = getZendendePartij(autorisatieParams.getZendendePartijCode());
        final Partij partijOndertekenaar = autorisatieParams.getOin().getOinWaardeOndertekenaar() == null
                ? zendendepartij : getOndertekenaar(autorisatieParams.getOin().getOinWaardeOndertekenaar());
        final Partij partijTransporteur = autorisatieParams.getOin().getOinWaardeTransporteur() == null
                ? zendendepartij : getTransporteur(autorisatieParams.getOin().getOinWaardeTransporteur());
        final Leveringsautorisatie leveringsautorisatie = getLeveringsautorisatie(autorisatieParams.getLeveringautorisatieId());
        AutorisatieUtil.assertStelselCorrect(leveringsautorisatie, zendendepartij, autorisatieParams.isBrpKoppelvlakVerzoek());
        AutorisatieUtil.assertLeveringsautorisieGeldig(leveringsautorisatie);
        AutorisatieUtil.assertLeveringsautorisieNietGeblokkeerd(leveringsautorisatie);

        final Dienst dienst = getDienst(leveringsautorisatie, autorisatieParams.getSoortDienst(), autorisatieParams.getDienstId());
        AutorisatieUtil.assertDienstGeldig(dienst);
        AutorisatieUtil.assertDienstNietGeblokkeerd(dienst);
        final Dienstbundel dienstbundel = dienst != null ? dienst.getDienstbundel() : null;
        AutorisatieUtil.assertDienstbundelGeldig(dienstbundel);
        AutorisatieUtil.assertDienstbundelNietGeblokkeerd(dienstbundel);

        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;
        if (leveringsautorisatie != null && leveringsautorisatie.getStelsel() == Stelsel.GBA) {
            toegangLeveringsAutorisatie = bepaalToegangLeveringsAutorisatieGba(leveringsautorisatie, zendendepartij);
        } else {
            toegangLeveringsAutorisatie = bepaalToegangLeveringsAutorisatie(leveringsautorisatie, zendendepartij, partijOndertekenaar, partijTransporteur);
        }

        AutorisatieUtil.assertToegangLeveringsAutorisatieGeldig(toegangLeveringsAutorisatie);
        AutorisatieUtil.assertToegangLeveringsAutorisatieNietGeblokkeerd(toegangLeveringsAutorisatie);
        assertPartijRolGeldig(toegangLeveringsAutorisatie.getGeautoriseerde(), autorisatieParams.getRol());
        return new Autorisatiebundel(toegangLeveringsAutorisatie, dienst);
    }

    private Partij getZendendePartij(final String zendendepartijCode) throws AutorisatieException {
        final Partij partij = partijService.vindPartijOpCode(zendendepartijCode);
        assertPartijNotNull(partij);
        if (!isPartijGeldig(partij)) {
            //De partij is niet geldig
            AutorisatieUtil.rapporteerRegelFout(Regel.R2242);
        }
        return partij;
    }

    private Partij getOndertekenaar(final String oin) throws AutorisatieException {
        final Partij partij = partijService.vindPartijOpOin(oin);
        assertPartijNotNull(partij);
        if (!isPartijGeldig(partij)) {
            //De ondertekenaar is geen geldige partij.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2243);
        }
        return partij;
    }

    private Partij getTransporteur(final String oin) throws AutorisatieException {
        final Partij partij = partijService.vindPartijOpOin(oin);
        assertPartijNotNull(partij);
        if (!isPartijGeldig(partij)) {
            //De transporteur is geen geldige partij.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2244);
        }
        return partij;
    }

    private Leveringsautorisatie getLeveringsautorisatie(final int leveringautorisatieId) throws AutorisatieException {
        final Leveringsautorisatie leveringsautorisatie = leveringsautorisatieService.geefLeveringautorisatie(leveringautorisatieId);
        if (leveringsautorisatie == null) {
            //De opgegeven leveringsautorisatie bestaat niet.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2053);
        }
        return leveringsautorisatie;
    }


    private Dienst getDienst(final Leveringsautorisatie leveringsautorisatie, final SoortDienst soortDienst,
                             final Integer dienstId) throws AutorisatieException {
        final Dienst dienst;
        if (dienstId != null) {

            if (!leveringsautorisatieService.bestaatDienst(dienstId)) {
                //De opgegeven dienst bestaat
                AutorisatieUtil.rapporteerRegelFout(Regel.R2055);
            }
            final Dienst gevondenDienstMetId = AutAutUtil.zoekDienst(leveringsautorisatie, dienstId);
            if (gevondenDienstMetId == null) {
                //De opgeven leveringsautorisatie bevat niet de opgegeven dienst
                AutorisatieUtil.rapporteerRegelFout(Regel.R2130);
            } else if (gevondenDienstMetId.getSoortDienst() != soortDienst) {
                //De opgegeven dienst komt niet overeen met het soort bericht
                AutorisatieUtil.rapporteerRegelFout(Regel.R2054);
            }
            dienst = gevondenDienstMetId;
        } else {
            dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        }

        if (dienst == null) {
            //De leveringsautorisatie bevat de gevraagde dienst niet.
            AutorisatieUtil.rapporteerRegelFout(Regel.R2130);
        }

        return dienst;
    }


    private ToegangLeveringsAutorisatie bepaalToegangLeveringsAutorisatieGba(final Leveringsautorisatie leveringsautorisatie, final Partij zendendePartij)
            throws AutorisatieException {
        return getRelevanteToegangLeveringAutorisaties(zendendePartij, leveringsautorisatie).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Geen toegangleveringsautorisatie gevonden."));
    }

    private ToegangLeveringsAutorisatie bepaalToegangLeveringsAutorisatie(final Leveringsautorisatie leveringsautorisatie, final Partij zendendePartij,
                                                                          final Partij ondertekenaar, final Partij transporteur) throws AutorisatieException {
        final boolean isZelfOndertekenaar =
                zendendePartij.getOin() != null && zendendePartij.getOin().equals(ondertekenaar.getOin());
        final boolean isZelfTransporteur =
                zendendePartij.getOin() != null && zendendePartij.getOin().equals(transporteur.getOin());

        boolean isOndertekenaarGevonden = false;
        boolean isTransporteurGevonden = false;

        ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = null;
        final Collection<ToegangLeveringsAutorisatie> toegangen = getRelevanteToegangLeveringAutorisaties(zendendePartij, leveringsautorisatie);
        for (final ToegangLeveringsAutorisatie autorisatie : toegangen) {
            final AutorisatieResultaat autorisatieResultaat = bepaalAutorisatieResultaat(
                    autorisatie.getGeautoriseerde().getPartij(), autorisatie.getOndertekenaar(), autorisatie.getTransporteur(),
                    ondertekenaar, transporteur,
                    isZelfOndertekenaar,
                    isZelfTransporteur);
            isOndertekenaarGevonden |= autorisatieResultaat.isOndertekenaarGeautoriseerd();
            isTransporteurGevonden |= autorisatieResultaat.isTransporteurGeautoriseerd();
            if (autorisatieResultaat.isGeautoriseerd()) {
                toegangLeveringsAutorisatie = autorisatie;
            }
        }

        verifieerAutorisatie(toegangLeveringsAutorisatie, isOndertekenaarGevonden, isTransporteurGevonden);
        return toegangLeveringsAutorisatie;
    }

    private Collection<ToegangLeveringsAutorisatie> getRelevanteToegangLeveringAutorisaties(final Partij zendendepartij,
                                                                                            final Leveringsautorisatie leveringsautorisatie)
            throws AutorisatieException {

        final List<ToegangLeveringsAutorisatie> autorisaties = new ArrayList<>(
                leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendepartij));
        if (autorisaties.isEmpty()) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2120);
        }

        // verwijder alle toegangleveringsautorisatie die de verkeerde leveringAutorisatieId hebben
        final Iterator<ToegangLeveringsAutorisatie> iterator = autorisaties.iterator();
        while (iterator.hasNext()) {
            final ToegangLeveringsAutorisatie tla = iterator.next();
            if (!tla.getLeveringsautorisatie().getId().equals(leveringsautorisatie.getId())) {
                iterator.remove();
            }
        }
        if (autorisaties.isEmpty()) {
            AutorisatieUtil.rapporteerRegelFout(Regel.R2053);
        }
        return autorisaties;
    }

    /**
     * Resultaat object.
     */
    private static final class AutorisatieResultaat {
        private static final AutorisatieResultaat AUTORISATIE_RESULTAAT = new AutorisatieResultaat(true, true);
        private final boolean isOndertekenaarGeautoriseerd;
        private final boolean isTransporteurGeautoriseerd;

        private AutorisatieResultaat(final boolean isOndertekenaarGeautoriseerd, final boolean isTransporteurGeautoriseerd) {
            this.isOndertekenaarGeautoriseerd = isOndertekenaarGeautoriseerd;
            this.isTransporteurGeautoriseerd = isTransporteurGeautoriseerd;
        }

        private static AutorisatieResultaat geautoriseerdResultaat() {
            return AUTORISATIE_RESULTAAT;
        }

        boolean isGeautoriseerd() {
            return isOndertekenaarGeautoriseerd && isTransporteurGeautoriseerd;
        }

        boolean isOndertekenaarGeautoriseerd() {
            return isOndertekenaarGeautoriseerd;
        }

        boolean isTransporteurGeautoriseerd() {
            return isTransporteurGeautoriseerd;
        }
    }
}
