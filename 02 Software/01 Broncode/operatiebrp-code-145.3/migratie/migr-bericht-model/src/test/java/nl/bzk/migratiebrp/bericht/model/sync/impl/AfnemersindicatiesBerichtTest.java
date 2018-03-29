/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

public class AfnemersindicatiesBerichtTest {

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testAfnemersindicatiesBericht() {
        final AfnemersindicatiesBericht bericht = new AfnemersindicatiesBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        final Lo3Afnemersindicatie afnemersindicatie = Lo3AfnemersindicatieSorter.sorteer(maakLo3Afnemersindicatie());
        bericht.setAfnemersindicaties(afnemersindicatie);

        final StringBuilder verschillenLog = new StringBuilder();

        final boolean result =
                Lo3AfnemersindicatiesVergelijker.vergelijk(
                        verschillenLog,
                        afnemersindicatie,
                        Lo3AfnemersindicatieSorter.sorteer(bericht.getAfnemersindicaties()));

        Assert.assertTrue(result);

        final String xml = bericht.format();
        final SyncBericht parsed = factory.getBericht(xml);

        Assert.assertEquals(AfnemersindicatiesBericht.class, parsed.getClass());
        parsed.setMessageId(bericht.getMessageId());
        Assert.assertEquals(bericht, parsed);
    }

    private Lo3Afnemersindicatie maakLo3Afnemersindicatie() {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersIndicatieStapels = new ArrayList<>();

        // @formatter:off
        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(maakAfnemersindicatieInhoud("000007"),
                null,
                Lo3StapelHelper.lo3His(null, 19920101, 19920101),
                Lo3StapelHelper.lo3Her(14, 0, 0))));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(maakAfnemersindicatieInhoud(null),
                null,
                Lo3StapelHelper.lo3His(null, 19960101, 19960101),
                Lo3StapelHelper.lo3Her(14, 1, 0)),
                Lo3StapelHelper.lo3Cat(maakAfnemersindicatieInhoud("000008"),
                        null,
                        Lo3StapelHelper.lo3His(null, 19920601, 19920601),
                        Lo3StapelHelper.lo3Her(14, 1, 1))));

        afnemersIndicatieStapels.add(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(maakAfnemersindicatieInhoud("000009"),
                null,
                Lo3StapelHelper.lo3His(null, 19920501, 19920501),
                Lo3StapelHelper.lo3Her(14, 2, 0))));
        // @formatter:on

        return new Lo3Afnemersindicatie("9734838049", afnemersIndicatieStapels);
    }

    private Lo3AfnemersindicatieInhoud maakAfnemersindicatieInhoud(final String afnemersindicatie) {
        return new Lo3AfnemersindicatieInhoud(afnemersindicatie);
    }
}
