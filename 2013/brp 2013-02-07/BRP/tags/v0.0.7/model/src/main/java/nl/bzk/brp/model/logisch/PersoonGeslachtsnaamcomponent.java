/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/** Component van de Geslachtsnaam van een Persoon. */
public class PersoonGeslachtsnaamcomponent implements Comparable<PersoonGeslachtsnaamcomponent> {

    private static final int HASHCODE_NON_ZERO_ODD_NUMBER            = 5;
    private static final int HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER = 17;

    private Persoon persoon;

    private Integer volgnummer;

    private String naam;

    private String voorvoegsel;

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    @Override
    public int compareTo(final PersoonGeslachtsnaamcomponent o) {
        return this.getVolgnummer() - o.getVolgnummer();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof PersoonGeslachtsnaamcomponent)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                PersoonGeslachtsnaamcomponent pg = (PersoonGeslachtsnaamcomponent) obj;
                resultaat = new EqualsBuilder().append(persoon, pg.persoon).append(volgnummer, pg.volgnummer)
                                               .append(naam, pg.naam).append(voorvoegsel, pg.voorvoegsel).isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_NON_ZERO_ODD_NUMBER, HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER)
            .append(persoon).append(volgnummer).append(naam).append(voorvoegsel).toHashCode();
    }

}
