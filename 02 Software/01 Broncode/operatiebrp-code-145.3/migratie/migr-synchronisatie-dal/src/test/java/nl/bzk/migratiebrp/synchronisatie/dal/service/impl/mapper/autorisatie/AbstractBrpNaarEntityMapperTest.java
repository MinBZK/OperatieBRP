/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Collections;

import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBrpNaarEntityMapperTest {

    @Mock
    protected DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    protected BRPActieFactory brpActieFactory;

    protected static <T extends BrpGroepInhoud> BrpStapel<T> stapel(final T inhoud) {
        final BrpHistorie historie = new BrpHistorie(null, null, BrpDatumTijd.fromDatum(19900102, null), null, null);
        final BrpActie actie =
                new BrpActie(
                        1L,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        BrpPartijCode.MIGRATIEVOORZIENING,
                        BrpDatumTijd.fromDatum(19900102, null),
                        null,
                        null,
                        1,
                        null);

        final BrpGroep<T> groep = new BrpGroep<>(inhoud, historie, actie, null, null);
        return new BrpStapel<>(Collections.singletonList(groep));
    }
}
