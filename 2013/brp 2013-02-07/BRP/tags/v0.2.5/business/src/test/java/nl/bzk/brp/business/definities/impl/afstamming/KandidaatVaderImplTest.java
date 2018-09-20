/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.definities.impl.afstamming;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.dataaccess.selectie.RelatieSelectieFilter;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;


public class KandidaatVaderImplTest {

    @Mock
    private PersoonRepository        persoonRepository;
    @Mock
    private RelatieRepository        relatieRepository;

    @InjectMocks
    private final KandidaatVaderImpl kandidaatVader = new KandidaatVaderImpl();

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void testVraagOpKandidaatVaderNietGevonden() {
        List<BetrokkenheidModel> echtgenoten = new ArrayList<BetrokkenheidModel>();

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderZonderOverledenVaderNietNL() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(null, (short) 2));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getBetrokkene());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderZonderOverledenVaderKindGeborenBuitenPeriodeHuwelijk() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(null, (short) 2));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getBetrokkene());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20090101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNietNL() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20110101, (short) 2));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getBetrokkene());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNietNLBuitenPeriode() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20101231, (short) 2));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getBetrokkene());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNL() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20110301, (short) 1));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getBetrokkene());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNLBuitenPeriode() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20110228, (short) 1));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getBetrokkene());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    private BetrokkenheidModel maakBetrokkenheden(final Integer overlijdingsDatum, final short nationaliteitCode) {
        RelatieBericht relatie = new RelatieBericht();
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.getGegevens().setDatumAanvang(new Datum(20101231));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());

        PersoonNationaliteitBericht nationaliteit = new PersoonNationaliteitBericht();
        nationaliteit.setNationaliteit(new Nationaliteit());
        nationaliteit.getNationaliteit().setNationaliteitcode(new Nationaliteitcode(nationaliteitCode));

        persoon.setNationaliteiten(Arrays.asList(nationaliteit));

        if (overlijdingsDatum != null) {
            relatie.getGegevens().setDatumEinde(new Datum(overlijdingsDatum));
            RedenBeeindigingRelatie redenBeeindigingRelatie = new RedenBeeindigingRelatie();
            ReflectionTestUtils.setField(redenBeeindigingRelatie, "code", new RedenBeeindigingRelatieCode(
                    BrpConstanten.REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE_STRING));
            relatie.getGegevens().setRedenBeeindigingRelatie(redenBeeindigingRelatie);
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("overleden"));
        } else {
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("nietoverleden"));
        }

        RelatieModel relatieModel = new RelatieModel(relatie);
        PersoonModel persoonModel = new PersoonModel(persoon);
        persoonModel.getNationaliteiten().add(new PersoonNationaliteitModel(nationaliteit, persoonModel));

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        return new BetrokkenheidModel(betrokkenheid, persoonModel, relatieModel);
    }
}
