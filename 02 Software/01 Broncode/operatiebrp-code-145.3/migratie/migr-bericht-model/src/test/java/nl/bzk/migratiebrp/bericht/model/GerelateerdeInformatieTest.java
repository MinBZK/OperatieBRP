/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class GerelateerdeInformatieTest {

    @Test
    public void test() {
        final List<Long> administratieveHandelingIds = Arrays.asList(123L);
        final List<String> partijen = Arrays.asList("123", null, "", "456");
        final List<String> aNummers = null;

        final GerelateerdeInformatie subject = new GerelateerdeInformatie(administratieveHandelingIds, partijen, aNummers);

        Assert.assertEquals(1, subject.getAdministratieveHandelingIds().size());
        Assert.assertEquals(2, subject.getPartijen().size());
        Assert.assertTrue(subject.getPartijen().contains("123"));
        Assert.assertTrue(subject.getPartijen().contains("456"));
        Assert.assertEquals(0, subject.getaNummers().size());
    }
}
