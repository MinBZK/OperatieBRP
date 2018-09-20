/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.Nationaliteit;



/**
 * Persoon Nationaliteit.
 *
 */
public class PersoonNationaliteit implements Comparable<PersoonNationaliteit> {

    private Nationaliteit nationaliteit;

    private String        redenVerliesNaam;

    private String        redenVerkrijgingNaam;

    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    public void setNationaliteit(final Nationaliteit nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public String getRedenVerliesNaam() {
        return redenVerliesNaam;
    }

    public void setRedenVerliesNaam(final String redenVerliesNaam) {
        this.redenVerliesNaam = redenVerliesNaam;
    }

    public String getRedenVerkrijgingNaam() {
        return redenVerkrijgingNaam;
    }

    public void setRedenVerkrijgingNaam(final String redenVerkrijgingNaam) {
        this.redenVerkrijgingNaam = redenVerkrijgingNaam;
    }

    @Override
    public int compareTo(final PersoonNationaliteit o) {
        if (o.getNationaliteit() != null && getNationaliteit() != null && getNationaliteit().getCode() != null) {
            return nationaliteit.getCode().compareTo(o.getNationaliteit().getCode());
        }
        return -1;
    }
}
