/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.service.dalapi.LeveringsautorisatieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * LeveringsAutorisatieCacheHelperImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class LeveringsAutorisatieCacheHelperImplTest {

    @Mock
    private LeveringsautorisatieRepository leveringsautorisatieRepository;

    @InjectMocks
    private LeveringsAutorisatieCacheHelperImpl leveringsAutorisatieCacheHelper;

    private PartijCacheImpl.Data partijCacheData;

    @Before
    public void setUp() {
        final Partij partij1 = new Partij("naam1", "000001");
        partij1.setId((short) 1);
        final PartijRol partijRol1 = new PartijRol(partij1, Rol.AFNEMER);
        partij1.addPartijRol(partijRol1);

        final Partij ondertekenaar = new Partij("ondertekenaar", "000002");
        final short ondertekenaarId = (short) 2;
        ondertekenaar.setId(ondertekenaarId);
        ondertekenaar.setOin("2");
        final PartijRol partijRol2 = new PartijRol(ondertekenaar, Rol.AFNEMER);
        partijRol2.setId(2);
        ondertekenaar.addPartijRol(partijRol2);

        final Partij transporteur = new Partij("transporteur", "000003");
        final short transporteurId = (short) 3;
        transporteur.setId(transporteurId);
        transporteur.setOin("3");
        final PartijRol partijRol3 = new PartijRol(transporteur, Rol.AFNEMER);
        partijRol3.setId(3);
        transporteur.addPartijRol(partijRol3);

        final Leveringsautorisatie leveringsautorisatie1 = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie1.setId(1);

        //la zonder dienstbundel
        final Leveringsautorisatie leveringsautorisatie2 = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie2.setId(2);

        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol1, leveringsautorisatie1);
        toegangLeveringsAutorisatie.setId(1);
        toegangLeveringsAutorisatie.setAfleverpunt("afleverpunt");
        final Partij ondertekenaarDummy = new Partij("ondertekenaar", "000002");
        ondertekenaarDummy.setId(ondertekenaarId);
        toegangLeveringsAutorisatie.setOndertekenaar(ondertekenaarDummy);
        final Partij transporteurDummy = new Partij("transporteur", "000003");
        toegangLeveringsAutorisatie.setTransporteur(transporteurDummy);
        transporteurDummy.setId(transporteurId);

        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie2 = new ToegangLeveringsAutorisatie(partijRol1, leveringsautorisatie2);
        toegangLeveringsAutorisatie2.setId(2);
        toegangLeveringsAutorisatie2.setAfleverpunt("afleverpunt");

        final Dienstbundel dienstbundel1 = new Dienstbundel(leveringsautorisatie1);
        dienstbundel1.setId(1);

        //dienstbundel zonder dienstbundel groep en dienst
        final Dienstbundel dienstbundel2 = new Dienstbundel(leveringsautorisatie1);
        dienstbundel2.setId(2);

        final DienstbundelGroep dienstbundelGroep1 = new DienstbundelGroep(dienstbundel1, Element.PERSOON_ADRES_STANDAARD, false, false, false);
        dienstbundelGroep1.setId(1);

        final DienstbundelGroepAttribuut dienstbundelGroepAttribuut1 = new DienstbundelGroepAttribuut(dienstbundelGroep1,
                Element.PERSOON_ADRES_HUISNUMMER);
        dienstbundelGroep1.setId(1);

        final Dienst dienst1 = new Dienst(dienstbundel1, SoortDienst.ZOEK_PERSOON);
        dienst1.setId(1);

        Mockito.when(leveringsautorisatieRepository.haalAlleDienstbundelGroepAttributenOpZonderAssocaties())
                .thenReturn(Lists.newArrayList(dienstbundelGroepAttribuut1));
        Mockito.when(leveringsautorisatieRepository.haalAlleDienstbundelGroepenOpZonderAssocaties()).thenReturn(Lists.newArrayList(dienstbundelGroep1));
        Mockito.when(leveringsautorisatieRepository.haalAlleDienstbundelsOpZonderAssocaties())
                .thenReturn(Lists.newArrayList(dienstbundel1, dienstbundel2));
        Mockito.when(leveringsautorisatieRepository.haalAlleDienstenOpZonderAssocaties()).thenReturn(Lists.newArrayList(dienst1));
        Mockito.when(leveringsautorisatieRepository.haalAlleLeveringsautorisatiesOpZonderAssocaties())
                .thenReturn(Lists.<Leveringsautorisatie>newArrayList(leveringsautorisatie1, leveringsautorisatie2));
        Mockito.when(leveringsautorisatieRepository.haalAlleToegangLeveringsautorisatiesOpZonderAssociaties())
                .thenReturn(Lists.newArrayList(toegangLeveringsAutorisatie, toegangLeveringsAutorisatie2));

        partijCacheData = new PartijCacheImpl.Data(Lists.newArrayList(partij1, ondertekenaar, transporteur));
    }

    @Test
    public void testLaad() {
        final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = leveringsAutorisatieCacheHelper.ophalenAlleToegangLeveringsautorisaties
                (partijCacheData);

        Assert.assertNotNull(toegangLeveringsAutorisaties);
        Assert.assertEquals(2, toegangLeveringsAutorisaties.size());

        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = Iterables.find(toegangLeveringsAutorisaties,
                dienstBinnenLevAut -> dienstBinnenLevAut.getId() == 1, null);
        Assert.assertEquals("2", toegangLeveringsAutorisatie.getOndertekenaar().getOin());
        Assert.assertEquals("3", toegangLeveringsAutorisatie.getTransporteur().getOin());

        final Leveringsautorisatie leveringsautorisatie = toegangLeveringsAutorisatie.getLeveringsautorisatie();
        Assert.assertNotNull(leveringsautorisatie);
        final Set<Dienstbundel> dienstbundelSet = leveringsautorisatie.getDienstbundelSet();
        Assert.assertNotNull(dienstbundelSet);
        Assert.assertEquals(2, dienstbundelSet.size());
        final Dienstbundel dienstbundel = Iterables.find(dienstbundelSet, dienstbundelInSet -> dienstbundelInSet.getId() == 1, null);

        //controleer dienst
        final Set<Dienst> diensten = dienstbundel.getDienstSet();
        Assert.assertNotNull(diensten);
        Assert.assertEquals(1, diensten.size());
        //controleer dienstbundel groepen
        final Set<DienstbundelGroep> dienstbundelGroepSet = dienstbundel.getDienstbundelGroepSet();
        Assert.assertNotNull(dienstbundelGroepSet);
        Assert.assertEquals(1, dienstbundelGroepSet.size());
        for (DienstbundelGroep dienstbundelGroep : dienstbundelGroepSet) {
            final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttribuutSet = dienstbundelGroep.getDienstbundelGroepAttribuutSet();
            Assert.assertNotNull(dienstbundelGroepAttribuutSet);
            Assert.assertEquals(1, dienstbundelGroepAttribuutSet.size());
        }

    }
}
