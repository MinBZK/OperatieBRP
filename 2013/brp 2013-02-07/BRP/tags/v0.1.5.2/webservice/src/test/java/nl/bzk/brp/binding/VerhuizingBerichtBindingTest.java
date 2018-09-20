/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import org.jibx.runtime.JiBXException;
import org.junit.Test;


public class VerhuizingBerichtBindingTest extends AbstractBindingInTest<VerhuizingBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MAX.xml");
        valideerOutputTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        //Check stuurgegevens
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("organisatie", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("applicatie", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("referentienummer", berichtStuurgegevens.getReferentienummer());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertEquals(1, bericht.getBrpActies().size());
        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(20120101, actie.getDatumAanvangGeldigheid().intValue());
        Assert.assertEquals(SoortActie.VERHUIZING, actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(1, actie.getRootObjecten().size());
        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertTrue(rootObject instanceof Persoon);
        Persoon verhuizende = (Persoon) rootObject;

        Assert.assertEquals("123456789", verhuizende.getIdentificatienummers().getBurgerservicenummer());
        // Komt niet meer voor in XSD
        //Assert.assertEquals("123456789", verhuizende.getIdentificatienummers().getAdministratienummer());

        PersoonAdres persoonAdres = (PersoonAdres) verhuizende.getAdressen().toArray()[0];
        Assert.assertEquals(FunctieAdres.WOONADRES, persoonAdres.getSoort());
        Assert.assertEquals("B", persoonAdres.getRedenWijziging().getCode());
        Assert.assertEquals("O", persoonAdres.getAangeverAdreshouding().getCode());
        Assert.assertEquals(Integer.valueOf(20120101), persoonAdres.getDatumAanvangAdreshouding());
        Assert.assertEquals("Blokhut", persoonAdres.getAdresseerbaarObject());
        Assert.assertEquals("nummerAand", persoonAdres.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals("0001", persoonAdres.getGemeente().getGemeentecode());
        Assert.assertEquals("openbare ruimte", persoonAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("op ruimte", persoonAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("schilderswijk", persoonAdres.getGemeentedeel());
        Assert.assertEquals("15", persoonAdres.getHuisnummer());
        Assert.assertEquals("A", persoonAdres.getHuisletter());
        Assert.assertEquals("III", persoonAdres.getHuisnummertoevoeging());
        Assert.assertEquals("1066DG", persoonAdres.getPostcode());
        Assert.assertEquals("1001", persoonAdres.getWoonplaats().getWoonplaatscode());
        Assert.assertEquals("OP", persoonAdres.getLocatieTovAdres());
        // Komt niet meer voor in XSD
        //Assert.assertEquals("zandberg", persoonAdres.getLocatieOmschrijving());
        Assert.assertEquals("9076", persoonAdres.getLand().getLandcode());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MIN.xml");
        valideerOutputTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertNotNull(bericht.getBrpActies());

        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertNotNull(actie);
        Assert.assertNotNull(actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());

        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertNotNull(rootObject);
        Assert.assertTrue(rootObject instanceof Persoon);
    }

    @Test
    public void testBindingMinNilBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MIN-nil.xml");
        valideerOutputTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertNotNull(bericht.getBrpActies());

        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertNotNull(actie);
        Assert.assertNotNull(actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());

        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertNotNull(rootObject);
        Assert.assertTrue(rootObject instanceof Persoon);
    }

    @Override
    public Class<VerhuizingBericht> getBindingClass() {
        return VerhuizingBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Migratie_Berichten.xsd";
    }
}
