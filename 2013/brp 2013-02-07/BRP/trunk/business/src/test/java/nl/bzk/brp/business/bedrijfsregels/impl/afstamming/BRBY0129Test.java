/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.operationeel.kern.FamilierechtelijkeBetrekkingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class BRBY0129Test {

    @Mock
    private PersoonRepository persoonRepository;

    @InjectMocks
    private final BRBY0129 brby0129 = new BRBY0129();

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void zouGeenMeldingMoetenGevenOmdatOudersVoorKindZijnGeboren() {
        final FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        final BetrokkenheidBericht kindBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.KIND, "1");
        final BetrokkenheidBericht moederBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.OUDER, "2");
        final BetrokkenheidBericht vaderBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.OUDER, "3");
        final PersoonModel vaderModel = mock(PersoonModel.class);
        final PersoonModel moederModel = mock(PersoonModel.class);


        final FamilierechtelijkeBetrekkingBericht relatieBericht = new FamilierechtelijkeBetrekkingBericht();
        relatieBericht.setBetrokkenheden(
            Arrays.asList(kindBetrokkeneBericht, vaderBetrokkeneBericht, moederBetrokkeneBericht));

        final PersoonGeboorteGroepModel moederGeboorte = mock(PersoonGeboorteGroepModel.class);
        final PersoonGeboorteGroepModel vaderGeboorte = mock(PersoonGeboorteGroepModel.class);

        // now the mocking
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("2"))).thenReturn(moederModel);
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("3"))).thenReturn(vaderModel);

        ReflectionTestUtils.setField(moederGeboorte, "datumGeboorte", DatumUtil.gisteren());
        when(moederModel.getGeboorte()).thenReturn(moederGeboorte);
        when(vaderModel.getGeboorte()).thenReturn(vaderGeboorte);
        when(moederGeboorte.getDatumGeboorte()).thenReturn(DatumUtil.gisteren());
        when(vaderGeboorte.getDatumGeboorte()).thenReturn(DatumUtil.gisteren());

        List<Melding> meldingen = brby0129.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(0));
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }


    @Test
    public void zouMeldingMoetenGevenOmdatVaderNaKindIsGeboren() {
        final FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        final BetrokkenheidBericht kindBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.KIND, "1");
        final BetrokkenheidBericht moederBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.OUDER, "2");
        final BetrokkenheidBericht vaderBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.OUDER, "3");
        final PersoonModel vaderModel = mock(PersoonModel.class);
        final PersoonModel moederModel = mock(PersoonModel.class);


        final FamilierechtelijkeBetrekkingBericht relatieBericht = new FamilierechtelijkeBetrekkingBericht();
        relatieBericht.setBetrokkenheden(
            Arrays.asList(kindBetrokkeneBericht, vaderBetrokkeneBericht, moederBetrokkeneBericht));

        final PersoonGeboorteGroepModel moederGeboorte = mock(PersoonGeboorteGroepModel.class);
        final PersoonGeboorteGroepModel vaderGeboorte = mock(PersoonGeboorteGroepModel.class);

        // now the mocking
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("2"))).thenReturn(moederModel);
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("3"))).thenReturn(vaderModel);

        ReflectionTestUtils.setField(moederGeboorte, "datumGeboorte", DatumUtil.gisteren());
        when(moederModel.getGeboorte()).thenReturn(moederGeboorte);
        when(vaderModel.getGeboorte()).thenReturn(vaderGeboorte);
        when(moederGeboorte.getDatumGeboorte()).thenReturn(DatumUtil.gisteren());
        when(vaderGeboorte.getDatumGeboorte()).thenReturn(DatumUtil.morgen());

        List<Melding> meldingen = brby0129.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(1));
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }

    @Test
    public void zouMeldingMoetenGevenOmdatOudersOpDezelfdeDagZijnGeboren() {
        final FamilierechtelijkeBetrekkingModel relatieModel = mock(FamilierechtelijkeBetrekkingModel.class);
        final BetrokkenheidBericht kindBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.KIND, "1");
        final BetrokkenheidBericht moederBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.OUDER, "2");
        final BetrokkenheidBericht vaderBetrokkeneBericht = maakBetrokkeneBericht(SoortBetrokkenheid.OUDER, "3");
        final PersoonModel vaderModel = mock(PersoonModel.class);
        final PersoonModel moederModel = mock(PersoonModel.class);


        final FamilierechtelijkeBetrekkingBericht relatieBericht = new FamilierechtelijkeBetrekkingBericht();
        relatieBericht.setBetrokkenheden(
            Arrays.asList(kindBetrokkeneBericht, vaderBetrokkeneBericht, moederBetrokkeneBericht));

        final PersoonGeboorteGroepModel moederGeboorte = mock(PersoonGeboorteGroepModel.class);
        final PersoonGeboorteGroepModel vaderGeboorte = mock(PersoonGeboorteGroepModel.class);

        // now the mocking
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("2"))).thenReturn(moederModel);
        when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer("3"))).thenReturn(vaderModel);

        ReflectionTestUtils.setField(moederGeboorte, "datumGeboorte", DatumUtil.gisteren());
        when(moederModel.getGeboorte()).thenReturn(moederGeboorte);
        when(vaderModel.getGeboorte()).thenReturn(vaderGeboorte);
        when(moederGeboorte.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());
        when(vaderGeboorte.getDatumGeboorte()).thenReturn(DatumUtil.vandaag());

        List<Melding> meldingen = brby0129.executeer(relatieModel, relatieBericht, null);

        assertThat(meldingen.size(), is(2));
        verify(persoonRepository, times(2)).findByBurgerservicenummer(isA(Burgerservicenummer.class));
    }


    private BetrokkenheidBericht maakBetrokkeneBericht(final SoortBetrokkenheid soortBetrokkenheid, final String id) {
        BetrokkenheidBericht betrokkenheidBericht;

        switch (soortBetrokkenheid) {
            case OUDER:
                betrokkenheidBericht = new OuderBericht(); break;
            case KIND:
                betrokkenheidBericht = new KindBericht(); break;
            default:
                throw new IllegalArgumentException("Soort : " + " soortBetrokkenheid + is niet ondersteund");
        }

        PersoonBericht persoonBericht = new PersoonBericht();

        // geboortedatum
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(DatumUtil.vandaag());
        persoonBericht.setGeboorte(persoonGeboorteGroepBericht);

        // bsn
        final PersoonIdentificatienummersGroepBericht identificatienummers =
            new PersoonIdentificatienummersGroepBericht();
        identificatienummers.setBurgerservicenummer(new Burgerservicenummer(id));
        persoonBericht.setIdentificatienummers(identificatienummers);

        betrokkenheidBericht.setPersoon(persoonBericht);
        return betrokkenheidBericht;
    }
}
