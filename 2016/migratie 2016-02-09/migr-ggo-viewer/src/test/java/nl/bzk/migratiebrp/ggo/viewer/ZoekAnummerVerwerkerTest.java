/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.ggo.viewer.builder.GgoPersoonslijstGroepBuilder;
import nl.bzk.migratiebrp.ggo.viewer.impl.ZoekAnummerVerwerkerImpl;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoFoutRegel;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.ggo.viewer.service.BcmService;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.ggo.viewer.service.PermissionService;
import nl.bzk.migratiebrp.ggo.viewer.service.ProtocolleringService;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = {"classpath:test-viewer-beans.xml" })
public class ZoekAnummerVerwerkerTest {

    @Mock
    private ViewerService viewerService;
    @Mock
    private DbService dbService;
    @Mock
    private BcmService bcmService;
    @Mock
    private PermissionService permissionService;
    @Mock
    private PersoonRepository persoonRepository;
    @Inject
    private JsonFormatter jsonFormatter;
    @Inject
    private GgoPersoonslijstGroepBuilder ggoPlGroepbuilder;
    @Inject
    private ProtocolleringService protocolleringService;
    // @Inject
    // @InjectMocks
    private ZoekAnummerVerwerkerImpl zoekAnummerVerwerker;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        zoekAnummerVerwerker =
                new ZoekAnummerVerwerkerImpl(dbService, viewerService, bcmService, permissionService, ggoPlGroepbuilder, protocolleringService);

