/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.deelnameEuVerkiezingen;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.doc;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.uitsluiting;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Doc;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Kiesrecht;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Assert;
import org.junit.Test;

public class BrpKiesrechtConverteerderTest extends AbstractComponentTest {

    private static final String DOC = "Euro-Doc";
    @Inject
    private BrpKiesrechtConverteerder subject;

    @Test
    
    public void test() {
        final int datumTijdRegistratie = 20040102;
        final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> deelnameEuVerkiezingen =
                stapel(groep(
                    deelnameEuVerkiezingen(true, 20040101, 20100101),
                    his(20040101),
                    act(7, datumTijdRegistratie, doc(DOC, "059901"))));

        final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel =
                stapel(groep(uitsluiting(true, 20090101), his(20090101), act(3, 20090102)));

        // Execute

        final Lo3Stapel<Lo3KiesrechtInhoud> result = subject.converteer(deelnameEuVerkiezingen, uitsluitingKiesrechtStapel);

        final Lo3KiesrechtInhoud expected = lo3Kiesrecht(true, 20040101, 20100101, true, 20090101);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", datumTijdRegistratie, DOC);

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }

    @Test
    
    public void testUitsluitingOnly() {
        final int datumTijdRegistratie = 20040102;
        final BrpStapel<BrpUitsluitingKiesrechtInhoud> uitsluitingKiesrechtStapel =
                stapel(groep(uitsluiting(true, 20090101), his(20090101), act(3, datumTijdRegistratie, doc(DOC, "059901"))));

        // Execute

        final Lo3Stapel<Lo3KiesrechtInhoud> result = subject.converteer(uitsluitingKiesrechtStapel);

        final Lo3KiesrechtInhoud expected = lo3Kiesrecht(null, null, null, true, 20090101);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", datumTijdRegistratie, DOC);

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }
}
