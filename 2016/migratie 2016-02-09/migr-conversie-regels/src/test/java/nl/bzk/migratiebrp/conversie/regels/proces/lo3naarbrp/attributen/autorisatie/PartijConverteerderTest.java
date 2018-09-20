/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.Collections;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;

import org.junit.Test;

public class PartijConverteerderTest extends AbstractComponentTest {

    @Inject
    private PartijConverteerder converteerder;

    @Test
    public void testWelAfnemersindicatie() {

        final TussenStapel<BrpPartijInhoud> tussenStapel = converteerder.converteerPartijStapel(maakPartijStapel());
        Assert.assertNotNull(tussenStapel);

        Assert.assertFalse(tussenStapel.getLaatsteElement().isInhoudelijkLeeg());
    }

    @Test
    public void testGeenAfnemersindicatie() {

        final TussenStapel<BrpPartijInhoud> tussenStapel = converteerder.converteerPartijStapel(maakPartijStapelZonderAfnemersindicatie());
        Assert.assertNotNull(tussenStapel);
        Assert.assertTrue(tussenStapel.getLaatsteElement().isInhoudelijkLeeg());
    }

    private Lo3Stapel<Lo3AutorisatieInhoud> maakPartijStapel() {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900101));

        final Lo3AutorisatieInhoud inhoud = AutorisatieConversieHelperTest.maakAutorisatie();
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);

        return new Lo3Stapel<>(Collections.singletonList(categorie));
    }

    private Lo3Stapel<Lo3AutorisatieInhoud> maakPartijStapelZonderAfnemersindicatie() {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900101));

        final Lo3AutorisatieInhoud inhoud = AutorisatieConversieHelperTest.maakAutorisatie();
        inhoud.setAfnemersindicatie(null); // implicieert inhoud.isGevuld() -> false
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);

        return new Lo3Stapel<>(Collections.singletonList(categorie));
    }

}
