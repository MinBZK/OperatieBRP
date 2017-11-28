/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.service.dal.ReferentieRepository;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link SelectieTaakService}.
 */
@Service
@Transactional
final class SelectieTaakServiceImpl implements SelectieTaakService {

    private final SelectieTaakRepository selectieTaakRepository;
    private final ReferentieRepository referentieRepository;
    private final SelectieTaakBerekenService selectieTaakBerekenService;

    @Inject
    SelectieTaakServiceImpl(SelectieTaakRepository selectieTaakRepository, ReferentieRepository referentieRepository,
                            SelectieTaakBerekenService selectieTaakBerekenService) {
        this.selectieTaakRepository = selectieTaakRepository;
        this.referentieRepository = referentieRepository;
        this.selectieTaakBerekenService = selectieTaakBerekenService;
    }

    private static boolean statusGewijzigd(Selectietaak entiteit, SelectieTaakDTO selectieTaak) {
        boolean statusGewijzigd = !Objects.equals(entiteit.getStatus(), selectieTaak.getStatus());
        boolean toelichtingGewijzigd = !Objects.equals(entiteit.getStatusToelichting(), selectieTaak.getStatusToelichting());
        return statusGewijzigd || toelichtingGewijzigd;
    }

    @Override
    public Collection<SelectieTaakDTO> getSelectieTaken(SelectiePeriodeDTO selectiePeriode) {
        Collection<SelectieTaakDTO>
                selectieTaken =
                selectieTaakBerekenService.berekenSelectieTaken(selectiePeriode.getBeginDatum(), selectiePeriode.getEindDatum());
        selectieTaken.stream().filter(t -> t.getId() == null).forEach(this::slaSelectieTaakOp);
        List<SelectieTaakDTO> sortedTaken = Lists.newArrayList(selectieTaken);
        sortedTaken.sort((links, rechts) -> {
            LocalDate datumTeVergelijkenLinks = Optional.ofNullable(links.getDatumPlanning()).orElse(links.getBerekendeSelectieDatum());
            LocalDate datumTeVergelijkenRechts = Optional.ofNullable(rechts.getDatumPlanning()).orElse(rechts.getBerekendeSelectieDatum());
            int compare = datumTeVergelijkenLinks.compareTo(datumTeVergelijkenRechts);
            if (compare == 0) {
                compare = links.getAfnemerCode().compareTo(rechts.getAfnemerCode());
            }
            return compare;
        });
        return sortedTaken;
    }

    @Override
    public SelectieTaakDTO slaSelectieTaakOp(SelectieTaakDTO selectieTaak) {
        Dienst dienst = referentieRepository.getReferentie(Dienst.class, selectieTaak.getDienstId());
        ToegangLeveringsAutorisatie
                tla =
                referentieRepository.getReferentie(ToegangLeveringsAutorisatie.class, selectieTaak.getToegangLeveringsautorisatieId());
        Selectietaak entiteit;
        if (selectieTaak.getId() == null) {
            entiteit = new Selectietaak(dienst, tla, selectieTaak.getVolgnummer());
            entiteit.setActueelEnGeldig(true);
        } else {
            entiteit = selectieTaakRepository.vindSelectietaak(selectieTaak.getId());
        }
        entiteit.setDatumPlanning(
                DatumUtil.vanDatumNaarInteger(Optional.ofNullable(selectieTaak.getDatumPlanning()).orElse(selectieTaak.getBerekendeSelectieDatum())));
        entiteit.setVolgnummer(selectieTaak.getVolgnummer());
        entiteit.setActueelEnGeldigStatus(true);
        Selectietaak finalEntiteit = entiteit;
        Optional.ofNullable(selectieTaak.getPeilmomentFormeelResultaat())
                .ifPresent(p -> finalEntiteit.setPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(p)));
        Optional.ofNullable(selectieTaak.getPeilmomentMaterieelResultaat())
                .ifPresent(p -> finalEntiteit.setPeilmomentMaterieelResultaat(DatumUtil.vanDatumNaarInteger(p)));
        // historierecord selectietaak
        Timestamp tsreg = Timestamp.from(ZonedDateTime.now().toInstant());
        SelectietaakHistorie historie = new SelectietaakHistorie(entiteit);
        historie.setDatumPlanning(entiteit.getDatumPlanning());
        historie.setDatumUitvoer(entiteit.getDatumUitvoer());
        historie.setIndicatieSelectielijstGebruiken(Optional.ofNullable(entiteit.isIndicatieSelectielijstGebruiken()).orElse(false));
        historie.setPeilmomentMaterieelResultaat(entiteit.getPeilmomentMaterieelResultaat());
        historie.setPeilmomentFormeelResultaat(entiteit.getPeilmomentFormeelResultaat());
        historie.setDatumTijdRegistratie(tsreg);
        entiteit.getSelectietaakHistorieSet().stream().filter(h -> h.getSelectietaak().getId().equals(finalEntiteit.getId()) && h.getDatumTijdVerval() == null)
                .findFirst()
                .ifPresent(h -> h.setDatumTijdVerval(tsreg));
        entiteit.getSelectietaakHistorieSet().add(historie);
        if (entiteit.getId() == null || statusGewijzigd(entiteit, selectieTaak)) {
            // historierecord selectiestatus
            final SelectietaakStatusHistorie statusHistorie = new SelectietaakStatusHistorie(entiteit);
            statusHistorie.setDatumTijdRegistratie(tsreg);
            statusHistorie.setStatusGewijzigdDoor("Systeem");
            statusHistorie.setStatus(selectieTaak.getStatus());
            statusHistorie.setStatusToelichting(selectieTaak.getStatusToelichting());
            entiteit.getSelectietaakStatusHistorieSet().stream()
                    .filter(h -> h.getSelectietaak().getId().equals(finalEntiteit.getId()) && h.getDatumTijdVerval() == null).findFirst()
                    .ifPresent(h -> h.setDatumTijdVerval(tsreg));
            entiteit.getSelectietaakStatusHistorieSet().add(statusHistorie);
            //update a-laag
            entiteit.setStatus(selectieTaak.getStatus());
            entiteit.setStatusToelichting(selectieTaak.getStatusToelichting());
        }
        if (selectieTaak.getId() == null) {
            entiteit = selectieTaakRepository.slaOp(entiteit);
        }
        selectieTaak.setId(entiteit.getId());
        return selectieTaak;
    }

}
