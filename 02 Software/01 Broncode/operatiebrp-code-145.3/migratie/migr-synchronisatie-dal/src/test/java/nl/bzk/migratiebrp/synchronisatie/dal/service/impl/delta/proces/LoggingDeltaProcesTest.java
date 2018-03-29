/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link LoggingDeltaProces}.
 */
public class LoggingDeltaProcesTest extends AbstractDeltaTest {

    private static final Timestamp TIMESTAMP = Timestamp.valueOf("2015-01-01 00:00:00.000");
    private DeltaBepalingContext context;
    private DeltaProces proces;
    private Persoon bestaandPersoon;
    private Persoon nieuwPersoon;
    private Lo3Bericht bestaandLo3Bericht;
    private Lo3Bericht nieuwLo3Bericht;

    @Before
    public void setUp() throws Exception {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);

        bestaandLo3Bericht = new Lo3Bericht("referentie", Lo3BerichtenBron.INITIELE_VULLING, TIMESTAMP, "bestaandeData", true);
        nieuwLo3Bericht = new Lo3Bericht("referentie2", Lo3BerichtenBron.INITIELE_VULLING, TIMESTAMP, "nieuweData", true);
        nieuwPersoon.addLo3Bericht(nieuwLo3Bericht);
        bestaandPersoon.addLo3Bericht(bestaandLo3Bericht);

        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, nieuwLo3Bericht, false);
        proces = new LoggingDeltaProces();
    }

    @Test
    public void testBepaalVerschillenOntkoppelenNietCat07Of13Voorkomens() {
        final BRPActie
                actie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonInschrijvingHistorieSet()).getActieInhoud();

        bestaandLo3Bericht.addVoorkomen("01", 0, 0, actie);
        assertFalse(bestaandLo3Bericht.getVoorkomens().isEmpty());
        final Lo3Voorkomen lo3Voorkomen = bestaandLo3Bericht.getVoorkomens().iterator().next();
        actie.setLo3Voorkomen(lo3Voorkomen);

        proces.bepaalVerschillen(context);
        assertNull(actie.getLo3Voorkomen());
        assertNull(lo3Voorkomen.getActie());
        assertTrue(bestaandLo3Bericht.getVoorkomens().isEmpty());
    }

    @Test
    public void testBepaalVerschillenOntkoppelenCat07Voorkomen() {
        final BRPActie
                actie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonInschrijvingHistorieSet()).getActieInhoud();
        bestaandLo3Bericht.addVoorkomen("07", 0, 0, actie);
        assertFalse(bestaandLo3Bericht.getVoorkomens().isEmpty());
        final Lo3Voorkomen lo3Voorkomen = bestaandLo3Bericht.getVoorkomens().iterator().next();
        actie.setLo3Voorkomen(lo3Voorkomen);

        proces.bepaalVerschillen(context);
        assertNotNull(actie.getLo3Voorkomen());
        assertNotNull(lo3Voorkomen.getActie());
        assertFalse(bestaandLo3Bericht.getVoorkomens().isEmpty());
    }

    @Test
    public void testBepaalVerschillenOntkoppelenCat13Voorkomen() {
        final BRPActie
                actie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonInschrijvingHistorieSet()).getActieInhoud();
        bestaandLo3Bericht.addVoorkomen("13", 0, 0, actie);
        assertFalse(bestaandLo3Bericht.getVoorkomens().isEmpty());
        final Lo3Voorkomen lo3Voorkomen = bestaandLo3Bericht.getVoorkomens().iterator().next();
        actie.setLo3Voorkomen(lo3Voorkomen);

        proces.bepaalVerschillen(context);
        assertNotNull(actie.getLo3Voorkomen());
        assertNotNull(lo3Voorkomen.getActie());
        assertFalse(bestaandLo3Bericht.getVoorkomens().isEmpty());
    }

    @Test
    public void testVerwerkVerschillen() {
        final BRPActie
                bestaandeActie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandPersoon.getPersoonInschrijvingHistorieSet()).getActieInhoud();
        bestaandLo3Bericht.addVoorkomen("01", 0, 0, bestaandeActie);

        final PersoonInschrijvingHistorie inschrijvingHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwPersoon.getPersoonInschrijvingHistorieSet());
        final BRPActie nieuweActie = inschrijvingHistorie.getActieInhoud();
        nieuwLo3Bericht.addVoorkomen("01", 0, 0, nieuweActie);

        assertTrue(bestaandPersoon.getLo3Berichten().size() == 1);
        assertEquals(bestaandLo3Bericht, bestaandPersoon.getLo3Berichten().iterator().next());
        assertEquals(nieuwPersoon, nieuwLo3Bericht.getPersoon());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = new HashMap<>();
        actieHerkomstMap.put(bestaandeActie, nieuwLo3Bericht.getVoorkomens().iterator().next());
        context.addActieHerkomstMapInhoud(actieHerkomstMap);

        proces.bepaalVerschillen(context);

        bestaandPersoon.getPersoonInschrijvingHistorieSet().add(inschrijvingHistorie);

        proces.verwerkVerschillen(context);
        assertTrue(bestaandPersoon.getLo3Berichten().size() == 2);
        Persoon gekoppeldPersoon = null;
        boolean bevatJuistBericht = false;
        for (final Lo3Bericht lo3Bericht : bestaandPersoon.getLo3Berichten()) {
            if (lo3Bericht == nieuwLo3Bericht) {
                gekoppeldPersoon = lo3Bericht.getPersoon();
                bevatJuistBericht = true;
            }
        }

        assertTrue(bevatJuistBericht);
        assertEquals(bestaandPersoon, gekoppeldPersoon);
    }
}
