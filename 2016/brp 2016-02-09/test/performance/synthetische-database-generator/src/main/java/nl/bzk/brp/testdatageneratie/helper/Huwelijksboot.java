/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.Constanten;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;


/**
 * Huwelijksboot.
 */
public class Huwelijksboot {

    private static Logger log = Logger.getLogger(Huwelijksboot.class);

    private int           iMan, iVrouw, iEchtgenoot;

    private final int[]         mannen, vrouwen;

    /**
     * Instantieert Huwelijksboot.
     *
     * @param personenPerThread personen per thread
     */
    public Huwelijksboot(final int personenPerThread) {
        mannen = new int[personenPerThread / Constanten.VIER];
        vrouwen = new int[personenPerThread / Constanten.VIER];
    }

    /**
     * Toevoegen van persoon aan huwelijksboot indien uitverkoren.
     *
     * @param pers pers
     */
    public void toevoegenIndienUitverkoren(final Pers pers) {
        if (RandomUtil.isFractie(2) && pers.getDatoverlijden() == null && GenUtil.isMeerderjarig(pers)) {
            if (Geslachtsaanduiding.MAN.ordinal() == pers.getGeslachtsaand().intValue()) {
                if (iMan < mannen.length) {
                    mannen[iMan] = pers.getId();
                    iMan++;
                }
            } else if (Geslachtsaanduiding.VROUW.ordinal() == pers.getGeslachtsaand().intValue()) {
                if (iVrouw < vrouwen.length) {
                    vrouwen[iVrouw] = pers.getId();
                    iVrouw++;
                }
            }
        }
    }

    /**
     * Volgende huwelijk.
     *
     * @return volgende huwelijk
     */
    public Huwelijk volgendeHuwelijk() {
        Huwelijk huwelijk;
        if (iEchtgenoot >= iMan || iVrouw <= 0) {
            huwelijk = null;
            valideer();
        } else {
            huwelijk = new Huwelijk(mannen[iEchtgenoot++], vrouwen[--iVrouw]);
        }
        return huwelijk;
    }

    /**
     * Valideer array.
     */
    public void valideer() {
        if (mannen[mannen.length - 1] != 0) {
            log.warn("alle ruimte voor mannen verbruikt");
        }
        if (vrouwen[vrouwen.length - 1] != 0) {
            log.warn("alle ruimte voor vrouwen verbruikt");
        }
    }

    /**
     * Geeft aantal huwelijken.
     *
     * @return aantal huwelijken
     */
    public int getAantalHuwelijken() {
        int aantalHuwelijken;

        if (iEchtgenoot > 0) {
            aantalHuwelijken = iEchtgenoot;
        } else if (iMan < iVrouw) {
            aantalHuwelijken = iMan;
        } else {
            aantalHuwelijken = iVrouw;
        }

        return aantalHuwelijken;
    }

}
