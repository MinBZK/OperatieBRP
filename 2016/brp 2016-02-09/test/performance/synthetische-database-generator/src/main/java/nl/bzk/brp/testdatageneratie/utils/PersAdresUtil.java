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
import nl.bzk.brp.testdatageneratie.domain.kern.Aang;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.Landgebied;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.helper.AdresHelper;
import org.apache.commons.lang3.RandomStringUtils;


/**
 * Pers adres utility klasse.
 */
public final class PersAdresUtil {

    private static final int FRACTIE_BRIEFADRES = 109;
    private static final int FRACTIE_BUITENLANDS_ADRES = 32;
    private static final int FRACTIE_LOCOMS = 1500;
    private static final int FRACTIE_LOCTOVADRES = 1500;
    private static final int TWEE = 2;
    private static final int TWINTIG = 20;
    private static final int VEERTIG = 40;
    private static final long MAX_RANDOM_ADRESHELPER = 9475478;

    private static Integer landNlUitRepo = null;

    /**
     * Private constructor.
     */
    private PersAdresUtil() {

    }

    /**
     * Genereert een pers adres voor synthetische data.
     *
     * @param pers pers
     * @return persadres
     */
    public static Persadres generatePersAdres(final Pers pers) {
        if (landNlUitRepo == null) {
            landNlUitRepo = MetaRepo.get(Landgebied.class, Locatie.LAND_CODE_NL).getId();
        }
        Persadres adres;
        if (RandomUtil.isFractie(FRACTIE_BUITENLANDS_ADRES)) {
            adres = creeerBuitenlandsAdres();
        } else {
            adres = creeerNederlandsAdres();
        }
        adres.setPers(pers.getId());
        return adres;
    }

    /**
     * Generate pers voor brp data.
     *
     * @param persId pers id
     * @return persadres
     */
    public static Persadres generatePersAdres(final Integer persId) {
        final Persadres adres = creeerNederlandsAdres();
        adres.setPers(persId);
        return adres;
    }

    /**
     * Creeert een buitenlands adres.
     *
     * @return buitenlands persadres
     */
    private static Persadres creeerBuitenlandsAdres() {
        Persadres adres = new Persadres();
        adres.setSrt((short) RandomUtil.getRandom(FunctieAdres.values()).ordinal());
        adres.setBladresregel1(RandomStringUtils.randomAlphabetic(VEERTIG));
        adres.setBladresregel2(RandomStringUtils.randomAlphabetic(VEERTIG));
        adres.setBladresregel3(RandomStringUtils.randomAlphabetic(VEERTIG));
        adres.setBladresregel4(RandomStringUtils.randomAlphabetic(VEERTIG));
        adres.setBladresregel5(RandomStringUtils.randomAlphabetic(VEERTIG));
        adres.setBladresregel6(RandomStringUtils.randomAlphabetic(VEERTIG));
        Landgebied land = RandomUtil.getLandgebiedBuitenlandsAdres();
        if (land != null) {
            adres.setLandgebied(land.getId());
        }
        int randomDate = randomDate();
        adres.setDataanvadresh(randomDate);
        adres.setRdnwijz((short) RandomUtil.getRedenWijzigingAdres().ordinal());

        return adres;
    }

    /**
     * Geeft een functieadres.
     *
     * @return the functieadres
     */
    private static FunctieAdres getFunctieadres() {
        FunctieAdres functieAdres;
        if (RandomUtil.isFractie(FRACTIE_BRIEFADRES)) {
            functieAdres = FunctieAdres.BRIEFADRES;
        } else {
            functieAdres = FunctieAdres.WOONADRES;
        }

        return functieAdres;
    }

    /**
     * Creeert een nederlands adres.
     *
     * @return persadres
     */
    private static Persadres creeerNederlandsAdres() {
        final AdresHelper adresGenerator = new AdresHelper(RandomUtil.nextLong(MAX_RANDOM_ADRESHELPER));
        final Persadres adres = new Persadres();

        adres.setSrt((short) getFunctieadres().ordinal());
        adres.setRdnwijz((short) RandomUtil.getRedenWijzigingAdres().ordinal());
        final Aang aangever =
                MetaRepo.get(Aang.class, RandomUtil.getAangifteAdreshoudingOms().getOmschrijving());
        if (aangever != null) {
            adres.setAangadresh(aangever.getId());
        }
        adres.setDataanvadresh(randomDate());
        adres.setIdentcodeadresseerbaarobject(adresGenerator.getIdentcodeAdresseerbaarobject());
        adres.setIdentcodenraand(adresGenerator.getIdentcodenraand());
        final Gem gem = adresGenerator.getGemeente();
        if (gem != null) {
            adres.setGem(gem.getId());
        }
        adres.setGemdeel(adresGenerator.getGemDeel());
        final String adresNor = adresGenerator.getAdresNor();
        adres.setNor(adresNor);
        adres.setAfgekortenor(adresNor);
        adres.setHuisnr(adresGenerator.getAdresHuisnummer());
        adres.setHuisletter(adresGenerator.getAdresHuisletter());
        adres.setHuisnrtoevoeging(adresGenerator.getAdresHuisnummertoevoeging());
        adres.setPostcode(adresGenerator.getAdresPostcode());
        final Plaats plaats = adresGenerator.getPlaats();
        if (plaats != null) {
            adres.setWplnaam(plaats.getNaam());
        }
        // Aangepast voor constraint in brp.sql (NULL / "by" / "to")
        if (RandomUtil.isFractie(FRACTIE_LOCTOVADRES)) {
            if (RandomUtil.isFractie(TWEE)) {
                adres.setLoctenopzichtevanadres("by");
            } else {
                adres.setLoctenopzichtevanadres("to");
            }
        }
        if (RandomUtil.isFractie(FRACTIE_LOCOMS)) {
            adres.setLocoms(RandomStringUtils.randomAlphabetic(TWINTIG));
        }
        adres.setLandgebied(landNlUitRepo);

        return adres;
    }

}
