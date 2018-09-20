/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.RandomService.isFractie;
import static nl.bzk.brp.testdatageneratie.datagenerators.PersIndicatieGenerator.creeerIndicatie;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.Huwelijksboot;
import nl.bzk.brp.testdatageneratie.MetaRepo;
import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.Settings;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersAdresGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersaanschrGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersbijhgemGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersbijhverantwoordelijkGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersgeslachtsaandGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersgeslnaamcompGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersidsGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersimmigratieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersindicatieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersnationGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersopschortingGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPerssamengesteldenaamGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersverblijfsrGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersvoornaamGenerator;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersaanschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhgem;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersbijhverantwoordelijk;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerseuverkiezingen;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeboorte;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersids;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersimmigratie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersinschr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnation;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersopschorting;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersoverlijden;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerspk;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersuitslnlkiesr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverblijfsr;
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
import nl.bzk.brp.testdatageneratie.domain.kern.Verantwoordelijke;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class TestDataGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(TestDataGenerator.class);
    private static final HisPersAdresGenerator hisPersAdresGenerator = new HisPersAdresGenerator();
    private static final HisPersbijhgemGenerator hisPersbijhgemGenerator = new HisPersbijhgemGenerator();
    private static final HisPersnationGenerator hisPersnationGenerator = new HisPersnationGenerator();
    private static final HisPersidsGenerator hisPersidsGenerator = new HisPersidsGenerator();
    private static final HisPersgeslachtsaandGenerator hisPersgeslachtsaandGenerator = new HisPersgeslachtsaandGenerator();
    private static final HisPersgeslnaamcompGenerator hisPersgeslnaamcompGenerator = new HisPersgeslnaamcompGenerator();
    private static final HisPersvoornaamGenerator hisPersvoornaamGenerator = new HisPersvoornaamGenerator();
    private static final HisPerssamengesteldenaamGenerator hisPerssamengesteldenaamGenerator = new HisPerssamengesteldenaamGenerator();
    private static final HisPersaanschrGenerator hisPersaanschrGenerator = new HisPersaanschrGenerator();
    private static final HisPersbijhverantwoordelijkGenerator hisPersbijhverantwoordelijkGenerator = new HisPersbijhverantwoordelijkGenerator();
    private static final HisPersopschortingGenerator hisPersopschortingGenerator = new HisPersopschortingGenerator();
    private static final HisPersimmigratieGenerator hisPersimmigratieGenerator = new HisPersimmigratieGenerator();
    private static final HisPersverblijfsrGenerator hisPersverblijfsrGenerator = new HisPersverblijfsrGenerator();
    private static final HisPersindicatieGenerator hisPersindicatieGenerator = new HisPersindicatieGenerator();

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

        voegAdresToe(kernSession, pers);

        voegIndicatiesToe(kernSession, pers, isStatenloos);

        if (Verantwoordelijke.MINISTER.equals(pers.getVerantwoordelijke())) {
            voegVerificatiesToe(kernSession, pers);
        }

        if (Settings.SAVE) {
            List<HisPersids> hisPersidsList =
                    hisPersidsGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersids hisPersids : hisPersidsList) {
                        kernSession.save(hisPersids);
                }

            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersids = new Random().nextInt(hisPersidsList.size());
                HisPersids hisPersidsCorrectie = (HisPersids) HisCorrectieGenerator.creeerHisCorrectie(new HisPersids(pers), hisPersidsList.get(randomHisPersids));
                kernSession.save(hisPersidsCorrectie);
            }

            List<HisPersgeslachtsaand> hisPersgeslachtsaandList =
                    hisPersgeslachtsaandGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersgeslachtsaand hisPersgeslachtsaand : hisPersgeslachtsaandList) {
                        kernSession.save(hisPersgeslachtsaand);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersgeslachtsaand = new Random().nextInt(hisPersgeslachtsaandList.size());
                HisPersgeslachtsaand hisPersgeslachtsaandCorrectie = (HisPersgeslachtsaand) HisCorrectieGenerator.creeerHisCorrectie(new HisPersgeslachtsaand(pers), hisPersgeslachtsaandList.get(randomHisPersgeslachtsaand));
                kernSession.save(hisPersgeslachtsaandCorrectie);
            }

            List<HisPerssamengesteldenaam> hisPerssamengesteldenaamList =
                    hisPerssamengesteldenaamGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPerssamengesteldenaam hisPerssamengesteldenaam : hisPerssamengesteldenaamList) {
                        kernSession.save(hisPerssamengesteldenaam);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPerssamengesteldenaam = new Random().nextInt(hisPerssamengesteldenaamList.size());
                HisPerssamengesteldenaam hisPerssamengesteldenaamCorrectie = (HisPerssamengesteldenaam) HisCorrectieGenerator.creeerHisCorrectie(new HisPerssamengesteldenaam(pers), hisPerssamengesteldenaamList.get(randomHisPerssamengesteldenaam));
                kernSession.save(hisPerssamengesteldenaamCorrectie);
            }

            List<HisPersaanschr> hisPersaanschrList =
                    hisPersaanschrGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersaanschr hisPersaanschr : hisPersaanschrList) {
                        kernSession.save(hisPersaanschr);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersaanschr = new Random().nextInt(hisPersaanschrList.size());
                HisPersaanschr hisPersaanschrCorrectie = (HisPersaanschr) HisCorrectieGenerator.creeerHisCorrectie(new HisPersaanschr(pers), hisPersaanschrList.get(randomHisPersaanschr));
                kernSession.save(hisPersaanschrCorrectie);
            }

            HisPersgeboorte hisPersgeboorte = new HisPersgeboorte(pers);
            kernSession.save(hisPersgeboorte);
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisPersgeboorte hisPersgeboorteCorrectie = (HisPersgeboorte) HisCorrectieGenerator.creeerHisCorrectie(new HisPersgeboorte(pers), hisPersgeboorte);
                kernSession.save(hisPersgeboorteCorrectie);
            }

            HisPersinschr hisPersinschr = new HisPersinschr(pers);
            kernSession.save(hisPersinschr);
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisPersinschr hisPersinschrCorrectie = (HisPersinschr) HisCorrectieGenerator.creeerHisCorrectie(new HisPersinschr(pers), hisPersinschr);
                kernSession.save(hisPersinschrCorrectie);
            }

            List<HisPersbijhverantwoordelijk> hisPersbijhverantwoordelijkList =
                    hisPersbijhverantwoordelijkGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersbijhverantwoordelijk hisPersbijhverantwoordelijk : hisPersbijhverantwoordelijkList) {
                        kernSession.save(hisPersbijhverantwoordelijk);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersbijhverantwoordelijk = new Random().nextInt(hisPersbijhverantwoordelijkList.size());
                HisPersbijhverantwoordelijk hisPersbijhverantwoordelijkCorrectie = (HisPersbijhverantwoordelijk) HisCorrectieGenerator.creeerHisCorrectie(new HisPersbijhverantwoordelijk(pers), hisPersbijhverantwoordelijkList.get(randomHisPersbijhverantwoordelijk));
                kernSession.save(hisPersbijhverantwoordelijkCorrectie);
            }

            if (pers.getDatoverlijden() != null) {
                HisPersoverlijden hisPersoverlijden = new HisPersoverlijden(pers);
                kernSession.save(hisPersoverlijden);
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisPersoverlijden hisPersoverlijdenCorrectie = (HisPersoverlijden) HisCorrectieGenerator.creeerHisCorrectie(new HisPersoverlijden(pers), hisPersoverlijden);
                    kernSession.save(hisPersoverlijdenCorrectie);
                }
            }
            if (pers.getInduitslnlkiesr() != null) {
                HisPersuitslnlkiesr hisPersuitslnlkiesr = new HisPersuitslnlkiesr(pers);
                kernSession.save(hisPersuitslnlkiesr);
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisPersuitslnlkiesr hisPersuitslnlkiesrCorrectie = (HisPersuitslnlkiesr) HisCorrectieGenerator.creeerHisCorrectie(new HisPersuitslnlkiesr(pers), hisPersuitslnlkiesr);
                    kernSession.save(hisPersuitslnlkiesrCorrectie);
                }
            }
            if (pers.getInddeelneuverkiezingen() != null) {
                HisPerseuverkiezingen hisPerseuverkiezingen = new HisPerseuverkiezingen(pers);
                kernSession.save(hisPerseuverkiezingen);
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisPerseuverkiezingen hisPerseuverkiezingenCorrectie = (HisPerseuverkiezingen) HisCorrectieGenerator.creeerHisCorrectie(new HisPerseuverkiezingen(pers), hisPerseuverkiezingen);
                    kernSession.save(hisPerseuverkiezingenCorrectie);
                }
            }
            if (pers.getRdnopschorting() != null) {
                List<HisPersopschorting> hisPersopschortingList =
                        hisPersopschortingGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                    for (HisPersopschorting hisPersopschorting : hisPersopschortingList) {
                            kernSession.save(hisPersopschorting);
                    }
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    int randomHisPersopschorting = new Random().nextInt(hisPersopschortingList.size());
                    HisPersopschorting hisPersopschortingCorrectie = (HisPersopschorting) HisCorrectieGenerator.creeerHisCorrectie(new HisPersopschorting(pers), hisPersopschortingList.get(randomHisPersopschorting));
                    kernSession.save(hisPersopschortingCorrectie);
                }
            }
            if (pers.getPartijByGempk() != null) {
                HisPerspk hisPerspk = new HisPerspk(pers);
                kernSession.save(hisPerspk);
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    HisPerspk hisPerspkCorrectie = (HisPerspk) HisCorrectieGenerator.creeerHisCorrectie(new HisPerspk(pers), hisPerspk);
                    kernSession.save(hisPerspkCorrectie);
                }
            }
            if (pers.getDatvestiginginnederland() != null) {
                List<HisPersimmigratie> hisPersimmigratieList =
                        hisPersimmigratieGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                    for (HisPersimmigratie hisPersimmigratie : hisPersimmigratieList) {
                            kernSession.save(hisPersimmigratie);
                    }
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    int randomHisPersimmigratie = new Random().nextInt(hisPersimmigratieList.size());
                    HisPersimmigratie hisPersimmigratieCorrectie = (HisPersimmigratie) HisCorrectieGenerator.creeerHisCorrectie(new HisPersimmigratie(pers), hisPersimmigratieList.get(randomHisPersimmigratie));
                    kernSession.save(hisPersimmigratieCorrectie);
                }
            }
            if (pers.getVerblijfsr() != null) {
                List<HisPersverblijfsr> hisPersverblijfsrList =
                        hisPersverblijfsrGenerator.generateHisMaterieels(pers, pers.getDatgeboorte());
                    for (HisPersverblijfsr hisPersverblijfsr : hisPersverblijfsrList) {
                            kernSession.save(hisPersverblijfsr);
                    }
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    int randomHisPersverblijfsr = new Random().nextInt(hisPersverblijfsrList.size());
                    HisPersverblijfsr hisPersverblijfsrCorrectie = (HisPersverblijfsr) HisCorrectieGenerator.creeerHisCorrectie(new HisPersverblijfsr(pers), hisPersverblijfsrList.get(randomHisPersverblijfsr));
                    kernSession.save(hisPersverblijfsrCorrectie);
                }
            }
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

                List<HisPersgeslnaamcomp> hisPersgeslnaamcompList =
                        hisPersgeslnaamcompGenerator.generateHisMaterieels(persgeslnaamcomp, pers.getDatgeboorte());
                    for (HisPersgeslnaamcomp hisPersgeslnaamcomp : hisPersgeslnaamcompList) {
                            kernSession.save(hisPersgeslnaamcomp);
                    }
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    int randomHisPersgeslnaamcomp = new Random().nextInt(hisPersgeslnaamcompList.size());
                    HisPersgeslnaamcomp hisPersgeslnaamcomp = (HisPersgeslnaamcomp) HisCorrectieGenerator.creeerHisCorrectie(new HisPersgeslnaamcomp(persgeslnaamcomp), hisPersgeslnaamcompList.get(randomHisPersgeslnaamcomp));
                    kernSession.save(hisPersgeslnaamcomp);
                }
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

                List<HisPersvoornaam> hisPersvoornaamList =
                        hisPersvoornaamGenerator.generateHisMaterieels(persVoornaam, pers.getDatgeboorte());
                    for (HisPersvoornaam hisPersvoornaam : hisPersvoornaamList) {
                            kernSession.save(hisPersvoornaam);
                    }
                if (isFractie(His.CORRECTIE_FRACTIE)) {
                    int randomHisPersvoornaam = new Random().nextInt(hisPersvoornaamList.size());
                    HisPersvoornaam hisPersvoornaam = (HisPersvoornaam) HisCorrectieGenerator.creeerHisCorrectie(new HisPersvoornaam(persVoornaam), hisPersvoornaamList.get(randomHisPersvoornaam));
                    kernSession.save(hisPersvoornaam);
                }
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

        List<HisPersnation> hisPersnations = hisPersnationGenerator.generateHisMaterieels(persnation, pers.getDatgeboorte());
        for (HisPersnation hisPersnation : hisPersnations) {
            if (Settings.SAVE) kernSession.save(hisPersnation);
        }
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

    private void voegAdresToe(final Session kernSession, final Pers pers) {
        Persadres adres = PersAdresGenerator.generatePersAdres(pers);
        if (Settings.SAVE) kernSession.save(adres);
        Integer dataanvHuidig = adres.getDataanvadresh();
        List<HisPersadres> hisPersAdressen = hisPersAdresGenerator.generateHisMaterieels(adres, dataanvHuidig);

        if (hisPersAdressen.size() == 1) {
            adres.setRdnwijzadres(null);
        }

        if (adres.getPers().getDatoverlijden() == null) {
            adres.setPlaats(RandomService.getWplNuGeldig());
            adres.setPartij(RandomService.getGemeenteNuGeldig());
        }

        for (HisPersadres hisPersadres : hisPersAdressen) {
            if (Settings.SAVE) kernSession.save(hisPersadres);
        }

        List<HisPersbijhgem> hisPersbijhgems = hisPersbijhgemGenerator.generateHisMaterieels(adres, dataanvHuidig);
        for (HisPersbijhgem hisPersbijhgem : hisPersbijhgems) {
            if (Settings.SAVE) kernSession.save(hisPersbijhgem);
        }
    }

    private void voegIndicatiesToe(final Session kernSession, final Pers pers, final boolean isStatenloos) {
        List<Persindicatie> indicaties = creeerIndicaties(pers, isStatenloos);
        for (Persindicatie indicatie : indicaties) {
            List<HisPersindicatie> hisPersindicaties = hisPersindicatieGenerator.generateHisMaterieels(indicatie, RandomService.randomDate());
            if (Settings.SAVE) {
                kernSession.save(indicatie);
                for (HisPersindicatie hisPersindicatie : hisPersindicaties) {
                    kernSession.save(hisPersindicatie);
                }
            }
        }
    }

    private List<Persindicatie> creeerIndicaties(final Pers pers, final boolean isStatenloos) {

        List<Persindicatie> resultaat = new ArrayList<Persindicatie>();
        EnumSet<Srtindicatie> indicatieSet = EnumSet.allOf(Srtindicatie.class);
        indicatieSet.remove(Srtindicatie.ORDINAL_NUL_NIET_GEBRUIKEN);
        for (Srtindicatie soortIndicatie : indicatieSet) {
            if (isFractie(90)) {
                resultaat.add(creeerIndicatie(pers, soortIndicatie));
            }
        }
        return resultaat;
    }

}
