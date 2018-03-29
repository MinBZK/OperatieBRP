/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.service;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Handler voor het toevoegen van OIN headers aan het HTTP verzoek.
 */
final class OinHandler extends AbstractPhaseInterceptor<Message> implements ApplicationListener<ContextRefreshedEvent> {

    private PartijRepository partijRepository;

    private String oinBeheer;

    private OinHandler() {
        super(Phase.PROTOCOL);
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        if (oinBeheer == null) {
            final Partij brpPartij = partijRepository.findByCode(Partij.PARTIJ_CODE_BRP);
            oinBeheer = brpPartij == null ? "" : Optional.ofNullable(brpPartij.getOin()).orElse("");
        }
    }

    @Override
    public void handleMessage(final Message message) {
        Map<String, List<String>> headers = Maps.newHashMap();
        headers.put("oin-ondertekenaar", Collections.singletonList(oinBeheer));
        headers.put("oin-transporteur", Collections.singletonList(oinBeheer));
        message.put(Message.PROTOCOL_HEADERS, headers);
    }

    @Inject
    public void setPartijRepository(final PartijRepository partijRepository) {
        this.partijRepository = partijRepository;
    }
}
