/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.moderniseringgba.migratie.controle.impl.SelectieServiceImpl;
import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.ControleTypeEnum;
import nl.moderniseringgba.migratie.controle.rapport.Opties;
import nl.moderniseringgba.migratie.logging.service.LoggingService;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectieServiceTest {

    @Mock
    private BrpDalService brpDalService;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private SelectieServiceImpl selectieService;

    @Test
    public void selecteerAnummersAllePersonen() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Opties opties = new Opties();
            opties.setControleType(ControleTypeEnum.ALLE_PERSONEN);
            opties.setVanaf(dateFormat.parse("20020708"));
            opties.setTot(dateFormat.parse("20120708"));
            opties.setGemeenteCode(null);

            final List<Long> gbavAnummers = createAnummerLijst();
            gbavAnummers.add(Long.valueOf(987654321l));
            Mockito.when(loggingService.findLogs(opties.getVanaf(), opties.getTot(), null)).thenReturn(gbavAnummers);
            Mockito.when(brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), null)).thenReturn(
                    createAnummerLijst());

            final ControleRapport controleRapport = new ControleRapport();
            final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

            assertNotNull("Mag niet leeg zijn.", anummers);
            assertTrue("Mag niet leeg zijn.", !anummers.isEmpty());
            assertTrue("Anr klopt niet.", anummers.iterator().next().equals(Long.valueOf(123456789l)));
            assertTrue("", controleRapport.getAantalBrpPl() < controleRapport.getAantalGbavPl());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void selecteerAnummersGeenBrp() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Opties opties = new Opties();
            opties.setControleType(ControleTypeEnum.ALLE_PERSONEN);
            opties.setVanaf(dateFormat.parse("20020708"));
            opties.setTot(dateFormat.parse("20120708"));
            opties.setGemeenteCode(null);

            Mockito.when(loggingService.findLogs(opties.getVanaf(), opties.getTot(), null)).thenReturn(
                    createAnummerLijst());
            Mockito.when(brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), null)).thenReturn(null);

            final ControleRapport controleRapport = new ControleRapport();
            final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

            assertNotNull("Mag niet leeg zijn.", anummers);
            assertTrue("Mag niet leeg zijn.", !anummers.isEmpty());
            assertTrue("Anr klopt niet.", anummers.iterator().next().equals(Long.valueOf(123456789l)));
            assertTrue("Er zouden meer GBA plen dan BRP plen gevonden moeten zijn.",
                    controleRapport.getAantalBrpPl() < controleRapport.getAantalGbavPl());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void selecteerAnummersGeenGba() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Opties opties = new Opties();
            opties.setControleType(ControleTypeEnum.ALLE_PERSONEN);
            opties.setVanaf(dateFormat.parse("20020708"));
            opties.setTot(dateFormat.parse("20120708"));
            opties.setGemeenteCode(null);

            Mockito.when(loggingService.findLogs(opties.getVanaf(), opties.getTot(), null)).thenReturn(null);
            Mockito.when(brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), null)).thenReturn(
                    createAnummerLijst());

            final ControleRapport controleRapport = new ControleRapport();
            final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

            assertNotNull("Mag niet leeg zijn.", anummers);
            assertTrue("Mag niet leeg zijn.", !anummers.isEmpty());
            assertTrue("Anr klopt niet.", anummers.iterator().next().equals(Long.valueOf(123456789l)));
            assertTrue("Er zouden meer BRP plen dan GBA plen gevonden moeten zijn.",
                    controleRapport.getAantalBrpPl() > controleRapport.getAantalGbavPl());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void selecteerAnummersGemeente() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Opties opties = new Opties();
            opties.setControleType(ControleTypeEnum.GEMEENTE);
            opties.setVanaf(dateFormat.parse("20020708"));
            opties.setTot(dateFormat.parse("20120708"));
            opties.setGemeenteCode("1904");

            Mockito.when(loggingService.findLogs(opties.getVanaf(), opties.getTot(), "1904")).thenReturn(
                    createAnummerLijst());
            Mockito.when(brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), "1904")).thenReturn(
                    createAnummerLijst());

            final ControleRapport controleRapport = new ControleRapport();
            final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

            assertNotNull("Mag niet leeg zijn.", anummers);
            assertTrue("Mag niet leeg zijn.", !anummers.isEmpty());
            assertTrue("Anr klopt niet.", anummers.iterator().next().equals(Long.valueOf(123456789l)));
            assertTrue("", controleRapport.getAantalBrpPl() == controleRapport.getAantalGbavPl());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void selecteerAnummersRni() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Opties opties = new Opties();
            opties.setControleType(ControleTypeEnum.RNI);
            opties.setVanaf(dateFormat.parse("20020708"));
            opties.setTot(dateFormat.parse("20120708"));
            opties.setGemeenteCode(null);

            Mockito.when(loggingService.findLogs(opties.getVanaf(), opties.getTot(), "1999")).thenReturn(
                    createAnummerLijst());
            Mockito.when(brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), "1999")).thenReturn(
                    createAnummerLijst());

            final ControleRapport controleRapport = new ControleRapport();
            final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

            assertNotNull("Mag niet leeg zijn.", anummers);
            assertTrue("Mag niet leeg zijn.", !anummers.isEmpty());
            assertTrue("Anr klopt niet.", anummers.iterator().next().equals(Long.valueOf(123456789l)));
            assertTrue("", controleRapport.getAantalBrpPl() == controleRapport.getAantalGbavPl());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    @Test
    public void selecteerAnummersPersoon() {
        try {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            final Opties opties = new Opties();
            opties.setControleType(ControleTypeEnum.EEN_PERSOON);
            opties.setVanaf(dateFormat.parse("20020708"));
            opties.setTot(dateFormat.parse("20120708"));
            opties.setGemeenteCode(null);

            Mockito.when(loggingService.findLogs(opties.getVanaf(), opties.getTot(), null)).thenReturn(
                    createAnummerLijst());
            Mockito.when(brpDalService.zoekBerichtLogAnrs(opties.getVanaf(), opties.getTot(), null)).thenReturn(
                    createAnummerLijst());

            final ControleRapport controleRapport = new ControleRapport();
            final Set<Long> anummers = selectieService.selecteerPLen(opties, controleRapport);

            assertTrue("Moet leeg zijn.", anummers == null || anummers.isEmpty());
        } catch (final ParseException e) {
            fail("Er zou geen fout op moeten treden.");
        }
    }

    private List<Long> createAnummerLijst() {
        final List<Long> anummers = new ArrayList<Long>();
        anummers.add(Long.valueOf(123456789l));
        return anummers;
    }
}
