/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.bericht.BerichtFactoryImpl;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class VoegStuurgegevensToeStapTest extends AbstractStappenTest {

    @InjectMocks
    private VoegStuurgegevensToeStap voegStuurgegevensToeStap = new VoegStuurgegevensToeStap();

    @Mock
    private BerichtFactory berichtFactory = new BerichtFactoryImpl();

    @Test
    public final void maaktStuurgegevens() {
        // arrange
        maakBericht(12348945, maakDummyLeveringinformatie(), 31223, "TestSysteem",
            SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        final AdministratieveHandelingModel administratieveHandeling = maakAdministratieveHandelingModel();

        getBerichtContext().setVolledigBericht(new VolledigBericht(
            new AdministratieveHandelingSynchronisatie(administratieveHandeling)));

        when(berichtFactory.maakStuurgegevens(any(Partij.class))).thenReturn(new BerichtStuurgegevensGroepBericht());
        when(berichtFactory.maakParameters(any(Leveringinformatie.class), any(SoortSynchronisatie.class)))
            .thenReturn(new BerichtParametersGroepBericht());

        // act
        final boolean stapResultaat =
            voegStuurgegevensToeStap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        assertThat(getBerichtContext().getVolledigBericht().getStuurgegevens(), notNullValue());
    }
}
