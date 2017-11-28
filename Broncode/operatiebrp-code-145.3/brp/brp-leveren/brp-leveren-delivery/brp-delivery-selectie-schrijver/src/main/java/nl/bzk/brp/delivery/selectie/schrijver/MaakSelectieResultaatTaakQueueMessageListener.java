/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.selectie.schrijver.MaakSelectieResultaatTaakVerwerkerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * MaakSelectieResultaatTaakQueueMessageListener.
 */
@Component
final class MaakSelectieResultaatTaakQueueMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();

    @Inject
    @Qualifier("maakSelectieResultaatTaakVerwerkerServiceImpl")
    private MaakSelectieResultaatTaakVerwerkerService maakSelectieResultaatTaakVerwerkerServiceImpl;
    @Inject
    @Qualifier("afnemerIndicatieMaakSelectieResultaatTaakVerwerkerServiceImpl")
    private MaakSelectieResultaatTaakVerwerkerService afnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl;

    private MaakSelectieResultaatTaakQueueMessageListener() {

    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        try {
            LOGGER.debug("onMessage");
            final TextMessage textMessage = (TextMessage) message;
            final String text = textMessage.getText();
            final MaakSelectieResultaatTaak
                    maakSelectieResultaatTaak =
                    JSON_STRING_SERIALISEERDER.deserialiseerVanuitString(text, MaakSelectieResultaatTaak.class);
            final SoortSelectie soortSelectie = maakSelectieResultaatTaak.getSoortSelectie();
            if (soortSelectie == SoortSelectie.STANDAARD_SELECTIE) {
                maakSelectieResultaatTaakVerwerkerServiceImpl.verwerk(maakSelectieResultaatTaak);
            } else if (soortSelectie == SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE
                    || soortSelectie == SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE) {
                afnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl.verwerk(maakSelectieResultaatTaak);
            }
        } catch (JMSException e) {
            LOGGER.error("error on message", e);
        }
    }
}

