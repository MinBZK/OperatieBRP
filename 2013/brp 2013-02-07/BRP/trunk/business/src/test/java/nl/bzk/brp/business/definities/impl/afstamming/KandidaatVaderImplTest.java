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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkModel;
import nl.bzk.brp.model.operationeel.kern.PartnerModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;


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
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(null,  Short.valueOf("2")));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getPersoon());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderZonderOverledenVaderKindGeborenBuitenPeriodeHuwelijk() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(null, Short.valueOf("2")));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getPersoon());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20090101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNietNL() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20110101, Short.valueOf("2")));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getPersoon());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNietNLBuitenPeriode() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20101231, Short.valueOf("2")));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getPersoon());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNL() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20110301, BrpConstanten.NL_NATIONALITEIT_CODE.getWaarde()));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getPersoon());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(1, kandidaten.size());
    }

    @Test
    public void testVraagOpKandidaatVaderMetOverledenVaderNLBuitenPeriode() {
        // Buitenlands nationaliteit
        List<BetrokkenheidModel> echtgenoten = Arrays.asList(maakBetrokkenheden(20110228,  BrpConstanten.NL_NATIONALITEIT_CODE.getWaarde()));

        Mockito.when(
                relatieRepository.haalOpBetrokkenhedenVanPersoon(Matchers.any(PersoonModel.class),
                        Matchers.any(RelatieSelectieFilter.class))).thenReturn(echtgenoten);

        Mockito.when(persoonRepository.haalPersoonOpMetAdresViaBetrokkenheid(echtgenoten.get(0))).thenReturn(
                echtgenoten.get(0).getPersoon());

        List<PersoonModel> kandidaten =
            kandidaatVader.bepaalKandidatenVader(new PersoonModel(new PersoonBericht()), new Datum(20120101));

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonOpMetAdresViaBetrokkenheid(
                (BetrokkenheidModel) Matchers.any());

        Assert.assertEquals(0, kandidaten.size());
    }

    private BetrokkenheidModel maakBetrokkenheden(final Integer overlijdingsDatum, final Short nationaliteitCode) {
        HuwelijkBericht relatie = new HuwelijkBericht();
        relatie.setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht());
        relatie.getStandaard().setDatumAanvang(new Datum(20101231));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());

        PersoonNationaliteitBericht nationaliteit = new PersoonNationaliteitBericht();
        nationaliteit.setNationaliteit(new Nationaliteit(
                new Nationaliteitcode(nationaliteitCode),
                new NaamEnumeratiewaarde("willekeurig nationaliteit"),
                new Datum(19601231), // aanvang datum
                null));

        persoon.setNationaliteiten(Arrays.asList(nationaliteit));

        if (overlijdingsDatum != null) {
            relatie.getStandaard().setDatumEinde(new Datum(overlijdingsDatum));
            RedenBeeindigingRelatie redenBeeindigingRelatie = StatischeObjecttypeBuilder.bouwRedenBeeindigingRelatie(BrpConstanten.REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE_STRING, "omschrijving reden.");

            relatie.getStandaard().setRedenEinde(redenBeeindigingRelatie);
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("111"));
        } else {
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("222"));
        }

        HuwelijkModel relatieModel = new HuwelijkModel(relatie);
        PersoonModel persoonModel = new PersoonModel(persoon);
        PersoonNationaliteitModel nationaliteitModel = new PersoonNationaliteitModel(nationaliteit, persoonModel);
        nationaliteitModel.setPersoonNationaliteitStatusHis(StatusHistorie.A);
        persoonModel.getNationaliteiten().add(nationaliteitModel);

        return new PartnerModel(new PartnerBericht(), relatieModel, persoonModel);
    }
}
