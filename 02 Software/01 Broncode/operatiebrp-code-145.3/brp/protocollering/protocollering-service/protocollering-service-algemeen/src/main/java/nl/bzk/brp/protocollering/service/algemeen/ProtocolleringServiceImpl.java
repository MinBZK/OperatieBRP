/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * De service die de protocollering verwerkt.
 */
@Bedrijfsregel(Regel.R1996)
@Service
@Transactional(transactionManager = "protocolleringTransactionManager")
public final class ProtocolleringServiceImpl implements ProtocolleringService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ProtocolleringRepository protocolleringRepository;
    @Inject
    private ScopePatroonService scopePatroonService;

    private ProtocolleringServiceImpl() {
    }

    @Override
    public Leveringsaantekening protocolleer(final ProtocolleringOpdracht protocolleringOpdracht) {
        final Leveringsaantekening
                leveringsaantekening =
                new Leveringsaantekening(protocolleringOpdracht.getToegangLeveringsautorisatieId(), protocolleringOpdracht.getDienstId(),
                        new Timestamp(protocolleringOpdracht.getDatumTijdKlaarzettenLevering().toInstant().toEpochMilli()),
                        new Timestamp(protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat().toInstant().toEpochMilli()));
        leveringsaantekening.setAdministratieveHandeling(protocolleringOpdracht.getAdministratieveHandelingId());
        leveringsaantekening.setSoortSynchronisatie(protocolleringOpdracht.getSoortSynchronisatie());
        leveringsaantekening.setDatumEindeMaterielePeriodeResultaat(protocolleringOpdracht.getDatumEindeMaterielePeriodeResultaat());
        leveringsaantekening.setDatumAanvangMaterielePeriodeResultaat(protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat());
        leveringsaantekening.setDatumTijdEindeFormelePeriodeResultaat(
                new Timestamp(protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat().toInstant().toEpochMilli()));
        if (protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat() != null) {
            leveringsaantekening
                    .setDatumTijdAanvangFormelePeriodeResultaat(
                            new Timestamp(protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat().toInstant().toEpochMilli()));
        }

        if (protocolleringOpdracht.getScopingAttributen() != null && !protocolleringOpdracht.getScopingAttributen().isEmpty()) {
            final Set<Integer> attribuutSet = Sets.newHashSet();
            attribuutSet.addAll(protocolleringOpdracht.getScopingAttributen());

            final ScopePatroon scopePatroon = scopePatroonService.getScopePatroon(attribuutSet);
            leveringsaantekening.setScopePatroon(scopePatroon);
        }
        if (protocolleringOpdracht.getGeleverdePersonen() != null) {
            final List<LeveringsaantekeningPersoon> list =
                    map(protocolleringOpdracht.getGeleverdePersonen(), leveringsaantekening);
            list.forEach(leveringsaantekening::addLeveringsaantekeningPersoon);
        }
        protocolleringRepository.opslaanNieuweLevering(leveringsaantekening);

        LOGGER.debug("Protocollering van levering {} met bijbehorende personen en communicatie geslaagd.", leveringsaantekening.getId());

        return leveringsaantekening;
    }

    @Override
    public void protocolleerPersonenBijLeveringaantekening(final Leveringsaantekening leveringsaantekening, final List<LeveringPersoon> persoonList) {
        final List<LeveringsaantekeningPersoon> personen = map(persoonList, leveringsaantekening);
        protocolleringRepository.slaBulkPersonenOpBijLeveringsaantekening(leveringsaantekening, personen);
    }

    /**
     * Protocolleer personen bij een levering.
     * @param leveringPersonen Een Set van leveringpersonen.
     * @param leveringsaantekening De gepersisteerde levering.
     */
    private List<LeveringsaantekeningPersoon> map(final List<LeveringPersoon> leveringPersonen, final Leveringsaantekening leveringsaantekening) {
        final List<LeveringsaantekeningPersoon> persoonList = Lists.newArrayListWithExpectedSize(leveringPersonen.size());
        for (final LeveringPersoon leveringPersoon : leveringPersonen) {
            final ZonedDateTime
                    tijdstipLaatsteWijziging = leveringPersoon.getTijdstipLaatsteWijziging();
            final Timestamp timestamp = new Timestamp(tijdstipLaatsteWijziging.toInstant().toEpochMilli());
            final LeveringsaantekeningPersoon nieuweLeveringPersoon = new LeveringsaantekeningPersoon(leveringsaantekening,
                    leveringPersoon.getPersoonId(), timestamp, leveringsaantekening.getDatumTijdKlaarzettenLevering());
            nieuweLeveringPersoon.setDatumAanvangMaterielePeriode(leveringPersoon.getDatumAanvangMaterielePeriode());
            LOGGER.debug("Protocolleer persoon {} voor levering {}", leveringPersoon.getPersoonId(), leveringsaantekening.getId());
            persoonList.add(nieuweLeveringPersoon);

        }
        return persoonList;
    }
}
