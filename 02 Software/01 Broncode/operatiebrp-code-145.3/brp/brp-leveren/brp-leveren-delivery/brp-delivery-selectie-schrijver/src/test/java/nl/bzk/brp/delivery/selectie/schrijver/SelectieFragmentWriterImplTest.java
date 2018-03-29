/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SelectieFragmentWriterImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieFragmentWriterImplTest {

    private static final ZonedDateTime TS_REG = DatumUtil.nuAlsZonedDateTime();
    private static final Long PERSOON_ID = 1L;
    private static final Integer ID = 1;
    private static final Map<Long, ZonedDateTime> PERSONEN = ImmutableMap.<Long, ZonedDateTime>builder().
            put(PERSOON_ID, TS_REG).build();

    @Mock
    private SelectieFileService selectieFileService;

    @InjectMocks
    private SelectieFragmentWriterImpl selectieFragmentWriter;


    @Test
    public void testHappyFlow() throws SelectieException, IOException {
        SelectieFragmentSchrijfBericht bericht = maakSelectieFragmentSchrijfBericht();
        bericht.setBerichten(Lists.newArrayList("bericht"));
        bericht.setProtocolleringPersonen(PERSONEN);
        selectieFragmentWriter.verwerk(bericht);

        Mockito.verify(selectieFileService).initSchrijfOpslag(bericht);
        Mockito.verify(selectieFileService).schrijfDeelFragment(Matchers.any(), Matchers.any());
        Mockito.verify(selectieFileService).concatDeelFragmenten(bericht);
        Mockito.verify(selectieFileService).schijfProtocolleringPersonen(bericht,
                Lists.newArrayList(String.format("{\"persoonId\":%d,\"tijdstipLaatsteWijziging\":\"%s\"}",
                        PERSOON_ID, DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(TS_REG))));
    }

    @Test
    public void test_GeenPersonenAanwezig() throws SelectieException, IOException {
        SelectieFragmentSchrijfBericht bericht = maakSelectieFragmentSchrijfBericht();
        bericht.setBerichten(Lists.newArrayList("bericht"));
        selectieFragmentWriter.verwerk(bericht);

        Mockito.verify(selectieFileService).initSchrijfOpslag(bericht);
        Mockito.verify(selectieFileService).schrijfDeelFragment(Matchers.any(), Matchers.any());
        Mockito.verify(selectieFileService).concatDeelFragmenten(bericht);
        Mockito.verify(selectieFileService, Mockito.never()).schijfProtocolleringPersonen(Mockito.any(), Mockito.any());
    }

    @Test
    public void test_GeenBerichtenAanwezig() throws SelectieException, IOException {
        SelectieFragmentSchrijfBericht bericht = maakSelectieFragmentSchrijfBericht();
        selectieFragmentWriter.verwerk(bericht);

        Mockito.verify(selectieFileService, Mockito.never()).initSchrijfOpslag(bericht);
        Mockito.verify(selectieFileService, Mockito.never()).schrijfDeelFragment(Matchers.any(), Matchers.any());
        Mockito.verify(selectieFileService, Mockito.never()).concatDeelFragmenten(bericht);
        Mockito.verify(selectieFileService, Mockito.never()).schijfProtocolleringPersonen(Mockito.any(), Mockito.any());
    }

    @Test(expected = SelectieException.class)
    public void testIOException() throws SelectieException, IOException {
        SelectieFragmentSchrijfBericht bericht = maakSelectieFragmentSchrijfBericht();
        bericht.setBerichten(Lists.newArrayList("bericht"));
        bericht.setProtocolleringPersonen(PERSONEN);
        Mockito.doThrow(IOException.class).when(selectieFileService).schijfProtocolleringPersonen(Mockito.any(), Mockito.any());

        selectieFragmentWriter.verwerk(bericht);
    }

    private SelectieFragmentSchrijfBericht maakSelectieFragmentSchrijfBericht() {
        SelectieFragmentSchrijfBericht bericht = new SelectieFragmentSchrijfBericht();
        bericht.setDienstId(ID);
        bericht.setToegangLeveringsAutorisatieId(ID);
        return bericht;
    }
}
