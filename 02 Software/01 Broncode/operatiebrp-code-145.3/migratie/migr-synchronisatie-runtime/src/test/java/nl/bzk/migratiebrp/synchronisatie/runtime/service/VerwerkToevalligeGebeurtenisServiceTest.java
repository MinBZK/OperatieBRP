/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import javax.jms.Destination;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisOntbinding;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.PersoonControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.RelatieVinder;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisVerzender;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.VerwerkToevalligeGebeurtenisVerzoekHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeboorteVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeslachtVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.IdentificatieNummerVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.SamengesteldeNaamVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.AttribuutMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.PersoonMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.RelatieMaker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.VerbintenisMaker;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jms.core.JmsTemplate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PersoonControle.class,
        ToevalligeGebeurtenisControle.class,
        ToevalligeGebeurtenisVerzender.class,
        BrpToevalligeGebeurtenis.class,
        RelatieVinder.class,
        BrpPersoonslijst.class})
public class VerwerkToevalligeGebeurtenisServiceTest {

    @Mock
    private Lo3SyntaxControle syntaxControle;

    @Mock
    private PersoonControle persoonControle;

    @Mock
    private PreconditiesService preconditiesService;

    @Mock
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    @Mock
    private BrpPersoonslijstService persoonslijstService;

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private BrpToevalligeGebeurtenis brpToevalligeGebeurtenis;

    @Mock
    private BrpPersoonslijst brpPersoonslijst;

    @Mock
    private BrpPersoonslijst brpPersoonslijstPartner;

    @Mock
    private ObjectSleutelService objectSleutelService;

    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private Destination destination;

    private SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    private VerwerkToevalligeGebeurtenisService subject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ToevalligeGebeurtenisVerzender toevalligeGebeurtenisVerzender = new ToevalligeGebeurtenisVerzender(jmsTemplate, destination);

        final IdentificatieNummerVergelijker identificatieNummerVergelijker = new IdentificatieNummerVergelijker();
        final SamengesteldeNaamVergelijker samengesteldeNaamVergelijker = new SamengesteldeNaamVergelijker();
        final GeboorteVergelijker geboorteVergelijker = new GeboorteVergelijker();
        final GeslachtVergelijker geslachtVergelijker = new GeslachtVergelijker();
        final RelatieVinder relatieVinder =
                new RelatieVinder(identificatieNummerVergelijker, samengesteldeNaamVergelijker, geboorteVergelijker, geslachtVergelijker);

        final AttribuutMaker attribuutMaker = new AttribuutMaker();
        final PersoonMaker persoonMaker = new PersoonMaker(attribuutMaker);
        final RelatieMaker relatieMaker = new RelatieMaker(attribuutMaker);
        final VerbintenisMaker verbintenisMaker = new VerbintenisMaker(relatieMaker, persoonMaker, objectSleutelService);
        final BerichtMaker berichtMaker = new BerichtMaker(objectSleutelService, attribuutMaker, verbintenisMaker, relatieMaker, persoonMaker);

        final BrpAttribuutConverteerder brpAttribuutConverteerder = new BrpAttribuutConverteerder(new ConversietabelFactoryImpl());

        final ToevalligeGebeurtenisVerwerker toevalligeGebeurtenisVerwerker = new ToevalligeGebeurtenisVerwerker(berichtMaker);

