/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.xml.xpath.XPath;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Node;

/**
 * Unit test voor {@link AbstractGeneriekeBerichtParser}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractGeneriekeBerichtParserTest {

    private TestGeneriekeBerichtParser parser;

    @Mock
    private Node node;

    @Mock
    private VerzoekParser<Verzoek> dienstVerzoekParser;

    @Before
    public void setUp() throws Exception {
        parser = new TestGeneriekeBerichtParser();
    }

    @Test
    public void testParse() throws Exception {
        final Verzoek verzoek = parser.parse(node);

        verify(dienstVerzoekParser, times(1)).vulStuurgegevens(any(Verzoek.class), any(Node.class), any(XPath.class));
        verify(dienstVerzoekParser, times(1)).vulParameters(any(Verzoek.class), any(Node.class), any(XPath.class));
        verify(dienstVerzoekParser, times(1)).vulDienstSpecifiekeGegevens(any(Verzoek.class), any(Node.class), any(XPath.class));
        assertThat(verzoek, is(not(nullValue())));
    }

    private final class TestGeneriekeBerichtParser extends AbstractGeneriekeBerichtParser<Verzoek> {

        @Override
        protected VerzoekParser<Verzoek> geefDienstSpecifiekeParser(final Node nodeName) {
            return dienstVerzoekParser;
        }

        @Override
        protected Verzoek maakVerzoek() {
            return new TestVerzoek();
        }

        private final class TestVerzoek extends VerzoekBasis implements Verzoek {

        }
    }
}
