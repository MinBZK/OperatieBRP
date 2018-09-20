/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/** Persoon \ Voornaam. */
public class PersoonVoornaam extends AbstractIdentificerendeGroep implements Comparable<PersoonVoornaam> {

    private static final int HASHCODE_NON_ZERO_ODD_NUMBER            = 3;
    private static final int HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER = 19;

    private Persoon persoon;

    private Integer volgnummer;

    private String naam;

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

    @Override
    public int compareTo(final PersoonVoornaam o) {
        return getVolgnummer().compareTo(o.getVolgnummer());
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof PersoonVoornaam)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                PersoonVoornaam pv = (PersoonVoornaam) obj;
                resultaat = new EqualsBuilder().append(persoon, pv.persoon).append(volgnummer, pv.volgnummer)
                                               .append(naam, pv.naam).isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_NON_ZERO_ODD_NUMBER, HASHCODE_MULTIPLIER_NON_ZERO_ODD_NUMBER)
            .append(persoon).append(volgnummer).append(naam).toHashCode();
    }

}
