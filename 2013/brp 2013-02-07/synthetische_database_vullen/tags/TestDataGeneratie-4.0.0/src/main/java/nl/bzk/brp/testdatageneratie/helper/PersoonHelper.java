/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;
import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.random;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruik;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruikId;
import nl.bzk.brp.testdatageneratie.domain.kern.Adellijketitel;
import nl.bzk.brp.testdatageneratie.domain.kern.Partij;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Predikaat;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

public class PersoonHelper {

    public final RandomBsnHelper randomBSNService;

    public PersoonHelper(final int threadIndex, final int rangeSize, final int threadBlockSize) {
        randomBSNService = new RandomBsnHelper(threadIndex, rangeSize, threadBlockSize);
    }

    public Pers generatePers(final String[] voornamen) {
        Pers pers = new Pers();
        Integer datGeboorte = RandomUtil.getDatGeboorte();

        if (isFractie(2347)) {
            pers.setAdellijketitelByAdellijketitel(RandomUtil.getAdellijketitel());
            if (pers.getAdellijketitelByAdellijketitel() == Adellijketitel.P) {
                pers.setPredikaatByPredikaat(Predikaat.K);
            } else {
                pers.setPredikaatByPredikaat(Predikaat.J);
            }
            if (isFractie(100)) {
                //TODO verifieer dat dit de juiste vervanging si voor adelijke titel gebruik
                pers.setIndtitelspredikatenbijaansch(true);
            } else {
                pers.setPredikaatByPredikaataanschr(pers.getPredikaatByPredikaat());
            }
        }

        if (isFractie(2)) {
            pers.setAnr((long) random.nextInt(Integer.MAX_VALUE));
        }

        if (isFractie(10)) {
            if (isFractie(2)) {
                pers.setDataanlaanpdeelneuverkiezing(RandomUtil.randomDate());
                pers.setInddeelneuverkiezingen(isFractie(2));
            } else {
                pers.setInddeelneuverkiezingen(true);
            }
        }
        if (isFractie(100)) {
            pers.setInduitslnlkiesr(true);
            if (isFractie(100)) {
                pers.setDateindeuitslnlkiesr(RandomUtil.randomDate());
            }
        }

        if (isFractie(1000)) {
            pers.setDateindeuitsleukiesr(RandomUtil.randomDate());
        }

        pers.setDatinschr(datGeboorte);

        pers.setDatinschringem(RandomUtil.randomDate());

        //Geslachtsnaam:
        pers.setGeslnaam(RandomUtil.getGeslnaam());

        pers.setBijhaard(RandomUtil.getBijhaard());
        pers.setSrtpers(RandomUtil.getSrtpers());

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

        pers.setTslaatstewijz(RandomUtil.getPastTimestamp());

        if (isFractie(1000)) {
            pers.setIndgegevensinonderzoek(true);
        }

        //TODO check of dit de correcte setting is
        pers.setIndnreeks(isFractie(10000));

        if (isFractie(3)) {
            pers.setDatoverlijden(RandomUtil.randomDate());
            Locatie locatie = RandomUtil.getGeboortePlaats();
            pers.setLandByLandoverlijden(locatie.getLand());
            if (locatie.isNederland()) {
                pers.setPartijByGemoverlijden(locatie.getPartij());
                pers.setPlaatsByWploverlijden(RandomUtil.getWplGeboorte());
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
        pers.setPartijByBijhgem(RandomUtil.getPartijByBijhgem());
        pers.setIndonverwdocaanw(isFractie(10000));

        //4% is opgeschort volgens bronnen tabel: 2,6 miljoen personen
        if (isFractie(25)) {
            //Reden opschorting
            pers.setRdnopschorting(RandomUtil.getRdnOpschorting());
        }

        //BEGIN SECTION Geboorte:
        pers.setDatgeboorte(datGeboorte);

        //Gemeente geboorte:
        //Find random geboorteplaats from bronnen:
        final Locatie geboorteplaats = RandomUtil.getGeboortePlaats();
        //Land geboorte:
        pers.setLandByLandgeboorte(geboorteplaats.getLand());

        if (geboorteplaats.isNederland()) {

            pers.setPartijByGemgeboorte(geboorteplaats.getPartij());
            pers.setPlaatsByWplgeboorte(RandomUtil.getWplGeboorte());

        } else {
            if (Locatie.LAND_CODE_ONBEKEND == geboorteplaats.getLandCode()) {
                //Land onbekend, gebruik omschrijving:
                pers.setOmslocgeboorte(geboorteplaats.getPlaats());
            } else {
                //Buitenland geboren:
                pers.setBlplaatsgeboorte(geboorteplaats.getPlaats());
                if (isFractie(10)) {
                    pers.setBlregiogeboorte(RandomStringUtils.randomAlphabetic(35));
                }
            }
            pers.setDatvestiginginnederland(RandomUtil.randomDate());

            pers.setDataanvverblijfstitel(RandomUtil.randomDate());
            if (!isFractie(3)) pers.setDatvoorzeindeverblijfstitel(RandomUtil.randomDate());

            pers.setVerblijfstitel(RandomUtil.getVerblijfstitel());
        }

        if (isFractie(100)) {
            pers.setLandByLandvanwaargevestigd(RandomUtil.getGeboortePlaats().getLand());
        }

        if (isFractie(5)) {
            pers.setPartijByGempk(MetaRepo.get(Partij.class));
            pers.setIndpkvollediggeconv(!isFractie(4));
        }

        if (isFractie(50)) {
            String scheidingsTeken = RandomUtil.nextScheidingsTeken();
            pers.setScheidingsteken(scheidingsTeken);
            if (!isFractie(20)) {
                pers.setScheidingstekenaanschr(scheidingsTeken);
            }
        }

        pers.setVersienr((long) random.nextInt(100));

        if (isFractie(22)) {
            String voorvoegsel = RandomUtil.getVoorvoegsel();
            pers.setVoorvoegsel(voorvoegsel);
            pers.setVoorvoegselaanschr(voorvoegsel);
        }
        pers.setBsn(randomBSNService.randomBsn()); //Zero inclusive



        return pers;
    }

    public String getVoorvoegsel() {
        switch (RandomUtil.random.nextInt(8)) {
            case 0: return "de";
            case 1: return "van";
            case 2: return "van de";
            case 3: return "van der";
            default: return null;
        }
    }

}
