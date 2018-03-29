/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import org.junit.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AbstractOngeldigLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeInhoudBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import org.junit.Test;

/**
 * Test situaties wanneer een AbstractOngeldigLo3Bericht wordt terug gegeven. Dit splitst voor Lo3 zich uit in een
 * OngeldigeSyntaxBericht of een OngeldigeInhoudBericht.
 */
public class Lo3BerichtFactortOngeldigBerichtTest {

    /**
     * Basis test om te kijken of de opbouw uberhaupt klopt en om de 'fout'situaties van af te leiden.
     */
    @Test
    public void testOk() {
        final String ii01 = "00000000" // random key
                + "Ii01" // bericht nummer
                + "0" // herhaling
                + "00022" // bericht lengte
                + "01" // categorie
                + "017" // categorie lengte
                + "0110" // element
                + "010" // element lengte
                + "1234567890"; // element inhoud

        final Lo3BerichtFactory factory = new Lo3BerichtFactory();
        final Lo3Bericht bericht = factory.getBericht(ii01);
        Assert.assertEquals(Ii01Bericht.class, bericht.getClass());
    }

    /**
     * Als het bericht niet voldoet aan het berichtuitwisselingformaat dient een OngeldigeSyntaxBericht te worden
     * gegeven.
     */
    @Test
    public void testOngeldigeSyntaxBericht() {
        final String ii01 = "00000000" // random key
                + "Ii01" // bericht nummer
                + "0" // herhaling
                + "00022" // bericht lengte
                + "01" // categorie
                + "017" // categorie lengte
                + "0110" // element
                + "010" // element lengte
                + "123456789"; // element inhoud

        final Lo3BerichtFactory factory = new Lo3BerichtFactory();
        final Lo3Bericht bericht = factory.getBericht(ii01);
        Assert.assertEquals(OngeldigeSyntaxBericht.class, bericht.getClass());
        Assert.assertEquals(
                "Berichtlengte klopt niet. Genoemde lengte=22, daadwerkelijke lengte=21",
                ((AbstractOngeldigLo3Bericht) bericht).getMelding());
    }

    /**
     * Als het bericht niet inhoudelijk voldoet aan Lo3 eisen aan elementen dient een OngeldigeInhoudBericht te worden
     * gegeven.
     */
    @Test
    public void testOngeldigeInhoudBericht() {
        final String ii01 = "00000000" // random key
                + "Ii01" // bericht nummer
                + "0" // herhaling
                + "00022" // bericht lengte
                + "01" // categorie
                + "017" // categorie lengte
                + "0110" // element
                + "010" // element lengte
                + "12345a6789"; // element inhoud

        final Lo3BerichtFactory factory = new Lo3BerichtFactory();
        final Lo3Bericht bericht = factory.getBericht(ii01);
        Assert.assertEquals(OngeldigeInhoudBericht.class, bericht.getClass());
        Assert.assertEquals("Element 0110 moet een numerieke waarde bevatten.", ((AbstractOngeldigLo3Bericht) bericht).getMelding());
    }
}
