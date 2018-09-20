/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands.selecties;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.List;

import com.google.common.base.Optional;
import nl.bzk.brp.bevraging.commands.BevraagInfo;
import nl.bzk.brp.bevraging.dataaccess.PersoonLeveringService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersoonExpressieResultSetExtractorTest {

    @Mock
    private PersoonLeveringService persoonLeveringService;

    @InjectMocks
    private PersoonExpressieResultSetExtractor extractor = new PersoonExpressieResultSetExtractor();

    @Test
    public void kanDataExtracten() throws Exception {
        // given
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getBytes(anyInt())).thenReturn(new byte[]{});
        when(persoonLeveringService.leverPersoon(any(byte[].class))).thenReturn(Optional.of(false));

        // when
        List<BevraagInfo> result = extractor.extractData(resultSet);

        Assert.assertThat(result.size(), Matchers.is(1));

    }
}
