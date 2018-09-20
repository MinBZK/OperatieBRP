/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

import static nl.bzk.brp.testdatageneratie.RandomService.randomDate;

import java.util.Date;

import nl.bzk.brp.testdatageneratie.GenUtil;
import nl.bzk.brp.testdatageneratie.MetaRepo;
import nl.bzk.brp.testdatageneratie.RandomService;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Locatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Aangadresh;
import nl.bzk.brp.testdatageneratie.domain.kern.Functieadres;
import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Land;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import org.apache.commons.lang3.RandomStringUtils;


public class PersAdresGenerator {

    private static final long MAX_WOONDUUR_IN_MS = 2L * 10 * 365 * 24 * 60 * 60 * 1000;

    public static HisPersadres[] generateHisPersAdressen(final Persadres persadres) {
        Integer dataanvHuidig = persadres.getDataanvadresh();
        Date tsReg = RandomService.getTimestamp(dataanvHuidig);
        HisPersadres hisHuidig = generateHisPersAdres(persadres);
        hisHuidig.setDataanvgel(dataanvHuidig);
        hisHuidig.setTsreg(tsReg);

        int aantalExclusiefHuidig = RandomService.random.nextInt(6);
        if (aantalExclusiefHuidig == 0) {
            persadres.setRdnwijzadres(null);
        }

        if (persadres.getPers().getDatoverlijden() == null) {
            persadres.setPlaats(RandomService.getWplNuGeldig());
            persadres.setPartij(RandomService.getGemeenteNuGeldig());
        }

        HisPersadres[] hisPersadresen = new HisPersadres[1 + aantalExclusiefHuidig * 2];
        hisPersadresen[0] = hisHuidig;
        for (int i = 1; i < hisPersadresen.length - 1; i++) {
            Date tsRegVroeger =
                RandomService.getPastTimestamp(tsReg.getTime() - RandomService.DAG_IN_MS, MAX_WOONDUUR_IN_MS);
            int brpDatumAanvangGeldigheid = GenUtil.naarBrpDatum(tsRegVroeger);

            hisPersadresen[i] = generateHisPersAdres(persadres);
            hisPersadresen[i].setDataanvgel(brpDatumAanvangGeldigheid);
            hisPersadresen[i].setDateindegel(hisPersadresen[i-1].getDataanvgel());
            hisPersadresen[i].setTsreg(tsReg);

            i++;

            hisPersadresen[i] = generateHisPersAdres(persadres);
            hisPersadresen[i].setDataanvgel(brpDatumAanvangGeldigheid);
            hisPersadresen[i].setTsreg(tsRegVroeger);
            hisPersadresen[i].setTsverval(tsReg);

            tsReg = tsRegVroeger;
        }
        return hisPersadresen;
    }

    private static HisPersadres generateHisPersAdres(final Persadres persadres) {
        HisPersadres hisPersadres = new HisPersadres(generatePersAdres(persadres.getPers()));
        hisPersadres.setPersadres(persadres);
        return hisPersadres;
    }

    public static Persadres generatePersAdres(final Pers pers) {
        Persadres adres;
        if (RandomService.isFractie(32)) {
            adres = creeerBuitenlandsAdres();
        } else {
            adres = creeerNederlandsAdres();
        }
        adres.setPers(pers);
        return adres;
    }

    private static Persadres creeerBuitenlandsAdres() {
        Persadres adres = new Persadres();
        adres.setFunctieadres(RandomService.getRandom(Functieadres.values()));
        adres.setBladresregel1(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel2(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel3(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel4(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel5(RandomStringUtils.randomAlphabetic(40));
        adres.setBladresregel6(RandomStringUtils.randomAlphabetic(40));
        adres.setLand(RandomService.getLandBuitenlandsAdres());
        int randomDate = randomDate();
        adres.setDatvertrekuitnederland(randomDate);
        adres.setDataanvadresh(randomDate);
        adres.setRdnwijzadres(RandomService.getRedenWijzigingAdres());

        return adres;
    }

    private static Functieadres getFunctieadres() {
        return RandomService.isFractie(109)? Functieadres.BRIEFADRES: Functieadres.WOONADRES;
    }

    private static Persadres creeerNederlandsAdres() {
        AdresGenerator adresGenerator = new AdresGenerator(RandomService.nextLong(9475478));
        Persadres adres = new Persadres();

        adres.setFunctieadres(getFunctieadres());
        adres.setRdnwijzadres(RandomService.getRedenWijzigingAdres());
        adres.setAangadresh(MetaRepo.get(Aangadresh.class, RandomService.getAangifteAdreshoudingOms().getOmschrijving()));
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
        if (RandomService.isFractie(1500)) adres.setLoctovadres(RandomStringUtils.randomAlphabetic(2));
        if (RandomService.isFractie(1500)) adres.setLocoms(RandomStringUtils.randomAlphabetic(20));
        adres.setLand(MetaRepo.get(Land.class, Locatie.LAND_CODE_NL));

        return adres;
    }

}
