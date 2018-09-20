/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator;

import junit.framework.Assert;
import nl.bzk.brp.generator.java.domein.Identifier;
import org.junit.Test;


public class IdentifierTest {
    
    @Test
    public final void testNull() {
        Assert.assertEquals(null, new Identifier(null).getLowerCamel());
    }

    @Test
    public final void testLeeg() {
        Assert.assertEquals("", new Identifier("").getLowerCamel());
    }

    @Test
    public final void testAfkortingOpEinde() {
        Assert.assertEquals("landId", new Identifier("LandID").getLowerCamel());
    }

    @Test
    public final void testAfkortingAanBegin() {
        Assert.assertEquals("ipAdres", new Identifier("IPAdres").getLowerCamel());
    }

    @Test
    public final void testAfkortingAanBeginVoorCijfers() {
        Assert.assertEquals("iso13861", new Identifier("ISO13861").getLowerCamel());
    }

    @Test
    public final void testAfkortingInMidden() {
        Assert.assertEquals("indicatieDeelnameEuVerkiezingen", new Identifier("IndicatieDeelnameEUVerkiezingen").getLowerCamel());
    }
    
    @Test
    public final void shouldLowerAfkortingInMidden() {
        Assert.assertEquals("IndicatieDeelnameEuVerkiezingen", new Identifier("IndicatieDeelnameEUVerkiezingen").getUpperCamel());
    }
    
    @Test
    public final void testAfkorting1Letter() {
        Assert.assertEquals("aNummer", new Identifier("ANummer").getLowerCamel());
    }

    @Test
    public final void shouldConvertMultipleAbbreviations() {
        Assert.assertEquals("PersoonUitsluitingNlKiesrechtGroepEu",new Identifier("PersoonUitsluitingNlKiesrechtGroepEu").toString());
    }
}
