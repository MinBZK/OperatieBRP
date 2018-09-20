/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.binding.AbstractBindingTest;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import org.jibx.runtime.JiBXException;
import org.junit.Test;


public class BRPBerichtBindingTest extends AbstractBindingTest<BijhoudingsBericht> {

    @Test
    public void testBRPBerichtBinding() throws JiBXException, IOException {
        final String xml = leesBestand("verhuizing.xml");

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        BRPActie brpActie =  bericht.getBrpActies().get(0);
        Assert.assertEquals(SoortActie.VERHUIZING, brpActie.getSoort());
        Assert.assertEquals(Integer.valueOf(1), brpActie.getPartij().getId());
        Assert.assertEquals(Integer.valueOf(12445555), brpActie.getDatumAanvangGeldigheid());

        Persoon persoon = (Persoon) brpActie.getRootObjecten().get(0);
        Assert.assertEquals("123", persoon.getIdentificatienummers().getBurgerservicenummer());

        PersoonAdres persoonAdres = (PersoonAdres) persoon.getAdressen().toArray()[0];
        Assert.assertEquals(FunctieAdres.WOONADRES, persoonAdres.getSoort());
        Assert.assertEquals("1", persoonAdres.getRedenWijziging().getCode());
        Assert.assertEquals("2", persoonAdres.getAangeverAdreshouding().getCode());
        Assert.assertEquals(Integer.valueOf(12344), persoonAdres.getDatumAanvangAdreshouding());
        Assert.assertEquals("abcd", persoonAdres.getAdresseerbaarObject());
        Assert.assertEquals("aand", persoonAdres.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals("1", persoonAdres.getGemeente().getGemeentecode());
        Assert.assertEquals("1", persoonAdres.getWoonplaats().getWoonplaatscode());
        Assert.assertEquals("naamobr", persoonAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("afnaamobr", persoonAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("3", persoonAdres.getHuisnummer());
        Assert.assertEquals("a", persoonAdres.getHuisletter());
        Assert.assertEquals("b", persoonAdres.getHuisnummertoevoeging());
        Assert.assertEquals("1000ab", persoonAdres.getPostcode());
    }

    @Override
    protected Class<BijhoudingsBericht> getBindingClass() {
        return BijhoudingsBericht.class;
    }
}
