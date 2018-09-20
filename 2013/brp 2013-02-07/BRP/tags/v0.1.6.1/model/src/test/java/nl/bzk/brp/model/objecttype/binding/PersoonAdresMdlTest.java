/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.binding;

import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.model.groep.impl.gen.AbstractPersoonAdresStandaardGroepMdl;
import nl.bzk.brp.model.groep.impl.usr.PersoonAdresStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonAdresMdl;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.RedenWijzigingAdres;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class PersoonAdresMdlTest extends AbstractBindingUitTest<PersoonAdresMdl> {


    @Test
    public void testBindingOut() throws JiBXException {
        PersoonAdresMdl persoonAdres = new PersoonAdresMdl();

        AbstractPersoonAdresStandaardGroepMdl gegevens = new PersoonAdresStandaardGroepMdl();
        RedenWijzigingAdres rdn = new RedenWijzigingAdres();
        ReflectionTestUtils.setField(rdn, "redenWijzigingAdresCode", new RedenWijzigingAdresCode("W"));

        ReflectionTestUtils.setField(gegevens, "redenwijziging", rdn);
        ReflectionTestUtils.setField(gegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(gegevens, "datumAanvangAdreshouding", new Datum(20120101));
        ReflectionTestUtils.setField(gegevens, "aangeverAdreshouding", AangeverAdreshoudingIdentiteit.INGESCHREVENE);
        ReflectionTestUtils.setField(gegevens, "adresseerbaarObject", new Adresseerbaarobject("Blokhut"));
        ReflectionTestUtils.setField(gegevens, "identificatiecodeNummeraanduiding",
                new IdentificatiecodeNummerAanduiding("IDNR"));
        Partij gemeente = new Partij();
        ReflectionTestUtils.setField(gemeente, "gemeenteCode", new GemeenteCode("123"));
        ReflectionTestUtils.setField(gegevens, "gemeente", gemeente);
        ReflectionTestUtils.setField(gegevens, "naamOpenbareRuimte", new NaamOpenbareRuimte("Toilet"));
        ReflectionTestUtils.setField(gegevens, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte("WC"));
        ReflectionTestUtils.setField(gegevens, "gemeentedeel", new Gemeentedeel("Westraven"));
        ReflectionTestUtils.setField(gegevens, "huisnummer", new Huisnummer("12"));
        ReflectionTestUtils.setField(gegevens, "huisletter", new Huisletter("A"));
        ReflectionTestUtils.setField(gegevens, "postcode", new Postcode("1066KL"));
        ReflectionTestUtils.setField(gegevens, "huisnummertoevoeging", new Huisnummertoevoeging("III"));
        Plaats woonplaats = new Plaats();
        ReflectionTestUtils.setField(woonplaats, "code", new PlaatsCode("555"));
        ReflectionTestUtils.setField(gegevens, "woonplaats", woonplaats);
        ReflectionTestUtils.setField(gegevens, "locatietovAdres", new LocatieTovAdres("In het midden"));
        ReflectionTestUtils.setField(gegevens, "locatieOmschrijving", new LocatieOmschrijving("Groen"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsAdresRegel1", new Adresregel("regeltje 1"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsAdresRegel2", new Adresregel("regeltje 2"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsAdresRegel3", new Adresregel("regeltje 3"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsAdresRegel4", new Adresregel("regeltje 4"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsAdresRegel5", new Adresregel("regeltje 5"));
        ReflectionTestUtils.setField(gegevens, "buitenlandsAdresRegel6", new Adresregel("regeltje 6"));
        Land land = new Land();
        ReflectionTestUtils.setField(land, "landCode", new LandCode("123"));
        ReflectionTestUtils.setField(gegevens, "land", land);
        ReflectionTestUtils.setField(gegevens, "datumVertrekUitNederland", new Datum(20121231));

        ReflectionTestUtils.setField(persoonAdres, "gegevens", gegevens);

        String xml = marshalObjectTest(persoonAdres);
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
                        + "<brp:gemeenteCode>123</brp:gemeenteCode>"
                        + "<brp:naamOpenbareRuimte>Toilet</brp:naamOpenbareRuimte>"
                        + "<brp:afgekorteNaamOpenbareRuimte>WC</brp:afgekorteNaamOpenbareRuimte>"
                        + "<brp:gemeentedeel>Westraven</brp:gemeentedeel>"
                        + "<brp:huisnummer>12</brp:huisnummer>"
                        + "<brp:huisletter>A</brp:huisletter>"
                        + "<brp:huisnummertoevoeging>III</brp:huisnummertoevoeging>"
                        + "<brp:postcode>1066KL</brp:postcode>"
                        + "<brp:woonplaatsCode>555</brp:woonplaatsCode>"
                        + "<brp:locatietovAdres>In het midden</brp:locatietovAdres>"
                        + "<brp:locatieOmschrijving>Groen</brp:locatieOmschrijving>"
                        + "<brp:buitenlandsAdresRegel1>regeltje 1</brp:buitenlandsAdresRegel1>"
                        + "<brp:buitenlandsAdresRegel2>regeltje 2</brp:buitenlandsAdresRegel2>"
                        + "<brp:buitenlandsAdresRegel3>regeltje 3</brp:buitenlandsAdresRegel3>"
                        + "<brp:buitenlandsAdresRegel4>regeltje 4</brp:buitenlandsAdresRegel4>"
                        + "<brp:buitenlandsAdresRegel5>regeltje 5</brp:buitenlandsAdresRegel5>"
                        + "<brp:buitenlandsAdresRegel6>regeltje 6</brp:buitenlandsAdresRegel6>"
                        + "<brp:landCode>123</brp:landCode>"
                        + "<brp:datumVertrekUitNederland>20121231</brp:datumVertrekUitNederland>"
                        + "</brp:Objecttype_PersoonAdres>";
        Assert.assertEquals(expectedXml, xml);
    }

    @Override
    protected Class<PersoonAdresMdl> getBindingClass() {
        return PersoonAdresMdl.class;
    }
}
