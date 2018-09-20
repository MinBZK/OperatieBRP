/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import static nl.bzk.brp.testdatageneratie.RandomService.isFractie;
import static nl.bzk.brp.testdatageneratie.datagenerators.PersIndicatieGenerator.creeerIndicatie;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.datagenerators.PersAdresGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.PersGenerator;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersaanschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhgem;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerseuverkiezingen;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersimmigratie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnation;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersopschorting;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerspk;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersuitslnlkiesr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Nation;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Persgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Persreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Persverificatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnvervallenreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtverificatie;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class TestDataGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(TestDataGenerator.class);
    private final int numberOfRecordsToProcess;
    private final int batchBlockSize;
    private final Huwelijksboot huwelijksboot;
    private final PersGenerator persGenerator;
    private final int threadIndex;

    public TestDataGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex,
            final int rangeSize, final int threadBlockSize, final Huwelijksboot huwelijksboot)
    {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
        this.huwelijksboot = huwelijksboot;
        persGenerator = new PersGenerator(this.threadIndex = threadIndex, rangeSize, threadBlockSize);
    }

    @Override
    public Boolean call() {

        try {
            Thread.sleep(500 * threadIndex);
        } catch (InterruptedException e) {
            log.info(e.getMessage());
            return false;
        }

        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                voegPersoonToe(kernSession);

                if (i % batchBlockSize == 0) {
                    log.debug(threadIndex+":"+i);
                    if (Settings.SAVE) {
                        kernSession.getTransaction().commit();
                        kernSession.clear();
                        kernSession.getTransaction().begin();
                    }
                }
            }

            log.info("####################################");

            kernSession.getTransaction().commit();
        } finally {
            if (kernSession != null) try {
                kernSession.close();
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
        return true;
    }

    private void voegPersoonToe(final Session kernSession) {
        String[] voornamen = RandomService.getVoornamen();
        Pers pers = persGenerator.generatePers(voornamen);

        if (Settings.SAVE) {
            kernSession.save(pers);
            huwelijksboot.toevoegenIndienUitverkoren(pers);
        }

        voegVoornamenToe(kernSession, voornamen, pers);

        voegGeslachtsnamenToe(kernSession, pers);

        boolean isStatenloos = isFractie(5);
        if (!isStatenloos) {
            voegNationalieitenToe(kernSession, pers);
        }

        voegReisdocumentenToe(kernSession, pers);

        Persadres adres = voegAdresToe(kernSession, pers);

        voegIndicatiesToe(kernSession, pers, isStatenloos);

        if (adres.getDatvertrekuitnederland() != null) {
            voegVerificatiesToe(kernSession, pers);
        }

        if (Settings.SAVE) {
            kernSession.save(new HisPersids(pers));
            kernSession.save(new HisPersgeslachtsaand(pers));
            kernSession.save(new HisPerssamengesteldenaam(pers));
            kernSession.save(new HisPersaanschr(pers));
            kernSession.save(new HisPersgeboorte(pers));
            if (pers.getDatoverlijden() != null) kernSession.save(new HisPersoverlijden(pers));
            if (pers.getInduitslnlkiesr() != null) kernSession.save(new HisPersuitslnlkiesr(pers));
            if (pers.getInddeelneuverkiezingen() != null) kernSession.save(new HisPerseuverkiezingen(pers));
            if (pers.getRdnopschorting() != null) kernSession.save(new HisPersopschorting(pers));
            if (pers.getIndpkvollediggeconv() != null) kernSession.save(new HisPerspk(pers));
            if (pers.getDatvestiginginnederland() != null) kernSession.save(new HisPersimmigratie(pers));
        }

    }

    private void voegVerificatiesToe(final Session kernSession, final Pers pers) {
        int aantal = RandomService.random.nextInt(3);
        for (int i = 0; i < aantal; i++) {
            voegVerificatieToe(kernSession, pers);
        }
    }

    private void voegVerificatieToe(final Session kernSession, final Pers pers) {
        Persverificatie verificatie = new Persverificatie();
        verificatie.setPers(pers);
        if (isFractie(2)) verificatie.setSrtverificatie(MetaRepo.get(Srtverificatie.class));
        if (isFractie(2)) verificatie.setDat(RandomService.randomDate());
        if (Settings.SAVE) {
            kernSession.save(verificatie);
            kernSession.save(new HisPersverificatie(verificatie));
        }
    }

    private void voegGeslachtsnamenToe(final Session kernSession, final Pers pers) {
        List<Persgeslnaamcomp> geslachtsnaamComponenten = new ArrayList<Persgeslnaamcomp>();
        StringTokenizer tokens = new StringTokenizer(pers.getGeslnaam(), " ");
        for (int i = 1; tokens.hasMoreTokens(); i++) {
            String token = tokens.nextToken();
            if (isHoofdletter(token.charAt(0))) {
                Persgeslnaamcomp persgeslnaamcomp = new Persgeslnaamcomp(pers, persGenerator.getVoorvoegsel(), token, i);
                persgeslnaamcomp.setAdellijketitel(pers.getAdellijketitel());
                persgeslnaamcomp.setPredikaat(pers.getPredikaatByPredikaat());
                geslachtsnaamComponenten.add(persgeslnaamcomp);
            }
        }
        if (Settings.SAVE) {
            for (Persgeslnaamcomp persgeslnaamcomp : geslachtsnaamComponenten) {
                kernSession.save(persgeslnaamcomp);
                kernSession.save(new HisPersgeslnaamcomp(persgeslnaamcomp));
            }
        }
    }

    private boolean isHoofdletter(final char letter) {
        return letter >= 'A' && letter <= 'Z';
    }

    private void voegVoornamenToe(final Session kernSession, final String[] voornamen, final Pers pers) {
        for (int i = 0; i < voornamen.length; i++) {
            Persvoornaam persVoornaam = new Persvoornaam();
            persVoornaam.setNaam(voornamen[i]);
            persVoornaam.setPers(pers);
            persVoornaam.setVolgnr(i+1);
            if (Settings.SAVE) {
                kernSession.save(persVoornaam);
                kernSession.save(new HisPersvoornaam(persVoornaam));
            }
        }
    }

    private void voegNationalieitenToe(final Session kernSession, final Pers pers) {
        Nation nationaliteit = RandomService.getNationaliteit();
        if (nationaliteit != null) {
            voegNationalieitToe(kernSession, pers, nationaliteit);
        }
        if (isFractie(80)) {
            Nation nationaliteit2;
            do {
                nationaliteit2 = RandomService.getNationaliteit();
            } while (nationaliteit2 == nationaliteit);
            if (nationaliteit2 != null) {
                voegNationalieitToe(kernSession, pers, nationaliteit2);
            }
        }
    }

    private void voegNationalieitToe(final Session kernSession, final Pers pers, final Nation nationaliteit) {
        Persnation persnation = new Persnation();
        persnation.setNation(nationaliteit);
        persnation.setPers(pers);
        if (nationaliteit.isNederlandse()) {
            persnation.setRdnverknlnation(RandomService.getNationaliteitVerkrijgReden());
            if (isFractie(5226)) {
                persnation.setRdnverliesnlnation(RandomService.getNationaliteitVerliesReden());
            }
        }
        if (Settings.SAVE) kernSession.save(persnation);

        HisPersnation hisPersnationActueel = new HisPersnation(persnation);

        if (persnation.getRdnverliesnlnation() != null) {
            Date verliesmoment = RandomService.getPastTimestamp();
            hisPersnationActueel.setDateindegel(GenUtil.naarBrpDatum(verliesmoment));

            HisPersnation hisPersnationOud = new HisPersnation(persnation);
            hisPersnationOud.setTsverval(verliesmoment);
            if (Settings.SAVE) kernSession.save(hisPersnationOud);
        }

        if (Settings.SAVE) kernSession.save(hisPersnationActueel);
    }

    private void voegReisdocumentenToe(final Session kernSession, final Pers pers) {
        int aantal = RandomService.random.nextInt(6);
        for (int i = 0; i < aantal; i++) {
            voegReisdocumentToe(kernSession, pers);
        }
    }

    private void voegReisdocumentToe(final Session kernSession, final Pers pers) {
        Persreisdoc persreisdoc = new Persreisdoc();
        persreisdoc.setSrtnlreisdoc(RandomService.getReisdocumentSoort());
        if (!RandomService.isFractie(3)) {
            persreisdoc.setRdnvervallenreisdoc(MetaRepo.get(Rdnvervallenreisdoc.class));
        }
        persreisdoc.setAutvanafgiftereisdoc(RandomService.getAutvanafgiftereisdoc());
        persreisdoc.setPers(pers);
        if (isFractie(100)) {
            persreisdoc.setDatinhingvermissing(RandomService.randomDate());
        }
        persreisdoc.setDatingangdoc(RandomService.randomDate());
        persreisdoc.setDatuitgifte(RandomService.randomDate());
        persreisdoc.setDatvoorzeeindegel(RandomService.randomDate());
        if (!isFractie(5)) {
            persreisdoc.setLengtehouder(RandomService.randomLengtehouder());
        }
        persreisdoc.setNr(RandomStringUtils.randomAlphanumeric(9));
        if (Settings.SAVE) {
            kernSession.save(persreisdoc);
            kernSession.save(new HisPersreisdoc(persreisdoc));
        }
    }

    private Persadres voegAdresToe(final Session kernSession, final Pers pers) {
        Persadres adres = PersAdresGenerator.generatePersAdres(pers);
        if (Settings.SAVE) {
            kernSession.save(adres);
            HisPersadres[] hisPersAdressen = PersAdresGenerator.generateHisPersAdressen(adres);
            for (HisPersadres hisPersadres : hisPersAdressen) {
                kernSession.save(new HisPersbijhgem(hisPersadres));
                kernSession.save(hisPersadres);
            }
        }
        return adres;
    }

    private void voegIndicatiesToe(final Session kernSession, final Pers pers, final boolean isStatenloos) {
        List<Persindicatie> indicaties = creeerIndicaties(pers, isStatenloos);
        for (Persindicatie indicatie : indicaties) {
            if (Settings.SAVE) {
                kernSession.save(indicatie);
                kernSession.save(new HisPersindicatie(indicatie));
            }
        }
    }

    private List<Persindicatie> creeerIndicaties(final Pers pers, final boolean isStatenloos) {
        List<Persindicatie> resultaat = new ArrayList<Persindicatie>();
        EnumSet<Srtindicatie> indicatieSet = EnumSet.allOf(Srtindicatie.class);
        indicatieSet.remove(Srtindicatie.ORDINAL_NUL_NIET_GEBRUIKEN);

        if (isFractie(100)) {
            resultaat.add(creeerIndicatie(pers, Srtindicatie.VERSTREKKINGSBEPERKING));
        }
        indicatieSet.remove(Srtindicatie.VERSTREKKINGSBEPERKING);

        if (isFractie(26)) {
            resultaat.add(creeerIndicatie(pers, Srtindicatie.DERDE_HEEFT_GEZAG));
        }
        indicatieSet.remove(Srtindicatie.DERDE_HEEFT_GEZAG);

        if (isStatenloos) {
            resultaat.add(creeerIndicatie(pers, Srtindicatie.STATENLOOS));
        }
        indicatieSet.remove(Srtindicatie.STATENLOOS);

        for (Srtindicatie soortIndicatie : indicatieSet) {
            if (isFractie(1000)) {
                resultaat.add(creeerIndicatie(pers, soortIndicatie));
            }
        }
        return resultaat;
    }

}
