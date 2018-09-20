/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.utils.PersIndicatieUtil.creeerIndicatie;
import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersAdresGenerator;
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
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisPersverblijfstitelGenerator;
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
import nl.bzk.brp.testdatageneratie.domain.kern.HisPerssamengesteldenaam;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersuitslnlkiesr;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersverblijfstitel;
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
import nl.bzk.brp.testdatageneratie.helper.Huwelijksboot;
import nl.bzk.brp.testdatageneratie.helper.PersoonHelper;
import nl.bzk.brp.testdatageneratie.utils.HisUtil;
import nl.bzk.brp.testdatageneratie.utils.PersAdresUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class PersoonGenerator implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(PersoonGenerator.class);

    private final int numberOfRecordsToProcess;
    private final int batchBlockSize;
    private final Huwelijksboot huwelijksboot;
    private final PersoonHelper persGenerator;
    private final int threadIndex;

    public PersoonGenerator(final int numberOfRecordsToProcess, final int batchBlockSize, final int threadIndex,
            final int rangeSize, final int threadBlockSize, final Huwelijksboot huwelijksboot)
    {
        this.numberOfRecordsToProcess = numberOfRecordsToProcess;
        this.batchBlockSize = batchBlockSize;
        this.huwelijksboot = huwelijksboot;
        persGenerator = new PersoonHelper(this.threadIndex = threadIndex, rangeSize, threadBlockSize);
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

            log.info("@@@@@@@@@@@@@@@@ Personen @@@@@@@@@@@@@@@@@@@@");

            for (long i = 1; i <= numberOfRecordsToProcess; i++) {

                voegPersoonToe(kernSession);

                if (i % batchBlockSize == 0) {
                    log.debug(threadIndex+":"+i);
                    kernSession.getTransaction().commit();
                    kernSession.clear();
                    kernSession.getTransaction().begin();
                }
            }

            log.info("################ Personen ####################");

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
        String[] voornamen = RandomUtil.getVoornamen();
        Pers pers = persGenerator.generatePers(voornamen);

        kernSession.save(pers);
        huwelijksboot.toevoegenIndienUitverkoren(pers);

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

        List<HisPersids> hisPersidsList =
                HisPersidsGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
            for (HisPersids hisPersids : hisPersidsList) {
                    kernSession.save(hisPersids);
            }

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPersids = new Random().nextInt(hisPersidsList.size());
            HisPersids hisPersidsCorrectie = (HisPersids) HisUtil.creeerHisCorrectie(HisPersidsGenerator.getInstance().generateHisMaterieel(pers), hisPersidsList.get(randomHisPersids));
            kernSession.save(hisPersidsCorrectie);
        }

        List<HisPersgeslachtsaand> hisPersgeslachtsaandList =
                HisPersgeslachtsaandGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
            for (HisPersgeslachtsaand hisPersgeslachtsaand : hisPersgeslachtsaandList) {
                    kernSession.save(hisPersgeslachtsaand);
            }
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPersgeslachtsaand = new Random().nextInt(hisPersgeslachtsaandList.size());
            HisPersgeslachtsaand hisPersgeslachtsaandCorrectie = (HisPersgeslachtsaand) HisUtil.creeerHisCorrectie(HisPersgeslachtsaandGenerator.getInstance().generateHisMaterieel(pers), hisPersgeslachtsaandList.get(randomHisPersgeslachtsaand));
            kernSession.save(hisPersgeslachtsaandCorrectie);
        }

        List<HisPerssamengesteldenaam> hisPerssamengesteldenaamList =
                HisPerssamengesteldenaamGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
            for (HisPerssamengesteldenaam hisPerssamengesteldenaam : hisPerssamengesteldenaamList) {
                    kernSession.save(hisPerssamengesteldenaam);
            }
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPerssamengesteldenaam = new Random().nextInt(hisPerssamengesteldenaamList.size());
            HisPerssamengesteldenaam hisPerssamengesteldenaamCorrectie = (HisPerssamengesteldenaam) HisUtil.creeerHisCorrectie(HisPerssamengesteldenaamGenerator.getInstance().generateHisMaterieel(pers), hisPerssamengesteldenaamList.get(randomHisPerssamengesteldenaam));
            kernSession.save(hisPerssamengesteldenaamCorrectie);
        }

        HisPersaanschr hisPersaanschr = HisUtil.creeerHisPersaanschr(pers);
        kernSession.save(hisPersaanschr);
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            HisPersaanschr hisPersaanschrCorrectie = (HisPersaanschr) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPersaanschr(pers), hisPersaanschr);
            kernSession.save(hisPersaanschrCorrectie);
        }

        HisPersgeboorte hisPersgeboorte = HisUtil.creeerHisPersgeboorte(pers);
        kernSession.save(hisPersgeboorte);
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            HisPersgeboorte hisPersgeboorteCorrectie = (HisPersgeboorte) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPersgeboorte(pers), hisPersgeboorte);
            kernSession.save(hisPersgeboorteCorrectie);
        }

        HisPersinschr hisPersinschr = HisUtil.creerHisPersinschr(pers);
        kernSession.save(hisPersinschr);
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            HisPersinschr hisPersinschrCorrectie = (HisPersinschr) HisUtil.creeerHisCorrectie(HisUtil.creerHisPersinschr(pers), hisPersinschr);
            kernSession.save(hisPersinschrCorrectie);
        }

        List<HisPersbijhverantwoordelijk> hisPersbijhverantwoordelijkList =
                HisPersbijhverantwoordelijkGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
            for (HisPersbijhverantwoordelijk hisPersbijhverantwoordelijk : hisPersbijhverantwoordelijkList) {
                    kernSession.save(hisPersbijhverantwoordelijk);
            }
        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisPersbijhverantwoordelijk = new Random().nextInt(hisPersbijhverantwoordelijkList.size());
            HisPersbijhverantwoordelijk hisPersbijhverantwoordelijkCorrectie = (HisPersbijhverantwoordelijk) HisUtil.creeerHisCorrectie(HisPersbijhverantwoordelijkGenerator.getInstance().generateHisMaterieel(pers), hisPersbijhverantwoordelijkList.get(randomHisPersbijhverantwoordelijk));
            kernSession.save(hisPersbijhverantwoordelijkCorrectie);
        }

        if (pers.getDatoverlijden() != null) {
            HisPersoverlijden hisPersoverlijden = HisUtil.creeerHisPersoverlijden(pers);
            kernSession.save(hisPersoverlijden);
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisPersoverlijden hisPersoverlijdenCorrectie = (HisPersoverlijden) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPersoverlijden(pers), hisPersoverlijden);
                kernSession.save(hisPersoverlijdenCorrectie);
            }
        }
        if (pers.getInduitslnlkiesr() != null) {
            HisPersuitslnlkiesr hisPersuitslnlkiesr = HisUtil.creeerHisPersuitslnlkiesr(pers);
            kernSession.save(hisPersuitslnlkiesr);
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisPersuitslnlkiesr hisPersuitslnlkiesrCorrectie = (HisPersuitslnlkiesr) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPersuitslnlkiesr(pers), hisPersuitslnlkiesr);
                kernSession.save(hisPersuitslnlkiesrCorrectie);
            }
        }
        if (pers.getInddeelneuverkiezingen() != null) {
            HisPerseuverkiezingen hisPerseuverkiezingen = HisUtil.creeerHisPerseuverkiezingen(pers);
            kernSession.save(hisPerseuverkiezingen);
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisPerseuverkiezingen hisPerseuverkiezingenCorrectie = (HisPerseuverkiezingen) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPerseuverkiezingen(pers), hisPerseuverkiezingen);
                kernSession.save(hisPerseuverkiezingenCorrectie);
            }
        }
        if (pers.getRdnopschorting() != null) {
            List<HisPersopschorting> hisPersopschortingList =
                    HisPersopschortingGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersopschorting hisPersopschorting : hisPersopschortingList) {
                        kernSession.save(hisPersopschorting);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersopschorting = new Random().nextInt(hisPersopschortingList.size());
                HisPersopschorting hisPersopschortingCorrectie = (HisPersopschorting) HisUtil.creeerHisCorrectie(HisPersopschortingGenerator.getInstance().generateHisMaterieel(pers), hisPersopschortingList.get(randomHisPersopschorting));
                kernSession.save(hisPersopschortingCorrectie);
            }
        }
        if (pers.getPartijByGempk() != null) {
            HisPerspk hisPerspk = HisUtil.creeerHisPerspk(pers);
            kernSession.save(hisPerspk);
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                HisPerspk hisPerspkCorrectie = (HisPerspk) HisUtil.creeerHisCorrectie(HisUtil.creeerHisPerspk(pers), hisPerspk);
                kernSession.save(hisPerspkCorrectie);
            }
        }
        if (pers.getDatvestiginginnederland() != null) {
            List<HisPersimmigratie> hisPersimmigratieList =
                    HisPersimmigratieGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersimmigratie hisPersimmigratie : hisPersimmigratieList) {
                        kernSession.save(hisPersimmigratie);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersimmigratie = new Random().nextInt(hisPersimmigratieList.size());
                HisPersimmigratie hisPersimmigratieCorrectie = (HisPersimmigratie) HisUtil.creeerHisCorrectie(HisPersimmigratieGenerator.getInstance().generateHisMaterieel(pers), hisPersimmigratieList.get(randomHisPersimmigratie));
                kernSession.save(hisPersimmigratieCorrectie);
            }
        }

        if (pers.getVerblijfstitel() != null) {
            List<HisPersverblijfstitel> hisPersverblijfstitelList =
                    HisPersverblijfstitelGenerator.getInstance().generateHisMaterieels(pers, pers.getDatgeboorte());
                for (HisPersverblijfstitel hisPersverblijfstitel : hisPersverblijfstitelList) {
                        kernSession.save(hisPersverblijfstitel);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersverblijfstitel = new Random().nextInt(hisPersverblijfstitelList.size());
                HisPersverblijfstitel hisPersverblijfstitelCorrectie = (HisPersverblijfstitel) HisUtil.creeerHisCorrectie(HisPersverblijfstitelGenerator.getInstance().generateHisMaterieel(pers), hisPersverblijfstitelList.get(randomHisPersverblijfstitel));
                kernSession.save(hisPersverblijfstitelCorrectie);
            }
        }
    }

    private void voegVerificatiesToe(final Session kernSession, final Pers pers) {
        int aantal = RandomUtil.random.nextInt(3);
        for (int i = 0; i < aantal; i++) {
            voegVerificatieToe(kernSession, pers);
        }
    }

    private void voegVerificatieToe(final Session kernSession, final Pers pers) {
        Persverificatie verificatie = new Persverificatie();
        verificatie.setPers(pers);
        if (isFractie(2)) verificatie.setSrtverificatie(MetaRepo.get(Srtverificatie.class));
        if (isFractie(2)) verificatie.setDat(RandomUtil.randomDate());
        kernSession.save(verificatie);
        kernSession.save(HisUtil.creerHisPersverificatie(verificatie));
    }

    private void voegGeslachtsnamenToe(final Session kernSession, final Pers pers) {
        List<Persgeslnaamcomp> geslachtsnaamComponenten = new ArrayList<Persgeslnaamcomp>();
        StringTokenizer tokens = new StringTokenizer(pers.getGeslnaam(), " ");
        for (int i = 1; tokens.hasMoreTokens(); i++) {
            String token = tokens.nextToken();
            if (isHoofdletter(token.charAt(0))) {
                Persgeslnaamcomp persgeslnaamcomp = new Persgeslnaamcomp(pers, persGenerator.getVoorvoegsel(), token, i);
                persgeslnaamcomp.setAdellijketitel(pers.getAdellijketitelByAdellijketitel());
                persgeslnaamcomp.setPredikaat(pers.getPredikaatByPredikaat());
                geslachtsnaamComponenten.add(persgeslnaamcomp);
            }
        }
        for (Persgeslnaamcomp persgeslnaamcomp : geslachtsnaamComponenten) {
            kernSession.save(persgeslnaamcomp);

            List<HisPersgeslnaamcomp> hisPersgeslnaamcompList =
                    HisPersgeslnaamcompGenerator.getInstance().generateHisMaterieels(persgeslnaamcomp, pers.getDatgeboorte());
                for (HisPersgeslnaamcomp hisPersgeslnaamcomp : hisPersgeslnaamcompList) {
                        kernSession.save(hisPersgeslnaamcomp);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersgeslnaamcomp = new Random().nextInt(hisPersgeslnaamcompList.size());
                HisPersgeslnaamcomp hisPersgeslnaamcomp = (HisPersgeslnaamcomp) HisUtil.creeerHisCorrectie(HisPersgeslnaamcompGenerator.getInstance().generateHisMaterieel(persgeslnaamcomp), hisPersgeslnaamcompList.get(randomHisPersgeslnaamcomp));
                kernSession.save(hisPersgeslnaamcomp);
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
            kernSession.save(persVoornaam);

            List<HisPersvoornaam> hisPersvoornaamList =
                    HisPersvoornaamGenerator.getInstance().generateHisMaterieels(persVoornaam, pers.getDatgeboorte());
                for (HisPersvoornaam hisPersvoornaam : hisPersvoornaamList) {
                        kernSession.save(hisPersvoornaam);
                }
            if (isFractie(His.CORRECTIE_FRACTIE)) {
                int randomHisPersvoornaam = new Random().nextInt(hisPersvoornaamList.size());
                HisPersvoornaam hisPersvoornaam = (HisPersvoornaam) HisUtil.creeerHisCorrectie(HisPersvoornaamGenerator.getInstance().generateHisMaterieel(persVoornaam), hisPersvoornaamList.get(randomHisPersvoornaam));
                kernSession.save(hisPersvoornaam);
            }
        }
    }

    private void voegNationalieitenToe(final Session kernSession, final Pers pers) {
        Nation nationaliteit = RandomUtil.getNationaliteit();
        if (nationaliteit != null) {
            voegNationalieitToe(kernSession, pers, nationaliteit);
        }
        if (isFractie(80)) {
            Nation nationaliteit2;
            do {
                nationaliteit2 = RandomUtil.getNationaliteit();
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
            persnation.setRdnverknlnation(RandomUtil.getNationaliteitVerkrijgReden());
            if (isFractie(5226)) {
                persnation.setRdnverliesnlnation(RandomUtil.getNationaliteitVerliesReden());
            }
        }
        kernSession.save(persnation);

        List<HisPersnation> hisPersnations = HisPersnationGenerator.getInstance().generateHisMaterieels(persnation, pers.getDatgeboorte());
        for (HisPersnation hisPersnation : hisPersnations) {
            kernSession.save(hisPersnation);
        }
    }

    private void voegReisdocumentenToe(final Session kernSession, final Pers pers) {
        int aantal = RandomUtil.random.nextInt(6);
        for (int i = 0; i < aantal; i++) {
            voegReisdocumentToe(kernSession, pers);
        }
    }

    private void voegReisdocumentToe(final Session kernSession, final Pers pers) {
        Persreisdoc persreisdoc = new Persreisdoc();
        persreisdoc.setSrtnlreisdoc(RandomUtil.getReisdocumentSoort());
        if (!RandomUtil.isFractie(3)) {
            persreisdoc.setRdnvervallenreisdoc(MetaRepo.get(Rdnvervallenreisdoc.class));
        }
        //TODO datum veranderd
        //persreisdoc.setDatuitgifte(RandomUtil.getAutvanafgiftereisdoc());
        persreisdoc.setPers(pers);
        if (isFractie(100)) {
            persreisdoc.setDatinhingvermissing(RandomUtil.randomDate());
        }
        persreisdoc.setDatingangdoc(RandomUtil.randomDate());
        persreisdoc.setDatuitgifte(RandomUtil.randomDate());
        persreisdoc.setDatvoorzeeindegel(RandomUtil.randomDate());
        if (!isFractie(5)) {
            persreisdoc.setLengtehouder(RandomUtil.randomLengtehouder());
        }
        persreisdoc.setNr(RandomStringUtils.randomAlphanumeric(9));

        // Autoriteit welke het Nederlands reisdocument heeft verstrekt of de bijschrijving heeft verricht. Aangeduid met een code.
        persreisdoc.setTyperingautafgifte(RandomStringUtils.randomAlphanumeric(8));

        kernSession.save(persreisdoc);
        kernSession.save(HisUtil.creeerHisPersreisdoc(persreisdoc));
    }

    private void voegAdresToe(final Session kernSession, final Pers pers) {
        Persadres adres = PersAdresUtil.generatePersAdres(pers);
        kernSession.save(adres);
        Integer dataanvHuidig = adres.getDataanvadresh();
        List<HisPersadres> hisPersAdressen = HisPersAdresGenerator.getInstance().generateHisMaterieels(adres, dataanvHuidig);

        if (hisPersAdressen.size() == 1) {
            adres.setRdnwijzadres(null);
        }

        if (adres.getPers().getDatoverlijden() == null) {
            adres.setPlaats(RandomUtil.getWplNuGeldig());
            adres.setPartij(RandomUtil.getGemeenteNuGeldig());
        }

        for (HisPersadres hisPersadres : hisPersAdressen) {
            kernSession.save(hisPersadres);
        }

        List<HisPersbijhgem> hisPersbijhgems = HisPersbijhgemGenerator.getInstance().generateHisMaterieels(adres, dataanvHuidig);
        for (HisPersbijhgem hisPersbijhgem : hisPersbijhgems) {
            kernSession.save(hisPersbijhgem);
        }
    }

    private void voegIndicatiesToe(final Session kernSession, final Pers pers, final boolean isStatenloos) {
        List<Persindicatie> indicaties = creeerIndicaties(pers, isStatenloos);
        for (Persindicatie indicatie : indicaties) {
            List<HisPersindicatie> hisPersindicaties = HisPersindicatieGenerator.getInstance().generateHisMaterieels(indicatie, RandomUtil.randomDate());
            kernSession.save(indicatie);
            for (HisPersindicatie hisPersindicatie : hisPersindicaties) {
                kernSession.save(hisPersindicatie);
            }
        }
    }

    private List<Persindicatie> creeerIndicaties(final Pers pers, final boolean isStatenloos) {

        List<Persindicatie> resultaat = new ArrayList<Persindicatie>();
        EnumSet<Srtindicatie> indicatieSet = EnumSet.allOf(Srtindicatie.class);
        indicatieSet.remove(Srtindicatie.ORDINAL_NUL_NIET_GEBRUIKEN);
        indicatieSet.remove(Srtindicatie.DUMMY_NIET_GEBRUIKEN);
        for (Srtindicatie soortIndicatie : indicatieSet) {
            if (isFractie(90)) {
                resultaat.add(creeerIndicatie(pers, soortIndicatie));
            }
        }
        return resultaat;
    }

}
