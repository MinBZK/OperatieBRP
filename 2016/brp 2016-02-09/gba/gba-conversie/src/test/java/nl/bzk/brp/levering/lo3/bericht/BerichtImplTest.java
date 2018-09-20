/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.format.Formatter;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BerichtImplTest {

    private static final String INHOUD = "inhoud";

    @Mock
    private Converteerder converteerder;
    @Mock
    private Filter filter;
    @Mock
    private Formatter formatter;
    @Mock
    private PersoonHisVolledig persoon;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private AdministratieveHandelingModel administratieveHandeling;

    private BerichtImpl subject;

    @Before
    public void setup() {
        subject = new BerichtImpl(SoortBericht.AG01, converteerder, filter, formatter, persoon, null, administratieveHandeling);
    }

    @Test
    public void test() {
        final ConversieCache conversieCache = new ConversieCache();
        final List<Lo3CategorieWaarde> ongefilterdeCategorieen = new ArrayList<>();
        final List<String> rubrieken = new ArrayList<>();
        final List<Lo3CategorieWaarde> gefilterdeCategorieen = new ArrayList<>();

        Mockito.when(converteerder.converteer(persoon, null, administratieveHandeling, conversieCache)).thenReturn(ongefilterdeCategorieen);
        Mockito.when(
            filter.filter(
                Matchers.eq(persoon),
                Matchers.isNull(List.class),
                Matchers.eq(administratieveHandeling),
                Matchers.same(ongefilterdeCategorieen),
                Matchers.same(rubrieken))).thenReturn(gefilterdeCategorieen);
        Mockito.when(formatter.maakPlatteTekst(Matchers.eq(persoon), Matchers.same(ongefilterdeCategorieen), Matchers.same(gefilterdeCategorieen)))
               .thenReturn(INHOUD);

        Assert.assertEquals(SoortSynchronisatie.VOLLEDIGBERICHT, subject.geefSoortSynchronisatie());
        Assert.assertEquals("Ag01", subject.geefSoortBericht());

        subject.converteerNaarLo3(conversieCache);
        Assert.assertFalse(subject.filterRubrieken(rubrieken));
        Assert.assertEquals(INHOUD, subject.maakUitgaandBericht());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testJibxName() {
        subject.JiBX_getName();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMarshal() throws JiBXException {
        subject.marshal(null);
    }
}
