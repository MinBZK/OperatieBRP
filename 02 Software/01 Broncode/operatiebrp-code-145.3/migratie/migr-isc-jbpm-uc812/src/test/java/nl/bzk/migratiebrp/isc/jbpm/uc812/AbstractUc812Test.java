/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc812-test-beans.xml")
public abstract class AbstractUc812Test extends AbstractJbpmTest {

    protected AbstractUc812Test() {
        super("/uc812/processdefinition.xml,/uc811/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    protected OngeldigBericht maakOngeldigBericht(final SyncBericht verzoek) {
        final OngeldigBericht result = new OngeldigBericht("Fout", "Ongeldigbericht");
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());
        return result;
    }

}
