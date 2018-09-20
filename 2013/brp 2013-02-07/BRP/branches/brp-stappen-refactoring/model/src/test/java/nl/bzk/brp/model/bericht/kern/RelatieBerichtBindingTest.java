/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore //TODO Refactor fix, test tijdelijk uitgezet
public class RelatieBerichtBindingTest extends AbstractBindingInTest<RelatieBericht> {

    @Test
    public void testFamilieRechtelijkeBetrekking() throws JiBXException {
        final String xml = "<ns:Objecttype_Relatie xmlns:ns=\"" + NAMESPACE_BRP + "\">"
            + "                <ns:betrokkenheden>\n"
            + "                    <ns:kind ns:cIDVerzendend=\"?\">\n"
            + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
            + "                            <ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
            + "                                <ns:code>V</ns:code>\n"
            + "                            </ns:geslachtsaanduiding>\n"
            + "                            <ns:geboorte ns:cIDVerzendend=\"?\">\n"
            + "                                <ns:datum>20120101</ns:datum>\n"
            + "                                <ns:landCode>6039</ns:landCode>\n"
            + "                            </ns:geboorte>\n"
            + "                        </ns:persoon>\n"
            + "                    </ns:kind>\n"
            + "                    <ns:ouder ns:cIDVerzendend=\"?\">\n"
            + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
            + "                        </ns:persoon>\n"
            + "                        <ns:ouderschap ns:cIDVerzendend=\"?\">\n"
            + "                        </ns:ouderschap>\n"
            + "                    </ns:ouder>\n"
            + "                    <ns:ouder ns:cIDVerzendend=\"?\">\n"
            + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
            + "                        </ns:persoon>\n"
            + "                        <ns:ouderschap ns:cIDVerzendend=\"?\">\n"
            + "                        </ns:ouderschap>\n"
            + "                    </ns:ouder>\n"
            + "                </ns:betrokkenheden>\n"
            + "</ns:Objecttype_Relatie>";
        final FamilierechtelijkeBetrekkingBericht relatie = (FamilierechtelijkeBetrekkingBericht) unmarshalObject(xml);
        Assert.assertNotNull(relatie);
        Assert.assertNotNull(relatie.getBetrokkenheden());
        Assert.assertTrue(relatie.getBetrokkenheden().size() == 3);

        Assert.assertNotNull(relatie.getKindBetrokkenheid());
        Assert.assertNotNull(relatie.getKindBetrokkenheid().getPersoon());

        Set<OuderBericht> oudersBetr = relatie.getOuderBetrokkenheden();
        Assert.assertNotNull(oudersBetr);
        Assert.assertTrue(oudersBetr.size() == 2);
        for (OuderBericht betrokkenheidBericht : oudersBetr) {
            Assert.assertNotNull(betrokkenheidBericht.getPersoon());
            Assert.assertNotNull(betrokkenheidBericht.getOuderschap());
        }

    }

