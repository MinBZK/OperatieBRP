/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.bzm.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test case voor de BRP Service SOAP connectie. Wordt niet gerund tijdens de build omdat het een integratietest is.
 * (Zie surefire config in pom.xml)
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:bzm-test-config.xml", "classpath:bzm-soap.xml"})
public class BzmSOAPTest {
    @Inject
    private BzmBrpService bzmService;

    private final String oinTransporteur = "123ABC";
    private final String oinOndertekenaar = "987ZYX";

    @Test
    public void registreerGeboorteSuccesTest() throws IOException {
        final String xmlBody = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/afs_InschrijvingGeboorte_Bijhouding_v2.0.xml")));
        final String resultaat = bzmService.verstuurBzmBericht(xmlBody, oinTransporteur, oinOndertekenaar);
        assertNotNull("Mag niet null zijn", resultaat);
        assertTrue("Resultaat begint niet zoals een RegistreerGeboorte Response", resultaat.startsWith("<brp:AFS_RegistreerGeboorte_BR"));
    }

    @Test
    public void registreerGeboorteFailTest() throws IOException {
        final String xmlBody =
                FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/afs_InschrijvingGeboorte_Bijhouding_v2.0_FOUT.xml")));
        final String resultaat = bzmService.verstuurBzmBericht(xmlBody, oinTransporteur, oinOndertekenaar);
        assertNull("Moet null zijn", resultaat);
    }

    @Test
    public void registreerIntergemeentelijkeVerhuizingSuccesTest() throws IOException {
        final String xmlBody =
                FileUtils.readFileToString(FileUtils.toFile(this.getClass()
                        .getResource("/mig_RegistratieIntergemeentelijkeVerhuizing_Bijhouding_v1.3.xml")));
        final String resultaat = bzmService.verstuurBzmBericht(xmlBody, oinTransporteur, oinOndertekenaar);
        assertNotNull("Mag niet null zijn", resultaat);
        assertTrue("Resultaat begint niet zoals een RegistreerVerhuizing Response", resultaat.startsWith("<brp:MIG_RegistreerVerhuizing_BR"));
    }
}
