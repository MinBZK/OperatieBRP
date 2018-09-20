/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

public class RelatieBerichtBindingTest extends AbstractBindingInTest<RelatieBericht> {

    @Test
    public void testFamilieRechtelijkeBetrekking() throws JiBXException {
        final String xml = "<ns:Objecttype_Relatie xmlns:ns=\"" + NAMESPACE_BRP + "\">"
                + "\t\t\t\t<ns:betrokkenheden>\n"
                + "\t\t\t\t\t<ns:kind ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t<ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t<ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t\t<ns:code>V</ns:code>\n"
                + "\t\t\t\t\t\t\t</ns:geslachtsaanduiding>\n"
                + "\t\t\t\t\t\t\t<ns:geboorte ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t\t<ns:datum>20120101</ns:datum>\n"
                + "\t\t\t\t\t\t\t\t<ns:landCode>6039</ns:landCode>\n"
                + "\t\t\t\t\t\t\t</ns:geboorte>\n"
                + "\t\t\t\t\t\t</ns:persoon>\n"
                + "\t\t\t\t\t</ns:kind>\n"
                + "\t\t\t\t\t<ns:ouder ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t<ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t</ns:persoon>\n"
                + "\t\t\t\t\t\t<ns:ouderschap ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t</ns:ouderschap>\n"
                + "\t\t\t\t\t</ns:ouder>\n"
                + "\t\t\t\t\t<ns:ouder ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t<ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t</ns:persoon>\n"
                + "\t\t\t\t\t\t<ns:ouderschap ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t</ns:ouderschap>\n"
                + "\t\t\t\t\t</ns:ouder>\n"
                + "\t\t\t\t</ns:betrokkenheden>\n"
                + "</ns:Objecttype_Relatie>";
        final RelatieBericht relatie = unmarshalObject(xml);
        Assert.assertNotNull(relatie);
        Assert.assertNotNull(relatie.getBetrokkenheden());
        Assert.assertTrue(relatie.getBetrokkenheden().size() == 3);

        Assert.assertNotNull(relatie.getKindBetrokkenheid());
        Assert.assertNotNull(relatie.getKindBetrokkenheid().getBetrokkene());

        Set<BetrokkenheidBericht> oudersBetr = relatie.getOuderBetrokkenheden();
        Assert.assertNotNull(oudersBetr);
        Assert.assertTrue(oudersBetr.size() == 2);
        for (BetrokkenheidBericht betrokkenheidBericht : oudersBetr) {
            Assert.assertNotNull(betrokkenheidBericht.getBetrokkene());
            Assert.assertNotNull(betrokkenheidBericht.getBetrokkenheidOuderschap());
        }

    }

