/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class })
@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class PersoonControllerIT extends AbstractDatabaseTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testPersonenZonderZoekenIsLeeg() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon/"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(0))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpSoort() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("soort", Integer.toString(SoortPersoon.INGESCHREVENE.getId())))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpBsn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("bsn", "123456789"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpAnr() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("anr", "1234567890"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpAfgeleid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("afgeleid", "Ja"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpNamenreeks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("namenreeks", "Ja"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpPredicaat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("predicaat", "1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpAdellijkeTitel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("adellijketitel", "5"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoornamen() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voornamen", "Immanuel"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoorvoegsel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voorvoegsel", "de"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpScheidingsteken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("scheidingsteken", "-"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeslachtsnaam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geslachtsnaamstam", "Bieren"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboortedatum() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortedatum", "12120205"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteplaats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortegemeente", "3"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboortewoonplaats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortewoonplaats", "3"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteBuitenlandseplaats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortebuitenlandseplaats", "Buitenlandse plaats"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteBuitenlandseregio() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboortebuitenlandseregio", "Buitenladse regio"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpGeboorteland() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("geboorteland", "4"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoornamenZonderAccent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voornamen", "Maria"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void testPersonenZoekOpVoornamenMetAccent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("voornamen", "Mariá"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpSoortAdres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("soortadres", "1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
               .andReturn();
    }

    @Test
    public void zoekenOpIdentificatieAdresseerbaarObject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("idadresseerbaarobject", "1492"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2))
               .andReturn();
    }

    @Test
    public void zoekenOpIdentificatieNummeraanduiding() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("idnummeraanduiding", "1581"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
               .andReturn();
    }

    @Test
    public void zoekenOpGemeente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("gemeente", "4"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpNaamOpenbareRuimmte() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("naamopenbareruimte", "Straatweg"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
               .andReturn();
    }

    @Test
    public void zoekenOpAfgekorteNaamOpenbareRuimte() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("afgekortenaamopenbareruimte", "Damstr"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpHuisnummer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("huisnummer", "512"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpHuisletter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("huisletter", "a"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(3))
               .andReturn();
    }

    @Test
    public void zoekenOpHuisnummertoevoeging() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("huisnummertoevoeging", "IV"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpPostcode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("postcode", "7812PK"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpWoonplaatsnaam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("woonplaatsnaam", "Almere"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(6))
               .andReturn();
    }

    @Test
    public void zoekenOpBuitenlandsadres1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("buitenlandsadres", "Regel 1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpBuitenlandsadres2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("buitenlandsadres", "Regel 5"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpBuitenlandsadres3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("buitenlandsadres", "Reg"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }

    @Test
    public void zoekenOpLandOfGebied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persoon").param("landofgebied", "4"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.first").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1))
               .andReturn();
    }
}
