/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.Collections;
import java.util.HashMap;
import javax.inject.Inject;
import org.junit.Test;

public class Lo3HistorieConversieVariantLB24Test extends AbstractLo3HistorieConversieVariantTest {

    @Inject
    private Lo3HistorieConversieVariantLB24 conversie;

    @Test
    public void testCasus_LB24_1() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 0, 20000202);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, null, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, null, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB24_2() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20000101, 20000202);
        maakLo3Groep(1, null, 0, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, null, null, 20000202010000L, null);
        valideerBrpGroep(1, 1, null, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB24_3() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, null, null, 20000102010100L, null);
        valideerBrpGroep(1, 1, null, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCasus_LB24_4() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000104);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(1, 0, null, null, 20000102010000L, null);
        valideerBrpGroep(0, 1, null, null, 20000104010000L, 20000104010000L);
    }

    @Test
    public void testCasus_LB24_4B() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000104);
        maakLo3Groep(2, "O", 20000101, 20000103);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(2, 0, null, null, 20000102010000L, null);
        valideerBrpGroep(0, 1, null, null, 20000104010000L, 20000104010000L);
        valideerBrpGroep(1, 2, null, null, 20000103010000L, 20000103010000L);
    }

    @Test
    public void testCasus_LB24_5() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20000102);
        maakLo3Groep(1, null, 20000101, 20000102);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, null, null, 20000102010200L, null);
        valideerBrpGroep(1, 1, null, null, 20000102010100L, 20000102010100L);
        valideerBrpGroep(2, 2, null, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testLegeRijEerst() {
        // vk onjst geldighd opneming
        maakLo3Groep(0, null, 20010101, 20000104);
        legeLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 0, null, null, 20000104010000L, null);
    }

    @Test
    public void testCasus_LB24_stap_3c() {
        // vk onjst geldighd opneming
        legeLo3Groep(0, null, 20010101, 20010102);
        maakLo3Groep(1, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 1, null, null, 20000102010000L, 20010102010000L);
    }

    @Test
    public void testCasus_LB24_stap_3d() {
        // vk onjst geldighd opneming
        legeLo3Groep(0, null, 20000101, 20020102);
        legeLo3Groep(1, "O", 20010101, 20010102);
        maakLo3Groep(2, null, 20000101, 20000102);

        converteer();

        // brpVk Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpGroep(0, 2, null, null, 20000102010100L, 20020102010000L);
        valideerBrpGroep(1, 2, null, null, 20000102010000L, 20010102010000L);
    }

    private void converteer() {
        setResultaat(conversie.converteer(getInvoer(), new HashMap<>()));
        Collections.reverse(getResultaat());
    }
}
