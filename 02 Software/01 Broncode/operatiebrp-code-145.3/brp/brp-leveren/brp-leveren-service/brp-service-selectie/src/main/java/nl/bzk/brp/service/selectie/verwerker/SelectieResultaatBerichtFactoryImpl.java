/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtHistorieFilterInformatie;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link SelectieResultaatBerichtFactory}.
 *
 * Voert de 'maak bericht' logica uit voor de gegeven autorisaties en persoon.
 */
@Component
final class SelectieResultaatBerichtFactoryImpl implements SelectieResultaatBerichtFactory {

    @Inject
    private VerwerkPersoonBerichtFactory verwerkPersoonBerichtFactory;

    @Inject
    private PartijService partijService;

    @Inject
    private MeldingBepalerService meldingBepalerService;

    private SelectieResultaatBerichtFactoryImpl() {
    }

    @Override
    public List<VerwerkPersoonBericht> maakBerichten(final Collection<SelectieAutorisatiebundel> autorisatiebundels,
                                                     final Persoonslijst persoonslijst) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        maakBerichtParameters.setVerantwoordingLeveren(true);
        maakBerichtParameters.setAutorisatiebundels(
                autorisatiebundels.stream().map(SelectieAutorisatiebundel::getAutorisatiebundel).collect(Collectors.toList()));
        maakBerichtParameters.setBijgehoudenPersoonBerichtDecorator(this::maakBericht);
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        for (SelectieAutorisatiebundel selectieAutorisatiebundel : autorisatiebundels) {
            maakBerichtPersoonMap.put(selectieAutorisatiebundel.getAutorisatiebundel(), maakPersoonslijstMetMaakBerichtInformatieMap(persoonslijst,
                    selectieAutorisatiebundel));
        }
        maakBerichtParameters.setMaakBerichtPersoonMap(maakBerichtPersoonMap);
        maakBerichtParameters.setBijgehoudenPersonen(Lists.newArrayList(persoonslijst));
        return verwerkPersoonBerichtFactory.maakBerichten(maakBerichtParameters);
    }

    private Map<Persoonslijst, MaakBerichtPersoonInformatie> maakPersoonslijstMetMaakBerichtInformatieMap(final Persoonslijst persoonslijst,
                                                                                                          final SelectieAutorisatiebundel autorisatiebundel) {
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> map = Maps.newHashMap();
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie =
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        if (autorisatiebundel.getAutorisatiebundel().getDienst().getSoortSelectie() == SoortSelectie.STANDAARD_SELECTIE.getId()) {
            Integer historieVorm = autorisatiebundel.getAutorisatiebundel().getDienst().getHistorievormSelectie();
            Integer peilmomentMaterieel = autorisatiebundel.getSelectieAutorisatieBericht().getPeilmomentMaterieel();
            ZonedDateTime peilmomentFormeel = autorisatiebundel.getSelectieAutorisatieBericht().getPeilmomentFormeel();
            if (historieVorm != null || peilmomentMaterieel != null || peilmomentFormeel != null) {
                final MaakBerichtHistorieFilterInformatie historieFilterInformatie = new MaakBerichtHistorieFilterInformatie(
                        historieVorm != null ? HistorieVorm.parseId(historieVorm) : HistorieVorm.GEEN,
                        peilmomentMaterieel, peilmomentFormeel);
                maakBerichtPersoonInformatie.setHistorieFilterInformatie(historieFilterInformatie);
            }
        }
        map.put(persoonslijst, maakBerichtPersoonInformatie);
        return map;
    }

    private List<VerwerkPersoonBericht> maakBericht(final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonen) {
        final List<VerwerkPersoonBericht> berichten = new ArrayList<>();
        for (Map.Entry<Autorisatiebundel, List<BijgehoudenPersoon>> autorisatiebundelListEntry : bijgehoudenPersonen.entrySet()) {
            final Autorisatiebundel autorisatiebundel = autorisatiebundelListEntry.getKey();
            final Dienst dienst = autorisatiebundel.getDienst();
            final boolean
                    directeVerzending =
                    dienst.getSoortSelectie() != SoortSelectie.STANDAARD_SELECTIE.getId() && Boolean.TRUE.equals(dienst.getIndVerzVolBerBijWijzAfniNaSelectie
                            ())
                            || (dienst.getLeverwijzeSelectie() != null && LeverwijzeSelectie.BERICHT.getId() == dienst.getLeverwijzeSelectie());
            if (directeVerzending) {
                //altijd 1 bijgehouden persoon per autorisatie
                autorisatiebundelListEntry.getValue().stream().findFirst().ifPresent(b -> voegDirecteVerzendingBerichtToe(b, berichten, autorisatiebundel));
            } else {
                final VerwerkPersoonBericht uitgaandBericht = new VerwerkPersoonBericht(null,
                        autorisatiebundelListEntry.getKey(), bijgehoudenPersonen.get(autorisatiebundelListEntry.getKey()));
                berichten.add(uitgaandBericht);
            }

        }
        return berichten;
    }

    private void voegDirecteVerzendingBerichtToe(final BijgehoudenPersoon bijgehoudenPersoon, List<VerwerkPersoonBericht> berichten,
                                                 Autorisatiebundel autorisatiebundel) {
        final Partij zendendePartij = partijService.geefBrpPartij();
        final Partij ontvangendePartij = autorisatiebundel.getPartij();
        final List<BijgehoudenPersoon> leverPersonen = Lists.newArrayList(bijgehoudenPersoon);
        final SoortAdministratieveHandeling soortAdministratieveHandeling = bepaalSoortAdministratieveHandeling(autorisatiebundel);
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                        .metMeldingen(meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoon))
                        .metParameters()
                            .metSoortSynchronisatie(SoortSynchronisatie.VOLLEDIG_BERICHT)
                            .metDienst(autorisatiebundel.getDienst())
                        .eindeParameters()
                        .metTijdstipRegistratie(DatumUtil.nuAlsZonedDateTime())
                        .metSoortNaam(soortAdministratieveHandeling.getNaam())
                        .metCategorieNaam(soortAdministratieveHandeling.getCategorie().getNaam())
                        .metPartijCode(ontvangendePartij.getCode())
                        .metStuurgegevens()
                            .metReferentienummer(UUID.randomUUID().toString())
                            .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                            .metZendendePartij(zendendePartij)
                            .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                            .metOntvangendePartij(ontvangendePartij)
                        .eindeStuurgegevens().build();
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, leverPersonen);
        //@formatter:on
        berichten.add(bericht);
    }

    private SoortAdministratieveHandeling bepaalSoortAdministratieveHandeling(Autorisatiebundel autorisatiebundel) {
        return SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId() == autorisatiebundel.getDienst().getSoortSelectie()
                ? SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE : SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE;
    }
}
