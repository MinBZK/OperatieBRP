/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.factory;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ag01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ag11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ag21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ag31Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.DeliveryReport;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Gv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Gv02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ha01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ib01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If31Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If41Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Iv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Iv11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Iv21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Jb01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Jf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Jf21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Jf31Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ji01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Jv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ng01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Of11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Og11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeInhoudBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Rb01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Rf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Rf31Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Rv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Sf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.StatusReport;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Sv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Sv11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Wa01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Wa11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Wf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xa01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;

/**
 * Vertaal een binnengekomen LO3 bericht naar een ESB LO3 Bericht object.
 */
public final class Lo3BerichtFactory implements BerichtFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int MINIMALE_BERICHT_LENGTE = 12;
    private static final Lo3Header GENERIEKE_HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER);

    /**
     * Vertaal een binnengekomen LO3 bericht naar een ESB LO3 Bericht object.
     * @param lo3Bericht binnengekomen LO3 bericht
     * @return ESB LO3 Bericht object
     */
    @Override
    public Lo3Bericht getBericht(final String lo3Bericht) {
        Lo3Bericht bericht;
        if (lo3Bericht == null || lo3Bericht.isEmpty()) {
            bericht = new NullBericht();
        } else if (lo3Bericht.length() < MINIMALE_BERICHT_LENGTE) {
            bericht = new OnbekendBericht(lo3Bericht, "Bericht is te kort om BerichtNummer te bepalen");
        } else {
            try {
                final String[] headers = GENERIEKE_HEADER.parseHeaders(lo3Bericht);
                bericht = parseBericht(lo3Bericht, BerichtType.valueOf(headers[1].toUpperCase()).getBericht());
            } catch (final BerichtSyntaxException | IllegalArgumentException e) {
                final String melding = "BerichtNummer kan niet worden bepaald of is onbekend.";
                LOGGER.warn(melding, e);
                bericht = new OnbekendBericht(lo3Bericht, melding);
            }
        }
        return bericht;
    }

    private Lo3Bericht parseBericht(String lo3Bericht, Lo3Bericht bericht) {
        try {
            bericht.parse(lo3Bericht);
            return bericht;
        } catch (final BerichtSyntaxException e) {
            LOGGER.warn("Bericht bevat geen geldige syntax", e);
            return new OngeldigeSyntaxBericht(lo3Bericht, e.getMessage());
        } catch (final BerichtInhoudException e) {
            LOGGER.warn("Bericht bevat geen geldige inhoud", e);
            return new OngeldigeInhoudBericht(lo3Bericht, e.getMessage());
        }
    }

    /**
     * Berichttypen.
     */
    private enum BerichtType {
        AF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Af01Bericht();
            }
        },
        AF11 {
            @Override
            Lo3Bericht getBericht() {
                return new Af11Bericht();
            }
        },
        AG01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ag01Bericht();
            }
        },
        AG11 {
            @Override
            Lo3Bericht getBericht() {
                return new Ag11Bericht();
            }
        },
        AG21 {
            @Override
            Lo3Bericht getBericht() {
                return new Ag21Bericht();
            }
        },
        AG31 {
            @Override
            Lo3Bericht getBericht() {
                return new Ag31Bericht();
            }
        },
        AP01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ap01Bericht();
            }
        },
        AV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Av01Bericht();
            }
        },
        GV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Gv01Bericht();
            }
        },
        GV02 {
            @Override
            Lo3Bericht getBericht() {
                return new Gv02Bericht();
            }
        },
        HA01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ha01Bericht();
            }
        },
        HF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Hf01Bericht();
            }
        },
        HQ01 {
            @Override
            Lo3Bericht getBericht() {
                return new Hq01Bericht();
            }
        },
        IB01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ib01Bericht();
            }
        },
        IF01 {
            @Override
            Lo3Bericht getBericht() {
                return new If01Bericht();
            }
        },
        IF21 {
            @Override
            Lo3Bericht getBericht() {
                return new If21Bericht();
            }
        },
        IF31 {
            @Override
            Lo3Bericht getBericht() {
                return new If31Bericht();
            }
        },
        IF41 {
            @Override
            Lo3Bericht getBericht() {
                return new If41Bericht();
            }
        },
        II01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ii01Bericht();
            }
        },
        IV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Iv01Bericht();
            }
        },
        IV11 {
            @Override
            Lo3Bericht getBericht() {
                return new Iv11Bericht();
            }
        },
        IV21 {
            @Override
            Lo3Bericht getBericht() {
                return new Iv21Bericht();
            }
        },
        JB01 {
            @Override
            Lo3Bericht getBericht() {
                return new Jb01Bericht();
            }
        },
        JF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Jf01Bericht();
            }
        },
        JB21 {
            @Override
            Lo3Bericht getBericht() {
                return new Jf21Bericht();
            }
        },
        JB31 {
            @Override
            Lo3Bericht getBericht() {
                return new Jf31Bericht();
            }
        },
        JI01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ji01Bericht();
            }
        },
        JV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Jv01Bericht();
            }
        },
        LA01 {
            @Override
            Lo3Bericht getBericht() {
                return new La01Bericht();
            }
        },
        LF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Lf01Bericht();
            }
        },
        LG01 {
            @Override
            Lo3Bericht getBericht() {
                return new Lg01Bericht();
            }
        },
        LQ01 {
            @Override
            Lo3Bericht getBericht() {
                return new Lq01Bericht();
            }
        },
        NG01 {
            @Override
            Lo3Bericht getBericht() {
                return new Ng01Bericht();
            }
        },
        OF11 {
            @Override
            Lo3Bericht getBericht() {
                return new Of11Bericht();
            }
        },
        OG11 {
            @Override
            Lo3Bericht getBericht() {
                return new Og11Bericht();
            }
        },
        PF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Pf01Bericht();
            }
        },
        PF02 {
            @Override
            Lo3Bericht getBericht() {
                return new Pf02Bericht();
            }
        },
        PF03 {
            @Override
            Lo3Bericht getBericht() {
                return new Pf03Bericht();
            }
        },
        RB01 {
            @Override
            Lo3Bericht getBericht() {
                return new Rb01Bericht();
            }
        },
        RF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Rf01Bericht();
            }
        },
        RF31 {
            @Override
            Lo3Bericht getBericht() {
                return new Rf31Bericht();
            }
        },
        RV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Rv01Bericht();
            }
        },
        SF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Sf01Bericht();
            }
        },
        SV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Sv01Bericht();
            }
        },
        SV11 {
            @Override
            Lo3Bericht getBericht() {
                return new Sv11Bericht();
            }
        },
        TB01 {
            @Override
            Lo3Bericht getBericht() {
                return new Tb01Bericht();
            }
        },
        TB02 {
            @Override
            Lo3Bericht getBericht() {
                return new Tb02Bericht();
            }
        },
        TF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Tf01Bericht();
            }
        },
        TF11 {
            @Override
            Lo3Bericht getBericht() {
                return new Tf11Bericht();
            }
        },
        TF21 {
            @Override
            Lo3Bericht getBericht() {
                return new Tf21Bericht();
            }
        },
        TV01 {
            @Override
            Lo3Bericht getBericht() {
                return new Tv01Bericht();
            }
        },
        VB01 {
            @Override
            Lo3Bericht getBericht() {
                return new Vb01Bericht();
            }
        },
        WA01 {
            @Override
            Lo3Bericht getBericht() {
                return new Wa01Bericht();
            }
        },
        WA11 {
            @Override
            Lo3Bericht getBericht() {
                return new Wa11Bericht();
            }
        },
        WF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Wf01Bericht();
            }
        },
        XA01 {
            @Override
            Lo3Bericht getBericht() {
                return new Xa01Bericht();
            }
        },
        XF01 {
            @Override
            Lo3Bericht getBericht() {
                return new Xf01Bericht();
            }
        },
        XQ01 {
            @Override
            Lo3Bericht getBericht() {
                return new Xq01Bericht();
            }
        },
        STAR {
            @Override
            Lo3Bericht getBericht() {
                return new StatusReport();
            }
        },
        DELR {
            @Override
            Lo3Bericht getBericht() {
                return new DeliveryReport();
            }
        };

        /**
         * Geef een ESB LO3 bericht instantie voor dit bericht type.
         * @return ESB LO3 bericht object
         */
        abstract Lo3Bericht getBericht();
    }

}
