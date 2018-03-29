/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.REFERENTIE_ID_ATTRIBUUT;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link AbstractActieElement}. Deze test test alleen de specifieke code in de genoemde class.
 */
public class ActieElementTest extends AbstractElementTest {

    private static final Integer PERSOON_ID = 1;
    private static final StringElement STRING_ELEMENT_PARTIJ_CODE = new StringElement("053001");
    private static final String PERSOON_SLEUTEL = "persoon.sleutel.1";
    private static final String PSEUDO_PERSOON_SLEUTEL = "pseudo.persoon.sleutel.1";
    private Map<String, String> objecttypeMap;
    private BijhoudingVerzoekBericht bericht;
    private final List<BronElement> bronnen = new ArrayList<>();
    private Partij partij;
    private TestActieElement hoofdActie;
    private BijhoudingPersoon persoonEntiteit = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
    private BijhoudingPersoon pseudoPersoonEntiteit = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
    private PersoonElement persoonElement;
    private PersoonElement pseudoPersoonElement;
    private PersoonElement pseudoPersoonElementGroepenEnSleutel;
    private ElementBuilder builder;

    @Before
    public void setUpTest() throws OngeldigeObjectSleutelException {
        partij = new Partij("partij", "053001");
        partij.setDatumIngang(19700101);
        objecttypeMap = new AbstractBmrGroep.AttributenBuilder().objecttype("objecttype").objectSleutel("s532era3s==").communicatieId("ci_test").build();
        bericht = mock(BijhoudingVerzoekBericht.class);
        builder = new ElementBuilder();
        final Map<String, String>
                att =
                new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_persoon").objectSleutel(PSEUDO_PERSOON_SLEUTEL).objecttype("Persoon").build();
        final Map<String, String>
                attPseudo =
                new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_persoon2").objectSleutel(PSEUDO_PERSOON_SLEUTEL).objecttype("Persoon").build();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        pseudoPersoonElement = builder.maakPersoonGegevensElement(att, params);
        pseudoPersoonElement.setVerzoekBericht(bericht);
        final ElementBuilder.PersoonParameters paramsPseudo = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters gebPara = new ElementBuilder.GeboorteParameters();
        gebPara.datum(20010101);
        paramsPseudo.geboorte(builder.maakGeboorteElement("geb_pseudo", gebPara));
        pseudoPersoonElementGroepenEnSleutel = builder.maakPersoonGegevensElement(attPseudo, paramsPseudo);
        pseudoPersoonElementGroepenEnSleutel.setVerzoekBericht(bericht);

        hoofdActie = new TestActieElement(objecttypeMap, null, null, null, persoonEntiteit, persoonElement, SoortActie.REGISTRATIE_HUWELIJK);
        hoofdActie.setVerzoekBericht(bericht);

        final AdministratieveHandelingElement
                administratieveHandelingElement =
                maakAdministratieveHandelingElement(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND, hoofdActie);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
        when(bericht.getStuurgegevens()).thenReturn(
                new StuurgegevensElement(
                        objecttypeMap,
                        STRING_ELEMENT_PARTIJ_CODE,
                        new StringElement("zendendeSysteem"),
                        null,
                        new StringElement("referentienummer"),
                        null,
                        new DatumTijdElement(DatumUtil.nuAlsZonedDateTime())));
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL)).thenReturn(persoonEntiteit);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PSEUDO_PERSOON_SLEUTEL)).thenReturn(pseudoPersoonEntiteit);
        when(getObjectSleutelService().maakPersoonObjectSleutel(anyString()))
                .thenReturn(new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(PERSOON_ID, 1));
        when(getPersoonRepository().findById(PERSOON_ID.longValue())).thenReturn(null);
        when(getDynamischeStamtabelRepository().getPartijByCode("053001")).thenReturn(partij);
        definieerWhenStappenVoorSoortDocument();

    }

    private AdministratieveHandelingElement maakAdministratieveHandelingElement(final AdministratieveHandelingElementSoort administratieveHandelingElementSoort,
                                                                                final ActieElement actie) {
        final AdministratieveHandelingElement administratieveHandelingElement =
                new AdministratieveHandelingElement(
                        objecttypeMap,
                        administratieveHandelingElementSoort,
                        STRING_ELEMENT_PARTIJ_CODE,
                        null,
                        null,
                        null,
                        bronnen,
                        Collections.singletonList(actie));
        administratieveHandelingElement.setVerzoekBericht(bericht);
        return administratieveHandelingElement;
    }

    @Test
    public void testBronReferentieNietAanwezig() {
        final TestActieElement actieElement =
                new TestActieElement(objecttypeMap, new DatumElement(20160101), null, null, persoonEntiteit, persoonElement, SoortActie.REGISTRATIE_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);
        controleerRegels(actieElement.valideerInhoud());
    }

    @Test
    public void testBronReferentieAanwezig() {
        final DocumentElement documentElement =
                new DocumentElement(objecttypeMap, new StringElement(SOORT_DOC_NAAM_HUWELIJK), null, null, STRING_ELEMENT_PARTIJ_CODE);
        final BronElement bronElement = new BronElement(objecttypeMap, documentElement, null);
        final Map<String, String> attributen = new LinkedHashMap<>(objecttypeMap);
        final String referentieId = "bla";
        attributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), referentieId);
        final Map<String, BmrGroep> communicatieIdGroep = new LinkedHashMap<>();
        communicatieIdGroep.put(referentieId, bronElement);

        final BronReferentieElement bronReferentieElement = new BronReferentieElement(attributen);
        bronReferentieElement.initialiseer(communicatieIdGroep);

        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.singletonList(bronReferentieElement),
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.REGISTRATIE_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);

        bronnen.add(bronElement);
        controleerRegels(actieElement.valideerInhoud());
    }

    @Bedrijfsregel(Regel.R1606)
    @Test
    public void testBronReferentieAanwezigVerkeerdeCombi() {
        final DocumentElement documentElement =
                new DocumentElement(objecttypeMap, new StringElement("Geboorteakte"), null, null, STRING_ELEMENT_PARTIJ_CODE);
        final BronElement bronElement = new BronElement(objecttypeMap, documentElement, null);
        final Map<String, String> attributen = new LinkedHashMap<>(objecttypeMap);
        final String referentieId = "bla";
        attributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), referentieId);
        final Map<String, BmrGroep> communicatieIdGroep = new LinkedHashMap<>();
        communicatieIdGroep.put(referentieId, bronElement);

        final BronReferentieElement bronReferentieElement = new BronReferentieElement(attributen);
        bronReferentieElement.initialiseer(communicatieIdGroep);

        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.singletonList(bronReferentieElement),
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.REGISTRATIE_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);

        bronnen.add(bronElement);
        controleerRegels(actieElement.valideerInhoud(), Regel.R1606);
    }

    @Bedrijfsregel(Regel.R1606)
    @Bedrijfsregel(Regel.R1668)
    @Test
    public void testBronReferentieAanwezigVerkeerdeCombiMaarAHisCorrectie() {
        final DocumentElement documentElement =
                new DocumentElement(objecttypeMap, new StringElement("Geboorteakte"), null, null, STRING_ELEMENT_PARTIJ_CODE);
        final BronElement bronElement = new BronElement(objecttypeMap, documentElement, null);
        final Map<String, String> attributen = new LinkedHashMap<>(objecttypeMap);
        final String referentieId = "bla";
        attributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), referentieId);
        final Map<String, BmrGroep> communicatieIdGroep = new LinkedHashMap<>();
        communicatieIdGroep.put(referentieId, bronElement);

        final AdministratieveHandelingElement correctieHandelingElement =
                new AdministratieveHandelingElement(
                        objecttypeMap,
                        AdministratieveHandelingElementSoort.CORRECTIE_HUWELIJK,
                        STRING_ELEMENT_PARTIJ_CODE,
                        null,
                        null,
                        null,
                        bronnen,
                        Collections.singletonList(hoofdActie));
        when(bericht.getAdministratieveHandeling()).thenReturn(correctieHandelingElement);

        final BronReferentieElement bronReferentieElement = new BronReferentieElement(attributen);
        bronReferentieElement.initialiseer(communicatieIdGroep);
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.singletonList(bronReferentieElement),
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.REGISTRATIE_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);

        bronnen.add(bronElement);
        controleerRegels(actieElement.valideerInhoud());
    }


    @Bedrijfsregel(Regel.R1606)
    @Test
    public void testBronReferentieAanwezigVerkeerdeCombiMaarActieRegistratieAdres() {
        final DocumentElement documentElement =
                new DocumentElement(objecttypeMap, new StringElement("Geboorteakte"), null, null, STRING_ELEMENT_PARTIJ_CODE);
        final BronElement bronElement = new BronElement(objecttypeMap, documentElement, null);
        final Map<String, String> attributen = new LinkedHashMap<>(objecttypeMap);
        final String referentieId = "bla";
        attributen.put(REFERENTIE_ID_ATTRIBUUT.toString(), referentieId);
        final Map<String, BmrGroep> communicatieIdGroep = new LinkedHashMap<>();
        communicatieIdGroep.put(referentieId, bronElement);

        final BronReferentieElement bronReferentieElement = new BronReferentieElement(attributen);
        bronReferentieElement.initialiseer(communicatieIdGroep);
        final ElementBuilder.PersoonParameters perspara = new ElementBuilder.PersoonParameters();
        persoonElement = builder.maakPersoonGegevensElement("pers", null, null, perspara);

        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.singletonList(bronReferentieElement),
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.REGISTRATIE_ADRES);
        actieElement.setVerzoekBericht(bericht);

        bronnen.add(bronElement);
        controleerRegels(actieElement.valideerInhoud());
    }

    @Test
    public void testObjectSleutelEnGroepVoorPseudoPersoonHuwelijk() {
        // soort actie
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.emptyList(),
                        pseudoPersoonEntiteit,
                        pseudoPersoonElementGroepenEnSleutel,
                        SoortActie.REGISTRATIE_AANVANG_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);

        controleerRegels(actieElement.valideerInhoud(), Regel.R2117, Regel.R2181);
    }

    @Test
    public void testObjectSleutelEnGroepVoorPseudoPersoonGeborene() {
        // soort actie
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.emptyList(),
                        pseudoPersoonEntiteit,
                        pseudoPersoonElementGroepenEnSleutel,
                        SoortActie.REGISTRATIE_GEBORENE);
        actieElement.setVerzoekBericht(bericht);

        controleerRegels(actieElement.valideerInhoud(), Regel.R2117, Regel.R2181);
    }

    @Bedrijfsregel(Regel.R2117)
    @Test
    public void testObjectSleutelVoorPseudoPersoon() {
        // soort actie
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.emptyList(),
                        pseudoPersoonEntiteit,
                        pseudoPersoonElement,
                        SoortActie.REGISTRATIE_AANVANG_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);

        controleerRegels(actieElement.valideerInhoud(), Regel.R2117);
    }

    @Bedrijfsregel(Regel.R2117)
    @Test
    public void testObjectSleutelVoorPersoon() {
        final String objectSleutel = "123a";
        persoonElement = builder.maakPersoonGegevensElement("pers", objectSleutel, null, new ElementBuilder.PersoonParameters());
        persoonElement.setVerzoekBericht(bericht);
        // soort actie
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20160101),
                        null,
                        Collections.emptyList(),
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.REGISTRATIE_AANVANG_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(persoonEntiteit);
        controleerRegels(actieElement.valideerInhoud());
    }

    @Bedrijfsregel(Regel.R1579)
    @Test
    public void testActieToegestaanBijOverledenPersoon() throws OngeldigeObjectSleutelException {
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoonEntiteit, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);

        // Persoon bestaat niet in database
        controleerRegels(hoofdActie.valideerInhoud());

        when(getPersoonRepository().findById(PERSOON_ID.longValue())).thenReturn(persoonEntiteit);
        // Persoon bestaat wel in database, maar heeft geen bijhouding historie (zou niet mogen voorkomen in productie,
        // maar verplichtheid is niet afgedwongen in de entiteit)
        controleerRegels(hoofdActie.valideerInhoud());

        // Persoon bestaat wel in database en is actueel (niet opgeschort)
        persoonEntiteit.addPersoonBijhoudingHistorie(bijhoudingHistorie);
        controleerRegels(hoofdActie.valideerInhoud());

        // Persoon bestaat wel in database en is overleden op datum actie
        bijhoudingHistorie.setNadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN);
        bijhoudingHistorie.setDatumAanvangGeldigheid(hoofdActie.getPeilDatum().getWaarde());
        controleerRegels(hoofdActie.valideerInhoud(), Regel.R1579);

        // Persoon bestaat wel in database en is overleden voor datum actie
        bijhoudingHistorie.setNadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN);
        bijhoudingHistorie.setDatumAanvangGeldigheid(20151231);
        controleerRegels(hoofdActie.valideerInhoud(), Regel.R1579);

        // Persoon bestaat wel in database en is overleden na datum actie
        bijhoudingHistorie.setNadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN);
        bijhoudingHistorie.setDatumAanvangGeldigheid(hoofdActie.getPeilDatum().getWaarde() + 1);
        controleerRegels(hoofdActie.valideerInhoud());

        TestActieElement
                andereActie =
                new TestActieElement(objecttypeMap, null, null, null, persoonEntiteit, persoonElement, SoortActie.REGISTRATIE_AANVANG_ONDERZOEK);
        andereActie.setVerzoekBericht(bericht);
        final AdministratieveHandelingElement ahElement =
                maakAdministratieveHandelingElement(AdministratieveHandelingElementSoort.AANVANG_ONDERZOEK, andereActie);
        when(bericht.getAdministratieveHandeling()).thenReturn(ahElement);

        controleerRegels(andereActie.valideerInhoud());
    }

    @Test
    public void testOngeldigeRechtsgrond() {
        final String rechtsgrondCode = "123";
        final Rechtsgrond rechtsgrond = new Rechtsgrond(rechtsgrondCode, "omschrijving");
        rechtsgrond.setDatumAanvangGeldigheid(20000101);
        rechtsgrond.setDatumEindeGeldigheid(20000101);
        final DocumentElement documentElement = builder.maakDocumentElement("CI_document_1", "soort", null, null, "123456");
        final BronElement bronElement = builder.maakBronElement("CI_bron_1", documentElement, rechtsgrondCode);
        final Map<String, BmrGroep> communicatieIdGroep = new LinkedHashMap<>();
        communicatieIdGroep.put(bronElement.getCommunicatieId(), bronElement);
        final BronReferentieElement bronReferentieElement = builder.maakBronReferentieElement("CI_bronreferentie_1", bronElement.getCommunicatieId());
        bronReferentieElement.initialiseer(communicatieIdGroep);
        when(getDynamischeStamtabelRepository().getRechtsgrondByCode(rechtsgrondCode)).thenReturn(rechtsgrond);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(documentElement.getSoortNaam().getWaarde()))
                .thenReturn(new SoortDocument("naam", "omschrijving"));
        when(getDynamischeStamtabelRepository().getSoortActieBrongebruikBySoortActieBrongebruikSleutel(any())).thenReturn(mock(SoortActieBrongebruik.class));
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(20170101));
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20170101),
                        null,
                        Collections.singletonList(bronReferentieElement),
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.VERVAL_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = actieElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R2431);
    }

    @Test
    @Bedrijfsregel(Regel.R2668)
    public void testGeenBronneBijCorrectie() {
        controleerRegels(controlleerBronnenBijCorrectie(SoortActie.VERVAL_HUWELIJK, false, false, false), Regel.R2668, Regel.R2448);
        controleerRegels(controlleerBronnenBijCorrectie(SoortActie.CORRECTIEVERVAL_ADRES, false, false, false), Regel.R2668);
    }

    @Test
    public void testBronnenBijCorrectieDocument() {
        controleerRegels(controlleerBronnenBijCorrectie(SoortActie.CORRECTIEVERVAL_ADRES, true, true, false));
    }
    @Test
    public void testBronnenBijCorrectieRechtsgrond() {
        controleerRegels(controlleerBronnenBijCorrectie(SoortActie.CORRECTIEVERVAL_ADRES, true, false, true));
    }

    public List<MeldingElement> controlleerBronnenBijCorrectie(final SoortActie soortActie, final boolean voegBronToe, final boolean voegDocumentAanBronToe,
                                                               final boolean voegRechtsgrondToe) {
        final AdministratieveHandelingElement correctieHandelingElement =
                new AdministratieveHandelingElement(
                        objecttypeMap,
                        AdministratieveHandelingElementSoort.CORRECTIE_HUWELIJK,
                        STRING_ELEMENT_PARTIJ_CODE,
                        null,
                        null,
                        null,
                        bronnen,
                        Collections.singletonList(hoofdActie));
        final List<BronReferentieElement> bronreferenties = new LinkedList<>();
        if (voegBronToe) {
            final BronReferentieElement bronReferentieElement = builder.maakBronReferentieElement("bron", "orgbron");
            final Map<String, BmrGroep> commuicatierGroepMap = new HashMap<>();
            DocumentElement document = null;
            if (voegDocumentAanBronToe) {
                document = builder.maakDocumentElement("doc", "soort", "12345", "omschrijving", "053001");
            }
            String rechtsgrond = null;
            final BronElement bronElement;
            if (voegRechtsgrondToe) {
                bronElement = builder.maakBronElement("orgbron", document, "iets");
            } else {
                bronElement = builder.maakBronElement("orgbron", document);
            }

            commuicatierGroepMap.put("orgbron", bronElement);
            bronReferentieElement.initialiseer(commuicatierGroepMap);
            bronreferenties.add(bronReferentieElement);
        }
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20170101),
                        null,
                        bronreferenties,
                        persoonEntiteit,
                        persoonElement,
                        soortActie);

        actieElement.setVerzoekBericht(bericht);
        when(bericht.getAdministratieveHandeling()).thenReturn(correctieHandelingElement);
        return actieElement.valideerInhoud();
    }

    @Test
    public void testGeenBronneBijActualisatie() {
        final TestActieElement actieElement =
                new TestActieElement(
                        objecttypeMap,
                        new DatumElement(20170101),
                        null,
                        null,
                        persoonEntiteit,
                        persoonElement,
                        SoortActie.REGISTRATIE_HUWELIJK);
        actieElement.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = actieElement.valideerInhoud();
        controleerRegels(meldingen);
    }

    private static final class TestActieElement extends AbstractActieElement {

        private boolean heeftInvloedOpGerelateerde = false;

        private final BijhoudingPersoon persoonEntiteit;
        private final PersoonElement persoonElement;
        private final SoortActie soortActie;

        /**
         * Maakt een AbstractActieElement object.
         * @param basisAttribuutGroep de basis attribuutgroep
         * @param datumAanvangGeldigheid datum aanvang geldigheid
         * @param datumEindeGeldigheid datum einde geldigheid
         * @param bronReferenties bron referenties
         * @param persoonEntiteit persoon entiteit
         * @param persoonElement persoon element
         * @param soortActie soort actie
         */
        TestActieElement(
                final Map<String, String> basisAttribuutGroep,
                final DatumElement datumAanvangGeldigheid,
                final DatumElement datumEindeGeldigheid,
                final List<BronReferentieElement> bronReferenties,
                final BijhoudingPersoon persoonEntiteit,
                final PersoonElement persoonElement,
                final SoortActie soortActie) {
            super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
            this.persoonEntiteit = persoonEntiteit;
            this.persoonElement = persoonElement;
            this.soortActie = soortActie;
        }

        @Override
        public SoortActie getSoortActie() {
            return soortActie;
        }

        @Override
        public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
            return null;
        }

        @Override
        protected List<MeldingElement> valideerSpecifiekeInhoud() {
            return VALIDATIE_OK;
        }

        @Override
        public List<BijhoudingPersoon> getHoofdPersonen() {
            return Collections.singletonList(persoonEntiteit);
        }

        @Override
        public List<PersoonElement> getPersoonElementen() {
            return Collections.singletonList(persoonElement);
        }

        @Override
        public DatumElement getPeilDatum() {
            return new DatumElement(DatumUtil.vandaag());
        }

        /**
         * @return true als de actie invloed heeft op de gerelateerden van de hoofdpersoon
         */
        @Override
        public boolean heeftInvloedOpGerelateerden() {
            return heeftInvloedOpGerelateerde;
        }
    }
}
