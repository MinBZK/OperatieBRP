/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.lo3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;
import org.springframework.stereotype.Service;

/**
 * Stap voor LO3 leveringen.
 */
@Service
final class MaakLo3BerichtServiceImpl implements MaakLo3BerichtService {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BerichtFactory berichtFactory;
    @Inject
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    @Inject
    private PartijService partijService;

    private MaakLo3BerichtServiceImpl() {

    }

    @Override
    public List<Mutatiebericht> maakBerichten(final List<Mutatielevering> mutatieleveringen, Mutatiehandeling handeling) throws StapException {
        final ConversieCache conversieCache = new ConversieCache();
        final List<Mutatiebericht> leveringList = Lists.newLinkedList();
        for (final Mutatielevering mutatielevering : mutatieleveringen) {
            if (Stelsel.GBA != mutatielevering.getStelsel()) {
                continue;
            }
            leveringList.addAll(maakBericht(mutatielevering, conversieCache, handeling));
        }
        return leveringList;
    }

    private List<Mutatiebericht> maakBericht(final Mutatielevering mutatielevering, final ConversieCache conversieCache, Mutatiehandeling handeling) {
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = mutatielevering.getAutorisatiebundel().getToegangLeveringsautorisatie();

        LOGGER.debug(
                "Controleer LO3 verwerking voor leveringsautorisatie {} van partij {}.",
                toegangLeveringsautorisatie.getLeveringsautorisatie().getNaam(),
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode());

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID.getVeld(), String.valueOf(toegangLeveringsautorisatie.getId()));
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(),
                String.valueOf(toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode()));
        mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), String.valueOf(toegangLeveringsautorisatie.getLeveringsautorisatie().getId()));

        LOGGER.debug(
                "Leveringsautorisatie {} van partij {} heeft afleverwijze: {}.",
                toegangLeveringsautorisatie.getLeveringsautorisatie().getNaam(),
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(),
                Stelsel.GBA);
        mdcMap.put(LeveringVeld.MDC_KANAAL.getVeld(), String.valueOf(Stelsel.GBA));
        LOGGER.debug(
                "Start LO3 stappenverwerking voor leveringsautorisatie van afnemer met code {} voor stelsel {}.",
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(),
                Stelsel.GBA);

        MDC.putData(mdcMap).close();

        final List<Mutatiebericht> resultaat = new ArrayList<>();
        final Persoonslijst per = mutatielevering.getPersonen().iterator().next();
        final AdministratieveHandeling administratieveHandeling = per.getAdministratieveHandeling();
        final IdentificatienummerMutatie identificatienummerMutatieResultaat =
                new IdentificatienummerMutatie(per.getMetaObject(), administratieveHandeling);

        // Bepaal berichten
        final List<Bericht> berichten =
                berichtFactory.maakBerichten(
                        mutatielevering.getAutorisatiebundel(),
                        mutatielevering.getTeLeverenPersonenMap(),
                        administratieveHandeling,
                        identificatienummerMutatieResultaat);

        LOGGER.debug(
                "Verwerken van {} berichten voor afnemer met code {}.",
                berichten.size(),
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode());

        for (final Bericht bericht : berichten) {
            // Converteer naar LO3
            bericht.converteerNaarLo3(conversieCache);

            // Bepaal Lo3 rubrieken voor filtering
            final List<String> lo3Filterrubrieken =
                    lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(
                            mutatielevering.getAutorisatiebundel().getDienst().getDienstbundel().getId());

            // Filter bericht
            if (!bericht.filterRubrieken(lo3Filterrubrieken)) {
                LOGGER.info(
                        "Uitgaand LO3-bericht gebouwd voor administratieve handeling [{}] "
                                + "bevat geen inhoud. Uitgaand LO3-bericht wordt daarom niet verzonden.",
                        handeling.getAdministratieveHandelingId());
                continue;
            }

            // Format
            final String lo3Bericht = bericht.maakUitgaandBericht();
            final Persoonslijst persoonslijst = bericht.getPersoonsgegevens();
            final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();

            final ProtocolleringOpdracht protocolleringOpdracht = maakProtocolleringOpdracht(mutatielevering, bericht, persoonslijst, nu, handeling);
            final ArchiveringOpdracht archiveringOpdracht = maakArchiveringOpdracht(mutatielevering, bericht, persoonslijst, nu, lo3Bericht);

            // Maak het JSON stuurgegevens bericht
            final SynchronisatieBerichtGegevens params = SynchronisatieBerichtGegevens.builder()
                    .metStelsel(mutatielevering.getAutorisatiebundel().getLeveringsautorisatie().getStelsel())
                    .metSoortDienst(mutatielevering.getAutorisatiebundel().getDienst().getSoortDienst())
                    .metProtocolleringsniveau(
                            mutatielevering.getAutorisatiebundel().getToegangLeveringsautorisatie().getLeveringsautorisatie().getProtocolleringsniveau())
                    .metProtocolleringOpdracht(protocolleringOpdracht)
                    .metArchiveringOpdracht(archiveringOpdracht)
                    .build();

            resultaat.add(new Mutatiebericht(mutatielevering, Sets.newHashSet(persoonslijst), lo3Bericht, params));
        }

        MDC.remove(LeveringVeld.MDC_KANAAL);
        return resultaat;
    }

    private ArchiveringOpdracht maakArchiveringOpdracht(final Mutatielevering mutatielevering, final Bericht bericht, final Persoonslijst persoonslijst,
                                                        final ZonedDateTime nu, final String lo3Bericht) {
        final ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, ZonedDateTime.now());
        archiveringOpdracht.setZendendePartijId(partijService.geefBrpPartij().getId());
        archiveringOpdracht.setZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        archiveringOpdracht.setOntvangendePartijId(mutatielevering.getAutorisatiebundel().getPartij().getId());
        archiveringOpdracht.setLeveringsAutorisatieId(mutatielevering.getAutorisatiebundel().getLeveringsautorisatieId());
        archiveringOpdracht.setRolId(mutatielevering.getAutorisatiebundel().getRol().getId());
        archiveringOpdracht.setTijdstipVerzending(nu);
        archiveringOpdracht.addTeArchiverenPersoon(persoonslijst.getId());
        archiveringOpdracht.setDienstId(mutatielevering.getAutorisatiebundel().getDienst().getId());
        archiveringOpdracht.setSoortSynchronisatie(bericht.getSoortSynchronisatie());
        archiveringOpdracht.setData(lo3Bericht);
        return archiveringOpdracht;
    }

    private ProtocolleringOpdracht maakProtocolleringOpdracht(final Mutatielevering mutatielevering, final Bericht bericht, final Persoonslijst persoonslijst,
                                                              final ZonedDateTime nu, final Mutatiehandeling mutatiehandeling) {
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setAdministratieveHandelingId(mutatiehandeling.getAdministratieveHandelingId());
        protocolleringOpdracht.setDatumTijdEindeFormelePeriodeResultaat(BrpNu.get().getDatum());
        protocolleringOpdracht.setDienstId(mutatielevering.getAutorisatiebundel().getDienst().getId());
        protocolleringOpdracht.setToegangLeveringsautorisatieId(mutatielevering.getAutorisatiebundel().getToegangLeveringsautorisatie().getId());
        protocolleringOpdracht.setSoortSynchronisatie(bericht.getSoortSynchronisatie());
        protocolleringOpdracht.setDatumTijdKlaarzettenLevering(nu);
        protocolleringOpdracht.setGeleverdePersonen(
                Collections.singletonList(new LeveringPersoon(persoonslijst.getId(), persoonslijst.getNuNuBeeld().bepaalTijdstipLaatsteWijziging())));
        return protocolleringOpdracht;
    }
}
