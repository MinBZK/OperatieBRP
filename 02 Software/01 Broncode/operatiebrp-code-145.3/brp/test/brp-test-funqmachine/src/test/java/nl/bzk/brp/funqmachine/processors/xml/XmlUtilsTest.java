/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

public class XmlUtilsTest {
    @Test
    public void kanDocumentMaken() throws XmlException {
        final Document document = XmlUtils.bouwDocument("<xml><child></child></xml>");
        assertEquals(1, document.getChildNodes().getLength());
    }

    @Test
    public void kanBrpDocumentMaken() throws XmlException {
        final Document document = XmlUtils.bouwDocument("<brp:xml xmlns:brp=\"http://www.bzk.nl/brp/brp0200\"><brp:child></brp:child></brp:xml>");
        assertEquals(1, document.getChildNodes().getLength());
    }

    @Test
    public void kanNodeOphalen() throws XmlException {
        final Document document = XmlUtils.bouwDocument("<brp:xml xmlns:brp=\"http://www.bzk.nl/brp/brp0200\"><brp:child></brp:child></brp:xml>");
        Node node = XmlUtils.getNode("//brp:child", document);
        final Diff diff = DiffBuilder.compare("<?xml version=\"1.0\" encoding=\"UTF-8\"?><brp:child xmlns:brp=\"http://www.bzk.nl/brp/brp0200\"/>")
                .withTest(XmlUtils.toXmlString(node)).withNamespaceContext(XmlUtils.getNamespaces()).ignoreComments().ignoreWhitespace().build();
        assertFalse(diff.hasDifferences());
    }

    @Test
    public void prettifyXml() throws XmlException {
        final String pretty =
                XmlUtils.prettify(
                        "<brp:xml xmlns:brp=\"http://www.bzk.nl/brp/brp0200\"><brp:child></brp:child><brp:child>content</brp:child><brp:child> "
                                + "</brp:child></brp:xml>");

        assert pretty.contains("<brp:child> </brp:child>");
    }
}
