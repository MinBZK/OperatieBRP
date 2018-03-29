/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import nl.bzk.migratiebrp.conversie.model.testutils.VerplichteStapel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.junit.Test;

public class Casus43OuderTest extends AbstractCasusTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = new ArrayList<>();
    private final BrpActie actie1;
    private final BrpActie actie2;

    {
        final Lo3TestObject<Lo3OuderInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3OuderInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);

        // @formatter:off
        // 1000000456 1-2-2000 - 1-2-2000 2-2-2000 Rotterdam A2
        // 1000000123 1-1-1990 - 1-1-1990 2-1-1990 Rotterdam A1

        // LO3 input
        lo2.vul(buildOuder("1000000456", 20000201), null, 20000201, 20000202, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0));
        lo1.vul(buildOuder("1000000123", VerplichteStapel.GEBOORTE_DATUM),
                null,
                19900101,
                19900102,
                1,
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_52, 0, 1));
        // @formatter:on

        vulCategorieen(lo1, lo2);
        actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "1A");
        actie2 = buildBrpActie(BrpDatumTijd.fromDatum(20000202, null), "2A");
    }

    @Override
    protected Lo3Stapel<Lo3OuderInhoud> maakOuder1Stapel() {
        return StapelUtils.createStapel(categorieen);
    }

    @Override
    protected Lo3Stapel<Lo3OuderInhoud> maakOuder2Stapel() {
        final List<Lo3Categorie<Lo3OuderInhoud>> ouder2Cats = new ArrayList<>();

        final Lo3TestObject<Lo3OuderInhoud> ouder2 = new Lo3TestObject<>(Document.AKTE);
        ouder2.vul(new Lo3OuderInhoud(), null, 20000201, 20000202, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, 0));

        ouder2Cats.add(new Lo3Categorie<>(ouder2.getInhoud(), ouder2.getAkte(), ouder2.getHistorie(), ouder2.getLo3Herkomst()));
        return StapelUtils.createStapel(ouder2Cats);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3OuderInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3OuderInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final List<BrpRelatie> familieRelaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING);
        assertEquals(1, familieRelaties.size());

        final BrpRelatie ouderRelatie = familieRelaties.get(0);
        assertNotNull(ouderRelatie);
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, ouderRelatie.getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.KIND, ouderRelatie.getRolCode());

        final BrpStapel<BrpRelatieInhoud> relatieStapel = ouderRelatie.getRelatieStapel();
        assertNotNull(relatieStapel);
        assertEquals(1, relatieStapel.size());

        final List<BrpBetrokkenheid> betrokkenheden = ouderRelatie.getBetrokkenheden();
        assertNotNull(betrokkenheden);
        // 2 betrokkenheden verwacht: 2 ouders in ouder1 categorie, geen in ouder2 (juridisch geen ouder)
        assertEquals(2, betrokkenheden.size());

        for (final BrpBetrokkenheid betrokkenheid : betrokkenheden) {
            assertEquals(BrpSoortBetrokkenheidCode.OUDER, betrokkenheid.getRol());

            assertNotNull(betrokkenheid.getIdentificatienummersStapel());
            assertEquals(1, betrokkenheid.getIdentificatienummersStapel().size());

            final String aNummer = betrokkenheid.getIdentificatienummersStapel().get(0).getInhoud().getAdministratienummer().getWaarde();

            if (Objects.equals(aNummer, "1000000123")) {
                assertOuder1a(betrokkenheid);

            } else if (Objects.equals(aNummer, "1000000456")) {
                assertOuder1b(betrokkenheid);

            }
        }
    }

    private void assertOuder1a(final BrpBetrokkenheid betrokkenheid) {
        // Zou ouder record van 01-01-1990 tot 01-02-2000 moeten hebben
        assertNotNull(betrokkenheid.getOuderStapel());
        assertEquals(1, betrokkenheid.getOuderStapel().size());
        final BrpGroep<BrpOuderInhoud> ouder = betrokkenheid.getOuderStapel().get(0);

        assertEquals(new BrpDatum(19900101, null), ouder.getHistorie().getDatumAanvangGeldigheid());
        assertEquals(new BrpDatum(20000201, null), ouder.getHistorie().getDatumEindeGeldigheid());
        assertActie(actie1, ouder.getActieInhoud());
        assertActie(actie2, ouder.getActieGeldigheid());
        assertActie(null, ouder.getActieVerval());
    }

    private void assertOuder1b(final BrpBetrokkenheid betrokkenheid) {
        // Zou ouder record van 01-02-2000 moeten hebben
        assertNotNull(betrokkenheid.getOuderStapel());
        assertEquals(1, betrokkenheid.getOuderStapel().size());
        final BrpGroep<BrpOuderInhoud> ouder = betrokkenheid.getOuderStapel().get(0);

        assertEquals(new BrpDatum(20000201, null), ouder.getHistorie().getDatumAanvangGeldigheid());
        assertEquals(null, ouder.getHistorie().getDatumEindeGeldigheid());
        assertActie(actie2, ouder.getActieInhoud());
        assertActie(null, ouder.getActieGeldigheid());
        assertActie(null, ouder.getActieVerval());
    }

    @Override
    @Test
    public void testRondverteer() {
        final Lo3Persoonslijst lo3 = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3);

        LOG.debug("\n\n\nRelaties:\n{}\n\n\n", brpPersoonslijst.getRelaties());

        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        assertNotNull(terug);

        Lo3StapelHelper.vergelijk(lo3.getOuder1Stapel(), terug.getOuder1Stapel());
    }
}
