/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link BijhoudingBetrokkenheid}.
 */
public class BijhoudingBetrokkenheidTest {

    private BRPActie actie;
    private Betrokkenheid betrokkenheid;

    @Before
    public void setup() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);

        final Timestamp nu = Timestamp.from(Instant.now());
        final Partij partij = new Partij("partij", "000001");
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GEBOORTE, nu);
        actie = new BRPActie(SoortActie.REGISTRATIE_GEBORENE, administratieveHandeling, partij, nu);
    }

    @Test
    public void testVoegOuderschapToe_indicatieTrue() {
        assertEquals(0, betrokkenheid.getBetrokkenheidOuderHistorieSet().size());

        final OuderschapElement ouderschapElement = new ElementBuilder().maakOuderschapElement("ouderschap", true);

        final BijhoudingBetrokkenheid bijhoudingBetrokkenheid = BijhoudingBetrokkenheid.decorate(betrokkenheid);
        bijhoudingBetrokkenheid.voegOuderschapToe(ouderschapElement, actie, 20160101);

        final Set<BetrokkenheidOuderHistorie> ouderHistorieSet = betrokkenheid.getBetrokkenheidOuderHistorieSet();
        assertEquals(1, ouderHistorieSet.size());

        BetrokkenheidOuderHistorie historie = ouderHistorieSet.iterator().next();
        assertEquals(actie, historie.getActieInhoud());
        assertTrue(historie.getIndicatieOuderUitWieKindIsGeboren());
    }

    @Test
    public void testVoegOuderschapToe_indicatieFalse() {
        assertEquals(0, betrokkenheid.getBetrokkenheidOuderHistorieSet().size());

        final OuderschapElement ouderschapElement = new ElementBuilder().maakOuderschapElement("ouderschap", false);

        final BijhoudingBetrokkenheid bijhoudingBetrokkenheid = BijhoudingBetrokkenheid.decorate(betrokkenheid);
        bijhoudingBetrokkenheid.voegOuderschapToe(ouderschapElement, actie, 20160101);

        final Set<BetrokkenheidOuderHistorie> ouderHistorieSet = betrokkenheid.getBetrokkenheidOuderHistorieSet();
        assertEquals(1, ouderHistorieSet.size());

        BetrokkenheidOuderHistorie historie = ouderHistorieSet.iterator().next();
        assertEquals(actie, historie.getActieInhoud());
        assertFalse(historie.getIndicatieOuderUitWieKindIsGeboren());
    }
}
