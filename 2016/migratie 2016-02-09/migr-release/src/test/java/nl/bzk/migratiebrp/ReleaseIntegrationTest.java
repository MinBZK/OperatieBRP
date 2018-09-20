/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp;

import javax.jms.JMSException;
import junit.framework.Assert;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal.Strategie;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.junit.Test;

public class ReleaseIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testSynchronisatie() throws InterruptedException, JMSException {
        LOGGER.info("Test uitvoeren");

        final int personenVoor = countPersonenInBrp();
        LOGGER.info("Aantal personen in BRP voor synchronisatie: {}", personenVoor);

        final String lg01 =
                "00000000Lg01000000000000000006372962081000000000001319011830110010637296208101200097819486170210008Leontien0240009Staartjes03100081966082103200040014033000460300410001V6110001E821000405188220008199409308230003PKA851000819920808861000819940930021500210006Minnie0240005Ienie03100081942083103200040013033000460300410001V621000819660821821000405188220008199409308230002PK851000819660821861000819940930031520210004Aart0240009Staartjes03100081938111803200040013033000460300410001M621000819660821821000405188220008199409308230002PK85100081966082186100081994093004086051000400016310003001821000405188220008199409308230002PK851000819660821861000819940930070776810008199409306910004051870100010801000400038020017201207011435010008710001P08235091000406260920008199806221010001W1030008199806221110010S vd Oyeln1115038Baron Schimmelpenninck van der Oyelaan11200021611600062252EB1170011Voorschoten11800160626010010016001119001606262000100160017210001T85100082011031686100082011031758126091000406260920008199806221010001W1030008199806221110010S vd Oyeln11200021611600062252EB7210001I85100081998062286100081998062458132091000405180920008199010021010001W1030008199512131110015Waldorpsstraat 112000330411600062521CG7210001I85100081995121386100081995121458133091000405180920008199010021010001W1030008199010021110017Van Swietenstraat11200022011600062518EH7210001A851000819901002861000819940930";

        LOGGER.info("Versturen Lg01 naar MAILBOX");
        verstuurBerichtViaMailbox("MSG-1", null, "0626", "3000200", lg01);

        LOGGER.info("Controleren aantal personen in BRP ...");
        try {
            new Herhaal(1000, 30, Strategie.REGELMATIG).herhaal(new Runnable() {
                @Override
                public void run() {
                    final int personenNa = countPersonenInBrp();
                    LOGGER.debug("Aantal personen in BRP na synchronisatie: {}", personenNa);
                    if (personenVoor + 1 != personenNa) {
                        throw new IllegalStateException("Aantal personen in BRP was niet " + (personenVoor + 1) + ", maar " + personenNa);
                    }
                }
            });
        } catch (final HerhaalException e) {
            Assert.fail("Aantal personen in BRP niet correct.");
        }

        LOGGER.info("Controleren signaal naar BRP");
        final String notificatie = getContent(expectBrpMessage("GbaBijhoudingen"));
        Assert.assertTrue(notificatie.matches("\\{\"administratieveHandelingId\":\\d*?,\"bijgehoudenPersoonIds\":\\[\\d*?\\]\\}"));

        LOGGER.info("Controleren archief naar BRP");
        final String archief = getContent(expectBrpMessage("GbaArchief"));
        Assert.assertTrue(archief.contains(lg01));

        LOGGER.info("Test gereed");
    }
}
