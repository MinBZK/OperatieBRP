/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Sleutel voor IST-stapels.
 */
public final class IstSleutel implements Sleutel {

    private final String categorie;
    private final int stapelnummer;
    private final boolean isBestaandeStapel;
    private final String veld;
    private final Map<String, Object> delen = new HashMap<>();
    private Integer voorkomennummer;

    /**
     * Constructor voor een IST-sleutel op basis van een stapel.
     * 
     * @param stapel
     *            stapel waarvoor een sleutel gemaakt moet worden
     * @param isBestaandeStapel
     *            true als het een bestaande stapel betreft
     */
    public IstSleutel(final Stapel stapel, final boolean isBestaandeStapel) {
        this.categorie = stapel.getCategorie();
        this.stapelnummer = stapel.getVolgnummer();
        this.isBestaandeStapel = isBestaandeStapel;
        this.voorkomennummer = null;
        this.veld = null;
    }

    /**
     * Constructor voor een IST-sleutel op basis van een stapel met als toevoeging welk veld van waarde is veranderd.
     * 
     * @param stapel
     *            stapel waarvoor een sleutel gemaakt moet worden
     * @param veld
     *            het veld waar de wijziging op is gevonden
     * @param isBestaandeStapel
     *            true als het een bestaande stapel betreft
     */
    public IstSleutel(final Stapel stapel, final String veld, final boolean isBestaandeStapel) {
        this.categorie = stapel.getCategorie();
        this.stapelnummer = stapel.getVolgnummer();
        this.isBestaandeStapel = isBestaandeStapel;
        this.voorkomennummer = null;
        this.veld = veld;
    }

    /**
     * Constructor voor een IST-sleutel op basis van een stapel voorkomen.
     *
     * @param voorkomen
     *            voorkomen waarvoor een sleutel gemaakt moet worden
     * @param veld
     *            het veld waar de wijziging op is gevonden
     * @param isBestaandVoorkomen
     *            true als het een bestaand voorkomen betreft
     */
    public IstSleutel(final StapelVoorkomen voorkomen, final String veld, final boolean isBestaandVoorkomen) {
        this(voorkomen.getStapel(), veld, isBestaandVoorkomen);
        this.voorkomennummer = voorkomen.getVolgnummer();
    }

    @Override
    public void addSleuteldeel(final String naam, final Object sleuteldeel) {
        delen.put(naam, sleuteldeel);
    }

    @Override
    public Map<String, Object> getDelen() {
        return delen;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(final Long id) {
        throw new IllegalStateException("ID is geen veld voor een IST-sleutel");
    }

    @Override
    public String getVeld() {
        return veld;
    }

    @Override
    public String toShortString() {
        return toString();
    }

    /**
     * @return De categorie.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return Het voorkomennummer.
     */
    public Integer getVoorkomennummer() {
        return voorkomennummer;
    }

    /**
     * @return Het stapelnummer.
     */
    public int getStapelnummer() {
        return stapelnummer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("categorie", categorie)
                                                                          .append("stapel", stapelnummer)
                                                                          .append("voorkomen", voorkomennummer)
                                                                          .append("veld", veld)
                                                                          .append("isBestaandeStapel", isBestaandeStapel)
                                                                          .append("delen", delen)
                                                                          .toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IstSleutel)) {
            return false;
        }
        final IstSleutel castOther = (IstSleutel) other;
        return new EqualsBuilder().append(categorie, castOther.categorie)
                                  .append(stapelnummer, castOther.stapelnummer)
                                  .append(voorkomennummer, castOther.voorkomennummer)
                                  .append(veld, castOther.veld)
                                  .append(delen, castOther.delen)
                                  .append(isBestaandeStapel, castOther.isBestaandeStapel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(categorie)
                                    .append(stapelnummer)
                                    .append(voorkomennummer)
                                    .append(veld)
                                    .append(isBestaandeStapel)
                                    .append(delen)
                                    .toHashCode();
    }

    /**
     * Geeft aan of deze sleutel voor een stapelvoorkomen is gemaakt of niet.
     * 
     * @return true als het {@link #voorkomennummer} is ingevuld en het dus een sleutel is voor een stapelvoorkomen.
     */
    public boolean isVoorkomenSleutel() {
        return getVoorkomennummer() != null;
    }
}
