/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
//TODO Refactor fix, test tijdelijk uitgezet
public class PersoonOverlijdenGroepBindingTest extends AbstractBindingInTest<PersoonOverlijdenGroepBericht> {

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
        Assert.assertEquals("12", overlijdenGroep.getGemeenteOverlijdenCode());
        Assert.assertEquals("1", overlijdenGroep.getWoonplaatsOverlijdenCode());
        Assert.assertEquals(new BuitenlandsePlaats("plaats"), overlijdenGroep.getBuitenlandsePlaatsOverlijden());
        Assert.assertEquals(new BuitenlandseRegio("regio"), overlijdenGroep.getBuitenlandseRegioOverlijden());
        Assert.assertEquals("10", overlijdenGroep.getLandOverlijdenCode());
        Assert
            .assertEquals(new LocatieOmschrijving("omschrijving"), overlijdenGroep.getOmschrijvingLocatieOverlijden());
    }

    @Override
    protected Class<PersoonOverlijdenGroepBericht> getBindingClass() {
        return PersoonOverlijdenGroepBericht.class;
    }

}
