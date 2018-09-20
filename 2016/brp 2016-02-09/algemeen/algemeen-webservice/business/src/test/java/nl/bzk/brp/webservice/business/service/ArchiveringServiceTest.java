/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.dataaccess.repository.archivering.BerichtPersoonRepository;
import nl.bzk.brp.dataaccess.repository.archivering.BerichtRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtPersoonModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link ArchiveringServiceImpl} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArchiveringServiceTest {

    private static final String             TEST                      = "Test";
    private static final String             DIT_IS_EEN_TEST           = "Dit is een Test";
    private static final String             WILLEKEURIGE_TEXT         = "Willekeurige text";
    private static final String             FOO                       = "foo";
    private static final String             ID                        = "iD";
    private static final String             REF                       = "123ref";
    private static final String             TEST_LEVERINGSAUTORISATIE = "test Leveringsautorisatie";
    private static final String             WAAR                      = "WAAR";
    private static final String             NOG_MEER_TEXT             = "Nog meer text";
    private static final String             BZM_TJE                   = "BZM-tje";
    private static final String             REF1                      = "ref";
    private static final String             CROSSREF                  = "crossref";
    @InjectMocks
    private final        ArchiveringService archiveringService        = new ArchiveringServiceImpl();

    @Mock
    private BerichtRepository berichtRepository;

    @Mock
    private BerichtPersoonRepository berichtPersoonRepository;

    @Mock
    private ToegangLeveringsautorisatieRepository tlaRepository;

    @Mock
    private AdministratieveHandelingModel administratieveHandeling;

    private final BerichtStuurgegevensGroepBericht stuurgegevens = maakStuurgegevens();
    private final SynchronisatieBerichtGegevens    gegevens      = new SynchronisatieBerichtGegevens();

    @Before
    public void init() {
        gegevens.setStuurgegevens(new BerichtStuurgegevensGroepModel(stuurgegevens));
        gegevens.getStuurgegevens().setZendendePartijId((short) 1);
        gegevens.getStuurgegevens().setOntvangendePartijId((short) 2);
        gegevens.setGeleverdePersoonsIds(Collections.singletonList(1));
        gegevens.setAdministratieveHandelingId(administratieveHandeling.getID());

        Mockito.when(berichtRepository.save(Mockito.any(BerichtModel.class))).thenAnswer(new Answer<BerichtModel>() {
            @Override
            public BerichtModel answer(final InvocationOnMock invocation) throws Throwable {
                return (BerichtModel) invocation.getArguments()[0];
            }
        });
    }

    /**
     * Test voor het archiveren van een bericht.
     */
    @Test
    public void testArchiveer() {
        // argument voor de 2e save ==> uitgaandBericht
        final ArgumentCaptor<BerichtModel> argument = ArgumentCaptor.forClass(BerichtModel.class);
        archiveringService.archiveer(TEST);

        verify(berichtRepository, Mockito.times(2)).save(argument.capture());
        final BerichtModel uitgaandBericht = argument.getValue();

        Assert.assertNull(uitgaandBericht.getSoort());
        Assert.assertNull(uitgaandBericht.getStandaard().getAdministratieveHandelingId());
        Assert.assertNotNull(uitgaandBericht.getStandaard().getAntwoordOp());
        Assert.assertEquals("<Wordt nader bepaald>", uitgaandBericht.getStandaard().getData().getWaarde());
        Assert.assertNull(uitgaandBericht.getStuurgegevens());
        Assert.assertEquals(Richting.UITGAAND, uitgaandBericht.getRichting().getWaarde());

        final BerichtModel inkomendBericht = uitgaandBericht.getStandaard().getAntwoordOp();
        Assert.assertNull(inkomendBericht.getSoort());
        Assert.assertNull(inkomendBericht.getStandaard().getAdministratieveHandelingId());
        Assert.assertNull(inkomendBericht.getStandaard().getAntwoordOp());
        Assert.assertEquals(TEST, inkomendBericht.getStandaard().getData().getWaarde());
        Assert.assertEquals(Richting.INGAAND, inkomendBericht.getRichting().getWaarde());
        Assert.assertNotNull(inkomendBericht.getStuurgegevens());

        final BerichtStuurgegevensGroepModel stuurgegevensInkomend = inkomendBericht.getStuurgegevens();
        Assert.assertNotNull(stuurgegevensInkomend.getDatumTijdOntvangst());
        Assert.assertNull(stuurgegevensInkomend.getZendendePartijId());
        Assert.assertNull(stuurgegevensInkomend.getZendendeSysteem());
        Assert.assertNull(stuurgegevensInkomend.getOntvangendePartijId());
        Assert.assertNull(stuurgegevensInkomend.getOntvangendeSysteem());
        Assert.assertNull(stuurgegevensInkomend.getReferentienummer());
        Assert.assertNull(stuurgegevensInkomend.getCrossReferentienummer());
        Assert.assertNull(stuurgegevensInkomend.getDatumTijdVerzending());
    }

    /**
     * Test voor het bijwerken van een uitgaand bericht waarbij het uitgaande bericht in de database niet bestaat.
     */
    @Test(expected = RuntimeException.class)
    public void testArchiveerUitgaandWaarUitgaandBerichtMist() {
        Mockito.when(berichtRepository.findOne(12L)).thenReturn(null);

        archiveringService.werkDataBij(12L, DIT_IS_EEN_TEST);
    }

    @Test
    public void testArchiveerUitgaandWaarUitgaandBerichtBestaat() {
        final BerichtModel bericht = maakInkomendBericht(null, "tekst");
        Mockito.when(berichtRepository.findOne(12L)).thenReturn(bericht);

        archiveringService.werkDataBij(12L, DIT_IS_EEN_TEST);

        verify(berichtRepository).save(bericht);
    }

    @Test
    public void testWerkIngaandBerichtInfoBij() {
        final BerichtModel ingaandBericht = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(null, null, null, null, null, null, null,
            new DatumTijdAttribuut(null)));
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaandBericht);

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaandBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaandBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensIngaandBericht.setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(123).maak()));
        ReflectionTestUtils.setField(stuurgegevensIngaandBericht.getZendendePartij().getWaarde(), ID, (short) 1234);
        stuurgegevensIngaandBericht.setReferentienummer(new ReferentienummerAttribuut(REF));
        final Date nu = Calendar.getInstance().getTime();
        stuurgegevensIngaandBericht.setDatumTijdVerzending(new DatumTijdAttribuut(nu));

        final BerichtParametersGroepBericht params = new BerichtParametersGroepBericht();
        params.setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.PREVALIDATIE));

        final ArgumentCaptor<List> teArchiverenPersoonIds = ArgumentCaptor.forClass(List.class);
        archiveringService.werkIngaandBerichtInfoBij(13L, 1L, stuurgegevensIngaandBericht, params, SoortBericht.DUMMY,
            new HashSet<>(Arrays.asList(1, 2, 3)));

        verify(berichtPersoonRepository, times(1)).save(teArchiverenPersoonIds.capture());

        Assert.assertNotNull(ingaandBericht.getStuurgegevens());
        final BerichtStuurgegevensGroepModel bijgewerkteStuurgegevens = ingaandBericht.getStuurgegevens();
        Assert.assertEquals(FOO, bijgewerkteStuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals((short) 1234,
            bijgewerkteStuurgegevens.getZendendePartijId().shortValue());
        Assert.assertEquals(REF, bijgewerkteStuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(bijgewerkteStuurgegevens.getCrossReferentienummer());
        Assert.assertNotNull(bijgewerkteStuurgegevens.getDatumTijdOntvangst());
        Assert.assertEquals(nu, bijgewerkteStuurgegevens.getDatumTijdVerzending().getWaarde());

        Assert.assertEquals(Verwerkingswijze.PREVALIDATIE,
            ingaandBericht.getParameters().getVerwerkingswijze().getWaarde());
        Assert.assertEquals(SoortBericht.DUMMY, ingaandBericht.getSoort().getWaarde());

        // Test bericht persoon archivering
        final List persoonBerichtModels = teArchiverenPersoonIds.getValue();
        Assert.assertEquals(1, ((BerichtPersoonModel) persoonBerichtModels.get(0)).getPersoonId().intValue());
        Assert.assertEquals(2, ((BerichtPersoonModel) persoonBerichtModels.get(1)).getPersoonId().intValue());
        Assert.assertEquals(3, ((BerichtPersoonModel) persoonBerichtModels.get(2)).getPersoonId().intValue());

        Assert.assertEquals(ingaandBericht, ((BerichtPersoonModel) persoonBerichtModels.get(2)).getBericht());

    }

    @Test
    public void testWerkIngaandBerichtInfoBijMetParameterNull() {
        final BerichtModel ingaandBericht = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(null, null, null, null, null, null, null,
            new DatumTijdAttribuut(null)));
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaandBericht);

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaandBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaandBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensIngaandBericht.setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(123).maak()));
        stuurgegevensIngaandBericht.setReferentienummer(new ReferentienummerAttribuut(REF));
        final Date nu = Calendar.getInstance().getTime();
        stuurgegevensIngaandBericht.setDatumTijdVerzending(new DatumTijdAttribuut(nu));

        //null parameter.
        archiveringService.werkIngaandBerichtInfoBij(13L, 1L, stuurgegevensIngaandBericht, null, SoortBericht.DUMMY, null);

        Assert.assertNull(ingaandBericht.getParameters());
        Assert.assertEquals(SoortBericht.DUMMY, ingaandBericht.getSoort().getWaarde());
    }

    @Test
    public void testWerkIngaandBerichtInfoBijMetZendendePartijNull() {
        final BerichtModel ingaandBericht = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(null, null, null, null, null, null, null,
            new DatumTijdAttribuut(null)));
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaandBericht);

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaandBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaandBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensIngaandBericht.setZendendePartij(null);
        stuurgegevensIngaandBericht.setReferentienummer(new ReferentienummerAttribuut(REF));
        final Date nu = Calendar.getInstance().getTime();
        stuurgegevensIngaandBericht.setDatumTijdVerzending(new DatumTijdAttribuut(nu));

        //null parameter.
        archiveringService.werkIngaandBerichtInfoBij(13L, 1L, stuurgegevensIngaandBericht, null, SoortBericht.DUMMY, null);

        Assert.assertNull(ingaandBericht.getStuurgegevens().getZendendePartijId());
        Assert.assertEquals(SoortBericht.DUMMY, ingaandBericht.getSoort().getWaarde());
    }

    @Test
    public void testWerkIngaandBerichtInfoBijMetInputParameterVerwerkingswijzeNull() {
        final BerichtModel ingaandBericht = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(null, null, null, null, null, null, null, new DatumTijdAttribuut(null)));
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaandBericht);

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaandBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaandBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensIngaandBericht.setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(123).metId((short) 1).maak()));
        stuurgegevensIngaandBericht.setReferentienummer(new ReferentienummerAttribuut(REF));
        final Date nu = Calendar.getInstance().getTime();
        stuurgegevensIngaandBericht.setDatumTijdVerzending(new DatumTijdAttribuut(nu));

        //null parameter.
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(null);
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker()
            .metNaam(TEST_LEVERINGSAUTORISATIE)
            .metPopulatiebeperking(WAAR)
            .metId(1).maak();
        parameters.setLeveringsautorisatie(new LeveringsautorisatieAttribuut(leveringsautorisatie));
        archiveringService.werkIngaandBerichtInfoBij(13L, 1L, stuurgegevensIngaandBericht, parameters,
            SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R, null);

        Assert.assertNotNull(ingaandBericht.getParameters());
        Assert.assertEquals(SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R, ingaandBericht.getSoort().getWaarde());
    }

    @Test
    public void testWerkIngaandBerichtInfoBijMetLeveringsautorisatieNull() {
        final BerichtModel ingaandBericht = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(null, null, null, null, null, null, null,
            new DatumTijdAttribuut(null)));
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaandBericht);

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaandBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaandBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensIngaandBericht.setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(123).metId(1).maak()));
        stuurgegevensIngaandBericht.setReferentienummer(new ReferentienummerAttribuut(REF));
        final Date nu = Calendar.getInstance().getTime();
        stuurgegevensIngaandBericht.setDatumTijdVerzending(new DatumTijdAttribuut(nu));

        //null parameter.
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(null);
        archiveringService.werkIngaandBerichtInfoBij(13L, 1L, stuurgegevensIngaandBericht, parameters,
            SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R, null);

        Assert.assertNotNull(ingaandBericht.getParameters());
        Assert.assertEquals(SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R, ingaandBericht.getSoort().getWaarde());
    }

    @Test
    public void testWerkIngaandBerichtInfoBijMetLegeLijstTeArchiverenPersonen() {
        final BerichtModel ingaandBericht = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        ingaandBericht.setStuurgegevens(new BerichtStuurgegevensGroepModel(null, null, null, null, null, null, null,
            new DatumTijdAttribuut(null)));
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaandBericht);

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaandBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaandBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensIngaandBericht.setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(123).metId(1).maak()));
        stuurgegevensIngaandBericht.setReferentienummer(new ReferentienummerAttribuut(REF));
        final Date nu = Calendar.getInstance().getTime();
        stuurgegevensIngaandBericht.setDatumTijdVerzending(new DatumTijdAttribuut(nu));

        //null parameter.
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(null);
        archiveringService.werkIngaandBerichtInfoBij(13L, 1L, stuurgegevensIngaandBericht, parameters,
            SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R,
            new HashSet<Integer>());

        Assert.assertNotNull(ingaandBericht.getParameters());
        Assert.assertEquals(SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R, ingaandBericht.getSoort().getWaarde());
    }

    @Test
    public void testWerkUitgaandBerichtInfoBij() {
        final BerichtModel ingaand = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        final BerichtModel uitgaand = maakUitgaandBericht(NOG_MEER_TEXT, ingaand);
        Mockito.when(berichtRepository.findOne(12L)).thenReturn(uitgaand);
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaand);

        final BerichtStuurgegevensGroepBericht stuurgegevensAntwoordBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensAntwoordBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensAntwoordBericht.setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(789).metId(1).maak()));
        stuurgegevensAntwoordBericht.setOntvangendePartij(new PartijAttribuut(TestPartijBuilder.maker().metCode(456).metId(2).maak()));
        stuurgegevensAntwoordBericht.setOntvangendeSysteem(new SysteemNaamAttribuut(BZM_TJE));
        stuurgegevensAntwoordBericht.setReferentienummer(new ReferentienummerAttribuut(REF1));
        stuurgegevensAntwoordBericht.setCrossReferentienummer(new ReferentienummerAttribuut(CROSSREF));
        //Controle dat de ontvangst datum tijd echt keihard op null wordt gezet:
        stuurgegevensAntwoordBericht.setDatumTijdOntvangst(new DatumTijdAttribuut(null));
        //Controle dat de datum tijd zending echt bijgewerkt wordt.
        stuurgegevensAntwoordBericht.setDatumTijdVerzending(new DatumTijdAttribuut(Calendar.getInstance().getTime()));

        final BerichtResultaatGroepBericht resultaat = new BerichtResultaatGroepBericht();
        resultaat.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        resultaat.setBijhouding(new BijhoudingsresultaatAttribuut(Bijhoudingsresultaat.UITGESTELD));
        resultaat.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(SoortMelding.DEBLOKKEERBAAR));

        final ArgumentCaptor<List> teArchiverenPersoonIds = ArgumentCaptor.forClass(List.class);


        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker()
            .metNaam(TEST_LEVERINGSAUTORISATIE)
            .metPopulatiebeperking(WAAR)
