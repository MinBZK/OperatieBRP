/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import static org.junit.Assert.fail;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpPersoonslijstServiceImplTest {
    @Spy
    private final SyncParameters syncParameters = new SyncParameters();

    @InjectMocks
    private BrpPersoonslijstServiceImpl subject;

    @Before
    public void setup() {
        syncParameters.setInitieleVulling(false);
    }

    @Test(expected = NullPointerException.class)
    public void persisteerPersoonslijstAnummerNull() {
        subject.persisteerPersoonslijst(maakBrpPersoonslijst(), null);
        fail("Er wordt een NullPointerException verwacht");
    }

    private BrpPersoonslijst maakBrpPersoonslijst() {
        return new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels()
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(new BrpNationaliteitCode("0001"), null, null, null, null, null, null)).build();
    }
}
