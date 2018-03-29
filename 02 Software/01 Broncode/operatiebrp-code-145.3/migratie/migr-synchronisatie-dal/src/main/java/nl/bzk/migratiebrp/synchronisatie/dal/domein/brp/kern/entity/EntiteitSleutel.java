/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Definieer een sleutel voor het bepalen of 1 object een afwijkende versie is van een ander object. Werkt door een
 * aantal identificerende velden aan de sleutel toe te voegen voor beide objecten, en dan de sleutels te vergelijken.
 * <p/>
 * Sleutels kunnen als veld ook andere sleutels bevatten, om een hierarchie van objecten te kunnen vergelijken.
 */
public final class EntiteitSleutel implements Sleutel {
    /**
     * Veldnaam voor sleuteldeel voor datum-tijd registratie.
     */
    public static final String SLEUTELDEEL_DATUM_TIJD_REGISTRATIE = "tsreg";
    /**
     * Veldnaam voor sleuteldeel voor datum aanvang geldigheid.
     */
    public static final String SLEUTELDEEL_DATUM_AANVANG_GELDIGHEID = "dataanvgel";
    /**
     * Veldnaam voor sleuteldeel voor categorie voor de groep Bijhouding.
     */
    public static final String SLEUTELDEEL_BIJHOUDING_CATEGORIE = "categorie";

    /**
     * Veldnaam voor sleuteldeel voor nationaliteit.
     */
    public static final String SLEUTELDEEL_NATIONALITEIT = "nation";

    private final Map<String, Object> delen = new HashMap<>();
    private final Class<?> entiteit;
    private final String veld;
    private final EntiteitSleutel eigenaarSleutel;
    private Long id;

    /**
     * Maakt een sleutel aan de hand van de opgegeven entiteit en veld.
     * @param entiteit entiteit waar deze sleutel voor geldt
     * @param veld veld waar deze sleutel voor geldt
     */
    public EntiteitSleutel(final Class<?> entiteit, final String veld) {
        this.entiteit = entiteit;
        this.veld = veld;
        eigenaarSleutel = null;
    }

    /**
     * Maakt een sleutel aan de hand van de opgegeven entiteit, veld en eigenaar sleutel.
     * @param entiteit entiteit waar deze sleutel voor geldt
     * @param veld veld waar deze sleutel voor geldt
     * @param eigenaarSleutel de sleutel van de eigenaar van de entiteit waar deze sleutel voor geldt
     */
    public EntiteitSleutel(final Class<?> entiteit, final String veld, final EntiteitSleutel eigenaarSleutel) {
        this.eigenaarSleutel = eigenaarSleutel;
        this.entiteit = entiteit;
        this.veld = veld;
    }

    /**
     * Maakt een Sleutel op basis van een bestaande sleutel.
     * @param sleutel sleutel waarop de nieuwe sleutel gebaseerd moet worden
     */
    public EntiteitSleutel(final EntiteitSleutel sleutel) {
        entiteit = sleutel.entiteit;
        veld = sleutel.veld;
        delen.putAll(sleutel.delen);
        eigenaarSleutel = sleutel.eigenaarSleutel;
    }

    /**
     * Maakt een Sleutel op basis van een bestaande sleutel, waarbij Veld wordt aangepast.
     * @param sleutel sleutel waarop de nieuwe sleutel gebaseerd moet worden
     * @param veld veld waar deze sleutel voor geldt
     */
    public EntiteitSleutel(final EntiteitSleutel sleutel, final String veld) {
        entiteit = sleutel.entiteit;
        this.veld = veld;
        delen.putAll(sleutel.delen);
        eigenaarSleutel = sleutel.eigenaarSleutel;
    }

    private static Map<String, Object> mapSubset(final Map<String, Object> map, final Set<String> sleutels) {
        final Map<String, Object> mapSubset = new HashMap<>();
        for (final String deelSleutel : sleutels) {
            mapSubset.put(deelSleutel, map.get(deelSleutel));
        }
        return mapSubset;
    }

    @Override
    public void addSleuteldeel(final String naam, final Object sleuteldeel) {
        delen.put(naam, sleuteldeel);
    }

    /**
     * Verwijder een veld/deel toe van de sleutel.
     * @param naam de naam van het sleuteldeel/veld
     */
    public void removeSleuteldeel(final String naam) {
        if (delen.containsKey(naam)) {
            delen.remove(naam);
        }
    }

