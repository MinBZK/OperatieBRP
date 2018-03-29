/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.junit.Assert;
import org.junit.Test;

public class InitVullingAfnemersindicatieTest {

    @Test
    public void test() {
        final InitVullingAfnemersindicatie subject = new InitVullingAfnemersindicatie(123456L);
        Assert.assertEquals(123456L, subject.getPlId());

        Assert.assertNull(subject.getBerichtResultaat());
        subject.setBerichtResultaat("VIERKANT");
        Assert.assertEquals("VIERKANT", subject.getBerichtResultaat());

        LogRegel logRegel = new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, -1, -1), LogSeverity.ERROR, SoortMeldingCode
                .AUT014, Lo3ElementEnum.ELEMENT_9510);
        InitVullingAfnemersindicatieStapelPk stapelPk = new InitVullingAfnemersindicatieStapelPk(subject.getPlId(), Short.valueOf(("-1")));
        InitVullingAfnemersindicatieStapelPk stapelPkCopy = new InitVullingAfnemersindicatieStapelPk(subject.getPlId(), Short.valueOf(("-1")));
        Assert.assertTrue(subject.getPlId() == stapelPk.getPlId());
        Assert.assertTrue(stapelPk.equals(stapelPkCopy));
        Assert.assertFalse(stapelPk.equals(subject));
        Assert.assertEquals("InitVullingAfnemersindicatieStapelPk[plId=123456,stapelNr=-1]", stapelPk.toString());

        subject.getStapels().add(new InitVullingAfnemersindicatieStapel(stapelPk));
        List<LogRegel> logregels = Collections.singletonList(logRegel);
    }
}
