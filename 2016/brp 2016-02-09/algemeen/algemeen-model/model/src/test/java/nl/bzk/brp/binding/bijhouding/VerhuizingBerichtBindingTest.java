/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import java.io.IOException;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class VerhuizingBerichtBindingTest extends AbstractBindingInIntegratieTest<RegistreerVerhuizingBericht> {

    @Test
    public void testBindingMaxBinnenGemeentelijkBericht() throws JiBXException, IOException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_binnengemeentelijk_MAX.xml");
        valideerTegenSchema(xml);

        final RegistreerVerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        // Check stuurgegevens
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = bericht.getStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);

        Assert.assertEquals("123456", berichtStuurgegevens.getZendendePartijCode());
        Assert.assertEquals("UNITTEST", berichtStuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals("12345678-1234-1234-1234-123456789123", berichtStuurgegevens.getReferentienummer()
                .getWaarde());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertEquals(1, bericht.getAdministratieveHandeling().getActies().size());
        final ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertEquals(20120101, actie.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(SoortActie.REGISTRATIE_ADRES, actie.getSoort().getWaarde());
        Assert.assertNotNull(actie.getRootObject());
        final RootObject rootObject = actie.getRootObject();
        Assert.assertTrue(rootObject instanceof PersoonBericht);
        final PersoonBericht verhuizende = (PersoonBericht) rootObject;

        Assert.assertEquals("123456789", verhuizende.getObjectSleutel());
        final PersoonAdresBericht persoonAdres = (PersoonAdresBericht) verhuizende.getAdressen().toArray()[0];
        Assert.assertEquals(FunctieAdres.WOONADRES, persoonAdres.getStandaard().getSoort().getWaarde());
        Assert.assertEquals("B", persoonAdres.getStandaard().getRedenWijzigingCode());
        Assert.assertEquals("O", persoonAdres.getStandaard().getAangeverAdreshoudingCode());
        Assert.assertEquals(Integer.valueOf(20120101), persoonAdres.getStandaard().getDatumAanvangAdreshouding()
                .getWaarde());
        Assert.assertEquals("adresseerbaarObj", persoonAdres.getStandaard().getIdentificatiecodeAdresseerbaarObject()
                .getWaarde());
        Assert.assertEquals("nummerAanduiding", persoonAdres.getStandaard().getIdentificatiecodeNummeraanduiding()
                .getWaarde());
        Assert.assertEquals("openbare ruimte", persoonAdres.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("op ruimte", persoonAdres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("schilderswijk", persoonAdres.getStandaard().getGemeentedeel().getWaarde());
        Assert.assertEquals(Integer.valueOf(15), persoonAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("A", persoonAdres.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals("III", persoonAdres.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1066DG", persoonAdres.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("1001", persoonAdres.getStandaard().getWoonplaatsnaam().getWaarde());
        Assert.assertEquals(LocatieTenOpzichteVanAdres.BY, persoonAdres.getStandaard().getLocatieTenOpzichteVanAdres()
                .getWaarde());
        Assert.assertEquals("Derde berg links", persoonAdres.getStandaard().getLocatieomschrijving().getWaarde());
    }

    @Test
    public void testBindingMaxInterGemeentelijkBericht() throws JiBXException, IOException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_intergemeentelijk_MAX.xml");
        valideerTegenSchema(xml);

        final RegistreerVerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);

        // Check stuurgegevens
        final BerichtStuurgegevensGroepBericht berichtStuurgegevens = bericht.getStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("123456", berichtStuurgegevens.getZendendePartijCode());
        Assert.assertEquals("UNITTEST", berichtStuurgegevens.getZendendeSysteem().getWaarde());
        Assert.assertEquals("12345678-1234-1234-1234-123456789123", berichtStuurgegevens.getReferentienummer()
                .getWaarde());
        Assert.assertNull(berichtStuurgegevens.getCrossReferentienummer());

        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());
        Assert.assertEquals(1, bericht.getAdministratieveHandeling().getActies().size());
        final ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertEquals(20120101, actie.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(SoortActie.REGISTRATIE_ADRES, actie.getSoort().getWaarde());
        Assert.assertNotNull(actie.getRootObject());
        final RootObject rootObject = actie.getRootObject();
        Assert.assertTrue(rootObject instanceof PersoonBericht);
        final PersoonBericht verhuizende = (PersoonBericht) rootObject;

        Assert.assertEquals("123456789", verhuizende.getObjectSleutel());
        final PersoonAdresBericht persoonAdres = (PersoonAdresBericht) verhuizende.getAdressen().toArray()[0];
        Assert.assertEquals(FunctieAdres.WOONADRES, persoonAdres.getStandaard().getSoort().getWaarde());
        Assert.assertEquals("B", persoonAdres.getStandaard().getRedenWijzigingCode());
        Assert.assertEquals("O", persoonAdres.getStandaard().getAangeverAdreshoudingCode());
        Assert.assertEquals(Integer.valueOf(20120101), persoonAdres.getStandaard().getDatumAanvangAdreshouding()
                .getWaarde());
        Assert.assertEquals("adresseerbaarObj", persoonAdres.getStandaard().getIdentificatiecodeAdresseerbaarObject()
                .getWaarde());
        Assert.assertEquals("nummerAanduiding", persoonAdres.getStandaard().getIdentificatiecodeNummeraanduiding()
                .getWaarde());
        Assert.assertEquals("2002", persoonAdres.getStandaard().getGemeenteCode());
        Assert.assertEquals("openbare ruimte", persoonAdres.getStandaard().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("op ruimte", persoonAdres.getStandaard().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("schilderswijk", persoonAdres.getStandaard().getGemeentedeel().getWaarde());
        Assert.assertEquals(Integer.valueOf(15), persoonAdres.getStandaard().getHuisnummer().getWaarde());
        Assert.assertEquals("A", persoonAdres.getStandaard().getHuisletter().getWaarde());
        Assert.assertEquals("III", persoonAdres.getStandaard().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("1066DG", persoonAdres.getStandaard().getPostcode().getWaarde());
        Assert.assertEquals("1001", persoonAdres.getStandaard().getWoonplaatsnaam().getWaarde());
        Assert.assertEquals(LocatieTenOpzichteVanAdres.BY, persoonAdres.getStandaard().getLocatieTenOpzichteVanAdres()
                .getWaarde());
        Assert.assertEquals("Derde berg links", persoonAdres.getStandaard().getLocatieomschrijving().getWaarde());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_binnengemeentelijk_MIN.xml");
        valideerTegenSchema(xml);

        final RegistreerVerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getStuurgegevens());
        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());

        final ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertNotNull(actie);
        Assert.assertNotNull(actie.getSoort());
        Assert.assertNotNull(actie.getRootObject());

        final RootObject rootObject = actie.getRootObject();
        Assert.assertNotNull(rootObject);
        Assert.assertTrue(rootObject instanceof PersoonBericht);
    }

    @Test
    public void testBindingMinNilBericht() throws IOException, JiBXException {
        final String xml = leesBestand("migratie_Verhuizing_Bijhouding_binnengemeentelijk_MIN-nil.xml");
        valideerTegenSchema(xml);

        final RegistreerVerhuizingBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getStuurgegevens());
        Assert.assertNotNull(bericht.getAdministratieveHandeling().getActies());

        final ActieBericht actie = bericht.getAdministratieveHandeling().getActies().get(0);
        Assert.assertNotNull(actie);
        Assert.assertNotNull(actie.getSoort());
        Assert.assertNotNull(actie.getRootObject());

        final RootObject rootObject = actie.getRootObject();
        Assert.assertNotNull(rootObject);
        Assert.assertTrue(rootObject instanceof PersoonBericht);
    }

    @Override
    public Class<RegistreerVerhuizingBericht> getBindingClass() {
        return RegistreerVerhuizingBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdVerblijfAdresBerichten();
    }
}