        login("rotterdam", "rotterdam");
    }

    private void login(final String username, final String password) {

        // Security, login etc.
        final WebIniSecurityManagerFactory factory = new WebIniSecurityManagerFactory(Ini.fromResourcePath("classpath:shiro.ini"));
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

    @Test
    public void testZoekOpAnummer() throws Exception {
        final FoutMelder foutMelder = new FoutMelder();
        final BrpPersoonslijst brpPersoonslijst = createBrpPersoonslijst(null);
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);
        final List<GgoFoutRegel> logRegels = new ArrayList<>();

        final Long aNr = (long) 42;
        when(dbService.zoekLo3Bericht(aNr)).thenReturn(lo3Bericht);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht)).thenReturn(lo3Persoonslijst);
        when(permissionService.hasPermissionOnPl(lo3Persoonslijst)).thenReturn(Boolean.TRUE);
        when(dbService.haalLogRegelsUitLo3Bericht(lo3Bericht)).thenReturn(logRegels);
        when(bcmService.controleerDoorBCM(eq(lo3Persoonslijst), any(FoutMelder.class))).thenReturn(new ArrayList<GgoFoutRegel>());
        when(dbService.zoekBrpPersoonsLijst(aNr)).thenReturn(brpPersoonslijst);
        when(viewerService.converteerTerug(eq(brpPersoonslijst), any(FoutMelder.class))).thenReturn(lo3Persoonslijst);

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = zoekAnummerVerwerker.zoekOpAnummer(aNr, foutMelder);

        final ViewerResponse response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
        final ResponseEntity<String> result = jsonFormatter.format(response);
        assertStructuurResultaat(result.getBody());

        assertFalse(result.getBody().contains("mag niet ingezien worden"));
        assertFalse(result.getBody().contains("Geen pl gevonden"));
    }

    @Test
    public void testNoBrpPlZoekOpAnummer() throws Exception {
        final FoutMelder foutMelder = new FoutMelder();
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);
        final List<GgoFoutRegel> logRegels = new ArrayList<>();

        final Long aNr = (long) 42;
        when(dbService.zoekLo3Bericht(aNr)).thenReturn(lo3Bericht);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht)).thenReturn(lo3Persoonslijst);
        when(permissionService.hasPermissionOnPl(lo3Persoonslijst)).thenReturn(Boolean.TRUE);
        when(dbService.haalLogRegelsUitLo3Bericht(lo3Bericht)).thenReturn(logRegels);
        when(bcmService.controleerDoorBCM(eq(lo3Persoonslijst), any(FoutMelder.class))).thenReturn(new ArrayList<GgoFoutRegel>());
        when(dbService.zoekPersoon(aNr)).thenReturn(null);
        when(dbService.zoekBrpPersoonsLijst(aNr)).thenReturn(null);

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = zoekAnummerVerwerker.zoekOpAnummer(aNr, foutMelder);

        final ViewerResponse response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
        final ResponseEntity<String> result = jsonFormatter.format(response);
        assertStructuurResultaat(result.getBody());

        assertFalse(result.getBody().contains("mag niet ingezien worden"));
        assertFalse(result.getBody().contains("Geen pl gevonden"));
    }

    @Test
    public void testNoPermissionZoekOpAnummer() throws Exception {
        final FoutMelder foutMelder = new FoutMelder();
        final Lo3Persoonslijst lo3Persoonslijst = Lo3PersoonslijstTestHelper.retrieveLo3Persoonslijsten("Omzetting.txt", new FoutMelder()).get(0);
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);
        final Long aNr = (long) 42;
        when(dbService.zoekLo3Bericht(aNr)).thenReturn(lo3Bericht);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht)).thenReturn(lo3Persoonslijst);
        when(permissionService.hasPermissionOnPl(lo3Persoonslijst)).thenReturn(Boolean.FALSE);

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = zoekAnummerVerwerker.zoekOpAnummer(aNr, foutMelder);

        final ViewerResponse response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
        final ResponseEntity<String> result = jsonFormatter.format(response);

        assertTrue(result.getBody().contains("mag niet ingezien worden"));
    }

    @Test
    public void testLo3PlNotFoundZoekOpAnummer() {
        final FoutMelder foutMelder = new FoutMelder();
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);
        final Long aNr = (long) 42;
        when(dbService.zoekLo3Bericht(aNr)).thenReturn(lo3Bericht);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht)).thenReturn(null);

        final List<GgoPersoonslijstGroep> persoonslijstGroepen = zoekAnummerVerwerker.zoekOpAnummer(aNr, foutMelder);

        final ViewerResponse response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
        final ResponseEntity<String> result = jsonFormatter.format(response);

        assertTrue(result.getBody().contains("Geen PL gevonden"));
    }

    private BrpPersoonslijst createBrpPersoonslijst(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep = createIdentificatie(12345L, 54321);
        builder.identificatienummersStapel(new BrpStapel<>(idBrpGroep));
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();
        groepen.add(new BrpGroep<>(new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)), new BrpHistorie(new BrpDatum(
            20000101,
            null), new BrpDatum(20110101, null), new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date()), null), null, null, null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));
        final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<>();
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroepBetrokkenheid = createIdentificatie(43L, 43);
        betrokkenheid.add(new BrpBetrokkenheid(null, new BrpStapel<>(idBrpGroepBetrokkenheid), null, null, null, null, null, null));
        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                    BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                    BrpSoortBetrokkenheidCode.OUDER,
                    new LinkedHashMap<Long, BrpActie>());
        relatieBuilder.betrokkenheden(betrokkenheid);
        builder.relatie(relatieBuilder.build());

        return builder.build();
    }

    private List<BrpGroep<BrpIdentificatienummersInhoud>> createIdentificatie(final Long aNummer, final Integer bsn) {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep = new ArrayList<>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong(aNummer), new BrpInteger(bsn));
        final BrpHistorie historie =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep = new BrpGroep<>(inhoud, historie, null, null, null);
        idBrpGroep.add(brpIdGroep);
        return idBrpGroep;
    }

    private void assertStructuurResultaat(final String result) {
        assertTrue(result.contains("\"ggoLo3PL\" : ["));
        assertTrue(result.contains("\"ggoBrpPL\" : "));
        assertTrue(result.contains("\"foutRegels\" : ["));
        assertTrue(result.contains("\"ggoLo3PLTerugConversie\" : "));
        assertTrue(result.contains("\"vergelijking\" : "));
    }
}
