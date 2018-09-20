/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceTest {
    private static final String ROLE_ROTTERDAM = "0599";
    private static final String ROLE_DENHAAG = "0518";

    @Mock
    private DbService dbService;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    public void testHasPermissionUser() throws IOException {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        // maak een brp pl uit rotterdam
        final BrpPersoonslijst brpPersoonslijst =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_ROTTERDAM)), null);

        assertTrue("User rotterdam should have permission", permissionService.hasPermissionOnPl(brpPersoonslijst));
    }

    @Test
    public void testHasNoPermissionUser() throws IOException {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        // maak een brp pl uit den haag
        final BrpPersoonslijst brpPersoonslijst =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_DENHAAG)), null);

        assertFalse("User rotterdam should NOT have permission",
                permissionService.hasPermissionOnPl(brpPersoonslijst));
    }

    @Test
    public void testHasPermissionRelated() throws IOException {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrOuder = 465L;
        // maak een brp pl uit den haag
        final BrpPersoonslijst brpPersoonslijst =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_DENHAAG)), aNrOuder);
        // maak een brp pl uit rotterdam
        final BrpPersoonslijst brpPersoonslijstOuder =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_ROTTERDAM)), null);

        when(dbService.zoekBrpPersoonsLijst(aNrOuder)).thenReturn(brpPersoonslijstOuder);

        assertTrue("User rotterdam should have permission, because pl has relations in rotterdam",
                permissionService.hasPermissionOnPl(brpPersoonslijst));
    }

    @Test
    public void testHasNoPermissionRelated() throws IOException {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrOuder = 465L;
        // maak een brp pl uit den haag
        final BrpPersoonslijst brpPersoonslijst =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_DENHAAG)), aNrOuder);
        // maak een brp pl uit den haag
        final BrpPersoonslijst brpPersoonslijstOuder =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_DENHAAG)), null);

        when(dbService.zoekBrpPersoonsLijst(aNrOuder)).thenReturn(brpPersoonslijstOuder);

        assertFalse("User rotterdam should NOT have permission, because pl has NO relations in rotterdam",
                permissionService.hasPermissionOnPl(brpPersoonslijst));
    }

    @Test
    public void testHasPermissionAdmin() throws IOException {
        // login als admin
        login("admin", "admin");

        // maak een brp pl uit rotterdam
        final BrpPersoonslijst brpPersoonslijst_RT =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_ROTTERDAM)), null);
        // maak een brp pl uit den haag
        final BrpPersoonslijst brpPersoonslijst_DH =
                createBrpPersoonslijst(new BrpGemeenteCode(new BigDecimal(ROLE_DENHAAG)), null);

        // admin mag alle plen inzien
        assertTrue("User admin should have permission on rotterdam",
                permissionService.hasPermissionOnPl(brpPersoonslijst_RT));
        assertTrue("User admin should have permission on den haag",
                permissionService.hasPermissionOnPl(brpPersoonslijst_DH));
    }

    private BrpPersoonslijst createBrpPersoonslijst(final BrpGemeenteCode gemeenteCode, final Long aNrOuder) {
        BrpGemeenteCode brpGemeenteCode = gemeenteCode;
        if (brpGemeenteCode == null) {
            brpGemeenteCode = new BrpGemeenteCode(new BigDecimal(ROLE_ROTTERDAM));
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingsgemeenteInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBijhoudingsgemeenteInhoud>>();
        groepen.add(new BrpGroep<BrpBijhoudingsgemeenteInhoud>(new BrpBijhoudingsgemeenteInhoud(brpGemeenteCode,
                BrpDatum.ONBEKEND, false), new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101),
                new BrpDatumTijd(new Date()), new BrpDatumTijd(new Date())), null, null, null));
        builder.bijhoudingsgemeenteStapel(new BrpStapel<BrpBijhoudingsgemeenteInhoud>(groepen));

        if (aNrOuder != null) {
            // final BrpRelatie relatie = new BrpRelatie(new BrpSoortRelatieCode(), new BrpSoortBetrokkenheidCode, new
            // List<BrpBetrokkenheid>(), new BrpStapel<BrpRelatieInhoud>());
            final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<BrpBetrokkenheid>();
            final BrpIdentificatienummersInhoud idInhoud =
                    new BrpIdentificatienummersInhoud(aNrOuder, Long.valueOf(888));
            final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep =
                    new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();
            final BrpHistorie historie =
                    new BrpHistorie(new BrpDatum(20000101), new BrpDatum(20110101), new BrpDatumTijd(new Date()),
                            new BrpDatumTijd(new Date()));
            final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep =
                    new BrpGroep<BrpIdentificatienummersInhoud>(idInhoud, historie, null, null, null);

            idBrpGroep.add(brpIdGroep);
            betrokkenheid.add(new BrpBetrokkenheid(null, new BrpStapel<BrpIdentificatienummersInhoud>(idBrpGroep),
                    null, null, null, null, null));

            final BrpRelatie relatie =
                    new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                            BrpSoortBetrokkenheidCode.OUDER, betrokkenheid, null);

            builder.relatie(relatie);
        }

        return builder.build();
    }

    private void login(final String username, final String password) {

        // Security, login etc.
        final Factory<SecurityManager> factory =
                new WebIniSecurityManagerFactory(Ini.fromResourcePath("classpath:shiro.ini"));
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
