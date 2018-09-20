/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.operationeel.kern.AbstractHisPersoonIndicatieMaterieleHistorieModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class MaterieleHistorieEntiteitComparatorTest {

    private MaterieleHistorieEntiteitComparator<AbstractHisPersoonIndicatieMaterieleHistorieModel> comparator;


    private enum Vergelijking {
        GELIJK, KLEINER, GROTER
    }


    ;

    // KLEIN is als dag / datum tijd een latere datum dan GROOT, omdat recentere data
    // bovenaan moet komen te staan (dus kleiner is in de sortering).
    private static final int KLEIN = 20140505;
    private static final int GROOT = 20140504;

    @Before
    public void init() {
        comparator = new MaterieleHistorieEntiteitComparator<>();
    }

    /*
     * LOSSE TESTS OP VERGELIJKING VAN DE VERSCHILLENDE ATTRIBUTEN.
     */

    @Test
    public void testCompareVerwerkingsSoort() {
        Assert.assertEquals(Vergelijking.KLEINER,
                vergelijkVerwerkingsSoort(Verwerkingssoort.IDENTIFICATIE, Verwerkingssoort.TOEVOEGING));
        Assert.assertEquals(Vergelijking.KLEINER,
                vergelijkVerwerkingsSoort(Verwerkingssoort.TOEVOEGING, Verwerkingssoort.WIJZIGING));
        Assert.assertEquals(Vergelijking.KLEINER,
                vergelijkVerwerkingsSoort(Verwerkingssoort.WIJZIGING, Verwerkingssoort.VERVAL));
        Assert.assertEquals(Vergelijking.KLEINER,
                vergelijkVerwerkingsSoort(Verwerkingssoort.VERVAL, Verwerkingssoort.VERWIJDERING));
        Assert.assertEquals(Vergelijking.GELIJK,
                vergelijkVerwerkingsSoort(Verwerkingssoort.VERWIJDERING, Verwerkingssoort.VERWIJDERING));
    }

    @Test
    public void testCompareDatumAanvangGeldigheid() {
        Assert.assertEquals(Vergelijking.GROTER, vergelijkDatumAanvangGeldigheid(GROOT, KLEIN));
        Assert.assertEquals(Vergelijking.KLEINER, vergelijkDatumAanvangGeldigheid(KLEIN, GROOT));
        Assert.assertEquals(Vergelijking.GELIJK, vergelijkDatumAanvangGeldigheid(GROOT, GROOT));
    }

    @Test
    public void testCompareDatumEindeGeldigheid() {
        Assert.assertEquals(Vergelijking.GROTER, vergelijkDatumEindeGeldigheid(GROOT, KLEIN));
        Assert.assertEquals(Vergelijking.KLEINER, vergelijkDatumEindeGeldigheid(KLEIN, GROOT));
        Assert.assertEquals(Vergelijking.GELIJK, vergelijkDatumEindeGeldigheid(GROOT, GROOT));
    }

    @Test
    public void testCompareDatumTijdRegistratie() {
        Assert.assertEquals(Vergelijking.GROTER, vergelijkDatumTijdRegistratie(GROOT, KLEIN));
        Assert.assertEquals(Vergelijking.KLEINER, vergelijkDatumTijdRegistratie(KLEIN, GROOT));
        Assert.assertEquals(Vergelijking.GELIJK, vergelijkDatumTijdRegistratie(GROOT, GROOT));
    }

    @Test
    public void testCompareDatumTijdVerval() {
        Assert.assertEquals(Vergelijking.GROTER, vergelijkDatumTijdVerval(GROOT, KLEIN));
        Assert.assertEquals(Vergelijking.KLEINER, vergelijkDatumTijdVerval(KLEIN, GROOT));
        Assert.assertEquals(Vergelijking.GELIJK, vergelijkDatumTijdVerval(GROOT, GROOT));
    }

    /*
     * TESTS OP DE PRIORITEIT IN VERGELIJKING VAN DE VERSCHILLENDE ATTRIBUTEN.
     */

    @Test
    public void testVerwerkingsSoortGaatVoorVerval() {
        Assert.assertEquals(
                Vergelijking.GROTER,
                vergelijk(maakHisRecord(Verwerkingssoort.TOEVOEGING, KLEIN, KLEIN, KLEIN, KLEIN),
                        maakHisRecord(Verwerkingssoort.IDENTIFICATIE, GROOT, GROOT, GROOT, GROOT)));
    }

    @Test
    public void testVervalGaatVoorDatumAanvangGeldigheid() {
        Assert.assertEquals(
                Vergelijking.GROTER,
                vergelijk(maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, KLEIN, KLEIN, GROOT),
                        maakHisRecord(Verwerkingssoort.IDENTIFICATIE, GROOT, GROOT, GROOT, KLEIN)));
    }

    @Test
    public void testDatumAanvangGeldigheidGaatVoorDatumEindeGeldigheid() {
        Assert.assertEquals(
                Vergelijking.GROTER,
                vergelijk(maakHisRecord(Verwerkingssoort.IDENTIFICATIE, GROOT, KLEIN, KLEIN, KLEIN),
                        maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, GROOT, GROOT, KLEIN)));
    }

    @Test
    public void testDatumEindeGeldigheidGaatVoorRegistratie() {
        Assert.assertEquals(
                Vergelijking.GROTER,
                vergelijk(maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, GROOT, KLEIN, KLEIN),
                        maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, KLEIN, GROOT, KLEIN)));
    }

    @Test
    public void testRegistratieAlsLaatste() {
        Assert.assertEquals(
                Vergelijking.GROTER,
                vergelijk(maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, KLEIN, GROOT, KLEIN),
                        maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, KLEIN, KLEIN, KLEIN)));
    }

    @Test
    public void testAllesIsGelijk() {
        Assert.assertEquals(
                Vergelijking.GELIJK,
                vergelijk(maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, KLEIN, KLEIN, KLEIN),
                        maakHisRecord(Verwerkingssoort.IDENTIFICATIE, KLEIN, KLEIN, KLEIN, KLEIN)));
    }

    private Vergelijking vergelijk(final AbstractHisPersoonIndicatieMaterieleHistorieModel his1,
            final AbstractHisPersoonIndicatieMaterieleHistorieModel his2)
    {
        return compareToVergelijking(comparator.compare(his1, his2));
    }

    private Vergelijking vergelijkVerwerkingsSoort(final Verwerkingssoort verwerkingsSoort1,
            final Verwerkingssoort verwerkingsSoort2)
    {
        return vergelijk(maakHisRecord(verwerkingsSoort1, null, null, null, null),
                maakHisRecord(verwerkingsSoort2, null, null, null, null));
    }

    private Vergelijking vergelijkDatumAanvangGeldigheid(final int dag1, final int dag2) {
        return vergelijk(maakHisRecord(null, dag1, null, null, null), maakHisRecord(null, dag2, null, null, null));
    }

    private Vergelijking vergelijkDatumEindeGeldigheid(final int deg1, final int deg2) {
        return vergelijk(maakHisRecord(null, null, deg1, null, null), maakHisRecord(null, null, deg2, null, null));
    }

    private Vergelijking vergelijkDatumTijdRegistratie(final int tijd1, final int tijd2) {
        return vergelijk(maakHisRecord(null, null, null, tijd1, null), maakHisRecord(null, null, null, tijd2, null));
    }

    private Vergelijking vergelijkDatumTijdVerval(final int tijd1, final int tijd2) {
        return vergelijk(maakHisRecord(null, null, null, null, tijd1), maakHisRecord(null, null, null, null, tijd2));
    }

    public AbstractHisPersoonIndicatieMaterieleHistorieModel maakHisRecord(final Verwerkingssoort verwerkingsSoort,
            final Integer dag, final Integer deg, final Integer tijdstipRegistratie, final Integer tijdstipVerval)
    {
        final AbstractHisPersoonIndicatieMaterieleHistorieModel his =
                Mockito.mock(AbstractHisPersoonIndicatieMaterieleHistorieModel.class);
        Mockito.when(his.getVerwerkingssoort()).thenReturn(verwerkingsSoort);
        if (dag != null) {
            Mockito.when(his.getDatumAanvangGeldigheid()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(dag));
        }
        if (deg != null) {
            Mockito.when(his.getDatumEindeGeldigheid()).thenReturn(new DatumEvtDeelsOnbekendAttribuut(deg));
        }
        if (tijdstipRegistratie != null) {
            Mockito.when(his.getTijdstipRegistratie()).thenReturn(
                    new DatumTijdAttribuut(new DatumAttribuut(tijdstipRegistratie).toDate()));
        }
        if (tijdstipVerval != null) {
            Mockito.when(his.getDatumTijdVerval()).thenReturn(new DatumTijdAttribuut(new DatumAttribuut(tijdstipVerval).toDate()));
        }
        return his;
    }

    private Vergelijking compareToVergelijking(final int compare) {
        Vergelijking vergelijking;
        if (compare < 0) {
            vergelijking = Vergelijking.KLEINER;
        } else if (compare > 0) {
            vergelijking = Vergelijking.GROTER;
        } else {
            vergelijking = Vergelijking.GELIJK;
        }
        return vergelijking;
    }
}
