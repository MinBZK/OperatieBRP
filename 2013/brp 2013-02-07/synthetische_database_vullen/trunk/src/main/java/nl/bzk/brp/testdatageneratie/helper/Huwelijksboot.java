/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.utils.GenUtil;
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;


public class Huwelijksboot {

    private static Logger log = Logger.getLogger(Huwelijksboot.class);

    private int           iMan, iVrouw, iEchtgenoot;

    private final int[]         mannen, vrouwen;

    public Huwelijksboot(final int personenPerThread) {
        this.mannen = new int[personenPerThread / 4];
        this.vrouwen = new int[personenPerThread / 4];
    }

    public void toevoegenIndienUitverkoren(final Pers pers) {
        if (RandomUtil.isFractie(2) && pers.getDatoverlijden() == null && GenUtil.isMeerderjarig(pers)) {
            if (Geslachtsaanduiding.MAN.equals(pers.getGeslachtsaand())) {
                if (iMan < mannen.length) {
                    mannen[iMan] = pers.getId();
                    iMan++;
                }
            } else if (Geslachtsaanduiding.VROUW.equals(pers.getGeslachtsaand())) {
                if (iVrouw < vrouwen.length) {
                    vrouwen[iVrouw] = pers.getId();
                    iVrouw++;
                }
            }
        }
    }

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

    public void valideer() {
        if (mannen[mannen.length - 1] != 0) {
            log.warn("alle ruimte voor mannen verbruikt");
        }
        if (vrouwen[vrouwen.length - 1] != 0) {
            log.warn("alle ruimte voor vrouwen verbruikt");
        }
    }

    public int getAantalHuwelijken() {
        return iEchtgenoot > 0 ? iEchtgenoot : iMan < iVrouw ? iMan : iVrouw;
    }

}
