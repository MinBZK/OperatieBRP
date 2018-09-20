/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import nl.moderniseringgba.isc.esb.message.BerichtFactory;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If21Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.If31Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Iv01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.La01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Pf03Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Vb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Wa01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Wf01Bericht;

/**
 * Vertaal een binnengekomen LO3 bericht naar een ESB LO3 Bericht object.
 */
public final class Lo3BerichtFactory implements BerichtFactory {

    private static final Lo3Header GENERIEKE_HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY,
            Lo3HeaderVeld.BERICHTNUMMER);

    /**
     * Vertaal een binnengekomen LO3 bericht naar een ESB LO3 Bericht object.
     * 
     * @param lo3Bericht
     *            binnengekomen LO3 bericht
     * @return ESB LO3 Bericht object
     */
    // CHECKSTYLE:OFF - Throws count - Meerder expliciete exceptions is hier duidelijker
    @Override
    public Lo3Bericht getBericht(final String lo3Bericht) {
        // CHECKSTYLE:ON
        if (lo3Bericht == null || "".equals(lo3Bericht)) {
            return new NullBericht();
        }

        String[] headers;
        try {
            headers = GENERIEKE_HEADER.parseHeaders(lo3Bericht);
        } catch (final BerichtSyntaxException e1) {
            return new OnbekendBericht(lo3Bericht, "BerichtNummer kan niet worden bepaald.");
        }
        final BerichtType berichtType;
        try {
            berichtType = BerichtType.valueOf(headers[1].toUpperCase());

        } catch (final IllegalArgumentException e) {
            return new OnbekendBericht(lo3Bericht, "BerichtNummer is onbekend.");
        }
        final Lo3Bericht bericht = berichtType.getBericht();

        try {
            bericht.parse(lo3Bericht);
        } catch (final BerichtSyntaxException e) {
            return new OngeldigBericht(lo3Bericht, e.getMessage());
        } catch (final BerichtInhoudException e) {
            return new OngeldigBericht(lo3Bericht, e.getMessage());
        }

        return bericht;
    }

    /**
     * Berichttypen.
     */
    private static enum BerichtType {
        // @formatter:off
        IB01 { @Override Lo3Bericht getBericht() { return new Ib01Bericht(); } },
        IF01 { @Override Lo3Bericht getBericht() { return new If01Bericht(); } },
        IF21 { @Override Lo3Bericht getBericht() { return new If21Bericht(); } },
        IF31 { @Override Lo3Bericht getBericht() { return new If31Bericht(); } },
        II01 { @Override Lo3Bericht getBericht() { return new Ii01Bericht(); } },
        IV01 { @Override Lo3Bericht getBericht() { return new Iv01Bericht(); } },
        LA01 { @Override Lo3Bericht getBericht() { return new La01Bericht(); } },
        LG01 { @Override Lo3Bericht getBericht() { return new Lg01Bericht(); } },
        PF01 { @Override Lo3Bericht getBericht() { return new Pf01Bericht(); } },
        PF02 { @Override Lo3Bericht getBericht() { return new Pf02Bericht(); } },
        PF03 { @Override Lo3Bericht getBericht() { return new Pf03Bericht(); } },
        TB01 { @Override Lo3Bericht getBericht() { return new Tb01Bericht(); } },
        TB02 { @Override Lo3Bericht getBericht() { return new Tb02Bericht(); } },
        TF01 { @Override Lo3Bericht getBericht() { return new Tf01Bericht(); } },
        TF11 { @Override Lo3Bericht getBericht() { return new Tf11Bericht(); } },
        TV01 { @Override Lo3Bericht getBericht() { return new Tv01Bericht(); } },
        VB01 { @Override Lo3Bericht getBericht() { return new Vb01Bericht(); } },
        WA01 { @Override Lo3Bericht getBericht() { return new Wa01Bericht(); } },
        WF01 { @Override Lo3Bericht getBericht() { return new Wf01Bericht(); } };
        // @formatter:on

        /**
         * Geef een ESB LO3 bericht instantie voor dit bericht type.
         * 
         * @return ESB LO3 bericht object
         */
        abstract Lo3Bericht getBericht();
    }

}
