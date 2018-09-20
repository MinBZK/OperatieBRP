/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.springframework.web.servlet.tags.form.util;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import junit.framework.Assert;
import nl.bzk.brp.beheer.web.servlet.tags.AbstractTagTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.tags.NestedPathTag;
import org.springframework.web.servlet.tags.form.FormTag;


@RunWith(MockitoJUnitRunner.class)
public class FormBindTagTest extends AbstractTagTest {

    private FormBindTag formBindTag;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        formBindTag = new FormBindTag();
        formBindTag.setPageContext(getMockPageContext());
    }

    /**
     * Test dat de command attribute bescbhikbaar is gesteld.
     *
     * @throws Exception
     */
    @Test
    public void testDoAfterBodyEersteLiActief() throws Exception {
        int tagReturnValue = formBindTag.doStartTag();

        Assert.assertEquals("command wordt verwacht in de pageContext requestScope", "command",
                getMockPageContext().getAttribute(FormTag.MODEL_ATTRIBUTE_VARIABLE_NAME, PageContext.REQUEST_SCOPE));

        Assert.assertEquals("command wordt verwacht in de pageContext requestScope", "command",
                getMockPageContext().getAttribute(FormTag.COMMAND_NAME_VARIABLE_NAME, PageContext.REQUEST_SCOPE));

        Assert.assertEquals("command wordt verwacht in de pageContext requestScope", "command.",
                getMockPageContext().getAttribute(NestedPathTag.NESTED_PATH_VARIABLE_NAME, PageContext.REQUEST_SCOPE));

        // TODO zoek een manier om de controlleren of er niks geschreven is naar de pagina

        Assert.assertEquals(TagSupport.EVAL_BODY_INCLUDE, tagReturnValue);
    }

    @Test
    public void testDoEndTag() throws Exception {
        int tagReturnValue = formBindTag.doEndTag();

        // TODO zoek een manier om de controlleren of er niks geschreven is naar de pagina

        Assert.assertEquals(TagSupport.EVAL_PAGE, tagReturnValue);
    }
}