    @Test
    public void testHuwelijk() throws JiBXException {
        final String xml = "<ns:Objecttype_Relatie xmlns:ns=\"" + NAMESPACE_BRP + "\">"
                + "\t\t\t\t<ns:datumAanvang>20120101</ns:datumAanvang>"
                + "\t\t\t\t<ns:gemeenteAanvangCode>1000</ns:gemeenteAanvangCode>"
                + "\t\t\t\t<ns:woonplaatsAanvangCode>1000</ns:woonplaatsAanvangCode>"
                + "\t\t\t\t<ns:buitenlandsePlaatsAanvang>blplaatsaanv</ns:buitenlandsePlaatsAanvang>"
                + "\t\t\t\t<ns:buitenlandseRegioAanvang>blregioaanv</ns:buitenlandseRegioAanvang>"
                + "\t\t\t\t<ns:landAanvangCode>1000</ns:landAanvangCode>"
                + "\t\t\t\t<ns:omschrijvingLocatieAanvang>omslocaanv</ns:omschrijvingLocatieAanvang>"
                + "\t\t\t\t<ns:redenEindeCode>1000</ns:redenEindeCode>"
                + "\t\t\t\t<ns:datumEinde>20120101</ns:datumEinde>"
                + "\t\t\t\t<ns:gemeenteEindeCode>1000</ns:gemeenteEindeCode>"
                + "\t\t\t\t<ns:woonplaatsEindeCode>1000</ns:woonplaatsEindeCode>"
                + "\t\t\t\t<ns:buitenlandsePlaatsEinde>blplaatseinde</ns:buitenlandsePlaatsEinde>"
                + "\t\t\t\t<ns:buitenlandseRegioEinde>blregioeinde</ns:buitenlandseRegioEinde>"
                + "\t\t\t\t<ns:landEindeCode>1000</ns:landEindeCode>"
                + "\t\t\t\t<ns:omschrijvingLocatieEinde>omsloceinde</ns:omschrijvingLocatieEinde>"
                + "\t\t\t\t<ns:betrokkenheden>\n"
                + "\t\t\t\t\t<ns:partner ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t<ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t<ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t\t<ns:code>V</ns:code>\n"
                + "\t\t\t\t\t\t\t</ns:geslachtsaanduiding>\n"
                + "\t\t\t\t\t\t\t<ns:geboorte ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t\t<ns:datum>20120101</ns:datum>\n"
                + "\t\t\t\t\t\t\t\t<ns:landCode>6039</ns:landCode>\n"
                + "\t\t\t\t\t\t\t</ns:geboorte>\n"
                + "\t\t\t\t\t\t</ns:persoon>\n"
                + "\t\t\t\t\t</ns:partner>\n"
                + "\t\t\t\t\t<ns:partner ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t<ns:persoon ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t<ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t\t<ns:code>V</ns:code>\n"
                + "\t\t\t\t\t\t\t</ns:geslachtsaanduiding>\n"
                + "\t\t\t\t\t\t\t<ns:geboorte ns:cIDVerzendend=\"?\">\n"
                + "\t\t\t\t\t\t\t\t<ns:datum>20120101</ns:datum>\n"
                + "\t\t\t\t\t\t\t\t<ns:landCode>6039</ns:landCode>\n"
                + "\t\t\t\t\t\t\t</ns:geboorte>\n"
                + "\t\t\t\t\t\t</ns:persoon>\n"
                + "\t\t\t\t\t</ns:partner>\n"
                + "\t\t\t\t</ns:betrokkenheden>\n"
                + "</ns:Objecttype_Relatie>";
        final RelatieBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        List<BetrokkenheidBericht> partners = bericht.getBetrokkenheden();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        RelatieStandaardGroepBericht standaardGroep = bericht.getGegevens();
        Assert.assertNotNull(standaardGroep);
        Assert.assertEquals(new Datum(20120101), standaardGroep.getDatumAanvang());
        Assert.assertEquals(new Datum(20120101), standaardGroep.getDatumEinde());
        Assert.assertEquals(new Gemeentecode((short) 1000), standaardGroep.getGemeenteAanvangCode());
        Assert.assertEquals(new Gemeentecode((short) 1000), standaardGroep.getGemeenteEindeCode());
        Assert.assertEquals(new PlaatsCode((short) 1000), standaardGroep.getWoonPlaatsAanvangCode());
        Assert.assertEquals(new PlaatsCode((short) 1000), standaardGroep.getWoonPlaatsEindeCode());
        Assert.assertEquals(new BuitenlandsePlaats("blplaatsaanv"), standaardGroep.getBuitenlandsePlaatsAanvang());
        Assert.assertEquals(new BuitenlandsePlaats("blplaatseinde"), standaardGroep.getBuitenlandsePlaatsEinde());
        Assert.assertEquals(new Landcode((short) 1000), standaardGroep.getLandAanvangCode());
        Assert.assertEquals(new Landcode((short) 1000), standaardGroep.getLandEindeCode());
        Assert.assertEquals(new BuitenlandseRegio("blregioaanv"), standaardGroep.getBuitenlandseRegioAanvang());
        Assert.assertEquals(new BuitenlandseRegio("blregioeinde"), standaardGroep.getBuitenlandseRegioEinde());
        Assert.assertEquals(new Omschrijving("omslocaanv"), standaardGroep.getOmschrijvingLocatieAanvang());
        Assert.assertEquals(new Omschrijving("omsloceinde"), standaardGroep.getOmschrijvingLocatieEinde());
        /*Assert.assertEquals(new PlaatsCode("1000"), standaardGroep.getRedenBeeindigingRelatie());*/
    }

    @Override
    protected Class<RelatieBericht> getBindingClass() {
        return RelatieBericht.class;
    }
}
