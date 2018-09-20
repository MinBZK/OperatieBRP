/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit tests voor de {@link AntwoordBerichtFactoryImpl} klasse.
 */
public class AntwoordBerichtFactoryImplTest {

    // TODO, deze soorten handelingen moeten vanuit BMR en Generatie al geexclude worden.
    private static final List<SoortAdministratieveHandeling> SOORTEN_NIET_BIJHOUDING_GERELATERDE_ADMHND_SOORTEN =
        Arrays.asList(SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                      SoortAdministratieveHandeling.G_B_A_A_NUMMER_WIJZIGING,
                      SoortAdministratieveHandeling.G_B_A_INFRASTRUCTURELE_WIJZIGING,
                      SoortAdministratieveHandeling.G_B_A_AFVOEREN_P_L);
    private static final String TEST_S = "Test: %s";
    private static final String KONINKLIJK_BESLUIT = "Koninklijk besluit";
    private static final String PARTIJ = "partij";
    private static final String GEBOORTEAKTE = "Geboorteakte";
    private static final String DOCUMENT_1 = "document1";
    private static final String DOCUMENT_2 = "document2";
    private static final String DOCUMENT_3 = "document3";
    private static final String ID = "iD";

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private final AntwoordBerichtFactoryImpl antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(antwoordBerichtFactory, "referentieDataRepository", referentieDataRepository);
        Mockito.when(referentieDataRepository.vindPartijOpCode(PartijCodeAttribuut.BRP_VOORZIENING))
            .thenReturn(TestPartijBuilder.maker().metCode(PartijCodeAttribuut.BRP_VOORZIENING).maak());
    }

    @Test
    public void zouHoogsteMeldingNiveauGMoetenGevenAlsErGeenMeldingenZijn() {
        final BerichtBericht ingaandBericht = new RegistreerGeboorteBericht();
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        final BijhoudingResultaat resultaat = new BijhoudingResultaat(new ArrayList<Melding>());

        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.BIJHOUDING));
        ingaandBericht.setParameters(parameters);
        ingaandBericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());

        final Bericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        Assert.assertEquals(SoortMelding.GEEN, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
    }

    @Test
    public void zouHoogsteMeldingNiveauFMoetenGevenAlsErEenFoutmeldingIs() {
        final BerichtBericht ingaandBericht = new RegistreerGeboorteBericht();
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.ACT0001, null, null));
        final BijhoudingResultaat resultaat = new BijhoudingResultaat(meldingen);

        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.BIJHOUDING));
        ingaandBericht.setParameters(parameters);
        ingaandBericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());

        final Bericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        Assert.assertEquals(SoortMelding.FOUT, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
    }

    @Test
    public void testCloneAdministratieveHandelingBericht() {
        final BerichtBericht ingaandBericht = new RegistreerGeboorteBericht();
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        final List<Melding> meldingen = new ArrayList<>();
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.BIJHOUDING));
        ingaandBericht.setParameters(parameters);

        AdministratieveHandelingBericht handeling;
        for (final SoortAdministratieveHandeling soort : SoortAdministratieveHandeling.values()) {
            if (soort == SoortAdministratieveHandeling.DUMMY || soort.name().endsWith("_VERVALLEN")) {
                continue;
            }
            if (SOORTEN_NIET_BIJHOUDING_GERELATERDE_ADMHND_SOORTEN.contains(soort)) {
                continue;
            }

            handeling = new AdministratieveHandelingBericht(new SoortAdministratieveHandelingAttribuut(soort)) {
            };

            handeling.setPartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA);
            handeling.setPartijCode(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde().getCode().toString());
            handeling.setToelichtingOntlening(
                new OntleningstoelichtingAttribuut(String.format(TEST_S, soort.name())));

            ingaandBericht.getStandaard().setAdministratieveHandeling(handeling);

            final AntwoordBericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht,
                new BijhoudingResultaat(meldingen));

            Assert.assertNotNull(antwoordBericht);
            Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA,
                antwoordBericht.getAdministratieveHandeling().getPartij());
            Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde().getCode().toString(),
                antwoordBericht.getAdministratieveHandeling().getPartijCode());
            Assert.assertEquals(String.format(TEST_S, soort.name()),
                antwoordBericht.getAdministratieveHandeling().getToelichtingOntlening().getWaarde());
        }
    }

    @Test
    public void testResultaatBevatWaarschuwingEnIsPrevalidatie() {
        final BerichtBericht ingaandBericht = new RegistreerAdoptieBericht();
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        final List<Melding> meldingen = new ArrayList<>();
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.PREVALIDATIE));
        ingaandBericht.setParameters(parameters);

        final BijhoudingResultaat resultaat = new BijhoudingResultaat(meldingen);

        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.ACT0001, null, null));
        resultaat.voegMeldingenToe(meldingen);
        ingaandBericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());

        final Bericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        Assert.assertEquals(SoortMelding.WAARSCHUWING,
            antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD,
            antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertEquals(Bijhoudingsresultaat.VERWERKT, antwoordBericht.getResultaat().getBijhouding().getWaarde());
    }

    @Test
    public void testBijgehoudenDocumentenInclusiefSortering() {
        final BerichtBericht ingaandBericht = new RegistreerAdoptieBericht();
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        final List<Melding> meldingen = new ArrayList<>();
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.PREVALIDATIE));
        ingaandBericht.setParameters(parameters);

        final AdministratieveHandelingBericht handeling = new AdministratieveHandelingBericht(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE))
        {
        };
        handeling.setPartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA);
        handeling.setPartijCode(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde().getCode().toString());
        handeling.setToelichtingOntlening(
            new OntleningstoelichtingAttribuut(
                String.format(TEST_S, SoortAdministratieveHandeling.ADOPTIE_INGEZETENE.name())));
        ingaandBericht.getStandaard().setAdministratieveHandeling(handeling);

        final Map<String, DocumentModel> documenten = new HashMap<>();
        final DocumentModel document1 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument(KONINKLIJK_BESLUIT));
        ReflectionTestUtils.setField(document1, ID, (long) 10);
        final DocumentStandaardGroepModel standaard1 = new DocumentStandaardGroepModel(
            new DocumentIdentificatieAttribuut("1234567"),
            null, null, StatischeObjecttypeBuilder.bouwPartij(1234567, PARTIJ));
        document1.setStandaard(standaard1);
        documenten.put(DOCUMENT_1, document1);

        final DocumentModel document2 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument(GEBOORTEAKTE));
        ReflectionTestUtils.setField(document2, ID, (long) 20);
        final DocumentStandaardGroepModel standaard2 = new DocumentStandaardGroepModel(
            new DocumentIdentificatieAttribuut("3456789"),
            null, null, StatischeObjecttypeBuilder.bouwPartij(1234567, PARTIJ));
        document2.setStandaard(standaard2);
        documenten.put(DOCUMENT_2, document2);

        final DocumentModel document3 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument(KONINKLIJK_BESLUIT));
        ReflectionTestUtils.setField(document3, ID, (long) 30);
        final DocumentStandaardGroepModel standaard3 = new DocumentStandaardGroepModel(
            new DocumentIdentificatieAttribuut("5678910"),
            null, null, StatischeObjecttypeBuilder.bouwPartij(1234567, PARTIJ));
        document3.setStandaard(standaard3);
        documenten.put(DOCUMENT_3, document3);

        final BijhoudingResultaat resultaat = new BijhoudingResultaat(meldingen);
        final AdministratieveHandelingModel admHandeling =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.ADOPTIE_INGEZETENE), null, null, null);
        resultaat.setAdministratieveHandeling(admHandeling);
        resultaat.voegDocumentenToe(documenten);

        final AntwoordBericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        Assert.assertEquals(3, antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().size());
        Assert.assertEquals(DOCUMENT_2,
            antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().get(0).getDocument()
                .getReferentieID());
        Assert.assertEquals(GEBOORTEAKTE,
            antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().get(0).getDocument()
                .getSoortNaam());
        Assert.assertEquals(DOCUMENT_1,
            antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().get(1).getDocument()
                .getReferentieID());
        Assert.assertEquals(KONINKLIJK_BESLUIT,
            antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().get(1).getDocument()
                .getSoortNaam());
        Assert.assertEquals(DOCUMENT_3,
            antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().get(2).getDocument()
                .getReferentieID());
        Assert.assertEquals(KONINKLIJK_BESLUIT,
            antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten().get(2).getDocument()
                .getSoortNaam());
    }

    @Test
    public void testZonderBijgehoudenDocumenten() {
        final BerichtBericht ingaandBericht = new RegistreerAdoptieBericht();
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        final List<Melding> meldingen = new ArrayList<>();
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.PREVALIDATIE));
        ingaandBericht.setParameters(parameters);

        final AdministratieveHandelingBericht handeling = new AdministratieveHandelingBericht(
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE))
        {
        };
        handeling.setPartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA);
        handeling.setPartijCode(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde().getCode().toString());
        handeling.setToelichtingOntlening(
            new OntleningstoelichtingAttribuut(
                String.format(TEST_S, SoortAdministratieveHandeling.ADOPTIE_INGEZETENE.name())));
        ingaandBericht.getStandaard().setAdministratieveHandeling(handeling);

        final BijhoudingResultaat resultaat = new BijhoudingResultaat(meldingen);

        final AdministratieveHandelingModel admHandeling =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.ADOPTIE_INGEZETENE), null, null, null);
        resultaat.setAdministratieveHandeling(admHandeling);

        final AntwoordBericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        Assert.assertNull(antwoordBericht.getAdministratieveHandeling().getBijgehoudenDocumenten());
    }
}
