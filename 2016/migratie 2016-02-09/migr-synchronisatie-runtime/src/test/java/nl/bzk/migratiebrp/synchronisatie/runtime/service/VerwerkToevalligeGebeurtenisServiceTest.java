/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AdellijkeTitelPredicaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.VerwerkToevalligeGebeurtenisVerzoekHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;

@RunWith(MockitoJUnitRunner.class)
public class VerwerkToevalligeGebeurtenisServiceTest {

    @Mock
    private ToevalligeGebeurtenisControle omzettingHuwelijkOfGeregistreerdPartnerschapControle;

    @Mock
    private ToevalligeGebeurtenisVerwerker omzettingHuwelijkOfGeregistreerdPartnerschapVerwerker;

    @Mock
    private ToevalligeGebeurtenisControle ontbindingHuwelijkOfGeregistreerdPartnerschapControle;

    @Mock
    private ToevalligeGebeurtenisVerwerker ontbindingHuwelijkOfGeregistreerdPartnerschapVerwerker;

    @Mock
    private ToevalligeGebeurtenisControle sluitingHuwelijkOfGeregistreerdPartnerschapControle;

    @Mock
    private ToevalligeGebeurtenisVerwerker sluitingHuwelijkOfGeregistreerdPartnerschapVerwerker;

    @Mock
    private ToevalligeGebeurtenisControle naamGeslachtControle;

    @Mock
    private ToevalligeGebeurtenisVerwerker naamGeslachtVerwerker;

    @Mock
    private ToevalligeGebeurtenisControle overlijdenControle;

    @Mock
    private ToevalligeGebeurtenisVerwerker overlijdenVerwerker;

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private Persoon persoon;

