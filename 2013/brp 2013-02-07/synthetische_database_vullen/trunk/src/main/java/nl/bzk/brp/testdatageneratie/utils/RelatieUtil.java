/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.List;
import java.util.Random;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisOuderouderlijkgezagGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisOuderouderschapGenerator;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieStartPlaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderschap;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;


public final class RelatieUtil {

    public static void opslaanBetrokkenheidMetHistorie(final Session kernSession, final Betr betr) {
        kernSession.save(betr);

        List<HisOuderouderlijkgezag> hisOuderouderlijkgezagList = HisOuderouderlijkgezagGenerator.getInstance().generateHisMaterieels(betr, RandomUtil.randomDate());
        for (HisOuderouderlijkgezag hisOuderouderlijkgezag : hisOuderouderlijkgezagList) {
            kernSession.save(hisOuderouderlijkgezag);
        }

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisOuderouderlijkgezag = new Random().nextInt(hisOuderouderlijkgezagList.size());
            HisOuderouderlijkgezag hisOuderouderlijkgezagCorrectie = (HisOuderouderlijkgezag) HisUtil.creeerHisCorrectie(HisOuderouderlijkgezagGenerator.getInstance().generateHisMaterieel(betr), hisOuderouderlijkgezagList.get(randomHisOuderouderlijkgezag));
            kernSession.save(hisOuderouderlijkgezagCorrectie);
        }

        List<HisOuderouderschap> hisOuderouderschapList = HisOuderouderschapGenerator.getInstance().generateHisMaterieels(betr, RandomUtil.randomDate());
            for (HisOuderouderschap hisOuderouderschap : hisOuderouderschapList) {
                kernSession.save(hisOuderouderschap);
            }

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisOuderouderschap = new Random().nextInt(hisOuderouderschapList.size());
            HisOuderouderschap hisOuderouderschapCorrectie = (HisOuderouderschap) HisUtil.creeerHisCorrectie(HisOuderouderschapGenerator.getInstance().generateHisMaterieel(betr), hisOuderouderschapList.get(randomHisOuderouderschap));
            kernSession.save(hisOuderouderschapCorrectie);
        }
    }

    public static Relatie creeerRelatie(final SoortRelatie srtrelatie, final Locatie locatieAanvang, final int datumAanvang) {
        Relatie relatie = new Relatie();

        relatie.setSrtrelatie(srtrelatie);

        relatie.setLandByLandaanv(locatieAanvang.getLand());
        relatie.setPartijByGemaanv(locatieAanvang.getPartij());
        relatie.setDataanv(datumAanvang);
        if (locatieAanvang.isNederland()) {
            // NL
            relatie.setPlaatsByWplaanv(RandomUtil.getWplGeboorte());
        } else if (locatieAanvang.getLandCode() == Locatie.LAND_CODE_ONBEKEND) {
            //Land onbekend, gebruik omschrijving:
            relatie.setOmslocaanv(locatieAanvang.getPlaats());
        } else {
            // Buitenland
            relatie.setBlplaatsaanv(locatieAanvang.getPlaats());
            if (RandomUtil.isFractie(10)) {
                relatie.setBlregioaanv(RandomStringUtils.randomAlphabetic(35));
            }
        }

        //TODO: bepaal verhoudingen
        relatie.setHuwelijkgeregistreerdpartner(RandomUtil.getTrueFalseString(5));
        relatie.setNaamskeuzeongeborenvruchtsta(RandomUtil.getTrueFalseString(10));
        relatie.setErkenningongeborenvruchtstat(RandomUtil.getTrueFalseString(10));

        return relatie;
    }

    public static Locatie getRelatieStartPlaats() {
        Locatie locatie;
        do {
            locatie = BronnenRepo.getBron(RelatieStartPlaats.class).getLocatie();
        } while (locatie.getLand() == null);
        return locatie;
    }

}
