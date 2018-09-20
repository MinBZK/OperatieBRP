/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonMetOnbekendeOuderDeserialisatieTest extends AbstractTestPersoonBuilderDeserialisatie {

    @Override
    protected void valideerObjecten(final PersoonHisVolledigImpl persoon, final PersoonHisVolledigImpl terugPersoon) {
        // then
        final RelatieHisVolledig relatie = terugPersoon.getBetrokkenheden().iterator().next().getRelatie();
        assertThat("relatie -> betrokkenheid heeft backreference",
            relatie.getBetrokkenheden().iterator().next().getRelatie(), is(relatie));

        assertThat("relatie -> (ouder) persoon referentie bestaat niet",
            relatie.getOuderBetrokkenheden().iterator().next().getPersoon(), nullValue());

        assertThat("relatie -> (kind) persoon referentie bestaat niet",
            (PersoonHisVolledigImpl) relatie.getKindBetrokkenheid().getPersoon(),
            allOf(notNullValue(), is(terugPersoon)));

        assertThat("persoon -> betrokkenheid heeft GEEN backreference",
            terugPersoon.getBetrokkenheden().iterator().next().getPersoon(),
            allOf(notNullValue(), is(terugPersoon)));
    }

    protected PersoonHisVolledigImpl maakPersoon() {
        final PersoonHisVolledigImpl persoon = maakBasisPersoon(1);

        final FamilierechtelijkeBetrekkingHisVolledigImpl fam = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        ReflectionTestUtils.setField(fam, "iD", 1234);

        final BetrokkenheidHisVolledigImpl famKind = new KindHisVolledigImpl(fam, persoon);
        ReflectionTestUtils.setField(famKind, "iD", 3241);
        persoon.getBetrokkenheden().add(famKind);

        final BetrokkenheidHisVolledigImpl famMoeder = new OuderHisVolledigImpl(fam, null);
        ReflectionTestUtils.setField(famMoeder, "iD", 4132);
        fam.getBetrokkenheden().add(famKind);
        fam.getBetrokkenheden().add(famMoeder);

        return persoon;
    }
}
