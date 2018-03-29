/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBetrokkenheid;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpActie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpIstVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpRelatie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;
import nl.bzk.migratiebrp.ggo.viewer.util.PortInitializer;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Test de GgoBrpBuilder klasse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class, TransactionalTestExecutionListener.class})
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml"}, initializers = {PortInitializer.class})
public class GgoBrpBuilderTest {

    @Inject
    private GgoBrpBuilder builder;
    @Inject
    private ViewerService viewerService;

    @Before
    public void setUp() {
        SynchronisatieLogging.init();
    }

    @Test
    public void testBuildNullPL() {
        final List<GgoStapel> viewerModel = builder.build(null, null);

        Assert.assertEquals("Model is anders dan verwacht.", "[ ]", createJsonString(viewerModel));
    }

    private void verwerk(final String persoonslijstXls, final String expectedTxt) throws Exception {
        final List<Lo3Persoonslijst> lo3Persoonslijsten = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten(persoonslijstXls, new FoutMelder());

        String expected = IOUtils.toString(getClass().getResourceAsStream(expectedTxt), "UTF8");
        expected = expected.trim().replaceAll("[\r]", "");

        Logging.initContext();
        for (final Lo3Persoonslijst lo3Persoonslijst : lo3Persoonslijsten) {
            final BrpPersoonslijst brpPersoonslijst = viewerService.converteerNaarBrp(lo3Persoonslijst, new FoutMelder());
            final Persoon persoon = viewerService.converteerNaarEntityModel(brpPersoonslijst);
            final List<GgoStapel> viewerModel = builder.build(brpPersoonslijst, persoon);

            for (final GgoStapel stapel : viewerModel) {
                wijzigDatumAdministratieveHandelingen(stapel);
            }
            Assert.assertEquals("Model is anders dan verwacht.", expected, createJsonString(viewerModel));
        }
        Logging.destroyContext();
    }

    // Het Tijdstip registratie wijzigt per run, dus aanpassen.
    private void wijzigDatumAdministratieveHandelingen(final GgoStapel stapel) {
        if (stapel == null) {
            return;
        }

        for (final GgoVoorkomen voorkomen : stapel.getVoorkomens()) {
            if ("Administratieve handeling".equals(voorkomen.getLabel())) {
                voorkomen.getInhoud().remove("Tijdstip registratie");
            }

            if (voorkomen instanceof GgoBrpVoorkomen) {
                final GgoBrpVoorkomen brpVoorkomen = (GgoBrpVoorkomen) voorkomen;
                wijzigDatumAdministratieveHandeling(brpVoorkomen.getActieInhoud());
                wijzigDatumAdministratieveHandeling(brpVoorkomen.getActieGeldigheid());
                wijzigDatumAdministratieveHandeling(brpVoorkomen.getActieVerval());
            }

            if (voorkomen instanceof GgoBrpRelatie) {
                final GgoBrpRelatie relatie = (GgoBrpRelatie) voorkomen;
                wijzigDatumAdministratieveHandelingen(relatie.getRelatieInhoud());
                wijzigDatumAdministratieveHandelingen(relatie.getRelatieAfgeleidAdministratief());
                for (final GgoStapel istStapel : relatie.getIstInhoud()) {
                    wijzigDatumAdministratieveHandelingen(istStapel);
                }

                for (final GgoBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                    wijzigDatumAdministratieveHandelingen(betrokkenheid);
                }
            }

            if (voorkomen instanceof GgoBrpIstVoorkomen) {
                final GgoBrpIstVoorkomen brpIstVoorkomen = (GgoBrpIstVoorkomen) voorkomen;
                brpIstVoorkomen.getAdministratieveHandeling().remove("Tijdstip registratie");
            }
        }
    }

    private void wijzigDatumAdministratieveHandelingen(final GgoBetrokkenheid betrokkenheid) {
        for (final GgoStapel stapel : betrokkenheid.getStapels()) {
            wijzigDatumAdministratieveHandelingen(stapel);
        }
    }

    private void wijzigDatumAdministratieveHandeling(final GgoBrpActie actie) {
        if (actie != null) {
            actie.getAdministratieveHandeling().put("Tijdstip registratie", "asdf");
        }
    }

    private String createJsonString(final List<GgoStapel> viewerModel) {
        String json = null;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            json = mapper.writeValueAsString(viewerModel);
            json = json.replaceAll("[\r]", "");
        } catch (final JsonProcessingException e) {
            Assert.fail("Er zou geen exception moeten optreden.");
        }
        return json;
    }
}
