/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.antwoord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.bevraging.business.dto.antwoord.AbstractBerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.domein.kern.Persoon;

public class BijhoudingResultaat extends AbstractBerichtAntwoord implements BerichtAntwoord {

    private String                       resultaatOmschrijving;
    private List<BijhoudingWaarschuwing> waarschuwingen;

    public BijhoudingResultaat() {
        waarschuwingen = new ArrayList<BijhoudingWaarschuwing>();
    }

    public BijhoudingResultaat(final String resultaatOmschrijving) {
        this();
        this.resultaatOmschrijving = resultaatOmschrijving;
    }

    public String getResultaatOmschrijving() {
        return resultaatOmschrijving;
    }

    public void setResultaatOmschrijving(final String resultaatOmschrijving) {
        this.resultaatOmschrijving = resultaatOmschrijving;
    }

    public List<BijhoudingWaarschuwing> getWaarschuwingen() {
        return Collections.unmodifiableList(waarschuwingen);
    }

    public void voegWaarschuwingToe(final BijhoudingWaarschuwing waarschuwing) {
        waarschuwingen.add(waarschuwing);
    }

    @Override
    public Collection<Persoon> getPersonen() {
        return null;
    }

    @Override
    public void wisContent() {
        return;
    }

    /**
     * Retourneert of het bijhoudings resultaat één of meerdere fouten heeft.
     *
     * @return of er een fout is opgetreden.
     */
    public boolean heeftBedrijfsregelFout() {
        boolean fout = false;
        for (BijhoudingWaarschuwing waarschuwing : waarschuwingen) {
            if (waarschuwing.getNiveau() == BijhoudingWaarschuwingNiveau.ZACHTE_FOUT
                    || waarschuwing.getNiveau() == BijhoudingWaarschuwingNiveau.HARDE_FOUT)
            {
                fout = true;
                break;
            }
        }
        return fout;
    }

}
