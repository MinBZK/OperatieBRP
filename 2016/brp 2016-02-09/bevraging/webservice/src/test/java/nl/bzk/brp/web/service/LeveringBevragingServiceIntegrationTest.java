/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.utils.SchemaUtils;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Node;

@Ignore//TEAMBRP-4135
public class LeveringBevragingServiceIntegrationTest extends AbstractLeveringBevragingIntegrationTest {

    private static final String BRP_STUURGEGEVENS = "brp:stuurgegevens";
    private static final String BRP_RESULTAAT = "brp:resultaat";
    private static final String BRP_PERSONEN = "brp:personen";
    private final SchemaUtils schemaUtils = new SchemaUtils();

    protected String getSchemaBestand() {
        return schemaUtils.getXsdLeveringBevragingBerichten();
    }

    @Test
    public final void testGeefDetailsPersoon() throws Exception {
        final Node antwoord = verzendBerichtNaarService("leveren_bevragen_VraagDetailsPersoon.xml");

        printResponse(antwoord.getOwnerDocument());

        Assert.assertEquals("brp:lvg_bvgGeefDetailsPersoon_R", antwoord.getFirstChild().getNodeName());
        Assert.assertEquals(BRP_STUURGEGEVENS, antwoord.getFirstChild().getFirstChild().getNodeName());
        Assert.assertEquals(BRP_RESULTAAT, antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());
        Assert.assertEquals(BRP_PERSONEN,
                            antwoord.getFirstChild().getFirstChild().getNextSibling().getNextSibling().getNodeName());

        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    @Ignore("Niet in scope voor leveren.")
    public final void testZoekPersoon() throws Exception {
        final Node antwoord = verzendBerichtNaarService("leveren_bevragen_ZoekPersoon.xml");

        printResponse(antwoord.getOwnerDocument());

        Assert.assertEquals("brp:lvg_bvgZoekPersoon_R", antwoord.getFirstChild().getNodeName());
        Assert.assertEquals(BRP_STUURGEGEVENS, antwoord.getFirstChild().getFirstChild().getNodeName());
        Assert.assertEquals(BRP_RESULTAAT, antwoord.getFirstChild().getFirstChild().getNextSibling().getNodeName());
        Assert.assertEquals("brp:meldingen", antwoord.getFirstChild().getFirstChild().getNextSibling().getNextSibling()
                .getNodeName());
        Assert.assertEquals(BRP_PERSONEN, antwoord.getFirstChild().getFirstChild().getNextSibling().getNextSibling()
                .getNextSibling().getNodeName());

        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }
}
