/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

/**
 * BerichtElementTest.
 */
public class BerichtElementTest {

    @Test
    public void testBouwBerichtElement() {
        final String naam = "xmlnaam";
        final String waarde = "xmlwaarde";
        final BerichtElement.Builder builder = BerichtElement.builder();
        builder.metNaam(naam);
        builder.metWaarde(waarde);
        final String naamKind = "xmlnaamkind";
        final String waardeKind = "xmlwaardekind";
        final BerichtElement.Builder builderKind = BerichtElement.builder();
        builderKind.metNaam(naamKind);
        builderKind.metWaarde(waardeKind);
        builder.metBerichtElement(builderKind);
        final BerichtElementAttribuut.Builder berichtAttr1 = BerichtElementAttribuut.builder();
        final String attrnaam = "attrnaam";
        final String attrwaarde = "attrwaarde";
        berichtAttr1.metNaam(attrnaam);
        berichtAttr1.metWaarde(attrwaarde);
        final BerichtElementAttribuut.Builder berichtAttr2 = BerichtElementAttribuut.maakBuilder(attrnaam, attrwaarde);
        builder.metBerichtElementAttribuut(berichtAttr1);
        builder.metBerichtElementAttribuut(berichtAttr2);

        final BerichtElement.Builder builderKind1 = BerichtElement.builder();
        builderKind1.metNaam(naamKind);
        builderKind1.metWaarde(waardeKind);
        builder.metBerichtElementen(Lists.newArrayList(builderKind1));

        final BerichtElement berichtElement = builder.build();

        Assert.assertNotNull(berichtElement);
        Assert.assertEquals(naam, berichtElement.getNaam());
        Assert.assertEquals(waarde, berichtElement.getWaarde());

        Assert.assertEquals(2, berichtElement.getElementen().size());
        Assert.assertEquals(2, berichtElement.getAttributen().size());

    }
}
