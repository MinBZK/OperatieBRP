/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Abstracte basis voor het testen van verzoekparsers.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractVerzoekParserTest {

    @Mock
    protected Node node;
    @Mock
    protected XPath xPath;

    protected final void mockNode(final String locatie) throws XPathExpressionException {
        mockNode(locatie, locatie);
    }

    protected final void mockNode(final String locatie, final String waarde) throws XPathExpressionException {
        final Node node = mock(Node.class);
        when(xPath.evaluate(locatie, this.node, XPathConstants.NODE)).thenReturn(node);
        when(node.getTextContent()).thenReturn(waarde);
    }

    protected final void mockNodeListLength(final String locatie, final int length) throws XPathExpressionException {
        final NodeList node = mock(NodeList.class);
        when(xPath.evaluate(locatie, this.node, XPathConstants.NODESET)).thenReturn(node);
        when(node.getLength()).thenReturn(length);
    }

    final void mockNodeList(final String locatie, final String... waarden) throws XPathExpressionException {
        final NodeList nodeList = mock(NodeList.class);
        when(xPath.evaluate(locatie, this.node, XPathConstants.NODESET)).thenReturn(nodeList);
        final List<Node> nodes = Lists.newArrayList();
        for (final String waarde : waarden) {
            final Node mock = mock(Node.class);
            nodes.add(mock);
            when(mock.getTextContent()).thenReturn(waarde);
        }
        when(nodeList.item(anyInt())).thenAnswer(a -> nodes.get((int) a.getArguments()[0]));
    }

    final void mockNodeListException(final String locatie) throws XPathExpressionException {
        when(xPath.evaluate(locatie, this.node, XPathConstants.NODESET)).thenThrow(new XPathExpressionException("Error"));
    }
}
