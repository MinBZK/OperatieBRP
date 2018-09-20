/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.AbstractReisdocumentTest;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test voor de terug conversie van BrpReisdocument/Signalering/BuitenlandsReisdocument naar Lo3Reisdocument.
 */
@Requirement(Requirements.CCA12)
public class BrpReisdocumentConverteerderTest extends AbstractReisdocumentTest {

    @Inject
    private BrpReisdocumentConverteerder converteerder;

    @Test
    public void testReisdocumentConversie() {
        final BrpReisdocumentInhoud inhoud =
                new BrpReisdocumentInhoud(
                    new BrpSoortNederlandsReisdocumentCode(SOORT_REISDOCUMENT),
                    new BrpString(NUMMER_NL_REISDOCUMENT),
                    new BrpDatum(DATUM_INGANG_GELDIGHEID, null),
                    new BrpDatum(DATUM_UITGIFTE_NL_REISDOCUMENT, null),
                    new BrpReisdocumentAutoriteitVanAfgifteCode(AUTORITEIT_VAN_AFGIFTE),
                    new BrpDatum(DATUM_EINDE_GELDIGHEID_NL_REISDOCUMENT, null),
                    new BrpDatum(DATUM_INHOUDING_OF_VERMISSING, null),
                    new BrpAanduidingInhoudingOfVermissingReisdocumentCode(AANDUIDING_INHOUDING_OF_VERMISSING));
        final BrpStapel<BrpReisdocumentInhoud> reisdocumentStapel = maakStapel(inhoud);

        final Lo3ReisdocumentInhoud lo3Inhoud = converteerNaarLo3(reisdocumentStapel);
        assertEquals(new Lo3SoortNederlandsReisdocument(SOORT_REISDOCUMENT), lo3Inhoud.getSoortNederlandsReisdocument());
        assertEquals(NUMMER_NL_REISDOCUMENT, Lo3String.unwrap(lo3Inhoud.getNummerNederlandsReisdocument()));
        assertEquals(new Lo3Datum(DATUM_UITGIFTE_NL_REISDOCUMENT), lo3Inhoud.getDatumUitgifteNederlandsReisdocument());
        assertEquals(new Lo3AutoriteitVanAfgifteNederlandsReisdocument(AUTORITEIT_VAN_AFGIFTE), lo3Inhoud.getAutoriteitVanAfgifteNederlandsReisdocument());
        assertEquals(new Lo3Datum(DATUM_EINDE_GELDIGHEID_NL_REISDOCUMENT), lo3Inhoud.getDatumEindeGeldigheidNederlandsReisdocument());
        assertEquals(new Lo3Datum(DATUM_INHOUDING_OF_VERMISSING), lo3Inhoud.getDatumInhoudingVermissingNederlandsReisdocument());
        assertEquals(
                new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(String.valueOf(AANDUIDING_INHOUDING_OF_VERMISSING)),
                lo3Inhoud.getAanduidingInhoudingVermissingNederlandsReisdocument());
        assertNull(lo3Inhoud.getSignalering());
    }

    @Test
    public void testSignalering() {
        final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud inhoud =
                new BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud(new BrpBoolean(true, null), null, null);

        final BrpStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> stapel = maakStapel(inhoud);
        final Lo3ReisdocumentInhoud lo3Inhoud = converteerNaarLo3(stapel);
        assertEquals(LO3_SIGNALERING, lo3Inhoud.getSignalering());

        assertNull(lo3Inhoud.getSoortNederlandsReisdocument());
        assertNull(lo3Inhoud.getNummerNederlandsReisdocument());
        assertNull(lo3Inhoud.getDatumUitgifteNederlandsReisdocument());
        assertNull(lo3Inhoud.getAutoriteitVanAfgifteNederlandsReisdocument());
        assertNull(lo3Inhoud.getDatumEindeGeldigheidNederlandsReisdocument());
        assertNull(lo3Inhoud.getDatumInhoudingVermissingNederlandsReisdocument());
        assertNull(lo3Inhoud.getAanduidingInhoudingVermissingNederlandsReisdocument());
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> maakStapel(final T inhoud) {
        return stapel(groep(inhoud, his(DATUM_INGANG_GELDIGHEID), act(9, 19940102)));
    }

    private <T extends BrpGroepInhoud> Lo3ReisdocumentInhoud converteerNaarLo3(final BrpStapel<T> brpStapel) {
        final Lo3Stapel<Lo3ReisdocumentInhoud> result = converteerder.converteer(brpStapel);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        final Lo3Categorie<Lo3ReisdocumentInhoud> voorkomen = result.get(0);
        Assert.assertNotNull(voorkomen);

        final Lo3ReisdocumentInhoud lo3Inhoud = voorkomen.getInhoud();
        assertFalse(lo3Inhoud.isLeeg());
        return lo3Inhoud;
    }
}
