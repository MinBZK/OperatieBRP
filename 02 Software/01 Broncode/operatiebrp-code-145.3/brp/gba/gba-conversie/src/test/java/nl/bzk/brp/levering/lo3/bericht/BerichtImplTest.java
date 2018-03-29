/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.format.Formatter;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private Persoonslijst            persoon;
    private AdministratieveHandeling administratieveHandeling;

    private BerichtImpl subject;

    @Before
    public void setup() throws ReflectiveOperationException {

        final ZonedDateTime tsReg = ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        administratieveHandeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
            .maakAdministratieveHandeling(-21L, "000034", tsReg, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
            .metObject(TestVerantwoording.maakActieBuilder(-21L, SoortActie.BEEINDIGING_VOORNAAM, tsReg, "000001", null)
            ).build());
        final Actie actieInhoud = administratieveHandeling.getActies().iterator().next();

        final MetaObject.Builder basispersoon = MetaObject.maakBuilder();
        basispersoon.metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(4645);

        final Set<AdministratieveHandeling> administratieveHandelingen = new HashSet<>();
        administratieveHandelingen.add(administratieveHandeling);
        persoon = new Persoonslijst(basispersoon.build(), 0L);

        subject = new BerichtImpl(SoortBericht.AG01, converteerder, filter, formatter, persoon, null, administratieveHandeling, null);
    }

    @Test
    public void test() {
        final ConversieCache conversieCache = new ConversieCache();
        final List<Lo3CategorieWaarde> ongefilterdeCategorieen = new ArrayList<>();
        final List<String> rubrieken = new ArrayList<>();
        final List<Lo3CategorieWaarde> gefilterdeCategorieen = new ArrayList<>();

        Mockito.when(converteerder.converteer(persoon, null, administratieveHandeling, null, conversieCache)).thenReturn(ongefilterdeCategorieen);
        Mockito.when(
            filter.filter(
                Matchers.eq(persoon),
                Matchers.anyListOf(Stapel.class),
                Matchers.eq(administratieveHandeling),
                Matchers.eq(null),
                Matchers.same(ongefilterdeCategorieen),
                Matchers.same(rubrieken)))
               .thenReturn(gefilterdeCategorieen);
        Mockito.when(
            formatter.maakPlatteTekst(
                Matchers.eq(persoon),
                Matchers.eq(null),
                Matchers.same(ongefilterdeCategorieen),
                Matchers.same(gefilterdeCategorieen)))
               .thenReturn(INHOUD);

        // Assert.assertEquals(SoortSynchronisatie.VOLLEDIGBERICHT, subject.geefSoortSynchronisatie());
        assertEquals("Ag01", subject.geefSoortBericht());

        subject.converteerNaarLo3(conversieCache);
        Assert.assertFalse(subject.filterRubrieken(rubrieken));
        assertEquals(INHOUD, subject.maakUitgaandBericht());
    }

    @Test
    public void getPersoonsgegevens() {
        assertThat(subject.getPersoonsgegevens(), instanceOf(Persoonslijst.class));
    }

    @Test
    public void getSynchronisatie() {
        assertEquals(SoortSynchronisatie.VOLLEDIG_BERICHT, subject.getSoortSynchronisatie());
    }
}
