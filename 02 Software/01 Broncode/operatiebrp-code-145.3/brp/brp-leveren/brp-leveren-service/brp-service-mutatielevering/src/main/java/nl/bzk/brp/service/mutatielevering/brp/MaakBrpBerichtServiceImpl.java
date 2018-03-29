/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.algemeen.BepaalGeleverdePersonenService;
import nl.bzk.brp.service.algemeen.MaakPersoonBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.springframework.stereotype.Service;

/**
 * Stap voor BRP leveringen.
 */
@Service
final class MaakBrpBerichtServiceImpl implements MaakBrpBerichtService {

    @Inject
    private MutatieleveringBerichtFactory mutatieleveringBerichtFactory;
    @Inject
    private BepaalGeleverdePersonenService bepaalGeleverdePersonenService;
    @Inject
    private MaakPersoonBerichtService maakPersoonBerichtService;

    private MaakBrpBerichtServiceImpl() {

    }

    @Override
    public List<Mutatiebericht> maakBerichten(final List<Mutatielevering> leveringList, final Mutatiehandeling mutatiehandeling)
            throws StapException {
        final List<Mutatiebericht> brpBerichten = Lists.newLinkedList();
        final Map<Autorisatiebundel, Mutatielevering> map = new HashMap<>();
        for (Mutatielevering mutatielevering : leveringList) {
            map.put(mutatielevering.getAutorisatiebundel(), mutatielevering);
        }

        final List<VerwerkPersoonBericht> berichten = mutatieleveringBerichtFactory.apply(leveringList, mutatiehandeling);
        for (VerwerkPersoonBericht verwerkPersoonBericht : berichten) {
            final Set<Persoonslijst> personenInBerichtSet = Sets.newHashSet();
            //let wel, dit zijn de bijgehouden personen in bericht en niet de bijgehouden personen voor de administratieve handeling
            final List<BijgehoudenPersoon> bijgehoudenPersonen = verwerkPersoonBericht.getBijgehoudenPersonen();
            for (final BijgehoudenPersoon bijgehoudenPersoon : bijgehoudenPersonen) {
                personenInBerichtSet.add(bijgehoudenPersoon.getPersoonslijst());
            }
            final Mutatielevering mutatielevering = map.get(verwerkPersoonBericht.getAutorisatiebundel());
            final String berichtText = maakPersoonBerichtService.maakPersoonBericht(verwerkPersoonBericht);
            final SynchronisatieBerichtGegevens stuurgegevens = maakStuurgegevens(mutatielevering, verwerkPersoonBericht, berichtText, mutatiehandeling);
            brpBerichten.add(new Mutatiebericht(mutatielevering, personenInBerichtSet, berichtText, stuurgegevens));
        }
        return brpBerichten;
    }

    @Bedrijfsregel(Regel.R1617)
    @Bedrijfsregel(Regel.R2236)
    private SynchronisatieBerichtGegevens maakStuurgegevens(final Mutatielevering mutatielevering, final VerwerkPersoonBericht bericht,
                                                            final String berichtText, final Mutatiehandeling mutatiehandeling) {
        final BepaalGeleverdePersonenService.Resultaat resultaat = bepaalGeleverdePersonenService
                .bepaal(mutatielevering.getAutorisatiebundel().getSoortDienst(),
                        mutatielevering.getAutorisatiebundel().getLeveringsautorisatie().getId(),
                        bericht.getBijgehoudenPersonen().stream().map(BijgehoudenPersoon::getPersoonslijst).collect(Collectors.toList()), null);

        final ProtocolleringOpdracht protocolleringOpdracht = maakLeveringBericht(mutatielevering, bericht, resultaat, mutatiehandeling);
        final ArchiveringOpdracht archiveringOpdracht = maakArchiveringOpdracht(mutatiehandeling, mutatielevering, bericht, berichtText, resultaat);

        final SynchronisatieBerichtGegevens.Builder params = SynchronisatieBerichtGegevens.builder()
                .metStelsel(mutatielevering.getAutorisatiebundel().getLeveringsautorisatie().getStelsel())
                .metSoortDienst(mutatielevering.getAutorisatiebundel().getDienst().getSoortDienst())
                .metProtocolleringsniveau(
                        mutatielevering.getAutorisatiebundel().getToegangLeveringsautorisatie().getLeveringsautorisatie().getProtocolleringsniveau())
                .metBrpEndpointURI(mutatielevering.getAutorisatiebundel().getToegangLeveringsautorisatie().getAfleverpunt())
                .metArchiveringOpdracht(archiveringOpdracht)
                .metProtocolleringOpdracht(protocolleringOpdracht);

        return params.build();
    }

    private ArchiveringOpdracht maakArchiveringOpdracht(final Mutatiehandeling mutatiehandeling, final Mutatielevering mutatielevering,
                                                        final VerwerkPersoonBericht bericht, final String berichtText,
                                                        final BepaalGeleverdePersonenService.Resultaat resultaat) {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setSoortBericht(SoortBericht.LVG_SYN_VERWERK_PERSOON);
        archiveringOpdracht.setAdministratieveHandelingId(mutatiehandeling.getAdministratieveHandelingId());
        archiveringOpdracht.setZendendePartijId(bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendePartij().getId());
        archiveringOpdracht.setZendendeSysteem(bericht.getBasisBerichtGegevens().getStuurgegevens().getZendendeSysteem());
        archiveringOpdracht.setOntvangendePartijId(bericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij().getId());
        archiveringOpdracht.setLeveringsAutorisatieId(mutatielevering.getAutorisatiebundel().getLeveringsautorisatieId());
        archiveringOpdracht.setRolId(mutatielevering.getAutorisatiebundel().getRol().getId());
        archiveringOpdracht.setReferentienummer(bericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer());
        archiveringOpdracht.setTijdstipVerzending(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        archiveringOpdracht.setData(berichtText);
        resultaat.getGeleverdePersoonIds().forEach(archiveringOpdracht::addTeArchiverenPersoon);
        archiveringOpdracht.setDienstId(mutatielevering.getAutorisatiebundel().getDienst().getId());
        archiveringOpdracht.setSoortSynchronisatie(bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie());
        return archiveringOpdracht;
    }

    private ProtocolleringOpdracht maakLeveringBericht(final Mutatielevering mutatielevering, final VerwerkPersoonBericht bericht,
                                                       final BepaalGeleverdePersonenService.Resultaat resultaat, final Mutatiehandeling mutatiehandeling) {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(mutatiehandeling.getAdministratieveHandelingId());
        protocolleringOpdracht.setDatumTijdEindeFormelePeriodeResultaat(BrpNu.get().getDatum());
        protocolleringOpdracht.setDienstId(mutatielevering.getAutorisatiebundel().getDienst().getId());
        protocolleringOpdracht.setToegangLeveringsautorisatieId(mutatielevering.getAutorisatiebundel().getToegangLeveringsautorisatie().getId());
        protocolleringOpdracht.setSoortSynchronisatie(bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie());
        protocolleringOpdracht.setDatumTijdKlaarzettenLevering(bericht.getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending());
        protocolleringOpdracht.setDatumAanvangMaterielePeriodeResultaat(resultaat.getDatumAanvangMaterielePeriodeResultaat());
        protocolleringOpdracht.setGeleverdePersonen(resultaat.getLeveringPersonen());
        return protocolleringOpdracht;
    }
}
