/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;

import junit.framework.Assert;
import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import org.jibx.runtime.JiBXException;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class VerhuizingBerichtBindingTest extends AbstractBindingInIntegratieTest<VerhuizingBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MAX.xml");
        valideerTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        // Check stuurgegevens
        BerichtStuurgegevensGroep berichtStuurgegevens = bericht.getStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("organisatie", berichtStuurgegevens.getOrganisatie().getWaarde());
        Assert.assertEquals("applicatie", berichtStuurgegevens.getApplicatie().getWaarde());
        Assert.assertEquals("referentienummer", berichtStuurgegevens.getReferentienummer().getWaarde());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertEquals(1, bericht.getAdministratieveHandeling().getActies().size());
        ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertEquals(20120101, actie.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(SoortActie.REGISTRATIE_ADRES, actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(1, actie.getRootObjecten().size());
        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertTrue(rootObject instanceof PersoonBericht);
        PersoonBericht verhuizende = (PersoonBericht) rootObject;

        Assert.assertEquals("123456789", verhuizende.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        // Komt niet meer voor in XSD
        // Assert.assertEquals("123456789", verhuizende.getIdentificatienummers().getAdministratienummer());

        PersoonAdresBericht persoonAdres = (PersoonAdresBericht) verhuizende.getAdressen().toArray()[0];
        Assert.assertEquals(FunctieAdres.WOONADRES, persoonAdres.getStandaard().getSoort());
        Assert.assertEquals("B", persoonAdres.getStandaard().getRedenWijzigingCode());
        Assert.assertEquals("O", persoonAdres.getStandaard().getAangeverAdreshouding().getCode());
        Assert.assertEquals(Integer.valueOf(20120101),
            persoonAdres.getStandaard().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals("Blokhut", persoonAdres.getStandaard().getAdresseerbaarObject().getWaarde());
        Assert
            .assertEquals("nummerAand", persoonAdres.getStandaard().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("0001", persoonAdres.getStandaard().getGemeenteCode().toString());
        Assert.assertEquals("openbare ruimte", persoonAdres.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("op ruimte", persoonAdres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("schilderswijk", persoonAdres.getStandaard().getGemeentedeel().getWaarde());
        Assert.assertEquals(Integer.valueOf(15), persoonAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("A", persoonAdres.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals("III", persoonAdres.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1066DG", persoonAdres.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("1001", persoonAdres.getStandaard().getWoonplaatsCode().toString());
        Assert.assertEquals(AanduidingBijHuisnummer.BY, persoonAdres.getStandaard().getLocatietovAdres());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_v0001_MIN.xml");
        valideerTegenSchema(xml);

        VerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getStuurgegevens());
        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());

        ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
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
        Assert.assertNotNull(bericht.getStuurgegevens());
        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());

        ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
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
