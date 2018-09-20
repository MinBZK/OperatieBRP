/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class AdministratieveHandelingSynchronisatieTest {

    private DatumTijdAttribuut tsreg = DatumTijdAttribuut.nu();
    private Long admHndId = 1234L;
    private AdministratieveHandelingSynchronisatie kennisgeving;

    private PersoonHisVolledig persoon1, persoon2, persoon3;

    @Before
    public void setUp() {
        DatumTijdAttribuut nu = DatumTijdAttribuut.nu();

        PartijAttribuut partij = StatischeObjecttypeBuilder.bouwPartij(123, "Gemeente Soest");
        AdministratieveHandelingModel administratieveHandeling =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.ADOPTIE_INGEZETENE), partij,
                        new OntleningstoelichtingAttribuut(""),
                        nu);
        ReflectionTestUtils.setField(administratieveHandeling, "iD", admHndId);
        List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        ActieModel actie =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), administratieveHandeling, null,
                        new DatumEvtDeelsOnbekendAttribuut(
                                20010101), null, tsreg, null);

        persoon1 =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwAfgeleidAdministratiefRecord(actie)
                        .sorteervolgorde(new Byte("3")).eindeRecord().build();
        persoon2 =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwAfgeleidAdministratiefRecord(actie)
                        .sorteervolgorde(new Byte("2")).eindeRecord().build();
        persoon3 =
                new PersoonHisVolledigImplBuilder(SoortPersoon.NIET_INGESCHREVENE)
                        .nieuwAfgeleidAdministratiefRecord(actie)
                        .sorteervolgorde(new Byte("1")).eindeRecord().build();

        // Dummy bijgehouden personen
        bijgehoudenPersonen.add(new PersoonHisVolledigView(persoon1, null));
        bijgehoudenPersonen.add(new PersoonHisVolledigView(persoon2, null));
        bijgehoudenPersonen.add(new PersoonHisVolledigView(persoon3, null));

        kennisgeving = new AdministratieveHandelingSynchronisatie(administratieveHandeling);
        kennisgeving.setBijgehoudenPersonen(bijgehoudenPersonen);
    }

    @Test
    public void standaardGeeftAlleBijgehoudenPersonen() {
        assertThat(kennisgeving.getBijgehoudenPersonen().size(), is(3));
    }

    @Test
    public void metEenFalsePredicateGeeftGeenPersonen() {
        kennisgeving.setBijgehoudenPersonenPredikaat(PredicateUtils.falsePredicate());
        assertThat(kennisgeving.getBijgehoudenPersonen().size(), is(0));
    }

    @Test
    public void metEenAnderPredicateGeeftGefilterdePersonen() {
        kennisgeving.setBijgehoudenPersonenPredikaat(new NietIngeschevenePredicate());
        assertThat(kennisgeving.getBijgehoudenPersonen().size(), is(1));
    }

    @Test
    public void testSorteringOpSorteerVolgorde() {
        // Mocks omdat de builder alleen op historie werkt.
        PersoonHisVolledigView persoonDelta1 = maakViewVoorPersoon(persoon1);
        PersoonHisVolledigView persoonDelta2 = maakViewVoorPersoon(persoon2);
        PersoonHisVolledigView persoonDelta3 = maakViewVoorPersoon(persoon3);
        kennisgeving.setBijgehoudenPersonen(Arrays.asList(persoonDelta1, persoonDelta2, persoonDelta3));

        assertEquals(persoonDelta1, kennisgeving.getBijgehoudenPersonen().get(2));
        assertEquals(persoonDelta2, kennisgeving.getBijgehoudenPersonen().get(1));
        assertEquals(persoonDelta3, kennisgeving.getBijgehoudenPersonen().get(0));
    }

    /**
     * Test predicate.
     */
    private class NietIngeschevenePredicate implements Predicate {

        @Override
        public boolean evaluate(final Object object) {
            PersoonHisVolledig persoon = (PersoonHisVolledig) object;

            return persoon.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE;
        }
    }

    private PersoonHisVolledigView maakViewVoorPersoon(final PersoonHisVolledig persoon) {
        return new PersoonHisVolledigView(persoon, new AdministratieveHandelingDeltaPredikaat(admHndId), tsreg);
    }
}
