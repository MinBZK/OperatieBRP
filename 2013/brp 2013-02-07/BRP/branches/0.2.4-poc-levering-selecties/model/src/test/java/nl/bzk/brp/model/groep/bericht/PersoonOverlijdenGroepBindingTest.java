/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class PersoonOverlijdenGroepBindingTest  extends AbstractBindingInTest<PersoonOverlijdenGroepBericht> {

    @Test
    public void testPersoonOverlijdenGroep() throws JiBXException {
        final String xml = "<brp:Groep_PersoonOverlijden brp:cIDVerzendend=\"id.overlijden\" "
               + "xmlns:brp=\"" + NAMESPACE_BRP + "\">\n"
               + "      <brp:datum brp:noValue=\"nietOndersteund\">20120101</brp:datum>\n"
               + "      <brp:gemeenteCode brp:noValue=\"nietOndersteund\">0012</brp:gemeenteCode>\n"
               + "      <brp:woonplaatsCode brp:noValue=\"nietOndersteund\">0001</brp:woonplaatsCode>\n"
               + "      <brp:buitenlandsePlaats brp:noValue=\"nietOndersteund\">plaats</brp:buitenlandsePlaats>\n"
               + "      <brp:buitenlandseRegio brp:noValue=\"nietOndersteund\">regio</brp:buitenlandseRegio>\n"
               + "      <brp:landCode brp:noValue=\"nietOndersteund\">10</brp:landCode>\n"
               + "      <brp:omschrijvingLocatie brp:noValue=\"nietOndersteund\">omschrijving</brp:omschrijvingLocatie>\n"
               + "    </brp:Groep_PersoonOverlijden >";



        final PersoonOverlijdenGroepBericht overlijdenGroep = unmarshalObject(xml);
        Assert.assertNotNull(overlijdenGroep);
        Assert.assertEquals(new Datum(20120101), overlijdenGroep.getDatumOverlijden());
        Assert.assertEquals(new Gemeentecode((short) 12), overlijdenGroep.getGemeenteOverlijdenCode());
        Assert.assertEquals(new PlaatsCode((short) 1), overlijdenGroep.getWoonplaatsOverlijdenCode());
        Assert.assertEquals(new BuitenlandsePlaats("plaats"), overlijdenGroep.getBuitenlandsePlaatsOverlijden());
        Assert.assertEquals(new BuitenlandseRegio("regio"), overlijdenGroep.getBuitenlandseRegioOverlijden());
        Assert.assertEquals(new Landcode((short) 10), overlijdenGroep.getLandOverlijdenCode());
        Assert.assertEquals(new LocatieOmschrijving("omschrijving"), overlijdenGroep.getOmschrijvingLocatieOverlijden());
    }

    @Override
    protected Class<PersoonOverlijdenGroepBericht> getBindingClass() {
        return PersoonOverlijdenGroepBericht.class;
    }

}
