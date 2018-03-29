/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.ggo.viewer.util.PortInitializer;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadState;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml"}, initializers = {PortInitializer.class})
public class ViewerControllerTest {

    private static final String RESULTAAT_MAG_NIET_NULL_ZIJN = "Resultaat mag niet null zijn";
    @Mock
    private ZoekAnummerVerwerker zoekAnummerVerwerker;
    @Inject
    @InjectMocks
    private ViewerController viewerController;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetIndex() {
        login("admin", "admin");
        assertEquals("viewerDemo", viewerController.getIndex().getViewName());
    }

    @Test
    public final void zoekOpAnummerHappyWelPL() {
        final FoutMelder foutMelder = new FoutMelder();
        final String aNummer = "42";
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = new ArrayList<>();
        when(zoekAnummerVerwerker.zoekOpAnummer(aNummer, foutMelder)).thenReturn(persoonslijstGroepen);

        final ResponseEntity<String> result = viewerController.zoekOpAnummer(aNummer);
        assertNotNull(RESULTAAT_MAG_NIET_NULL_ZIJN, result);
    }

    @Test
    public final void zoekOpAnummerHappyGeenPL() {
        final FoutMelder foutMelder = new FoutMelder();
        final String aNummer = "42";

        Mockito.when(zoekAnummerVerwerker.zoekOpAnummer(aNummer, foutMelder)).thenReturn(null);

        final ResponseEntity<String> result = viewerController.zoekOpAnummer(aNummer);
        assertNotNull(RESULTAAT_MAG_NIET_NULL_ZIJN, result);
    }

    @Test
    public final void uploadRequestHappy() throws IOException {
        final String filename = "";
        final byte[] file = "".getBytes();

        final ResponseEntity<String> result = viewerController.uploadRequest(filename, file);
        assertNotNull(RESULTAAT_MAG_NIET_NULL_ZIJN, result);
    }

    @Test
    public final void uploadRequestFileNameHappy() throws IOException {
        final String filename = "";
        final ResponseEntity<String> result = viewerController.uploadRequestFileName(filename);

        assertNotNull(RESULTAAT_MAG_NIET_NULL_ZIJN, result);
    }

    @Test
    public final void uploadRequestMSIE() {
        final MultipartFile multipartFile = new MockMultipartFile("asdf", new byte[]{});
        final ResponseEntity<String> result = viewerController.uploadRequestMSIE(multipartFile);

        assertNotNull(RESULTAAT_MAG_NIET_NULL_ZIJN, result);
    }

    private void login(final String username, final String password) {

        // Security, login etc.
        final Factory<SecurityManager> factory = new WebIniSecurityManagerFactory(Ini.fromResourcePath("classpath:shiro.ini"));
        final SecurityManager securityManager = factory.getInstance();

        final DefaultWebSessionContext ctx = new DefaultWebSessionContext();
        ctx.setServletRequest(new MockHttpServletRequest());
        ctx.setServletResponse(new MockHttpServletResponse());
        final Session session = securityManager.start(ctx);

        final Subject currentUser = new Subject.Builder(securityManager).session(session).buildSubject();
        final ThreadState shiroThreadState = new SubjectThreadState(currentUser);
        shiroThreadState.bind();

        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        currentUser.login(token);
    }

}
