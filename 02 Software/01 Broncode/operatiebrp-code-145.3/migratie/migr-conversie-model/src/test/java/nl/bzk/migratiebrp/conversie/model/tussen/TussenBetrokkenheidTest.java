/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.testutils.EqualsAndHashcodeTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TussenBetrokkenheidTest {

    @Mock
    private TussenStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
    @Mock
    private TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
    @Mock
    private TussenStapel<BrpGeboorteInhoud> geboorteStapel;
    @Mock
    private TussenStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel;
    @Mock
    private TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
    @Mock
    private TussenStapel<BrpOuderInhoud> ouderStapel;

    public static TussenBetrokkenheid createTussenBetrokkenheid(BrpSoortBetrokkenheidCode sb) {
        Lo3Herkomst her = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        return new TussenBetrokkenheid(sb,
                (TussenStapel<BrpIdentificatienummersInhoud>) TussenTestUtil.createTussenStapel(BrpIdentificatienummersInhoudTest.createInhoud(), her),
                null, null, null, null, null);
    }

    @Test
    public void test() throws ReflectiveOperationException {
        final TussenBetrokkenheid subject =
                new TussenBetrokkenheid(
                        BrpSoortBetrokkenheidCode.OUDER,
                        identificatienummersStapel,
                        geslachtsaanduidingStapel,
                        geboorteStapel,
                        ouderlijkGezagStapel,
                        samengesteldeNaamStapel,
                        ouderStapel);

        Assert.assertEquals(BrpSoortBetrokkenheidCode.OUDER, subject.getRol());
        Assert.assertEquals(identificatienummersStapel, subject.getIdentificatienummersStapel());
        Assert.assertEquals(geslachtsaanduidingStapel, subject.getGeslachtsaanduidingStapel());
        Assert.assertEquals(geboorteStapel, subject.getGeboorteStapel());
        Assert.assertEquals(ouderlijkGezagStapel, subject.getOuderlijkGezagStapel());
        Assert.assertEquals(samengesteldeNaamStapel, subject.getSamengesteldeNaamStapel());
        Assert.assertEquals(ouderStapel, subject.getOuderStapel());

        EqualsAndHashcodeTester.testEqualsHashcodeAndToString(
                subject,
                new TussenBetrokkenheid(
                        BrpSoortBetrokkenheidCode.OUDER,
                        identificatienummersStapel,
                        geslachtsaanduidingStapel,
                        geboorteStapel,
                        ouderlijkGezagStapel,
                        samengesteldeNaamStapel,
                        ouderStapel),
                new TussenBetrokkenheid(
                        BrpSoortBetrokkenheidCode.KIND,
                        identificatienummersStapel,
                        geslachtsaanduidingStapel,
                        geboorteStapel,
                        ouderlijkGezagStapel,
                        samengesteldeNaamStapel,
                        ouderStapel));
    }
}
