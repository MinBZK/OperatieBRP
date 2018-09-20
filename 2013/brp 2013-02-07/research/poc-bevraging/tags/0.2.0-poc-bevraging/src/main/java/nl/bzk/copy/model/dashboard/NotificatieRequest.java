/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.dashboard;

import java.util.List;


public interface NotificatieRequest {

    public nl.bzk.copy.model.dashboard.BerichtKenmerken getKenmerken();

    public void setKenmerken(final nl.bzk.copy.model.dashboard.BerichtKenmerken kenmerken);

    public Verwerking getVerwerking();

    public void setVerwerking(final Verwerking verwerking);

    public List<nl.bzk.copy.model.dashboard.Melding> getMeldingen();

    public void setMeldingen(final List<Melding> meldingen);
}
