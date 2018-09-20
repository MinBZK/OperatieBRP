/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.model.validatie.Melding;

/**
 * TODO: Add documentation
 */
public class AbstractStappenResultaat implements StappenResultaat {

    protected final List<Melding> meldingen;
    protected boolean resultaat = true;

    public AbstractStappenResultaat() {
        this(null);
    }

    public AbstractStappenResultaat(final List<Melding> meldingen) {
        this.meldingen = new ArrayList<Melding>();

        if (meldingen != null) {
            this.meldingen.addAll(meldingen);
            Collections.sort(this.meldingen);
        }
    }

    public List<Melding> getMeldingen() {
        return Collections.unmodifiableList(meldingen);
    }

    public void voegMeldingToe(final Melding melding) {
        meldingen.add(melding);
        Collections.sort(meldingen);
    }

    public void voegMeldingenToe(final List<Melding> leveringMeldingen) {
        meldingen.addAll(leveringMeldingen);
        Collections.sort(meldingen);
    }

    @Override
    public boolean isFoutief() {
        return resultaat;
    }

    @Override
    public boolean isSuccesvol() {
        return resultaat;
    }
}
