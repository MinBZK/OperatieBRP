/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class PersoonAdresBindingTest extends AbstractBindingUitTest<PersoonAdresModel> {


    @Test
    public void testBindingOut() throws JiBXException {
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        RedenWijzigingAdres rdn = new RedenWijzigingAdres();
        rdn.setRedenWijzigingAdresCode(new RedenWijzigingAdresCode("W"));

        gegevens.setRedenwijziging(rdn);
        gegevens.setSoort(FunctieAdres.WOONADRES);
        gegevens.setDatumAanvangAdreshouding(new Datum(20120101));
        gegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.INGESCHREVENE);
        gegevens.setAdresseerbaarObject(new Adresseerbaarobject("Blokhut"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("IDNR"));
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 123));
        gegevens.setGemeente(gemeente);
        gegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Toilet"));
        gegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("WC"));
        gegevens.setGemeentedeel(new Gemeentedeel("Westraven"));
        gegevens.setHuisnummer(new Huisnummer(12));
        gegevens.setHuisletter(new Huisletter("A"));
        gegevens.setPostcode(new Postcode("1066KL"));
        gegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("III"));
        Plaats woonplaats = new Plaats();
        woonplaats.setCode(new PlaatsCode((short) 555));
        gegevens.setWoonplaats(woonplaats);
        gegevens.setLocatietovAdres(new LocatieTovAdres("In het midden"));
        gegevens.setLocatieOmschrijving(new LocatieOmschrijving("Groen"));
        gegevens.setBuitenlandsAdresRegel1(new Adresregel("regeltje 1"));
        gegevens.setBuitenlandsAdresRegel2(new Adresregel("regeltje 2"));
        gegevens.setBuitenlandsAdresRegel3(new Adresregel("regeltje 3"));
        gegevens.setBuitenlandsAdresRegel4(new Adresregel("regeltje 4"));
        gegevens.setBuitenlandsAdresRegel5(new Adresregel("regeltje 5"));
        gegevens.setBuitenlandsAdresRegel6(new Adresregel("regeltje 6"));
        Land land = new Land();
        land.setCode(new Landcode((short) 123));
        gegevens.setLand(land);
        gegevens.setDatumVertrekUitNederland(new Datum(20121231));

        persoonAdres.setGegevens(gegevens);

        String xml = marshalObject(new PersoonAdresModel(persoonAdres, new PersoonModel(new PersoonBericht())));
        final String expectedXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<brp:Objecttype_PersoonAdres xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<brp:soortCode>W</brp:soortCode>"
                + "<brp:redenWijzigingCode>W</brp:redenWijzigingCode>"
                + "<brp:aangeverAdreshoudingCode>I</brp:aangeverAdreshoudingCode>"
                + "<brp:datumAanvangAdreshouding>20120101</brp:datumAanvangAdreshouding>"
                + "<brp:adresseerbaarObject>Blokhut</brp:adresseerbaarObject>"
                + "<brp:identificatiecodeNummeraanduiding>IDNR</brp:identificatiecodeNummeraanduiding>"
                + "<brp:gemeenteCode>0123</brp:gemeenteCode>"
                + "<brp:naamOpenbareRuimte>Toilet</brp:naamOpenbareRuimte>"
                + "<brp:afgekorteNaamOpenbareRuimte>WC</brp:afgekorteNaamOpenbareRuimte>"
                + "<brp:gemeentedeel>Westraven</brp:gemeentedeel>"
                + "<brp:huisnummer>12</brp:huisnummer>"
                + "<brp:huisletter>A</brp:huisletter>"
                + "<brp:huisnummertoevoeging>III</brp:huisnummertoevoeging>"
                + "<brp:postcode>1066KL</brp:postcode>"
                + "<brp:woonplaatsCode>0555</brp:woonplaatsCode>"
                + "<brp:locatietovAdres>In het midden</brp:locatietovAdres>"
                + "<brp:locatieOmschrijving>Groen</brp:locatieOmschrijving>"
                + "<brp:buitenlandsAdresRegel1>regeltje 1</brp:buitenlandsAdresRegel1>"
                + "<brp:buitenlandsAdresRegel2>regeltje 2</brp:buitenlandsAdresRegel2>"
                + "<brp:buitenlandsAdresRegel3>regeltje 3</brp:buitenlandsAdresRegel3>"
                + "<brp:buitenlandsAdresRegel4>regeltje 4</brp:buitenlandsAdresRegel4>"
                + "<brp:buitenlandsAdresRegel5>regeltje 5</brp:buitenlandsAdresRegel5>"
                + "<brp:buitenlandsAdresRegel6>regeltje 6</brp:buitenlandsAdresRegel6>"
                + "<brp:landCode>0123</brp:landCode>"
                + "<brp:datumVertrekUitNederland>20121231</brp:datumVertrekUitNederland>"
                + "</brp:Objecttype_PersoonAdres>";
        Assert.assertEquals(expectedXml, xml);
    }

    @Override
    protected Class<PersoonAdresModel> getBindingClass() {
        return PersoonAdresModel.class;
    }
}
