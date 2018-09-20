/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.behandeldAlsNederlander;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.vastgesteldNietNederlander;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import org.junit.Test;

/**
 * Test de conversie van Nationaliteit gegevens.
 */
public class BrpNationaliteitConversieTest extends AbstractBrpConversieTest {

    @Inject
    private BrpConverteerder brpConverteerder;

    @Test
    public void testGeenNationaliteit() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);

        assertTrue(lo3Pl.getNationaliteitStapels().isEmpty());
    }

    @Test
    public void testNlNationaliteit() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", "033", null);

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);

        assertEquals(1, lo3Pl.getNationaliteitStapels().size());
    }

    @Test
    public void testNlNationaliteitPlusBVP1() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", "033", null);
        vulBehandeldAlsNederlander(builder, his(20010101, 20010102000000L));

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);

        assertEquals(1, lo3Pl.getNationaliteitStapels().size());
    }

    @Test
    public void testBeNationaliteitPlusBVP1() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0052", null, null);
        vulBehandeldAlsNederlander(builder, his(20010101, 20010102000000L));

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);

        assertEquals(2, lo3Pl.getNationaliteitStapels().size());
    }

    @Test
    public void testNlNationaliteitPlusBVP2() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0001", "033", null);
        vulVastgesteldNietNederlander(builder, his(20010101, 20010102000000L));

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);

        assertEquals(1, lo3Pl.getNationaliteitStapels().size());
    }

    @Test
    public void testBeNationaliteitPlusBVP2() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        vulVerplichteStapels(builder);

        vulNationaliteit(builder, "0052", null, null);
        vulVastgesteldNietNederlander(builder, his(20010101, 20010102000000L));

        final BrpPersoonslijst brpPersoonslijst = builder.build();
        final Lo3Persoonslijst lo3Pl = brpConverteerder.converteer(brpPersoonslijst);

        assertEquals(2, lo3Pl.getNationaliteitStapels().size());
    }

    protected void vulBehandeldAlsNederlander(final BrpPersoonslijstBuilder builder, final BrpHistorie historie) {
        final List<BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>> bvpGroepen = new ArrayList<>();
        final BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> bvpGroep =
                new BrpGroep<>(behandeldAlsNederlander(true), historie, act(1, 20100102), null, null);
        bvpGroepen.add(bvpGroep);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> bvpStapel = new BrpStapel<>(bvpGroepen);
        builder.behandeldAlsNederlanderIndicatieStapel(bvpStapel);
    }

    protected void vulVastgesteldNietNederlander(final BrpPersoonslijstBuilder builder, final BrpHistorie historie) {
        final List<BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>> bvpGroepen = new ArrayList<>();
        final BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> bvpGroep =
                new BrpGroep<>(vastgesteldNietNederlander(true), historie, act(1, 20100102), null, null);
        bvpGroepen.add(bvpGroep);
        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> bvpStapel = new BrpStapel<>(bvpGroepen);
        builder.vastgesteldNietNederlanderIndicatieStapel(bvpStapel);
    }
}