    @Test
    public void testHuwelijk() throws JiBXException {
        final String xml = "<ns:Objecttype_Relatie xmlns:ns=\"" + NAMESPACE_BRP + "\">"
            + "                <ns:datumAanvang>20120101</ns:datumAanvang>"
            + "                <ns:gemeenteAanvangCode>1000</ns:gemeenteAanvangCode>"
            + "                <ns:woonplaatsAanvangCode>1000</ns:woonplaatsAanvangCode>"
            + "                <ns:buitenlandsePlaatsAanvang>blplaatsaanv</ns:buitenlandsePlaatsAanvang>"
            + "                <ns:buitenlandseRegioAanvang>blregioaanv</ns:buitenlandseRegioAanvang>"
            + "                <ns:landAanvangCode>1000</ns:landAanvangCode>"
            + "                <ns:omschrijvingLocatieAanvang>omslocaanv</ns:omschrijvingLocatieAanvang>"
            + "                <ns:redenEindeCode>1000</ns:redenEindeCode>"
            + "                <ns:datumEinde>20120101</ns:datumEinde>"
            + "                <ns:gemeenteEindeCode>1000</ns:gemeenteEindeCode>"
            + "                <ns:woonplaatsEindeCode>1000</ns:woonplaatsEindeCode>"
            + "                <ns:buitenlandsePlaatsEinde>blplaatseinde</ns:buitenlandsePlaatsEinde>"
            + "                <ns:buitenlandseRegioEinde>blregioeinde</ns:buitenlandseRegioEinde>"
            + "                <ns:landEindeCode>1000</ns:landEindeCode>"
            + "                <ns:omschrijvingLocatieEinde>omsloceinde</ns:omschrijvingLocatieEinde>"
            + "                <ns:betrokkenheden>\n"
            + "                    <ns:partner ns:cIDVerzendend=\"?\">\n"
            + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
            + "                            <ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
            + "                                <ns:code>V</ns:code>\n"
            + "                            </ns:geslachtsaanduiding>\n"
            + "                            <ns:geboorte ns:cIDVerzendend=\"?\">\n"
            + "                                <ns:datum>20120101</ns:datum>\n"
            + "                                <ns:landCode>6039</ns:landCode>\n"
            + "                            </ns:geboorte>\n"
            + "                        </ns:persoon>\n"
            + "                    </ns:partner>\n"
            + "                    <ns:partner ns:cIDVerzendend=\"?\">\n"
            + "                        <ns:persoon ns:cIDVerzendend=\"?\">\n"
            + "                            <ns:geslachtsaanduiding ns:cIDVerzendend=\"?\">\n"
            + "                                <ns:code>V</ns:code>\n"
            + "                            </ns:geslachtsaanduiding>\n"
            + "                            <ns:geboorte ns:cIDVerzendend=\"?\">\n"
            + "                                <ns:datum>20120101</ns:datum>\n"
            + "                                <ns:landCode>6039</ns:landCode>\n"
            + "                            </ns:geboorte>\n"
            + "                        </ns:persoon>\n"
            + "                    </ns:partner>\n"
            + "                </ns:betrokkenheden>\n"
            + "</ns:Objecttype_Relatie>";
        final HuwelijkBericht bericht = (HuwelijkBericht) unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        List<BetrokkenheidBericht> partners = bericht.getBetrokkenheden();
        Assert.assertNotNull(partners);
        Assert.assertTrue(partners.size() == 2);

        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht standaardGroep = bericht.getStandaard();
        Assert.assertNotNull(standaardGroep);
        Assert.assertEquals(new Datum(20120101), standaardGroep.getDatumAanvang());
        Assert.assertEquals(new Datum(20120101), standaardGroep.getDatumEinde());
        Assert.assertEquals(new GemeenteCode("1000"), standaardGroep.getGemeenteAanvangCode());
        Assert.assertEquals(new GemeenteCode("1000"), standaardGroep.getGemeenteEindeCode());
        Assert.assertEquals(new Woonplaatscode("1000"), standaardGroep.getWoonplaatsAanvang());
        Assert.assertEquals(new Woonplaatscode("1000"), standaardGroep.getWoonplaatsEinde());
        Assert.assertEquals(new BuitenlandsePlaats("blplaatsaanv"), standaardGroep.getBuitenlandsePlaatsAanvang());
        Assert.assertEquals(new BuitenlandsePlaats("blplaatseinde"), standaardGroep.getBuitenlandsePlaatsEinde());
        Assert.assertEquals(new Landcode("1000"), standaardGroep.getLandAanvangCode());
        Assert.assertEquals(new Landcode("1000"), standaardGroep.getLandEindeCode());
        Assert.assertEquals(new BuitenlandseRegio("blregioaanv"), standaardGroep.getBuitenlandseRegioAanvang());
        Assert.assertEquals(new BuitenlandseRegio("blregioeinde"), standaardGroep.getBuitenlandseRegioEinde());
        Assert.assertEquals(new OmschrijvingEnumeratiewaarde("omslocaanv"), standaardGroep.getOmschrijvingLocatieAanvang());
        Assert.assertEquals(new OmschrijvingEnumeratiewaarde("omsloceinde"), standaardGroep.getOmschrijvingLocatieEinde());
        /*Assert.assertEquals(new Woonplaatscode("1000"), standaardGroep.getRedenBeeindigingRelatie());*/
    }

    @Override
    protected Class<RelatieBericht> getBindingClass() {
        return RelatieBericht.class;
    }
}
