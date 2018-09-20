/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.testutils.StapelUtils;
import nl.moderniseringgba.migratie.testutils.VerplichteStapel;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus43OuderTest extends CasusTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

    {
        final Lo3TestObject<Lo3OuderInhoud> lo1 = new Lo3TestObject<Lo3OuderInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3OuderInhoud> lo2 = new Lo3TestObject<Lo3OuderInhoud>(Document.AKTE);

        // @formatter:off
        // 1000000456     1-2-2000    -   1-2-1990    3-2-1990    Rotterdam   A2
        // 1000000123     1-1-1990    -   1-2-1990    2-2-1990    Rotterdam   A1
        
        // LO3 input
        lo2.vul(buildOuder(1000000456L, 20000201), null, 20000201, 20000202, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 1));
        lo1.vul(buildOuder(1000000123L, VerplichteStapel.GEBOORTE_DATUM), null, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0));
        // @formatter:on

        vulCategorieen(lo1, lo2);
    }

    @Override
    protected Lo3Stapel<Lo3OuderInhoud> maakOuder1Stapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3OuderInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3OuderInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3OuderInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo
                    .getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final List<BrpRelatie> familieRelaties =
                brpPersoonslijst.getRelaties(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING);
        assertEquals(1, familieRelaties.size());

        final BrpRelatie ouderRelatie = familieRelaties.get(0);
        assertNotNull(ouderRelatie);
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, ouderRelatie.getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.KIND, ouderRelatie.getRolCode());

        assertNull(ouderRelatie.getRelatieStapel());

        final List<BrpBetrokkenheid> betrokkenheden = ouderRelatie.getBetrokkenheden();
        assertNotNull(betrokkenheden);
        // 3 betrokkenheden verwacht: 2 ouders in ouder1 categorie, 1 ouder in ouder2 categorie
        assertEquals(3, betrokkenheden.size());

        for (final BrpBetrokkenheid betrokkenheid : betrokkenheden) {
            assertEquals(BrpSoortBetrokkenheidCode.OUDER, betrokkenheid.getRol());

            assertNotNull(betrokkenheid.getIdentificatienummersStapel());
            assertEquals(1, betrokkenheid.getIdentificatienummersStapel().size());

            final Long aNummer =
                    betrokkenheid.getIdentificatienummersStapel().get(0).getInhoud().getAdministratienummer();

            if (aNummer.longValue() == 1000000123L) {
                assertOuder1a(betrokkenheid);

            } else if (aNummer.longValue() == 1000000456L) {
                assertOuder1b(betrokkenheid);

            }
            // else: andere ouder, die wordt buiten beschouwing gelaten

        }
    }

    private void assertOuder1a(final BrpBetrokkenheid betrokkenheid) {
        // Zou ouder record van 01-01-1990 tot 01-02-2000 moeten hebben
        assertNotNull(betrokkenheid.getOuderStapel());
        assertEquals(1, betrokkenheid.getOuderStapel().size());
        final BrpGroep<BrpOuderInhoud> ouder = betrokkenheid.getOuderStapel().get(0);

        assertEquals(new BrpDatum(19900101), ouder.getHistorie().getDatumAanvangGeldigheid());
        assertEquals(new BrpDatum(20000201), ouder.getHistorie().getDatumEindeGeldigheid());
    }

    private void assertOuder1b(final BrpBetrokkenheid betrokkenheid) {
        // Zou ouder record van 01-02-2000 moeten hebben
        assertNotNull(betrokkenheid.getOuderStapel());
        assertEquals(1, betrokkenheid.getOuderStapel().size());
        final BrpGroep<BrpOuderInhoud> ouder = betrokkenheid.getOuderStapel().get(0);

        assertEquals(new BrpDatum(20000201), ouder.getHistorie().getDatumAanvangGeldigheid());
        assertEquals(null, ouder.getHistorie().getDatumEindeGeldigheid());
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final Lo3Persoonslijst lo3 = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3);

        LOG.debug("\n\n\nRelaties:\n{}\n\n\n", brpPersoonslijst.getRelaties());

        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        assertNotNull(terug);

        Lo3StapelHelper.vergelijk(lo3.getOuder1Stapels(), terug.getOuder1Stapels());
    }
}
