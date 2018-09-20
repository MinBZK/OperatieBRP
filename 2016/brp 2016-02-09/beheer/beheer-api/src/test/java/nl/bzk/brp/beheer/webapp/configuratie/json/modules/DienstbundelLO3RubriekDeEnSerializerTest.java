/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;
import org.junit.Assert;
import org.junit.Test;

public class DienstbundelLO3RubriekDeEnSerializerTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();

    @Test
    public void testLeeg() throws IOException {
        final ObjectReader reader = subject.reader(DienstbundelLO3Rubriek.class);
        final DienstbundelLO3Rubriek value = reader.<DienstbundelLO3Rubriek>readValue("{}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getID());
        Assert.assertNull(value.getRubriek());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testVolledig() throws IOException {
        final ObjectReader reader = subject.reader(DienstbundelLO3Rubriek.class);
        final DienstbundelLO3Rubriek value = reader.<DienstbundelLO3Rubriek>readValue("{\"iD\":2,\"abonnement\":\"2\",\"rubriek\":3,\"actief\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.valueOf(2), value.getID());

        controleerHeenEnWeer(value);
    }

    @Test
    public void testNieuw() throws IOException {
        final ObjectReader reader = subject.reader(DienstbundelLO3Rubriek.class);
        final DienstbundelLO3Rubriek value = reader.<DienstbundelLO3Rubriek>readValue("{\"abonnement\":\"2\",\"rubriek\":\"3\",\"actief\":\"Ja\"}");

        Assert.assertNotNull(value);
        Assert.assertNull(value.getID());
        Assert.assertNotNull(value.getRubriek());
        Assert.assertEquals(Integer.valueOf((short) 3), value.getRubriek().getID());

        controleerHeenEnWeer(value);
    }

    private void controleerHeenEnWeer(final DienstbundelLO3Rubriek heen) throws IOException {
        final ObjectWriter writer = subject.writer();
        final String json = writer.writeValueAsString(heen);

        final ObjectReader reader = subject.reader(DienstbundelLO3Rubriek.class);
        final DienstbundelLO3Rubriek weer = reader.<DienstbundelLO3Rubriek>readValue(json);

        Assert.assertEquals(heen.getID(), weer.getID());

    }
}
