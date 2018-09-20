/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import static nl.bzk.brp.testdatageneratie.utils.RandomUtil.randomDate;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Aangadresh;
import nl.bzk.brp.testdatageneratie.domain.kern.Land;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.helper.AdresHelper;
import org.apache.commons.lang3.RandomStringUtils;


public final class PersAdresUtil {

    public static Persadres generatePersAdres(final Pers pers) {
        Persadres adres;
        if (RandomUtil.isFractie(32)) {
            adres = creeerBuitenlandsAdres();
        } else {
            adres = creeerNederlandsAdres();
        }
        adres.setPers(pers);
        return adres;
    }

    private static Persadres creeerBuitenlandsAdres() {
        Persadres adres = new Persadres();
        adres.setFunctieadres(RandomUtil.getRandom(FunctieAdres.values()));
        adres.setBladresregel1(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel2(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel3(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel4(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel5(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel6(RandomStringUtils.randomAlphabetic(40));
        adres.setLand(RandomUtil.getLandBuitenlandsAdres());
        int randomDate = randomDate();
        adres.setDatvertrekuitnederland(randomDate);
        adres.setDataanvadresh(randomDate);
        adres.setRdnwijzadres(RandomUtil.getRedenWijzigingAdres());

        return adres;
    }

    private static FunctieAdres getFunctieadres() {
        return RandomUtil.isFractie(109)? FunctieAdres.BRIEFADRES: FunctieAdres.WOONADRES;
    }

    private static Persadres creeerNederlandsAdres() {
        AdresHelper adresGenerator = new AdresHelper(RandomUtil.nextLong(9475478));
        Persadres adres = new Persadres();

        adres.setFunctieadres(getFunctieadres());
        adres.setRdnwijzadres(RandomUtil.getRedenWijzigingAdres());
        adres.setAangadresh(MetaRepo.get(Aangadresh.class, RandomUtil.getAangifteAdreshoudingOms().getOmschrijving()));
        adres.setDataanvadresh(randomDate());
        adres.setAdresseerbaarobject(adresGenerator.getAdresseerbaarobject());
        adres.setIdentcodenraand(adresGenerator.getIdentcodenraand());
        adres.setPartij(adresGenerator.getGemeente());
        String adresNor = adresGenerator.getAdresNor();
        adres.setNor(adresNor);
        adres.setAfgekortenor(adresNor);
        adres.setGemdeel(adresGenerator.getGemDeel());
        adres.setHuisnr(adresGenerator.getAdresHuisnummer());
        adres.setHuisletter(adresGenerator.getAdresHuisletter());
        adres.setHuisnrtoevoeging(adresGenerator.getAdresHuisnummertoevoeging());
        adres.setPostcode(adresGenerator.getAdresPostcode());
        adres.setPlaats(adresGenerator.getPlaats());
        // Aangepast voor constraint in brp.sql (NULL / "by" / "to")
        if (RandomUtil.isFractie(1500)) adres.setLoctovadres(RandomUtil.isFractie(2) ? "by" : "to");
        if (RandomUtil.isFractie(1500)) adres.setLocoms(RandomStringUtils.randomAlphabetic(20));
        adres.setLand(MetaRepo.get(Land.class, Locatie.LAND_CODE_NL));

        return adres;
    }

}
