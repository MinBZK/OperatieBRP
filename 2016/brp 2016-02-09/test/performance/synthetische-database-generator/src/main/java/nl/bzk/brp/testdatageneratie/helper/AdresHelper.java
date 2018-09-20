/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import java.util.Random;

import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresHuisletter;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresHuisnummer;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresHuisnummertoevoeging;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresNor;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresPostcode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.GemeenteDeel;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.Partij;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;


public class AdresHelper {

    public Random random;

    public AdresHelper(final long seed) {
        random = new Random(seed);
    }

    public Gem getGemeente() {
        return RandomUtil.getGem(random);
    }

    public String getGemDeel() {
        final GemeenteDeel gemeentedeel = BronnenRepo.getBron(GemeenteDeel.class, random);
        return gemeentedeel.getGemeenteDeel();
    }

    public String getIdentcodeAdresseerbaarobject() {
        return (random.nextInt(9000) + 1000) + "0100000" + (random.nextInt(90000) + 10000);
    }

    public String getIdentcodenraand() {
        return "" + (random.nextInt(90000000) + 10000000);
    }

    public String getAdresNor() {
        String waarde = null;
        if (!RandomUtil.isFractie(1481)) {
            do {
                AdresNor adresNor = BronnenRepo.getBron(AdresNor.class, random);
                waarde = adresNor.getWaarde();
                // Het mag geen lege string bevatten ivm constraint op kern.persadres
            } while (waarde.equals(""));
        }
        return waarde;
    }

    public Integer getAdresHuisnummer() {
        AdresHuisnummer adresHuisnummer = BronnenRepo.getBron(AdresHuisnummer.class, random);
        return adresHuisnummer.getWaarde();
    }

    public String getAdresHuisletter() {
        String waarde = null;
        if (RandomUtil.isFractie(14)) {
            AdresHuisletter adresHuisletter = BronnenRepo.getBron(AdresHuisletter.class, random);
            waarde = adresHuisletter.getWaarde();
        }
        return waarde;
    }

    public String getAdresHuisnummertoevoeging() {
        String waarde = null;
        if (RandomUtil.isFractie(23)) {
            AdresHuisnummertoevoeging adresHuisnummertoevoeging =
                BronnenRepo.getBron(AdresHuisnummertoevoeging.class, random);
            waarde = adresHuisnummertoevoeging.getWaarde();
        }
        return waarde;
    }

    public String getAdresPostcode() {
        String waarde = null;
        if (!RandomUtil.isFractie(40)) {
            AdresPostcode adresPostcode = BronnenRepo.getBron(AdresPostcode.class, random);
            waarde = adresPostcode.getWaarde();
        }
        return waarde;
    }

    public Plaats getPlaats() {
        return MetaRepo.getByRandom(Plaats.class, random);
    }

}
