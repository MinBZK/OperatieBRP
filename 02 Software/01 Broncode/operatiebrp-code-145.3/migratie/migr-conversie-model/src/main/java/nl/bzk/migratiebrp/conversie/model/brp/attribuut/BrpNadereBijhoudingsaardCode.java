/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Deze enum representeert een BRP Reden nadere bijhoudingsaard. Dit is een statische stamtabel en daarom een enum en
 * geen class.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpNadereBijhoudingsaardCode extends AbstractBrpAttribuutMetOnderzoek {

    /**
     * Actueel.
     */
    public static final BrpNadereBijhoudingsaardCode ACTUEEL = new BrpNadereBijhoudingsaardCode("A");

    /**
     * Rechtstreeks niet ingezetene.
     */
    public static final BrpNadereBijhoudingsaardCode RECHTSTREEKS_NIET_INGEZETENE = new BrpNadereBijhoudingsaardCode("R");

    /**
     * Emigratie.
     */
    public static final BrpNadereBijhoudingsaardCode EMIGRATIE = new BrpNadereBijhoudingsaardCode("E");

    /**
     * Overleden.
     */
    public static final BrpNadereBijhoudingsaardCode OVERLEDEN = new BrpNadereBijhoudingsaardCode("O");

    /**
     * Vertrokken onbekend waarheen.
     */
    public static final BrpNadereBijhoudingsaardCode VERTROKKEN_ONBEKEND_WAARHEEN = new BrpNadereBijhoudingsaardCode("V");

    /**
     * Bijzondere status.
     */
    public static final BrpNadereBijhoudingsaardCode BIJZONDERE_STATUS = new BrpNadereBijhoudingsaardCode("M");

    /**
     * Fout.
     */
    public static final BrpNadereBijhoudingsaardCode FOUT = new BrpNadereBijhoudingsaardCode("F");

    /**
     * Onbekend.
     */
    public static final BrpNadereBijhoudingsaardCode ONBEKEND = new BrpNadereBijhoudingsaardCode("?");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een BrpNadereBijhoudingsaardCode.
     * @param waarde BRP code
     */
    public BrpNadereBijhoudingsaardCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een BrpNadereBijhoudingsaardCode object met onderzoek.
     * @param waarde de waarde
     * @param onderzoek het onderzoek waar deze waarde onder valt. Mag NULL zijn.
     */
    public BrpNadereBijhoudingsaardCode(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public String getWaarde() {
        return (String) unwrapImpl(this);
    }
}
