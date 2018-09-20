/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.dashboard;

import java.util.List;


/**
 * POJO voor het bericht van de BRP.
 */
public abstract class AbstractBerichtRequest implements NotificatieRequest {

    private BerichtKenmerken kenmerken;

    private Verwerking verwerking;

    private List<Melding> meldingen;

    /**
     * Constructor voor JSON mapping framework.
     */
    public AbstractBerichtRequest() {
    }

    public BerichtKenmerken getKenmerken() {
        return kenmerken;
    }

    public void setKenmerken(final BerichtKenmerken kenmerken) {
        this.kenmerken = kenmerken;
    }

    public Verwerking getVerwerking() {
        return verwerking;
    }

    public void setVerwerking(final Verwerking verwerking) {
        this.verwerking = verwerking;
    }

    public List<Melding> getMeldingen() {
        return meldingen;
    }

    public void setMeldingen(final List<Melding> meldingen) {
        this.meldingen = meldingen;
    }

}
