/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.controller;

import nl.bzk.brp.beheer.web.messages.MessageUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

//CHECKSTYLE:OFF
@RunWith(MockitoJUnitRunner.class)
public class AbstractDetailControllerTest {
//CHECKSTYLE:ON
    private AbstractDetailController      baseController;

    @Mock
    private MessageUtil             messageUtil;

    private MockHttpServletRequest  httpServletRequest;

    private MockHttpServletResponse httpServletResponse;

    @Before
    public void setUp() {
        httpServletRequest = new MockHttpServletRequest();
        httpServletResponse = new MockHttpServletResponse();

        baseController = new AbstractDetailController() {
        };

        baseController.setMessageUtil(messageUtil);

        httpServletRequest.setRequestURI("/web/abc.html");
    }

    /**
     * Test of de redirect geen parameters bevat.
     *
     * @throws Exception
     */
    @Test
    public void redirect() throws Exception {
        ModelAndView modelAndView = baseController.redirect("http://locatie");

        RedirectView redirectView = (RedirectView) modelAndView.getView();

        ModelMap modelMap = new ModelMap();
        modelMap.put("param1", "value1");
        redirectView.render(modelMap, httpServletRequest, httpServletResponse);

        Assert.assertEquals("http://locatie", httpServletResponse.getRedirectedUrl());
    }
}