        final ObjectSleutel persoonObjectSleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(1, 1);
        final ObjectSleutel relatieObjectSleutel = new ObjectSleutelServiceImpl().maakRelatieObjectSleutel(1);
        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(Matchers.anyInt(), Matchers.anyInt())).thenReturn(persoonObjectSleutel);
        Mockito.when(objectSleutelService.maakRelatieObjectSleutel(Matchers.anyInt())).thenReturn(relatieObjectSleutel);

        maakPersoon();
        subject = new VerwerkToevalligeGebeurtenisService(syntaxControle, toevalligeGebeurtenisVerwerker, persoonControle, toevalligeGebeurtenisVerzender,
                relatieVinder, preconditiesService, converteerLo3NaarBrpService, brpDalService, persoonslijstService, brpAttribuutConverteerder, berichtMaker);
    }

    @Test
    public void testHappyFlowOverlijden() throws Exception {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisOverlijden overlijden = VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(20161101, "0599", null, "6030");
        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getOverlijden()).thenReturn(overlijden);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("2A", null));
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }

    @Test
    public void testHappyFlowNaamGeslacht() throws Exception {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisNaamGeslacht naamGeslacht =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakNaamGeslacht(
                        new BrpPredicaatCode(Predicaat.J.getCode()),
                        "Henk",
                        "de baron van",
                        ' ',
                        null,
                        "Heusden",
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getGeslachtsaanduiding()).thenReturn(naamGeslacht);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("1H", null));
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }

    @Test
    public void testHappyFlowOmzetting() throws Exception {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        null,
                        null,
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");

        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }

    @Test
    public void testHappyFlowSluiting() throws Exception {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, null);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                false);

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }

    @Test
    public void testHappyFlowOntbinding() throws Exception {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        null,
                        null,
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisOntbinding ontbinding =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakOntbinding(new BrpRedenEindeRelatieCode('B'), 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, ontbinding, null);

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5B", null));
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNull(antwoord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenWijziging() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.verwerkBericht(verzoek);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentAdoptie() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException, ControleException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getFamilierechtelijkeBetrekking()).thenReturn(
                new BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking(
                        null,
                        null,
                        null,
                        new BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie(null, null),
                        null));
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.verwerkBericht(verzoek);
    }

    @Test
    public void testAfgekeurd() throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final OngeldigePersoonslijstException exception = new OngeldigePersoonslijstException("Preconditie afgegaan.");

        PowerMockito.when(syntaxControle.controleer(Matchers.anyListOf(Lo3CategorieWaarde.class))).thenThrow(exception);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        assertNotNull(antwoord);
        assertEquals("Status komt niet overeen.", StatusType.AFGEKEURD, antwoord.getStatus());
    }

    @Test
    public void testFoutredenG() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(null);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        assertNotNull(antwoord);
        assertEquals("Status komt niet overeen.", StatusType.FOUT, antwoord.getStatus());
        assertEquals("Foutreden komt niet overeen.", FoutredenType.G, antwoord.getFoutreden());
    }

    @Test
    public void testFoutredenV() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("059901"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        assertEquals("Status komt niet overeen.", StatusType.FOUT, antwoord.getStatus());
        assertEquals("Foutreden komt niet overeen.", FoutredenType.V, antwoord.getFoutreden());
    }

    @Test
    public void testFoutredenB() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));

        final nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering blokkering = new Blokkering("2354545000", new Timestamp(System.currentTimeMillis()));
        blokkering.setProcessId(123354L);
        blokkering.setGemeenteCodeNaar("059901");
        blokkering.setRedenBlokkering(nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP);
        PowerMockito.when(brpDalService.vraagOpBlokkering(Matchers.anyString())).thenReturn(blokkering);
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        assertEquals("Status komt niet overeen.", StatusType.FOUT, antwoord.getStatus());
        assertEquals("Foutreden komt niet overeen.", FoutredenType.B, antwoord.getFoutreden());
    }

    @Test
    public void testFoutredenN() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException, ControleException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                null,
                null,
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                false,
                false);

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(persoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString())).thenReturn(brpPersoonslijst);
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        assertEquals("Status komt niet overeen.", StatusType.FOUT, antwoord.getStatus());
        assertEquals("Foutreden komt niet overeen.", FoutredenType.N, antwoord.getFoutreden());
    }

    @Test
    public void testFoutredenU() throws IOException, OngeldigePersoonslijstException, BerichtSyntaxException, ControleException {
        final String bericht =
                IOUtils.toString(VerwerkToevalligeGebeurtenisService.class.getResourceAsStream("VerwerkToevalligeGebeurtenisVerzoekBericht.xml"));
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = (VerwerkToevalligeGebeurtenisVerzoekBericht) factory.getBericht(bericht);
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(verzoek.getTb02InhoudAlsTeletex());
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "de",
                        ' ',
                        null,
                        "Vries",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));

        PowerMockito.when(syntaxControle.controleer(lo3Inhoud)).thenReturn(lo3Inhoud);
        PowerMockito.when(converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(Matchers.any(Lo3ToevalligeGebeurtenis.class)))
                .thenReturn(brpToevalligeGebeurtenis);
        PowerMockito.doThrow(new ControleException(FoutredenType.U))
                .when(persoonControle)
                .controleer(Matchers.any(BrpPersoonslijst.class), Matchers.any(BrpToevalligeGebeurtenis.class));
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.doThrow(new IllegalStateException("Meerdere personen gevonden."))
                .when(persoonslijstService)
                .zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(Matchers.anyString());
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = subject.verwerkBericht(verzoek);
        Assert.assertNotNull(antwoord);
        assertEquals("Status komt niet overeen.", StatusType.FOUT, antwoord.getStatus());
        assertEquals("Foutreden komt niet overeen.", FoutredenType.U, antwoord.getFoutreden());
    }

    private void maakPersoon() {
        final BrpPersoonslijstBuilder builderPersoon = new BrpPersoonslijstBuilder();
        final BrpHistorie hisPersoon = BrpStapelHelper.his(19581512);
        final BrpActie actiePersoon = BrpStapelHelper.act(2, 20161501);
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamPartner =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(BrpStapelHelper.samengesteldnaam("Truus", "Vries", null, AdellijkeTitel.M.getCode()), hisPersoon, actiePersoon));
        final BrpStapel<BrpGeboorteInhoud> geboortePartner =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geboorte(19581501, "0599"), hisPersoon, actiePersoon));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtPartner =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geslacht("V"), hisPersoon, actiePersoon));
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingPartner =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding("060001", null, null), hisPersoon, actiePersoon));
        builderPersoon.geboorteStapel(geboortePartner);
        builderPersoon.geslachtsaanduidingStapel(geslachtPartner);
        builderPersoon.samengesteldeNaamStapel(samengesteldeNaamPartner);
        builderPersoon.bijhoudingStapel(bijhoudingPartner);
        builderPersoon.persoonVersie(4L);
        brpPersoonslijstPartner = builderPersoon.build();

        final BrpRelatie relatieSluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                        1L,
                        BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                        new BrpDatum(20110101, null),
                        new BrpGemeenteCode("0599"),
                        new BrpLandOfGebiedCode("6030"),
                        null,
                        new BrpDatum(20160101, null),
                        new BrpGemeenteCode("0600"),
                        new BrpLandOfGebiedCode("6030"),
                        null,
                        null,
                        geboortePartner,
                        geslachtPartner,
                        samengesteldeNaamPartner,
                        false,
                        false);
        final BrpRelatie relatieOmzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                        2L,
                        BrpSoortRelatieCode.HUWELIJK,
                        new BrpDatum(20160101, null),
                        new BrpGemeenteCode("0600"),
                        new BrpLandOfGebiedCode("6030"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        geboortePartner,
                        geslachtPartner,
                        samengesteldeNaamPartner,
                        false,
                        false);
        Arrays.asList(relatieSluiting, relatieOmzetting);
        builderPersoon.persoonId(1L);
        builderPersoon.geboorteStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geboorte(19581512, "0599"), hisPersoon, actiePersoon)));
        builderPersoon.geslachtsaanduidingStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geslacht("M"), hisPersoon, actiePersoon)));
        builderPersoon.identificatienummersStapel(
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.identificatie("12345", "56246242"), hisPersoon, actiePersoon)));
        builderPersoon.samengesteldeNaamStapel(
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(
                                BrpStapelHelper.samengesteldnaam("Jan", "Heusden", Predicaat.H.getCode(), AdellijkeTitel.B.getCode()),
                                hisPersoon,
                                actiePersoon)));
        final BrpStapel<BrpBijhoudingInhoud> bijhouding =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(
                                BrpStapelHelper.bijhouding("060001", BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.ACTUEEL),
                                hisPersoon,
                                actiePersoon));

        builderPersoon.bijhoudingStapel(bijhouding);
        builderPersoon.relaties(Arrays.asList(relatieSluiting, relatieOmzetting));
        brpPersoonslijst = builderPersoon.build();
    }
}
