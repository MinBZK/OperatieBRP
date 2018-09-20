/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.servlet.tags.menu;

import javax.servlet.jsp.tagext.TagSupport;

import junit.framework.Assert;
import nl.bzk.brp.beheer.web.servlet.tags.AbstractTagTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockBodyContent;
import org.springframework.mock.web.MockJspWriter;


@RunWith(MockitoJUnitRunner.class)
public class MenuTagTest extends AbstractTagTest {

    private static final String MENU_CONFIG = "Home=web/home.html\nBeheer=web/beheren/partijen/overzicht.html";

    private MenuTag             menuTag;

    private MockBodyContent     mockBodyContent;

    private MockJspWriter       mockJspWriter;

    /**
     * Mocken van de pageContext.
     *
     * @throws Exception
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockJspWriter = new MockJspWriter(getMockHttpServletResponse());

        menuTag = new MenuTag();
        menuTag.setPageContext(getMockPageContext());
    }

    /**
     * Test de url waarin zich bevindt is web/home.html, de eerste li met de url
     * web/home.html zou de css class active moeten hebben.
     *
     * @throws Exception
     */
    @Test
    public void testDoAfterBodyEersteLiActief() throws Exception {
        Mockito.when(getMockRequestContext().getRequestUri()).thenReturn("web/home.html");

        mockBodyContent = new MockBodyContent(MENU_CONFIG, mockJspWriter);
        menuTag.setBodyContent(mockBodyContent);
        menuTag.setLevel(1);

        menuTag.doStartTag();
        int tagReturnValue = menuTag.doAfterBody();
        String output = getMockHttpServletResponse().getContentAsString();

        Assert.assertEquals(TagSupport.SKIP_BODY, tagReturnValue);

        Assert.assertEquals(
                "Twee <li> zou de class active moeten hebben",
                "<ul><li class=\"active\"><a href=\"web/home.html\">Home</a></li><li class=\"\"><a href=\"web/beheren/partijen/overzicht.html\">Beheer</a></li></ul>",
                output);
    }

    /**
     * Test de url waarin zich bevindt is web/beheren/partijen/overzicht.html,
     * de tweede li met de url web/beheren/partijen/overzicht.html zou de css
     * class active moeten hebben.
     *
     * @throws Exception
     */
    @Test
    public void testDoAfterBodyTweedeLiActief() throws Exception {
        Mockito.when(getMockRequestContext().getRequestUri()).thenReturn("web/beheren/partijen/overzicht.html");

        mockBodyContent = new MockBodyContent(MENU_CONFIG, mockJspWriter);
        menuTag.setBodyContent(mockBodyContent);
        menuTag.setLevel(1);
        menuTag.setCssClass("cssClass");
        menuTag.setLiCssClass("liClass");

        menuTag.doStartTag();
        int tagReturnValue = menuTag.doAfterBody();
        String output = getMockHttpServletResponse().getContentAsString();

        Assert.assertEquals(TagSupport.SKIP_BODY, tagReturnValue);

        Assert.assertEquals(
                "Twee <li> zou de class active moeten hebben",
                "<ul class=\"cssClass\"><li class=\"liClass \"><a href=\"web/home.html\">Home</a></li><li class=\"liClass active\"><a href=\"web/beheren/partijen/overzicht.html\">Beheer</a></li></ul>",
                output);
    }

    /**
     * Test twee niveau omhoog in de url abc/def/ghi/jkl.html zou overeen moeten
     * komen met abc/def/aaa/aaa/html. Want twee niveau omhoog geeft allebei de
     * url abc/def.
     *
     * @throws Exception
     */
    @Test
    public void testDoAfterBodyTweeNiveauOmhoog() throws Exception {
        Mockito.when(getMockRequestContext().getRequestUri()).thenReturn("abc/def/ghi/jkl.html");

        mockBodyContent = new MockBodyContent("Home=abc/def/aaa/aaa.html", mockJspWriter);
        menuTag.setBodyContent(mockBodyContent);
        menuTag.setLevel(2);

        menuTag.doStartTag();
        int tagReturnValue = menuTag.doAfterBody();
        String output = getMockHttpServletResponse().getContentAsString();

        Assert.assertEquals(TagSupport.SKIP_BODY, tagReturnValue);

        Assert.assertEquals("li zou actief moeten zijn",
                "<ul><li class=\"active\"><a href=\"abc/def/aaa/aaa.html\">Home</a></li></ul>", output);
    }

    /**
     * Test een niveau omhoog in de url abc/def/ghi/jkl.html zou niet overeen
     * moeten komen met abc/def/aaa/aaa/html. Want een niveau omhoog geeft
     * abc/def/ghi en abc/def/aaa terug.
     *
     * @throws Exception
     */
    @Test
    public void testDoAfterBodyEenNiveauOmhoogNietActief() throws Exception {
        Mockito.when(getMockRequestContext().getRequestUri()).thenReturn("abc/def/ghi/jkl.html");

        mockBodyContent = new MockBodyContent("Home=abc/def/aaa/aaa.html", mockJspWriter);
        menuTag.setBodyContent(mockBodyContent);
        menuTag.setLevel(1);

        menuTag.doStartTag();
        int tagReturnValue = menuTag.doAfterBody();
        String output = getMockHttpServletResponse().getContentAsString();

        Assert.assertEquals(TagSupport.SKIP_BODY, tagReturnValue);

        Assert.assertFalse("li zou niet actief moeten zijn",
                "<ul><li class=\"active\"><a href=\"abc/def/aaa/aaa.html\">Home</a></li></ul>".equals(output));
    }
}
