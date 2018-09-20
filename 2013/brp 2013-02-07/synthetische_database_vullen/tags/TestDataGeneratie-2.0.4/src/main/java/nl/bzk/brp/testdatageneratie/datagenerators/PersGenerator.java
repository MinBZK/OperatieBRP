/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.RandomService.isFractie;
import static nl.bzk.brp.testdatageneratie.RandomService.random;
import nl.bzk.brp.testdatageneratie.BronnenRepo;
import nl.bzk.brp.testdatageneratie.RandomBSNService;
import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruik;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruikId;
import nl.bzk.brp.testdatageneratie.domain.kern.Adellijketitel;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Predikaat;
import org.apache.commons.lang3.RandomStringUtils;

public class PersGenerator {

    public final RandomBSNService randomBSNService;

    public PersGenerator(final int threadIndex, final int rangeSize, final int threadBlockSize) {
        randomBSNService = new RandomBSNService(threadIndex, rangeSize, threadBlockSize);
    }

    public Pers generatePers(final String[] voornamen) {
        Pers pers = new Pers();
        Integer datGeboorte = RandomService.getDatGeboorte();

        if (isFractie(2347)) {
            pers.setAdellijketitel(RandomService.getAdellijketitel());
            if (pers.getAdellijketitel() == Adellijketitel.P) {
                pers.setPredikaatByPredikaat(Predikaat.K);
            } else {
                pers.setPredikaatByPredikaat(Predikaat.J);
            }
            if (isFractie(100)) {
                pers.setIndaanschrmetadellijketitels(true);
            } else {
                pers.setPredikaatByPredikaataanschr(pers.getPredikaatByPredikaat());
            }
        }

        if (isFractie(2)) {
            pers.setAnr((long) random.nextInt(Integer.MAX_VALUE));
        }

        if (isFractie(1000)) {
            pers.setDataanlaanpdeelneuverkiezing(RandomService.randomDate());
            pers.setInddeelneuverkiezingen(isFractie(2));
        } else if (isFractie(1000)) {
            pers.setInddeelneuverkiezingen(true);
        }

        if (isFractie(100)) {
            pers.setInduitslnlkiesr(true);
            if (isFractie(100)) {
                pers.setDateindeuitslnlkiesr(RandomService.randomDate());
            }
        }

        if (isFractie(1000)) {
            pers.setDateindeuitsleukiesr(RandomService.randomDate());
        }

        pers.setDatinschr(datGeboorte);

        pers.setDatinschringem(RandomService.randomDate());

        //Geslachtsnaam:
        pers.setGeslnaam(RandomService.getGeslnaam());

        pers.setVerantwoordelijke(RandomService.getVerantwoordelijke());
        pers.setSrtpers(RandomService.getSrtpers());

        //Voornamen:
        StringBuilder voornamenBuilder = new StringBuilder();
        for (int i = 0; i < voornamen.length; i++) {
            String voornaam = voornamen[i];
            voornamenBuilder.append(voornaam).append(" ");
        }
        String voornamenString = voornamenBuilder.toString().trim();
        pers.setVoornamen(voornamenString);
        pers.setVoornamenaanschr(voornamenString);

        NaamGebruikId naamGebruik = BronnenRepo.getBron(NaamGebruik.class).getId();
        pers.setWijzegebruikgeslnaam(naamGebruik.getNaamGebruik());
        pers.setGeslachtsaand(naamGebruik.getGeslacht());

        {
            pers.setGeslnaamaanschr(pers.getGeslnaam());
            pers.setIndaanschralgoritmischafgele(!isFractie(1000));
        }

        pers.setIndalgoritmischafgeleid(!isFractie(1000));

        pers.setTijdstiplaatstewijz(RandomService.getPastTimestamp());

        if (isFractie(1000)) {
            pers.setIndgegevensinonderzoek(true);
        }

        pers.setIndnreeksalsgeslnaam(isFractie(10000));

        if (isFractie(3)) {
            pers.setDatoverlijden(RandomService.randomDate());
            Locatie locatie = RandomService.getGeboortePlaats();
            pers.setLandByLandoverlijden(locatie.getLand());
            if (locatie.isNederland()) {
                pers.setPartijByGemoverlijden(locatie.getPartij());
                pers.setPlaatsByWploverlijden(RandomService.getWplGeboorte());
            } else if (locatie.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
                //Land onbekend, gebruik omschrijving:
                pers.setOmslocoverlijden(locatie.getPlaats());
            } else {
                pers.setBlplaatsoverlijden(RandomStringUtils.randomAlphabetic(40));
                if (isFractie(10)) {
                    pers.setBlregiooverlijden(RandomStringUtils.randomAlphabetic(35));
                }
            }
        }

        //Bijhoudings gemeente:
        pers.setPartijByBijhgem(RandomService.getPartijByBijhgem());
        pers.setIndonverwdocaanw(isFractie(10000));

        //4% is opgeschort volgens bronnen tabel: 2,6 miljoen personen
        if (isFractie(25)) {
            //Reden opschorting
            pers.setRdnopschorting(RandomService.getRdnOpschorting());
        }

        //BEGIN SECTION Geboorte:
        pers.setDatgeboorte(datGeboorte);

        //Gemeente geboorte:
        //Find random geboorteplaats from bronnen:
        final Locatie geboorteplaats = RandomService.getGeboortePlaats();
        //Land geboorte:
        pers.setLandByLandgeboorte(geboorteplaats.getLand());

        if (geboorteplaats.isNederland()) {

            pers.setPartijByGemgeboorte(geboorteplaats.getPartij());
            pers.setPlaatsByWplgeboorte(RandomService.getWplGeboorte());

        } else {
            if (Locatie.LAND_CODE_ONBEKEND == geboorteplaats.getLandCode()) {
                //Land onbekend, gebruik omschrijving:
                pers.setOmsgeboorteloc(geboorteplaats.getPlaats());
            } else {
                //Buitenland geboren:
                pers.setBlgeboorteplaats(geboorteplaats.getPlaats());
                if (isFractie(10)) {
                    pers.setBlregiogeboorte(RandomStringUtils.randomAlphabetic(35));
                }
            }
            pers.setDatvestiginginnederland(RandomService.randomDate());
            pers.setDataanvaaneenslverblijfsr(RandomService.randomDate());
            pers.setDataanvverblijfsr(RandomService.randomDate());
            if (!isFractie(3)) pers.setDatvoorzeindeverblijfsr(RandomService.randomDate());

            pers.setVerblijfsr(RandomService.getVerblijfsrecht());
        }

        if (isFractie(100)) {
            pers.setLandByLandvanwaargevestigd(RandomService.getGeboortePlaats().getLand());
        }

        if (!isFractie(5)) {
            pers.setPartijByGempk(RandomService.getGeboortePlaats().getPartij());
            pers.setIndpkvollediggeconv(!isFractie(4));
        }

        if (isFractie(50)) {
            String scheidingsTeken = RandomService.nextScheidingsTeken();
            pers.setScheidingsteken(scheidingsTeken);
            if (!isFractie(20)) {
                pers.setScheidingstekenaanschr(scheidingsTeken);
            }
        }

        pers.setVersienr((long) random.nextInt(100));

        if (isFractie(22)) {
            String voorvoegsel = RandomService.getVoorvoegsel();
            pers.setVoorvoegsel(voorvoegsel);
            pers.setVoorvoegselaanschr(voorvoegsel);
        }
        pers.setBsn(randomBSNService.randomBsn()); //Zero inclusive



        return pers;
    }

    public String getVoorvoegsel() {
        switch (RandomService.random.nextInt(8)) {
            case 0: return "de";
            case 1: return "van";
            case 2: return "van de";
            case 3: return "van der";
            default: return null;
        }
    }

}
