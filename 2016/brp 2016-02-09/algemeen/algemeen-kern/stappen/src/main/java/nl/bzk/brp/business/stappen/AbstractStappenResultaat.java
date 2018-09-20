/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Abstracte klasse voor het resultaat van een stap. Deze klasse dient als basis voor een resultaat klasse in het stappen proces.
 */
public abstract class AbstractStappenResultaat implements StappenResultaat {

    private final List<Melding> meldingen;

    /**
     * Maak een resultaat aan zonder meldingen.
     */
    public AbstractStappenResultaat() {
        this(null);
    }

    /**
     * Maak een resultaat aan met een initi&euml;le lijst van meldingen.
     *
     * @param meldingen de lijst van initi&euml;le meldingen
     */
    public AbstractStappenResultaat(final List<Melding> meldingen) {
        this.meldingen = new ArrayList<>();

        if (meldingen != null) {
            this.meldingen.addAll(meldingen);
            Collections.sort(this.meldingen);
        }
    }

    /**
     * Retourneert de (onaanpasbare) lijst van meldingen.
     *
     * @return de lijst van meldingen.
     */
    public List<Melding> getMeldingen() {
        return Collections.unmodifiableList(meldingen);
    }

    /**
     * Voegt een melding toe aan de huidige reeks van meldingen.
     *
     * @param melding De toe te voegen melding.
     */
    public void voegMeldingToe(final Melding melding) {
        if (null != melding) {
            meldingen.add(melding);
            sorteerMeldingen();
        }
    }

    /**
     * Deblokkeer een melding, waarmee zijn soortmelding wordt verlaagd in niveau van deblokkeerbaar naar waarschuwing.
     *
     * @param melding de melding die gedeblokkeerd moet worden
     */
    public final void deblokkeer(final Melding melding) {
        final int index = meldingen.indexOf(melding);

        if (index > -1) {
            melding.overrule();
            meldingen.set(index, melding);
            sorteerMeldingen();
        }
    }

    /**
     * Voegt een lijst van meldingen toe aan de huidige reeks van meldingen en sorteert ze.
     *
     * @param lijstMeldingen De toe te voegen meldingen.
     */
    public void voegMeldingenToe(final List<Melding> lijstMeldingen) {
        if (null != lijstMeldingen) {
            meldingen.addAll(lijstMeldingen);
            sorteerMeldingen();
        }
    }

    /**
     * Methode om de meldingen (opnieuw) te sorteren. Dit is van belang wanneer meldingen van status veranderen.
     */
    public final void sorteerMeldingen() {
        Collections.sort(meldingen);
    }

    @Override
    public boolean isFoutief() {
        return bevatStoppendeFouten();
    }

    @Override
    public boolean isSuccesvol() {
        return !bevatStoppendeFouten();
    }

    @Override
    public boolean bevatStoppendeFouten() {
        for (final Melding melding : meldingen) {
            if (melding.getSoort() == SoortMelding.FOUT || melding.getSoort() == SoortMelding.DEBLOKKEERBAAR) {
                return true;
            }
        }
        return false;
    }
}
