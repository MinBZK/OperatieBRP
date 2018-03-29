/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import javax.jms.JMSException;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;

/**
 * Partij service implementatie.
 */
public class PartijServiceImpl extends AbstractRegisterServiceImpl<PartijRegister> implements PartijService {

    @Override
    protected String maakVerzoek() {
        return new LeesPartijRegisterVerzoekBericht().format();
    }

    @Override
    protected PartijRegister leesRegister(final String bericht) throws JMSException {
        final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
        if (syncBericht instanceof LeesPartijRegisterAntwoordBericht) {
            return ((LeesPartijRegisterAntwoordBericht) syncBericht).getPartijRegister();
        } else {
            throw new JMSException("Binnenkomend bericht voor partij register was van een onbekend type. Bericht wordt genegeerd ...");
        }
    }
}
