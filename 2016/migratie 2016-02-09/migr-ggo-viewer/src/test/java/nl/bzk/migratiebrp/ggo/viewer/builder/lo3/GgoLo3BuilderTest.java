/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.lo3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.ggo.viewer.Lo3PersoonslijstTestHelper;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit.InsertBefore;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Test de GgoLo3Builder klasse
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class GgoLo3BuilderTest {

    @Inject
    private GgoLo3Builder builder;

    @Test
    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml" })
    public void testBuildSuccess() throws Exception {
        final List<Lo3Persoonslijst> lo3Persoonslijsten =
                Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("PL_alle_cats_elements.xls", new FoutMelder());

        String expected = IOUtils.toString(getClass().getResourceAsStream("/PL_alle_cats_elements-expected.txt"));
        expected = expected.replaceAll("[\r]", "");

        for (final Lo3Persoonslijst lo3Persoonslijst : lo3Persoonslijsten) {
            final List<GgoStapel> viewerModel = builder.build(lo3Persoonslijst);

            assertEquals("Model is anders dan verwacht.", expected.trim(), createJsonString(viewerModel).trim());
        }
    }

    @Test
    public void testBuildLegePL() {
        final Lo3Persoonslijst lo3Persoonslijst = null;
        final List<GgoStapel> viewerModel = builder.build(lo3Persoonslijst);

        assertEquals("Model is anders dan verwacht.", "null", createJsonString(viewerModel));
    }

    @Test
    public void testBuildEmptyPL() {
        final Lo3PersoonslijstBuilder Lo3ModelBuilder = new Lo3PersoonslijstBuilder();
        final Lo3Persoonslijst lo3Persoonslijst = Lo3ModelBuilder.build();

        final List<GgoStapel> viewerModel = builder.build(lo3Persoonslijst);

        assertEquals("Model is anders dan verwacht.", "[ ]", createJsonString(viewerModel));
    }

    private String createJsonString(final List<GgoStapel> viewerModel) {
        String json = null;
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            json = mapper.writeValueAsString(viewerModel);
            json = json.replaceAll("[\r]", "");
        } catch (final JsonProcessingException e) {
            fail("Er zou geen exception moeten optreden.");
        }
        return json;
    }
}
