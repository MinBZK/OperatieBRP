/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AlgemeneAbstractActieUitvoerderTest {

    private AbstractActieUitvoerder<BerichtRootObject, HisVolledigRootObject> uitvoerder;

    @Mock
    private Verwerkingsregel verwerkingsregel1;
    @Mock
    private Afleidingsregel  afleidingsregel1a;
    @Mock
    private Afleidingsregel  afleidingsregel1b;

    @Mock
    private Verwerkingsregel verwerkingsregel2;
    @Mock
    private Afleidingsregel  afleidingsregel2a;
    @Mock
    private Afleidingsregel  afleidingsregel2b;

    private BerichtRootObject     berichtRootObject     = new PersoonBericht();
    private HisVolledigRootObject hisVolledigRootObject = new PersoonHisVolledigImpl(
            new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    private ActieBericht          actieBericht          = new ActieRegistratieAdresBericht();

    @Before
    public void setUp() {
        when(verwerkingsregel1.getAfleidingsregels()).thenReturn(
                Arrays.asList(afleidingsregel1a, afleidingsregel1b));
        when(verwerkingsregel2.getAfleidingsregels()).thenReturn(
                Arrays.asList(afleidingsregel2a, afleidingsregel2b));

        uitvoerder = new AbstractActieUitvoerder<BerichtRootObject, HisVolledigRootObject>() {

            @Override
            protected HisVolledigRootObject bepaalRootObjectHisVolledig() {
                return hisVolledigRootObject;
            }

            @Override
            protected void verzamelVerwerkingsregels() {
                voegVerwerkingsregelToe(verwerkingsregel1);
                voegVerwerkingsregelToe(verwerkingsregel2);
            }

            @Override
            protected void verzamelAfleidingsregels() {
                voegAfleidingsregelToe(new AbstractAfleidingsregel(null, null) {
                    @Override
                    public AfleidingResultaat leidAf() {
                        return new AfleidingResultaat();
                    }

                    @Override
                    public Regel getRegel() {
                        return null;
                    }

                });
            }
        };

        uitvoerder.setActieBericht(actieBericht);
        actieBericht.setRootObject(berichtRootObject);
    }

    @Test
    public void testVoerActieUit() {
        final List<Afleidingsregel> afleidingsregels = uitvoerder.voerActieUit();

        verify(verwerkingsregel1, times(1)).neemBerichtDataOverInModel();
        verify(verwerkingsregel2, times(1)).neemBerichtDataOverInModel();

        verify(verwerkingsregel1, times(1)).verzamelAfleidingsregels();
        verify(verwerkingsregel2, times(1)).verzamelAfleidingsregels();

        verify(verwerkingsregel1, times(1)).getAfleidingsregels();
        verify(verwerkingsregel2, times(1)).getAfleidingsregels();

        // Elke verwerkingsregel kent 2 afleidingsregels, plus kent de uitvoerder nog 1 afleidingsregel.
        assertEquals(5, afleidingsregels.size());
    }
}
