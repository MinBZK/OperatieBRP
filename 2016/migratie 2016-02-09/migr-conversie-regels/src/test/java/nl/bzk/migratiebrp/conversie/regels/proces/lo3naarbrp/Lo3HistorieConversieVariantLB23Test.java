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

public class Lo3HistorieConversieVariantLB23Test extends AbstractLo3HistorieConversieVariantTest {

    @Inject
    private Lo3HistorieConversieVariantLB23 conversie;

    @Test
    public void testCasus_LB23_1() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 0, 20000202);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 0, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_2() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000202);
        maakLo3Groep(1, null, 0, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 0, 20000101, 20000102010000L, null);
    }

    @Test
    public void testCasus_LB23_3() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 0, 20000202);
        maakLo3Groep(1, "O", 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 0, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_4() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, null, 0, 20000202);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(2, 2, 20000101, 20010101, 20000102010000L, null);
        valideerBrpGroep(1, 1, 0, 20000101, 20000202010000L, null);
    }

    @Test
    public void testCasus_LB23_5() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000102010100L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_6() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(1, 1, 20000101, 20010101, 20000102010100L, null);
        valideerBrpGroep(2, 2, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_7() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, null, 20000101, 20000103);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(1, 2, 20000101, 20010101, 20000103010000L, null);
        valideerBrpGroep(2, 1, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_8() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000202);
        maakLo3Groep(1, null, 20010101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, 20010101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_9() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20010101);
        maakLo3Groep(1, "O", 20000101, 20000103);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20010101, null, 20010101010000L, null);
        valideerBrpGroep(1, 1, 20000101, null, 20000103010000L, 20000103010000L);
        valideerBrpGroep(2, 2, 20000101, 20010101, 20000102010000L, null);
    }

    @Test
    public void testCasus_LB23_10() {
        Logging.initContext();

        // vk onjst geldighd opneming
        legeLo3Groep(0, null, 20000101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 1, 20000101, 20000101, 20000102010000L, 20000102010000L);

        Logging.destroyContext();
    }

    @Test
    public void testCasus_LB23_11() {
        Logging.initContext();

        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000102);
        legeLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000102010100L, null);
        valideerBrpGroep(1, 2, 20000101, 20000101, 20000102010000L, 20000102010000L);

        Logging.destroyContext();
    }

    @Test
    public void testCasus_LB23_12() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000102);
        legeLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, "O", 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, 20000101, null, 20000102010100L, null);
        valideerBrpGroep(1, 2, 20000101, 20000101, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB23_13() {
        Logging.initContext();

        // vk onjst geldighd opneming
        legeLo3Groep(0, null, 0, 20000102);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 1, 20000101, 0, 20000102010000L, null);

        Logging.destroyContext();
    }

    private void converteer() {
        setResultaat(conversie.converteer(getInvoer(), new HashMap<Long, BrpActie>()));
        Collections.reverse(getResultaat());
    }
}
