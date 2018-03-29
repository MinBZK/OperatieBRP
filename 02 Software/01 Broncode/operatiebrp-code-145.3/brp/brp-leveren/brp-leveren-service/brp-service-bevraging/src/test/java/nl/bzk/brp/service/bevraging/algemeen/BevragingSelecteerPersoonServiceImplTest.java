/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.persoonselectie.SelecteerPersoonService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link BevragingSelecteerPersoonServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BevragingSelecteerPersoonServiceImplTest {

    private static final String VALIDE_BSN = "426696955";
    private static final String INVALIDE_BSN = "123";
    private static final String VALIDE_ADMINISTRATIENUMMER = "9376096018";
    private static final String INVALIDE_ADMINISTRATIENUMMER = "123";
    private static final String OBJECTSLEUTEL = "123";
    private static final String PARTIJ_CODE = "99";

    private final Persoonslijst persoonslijst = new Persoonslijst(TestBuilders.maakIngeschrevenPersoon().build(), 0L);

    @InjectMocks
    BevragingSelecteerPersoonServiceImpl bevragingSelecteerPersoonService;

    @Mock
    private SelecteerPersoonService selecteerPersoonService;
    @Mock
    private ObjectSleutelService objectSleutelService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void selecteerPersoonOpBsnHappyFlow() throws Exception {
        Mockito.when(selecteerPersoonService.selecteerPersoonMetBsn(Mockito.anyString(), Mockito.any())).thenReturn(persoonslijst);

        final Persoonslijst result = bevragingSelecteerPersoonService.selecteerPersoon(VALIDE_BSN, null, null, PARTIJ_CODE, null);

        Assert.assertEquals(persoonslijst, result);
    }

    @Test
    public void selecteerPersoonOpAnummerHappyFlow() throws Exception {
        Mockito.when(selecteerPersoonService.selecteerPersoonMetANummer(Mockito.anyString(), Mockito.any())).thenReturn(persoonslijst);

        final Persoonslijst result = bevragingSelecteerPersoonService.selecteerPersoon(null, VALIDE_ADMINISTRATIENUMMER, null, PARTIJ_CODE, null);

        Assert.assertEquals(persoonslijst, result);
    }

    @Test
    public void selecteerPersoonOpObjectsleutelHappyFlow() throws Exception {
        Mockito.when(selecteerPersoonService.selecteerPersoonMetId(Mockito.anyLong(), Mockito.any())).thenReturn(persoonslijst);
        final ObjectSleutel persoonObjectSleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(1, 123);
        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(OBJECTSLEUTEL)).thenReturn(persoonObjectSleutel);

        final Persoonslijst result = bevragingSelecteerPersoonService.selecteerPersoon(null, null, OBJECTSLEUTEL, PARTIJ_CODE, null);

        Assert.assertEquals(persoonslijst, result);
    }

    @Test
    public void selecteerPersoonOpObjectsleutelVerkeerdeVersie() throws Exception {
        Mockito.when(selecteerPersoonService.selecteerPersoonMetId(Mockito.anyLong(), Mockito.any())).thenReturn(persoonslijst);
        final ObjectSleutel persoonObjectSleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(1, 124);
        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(OBJECTSLEUTEL)).thenReturn(persoonObjectSleutel);

        thrown.expect(new ExceptionRegelMatcher(Regel.R1833));
        bevragingSelecteerPersoonService.selecteerPersoon(null, null, OBJECTSLEUTEL, PARTIJ_CODE, null);
    }

    @Test
    public void selecteerPersoonOngeldigBsn() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R1587));
        bevragingSelecteerPersoonService.selecteerPersoon(INVALIDE_BSN, null, null, PARTIJ_CODE, null);
    }

    @Test
    public void selecteerPersoonOngeldigAnummer() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R1585));
        bevragingSelecteerPersoonService.selecteerPersoon(null, INVALIDE_ADMINISTRATIENUMMER, null, PARTIJ_CODE, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void selecteerPersoonOngeldigeObjectsleutel() throws Exception {
        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(Mockito.anyString())).thenThrow(OngeldigeObjectSleutelException.class);
        thrown.expect(new ExceptionRegelMatcher(Regel.R1833));
        bevragingSelecteerPersoonService.selecteerPersoon(null, null, OBJECTSLEUTEL, PARTIJ_CODE, null);
    }

    @Test
    public void selecteerPersoonAlleIdentificatiecriteriaNull() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R2192));
        bevragingSelecteerPersoonService.selecteerPersoon(null, null, null, PARTIJ_CODE, null);
    }

    @Test
    public void selecteerPersoonTeveelIdentificatieCriteria() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R2192));
        bevragingSelecteerPersoonService.selecteerPersoon(VALIDE_BSN, VALIDE_ADMINISTRATIENUMMER, null, PARTIJ_CODE, null);
    }

    @Test
    public void selecteerPersoonOngeldigeObjectSleutel() throws Exception {
        thrown.expect(new ExceptionRegelMatcher(Regel.R1833));
        Mockito.when(objectSleutelService.maakPersoonObjectSleutel(Mockito.any())).thenThrow(new OngeldigeObjectSleutelException(Regel.R1833));
        bevragingSelecteerPersoonService.selecteerPersoon(null, null, "abc", PARTIJ_CODE, null);
    }

}
