/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper;

import org.junit.Test;

public class BrpPersoonslijstValidatorTest {

    @Test
    @Preconditie(Precondities.PRE058)
    public void testValideerBehandeldAlsNederlanderVastgesteldNietNederlanderGelijk() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel =
                maakBehandeldAlsNederlanderStapel(historie);
        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel =
                maakVastgesteldNietNederlanderStapel(historie);
        try {
            BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
                    behandeldAlsNederlanderIndicatieStapel, vastgesteldNietNederlanderIndicatieStapel);
            fail("Exceptie verwacht omdat behandeld en vastgesteld beiden tegelijk geldig zijn.");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Precondities.PRE058.name()));
        }
    }

    @Test
    @Preconditie(Precondities.PRE058)
    public void testValideerBehandeldAlsNederlanderVastgesteldNietNederlanderNull1() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel =
                maakVastgesteldNietNederlanderStapel(historie);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(null,
                vastgesteldNietNederlanderIndicatieStapel);
    }

    @Test
    @Preconditie(Precondities.PRE058)
    public void testValideerBehandeldAlsNederlanderVastgesteldNietNederlanderNull2() {
        final BrpHistorie historie = BrpStapelHelper.his(20010101, 20010102000000L);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel =
                maakBehandeldAlsNederlanderStapel(historie);
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
                behandeldAlsNederlanderIndicatieStapel, null);
    }

    @Test
    @Preconditie(Precondities.PRE058)
    public void testValideerBehandeldAlsNederlanderVastgesteldNietNederlanderNull3() {
        BrpPersoonslijstValidator.valideerBehandeldAlsNederlanderVastgesteldNietNederlander(null, null);
    }

    // TODO: wel overlappende indicaties, maar een op indicatie false

    private BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> maakVastgesteldNietNederlanderStapel(
            final BrpHistorie... histories) {
        final List<BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>> groepen =
                new ArrayList<BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>>();
        final BrpVastgesteldNietNederlanderIndicatieInhoud inhoud =
                new BrpVastgesteldNietNederlanderIndicatieInhoud(true);
        for (final BrpHistorie historie : histories) {
            final BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud> groep =
                    new BrpGroep<BrpVastgesteldNietNederlanderIndicatieInhoud>(inhoud, historie, null, null, null);
            groepen.add(groep);
        }
        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> stapel =
                new BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>(groepen);
        return stapel;
    }

    private BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> maakBehandeldAlsNederlanderStapel(
            final BrpHistorie... histories) {
        final List<BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>>();
        final BrpBehandeldAlsNederlanderIndicatieInhoud inhoud = new BrpBehandeldAlsNederlanderIndicatieInhoud(true);
        for (final BrpHistorie historie : histories) {
            final BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud> groep =
                    new BrpGroep<BrpBehandeldAlsNederlanderIndicatieInhoud>(inhoud, historie, null, null, null);
            groepen.add(groep);
        }
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> stapel =
                new BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud>(groepen);
        return stapel;
    }
}
