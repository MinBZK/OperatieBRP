/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime;

import javax.inject.Inject;

import java.text.ParseException;
import java.util.Properties;
import nl.moderniseringgba.migratie.init.runtime.domein.ConversieResultaat;
import nl.moderniseringgba.migratie.init.runtime.repository.GbavRepository;
import nl.moderniseringgba.migratie.init.runtime.repository.Lo3BerichtVerwerker;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:runtime-test-beans.xml")
public class GbavRepositoryTest {

    @Inject
    private GbavRepository gbavRepository;

    @Test
    public void findAlleLO3Berichten() {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-1990");
        config.put("datum.eind", "01-01-2013");

        berichtenTeller = 0;

        try {
            gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN,
                                               config,
                                               verwerker,
                                               1);
        } catch (final ParseException e) {
            Assert.fail("Vind bericht mislukt.");
        }

        Assert.assertTrue("Geen berichten gevonden!", berichtenTeller == 28);
    }

    @Test
    public void findGeenLO3Berichten() {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-2014");
        config.put("datum.eind", "01-01-2015");

        berichtenTeller = 0;

        try {
            gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN,
                                               config,
                                               verwerker,
                                               1);
        } catch (final ParseException e) {
            Assert.fail("Vind bericht mislukt.");
        }

        Assert.assertTrue("Onverwachts berichten gevonden!", berichtenTeller == 0);
    }

    @Test
    public void findGeenLO3BerichtenVoorStatus() {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-1990");
        config.put("datum.eind", "01-01-2013");

        berichtenTeller = 0;
        
        try {
            gbavRepository.verwerkLo3Berichten(ConversieResultaat.VERZONDEN,
                                               config,
                                               verwerker,
                                               1);
        } catch (final ParseException e) {
            Assert.fail("Vind bericht mislukt.");
        }

        Assert.assertTrue("Onverwachts berichten gevonden!", berichtenTeller == 0);
    }

    @Test(expected = NumberFormatException.class)
    public void findLO3BerichtenNumberFormatException() throws ParseException {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-2014");
        config.put("datum.eind", "01-01-2015");
        config.put("gemeente.code", "ddd");

        gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN,
                                           config,
                                           verwerker,
                                           1);
    }

    @Test(expected = ParseException.class)
    public void findLO3BerichtenParseException() throws ParseException {
        final Properties config = new Properties();
        config.put("datum.start", "01-01-a014");
        config.put("datum.eind", "01-01-2015");
        
        gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN,
                                           config,
                                           verwerker,
                                           1);
    }
    
    private int berichtenTeller;
    
    private final Lo3BerichtVerwerker verwerker = new Lo3BerichtVerwerker() {
        int teller = 0;

        @Override
        public void addLo3Bericht(final String lo3Bericht, final long aNummer) {
            teller += 1;
        }

        @Override
        public Void call() throws Exception {
            berichtenTeller += teller;
            teller = 0;
            return null;
        }
    };
}
