/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisOverlijdenConverteerderTest {

    private Lo3AttribuutConverteerder lo3AttribuutConverteerder;

    private ToevalligeGebeurtenisOverlijdenConverteerder subject;

    @Before
    public void setup() {
        lo3AttribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        subject = new ToevalligeGebeurtenisOverlijdenConverteerder(lo3AttribuutConverteerder);
    }

    @Test
    public void testConverteer() {
        final Lo3OverlijdenInhoud inhoud = new Lo3OverlijdenInhoud(new Lo3Datum(20160401), new Lo3GemeenteCode("0599"), new Lo3LandCode("6030"));
        final Lo3Categorie<Lo3OverlijdenInhoud> categorie = new Lo3Categorie<>(inhoud, null, new Lo3Historie(null, null, null), null);
        final BrpToevalligeGebeurtenisOverlijden overlijden = subject.converteer(categorie);
        Assert.assertEquals("datum moet overeenkomen", Integer.valueOf(20160401), overlijden.getDatum().getWaarde());
        Assert.assertEquals("Plaats moet overeenkomen", "0599", overlijden.getGemeenteCode().getWaarde());
        Assert.assertEquals("Land moet overeenkomen", "6030", overlijden.getLandOfGebiedCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        Assert.assertNull("Bij null moet antwoord leeg zijn", subject.converteer(null));
    }
}
