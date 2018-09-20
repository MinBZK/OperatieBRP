/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenPersoonBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteAntwoordBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.custommonkey.xmlunit.XMLUnit;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;


public class InschrijvingGeboorteAntwoordBerichtBindingTest extends
        AbstractBindingUitIntegratieTest<RegistreerGeboorteAntwoordBericht>
{

    private static final String RESULTAAT_NODE_NAAM = "bhg_afsRegistreerGeboorte_R";

    @Override
    public Class<RegistreerGeboorteAntwoordBericht> getBindingClass() {
        return RegistreerGeboorteAntwoordBericht.class;
    }

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testLeegAntwoordBericht() throws JiBXException, IOException, SAXException {
        RegistreerGeboorteAntwoordBericht bericht = new RegistreerGeboorteAntwoordBericht();

        bericht.setParameters(maakParametersVoorAntwoordBericht(Verwerkingswijze.BIJHOUDING));
        bericht.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        bericht.getAdministratieveHandeling().setPartij(StatischeObjecttypeBuilder.bouwPartij(1, "1"));
        bericht.getAdministratieveHandeling().setTijdstipRegistratie(DatumTijdAttribuut.nu());

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789");
        assertXMLEqual(
                getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "Geslaagd", "Geen", null, null, null,
                        "2012-03-25T14:35:06.789", "geboorteInNederland", true), xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testAntwoordMetBijgehoudenPersonen() throws JiBXException, IOException, SAXException {
        BerichtIdentificeerbaar identificeerbaar = new PersoonBericht();
        identificeerbaar.setCommunicatieID("communicatieId");

        final ArrayList<AdministratieveHandelingBijgehoudenPersoonBericht> berichtBijgehoudenPersonen =
                new ArrayList<>();

        PersoonBericht pers1 = bouwBijgehoudenPersoon(123456782);
        PersoonBericht pers2 = bouwBijgehoudenPersoon(123456781);
        PersoonBericht pers3 = bouwBijgehoudenPersoon(123456780);

        final AdministratieveHandelingBijgehoudenPersoonBericht bijgehoudenPersoonBericht1 =
                new AdministratieveHandelingBijgehoudenPersoonBericht();
        bijgehoudenPersoonBericht1.setPersoon(pers1);
        berichtBijgehoudenPersonen.add(bijgehoudenPersoonBericht1);

        final AdministratieveHandelingBijgehoudenPersoonBericht bijgehoudenPersoonBericht2 =
                new AdministratieveHandelingBijgehoudenPersoonBericht();
        bijgehoudenPersoonBericht2.setPersoon(pers2);
        berichtBijgehoudenPersonen.add(bijgehoudenPersoonBericht2);

        final AdministratieveHandelingBijgehoudenPersoonBericht bijgehoudenPersoonBericht3 =
                new AdministratieveHandelingBijgehoudenPersoonBericht();
        bijgehoudenPersoonBericht3.setPersoon(pers3);
        berichtBijgehoudenPersonen.add(bijgehoudenPersoonBericht3);

        pers1.setAdressen(new ArrayList<PersoonAdresBericht>());
        pers2.setAdressen(new ArrayList<PersoonAdresBericht>());
        pers3.setAdressen(new ArrayList<PersoonAdresBericht>());

        PersoonAdresBericht adres = PersoonAdresBuilder.bouwWoonadres(null, 12, "1234AB", null, null, null);
        pers1.getAdressen().add(adres);
        pers2.getAdressen().add(adres);
        pers3.getAdressen().add(adres);

        final RegistreerGeboorteAntwoordBericht bericht = new RegistreerGeboorteAntwoordBericht();
        bericht.setParameters(maakParametersVoorAntwoordBericht(Verwerkingswijze.BIJHOUDING));
        bericht.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.INFORMATIE, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));

        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingGeboorteInNederlandBericht());
        bericht.getAdministratieveHandeling().setPartij(StatischeObjecttypeBuilder.bouwPartij(1, "1"));
        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(berichtBijgehoudenPersonen);
        bericht.getAdministratieveHandeling().setTijdstipRegistratie(DatumTijdAttribuut.nu());

        Melding[] meldingen = { new Melding(SoortMelding.INFORMATIE, Regel.ACT0001, identificeerbaar, "veld") };
        bericht.setMeldingen(maakBerichtMeldingenBericht(meldingen));

        String xml = marshalObject(bericht);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipOntlening", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789");
        assertXMLEqual(
            getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "Geslaagd", "Informatie", meldingen,
                Arrays.asList(pers1, pers2, pers3), null, "2012-03-25T14:35:06.789", "geboorteInNederland",
                true), xml);
        valideerTegenSchema(xml);
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdAfstammingBerichten();
    }
}
