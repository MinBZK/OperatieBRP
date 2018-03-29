/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;

/**
 * Unit test voor {@link BetrokkenheidDecorator}.
 */
public class BetrokkenheidDecoratorTest {

    @Test
    public void testZoekMatchendeOuderBetrokkenheidMatchedZichzelf() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);

        final BetrokkenheidDecorator matchingDecorator = decorator.zoekMatchendeOuderBetrokkenheid(Collections.singleton(decorator));
        assertNotNull(matchingDecorator);
    }

    @Test
    public void testZoekMatchendeOuderBetrokkenheidGeenMatchAanvang() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);
        final BetrokkenheidDecorator geenMatch = maakBetrokkenheid(20140101, null);

        final BetrokkenheidDecorator matchingDecorator = decorator.zoekMatchendeOuderBetrokkenheid(Collections.singleton(geenMatch));
        assertNull(matchingDecorator);
    }

    @Test
    public void testZoekMatchendeOuderBetrokkenheidGeenMatchEinde() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, 20150102);
        final BetrokkenheidDecorator geenMatch = maakBetrokkenheid(20150101, 20150103);

        final BetrokkenheidDecorator matchingDecorator = decorator.zoekMatchendeOuderBetrokkenheid(Collections.singleton(geenMatch));
        assertNull(matchingDecorator);
    }

    @Test
    public void testZoekMatchendeOuderBetrokkenheidMeerdereExactMatches() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);
        final BetrokkenheidDecorator mogelijkeMatch = maakBetrokkenheid(20150101, null);

        final Set<BetrokkenheidDecorator> mogelijkeMatches = new LinkedHashSet<>();
        mogelijkeMatches.add(decorator);
        mogelijkeMatches.add(mogelijkeMatch);
        decorator.zoekMatchendeOuderBetrokkenheid(mogelijkeMatches);
    }

    @Test
    public void testZoekMatchendeOuderBetrokkenheidExacteEnPartieleMatch() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);
        final BetrokkenheidDecorator exacteMatch = maakBetrokkenheid(20150101, null);
        final BetrokkenheidDecorator partieleMatch = maakBetrokkenheid(20150101, 20150301);

        final Set<BetrokkenheidDecorator> mogelijkeMatches = new LinkedHashSet<>();
        mogelijkeMatches.add(partieleMatch);
        mogelijkeMatches.add(exacteMatch);
        final BetrokkenheidDecorator matchingDecorator = decorator.zoekMatchendeOuderBetrokkenheid(mogelijkeMatches);

        assertNotNull(matchingDecorator);
        assertEquals(exacteMatch, matchingDecorator);
    }

    @Test
    public void testZoekMatchendeOuderBetrokkenheidPartieleMatch() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);
        final BetrokkenheidDecorator partieleMatch = maakBetrokkenheid(20150101, 20150301);
        final BetrokkenheidDecorator geenMatch = maakBetrokkenheid(20150301, null);

        final Set<BetrokkenheidDecorator> mogelijkeMatches = new LinkedHashSet<>();
        mogelijkeMatches.add(partieleMatch);
        mogelijkeMatches.add(geenMatch);
        final BetrokkenheidDecorator matchingDecorator = decorator.zoekMatchendeOuderBetrokkenheid(mogelijkeMatches);

        assertNotNull(matchingDecorator);
        assertEquals(partieleMatch, matchingDecorator);
    }

    @Test
    public void testZoekMatchendeOuderBetrokkenheidMeerderePartieleMatch() throws Exception {
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);
        final BetrokkenheidDecorator partieleMatch = maakBetrokkenheid(20150101, 20150301);
        final BetrokkenheidDecorator partieleMatch2 = maakBetrokkenheid(20150101, 20150601);
        final BetrokkenheidDecorator geenMatch = maakBetrokkenheid(20150301, null);

        final Set<BetrokkenheidDecorator> mogelijkeMatches = new LinkedHashSet<>();
        mogelijkeMatches.add(partieleMatch);
        mogelijkeMatches.add(partieleMatch2);
        mogelijkeMatches.add(geenMatch);
        final BetrokkenheidDecorator matchingDecorator = decorator.zoekMatchendeOuderBetrokkenheid(mogelijkeMatches);

        assertNull(matchingDecorator);
    }

    @Test
    public void testOverigeMethoden() {
        assertNull(BetrokkenheidDecorator.decorate(null));
        final BetrokkenheidDecorator decorator = maakBetrokkenheid(20150101, null);
        assertNotNull(decorator);
        assertEquals(SoortBetrokkenheid.OUDER, decorator.getSoortBetrokkenheid());
        assertNotNull(decorator.getRelatie());
        final PersoonDecorator persoonDecorator = decorator.getPersoonDecorator();
        assertNotNull(persoonDecorator);

        decorator.removePersoon();
        assertNull(decorator.getPersoonDecorator());

    }

    private BetrokkenheidDecorator maakBetrokkenheid(final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid) {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(betrokkenheid);
        ouderHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        ouderHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);

        betrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);
        betrokkenheid.setPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        return BetrokkenheidDecorator.decorate(betrokkenheid);
    }
}
