/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.bijhouding;

import java.util.ArrayList;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingSynchronisatiePersoonBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class BerichtVerrijkingsStapTest {

    private static final String NAAM_PARTIJ = "testpartij";

    @InjectMocks
    private final BerichtVerrijkingsStap berichtVerrijkingsStap = new BerichtVerrijkingsStap();

    @Mock
    private ReferentieDataRepository referentieRepository;

    private final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();

    private BevragingBerichtContextBasis berichtContext;

    @Spy
    private final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>(0));

    @Before
    public final void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(referentieRepository.vindPartijOpCode(new PartijCodeAttribuut(1234)))
            .thenReturn(TestPartijBuilder.maker().metNaam(new NaamEnumeratiewaardeAttribuut(NAAM_PARTIJ)).maak());

        maakBericht();
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.getStuurgegevens().setZendendePartijCode("01234");
        bericht.getStuurgegevens()
            .setZendendePartij(new PartijAttribuut(TestPartijBuilder.maker().metNaam(new NaamEnumeratiewaardeAttribuut(NAAM_PARTIJ)).maak()));
    }

    @Test
    public final void testVoerStapUitLeveringsautorisatieWordtOpgehaaldMetDiensten() {
        final boolean stapResultaat = berichtVerrijkingsStap.voerStapUit(getBericht(), getBerichtContext(), getResultaat());

        Assert.assertTrue(stapResultaat);
        Assert.assertNotNull(getBericht().getStuurgegevens().getZendendePartij());
    }


    protected void maakBericht() {
        final BerichtZoekcriteriaPersoonGroepBericht criteria = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setZoekcriteriaPersoon(criteria);

        final AdministratieveHandelingBericht administratieveHandeling = new HandelingSynchronisatiePersoonBericht();
        bericht.getStandaard().setAdministratieveHandeling(administratieveHandeling);

        berichtContext = new BevragingBerichtContextBasis(new BerichtenIds(null, null),
            TestPartijBuilder.maker().metNaam(new NaamEnumeratiewaardeAttribuut(NAAM_PARTIJ)).maak(), null, null);
        berichtContext = Mockito.spy(berichtContext);
        berichtContext.setPersoonHisVolledigImpl(TestPersoonJohnnyJordaan.maak());
    }

    public final GeefDetailsPersoonBericht getBericht() {
        return bericht;
    }

    public final BevragingBerichtContextBasis getBerichtContext() {
        return berichtContext;
    }

    public final BevragingResultaat getResultaat() {
        return resultaat;
    }
}
