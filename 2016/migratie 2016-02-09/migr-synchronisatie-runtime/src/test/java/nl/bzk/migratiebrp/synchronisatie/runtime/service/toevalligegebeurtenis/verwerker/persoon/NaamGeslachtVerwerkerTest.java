/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.persoon;

import java.math.BigInteger;
import java.util.Collections;

import javax.inject.Named;
import javax.jms.Destination;

import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtBijhouding;
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
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon.NaamGeslachtControle;
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
public class NaamGeslachtVerwerkerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Mock
    @Named("geslachtsaanduidingControle")
    private ToevalligeGebeurtenisControle geslachtsaanduidingControle;

    @Mock
    @Named("geslachtsnaamComponentenControle")
    private ToevalligeGebeurtenisControle geslachtsnaamComponentenControle;

    @InjectMocks
    private NaamGeslachtControle naamGeslachtControle;

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
    private NaamGeslachtVerwerker subject;

    @Test
    public void testGevuldBinnenland() {
        setupControle(true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBinnenland();
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getBronnen());
    }

    @Test
    public void testLeegGeslacht() {
        setupControle(true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBinnenland();
        verzoek.getUpdatePersoon().setGeslacht(null);
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getBronnen());
    }

    @Test
    public void testLegeNaam() {
        setupControle(true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBinnenland();
        verzoek.getUpdatePersoon().setNaam(null);
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getBronnen());
    }

    @Test
    public void testGevuldBuitenland() {
        setupControle(true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBuitenland(false);
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getBronnen());
    }

    @Test
    public void testGevuldBuitenlandLangePlaatsnaam() {
        setupControle(true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBuitenland(true);
        final ObjecttypeBerichtBijhouding opdracht = subject.maakBrpToevalligeGebeurtenisOpdracht(verzoek, persoon);
        LOGGER.info("Resultaat: " + opdracht.toString());
        Assert.assertNotNull(opdracht.getStuurgegevens());
        Assert.assertEquals(opdracht.getStuurgegevens().getValue().getZendendeSysteem().getValue().getValue(), "ISC");
        Assert.assertNotNull(opdracht.getParameters());
        Assert.assertEquals(opdracht.getParameters().getValue().getVerwerkingswijze().getValue().getValue(), VerwerkingswijzeNaamS.BIJHOUDING);
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode());
        Assert.assertEquals(opdracht.getGBAWijzigingNaamGeslacht().getValue().getPartijCode().getValue().getValue(), "600");
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getActies());
        Assert.assertNotNull(opdracht.getGBAWijzigingNaamGeslacht().getValue().getBronnen());
    }

    @Test
    public void testVerwerkTrue() {
        setupControle(true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = maakVerzoekBinnenland();
        Assert.assertTrue(subject.verwerk(verzoek, persoon));
    }

    @Before
    public void setup() {
        final BrpGeslachtsaanduidingCode man = new BrpGeslachtsaanduidingCode("M");
        final AdellijkeTitelPredikaatPaar adellijketitelpaar = new AdellijkeTitelPredikaatPaar(new BrpCharacter('B'), new BrpCharacter('B'), man);
        new AdellijkeTitelPredikaatPaar(new BrpCharacter('B'), new BrpCharacter('B'), man);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(Long.valueOf(141343L));
        Mockito.when(brpDalService.zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyLong())).thenReturn(
            Collections.<Persoon>singletonList(persoon));
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

    private void setupControle(final boolean returnValueControle) {
        Mockito.when(geslachtsaanduidingControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
               .thenReturn(returnValueControle);
        Mockito.when(
                   geslachtsnaamComponentenControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
               .thenReturn(returnValueControle);

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
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(BigInteger.valueOf(20150920L), "0630", "Amsterdam"));
        verzoek.setUpdatePersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaamGeslacht(GeslachtsaanduidingType.V, persoon2));
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
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(
            BigInteger.valueOf(20150920L),
            "Verenigde Staten",
            gebruikLangePlaatsnaam ? "New York" : "Rome"));
        verzoek.setUpdatePersoon(VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaamGeslacht(GeslachtsaanduidingType.V, persoon2));

        return verzoek;
    }

}
