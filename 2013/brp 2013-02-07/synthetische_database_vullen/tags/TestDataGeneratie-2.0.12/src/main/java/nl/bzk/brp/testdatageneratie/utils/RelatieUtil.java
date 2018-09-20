/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.isFractie;

import java.util.List;
import java.util.Random;

import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisBetrouderlijkgezagGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.hismaterieel.HisBetrouderschapGenerator;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieStartPlaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisBetrouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisBetrouderschap;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtrelatie;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;


public final class RelatieUtil {

    public static void opslaanBetrokkenheidMetHistorie(final Session kernSession, final Betr betr) {
        kernSession.save(betr);

        List<HisBetrouderlijkgezag> hisBetrouderlijkgezagList =
                HisBetrouderlijkgezagGenerator.getInstance().generateHisMaterieels(betr, RandomUtil.randomDate());
        for (HisBetrouderlijkgezag hisBetrouderlijkgezag : hisBetrouderlijkgezagList) {
            kernSession.save(hisBetrouderlijkgezag);
        }

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisBetrouderlijkgezag = new Random().nextInt(hisBetrouderlijkgezagList.size());
            HisBetrouderlijkgezag hisBetrouderlijkgezagCorrectie =
                (HisBetrouderlijkgezag) HisCorrectieUtil.creeerHisCorrectie(HisBetrouderlijkgezagGenerator.getInstance().generateHisMaterieel(betr),
                        hisBetrouderlijkgezagList.get(randomHisBetrouderlijkgezag));
            kernSession.save(hisBetrouderlijkgezagCorrectie);
        }

        List<HisBetrouderschap> hisBetrouderschapList =
                HisBetrouderschapGenerator.getInstance().generateHisMaterieels(betr, RandomUtil.randomDate());
            for (HisBetrouderschap hisBetrouderschap : hisBetrouderschapList) {
                kernSession.save(hisBetrouderschap);
            }

        if (isFractie(His.CORRECTIE_FRACTIE)) {
            int randomHisBetrouderschap = new Random().nextInt(hisBetrouderschapList.size());
            HisBetrouderschap hisBetrouderschapCorrectie = (HisBetrouderschap) HisCorrectieUtil.creeerHisCorrectie(HisBetrouderschapGenerator.getInstance().generateHisMaterieel(betr), hisBetrouderschapList.get(randomHisBetrouderschap));
            kernSession.save(hisBetrouderschapCorrectie);
        }
    }

    public static Relatie creeerRelatie(final Srtrelatie srtrelatie, final Locatie locatieAanvang, final int datumAanvang) {
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