//            .metIndicatieLeveren(false)
            .metId(1).maak();

        archiveringService.werkUitgaandBerichtInfoBij(12L, 1L, stuurgegevensAntwoordBericht, resultaat,
            new SoortBerichtAttribuut(SoortBericht.DUMMY), la, null,
            new HashSet<>(Arrays.asList(4, 5, 6)));

        verify(berichtPersoonRepository, times(1)).save(teArchiverenPersoonIds.capture());

        Assert.assertEquals(1L, uitgaand.getStandaard().getAdministratieveHandelingId().longValue());

        Assert.assertEquals(new SysteemNaamAttribuut(FOO), uitgaand.getStuurgegevens().getZendendeSysteem());
        Assert.assertEquals(new Short((short) 1),
            uitgaand.getStuurgegevens().getZendendePartijId());

        Assert.assertEquals(new SysteemNaamAttribuut(BZM_TJE), uitgaand.getStuurgegevens().getOntvangendeSysteem());
        Assert.assertEquals(new Short((short) 2),
            uitgaand.getStuurgegevens().getOntvangendePartijId());

        Assert.assertEquals(REF1, uitgaand.getStuurgegevens().getReferentienummer().getWaarde());
        Assert.assertEquals(CROSSREF, uitgaand.getStuurgegevens().getCrossReferentienummer().getWaarde());

        Assert.assertTrue(stuurgegevensAntwoordBericht.getDatumTijdVerzending()
            .equals(uitgaand.getStuurgegevens().getDatumTijdVerzending()));
        Assert.assertNull(uitgaand.getStuurgegevens().getDatumTijdOntvangst());

        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD, uitgaand.getResultaat().getVerwerking().getWaarde());
        Assert.assertEquals(Bijhoudingsresultaat.UITGESTELD, uitgaand.getResultaat().getBijhouding().getWaarde());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR,
            uitgaand.getResultaat().getHoogsteMeldingsniveau().getWaarde());

        // Test bericht persoon archivering
        final List berichtPersoonModels = teArchiverenPersoonIds.getValue();
        Assert.assertEquals(4, ((BerichtPersoonModel) berichtPersoonModels.get(0)).getPersoonId().intValue());
        Assert.assertEquals(5, ((BerichtPersoonModel) berichtPersoonModels.get(1)).getPersoonId().intValue());
        Assert.assertEquals(6, ((BerichtPersoonModel) berichtPersoonModels.get(2)).getPersoonId().intValue());

        Assert.assertEquals(uitgaand, ((BerichtPersoonModel) berichtPersoonModels.get(2)).getBericht());
    }

    @Test
    public void testWerkUitgaandBerichtInfoBijOntvangendeEnZendendePartijEnLeveringsautorisatieIdNull() {
        final BerichtModel ingaand = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        final BerichtModel uitgaand = maakUitgaandBericht(NOG_MEER_TEXT, ingaand);
        Mockito.when(berichtRepository.findOne(12L)).thenReturn(uitgaand);
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaand);

        final BerichtStuurgegevensGroepBericht stuurgegevensAntwoordBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensAntwoordBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensAntwoordBericht.setZendendePartij(null);
        stuurgegevensAntwoordBericht.setOntvangendePartij(null);
        stuurgegevensAntwoordBericht.setOntvangendeSysteem(new SysteemNaamAttribuut(BZM_TJE));
        stuurgegevensAntwoordBericht.setReferentienummer(new ReferentienummerAttribuut(REF1));
        stuurgegevensAntwoordBericht.setCrossReferentienummer(new ReferentienummerAttribuut(CROSSREF));
        //Controle dat de ontvangst datum tijd echt keihard op null wordt gezet:
        stuurgegevensAntwoordBericht.setDatumTijdOntvangst(new DatumTijdAttribuut(null));
        //Controle dat de datum tijd zending echt bijgewerkt wordt.
        stuurgegevensAntwoordBericht.setDatumTijdVerzending(new DatumTijdAttribuut(Calendar.getInstance().getTime()));

        final BerichtResultaatGroepBericht resultaat = new BerichtResultaatGroepBericht();
        resultaat.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        resultaat.setBijhouding(new BijhoudingsresultaatAttribuut(Bijhoudingsresultaat.UITGESTELD));
        resultaat.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(SoortMelding.DEBLOKKEERBAAR));

        final ArgumentCaptor<List> teArchiverenPersoonIds = ArgumentCaptor.forClass(List.class);

        archiveringService.werkUitgaandBerichtInfoBij(12L, 1L, stuurgegevensAntwoordBericht, resultaat,
            new SoortBerichtAttribuut(SoortBericht.DUMMY), null,
            new VerwerkingswijzeAttribuut(Verwerkingswijze.BIJHOUDING),
            new HashSet<>(Arrays.asList(4, 5, 6)));

        verify(berichtPersoonRepository, times(1)).save(teArchiverenPersoonIds.capture());

        Assert.assertEquals(1L, uitgaand.getStandaard().getAdministratieveHandelingId().longValue());
    }

    @Test
    public void testWerkUitgaandBerichtInfoBijOntvangendeEnZendendePartijEnLeveringsautorisatieIdEnVerwerkingswijzeNull() {
        final BerichtModel ingaand = maakInkomendBericht(null, WILLEKEURIGE_TEXT);
        final BerichtModel uitgaand = maakUitgaandBericht(NOG_MEER_TEXT, ingaand);
        Mockito.when(berichtRepository.findOne(12L)).thenReturn(uitgaand);
        Mockito.when(berichtRepository.findOne(13L)).thenReturn(ingaand);

        final BerichtStuurgegevensGroepBericht stuurgegevensAntwoordBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensAntwoordBericht.setZendendeSysteem(new SysteemNaamAttribuut(FOO));
        stuurgegevensAntwoordBericht.setZendendePartij(null);
        stuurgegevensAntwoordBericht.setOntvangendePartij(null);
        stuurgegevensAntwoordBericht.setOntvangendeSysteem(new SysteemNaamAttribuut(BZM_TJE));
        stuurgegevensAntwoordBericht.setReferentienummer(new ReferentienummerAttribuut(REF1));
        stuurgegevensAntwoordBericht.setCrossReferentienummer(new ReferentienummerAttribuut(CROSSREF));
        //Controle dat de ontvangst datum tijd echt keihard op null wordt gezet:
        stuurgegevensAntwoordBericht.setDatumTijdOntvangst(new DatumTijdAttribuut(null));
        //Controle dat de datum tijd zending echt bijgewerkt wordt.
        stuurgegevensAntwoordBericht.setDatumTijdVerzending(new DatumTijdAttribuut(Calendar.getInstance().getTime()));

        final BerichtResultaatGroepBericht resultaat = new BerichtResultaatGroepBericht();
        resultaat.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        resultaat.setBijhouding(new BijhoudingsresultaatAttribuut(Bijhoudingsresultaat.UITGESTELD));
        resultaat.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(SoortMelding.DEBLOKKEERBAAR));

        final ArgumentCaptor<List> teArchiverenPersoonIds = ArgumentCaptor.forClass(List.class);

        archiveringService.werkUitgaandBerichtInfoBij(12L, 1L, stuurgegevensAntwoordBericht, resultaat,
            new SoortBerichtAttribuut(SoortBericht.DUMMY), null,
            null, new HashSet<>(Arrays.asList(4, 5, 6)));

        verify(berichtPersoonRepository, times(1)).save(teArchiverenPersoonIds.capture());

        Assert.assertEquals(1L, uitgaand.getStandaard().getAdministratieveHandelingId().longValue());
    }

    @Test
    public void eenBerichtWordtGearchiveerd() {
        gegevens.setToegangLeveringsautorisatieIdId(123456);

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        Mockito.when(tlaRepository.haalToegangLeveringsautorisatieOp(Mockito.anyInt())).thenReturn(tla);

        // when
        archiveringService.archiveer(gegevens);

        // then
        verify(berichtRepository, times(1)).save(Mockito.any(BerichtModel.class));
        verify(berichtPersoonRepository, times(1)).save(Mockito.anyListOf(BerichtPersoonModel.class));
    }

    @Test
    public void eenBerichtWordtGearchiveerdIdem() {
        gegevens.setToegangLeveringsautorisatieIdId(123456);

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        Mockito.when(tlaRepository.haalToegangLeveringsautorisatieOp(Mockito.anyInt())).thenReturn(tla);

        // when
        archiveringService.archiveer(gegevens);

        // then
        verify(berichtRepository, times(1)).save(Mockito.any(BerichtModel.class));
        verify(berichtPersoonRepository, times(1)).save(Mockito.anyListOf(BerichtPersoonModel.class));
    }

    private BerichtModel maakInkomendBericht(final SoortBericht soortBericht, final String ingaandBerichtdata) {
        final BerichtModel inkomendBericht =
            new BerichtModel(new SoortBerichtAttribuut(soortBericht), new RichtingAttribuut(Richting.INGAAND));
        final BerichtStandaardGroepModel standaardGroep =
            new BerichtStandaardGroepModel(null, new BerichtdataAttribuut(ingaandBerichtdata), null);
        inkomendBericht.setStandaard(standaardGroep);
        return inkomendBericht;
    }

    private BerichtModel maakUitgaandBericht(final String uitgaandBerichtdata, final BerichtModel antwoordOp) {
        final BerichtModel uitgaandBericht =
            new BerichtModel(null, new RichtingAttribuut(Richting.UITGAAND));
        final BerichtStandaardGroepModel standaardGroep =
            new BerichtStandaardGroepModel(null, new BerichtdataAttribuut(uitgaandBerichtdata), antwoordOp);
        uitgaandBericht.setStandaard(standaardGroep);
        return uitgaandBericht;
    }

    private BerichtStuurgegevensGroepBericht maakStuurgegevens() {
        final Partij verzendendePartij = TestPartijBuilder.maker().metCode(4321).metId(1).maak();
        final Partij ontvangendePartij = TestPartijBuilder.maker().metCode(1234).metId(2).maak();

        final PartijAttribuut zendendePartij = new PartijAttribuut(verzendendePartij);
        final SysteemNaamAttribuut zendendeSysteem = new SysteemNaamAttribuut("BRP");
        final PartijAttribuut ontvangendePartijAttribuut = new PartijAttribuut(ontvangendePartij);
        final SysteemNaamAttribuut ontvangendeSysteem = new SysteemNaamAttribuut("applicatie");
        final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut("referentienr");
        final DatumTijdAttribuut datumTijdVerzending = new DatumTijdAttribuut(new Date());

        final BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroepBericht.setZendendePartij(zendendePartij);
        stuurgegevensGroepBericht.setZendendeSysteem(zendendeSysteem);
        stuurgegevensGroepBericht.setOntvangendePartij(ontvangendePartijAttribuut);
        stuurgegevensGroepBericht.setOntvangendeSysteem(ontvangendeSysteem);
        stuurgegevensGroepBericht.setReferentienummer(referentienummer);
        stuurgegevensGroepBericht.setDatumTijdVerzending(datumTijdVerzending);

        return stuurgegevensGroepBericht;
    }
}
