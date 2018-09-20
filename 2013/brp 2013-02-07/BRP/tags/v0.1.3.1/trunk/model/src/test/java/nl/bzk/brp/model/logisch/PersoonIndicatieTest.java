/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de {@link PersoonIndicatie} class. */
public class PersoonIndicatieTest {

    private Persoon persoon1;
    private Persoon persoon2;

    @Test
    public void testCompareGelijkAlsAlleenSoortGelijkIs() {
        PersoonIndicatie indicatie1 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, Boolean.FALSE, persoon1);
        PersoonIndicatie indicatie2 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, Boolean.TRUE, persoon2);

        Assert.assertEquals(0, indicatie1.compareTo(indicatie2));
        Assert.assertEquals(0, indicatie2.compareTo(indicatie1));
    }

    @Test
    public void testCompareNietGelijkAlsSoortVerschiltMaarRestNiet() {
        PersoonIndicatie indicatie1 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, Boolean.FALSE, persoon1);
        PersoonIndicatie indicatie2 =
            bouwPersoonIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, Boolean.FALSE, persoon1);

        Assert.assertFalse(indicatie1.compareTo(indicatie2) == 0);
        Assert.assertFalse(indicatie2.compareTo(indicatie1) == 0);
    }

    @Test
    public void testCompareGelijkVoorZelfde() {
        PersoonIndicatie indicatie1 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, Boolean.FALSE, persoon1);

        Assert.assertEquals(0, indicatie1.compareTo(indicatie1));
    }

    @Test
    public void testVolgordeOpBasisVanCompare() {
        SortedSet<PersoonIndicatie> indicaties = new TreeSet<PersoonIndicatie>();
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.STATENLOOS, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.GEPRIVILEGIEERDE, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.DERDE_HEEFT_GEZAG, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT, false, persoon1));
        indicaties.add(bouwPersoonIndicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT, false, persoon1));

        Object[] gesorteerdeIndicaties = indicaties.toArray();

        Assert.assertEquals(SoortIndicatie.DERDE_HEEFT_GEZAG, ((PersoonIndicatie) gesorteerdeIndicaties[0]).getSoort());
        Assert.assertEquals(SoortIndicatie.ONDER_CURATELE, ((PersoonIndicatie) gesorteerdeIndicaties[1]).getSoort());
        Assert.assertEquals(SoortIndicatie.VERSTREKKINGSBEPERKING,
            ((PersoonIndicatie) gesorteerdeIndicaties[2]).getSoort());
        Assert.assertEquals(SoortIndicatie.GEPRIVILEGIEERDE, ((PersoonIndicatie) gesorteerdeIndicaties[3]).getSoort());
        Assert.assertEquals(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER,
            ((PersoonIndicatie) gesorteerdeIndicaties[4]).getSoort());
        Assert.assertEquals(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER,
            ((PersoonIndicatie) gesorteerdeIndicaties[5]).getSoort());
        Assert.assertEquals(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT,
            ((PersoonIndicatie) gesorteerdeIndicaties[6]).getSoort());
        Assert.assertEquals(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT,
            ((PersoonIndicatie) gesorteerdeIndicaties[7]).getSoort());
        Assert.assertEquals(SoortIndicatie.STATENLOOS, ((PersoonIndicatie) gesorteerdeIndicaties[8]).getSoort());
    }

    @Test
    public void testEquals() {
        PersoonIndicatie indicatie1 = bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, false, persoon1);
        PersoonIndicatie indicatie2 = bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, true, persoon1);
        PersoonIndicatie indicatie3 = bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, false, persoon2);
        PersoonIndicatie indicatie4 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, false, persoon1);
        PersoonIndicatie indicatie5 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, true, persoon2);

        Assert.assertFalse(indicatie1.equals(null));
        Assert.assertFalse(indicatie1.equals(new Object()));
        Assert.assertTrue(indicatie1.equals(indicatie1));

        // Test met alleen andere waarde: gelijk
        Assert.assertTrue(indicatie1.equals(indicatie2));
        Assert.assertTrue(indicatie2.equals(indicatie1));

        // Test met alleen ander persoon: niet gelijk
        Assert.assertFalse(indicatie1.equals(indicatie3));
        Assert.assertFalse(indicatie3.equals(indicatie1));

        // Test met alleen andere soort: niet gelijk
        Assert.assertFalse(indicatie1.equals(indicatie4));
        Assert.assertFalse(indicatie4.equals(indicatie1));

        // Test met alles anders: niet gelijk
        Assert.assertFalse(indicatie1.equals(indicatie5));
        Assert.assertFalse(indicatie5.equals(indicatie1));
    }

    @Test
    public void testHashCode() {
        PersoonIndicatie indicatie1 = bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, false, persoon1);
        PersoonIndicatie indicatie2 = bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, true, persoon1);
        PersoonIndicatie indicatie3 = bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, false, persoon2);
        PersoonIndicatie indicatie4 = bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, false, persoon1);

        Assert.assertTrue(indicatie1.hashCode() == indicatie1.hashCode());
        Assert.assertTrue(indicatie1.hashCode() == indicatie2.hashCode());
        Assert.assertFalse(indicatie1.hashCode() == indicatie3.hashCode());
        Assert.assertFalse(indicatie1.hashCode() == indicatie4.hashCode());
    }

    @Before
    public void init() {
        persoon1 = new Persoon();
        persoon2 = new Persoon();
    }

    private PersoonIndicatie bouwPersoonIndicatie(final SoortIndicatie soortIndicatie, final Boolean waarde,
        final Persoon persoon)
    {
        PersoonIndicatie persoonIndicatie = new PersoonIndicatie();
        persoonIndicatie.setSoort(soortIndicatie);
        persoonIndicatie.setWaarde(waarde);
        persoonIndicatie.setPersoon(persoon);
        return persoonIndicatie;
    }
}
