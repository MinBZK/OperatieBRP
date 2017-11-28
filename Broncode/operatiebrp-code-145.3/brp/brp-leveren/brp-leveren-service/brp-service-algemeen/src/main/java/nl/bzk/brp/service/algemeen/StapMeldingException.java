/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import org.apache.commons.lang3.StringUtils;

/**
 * Exceptieklasse voor functionele fouten die optreden in verwerkingsstappen.
 */
public class StapMeldingException extends StapException {
    private static final long serialVersionUID = 7500556507507782745L;

    private final List<Melding> meldingen = new LinkedList<>();

    /**
     * Constructor voor fout in stap met melding.
     * @param melding de opgetreden melding
     */
    public StapMeldingException(final Melding melding) {
        this(Collections.singletonList(melding));
    }

    /**
     * Constructor voor fout in stap met melding en exception.
     * @param melding de opgetreden melding
     * @param exception exception
     */
    public StapMeldingException(final Melding melding, final Exception exception) {
        this(Collections.singletonList(melding), exception);
    }

    /**
     * Constructor voor het geval er meerdere meldingen zijn.
     * @param meldingen de opgetreden meldingen
     */
    public StapMeldingException(final List<Melding> meldingen) {
        this(meldingen, null);
    }

    /**
     * Constructor voor het geval er meerdere meldingen zijn.
     * @param meldingen de opgetreden meldingen
     * @param exception exception
     */
    public StapMeldingException(final List<Melding> meldingen, final Exception exception) {
        super(String.format("Functionele regelfout(en): %n %s", maakFoutregels(meldingen)), exception);
        this.meldingen.addAll(meldingen);
    }

    /**
     * Maakt een foutmelding obv een regel.
     * @param regel de overtredende regel
     */
    public StapMeldingException(final Regel regel) {
        this(new Melding(regel));
    }

    /**
     * Maakt een foutmelding obv een regel en exception.
     * @param regel regel
     * @param exception exception
     */
    public StapMeldingException(final Regel regel, final Exception exception) {
        super(exception);
        this.meldingen.addAll(Collections.singletonList(new Melding(regel)));
    }

    /**
     * @return de opgetreden melding
     */
    public final List<Melding> getMeldingen() {
        return meldingen;
    }

    private static String maakFoutregels(final Iterable<Melding> meldingIterable) {
        final StringBuilder sb = new StringBuilder();
        for (final Melding melding : meldingIterable) {
            sb.append(maakFoutregel(melding));
        }
        return sb.toString();
    }

    private static String maakFoutregel(final Melding melding) {
        return String.format("[Regel %s - %s] %n", melding.getRegel().getCode(),
                StringUtils.defaultIfEmpty(melding.getMeldingTekst(), melding.getRegel().getMelding()));
    }
}
