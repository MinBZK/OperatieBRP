/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.Collections;
import java.util.HashMap;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Test;

public class Lo3HistorieConversieVariantLB25Test extends AbstractLo3HistorieConversieVariantTest {
    @Inject
    private Lo3HistorieConversieVariantLB25 conversie;

    @Test
    public void test() {
        // @formatter:off
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 19920101, 19920101);
        maakLo3Groep(1, null, 19900101, 19900101);
        legeLo3Groep(2, null, 19850101, 19850101);
        maakLo3Groep(3, null, 19800101, 19800101);

        converteer();

        // Inh.
        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 19920101, null, 19920101010000L, null);
        valideerBrpGroep(1, 1, 19900101, null, 19900101010000L, 19920101010000L);
        //lo3Voorkomen 2 word niet geconverteerd omdat de inhoud leeg is, wel gebruikt om vorige te laten vervallen
        valideerBrpGroep(2, 3, 19800101, null, 19800101010000L, 19850101010000L);
        // @formatter:on
    }

    @Test
    public void test2() {
        // @formatter:off
        // vk onjst geldighd opneming
        legeLo3Groep(0, null, 19960101, 19960101);
        legeLo3Groep(1, null, 19940101, 19940101);
        maakLo3Groep(2, null, 19920101, 19920101);
        maakLo3Groep(3, null, 19900101, 19900101);
        legeLo3Groep(4, null, 19870101, 19870101);
        legeLo3Groep(5, null, 19850101, 19850101);
        maakLo3Groep(6, null, 19800101, 19800101);
        legeLo3Groep(7, null, 19700101, 19700101);
        legeLo3Groep(8, null, 19600101, 19700101);

        converteer();

        // Inh.
        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 2, 19920101, null, 19920101010000L, 19940101010000L);
        valideerBrpGroep(1, 3, 19900101, null, 19900101010000L, 19920101010000L);
        //lo3Voorkomen 4 en 5 worden niet geconverteerd omdat de inhoud leeg is, wel gebruikt om vorige te laten vervallen
        valideerBrpGroep(2, 6, 19800101, null, 19800101010000L, 19850101010000L);
        // @formatter:on
    }

    private void converteer() {
        Logging.initContext();
        try {
            setResultaat(conversie.converteer(getInvoer(), new HashMap<Long, BrpActie>()));
            Collections.reverse(getResultaat());
        } finally {
            Logging.destroyContext();
        }

    }

}
