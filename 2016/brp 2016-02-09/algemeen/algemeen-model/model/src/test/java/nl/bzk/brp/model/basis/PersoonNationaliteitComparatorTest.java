/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.SortedSet;
import java.util.TreeSet;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitComparator;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;
import org.mockito.Mockito;


public class PersoonNationaliteitComparatorTest {

    @Test
    public void testCompare() {
        final PersoonNationaliteitHisVolledig persoonNationaliteitNL = Mockito.mock(PersoonNationaliteitHisVolledig.class);
        final NationaliteitAttribuut nationaliteitNL = Mockito.mock(NationaliteitAttribuut.class);
        Mockito.when(nationaliteitNL.getWaarde()).thenReturn(
                StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde());

        final PersoonNationaliteitHisVolledig persoonNationaliteitCH = Mockito.mock(PersoonNationaliteitHisVolledig.class);
        final NationaliteitAttribuut nationaliteitCH = Mockito.mock(NationaliteitAttribuut.class);
        Mockito.when(nationaliteitCH.getWaarde()).thenReturn(
                StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS.getWaarde());

        final PersoonNationaliteitHisVolledig persoonNationaliteitMA = Mockito.mock(PersoonNationaliteitHisVolledig.class);
        final NationaliteitAttribuut nationaliteitMA = Mockito.mock(NationaliteitAttribuut.class);
        Mockito.when(nationaliteitMA.getWaarde())
                .thenReturn(StatischeObjecttypeBuilder.NATIONALITEIT_TURKS.getWaarde());

        Mockito.when(persoonNationaliteitNL.getNationaliteit()).thenReturn(nationaliteitNL);
        Mockito.when(persoonNationaliteitCH.getNationaliteit()).thenReturn(nationaliteitCH);
        Mockito.when(persoonNationaliteitMA.getNationaliteit()).thenReturn(nationaliteitMA);

        final SortedSet<PersoonNationaliteitHisVolledig> setje = new TreeSet<>(new PersoonNationaliteitComparator());
        setje.add(persoonNationaliteitMA);
        setje.add(persoonNationaliteitCH);
        setje.add(persoonNationaliteitNL);

        assertThat(setje, contains(persoonNationaliteitNL, persoonNationaliteitCH, persoonNationaliteitMA));
    }
}