    @Mock(name = "gbaToevalligeGebeurtenissen")
    private Destination gbaToevalligeGebeurtenissenQueue;
    @Mock(name = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;
    @Mock
    private Session jmsSession;

    @InjectMocks
    private VerwerkToevalligeGebeurtenisService subject;

    @Mock
    private TextMessage messageToBrp;

    @Test
    public void testCorrecteSluiting() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieSluitingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                BigInteger.valueOf(19561005L),
                "0630",
                "0323",
                GeslachtsaanduidingType.V,
                "123456789",
                "5245212343",
                persoon2)));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Antwoord moet null zijn als de verwerking correct is en er geen foutreden N is opgetreden. Het BRP-bericht
        // wordt vanuit de verwerker verstuurd.
        Assert.assertNull(antwoord);
    }

    @Test
    public void testCorrecteOntbinding() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieOntbindingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            BigInteger.valueOf(20150901L),
            "0630",
                "0600"));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Antwoord moet null zijn als de verwerking correct is en er geen foutreden N is opgetreden. Het BRP-bericht
        // wordt vanuit de verwerker verstuurd.
        Assert.assertNull(antwoord);
    }

    @Test
    public void testOntbindingAntwoordBerichtFoutredenN() throws Exception {
        setupToevalligeGebeurtenisControles(false);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieOntbindingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            BigInteger.valueOf(20150901L),
            "0630",
                "0600"));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.N, antwoord.getFoutreden());
    }

    @Test
    public void testCorrecteOmzetting() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieOmzettingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            BigInteger.valueOf(20150905L),
            SoortRelatieType.P));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNull(antwoord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOntbindingIllegalArgumentException() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(false);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieOntbindingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            BigInteger.valueOf(20150901L),
            "0630",
                "0600"));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);

    }

    @Test
    public void testOmzettingAntwoordBerichtFoutredenN() throws Exception {
        setupToevalligeGebeurtenisControles(false);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieOmzettingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            BigInteger.valueOf(20150905L),
            SoortRelatieType.P));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Antwoord moet null zijn als de verwerking correct is en er geen foutreden N is opgetreden. Het BRP-bericht
        // wordt vanuit de verwerker verstuurd.

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.N, antwoord.getFoutreden());
    }

    @Test
    public void testSluitingAntwoordBerichtFoutredenN() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        setupToevalligeGebeurtenisControles(false);

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieSluitingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                BigInteger.valueOf(19561005L),
                "0630",
                "0323",
                GeslachtsaanduidingType.V,
                "123456789",
                "5245212343",
                persoon2)));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.N, antwoord.getFoutreden());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentSluiting() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(false);

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieSluitingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                BigInteger.valueOf(19561005L),
                "0630",
                "0323",
                GeslachtsaanduidingType.V,
                "123456789",
                "5245212343",
                persoon2)));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOmzettingIllegalArgument() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(false);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieOmzettingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            BigInteger.valueOf(20150905L),
            SoortRelatieType.P));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);

    }

    @Test
    public void testAntwoordBerichtFoutredenV() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieSluitingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            "0600",
            VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                BigInteger.valueOf(19561005L),
                "0630",
                "0323",
                GeslachtsaanduidingType.V,
                "123456789",
                "5245212343",
                persoon2)));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.V, antwoord.getFoutreden());
    }

    @Test
    public void testCorrecteNaamGeslachtWijziging() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setUpdatePersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaamGeslacht(GeslachtsaanduidingType.V, persoon2));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Antwoord moet null zijn als de verwerking correct is en er geen foutreden N is opgetreden. Het BRP-bericht
        // wordt vanuit de verwerker verstuurd.
        Assert.assertNull(antwoord);
    }

    @Test
    public void testNaamGeslachtWijzigingAntwoordBerichtFoutredenN() throws Exception {
        setupToevalligeGebeurtenisControles(false);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setUpdatePersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaamGeslacht(GeslachtsaanduidingType.V, persoon2));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.N, antwoord.getFoutreden());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaamGeslachtWijzigingIllegalArgumentException() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(false);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setUpdatePersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaamGeslacht(GeslachtsaanduidingType.V, persoon2));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);
    }

    @Test
    public void testCorrectOverlijden() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(BigInteger.valueOf(20150929L), "0630", "0599"));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        // Antwoord moet null zijn als de verwerking correct is en er geen foutreden N is opgetreden. Het BRP-bericht
        // wordt vanuit de verwerker verstuurd.
        Assert.assertNull(antwoord);
    }

    @Test
    public void testOverlijdenAntwoordBerichtFoutredenN() throws Exception {
        setupToevalligeGebeurtenisControles(false);
        setupToevalligeGebeurtenisVerwerkers(true);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(BigInteger.valueOf(20150929L), "0630", "0599"));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.N, antwoord.getFoutreden());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverlijdenIllegalArgumentException() throws Exception {
        setupToevalligeGebeurtenisControles(true);
        setupToevalligeGebeurtenisVerwerkers(false);
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(BigInteger.valueOf(20150929L), "0630", "0599"));
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdoptieUnsupportedOperationException() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setFamilieRechtelijkeBetrekking(VerwerkToevalligeGebeurtenisVerzoekHelper.maakFamilierechtelijkeBetrekking());
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNietsUnsupportedOperationException() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));
        verzoek.setDoelGemeente("0600");

        Mockito.when(persoon.getBijhoudingspartij()).thenReturn(new Partij("Amsterdam", 600));

        // Execute voor subject
        subject.verwerkBericht(verzoek);
    }

    @Test
    public void testFoutredenU() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));

        final List<Persoon> personen = new ArrayList<>();
        personen.add(persoon);
        personen.add(persoon);

        Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(
            Collections.checkedList(personen, Persoon.class));

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.U, antwoord.getFoutreden());
    }

    @Test
    public void testFoutredenG() throws Exception {
        Assert.assertEquals(VerwerkToevalligeGebeurtenisVerzoekBericht.class, subject.getVerzoekType());
        Assert.assertNotNull(subject.getServiceNaam());

        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setPersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
            BigInteger.valueOf(19610115L),
            "0630",
            "0323",
            GeslachtsaanduidingType.M,
            "987654321",
            "8421513215",
            persoon1));

        Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(Collections.<Persoon>emptyList());

        // Execute voor subject
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        Assert.assertNotNull(antwoord);
        Assert.assertEquals(StatusType.FOUT, antwoord.getStatus());
        Assert.assertNotNull(antwoord.getFoutreden());
        Assert.assertEquals(FoutredenType.G, antwoord.getFoutreden());
    }

    public void setupToevalligeGebeurtenisControles(final boolean resultaatwaarde) {
        Mockito.when(
            omzettingHuwelijkOfGeregistreerdPartnerschapControle.controleer(
                Matchers.any(Persoon.class),
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(resultaatwaarde);
        Mockito.when(
            ontbindingHuwelijkOfGeregistreerdPartnerschapControle.controleer(
                Matchers.any(Persoon.class),
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(resultaatwaarde);
        Mockito.when(
            sluitingHuwelijkOfGeregistreerdPartnerschapControle.controleer(
                Matchers.any(Persoon.class),
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(resultaatwaarde);
        Mockito.when(naamGeslachtControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
        .thenReturn(resultaatwaarde);
        Mockito.when(overlijdenControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
        .thenReturn(resultaatwaarde);

    }

    public void setupToevalligeGebeurtenisVerwerkers(final boolean resultaatwaarde) {
        Mockito.when(
            omzettingHuwelijkOfGeregistreerdPartnerschapVerwerker.verwerk(
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class),
                Matchers.any(Persoon.class))).thenReturn(resultaatwaarde);
        Mockito.when(
            ontbindingHuwelijkOfGeregistreerdPartnerschapVerwerker.verwerk(
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class),
                Matchers.any(Persoon.class))).thenReturn(resultaatwaarde);
        Mockito.when(
            sluitingHuwelijkOfGeregistreerdPartnerschapVerwerker.verwerk(
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class),
                Matchers.any(Persoon.class))).thenReturn(resultaatwaarde);
        Mockito.when(naamGeslachtVerwerker.verwerk(Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class), Matchers.any(Persoon.class)))
        .thenReturn(resultaatwaarde);
        Mockito.when(overlijdenVerwerker.verwerk(Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class), Matchers.any(Persoon.class))).thenReturn(
            resultaatwaarde);
    }

    @Before
    public void setup() {
        Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(
            Collections.<Persoon>singletonList(persoon));
    }
}
