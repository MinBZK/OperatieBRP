/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Test;

public class AfnemersindicatiesAntwoordBerichtTest {

    private static final String FOUTMELDING = "Foutmelding";
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testAfnemersindicatiesBericht() throws BerichtInhoudException {

        final Set<LogRegel> logregels = new HashSet<>();
        logregels.add(new LogRegel(Lo3StapelHelper.lo3Her(1, 0, 1), LogSeverity.ERROR, SoortMeldingCode.AFN001, Lo3ElementEnum.ELEMENT_0110));

        final AfnemersindicatiesAntwoordBericht bericht = new AfnemersindicatiesAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.FOUT);
        bericht.setFoutmelding(FOUTMELDING);
        bericht.setLogging(logregels);

        final String xml = bericht.format();

        final SyncBericht parsed = factory.getBericht(xml);

        Assert.assertEquals(AfnemersindicatiesAntwoordBericht.class, parsed.getClass());
        parsed.setMessageId(bericht.getMessageId());
        Assert.assertEquals(bericht, parsed);
        Assert.assertEquals(StatusType.FOUT, bericht.getStatus());
        Assert.assertEquals(FOUTMELDING, bericht.getFoutmelding());
        Assert.assertNotNull(bericht.getLogging());
        Assert.assertEquals(1, bericht.getLogging().size());
        Assert.assertEquals(LogSeverity.ERROR, bericht.getLogging().get(0).getSeverity());
        Assert.assertEquals(SoortMeldingCode.AFN001, bericht.getLogging().get(0).getSoortMeldingCode());
        Assert.assertEquals(Lo3ElementEnum.ELEMENT_0110, bericht.getLogging().get(0).getLo3ElementNummer());
    }
}
