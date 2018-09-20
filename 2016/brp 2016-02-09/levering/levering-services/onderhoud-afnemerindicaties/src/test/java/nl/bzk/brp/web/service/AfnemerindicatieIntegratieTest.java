/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.utils.XmlUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Integratietest voor synchronisatie persoon.
 *
 * Ignore ivm TEAMBRP-4135.
 */
@Ignore
public class AfnemerindicatieIntegratieTest extends AbstractAfnemerindicatieIntegrationTest {

    @Test
    @Category(nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase.class)
    public final void testPlaatsAfnemerindicatie() throws Exception {
        final Node antwoord = verzendBerichtNaarService("leveren_RegistreerAfnemerindicatieOpBsn.xml");
        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);

        // Verifieer inhoud van antwoordbericht
        logger.info("XML: " + xml);
        assertVerwerkingsresultaat(Verwerkingsresultaat.GESLAAGD, document);

        // Schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    @Category(nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase.class)
    public final void testVerwijderAfnemerindicatie() throws Exception {
        final Node antwoord = verzendBerichtNaarService("leveren_VerwijderAfnemerindicatieOpBsn.xml");
        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);

        // Verifieer inhoud van antwoordbericht
        logger.info("XML bericht: " + xml);
        assertVerwerkingsresultaat(Verwerkingsresultaat.GESLAAGD, document);

        // Schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }
}
