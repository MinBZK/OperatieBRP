/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;

import junit.framework.Assert;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import org.jibx.runtime.JiBXException;
import org.junit.Test;


public class VerhuizingBerichtBindingTest extends AbstractBindingInIntegratieTest<VerhuizingBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MAX.xml");
        valideerTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        // Check stuurgegevens
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("organisatie", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("applicatie", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("referentienummer", berichtStuurgegevens.getReferentienummer());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertEquals(1, bericht.getBrpActies().size());
        ActieBericht actie = (ActieBericht) bericht.getBrpActies().get(0);
        Assert.assertEquals(20120101, actie.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(SoortActie.VERHUIZING, actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(1, actie.getRootObjecten().size());
        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertTrue(rootObject instanceof PersoonBericht);
        PersoonBericht verhuizende = (PersoonBericht) rootObject;

        Assert.assertEquals("123456789", verhuizende.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        // Komt niet meer voor in XSD
        // Assert.assertEquals("123456789", verhuizende.getIdentificatienummers().getANummer());

        PersoonAdresBericht persoonAdres = (PersoonAdresBericht) verhuizende.getAdressen().toArray()[0];
        Assert.assertEquals(FunctieAdres.WOONADRES, persoonAdres.getGegevens().getSoort());
        Assert.assertEquals("B", persoonAdres.getGegevens().getRedenWijzigingAdresCode().getWaarde());
        Assert.assertEquals("O", persoonAdres.getGegevens().getAangeverAdreshouding().getCode());
        Assert.assertEquals(Integer.valueOf(20120101), persoonAdres.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals("Blokhut", persoonAdres.getGegevens().getAanduidingAdresseerbaarObject().getWaarde());
        Assert.assertEquals("nummerAand", persoonAdres.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("0001", persoonAdres.getGegevens().getGemeentecode().toString());
        Assert.assertEquals("openbare ruimte", persoonAdres.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("op ruimte", persoonAdres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("schilderswijk", persoonAdres.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals(Integer.valueOf(15), persoonAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("A", persoonAdres.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("III", persoonAdres.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1066DG", persoonAdres.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("1001", persoonAdres.getGegevens().getCode().toString());
        Assert.assertEquals("OP", persoonAdres.getGegevens().getLocatietovAdres().getWaarde());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MIN.xml");
        valideerTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertNotNull(bericht.getBrpActies());

        ActieBericht actie = (ActieBericht) bericht.getBrpActies().get(0);
        Assert.assertNotNull(actie);
        Assert.assertNotNull(actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());

        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertNotNull(rootObject);
        Assert.assertTrue(rootObject instanceof PersoonBericht);
    }

    @Test
    public void testBindingMinNilBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MIN-nil.xml");
        valideerTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertNotNull(bericht.getBrpActies());

        ActieBericht actie = (ActieBericht) bericht.getBrpActies().get(0);
        Assert.assertNotNull(actie);
        Assert.assertNotNull(actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());

        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertNotNull(rootObject);
        Assert.assertTrue(rootObject instanceof PersoonBericht);
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
