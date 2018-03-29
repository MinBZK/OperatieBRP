/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import org.junit.Test;

/**
 * Testen voor {@link ActieElementSorter}.
 */
public class ActieElementSorterTest {

    @Test
    public void testSort1() {
        assertSortering(maakTestLijst1(), Arrays.asList(1, 7, 3, 4, 2, 6, 8, 5));
    }

    @Test
    public void testSort2() {
        assertSortering(maakTestLijst2(), Arrays.asList(1, 2, 4, 3));
    }

    @Test
    public void testSort3() {
        assertSortering(maakTestLijst3(), Arrays.asList(1, 4, 2, 3));
    }

    @Test
    public void testSort4() {
        assertSortering(maakTestLijst4(), Arrays.asList(1, 2, 3));
    }

    @Test
    public void testSortEmptyList() {
        assertSortering(Collections.emptyList(), Collections.emptyList());
    }

    private static void assertSortering(final List<SimpleActie> teSorterenActies, final List<Integer> verwachteIdVolgorde) {
        //execute
        final List<SimpleActie> gesorteerdeLijst = ActieElementSorter.sort(teSorterenActies);
        //verify
        assertVolgorde(verwachteIdVolgorde, gesorteerdeLijst);
    }

    private static void assertVolgorde(final List<Integer> ids, final List<SimpleActie> acties) {
        assertEquals(ids, extractIds(acties));
    }

    private static List<Integer> extractIds(final List<SimpleActie> acties) {
        final List<Integer> results = new ArrayList<>(acties.size());
        for (final SimpleActie actieElement : acties) {
            results.add(actieElement.getId());
        }
        return results;
    }

    private static List<SimpleActie> maakTestLijst1() {
        final List<SimpleActie> results = new ArrayList<>();
        results.add(maakAanvangActie(1, 20070101));
        results.add(maakAanvangActie(2, 20080101));
        results.add(maakAanvangActie(3, null));
        results.add(maakAanvangActie(4, null));
        results.add(maakAanvangActie(5, 20100101));
        results.add(maakAanvangActie(6, 20090101));
        results.add(maakAanvangActie(7, 20060101));
        results.add(maakEindeActie(8, 20100101));
        return results;
    }

    private static List<SimpleActie> maakTestLijst2() {
        final List<SimpleActie> results = new ArrayList<>();
        results.add(maakAanvangActie(1, 20160104));
        results.add(maakAanvangActie(2, 20160102));
        results.add(maakAanvangActie(3, 20160104));
        results.add(maakEindeActie(4, 20160104));
        return results;
    }

    private static List<SimpleActie> maakTestLijst3() {
        final List<SimpleActie> results = new ArrayList<>();
        results.add(maakEindeActie(1, 20170101));
        results.add(maakAanvangActie(2, 20170101));
        results.add(maakAanvangActie(3, null));
        results.add(maakAanvangActie(4, 20160101));
        return results;
    }

    private static List<SimpleActie> maakTestLijst4() {
        final List<SimpleActie> results = new ArrayList<>();
        results.add(maakAanvangActie(1, null));
        results.add(maakAanvangActie(2, null));
        results.add(maakAanvangActie(3, null));
        return results;
    }

    private static SimpleActie maakAanvangActie(final int id, final Integer datum) {
        return new SimpleActie(id, maakDatumElement(datum), null);
    }

    private static SimpleActie maakEindeActie(final int id, final Integer datum) {
        return new SimpleActie(id, null, maakDatumElement(datum));
    }

    private static DatumElement maakDatumElement(final Integer datum) {
        if (datum == null) {
            return null;
        }
        return new DatumElement(datum);
    }

    private final static class SimpleActie extends AbstractActieElement {

        private int id;

        /**
         * Maakt een SimpleActie object.
         * @param id id
         * @param datumAanvangGeldigheid datum aanvang geldigheid
         * @param datumEindeGeldigheid de datum einde geldigheid
         */
        public SimpleActie(final int id, final DatumElement datumAanvangGeldigheid, final DatumElement datumEindeGeldigheid) {
            super(new AttributenBuilder().objecttype("Actie").build(), datumAanvangGeldigheid, datumEindeGeldigheid, null);
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public SoortActie getSoortActie() {
            return null;
        }

        @Override
        public BRPActie verwerk(BijhoudingVerzoekBericht bericht, AdministratieveHandeling administratieveHandeling) {
            return null;
        }

        @Override
        public List<BijhoudingPersoon> getHoofdPersonen() {
            return Collections.emptyList();
        }

        @Override
        public List<PersoonElement> getPersoonElementen() {
            return Collections.emptyList();
        }

        @Override
        public DatumElement getPeilDatum() {
            return null;
        }

        @Override
        protected List<MeldingElement> valideerSpecifiekeInhoud() {
            return Collections.emptyList();
        }
    }

}
