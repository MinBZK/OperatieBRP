/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.docker.dockerservice.mojo.service;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ServicesUtilTest {

    @Test
    public void retrieveAllServices() throws Exception {
        final List<String> resultaat = ServicesUtil.bepaalAlleServices();
        Assert.assertEquals(66, resultaat.size());
    }


}