    /**
     * Geef de waarde van entiteit van EntiteitSleutel.
     * @return de waarde van entiteit van EntiteitSleutel
     */
    public Class<?> getEntiteit() {
        return entiteit;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel#getVeld()
     */
    @Override
    public String getVeld() {
        return veld;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel#setId(java.lang.Long)
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van eigenaar sleutel van EntiteitSleutel.
     * @return de waarde van eigenaar sleutel van EntiteitSleutel
     */
    public EntiteitSleutel getEigenaarSleutel() {
        return eigenaarSleutel;
    }

    /*
     * (non-Javadoc)
     * 
     * l * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel#getDelen()
     */
    @Override
    public Map<String, Object> getDelen() {
        return Collections.unmodifiableMap(delen);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EntiteitSleutel)) {
            return false;
        }
        final EntiteitSleutel castOther = (EntiteitSleutel) other;
        return new EqualsBuilder().append(entiteit, castOther.entiteit)
                .append(veld, castOther.veld)
                .append(delen, castOther.delen)
                .append(eigenaarSleutel, castOther.eigenaarSleutel)
                .isEquals();
    }

    /**
     * Vergelijkt alleen de entiteit en veld attributen van sleutels met elkaar.
     * @param sleutel sleutel waarmee vergeleken wordt.
     * @return true als de entiteit en veld gelijk zijn aan de opgegeven sleutel
     */
    public boolean equalsIgnoreOntbrekendeDelen(final EntiteitSleutel sleutel) {
        final Set<String> gezamelijkeDelenSleutels = new HashSet<>(delen.keySet());
        gezamelijkeDelenSleutels.retainAll(sleutel.delen.keySet());

        final Map<String, Object> mijnDelen = mapSubset(delen, gezamelijkeDelenSleutels);
        final Map<String, Object> sleutelDelen = mapSubset(sleutel.delen, gezamelijkeDelenSleutels);
        return new EqualsBuilder().append(entiteit, sleutel.entiteit)
                .append(veld, sleutel.veld)
                .append(mijnDelen, sleutelDelen)
                .append(eigenaarSleutel, sleutel.eigenaarSleutel)
                .isEquals();
    }

    /**
     * Vergelijkt alleen de entiteit en veld attributen van sleutels met elkaar.
     * @param sleutel sleutel waarmee vergeleken wordt.
     * @return true als de entiteit en veld gelijk zijn aan de opgegeven sleutel
     */
    public boolean equalsIgnoreDelen(final EntiteitSleutel sleutel) {
        boolean equal = new EqualsBuilder().append(entiteit, sleutel.entiteit).append(veld, sleutel.veld).build();

        if (eigenaarSleutel != null && sleutel.eigenaarSleutel != null) {
            equal &= eigenaarSleutel.equalsIgnoreDelen(sleutel.eigenaarSleutel);
        } else if (eigenaarSleutel != null || sleutel.eigenaarSleutel != null) {
            // een heeft wel een eigenaar, ander niet
            equal = false;
        }

        return equal;
    }

    /**
     * Vult de sleutel aan voor reisdocument.
     * @param technischId technisch ID voor het reisdocument
     * @param reisdocument een {@link PersoonReisdocument}
     * @param reisdocumentHistorie een {@link PersoonReisdocumentHistorie}
     */
    public void vulSleutelAanVoorReisdocument(
            final Long technischId,
            final PersoonReisdocument reisdocument,
            final PersoonReisdocumentHistorie reisdocumentHistorie) {
        addSleuteldeel(PersoonReisdocument.TECHNISCH_ID, technischId);
        addSleuteldeel("srt", reisdocument.getSoortNederlandsReisdocument().getCode());
        addSleuteldeel("nr", reisdocumentHistorie.getNummer());
        addSleuteldeel("autvanafgifte", reisdocumentHistorie.getAutoriteitVanAfgifte());
        addSleuteldeel("dateindedoc", reisdocumentHistorie.getDatumEindeDocument());
        addSleuteldeel("datuitgifte", reisdocumentHistorie.getDatumUitgifte());
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(entiteit).append(veld).append(delen).append(eigenaarSleutel).toHashCode();
    }

    @Override
    public String toString() {
        final ToStringBuilder sb =
                new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("Entiteit", entiteit)
                        .append("Veld", veld)
                        .append("EigenaarSleutel", eigenaarSleutel);
        for (final Map.Entry<String, Object> entry : delen.entrySet()) {
            sb.append(entry.getKey(), entry.getValue());
        }
        return sb.toString();
    }

    /**
     * @return Een korte omschrijving van de entiteitsleutel bestaande uit de entiteit naam plus de veldnaam.
     */
    @Override
    public String toShortString() {
        return String.format("%1$s.%2$s", getEntiteit().getSimpleName(), getVeld());
    }
}
