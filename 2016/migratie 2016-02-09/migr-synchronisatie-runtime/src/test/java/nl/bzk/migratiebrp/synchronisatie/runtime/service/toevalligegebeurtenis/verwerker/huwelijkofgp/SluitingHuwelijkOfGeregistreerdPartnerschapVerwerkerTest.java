/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.huwelijkofgp;

import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.jms.Destination;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import nl.bzk.migratiebrp.bericht.model.brp.generated.AdministratieveHandelingCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.HandelingGBASluitingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.PartijCode;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingswijzeNaamS;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdellijkeTitelPredicaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.VerwerkToevalligeGebeurtenisVerzoekHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.huwelijkofgp.SluitingHuwelijkOfGeregistreerdPartnerschapControle;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

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
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SluitingHuwelijkOfGeregistreerdPartnerschapVerwerkerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Mock
    @Named("persoonControle")
    private ToevalligeGebeurtenisControle persoonControle;

    @InjectMocks
    private SluitingHuwelijkOfGeregistreerdPartnerschapControle sluitingHuwelijkOfGeregistreerdPartnerschapControle;

    @Mock
    @Named(value = "brpQueueJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Mock
    @Named(value = "gbaToevalligeGebeurtenissen")
    private Destination destination;

    @Mock
    private ConversietabelFactory conversieTabellen;

    @Mock
    private Conversietabel<Lo3GemeenteCode, BrpPartijCode> partijConversietabel;

    @Mock
    private Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> soortRelatieConversietabel;

    @Mock
    private Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> adellijkeTitelPredicaatConversietabel;

    @Mock
    private Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> geslachtsaanduidingConversietabel;

    @Mock
    private Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> landConversietabel;

    @Mock
    private Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> gemeenteConversietabel;

    @Mock
    private Lo3AttribuutConverteerder converteerder;

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private Persoon persoon;

    @InjectMocks
    private SluitingHuwelijkOfGeregistreerdPartnerschapVerwerker subject;

    @Test
    public void testGevuldBinnenland() {
        setupGerelateerde(false, false);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBinnenland();
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap());
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getBronnen());
    }

    @Test
    public void testGevuldBuitenland() {
        setupGerelateerde(true, false);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBuitenland(false);
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap());
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getBronnen());
    }

    @Test
    public void testGevuldBuitenlandLangePlaatsnaam() {
        setupGerelateerde(false, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBuitenland(true);
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap());
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBASluitingHuwelijkGeregistreerdPartnerschap().getValue().getBronnen());
    }

    @Test
    public void testVerwerkTrue() {
        setupControle(false);
        setupGerelateerde(false, false);
        setupControle(true);
        Assert.assertTrue(subject.verwerk(maakVerzoekBinnenland(), persoon));
    }

    @Before
    public void setup() {
        final BrpGeslachtsaanduidingCode man = new BrpGeslachtsaanduidingCode("M");
        final AdellijkeTitelPredikaatPaar adellijketitelpaar = new AdellijkeTitelPredikaatPaar(new BrpCharacter('B'), new BrpCharacter('B'), man);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(Long.valueOf(141343L));
        Mockito.when(landConversietabel.converteerNaarBrp(Matchers.any(Lo3LandCode.class))).thenReturn(new BrpLandOfGebiedCode(Short.valueOf("6030")));
        Mockito.when(gemeenteConversietabel.converteerNaarBrp(Matchers.any(Lo3GemeenteCode.class))).thenReturn(new BrpGemeenteCode(Short.valueOf("0599")));
        Mockito.when(geslachtsaanduidingConversietabel.converteerNaarBrp(Matchers.any(Lo3Geslachtsaanduiding.class))).thenReturn(man);
        Mockito.when(adellijkeTitelPredicaatConversietabel.converteerNaarBrp(Matchers.any(Lo3AdellijkeTitelPredikaatCode.class))).thenReturn(
            adellijketitelpaar);
        Mockito.when(soortRelatieConversietabel.converteerNaarBrp(Matchers.any(Lo3SoortVerbintenis.class))).thenReturn(BrpSoortRelatieCode.HUWELIJK);
        Mockito.when(partijConversietabel.converteerNaarBrp(Matchers.any(Lo3GemeenteCode.class))).thenReturn(new BrpPartijCode(Integer.valueOf("0600")));
        Mockito.when(conversieTabellen.createSoortRelatieConversietabel()).thenReturn(soortRelatieConversietabel);
        Mockito.when(conversieTabellen.createPartijConversietabel()).thenReturn(partijConversietabel);
        Mockito.when(conversieTabellen.createAdellijkeTitelPredikaatConversietabel()).thenReturn(adellijkeTitelPredicaatConversietabel);
        Mockito.when(conversieTabellen.createGeslachtsaanduidingConversietabel()).thenReturn(geslachtsaanduidingConversietabel);
        Mockito.when(conversieTabellen.createLandConversietabel()).thenReturn(landConversietabel);
        Mockito.when(conversieTabellen.createGemeenteConversietabel()).thenReturn(gemeenteConversietabel);
        ReflectionTestUtils.setField(converteerder, "conversietabellen", conversieTabellen);
    }

    private void setupGerelateerde(final boolean meerdereGerelateerden, final boolean geenGerelateerden) {
        if (geenGerelateerden) {
            Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(Collections.<Persoon>emptyList());
        } else if (!meerdereGerelateerden) {
            Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(
                Collections.<Persoon>singletonList(persoon));
        } else {
            final List<Persoon> personen = new ArrayList<>();
            personen.add(persoon);
            personen.add(persoon);
            Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(personen);
        }
    }

    private void setupControle(final boolean returnValueControle) {
        ReflectionTestUtils.setField(sluitingHuwelijkOfGeregistreerdPartnerschapControle, "persoonControle", persoonControle);
        Mockito.when(
            sluitingHuwelijkOfGeregistreerdPartnerschapControle.controleer(
                Matchers.any(Persoon.class),
                Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(returnValueControle);
    }

    private VerwerkToevalligeGebeurtenisVerzoekBericht maakVerzoekBinnenland() {
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

        return verzoek;
    }

    private VerwerkToevalligeGebeurtenisVerzoekBericht maakVerzoekBuitenland(final boolean gebruikLangePlaatsnaam) {
        final NaamGroepType persoon1 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.B, "Heusden", "Jan", "van");
        final NaamGroepType persoon2 = VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaam(AdellijkeTitelPredicaatType.BS, "Vries", "Truus", "de");
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final String plaatsnaam = gebruikLangePlaatsnaam ? "New York" : "Bali";
        verzoek.setAkte(VerwerkToevalligeGebeurtenisVerzoekHelper.maakAkteGroep("12312B23", "0599"));
        verzoek.setCorrelationId("correlatie");
        verzoek.setGeldigheid(VerwerkToevalligeGebeurtenisVerzoekHelper.maakGeldigheid(BigInteger.valueOf(20150915L)));
        verzoek.setMessageId("messageId");
        verzoek.setRelatie(VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatieSluitingHuwelijk(
            SoortRelatieType.H,
            BigInteger.valueOf(20150901L),
            "0630",
            plaatsnaam,
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

        return verzoek;
    }

    @Test
    public void testMarshallen() throws Exception {
        setupGerelateerde(false, false);
        final ObjectFactory brpObjectFactory = new ObjectFactory();
        final MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding testBericht =
                new MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding();
        final HandelingGBASluitingHuwelijkGeregistreerdPartnerschap handeling = new HandelingGBASluitingHuwelijkGeregistreerdPartnerschap();
        AdministratieveHandelingCode code = new AdministratieveHandelingCode();
        code.setValue("CodeCode");
        handeling.setCode(brpObjectFactory.createObjecttypeAdministratieveHandelingCode(code));
        testBericht.setGBASluitingHuwelijkGeregistreerdPartnerschap(brpObjectFactory.createObjecttypeBerichtGBASluitingHuwelijkGeregistreerdPartnerschap(handeling));
        PartijCode partijCode = new PartijCode();
        partijCode.setValue("BlaBla");
        handeling.setPartijCode(brpObjectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));
        JAXBElement<MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding> jaxbBericht =
                brpObjectFactory.createIscMigRegistreerHuwelijkGeregistreerdPartnerschap(testBericht);
        JAXBContext jaxbContext = JAXBContext.newInstance("nl.bzk.migratiebrp.bericht.model.brp.generated");
        final StringWriter result = new StringWriter();
        jaxbContext.createMarshaller().marshal(jaxbBericht, result);
        LOGGER.info(result.toString());
    }

}
