/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.toevalligegebeurtenis;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ToevalligeGebeurtenisNaamGeslachtConverteerderTest {

    private ToevalligeGebeurtenisNaamGeslachtConverteerder subject;

    @Before
    public void setup() {
        final Lo3AttribuutConverteerder lo3AttribuutConverteerder = new Lo3AttribuutConverteerder(new ConversietabelFactoryImpl());
        subject = new ToevalligeGebeurtenisNaamGeslachtConverteerder(lo3AttribuutConverteerder);
    }

    @Test
    public void testConverteer() {
        Lo3PersoonInhoud nieuw = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        Lo3PersoonInhoud oud = maakPersoon(null, null, "oude voornaam", "G", "voor de", "oude achternaam", 19700101, "0599", "6030", "V");
        Lo3Categorie<Lo3PersoonInhoud> categorieNieuw = new Lo3Categorie<>(nieuw, null, new Lo3Historie(null, null, null), null);
        Lo3Categorie<Lo3PersoonInhoud> categorieOud = new Lo3Categorie<>(oud, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3PersoonInhoud> stapel = new Lo3Stapel<>(Arrays.asList(categorieNieuw, categorieOud));
        BrpToevalligeGebeurtenisNaamGeslacht persoon = subject.converteer(stapel);
        Assert.assertEquals("Voornaam moet gelijk zijn", "voornaam", persoon.getVoornamen().getWaarde());
        Assert.assertEquals("Adelijke titel moet graaf zijn", "G", persoon.getAdellijkeTitelCode().getWaarde());
        Assert.assertEquals("voorvoegsels moeten gelijk zijn", "voor de", persoon.getVoorvoegsel().getWaarde());
        Assert.assertEquals("Scheidingsteken moet aanwezig zijn", Character.valueOf(' '), persoon.getScheidingsteken().getWaarde());
        Assert.assertEquals("Achternaam moet gelijk zijn", "achternaam", persoon.getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals("Geslacht moet gelijk zijn", "M", persoon.getGeslachtsaanduidingCode().getWaarde());
    }

    @Test
    public void testConverteerEenCategorie() {
        Lo3PersoonInhoud nieuw = maakPersoon("100", "100", "voornaam", "G", "voor de", "achternaam", 19700101, "0599", "6030", "M");
        Lo3Categorie<Lo3PersoonInhoud> categorieNieuw = new Lo3Categorie<>(nieuw, null, new Lo3Historie(null, null, null), null);
        Lo3Stapel<Lo3PersoonInhoud> stapel = new Lo3Stapel<>(Collections.singletonList(categorieNieuw));
        BrpToevalligeGebeurtenisNaamGeslacht persoon = subject.converteer(stapel);
        Assert.assertNull("persoon moet null zijn", persoon);
    }

    @Test
    public void testConverteerNull() {
        Assert.assertNull("persoon moet null zijn", subject.converteer(null));
    }

    private Lo3PersoonInhoud maakPersoon(
            final String anummer,
            final String bsn,
            final String voornaam,
            final String titelPredicaat,
            final String voorvoegsel,
            final String achternaam,
            final Integer geboorteDatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String geslacht) {
        return new Lo3PersoonInhoud(
                anummer == null ? null : new Lo3String(anummer),
                bsn == null ? null : new Lo3String(bsn),
                voornaam == null ? null : new Lo3String(voornaam),
                titelPredicaat == null ? null : new Lo3AdellijkeTitelPredikaatCode(titelPredicaat),
                voorvoegsel == null ? null : new Lo3String(voorvoegsel),
                achternaam == null ? null : new Lo3String(achternaam),
                geboorteDatum == null ? null : new Lo3Datum(geboorteDatum),
                geboorteGemeenteCode == null ? null : new Lo3GemeenteCode(geboorteGemeenteCode),
                geboorteLandCode == null ? null : new Lo3LandCode(geboorteLandCode),
                geslacht == null ? null : new Lo3Geslachtsaanduiding(geslacht),
                null,
                null,
                new Lo3AanduidingNaamgebruikCode("J"));
    }
}
