/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.springframework.stereotype.Service;

/**
 * VerwerkerPublicatieServiceImpl.
 */
@Service
final class VerwerkerPublicatieServiceImpl implements VerwerkerPublicatieService {

    @Inject
    private SelectieSchrijfTaakPublicatieService selectieSchrijfTaakPublicatieService;

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    private VerwerkerPublicatieServiceImpl() {

    }

    @Override
    public int publiceerSchrijfTaken(final SelectieVerwerkTaakBericht selectieTaak, final Collection<VerwerkPersoonResultaat> resultaten) {
        final ArrayListMultimap<Integer, VerwerkPersoonResultaat> berichtenPerSelectietaak = ArrayListMultimap.create();
        for (VerwerkPersoonResultaat resultaat : resultaten) {
            berichtenPerSelectietaak.put(resultaat.getSelectieTaakId(), resultaat);
        }

        final List<SelectieFragmentSchrijfBericht> schrijfTaken = new ArrayList<>();
        for (SelectieAutorisatieBericht selectieAutorisatie : selectieTaak.getSelectieAutorisaties()) {
            final List<VerwerkPersoonResultaat> berichtenVoorAutorisatie = berichtenPerSelectietaak.get(selectieAutorisatie.getSelectietaakId());
            if (!berichtenVoorAutorisatie.isEmpty()) {
                final SelectieFragmentSchrijfBericht bericht =
                        maakSelectieFragmentSchrijfBericht(selectieTaak, selectieAutorisatie, berichtenVoorAutorisatie);
                schrijfTaken.add(bericht);
            }
        }
        //publiceer xml berichten naar schrijf node
        if (!schrijfTaken.isEmpty()) {
            selectieSchrijfTaakPublicatieService.publiceerSchrijfTaken(schrijfTaken);
        }
        return schrijfTaken.size();
    }

    private SelectieFragmentSchrijfBericht maakSelectieFragmentSchrijfBericht(final SelectieVerwerkTaakBericht selectieTaak,
                                                                              final SelectieAutorisatieBericht selectieAutorisatie,
                                                                              final List<VerwerkPersoonResultaat> berichtenVoorAutorisatie) {
        final SelectieFragmentSchrijfBericht bericht = new SelectieFragmentSchrijfBericht();
        bericht.setSelectieRunId(selectieTaak.getSelectieRunId());
        bericht.setDienstId(selectieAutorisatie.getDienstId());
        bericht.setToegangLeveringsAutorisatieId(selectieAutorisatie.getToegangLeveringsAutorisatieId());
        bericht.setSelectietaakId(selectieAutorisatie.getSelectietaakId());
        bericht.setSelectietaakDatumUitvoer(selectieTaak.getSelectieStartDatum());
        bericht.setBerichten(berichtenVoorAutorisatie.stream().map(VerwerkPersoonResultaat::getPersoonFragment).collect(Collectors.toList()));
        //zet protocolleer gegevens indien van toepassing
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie =
                leveringsautorisatieService.geefToegangLeveringsAutorisatie(selectieAutorisatie.getToegangLeveringsAutorisatieId());
        if (toegangLeveringsAutorisatie.getGeautoriseerde().getRol() == Rol.AFNEMER &&
                toegangLeveringsAutorisatie.getLeveringsautorisatie().getProtocolleringsniveau() != Protocolleringsniveau.GEHEIM) {
            Map<Long, ZonedDateTime> teProtocollerenPersonen = Maps.newHashMap();
            for (VerwerkPersoonResultaat verwerkPersoonResultaat : berichtenVoorAutorisatie) {
                final ZonedDateTime zonedDateTime = verwerkPersoonResultaat.getPersoonslijst().bepaalTijdstipLaatsteWijziging();
                teProtocollerenPersonen.put(verwerkPersoonResultaat.getPersoonslijst().getId(), zonedDateTime);
            }
            bericht.setProtocolleringPersonen(teProtocollerenPersonen);
        }
        return bericht;
    }

}
