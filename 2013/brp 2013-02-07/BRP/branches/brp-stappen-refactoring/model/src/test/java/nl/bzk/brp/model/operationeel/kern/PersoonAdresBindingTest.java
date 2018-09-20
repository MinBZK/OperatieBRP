/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


/**
 *
 */
@Ignore //TODO Refactor fix, test tijdelijk uitgezet
public class PersoonAdresBindingTest extends AbstractBindingUitTest<PersoonAdresModel> {

    @Test
    public void testBindingOut() throws JiBXException {
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        RedenWijzigingAdres rdn = StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_INFRA;

//        RedenWijzigingAdres rdn = new RedenWijzigingAdres(new RedenWijzigingAdresCode("W"), null);

        gegevens.setRedenWijziging(rdn);
        gegevens.setSoort(FunctieAdres.WOONADRES);
        gegevens.setDatumAanvangAdreshouding(new Datum(20120101));
        gegevens.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_ADRESHOUDING_INGESCHREVENE);
        gegevens.setAdresseerbaarObject(new AanduidingAdresseerbaarObject("Blokhut"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduiding("IDNR"));
        //Partij gemeente =
        //    new Partij(null, SoortPartij.GEMEENTE, new GemeenteCode("123"), null, null, null, null, null,
        //        StatusHistorie.A, null);
        gegevens.setGemeente(StatischeObjecttypeBuilder.GEMEENTE_BREDA);
        gegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Toilet"));
        gegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("WC"));
        gegevens.setGemeentedeel(new Gemeentedeel("Westraven"));
        gegevens.setHuisnummer(new Huisnummer("12"));
        gegevens.setHuisletter(new Huisletter("A"));
        gegevens.setPostcode(new Postcode("1066KL"));
        gegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("III"));
//        Plaats woonplaats = new Plaats(new Woonplaatscode("555"), null, null, null);
        gegevens.setWoonplaats(StatischeObjecttypeBuilder.WOONPLAATS_DEN_HAAG);
        gegevens.setLocatietovAdres(AanduidingBijHuisnummer.TO);
        gegevens.setLocatieOmschrijving(new LocatieOmschrijving("Groen"));
        gegevens.setBuitenlandsAdresRegel1(new Adresregel("regeltje 1"));
        gegevens.setBuitenlandsAdresRegel2(new Adresregel("regeltje 2"));
        gegevens.setBuitenlandsAdresRegel3(new Adresregel("regeltje 3"));
        gegevens.setBuitenlandsAdresRegel4(new Adresregel("regeltje 4"));
        gegevens.setBuitenlandsAdresRegel5(new Adresregel("regeltje 5"));
        gegevens.setBuitenlandsAdresRegel6(new Adresregel("regeltje 6"));
//        Land land = new Land(new Landcode("123"), null, null, null, null);
        gegevens.setLand(StatischeObjecttypeBuilder.LAND_BELGIE);
        gegevens.setDatumVertrekUitNederland(new Datum(20121231));

        persoonAdres.setStandaard(gegevens);

        String xml = marshalObject(new PersoonAdresModel(persoonAdres, new PersoonModel(new PersoonBericht())));
        final String expectedXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<brp:Objecttype_PersoonAdres xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "<brp:soortCode>W</brp:soortCode>"
                + "<brp:redenWijzigingCode>W</brp:redenWijzigingCode>"
                + "<brp:aangeverAdreshoudingCode>I</brp:aangeverAdreshoudingCode>"
                + "<brp:datumAanvangAdreshouding>20120101</brp:datumAanvangAdreshouding>"
                + "<brp:adresseerbaarObject>Blokhut</brp:adresseerbaarObject>"
                + "<brp:identificatiecodeNummeraanduiding>IDNR</brp:identificatiecodeNummeraanduiding>"
                + "<brp:gemeenteCode>0123</brp:gemeenteCode>"
                + "<brp:naamOpenbareRuimte>Toilet</brp:naamOpenbareRuimte>"
                + "<brp:afgekorteNaamOpenbareRuimte>WC</brp:afgekorteNaamOpenbareRuimte>"
                + "<brp:gemeentedeel>Westraven</brp:gemeentedeel>" + "<brp:huisnummer>12</brp:huisnummer>"
                + "<brp:huisletter>A</brp:huisletter>" + "<brp:huisnummertoevoeging>III</brp:huisnummertoevoeging>"
                + "<brp:postcode>1066KL</brp:postcode>" + "<brp:woonplaatsCode>0555</brp:woonplaatsCode>"
                + "<brp:locatietovAdres>to</brp:locatietovAdres>"
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
