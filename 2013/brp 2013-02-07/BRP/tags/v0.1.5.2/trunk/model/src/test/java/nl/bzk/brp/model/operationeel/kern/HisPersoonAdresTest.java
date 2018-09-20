/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Date;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import org.junit.Test;

/** Unit test class voor de {@link HisPersoonAdres} class. */
public class HisPersoonAdresTest {

    @Test
    public void testClone() throws CloneNotSupportedException {
        HisPersoonAdres origineel = bouwNieuwHisPeroonAdres();
        HisPersoonAdres clone = origineel.clone();

        Assert.assertNotNull(clone);
        Assert.assertNotSame(origineel, clone);

        Assert.assertSame(origineel.getPersoonAdres(), clone.getPersoonAdres());
        Assert.assertSame(origineel.getLand(), clone.getLand());
        Assert.assertSame(origineel.getGemeente(), clone.getGemeente());
        Assert.assertSame(origineel.getWoonplaats(), clone.getWoonplaats());
        Assert.assertNull(clone.getId());
        Assert.assertNull(clone.getDatumTijdRegistratie());
        Assert.assertNull(clone.getDatumTijdVerval());

        Assert.assertEquals(origineel.getAdresseerbaarObject(), clone.getAdresseerbaarObject());
        Assert.assertEquals(origineel.getAfgekorteNaamOpenbareRuimte(), clone.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals(origineel.getBuitenlandsAdresRegel1(), clone.getBuitenlandsAdresRegel1());
        Assert.assertEquals(origineel.getBuitenlandsAdresRegel2(), clone.getBuitenlandsAdresRegel2());
        Assert.assertEquals(origineel.getBuitenlandsAdresRegel3(), clone.getBuitenlandsAdresRegel3());
        Assert.assertEquals(origineel.getBuitenlandsAdresRegel4(), clone.getBuitenlandsAdresRegel4());
        Assert.assertEquals(origineel.getBuitenlandsAdresRegel5(), clone.getBuitenlandsAdresRegel5());
        Assert.assertEquals(origineel.getBuitenlandsAdresRegel6(), clone.getBuitenlandsAdresRegel6());
        Assert.assertEquals(origineel.getDatumAanvangAdreshouding(), clone.getDatumAanvangAdreshouding());
        Assert.assertEquals(origineel.getDatumVertrekUitNederland(), clone.getDatumVertrekUitNederland());
        Assert.assertEquals(origineel.getGemeentedeel(), clone.getGemeentedeel());
        Assert.assertEquals(origineel.getHuisletter(), clone.getHuisletter());
        Assert.assertEquals(origineel.getHuisnummer(), clone.getHuisnummer());
        Assert.assertEquals(origineel.getHuisnummertoevoeging(), clone.getHuisnummertoevoeging());
        Assert.assertEquals(origineel.getIdentificatiecodeNummeraanduiding(),
            clone.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(origineel.getLocatieOmschrijving(), clone.getLocatieOmschrijving());
        Assert.assertEquals(origineel.getLocatietovAdres(), clone.getLocatietovAdres());
        Assert.assertEquals(origineel.getNaamOpenbareRuimte(), clone.getNaamOpenbareRuimte());
        Assert.assertEquals(origineel.getPostcode(), clone.getPostcode());
        Assert.assertEquals(origineel.getSoort(), clone.getSoort());
        Assert.assertEquals(origineel.getDatumAanvangGeldigheid(), clone.getDatumAanvangGeldigheid());
        Assert.assertEquals(origineel.getDatumEindeGeldigheid(), clone.getDatumEindeGeldigheid());
    }

    /**
     * Retourneert een volledig ingevuld {@link HisPersoonAdres} instantie.
     *
     * @return een volledig ingevuld {@link HisPersoonAdres} instantie.
     */
    private HisPersoonAdres bouwNieuwHisPeroonAdres() {
        HisPersoonAdres adres = new HisPersoonAdres();
        adres.setAdresseerbaarObject("Adresseerbaar Object");
        adres.setAfgekorteNaamOpenbareRuimte("Afgekorte nor");
        adres.setBuitenlandsAdresRegel1("regel1");
        adres.setBuitenlandsAdresRegel2("regel2");
        adres.setBuitenlandsAdresRegel3("regel3");
        adres.setBuitenlandsAdresRegel4("regel4");
        adres.setBuitenlandsAdresRegel5("regel5");
        adres.setBuitenlandsAdresRegel6("regel6");
        adres.setDatumAanvangAdreshouding(20100902);
        adres.setDatumVertrekUitNederland(20110209);
        adres.setWoonplaats(bouwNieuwePlaats());
        adres.setGemeente(bouwNieuweGemeente());
        adres.setGemeentedeel("gemeente deel");
        adres.setHuisletter("a");
        adres.setHuisnummer("11");
        adres.setId(2L);
        adres.setIdentificatiecodeNummeraanduiding("123456");
        adres.setHuisnummertoevoeging("boven");
        adres.setLand(bouwNieuwLand());
        adres.setLocatieOmschrijving("Locatie omschr");
        adres.setLocatietovAdres("rechts");
        adres.setNaamOpenbareRuimte("nor");
        adres.setPersoonAdres(bouwNieuwPersoonAdres());
        adres.setPostcode("1234AA");
        adres.setSoort(FunctieAdres.WOONADRES);
        adres.setDatumAanvangGeldigheid(20091123);
        adres.setDatumEindeGeldigheid(20120314);
        adres.setDatumTijdRegistratie(new Date());
        adres.setDatumTijdVerval(new Date());
        return adres;
    }

    /**
     * Retourneert een lege {@link Partij} instantie.
     *
     * @return een lege {@link Partij} instantie.
     */
    private Partij bouwNieuweGemeente() {
        Partij gemeente = new Partij();
        return gemeente;
    }

    /**
     * Retourneert een lege {@link Land} instantie.
     *
     * @return een lege {@link Land} instantie.
     */
    private Land bouwNieuwLand() {
        Land land = new Land();
        return land;
    }

    /**
     * Retourneert een lege {@link Plaats} instantie.
     *
     * @return een lege {@link Plaats} instantie.
     */
    private Plaats bouwNieuwePlaats() {
        Plaats plaats = new Plaats();
        return plaats;
    }

    /**
     * Retourneert een lege {@link PersistentPersoonAdres} instantie.
     *
     * @return een lege {@link PersistentPersoonAdres} instantie.
     */
    private PersistentPersoonAdres bouwNieuwPersoonAdres() {
        PersistentPersoonAdres adres = new PersistentPersoonAdres();
        return adres;
    }
}
