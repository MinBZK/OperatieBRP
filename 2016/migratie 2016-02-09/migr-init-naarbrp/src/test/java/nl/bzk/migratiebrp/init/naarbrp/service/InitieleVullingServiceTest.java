/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockQueue;
import com.mockrunner.mock.jms.MockTextMessage;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.GbavRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.impl.InitieleVullingServiceImpl;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test de service voor het versturen van LO3-berichten.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ExcelData.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class InitieleVullingServiceTest {

    private static final String BATCH_AANTAL = "batch.aantal";

    @Mock
    private GbavRepository gbavRepository;

    @Mock
    private AutorisatieRepository autorisatieRepository;

    @Mock
    private AfnemersIndicatieRepository afnemersIndicatieRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private Destination destination;

    @Mock
    private ExcelAdapter excelAdapter;

    @Mock
    private DestinationManager manager;

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private final InitieleVullingService service = new InitieleVullingServiceImpl();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(service);

        service.setBatchSize(100);
    }

    @Ignore
    @Test
    public void leesAlleLo3BerichtenEnVerstuur() {
        // final Properties config = new Properties();
        // config.put("datum.start", "01-01-1990");
        // config.put("datum.eind", "01-01-2013");

        try {
            service.synchroniseerPersonen();
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");

        final List messages = queue.getCurrentMessageList();
        assertTrue("Er zijn geen berichten!", messages.size() == 28);
        queue.clear();
    }

    @Ignore
    @Test
    public void leesAlleLo3BerichtenEnVerstuurInMeerdereBatches() {
        // final Properties config = new Properties();
        // config.put("datum.start", "01-01-1990");
        // config.put("datum.eind", "01-01-2013");
        // // Batch van 15, in 2 batches zijn alle berichten verwerkt.
        // config.put(BATCH_AANTAL, "15");

        service.setBatchSize(15);

        try {
            service.synchroniseerPersonen();
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");

        final List messages = queue.getCurrentMessageList();
        assertTrue("Er zijn geen berichten!", messages.size() == 28);
        queue.clear();
    }

    @Ignore
    @Test
    public void leesAlleLo3BerichtenEnControleerIds() throws JMSException {
        // final Properties config = new Properties();
        // config.put("datum.start", "01-01-1990");
        // config.put("datum.eind", "01-01-2013");
        // // Batch van 15, in 2 batches zijn alle berichten verwerkt.
        // config.put(BATCH_AANTAL, "15");
        service.setBatchSize(15);
        try {
            service.synchroniseerPersonen();
        } catch (final ParseException e) {
            Assert.fail("Lezen berichten mislukt.");
        }
        final MockQueue queue = manager.getQueue("/queue/init.vulling.naarlo3");

        final List<MockTextMessage> messages = queue.getCurrentMessageList();
        assertTrue("Er zijn geen berichten!", messages.size() == 28);
        final List<String> berichtIds = new ArrayList<>();
        for (final MockTextMessage message : messages) {
            berichtIds.add(message.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        }

        final List<String> verwachteIds = new ArrayList<>();

        verwachteIds.add(String.valueOf(3832803548L));
        verwachteIds.add(String.valueOf(5054783237L));
        verwachteIds.add(String.valueOf(7489487289L));
        verwachteIds.add(String.valueOf(4692587979L));
        verwachteIds.add(String.valueOf(5460975298L));
        verwachteIds.add(String.valueOf(4568204852L));
        verwachteIds.add(String.valueOf(8234912384L));
        verwachteIds.add(String.valueOf(8234912103L));
        verwachteIds.add(String.valueOf(7915932521L));
        verwachteIds.add(String.valueOf(5783071526L));
        verwachteIds.add(String.valueOf(8234912690L));
        verwachteIds.add(String.valueOf(7696126369L));
        verwachteIds.add(String.valueOf(6929678748L));
        verwachteIds.add(String.valueOf(6860742407L));
        verwachteIds.add(String.valueOf(7343505746L));
        verwachteIds.add(String.valueOf(6579659649L));
        verwachteIds.add(String.valueOf(7692704965L));
        verwachteIds.add(String.valueOf(2102970797L));
        verwachteIds.add(String.valueOf(2102894756L));
        verwachteIds.add(String.valueOf(2102832762L));
        verwachteIds.add(String.valueOf(2102823690L));
        verwachteIds.add(String.valueOf(2503828934L));
        verwachteIds.add(String.valueOf(9757279162L));
        verwachteIds.add(String.valueOf(1010141626L));
        verwachteIds.add(String.valueOf(7234370783L));
        verwachteIds.add(String.valueOf(3968904934L));
        verwachteIds.add(String.valueOf(7560314837L));
        verwachteIds.add(String.valueOf(6064295705L));

        for (final String id : verwachteIds) {
            assertTrue("Missend id: " + id, berichtIds.contains(id));
        }
        queue.clear();
    }

    @Test
    public void testVulBerichtenTabelExcel() throws BerichtSyntaxException, ExcelAdapterException, Lo3SyntaxException {
        final String lo3_pl =
                "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3_pl);

        final ExcelData data = Mockito.mock(ExcelData.class);
        final List<ExcelData> excelData = new ArrayList<>();
        excelData.add(data);

        Mockito.when(data.getCategorieLijst()).thenReturn(categorieen);
        Mockito.when(excelAdapter.leesExcelBestand(Matchers.any(InputStream.class))).thenReturn(excelData);

        final String excelLocatie = "src/test/resources/initvullingservice/excel";
        service.vulBerichtenTabelExcel(excelLocatie);

        verify(gbavRepository).createInitVullingTable();
        verify(excelAdapter).leesExcelBestand(any(InputStream.class));
        verify(gbavRepository).saveLg01(anyString(), anyLong(), anyInt(), (ConversieResultaat) any());

        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(gbavRepository);
        verifyNoMoreInteractions(excelAdapter);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testLeesAlleBerichten() throws ParseException {

        service.synchroniseerPersonen();

        verify(gbavRepository).verwerkLo3Berichten(argThat(new ConversieResultaatMatcher()), any(VerzendSynchronisatieBerichtVerwerker.class), anyInt());

        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(gbavRepository);
        verifyNoMoreInteractions(excelAdapter);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testCreateInitAfnemersIndicatieTabel() {
        service.laadInitAfnemersIndicatieTabel();

        verify(afnemersIndicatieRepository).laadInitVullingAfnIndTable();
        verifyNoMoreInteractions(gbavRepository);
        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(excelAdapter);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testCreateInitAutorisatieRegelTabel() {
        service.laadInitAutorisatieRegelTabel();

        verify(autorisatieRepository).laadInitVullingAutTable();
        verifyNoMoreInteractions(gbavRepository);
        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(excelAdapter);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    @Test
    public void testCreateAndFillInitVullingTable() throws ParseException {
        service.laadInitieleVullingTable();

        verify(gbavRepository).laadInitVullingTable();
        verifyNoMoreInteractions(gbavRepository);
        verifyNoMoreInteractions(autorisatieRepository);
        verifyNoMoreInteractions(afnemersIndicatieRepository);
        verifyNoMoreInteractions(excelAdapter);
        verifyNoMoreInteractions(dataSource);
        verifyNoMoreInteractions(jmsTemplate);
    }

    public static final class ConversieResultaatMatcher extends ArgumentMatcher<ConversieResultaat> {

        @Override
        public boolean matches(final Object argument) {
            if (argument instanceof ConversieResultaat) {
                return ConversieResultaat.TE_VERZENDEN == argument;
            } else {
                return false;
            }
        }
    }
}
