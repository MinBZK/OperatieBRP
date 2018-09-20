/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterVoorkomenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import org.junit.Test;

public class LeesAutorisatieRegisterAntwoordBerichtTest extends AbstractSyncBerichtTest {

    @Test
    public void testOk() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesAutorisatieRegisterAntwoordBericht bericht = new LeesAutorisatieRegisterAntwoordBericht(maakLeesAutorisatieRegisterAntwoordType());
        bericht.setMessageId(MessageIdGenerator.generateId());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals("LeesAutorisatieRegisterAntwoord", bericht.getBerichtType());
        Assert.assertEquals(StatusType.OK, bericht.getStatus());
        Assert.assertNotNull(bericht.getAutorisatieRegister());
        Assert.assertEquals(2, bericht.getAutorisatieRegister().geefAlleAutorisaties().size());
        Assert.assertNull(bericht.getStartCyclus());
    }

    @Test
    public void testLeeg() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesAutorisatieRegisterAntwoordBericht bericht = new LeesAutorisatieRegisterAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.OK);

        Assert.assertEquals("LeesAutorisatieRegisterAntwoord", bericht.getBerichtType());
        Assert.assertEquals(StatusType.OK, bericht.getStatus());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNotNull(bericht.getAutorisatieRegister());
        Assert.assertEquals(0, bericht.getAutorisatieRegister().geefAlleAutorisaties().size());
    }

    private LeesAutorisatieRegisterAntwoordType maakLeesAutorisatieRegisterAntwoordType() {
        final LeesAutorisatieRegisterAntwoordType type = new LeesAutorisatieRegisterAntwoordType();
        type.setStatus(StatusType.OK);
        type.setAutorisatieRegister(new AutorisatieRegisterType());
        type.getAutorisatieRegister().getAutorisatie().add(maakAutorisatieRegisterVoorkomenType("580001", 123, 1, 1, null, null));
        type.getAutorisatieRegister().getAutorisatie().add(maakAutorisatieRegisterVoorkomenType("580002", 4565, null, null, 2, null));

        return type;
    }

    private AutorisatieRegisterVoorkomenType maakAutorisatieRegisterVoorkomenType(
        final String partijCode,
        final int leveringsautorisatieId,
        final Integer plaatsenAfnIndDienstId,
        final Integer verwijderenAfnIndDienstId,
        final Integer bevragenPersoonDienstId,
        final Integer bevragenAdresDienstId)
    {
        final AutorisatieRegisterVoorkomenType autorisatie = new AutorisatieRegisterVoorkomenType();
        autorisatie.setPartijCode(partijCode);
        autorisatie.setToegangLeveringsautorisatieId(leveringsautorisatieId);
        autorisatie.setPlaatsenAfnemersindicatiesDienstId(plaatsenAfnIndDienstId);
        autorisatie.setVerwijderenAfnemersindicatiesDienstId(verwijderenAfnIndDienstId);
        autorisatie.setBevragenPersoonDienstId(bevragenPersoonDienstId);
        autorisatie.setBevragenAdresDienstId(bevragenAdresDienstId);
        return autorisatie;
    }
}
