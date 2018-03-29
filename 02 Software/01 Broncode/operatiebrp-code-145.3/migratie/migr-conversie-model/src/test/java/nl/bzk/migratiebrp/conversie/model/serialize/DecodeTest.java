/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Assert;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import org.junit.Test;

public class DecodeTest {

    @Test
    public void test() throws IOException, XmlException {
        final BrpPersoonslijst brpPersoonslijst;
        try (Reader reader = new BufferedReader(new InputStreamReader(DecodeTest.class.getResourceAsStream("/brppersoonslijst2.xml")))) {
            brpPersoonslijst = MigratieXml.decode(BrpPersoonslijst.class, reader);
        }

        System.out.println(brpPersoonslijst);
        Assert.assertNotNull("BRP Persoonslijst verwacht", brpPersoonslijst);

        // Zoek de 'originele' actie (regel 750 in XML)
        final BrpStapel<BrpNaamgebruikInhoud> brpNaamgebruikStapel = brpPersoonslijst.getNaamgebruikStapel();
        System.out.println(brpNaamgebruikStapel);
        Assert.assertNotNull("Naamgebruik stapel verwacht", brpNaamgebruikStapel);
        Assert.assertEquals("1 voorkomen in naamgebruik stapel verwacht", 1, brpNaamgebruikStapel.size());
        final BrpGroep<BrpNaamgebruikInhoud> brpNaamgebruikGroep = brpNaamgebruikStapel.get(0);
        System.out.println(brpNaamgebruikGroep);
        final BrpActie brpNaamgebruikActieInhoud = brpNaamgebruikGroep.getActieInhoud();
        System.out.println(brpNaamgebruikActieInhoud);
        Assert.assertNotNull("Actie inhoud in voorkomen in naamgebruik verwacht", brpNaamgebruikActieInhoud);
        Assert.assertEquals(
                "Id van actie inhoud in voorkomen in naamgebruik heeft niet de verwachte waarde",
                Long.valueOf(8L),
                brpNaamgebruikActieInhoud.getId());
        Assert.assertEquals(
                "Soort van actie inhoud in voorkomen in naamgebruik heeft niet de verwachte waarde",
                BrpSoortActieCode.CONVERSIE_GBA,
                brpNaamgebruikActieInhoud.getSoortActieCode());

        // Zoek de gerefereerde actie (regel 981 in XML, maar child is eerder gesorteerd in XML configuratie)
        final BrpStapel<BrpGeslachtsaanduidingInhoud> brpGeslachtsaanduidingStapel = brpPersoonslijst.getGeslachtsaanduidingStapel();
        System.out.println(brpGeslachtsaanduidingStapel);
        Assert.assertNotNull("Geslachtsaanduiding stapel verwacht", brpGeslachtsaanduidingStapel);
        Assert.assertEquals("1 voorkomen in geslachtsaanduiding stapel verwacht", 1, brpGeslachtsaanduidingStapel.size());
        final BrpGroep<BrpGeslachtsaanduidingInhoud> brpGeslachtsaanduidingGroep = brpGeslachtsaanduidingStapel.get(0);
        System.out.println(brpGeslachtsaanduidingGroep);
        final BrpActie brpGeslachtsaanduidingActieInhoud = brpGeslachtsaanduidingGroep.getActieInhoud();
        System.out.println(brpGeslachtsaanduidingActieInhoud);
        Assert.assertNotNull("Actie inhoud in voorkomen in geslachtsaanduiding verwacht", brpGeslachtsaanduidingActieInhoud);
        Assert.assertEquals(
                "Id van actie inhoud in voorkomen in geslachtsaanduiding heeft niet de verwachte waarde",
                Long.valueOf(8L),
                brpGeslachtsaanduidingActieInhoud.getId());
        Assert.assertEquals(
                "Soort van actie inhoud in voorkomen in geslachtsaanduiding heeft niet de verwachte waarde",
                BrpSoortActieCode.CONVERSIE_GBA,
                brpGeslachtsaanduidingActieInhoud.getSoortActieCode());
    }
}
