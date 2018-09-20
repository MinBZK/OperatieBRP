/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.servlet.tags;

import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;


/**
 * Deze class kan gebruikt worden als basis voor custom tag unittest.
 *
 */
public abstract class AbstractTagTest {

    @Mock
    private RequestContext          mockRequestContext;

    private MockPageContext         mockPageContext;

    private MockHttpServletResponse mockHttpServletResponse;

    /**
     * Basis setup methode om the pageContext op te zetten.
     *
     * @throws Exception
     */
    protected void setUp() throws Exception {
        mockPageContext = new MockPageContext();
        mockPageContext.setAttribute(RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE, mockRequestContext);

        mockHttpServletResponse = new MockHttpServletResponse();
    }

    protected RequestContext getMockRequestContext() {
        return mockRequestContext;
    }


    protected MockPageContext getMockPageContext() {
        return mockPageContext;
    }

    protected MockHttpServletResponse getMockHttpServletResponse() {
        return mockHttpServletResponse;
    }

}
