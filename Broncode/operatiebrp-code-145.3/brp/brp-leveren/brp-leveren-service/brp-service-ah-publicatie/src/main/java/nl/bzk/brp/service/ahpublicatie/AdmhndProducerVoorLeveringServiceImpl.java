/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.ahpublicatie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.dalapi.TeLeverenHandelingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AdmhnProducerVoorLeveringService.
 */
@Service
final class AdmhndProducerVoorLeveringServiceImpl implements AdmhndProducerVoorLeveringService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Inject
    private AdmhndPublicatieVoorLeveringService admhndPublicatieVoorLeveringService;

    @Value("${brp.levering.admhndpublicatie.maxinlevering:1000}")
    private int maxHandelingenInLevering;

    private AdmhndProducerVoorLeveringServiceImpl() {

    }

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public void produceerHandelingenVoorLevering() {
        final Set<HandelingVoorPublicatie> handelingen = geefHandelingenVoorLevering();
        if (handelingen.isEmpty()) {
            LOGGER.trace("geen handelingen voor publicatie beschikbaar");
            return;
        }
        final Set<Long> ids = new HashSet<>();
        final Set<String> jsonMessages = new HashSet<>();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            for (HandelingVoorPublicatie handelingVoorPublicatie : handelingen) {
                ids.add(handelingVoorPublicatie.getAdmhndId());
                jsonMessages.add(mapper.writeValueAsString(handelingVoorPublicatie));
            }
            //zet alle handelingen op status in levering
            final int updateCount = administratieveHandelingRepository.zetHandelingenStatusInLevering(ids);
            if (ids.size() != updateCount) {
                LOGGER.warn("update count van administratieve handeling zet in levering ongelijk aan aantal handelingen");
            }
            //publiceer de handelingen op jms queue
            admhndPublicatieVoorLeveringService.publiceerHandelingen(jsonMessages);
            LOGGER.trace("administratieve handeling in levering gezet: " + updateCount);
        } catch (JsonProcessingException e) {
            throw new BrpServiceRuntimeException(e);
        }
    }

    private Set<HandelingVoorPublicatie> geefHandelingenVoorLevering() {
        final Set<Long> personenInLevering = new HashSet<>();
        final Set<Long> handelingenInLevering = new HashSet<>();
        final SetMultimap<Long, Long> admhndMap = LinkedHashMultimap.create();
        LOGGER.trace("start haal handelingen op voor levering");
        final List<TeLeverenHandelingDTO> handelingenInLeveringOfTeLeveren = administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie();
        LOGGER.trace("einde haal handelingen op voor levering, aantal handelingen: " + handelingenInLeveringOfTeLeveren.size());
        //filter handelingen nog in levering
        for (TeLeverenHandelingDTO teLeverenHandeling : handelingenInLeveringOfTeLeveren) {
            if (teLeverenHandeling.getStatus() == StatusLeveringAdministratieveHandeling.IN_LEVERING) {
                //handeling al in levering
                LOGGER
                        .debug(String.format("admhnd met id %d nog in levering, zet bijgehouden personen op ignore lijst", teLeverenHandeling.getAdmhndId()));
                if (teLeverenHandeling.getBijgehoudenPersoon() != null) {
                    personenInLevering.add(teLeverenHandeling.getBijgehoudenPersoon());
                }
                handelingenInLevering.add(teLeverenHandeling.getAdmhndId());
            } else {
                admhndMap.put(teLeverenHandeling.getAdmhndId(), teLeverenHandeling.getBijgehoudenPersoon());
            }
        }
        if (handelingenInLevering.size() > maxHandelingenInLevering) {
            LOGGER.debug("max aantal handelingen staan al in levering, verwerk geen nieuwe");
            return Collections.emptySet();
        }

        return filterHandelingen(personenInLevering, admhndMap);
    }

    private Set<HandelingVoorPublicatie> filterHandelingen(final Set<Long> personenInLevering, final SetMultimap<Long, Long> admhndMap) {
        final Set<HandelingVoorPublicatie> teLeverenHandelingen = new HashSet<>();
        //loop over handelingen zonder status in levering en bepaal unieke bijgehouden persoon sets
        for (Long admhnId : admhndMap.keySet()) {
            final Set<Long> bijgehoudenPersonen = admhndMap.get(admhnId);
            if (Collections.disjoint(bijgehoudenPersonen, personenInLevering)) {
                final HandelingVoorPublicatie handelingVoorPublicatie = new HandelingVoorPublicatie();
                handelingVoorPublicatie.setAdmhndId(admhnId);
                handelingVoorPublicatie.setBijgehoudenPersonen(bijgehoudenPersonen);
                teLeverenHandelingen.add(handelingVoorPublicatie);
                personenInLevering.addAll(bijgehoudenPersonen);
            } else {
                LOGGER.debug(String.format("admhnd %s voor persoon al in levering, discard voor nu. Worden later opgepakt", admhnId));
            }
        }
        return teLeverenHandelingen;
    }
}
