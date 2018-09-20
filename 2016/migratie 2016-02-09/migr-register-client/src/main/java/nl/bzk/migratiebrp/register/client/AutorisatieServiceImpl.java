/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import javax.jms.JMSException;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegister;

/**
 * Autorisatie service implementatie.
 */
public final class AutorisatieServiceImpl extends AbstractRegisterServiceImpl<AutorisatieRegister> implements AutorisatieService {

    @Override
    protected String maakVerzoek() {
        return new LeesAutorisatieRegisterVerzoekBericht().format();
    }

    @Override
    protected AutorisatieRegister leesRegister(final String bericht) throws JMSException {
        final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
        if (syncBericht instanceof LeesAutorisatieRegisterAntwoordBericht) {
            return ((LeesAutorisatieRegisterAntwoordBericht) syncBericht).getAutorisatieRegister();
        } else {
            throw new JMSException("Binnenkomend bericht voor autorisatie register was van een onbekend type. Bericht wordt genegeerd ...");
        }
    }
}
