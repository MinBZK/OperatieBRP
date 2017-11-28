/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.service.dal.DienstRepository;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link SelectieTaakBerekenService}.
 */
@Service
@Transactional
final class SelectieTaakBerekenServiceImpl implements SelectieTaakBerekenService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final SelectieTaakRepository selectieTaakRepository;
    private final DienstRepository dienstRepository;
    private final OverlappendeSelectieTaakFilterService overlappendeSelectieTaakFilterService;
    private final SelectieTaakAutorisatieFilterService selectieTaakAutorisatieFilterService;

    @Inject
    SelectieTaakBerekenServiceImpl(SelectieTaakRepository selectieTaakRepository, DienstRepository dienstRepository,
                                   OverlappendeSelectieTaakFilterService overlappendeSelectieTaakFilterService,
                                   SelectieTaakAutorisatieFilterService selectieTaakAutorisatieFilterService) {
        this.selectieTaakRepository = selectieTaakRepository;
        this.dienstRepository = dienstRepository;
        this.overlappendeSelectieTaakFilterService = overlappendeSelectieTaakFilterService;
        this.selectieTaakAutorisatieFilterService = selectieTaakAutorisatieFilterService;
    }

    private static void berekenSelectieTaken(List<SelectieTaakDTO> selectietaken, Dienst dienst, LocalDate beginDatum, LocalDate eindDatum) {
        LocalDate eersteSelectieDatum = DatumUtil.vanIntegerNaarLocalDate(dienst.getEersteSelectieDatum());
        EenheidSelectieInterval eenheidSelectieInterval = EenheidSelectieInterval.parseId(dienst.getEenheidSelectieInterval());
        ChronoUnit chronoUnit = SelectieTaakServiceUtil.EENHEID_SELECTIE_INTERVAL_CHRONO_UNIT_MAP.get(eenheidSelectieInterval);
        BerekendeEersteSelectieDatum berekendeEersteSelectieDatum = new BerekendeEersteSelectieDatum(eersteSelectieDatum, 0);
        if (eersteSelectieDatum.isBefore(beginDatum)) {
            // Bepaal een nieuwe eerste selectiedatum als de originele voor het window ligt.
            berekendeEersteSelectieDatum = bepaalEersteSelectiedatum(eersteSelectieDatum, beginDatum, dienst, chronoUnit);
        }
        if (berekendeEersteSelectieDatum.eersteSelectieDatum.isBefore(beginDatum)) {
            // Eerste selectiedatum nog steeds voor begindatum (geen herhaling), stop.
            return;
        }
        BerekendeEersteSelectieDatum finalBerekendeEersteSelectieDatum = berekendeEersteSelectieDatum;
        Optional<LocalDate>
                eerstePeilmomentMaterieelResultaat =
                Optional.ofNullable(dienst.getSelectiePeilmomentMaterieelResultaat())
                        .map(peilmoment -> bepaalPeilmoment(DatumUtil.vanIntegerNaarLocalDate(peilmoment), dienst.getSelectieInterval(), chronoUnit,
                                finalBerekendeEersteSelectieDatum.aantalPeriodesVerschilMetOrigineel, LocalDate.class));
        Optional<ZonedDateTime>
                eerstePeilmomentFormeelResultaat =
                Optional.ofNullable(dienst.getSelectiePeilmomentFormeelResultaat())
                        .map(peilmoment -> bepaalPeilmoment(DatumUtil.vanTimestampNaarZonedDateTime(peilmoment),
                                dienst.getSelectieInterval(), chronoUnit, finalBerekendeEersteSelectieDatum.aantalPeriodesVerschilMetOrigineel,
                                ZonedDateTime.class));
        eersteSelectieDatum = berekendeEersteSelectieDatum.eersteSelectieDatum;
        LocalDate selectieDatum = null;
        LocalDate peilMomentMaterieelResultaat = null;
        ZonedDateTime peilMomentFormeelResultaat = null;
        int iteratie = 1;
        do {
            if (selectieDatum == null) {
                selectieDatum = eersteSelectieDatum;
                peilMomentMaterieelResultaat = eerstePeilmomentMaterieelResultaat.orElse(null);
                peilMomentFormeelResultaat = eerstePeilmomentFormeelResultaat.orElse(null);
            } else if (dienst.getSelectieInterval() != null) {
                selectieDatum = eersteSelectieDatum.plus((long) dienst.getSelectieInterval() * iteratie, chronoUnit);
                if (eerstePeilmomentMaterieelResultaat.isPresent()) {
                    peilMomentMaterieelResultaat = eerstePeilmomentMaterieelResultaat.get().plus((long) dienst.getSelectieInterval() * iteratie, chronoUnit);
                }
                if (eerstePeilmomentFormeelResultaat.isPresent()) {
                    peilMomentFormeelResultaat = eerstePeilmomentFormeelResultaat.get().plus((long) dienst.getSelectieInterval() * iteratie, chronoUnit);
                }
                iteratie++;
            }
            if (selectieDatum.isBefore(eindDatum)) {
                maakSelectieTaak(selectietaken, dienst, selectieDatum, peilMomentMaterieelResultaat, peilMomentFormeelResultaat,
                        iteratie + berekendeEersteSelectieDatum.aantalPeriodesVerschilMetOrigineel);
            } else {
                break;
            }
        } while (dienst.getSelectieInterval() != null);
    }

    private static BerekendeEersteSelectieDatum bepaalEersteSelectiedatum(LocalDate eersteSelectieDatum, LocalDate beginDatum,
                                                                          Dienst dienst, ChronoUnit chronoUnit) {
        LocalDate nieuweEersteSelectiedatum = eersteSelectieDatum;
        int aantalPeriodes = 0;
        while (nieuweEersteSelectiedatum.isBefore(beginDatum) && chronoUnit != null && dienst.getEenheidSelectieInterval() != null) {
            nieuweEersteSelectiedatum = nieuweEersteSelectiedatum.plus(dienst.getSelectieInterval(), chronoUnit);
            aantalPeriodes++;
        }
        return new BerekendeEersteSelectieDatum(nieuweEersteSelectiedatum, aantalPeriodes);
    }

    private static <T extends Temporal> T bepaalPeilmoment(T peilmoment, Integer interval, ChronoUnit chronoUnit,
                                                           Integer aantalPeriodesVerschilMetOrigineel, Class<T> clazz) {
        Temporal nieuwPeilmoment = peilmoment;
        if (interval != null && chronoUnit != null && aantalPeriodesVerschilMetOrigineel != null) {
            nieuwPeilmoment =
                    nieuwPeilmoment.plus((long) aantalPeriodesVerschilMetOrigineel * interval, chronoUnit);
        }
        return clazz.cast(nieuwPeilmoment);
    }

    private static void maakSelectieTaak(List<SelectieTaakDTO> selectietaken, Dienst dienst, LocalDate selectieDatum, LocalDate peilMomentMaterieelResultaat,
                                         ZonedDateTime peilMomentFormeelResultaat, int volgnummer) {
        Collection<ToegangLeveringsAutorisatie>
                toegangLeveringsAutorisaties =
                dienst.getDienstbundel().getLeveringsautorisatie().getToegangLeveringsautorisatieSet();
        toegangLeveringsAutorisaties.stream().map(tla -> new SelectieTaakDTO(dienst, tla, selectieDatum, peilMomentMaterieelResultaat,
                peilMomentFormeelResultaat, volgnummer)).forEach(selectietaken::add);
    }

    private static void bepaalGemist(Collection<SelectieTaakDTO> taken) {
        for (SelectieTaakDTO taak : taken) {
            LocalDate datumPlanning = Optional.ofNullable(taak.getDatumPlanning()).orElse(taak.getBerekendeSelectieDatum());
            SelectietaakStatus status = SelectietaakStatus.parseId((int) taak.getStatus());
            if ((datumPlanning.isBefore(LocalDate.now()) && SelectieTaakServiceUtil.STATUS_ONVERWERKT.contains(status))
                    || SelectieTaakServiceUtil.STATUS_FOUTIEF.contains(status)) {
                taak.setOpnieuwPlannen(true);
            }
        }
    }

    @Override
    public Collection<SelectieTaakDTO> berekenSelectieTaken(LocalDate beginDatum, LocalDate eindDatum) {
        Collection<Dienst>
                diensten =
                dienstRepository.getSelectieDienstenBinnenPeriode(DatumUtil.vanDatumNaarInteger(beginDatum), DatumUtil.vanDatumNaarInteger(eindDatum));
        List<SelectieTaakDTO> berekendeTaakDtos = Lists.newArrayList();
        for (Dienst dienst : diensten) {
            if (dienst.getEersteSelectieDatum() == null || dienst.getSoortSelectie() == null) {
                LOG.warn("Dienst {} moet eerste selectiedatum en soort selectie gevuld hebben.", dienst.getId());
                continue;
            }
            berekenSelectieTaken(berekendeTaakDtos, dienst, beginDatum, eindDatum);
        }
        Collection<Selectietaak> gepersisteerdeTaken = selectieTaakRepository.getGeldigeSelectieTakenBinnenPeriode(beginDatum, eindDatum);
        Collection<SelectieTaakDTO> gepersisteerdeTaakDtos = gepersisteerdeTaken.stream().map(SelectieTaakDTO::new).collect(Collectors.toList());
        Collection<SelectieTaakDTO>
                selectietakenZonderOverlap =
                overlappendeSelectieTaakFilterService.filter(berekendeTaakDtos, gepersisteerdeTaakDtos);
        Collection<SelectieTaakDTO> geautoriseerdeTaken = selectieTaakAutorisatieFilterService.filter(selectietakenZonderOverlap);
        bepaalGemist(geautoriseerdeTaken);
        return geautoriseerdeTaken;
    }

    private static final class BerekendeEersteSelectieDatum {
        private int aantalPeriodesVerschilMetOrigineel;
        private LocalDate eersteSelectieDatum;

        private BerekendeEersteSelectieDatum(LocalDate eersteSelectieDatum, int aantalPeriodesVerschilMetOrigineel) {
            this.eersteSelectieDatum = eersteSelectieDatum;
            this.aantalPeriodesVerschilMetOrigineel = aantalPeriodesVerschilMetOrigineel;
        }
    }
}
