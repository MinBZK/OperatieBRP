/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AangifteAdreshoudingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
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
    public void testHasPermissionUser() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        // maak een lo3 pl uit rotterdam
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_ROTTERDAM, null, null, null, null);

        assertTrue("User rotterdam should have permission", permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasNoPermissionUser() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_DENHAAG, null, null, null, null);

        assertFalse("User rotterdam should NOT have permission", permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasPermissionRelatedOuder1() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrOuder1 = 465L;
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_DENHAAG, aNrOuder1, null, null, null);
        // maak een lo3 pl uit rotterdam
        final Lo3Persoonslijst lo3PersoonslijstOuder = createLo3Persoonslijst(ROLE_ROTTERDAM, null, null, null, null);
        final Lo3Bericht lo3BerichtOuder =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);

        when(dbService.zoekLo3Bericht(aNrOuder1)).thenReturn(lo3BerichtOuder);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3BerichtOuder)).thenReturn(lo3PersoonslijstOuder);

        assertTrue("User rotterdam should have permission, because pl has relations in rotterdam", permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasPermissionRelatedOuder2() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrOuder2 = 466L;
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_DENHAAG, null, aNrOuder2, null, null);
        // maak een lo3 pl uit rotterdam
        final Lo3Persoonslijst lo3PersoonslijstOuder = createLo3Persoonslijst(ROLE_ROTTERDAM, null, null, null, null);
        final Lo3Bericht lo3BerichtOuder =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);

        when(dbService.zoekLo3Bericht(aNrOuder2)).thenReturn(lo3BerichtOuder);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3BerichtOuder)).thenReturn(lo3PersoonslijstOuder);

        assertTrue("User rotterdam should have permission, because pl has relations in rotterdam", permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasPermissionRelatedHuwelijk() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrHuwelijk = 467L;
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_DENHAAG, null, null, aNrHuwelijk, null);
        // maak een lo3 pl uit rotterdam
        final Lo3Persoonslijst lo3PersoonslijstOuder = createLo3Persoonslijst(ROLE_ROTTERDAM, null, null, null, null);
        final Lo3Bericht lo3BerichtOuder =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);

        when(dbService.zoekLo3Bericht(aNrHuwelijk)).thenReturn(lo3BerichtOuder);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3BerichtOuder)).thenReturn(lo3PersoonslijstOuder);

        assertTrue("User rotterdam should have permission, because pl has relations in rotterdam", permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasPermissionRelatedKind() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrKind = 468L;
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_DENHAAG, null, null, null, aNrKind);
        // maak een lo3 pl uit rotterdam
        final Lo3Persoonslijst lo3PersoonslijstOuder = createLo3Persoonslijst(ROLE_ROTTERDAM, null, null, null, null);
        final Lo3Bericht lo3BerichtOuder =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);

        when(dbService.zoekLo3Bericht(aNrKind)).thenReturn(lo3BerichtOuder);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3BerichtOuder)).thenReturn(lo3PersoonslijstOuder);

        assertTrue("User rotterdam should have permission, because pl has relations in rotterdam", permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasNoPermissionRelated() {
        // login als een gebruiker van gemeente rotterdam
        login("rotterdam", "rotterdam");

        final Long aNrOuder = 465L;
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst = createLo3Persoonslijst(ROLE_DENHAAG, aNrOuder, null, null, null);
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3PersoonslijstOuder = createLo3Persoonslijst(ROLE_DENHAAG, null, null, null, null);
        final Lo3Bericht lo3BerichtOuder =
                new Lo3Bericht("ggo_viewer_test", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(new Date().getTime()), "data", true);

        when(dbService.zoekLo3Bericht(aNrOuder)).thenReturn(lo3BerichtOuder);
        when(dbService.haalLo3PersoonslijstUitLo3Bericht(lo3BerichtOuder)).thenReturn(lo3PersoonslijstOuder);

        assertFalse(
            "User rotterdam should NOT have permission, because pl has NO relations in rotterdam",
            permissionService.hasPermissionOnPl(lo3Persoonslijst));
    }

    @Test
    public void testHasPermissionAdmin() {
        // login als admin
        login("admin", "admin");

        // maak een lo3 pl uit rotterdam
        final Lo3Persoonslijst lo3Persoonslijst_RT = createLo3Persoonslijst(ROLE_ROTTERDAM, null, null, null, null);
        // maak een lo3 pl uit den haag
        final Lo3Persoonslijst lo3Persoonslijst_DH = createLo3Persoonslijst(ROLE_DENHAAG, null, null, null, null);

        // admin mag alle plen inzien
        assertTrue("User admin should have permission on rotterdam", permissionService.hasPermissionOnPl(lo3Persoonslijst_RT));
        assertTrue("User admin should have permission on den haag", permissionService.hasPermissionOnPl(lo3Persoonslijst_DH));
    }

    private Lo3Persoonslijst createLo3Persoonslijst(
        final String gemeenteVanInschrijving,
        final Long aNrOuder1,
        final Long aNrOuder2,
        final Long aNrHuwelijk,
        final Long aNrKind)
    {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // Persoon
        builder.persoonStapel(VerplichteStapel.createPersoonStapel());
        // Ouder 1
        if (aNrOuder1 != null) {
            builder.ouder1Stapel(VerplichteStapel.createOuderStapel(aNrOuder1, Lo3CategorieEnum.CATEGORIE_02));
        }
        // Ouder 2
        if (aNrOuder2 != null) {
            builder.ouder2Stapel(VerplichteStapel.createOuderStapel(aNrOuder2, Lo3CategorieEnum.CATEGORIE_03));
        }
        // Huwelijk
        if (aNrHuwelijk != null) {
            builder.huwelijkOfGpStapel(createLo3Huwelijk(aNrHuwelijk));
        }

        // Inschrijving
        builder.inschrijvingStapel(VerplichteStapel.createInschrijvingStapel());

        // Verblijfplaats
        builder.verblijfplaatsStapel(createLo3Verblijfplaats(gemeenteVanInschrijving));

        // Kind
        if (aNrKind != null) {
            builder.kindStapel(createLo3Kind(aNrKind));
        }

        return builder.build();
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> createLo3Huwelijk(final Long aNrHuwelijk) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijken = new ArrayList<>();
        final Lo3HuwelijkOfGpInhoud inhoud =
                new Lo3HuwelijkOfGpInhoud(
                    Lo3Long.wrap(aNrHuwelijk),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20120101));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-3000, new Lo3GemeenteCode("0518"), Lo3String.wrap("Verblijf-Akte"), null, null, null, null, null);
        huwelijken.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0)));
        return new Lo3Stapel<>(huwelijken);
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> createLo3Verblijfplaats(final String gemeenteVanInschrijving) {
        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> verblijfplaatsen = new ArrayList<>();
        final Lo3VerblijfplaatsInhoud inhoud =
                new Lo3VerblijfplaatsInhoud(
                    new Lo3GemeenteCode(gemeenteVanInschrijving),
                    new Lo3Datum(20120101),
                    Lo3FunctieAdresEnum.WOONADRES.asElement(),
                    null,
                    new Lo3Datum(20120101),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Lo3String.wrap("locatieBeschrijving"),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Lo3AangifteAdreshoudingEnum.AMBSTHALVE.asElement(),
                    null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20120101));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-3000, new Lo3GemeenteCode(ROLE_DENHAAG), Lo3String.wrap("Verblijf-Akte"), null, null, null, null, null);
        verblijfplaatsen.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
        return new Lo3Stapel<>(verblijfplaatsen);
    }

    private Lo3Stapel<Lo3KindInhoud> createLo3Kind(final long aNrKind) {
        final List<Lo3Categorie<Lo3KindInhoud>> kids = new ArrayList<>();
        final Lo3KindInhoud inhoud = new Lo3KindInhoud(Lo3Long.wrap(aNrKind), null, null, null, null, null, null, null, null);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20120101), new Lo3Datum(20120101));
        final Lo3Documentatie documentatie =
                new Lo3Documentatie(-3000, new Lo3GemeenteCode(ROLE_DENHAAG), Lo3String.wrap("Verblijf-Akte"), null, null, null, null, null);
        kids.add(new Lo3Categorie<>(inhoud, documentatie, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 1, 0)));
        return new Lo3Stapel<>(kids);
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
}
