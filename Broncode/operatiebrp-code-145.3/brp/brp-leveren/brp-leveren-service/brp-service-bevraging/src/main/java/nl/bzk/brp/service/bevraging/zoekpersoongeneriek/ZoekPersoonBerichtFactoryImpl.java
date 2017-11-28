/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.MeldingBepalerService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtHistorieFilterInformatie;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import org.springframework.stereotype.Component;

/**
 * Factory voor zoek persoon (generiek).
 */
@Component("zoekPersoonBerichtFactoryImpl")
@Bedrijfsregel(Regel.R2274)
final class ZoekPersoonBerichtFactoryImpl implements ZoekPersoonBerichtFactory {

    @Inject
    private VerwerkPersoonBerichtFactory stappenlijstUitvoerService;
    @Inject
    private PartijService partijService;
    @Inject
    private MeldingBepalerService meldingBepalerService;

    private ZoekPersoonBerichtFactoryImpl() {

    }

    @Override
    public VerwerkPersoonBericht maakZoekPersoonBericht(final List<Persoonslijst> persoonslijstLijst, final Autorisatiebundel autorisatiebundel,
                                                        final BevragingVerzoek bevragingVerzoek, final Integer peilmomentMaterieel) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        maakBerichtParameters.setSoortDienst(bevragingVerzoek.getSoortDienst());
        maakBerichtParameters.setVerantwoordingLeveren(false);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        maakBerichtParameters.setMigratieGroepEnkelOpnemenBijEmigratie(true);
        maakBerichtParameters.setBijgehoudenPersoonBerichtDecorator(bijgehoudenPersoonList -> {
            final VerwerkPersoonBericht uitgaandBericht = maakVerwerkPersoonBericht(autorisatiebundel, bevragingVerzoek, bijgehoudenPersoonList);
            return Collections.singletonList(uitgaandBericht);
        });

        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> map = Maps.newHashMap();
        maakBerichtPersoonMap.put(autorisatiebundel, map);

        for (final Persoonslijst persoonslijst : persoonslijstLijst) {
            final MaakBerichtHistorieFilterInformatie historieFilterInformatie = new MaakBerichtHistorieFilterInformatie(HistorieVorm.GEEN,
                    peilmomentMaterieel == null ? BrpNu.get().alsIntegerDatumNederland() : peilmomentMaterieel,
                    BrpNu.get().getDatum());
            final MaakBerichtPersoonInformatie maakBerichtPersoon =
                    new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
            maakBerichtPersoon.setHistorieFilterInformatie(historieFilterInformatie);
            map.put(persoonslijst, maakBerichtPersoon);
        }

        maakBerichtParameters.setMaakBerichtPersoonMap(maakBerichtPersoonMap);
        maakBerichtParameters.setBijgehoudenPersonen(persoonslijstLijst);

        return stappenlijstUitvoerService.maakBerichten(maakBerichtParameters).iterator().next();
    }

    private VerwerkPersoonBericht maakVerwerkPersoonBericht(final Autorisatiebundel autorisatiebundel,
                                                            final BevragingVerzoek bevragingVerzoek,
                                                            final Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersoonList) {
        //@formatter:off
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metMeldingen(
                    meldingBepalerService.geefWaarschuwingen(bijgehoudenPersoonList.get(autorisatiebundel)))
            .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metCrossReferentienummer(bevragingVerzoek.getStuurgegevens().getReferentieNummer())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(partijService.geefBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
            .eindeStuurgegevens()
            .metResultaat(
                BerichtVerwerkingsResultaat.builder()
                    .metVerwerking(VerwerkingsResultaat.GESLAAGD)
                    .metHoogsteMeldingsniveau(SoortMelding.GEEN.getNaam())
                    .build())
        .build();
        //@formatter:on
        return new VerwerkPersoonBericht(basisBerichtGegevens, autorisatiebundel, bijgehoudenPersoonList.get(autorisatiebundel));
    }
}
