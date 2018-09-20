/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.serialisatie.JacksonJsonSerializer;
import nl.bzk.brp.util.StamgegevenBuilder;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.KindHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Daar de serialisatie en deserialisatie middels Jackson concurrency problemen kan geven (omdat
 * sommige Jackson classes niet thread-safe zijn ten tijde van de configuratie), is hier een speciale unit test
 * voor toegevoegd die, bij problemen, faalt met timeouts en/of andere excepties.
 */
public class PersoonSerialisatieConcurrencyTest {

    private static final int AANTAL_PERSONEN     = 50;
    private static final int AANTAL_THREADS      = 50;
    private static final int MAX_THREADS_TIMEOUT = 15000;

    private final JacksonJsonSerializer<PersoonHisVolledigImpl> serializer =
        new PersoonHisVolledigStringSerializer();

    @Test
    public void testConcurrentSerialisatie() {
        final List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < AANTAL_THREADS; i++) {
            final Thread thread = new Thread(new SerialiseerTask());
            threads.add(thread);
            thread.start();
        }

        wachtTotAlleThreadsKlaarZijn(threads);
    }

    @Test
    public void testConcurrentDeserialisatie() {
        final List<byte[]> blobs = bouwLijstVanPersoonBlobs();
        final List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < AANTAL_THREADS; i++) {
            final Thread thread = new Thread(new DeserialiseerTask(blobs));
            threads.add(thread);
            thread.start();
        }

        wachtTotAlleThreadsKlaarZijn(threads);
    }

    @Test
    public void testConcurrentSerialisatieEnDeserialisatie() {
        final List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < AANTAL_THREADS; i++) {
            final Thread thread = new Thread(new SerialiseerEnDeserialiseerTask());
            threads.add(thread);
            thread.start();
        }

        wachtTotAlleThreadsKlaarZijn(threads);
    }

    /**
     * Bouwt een lijst van personen blobs (dus JSON geserialiseerde personen) op en retourneert deze.
     *
     * @return een lijst van personen blobs.
     */
    private List<byte[]> bouwLijstVanPersoonBlobs() {
        final List<byte[]> blobs = new ArrayList<>();
        for (int persoonId = 1; persoonId <= AANTAL_PERSONEN; persoonId++) {
            blobs.add(serializer.serialiseer(bouwNieuwPersoon(persoonId, true)));
        }
        return blobs;
    }

    /**
     * Wacht tot alle threads in de opgegeven lijst zijn afgerond of totdat er meer dan het standaard aantal
     * secondes (timeout) is verlopen. Indien niet alle threads klaar zijn voordat de timeout optreedt, zal
     * deze methode een 'fail' veroorzaken en alle nog lopende threads stoppen.
     *
     * @param threads een lijst van threads.
     */
    private void wachtTotAlleThreadsKlaarZijn(final List<Thread> threads) {
        final long timeout = System.currentTimeMillis() + MAX_THREADS_TIMEOUT;

        int running;
        do {
            running = 0;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    running++;
                }
            }
        } while (running > 0 && System.currentTimeMillis() < timeout);

        if (running > 0) {
            int interrupted = 0;
            for (Thread thread : threads) {
                if (thread.isInterrupted()) {
                    interrupted++;
                }
                if (thread.isAlive()) {
                    thread.interrupt();
                }
            }
            Assert.fail(String.format(
                    "Er waren nog %d threads actief na de timeout van %d seconde (en %s daarvan waren interrupted).",
                    running, MAX_THREADS_TIMEOUT / 1000, interrupted));
        }
    }

    /**
     * Instantieert en retourneert een Persoon met opgegeven persoon Id. Indien de <code>inclFamilie</code> parameter op
     * <code>true</code> staat zal er ook nog een familierechtelijke betrekking worden aangemaakt
     * voor de nieuwe persoon (met de persoon in de rol van kind) met daarin ook nog andere (nieuwe personen) als
     * ouders.
     *
     * @param persoonId   de id van de nieuw toe te voegen persoon.
     * @param inclFamilie of er ook een familie rechtelijke betrekking aangemaakt dient te worden.
     * @return een nieuwe Persoon.
     */
    private PersoonHisVolledigImpl bouwNieuwPersoon(final int persoonId, final boolean inclFamilie) {
        final PersoonHisVolledigImplBuilder builder =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeboorteRecord(maakActie())
                        .datumGeboorte(20110101)
                        .landGebiedGeboorte((short) 6030)
                        .eindeRecord()
                        .nieuwNaamgebruikRecord(maakActie())
                        .adellijkeTitelNaamgebruik("Koning")
                        .eindeRecord()
                        .nieuwIdentificatienummersRecord(maakActie())
                        .administratienummer(1234567890L + persoonId)
                        .burgerservicenummer(123456789 + persoonId)
                        .eindeRecord()
                        .nieuwBijhoudingRecord(maakActie())
                        .bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
                        .bijhoudingspartij(34)
                        .indicatieOnverwerktDocumentAanwezig(JaNeeAttribuut.NEE)
                        .eindeRecord()
                        .nieuwInschrijvingRecord(maakActie())
                        .datumInschrijving(20010101)
                        .versienummer(2L)
                        .eindeRecord()
                        .voegPersoonAdresToe(
                                new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(maakActie())
                                        .aangeverAdreshouding("P").afgekorteNaamOpenbareRuimte("kort")
                                        .buitenlandsAdresRegel3("buitenlandje")
                                        .locatieTenOpzichteVanAdres(LocatieTenOpzichteVanAdres.TO).eindeRecord()
                                        .build())
                        .voegPersoonNationaliteitToe(
                                new PersoonNationaliteitHisVolledigImplBuilder(
                                        StamgegevenBuilder.bouwDynamischStamgegeven(
                                                Nationaliteit.class, 6030)).nieuwStandaardRecord(maakActie())
                                        .redenVerkrijging((short) 321).redenVerlies((short) 4763).eindeRecord()
                                        .build());

        final PersoonHisVolledigImpl persoon = builder.build();
        ReflectionTestUtils.setField(persoon, "iD", persoonId);
        if (inclFamilie) {
            bouwNieuweFamilierechtelijkeBetrekking(persoonId, persoon);
        }
        return persoon;
    }

    /**
     * Voegt een familierechtelijke betrekking tot aan de opgegeven persoon (met als rol kind) en twee nieuw
     * toe gevoegde personen in de rol van ouder.
     *
     * @param persoonId het id van de persoon.
     * @param persoon   de persoon waaraan de familie rechtelijke betrekking wordt toegevoegd.
     */
    private void bouwNieuweFamilierechtelijkeBetrekking(final int persoonId, final PersoonHisVolledigImpl persoon) {
        final FamilierechtelijkeBetrekkingHisVolledigImpl familie =
                new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();
        ReflectionTestUtils.setField(familie, "iD", persoonId);

        final KindHisVolledigImpl kindBetrokkenheid = new KindHisVolledigImplBuilder(familie, persoon).build();
        persoon.getBetrokkenheden().add(kindBetrokkenheid);
        ReflectionTestUtils.setField(kindBetrokkenheid, "iD", persoonId);

        final PersoonHisVolledigImpl ouder1 = bouwNieuwPersoon(persoonId + 1000, false);
        final PersoonHisVolledigImpl ouder2 = bouwNieuwPersoon(persoonId + 2000, false);
        final OuderHisVolledigImpl ouderBetrokkenheid1 =
                new OuderHisVolledigImplBuilder(familie, ouder1).nieuwOuderschapRecord(maakActie()).indicatieOuder(Ja.J)
                        .eindeRecord().nieuwOuderlijkGezagRecord(maakActie()).indicatieOuderHeeftGezag(true)
                        .eindeRecord()
                        .build();
        final OuderHisVolledigImpl ouderBetrokkenheid2 =
                new OuderHisVolledigImplBuilder(familie, ouder2).nieuwOuderschapRecord(maakActie()).indicatieOuder(Ja.J)
                        .eindeRecord().nieuwOuderlijkGezagRecord(maakActie()).indicatieOuderHeeftGezag(true)
                        .eindeRecord()
                        .build();

        familie.getBetrokkenheden().add(ouderBetrokkenheid1);
        familie.getBetrokkenheden().add(ouderBetrokkenheid2);
        ouder1.getBetrokkenheden().add(ouderBetrokkenheid1);
        ouder2.getBetrokkenheden().add(ouderBetrokkenheid2);
        ReflectionTestUtils.setField(ouder1, "iD", persoonId + 1000);
        ReflectionTestUtils.setField(ouder2, "iD", persoonId + 2000);
        ReflectionTestUtils.setField(ouderBetrokkenheid1, "iD", persoonId + 1000);
        ReflectionTestUtils.setField(ouderBetrokkenheid2, "iD", persoonId + 2000);
    }

    /**
     * Instantieert en retourneert een standaard actie met standaard gegevens.
     *
     * @return een actie.
     */
    private ActieModel maakActie() {
        ActieBericht actieBericht = new ActieConversieGBABericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actieBericht.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new DatumAttribuut(20110101).toDate()));
        ActieModel actieModel = new ActieModel(actieBericht, null);
        ReflectionTestUtils.setField(actieModel, "iD", new Random().nextLong());
        return actieModel;
    }

    /**
     * Task om concurrent serialisaties uit te voeren.
     */
    private class SerialiseerTask implements Runnable {

        @Override
        public void run() {
            bouwLijstVanPersoonBlobs();
        }
    }


    /**
     * Task om concurrent deserialisaties uit te voeren.
     */
    private final class DeserialiseerTask implements Runnable {

        private final List<byte[]> blobs;

        private DeserialiseerTask(final List<byte[]> blobs) {
            this.blobs = blobs;
        }

        @Override
        public void run() {
            for (byte[] blob : blobs) {
                serializer.deserialiseer(blob);
            }
        }
    }


    /**
     * Task om concurrent serialisaties en deserialisaties uit te voeren.
     */
    private class SerialiseerEnDeserialiseerTask implements Runnable {

        @Override
        public void run() {
            for (int persoonId = 1; persoonId <= AANTAL_PERSONEN; persoonId++) {
                byte[] blob = serializer.serialiseer(bouwNieuwPersoon(persoonId, true));
                serializer.deserialiseer(blob);
            }
        }
    }

}
