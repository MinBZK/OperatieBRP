/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client;

import javax.jms.JMSException;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;

/**
 * Gemeente service implementatie.
 */
public final class GemeenteServiceImpl extends AbstractRegisterServiceImpl<GemeenteRegister> implements GemeenteService {

    @Override
    protected String maakVerzoek() {
        return new LeesGemeenteRegisterVerzoekBericht().format();
    }

    @Override
    protected GemeenteRegister leesRegister(final String bericht) throws JMSException {
        final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
        if (syncBericht instanceof LeesGemeenteRegisterAntwoordBericht) {
            return ((LeesGemeenteRegisterAntwoordBericht) syncBericht).getGemeenteRegister();
        } else {
            throw new JMSException("Binnenkomend bericht voor gemeente register was van een onbekend type. Bericht wordt genegeerd ...");
        }
    }
}
