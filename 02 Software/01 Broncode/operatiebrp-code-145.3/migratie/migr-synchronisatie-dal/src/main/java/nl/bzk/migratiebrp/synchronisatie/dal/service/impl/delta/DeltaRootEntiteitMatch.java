/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class waarin een wordt opgeslagen welke oude en nieuwe root entiteiten bij elkaar horen. Ook is het mogelijk dat of
 * de oude of de nieuwe entiteit null is.
 */
public final class DeltaRootEntiteitMatch {

    private final RootEntiteit bestaandeRootEntiteit;
    private final RootEntiteit nieuweRootEntiteit;
    private final RootEntiteit eigenaarEntiteit;
    private final String eigenaarEntiteitVeldnaam;
    private final boolean isIstStapel;
    private VergelijkerResultaat vergelijkerResultaat;

    /**
     * Constructor waarbij de oude entiteit en bijbehorende nieuwe root entiteit worden mee gegeven.
     * @param bestaandeRootEntiteit de oude root entiteit, mag null zijn
     * @param nieuweRootEntiteit de nieuwe entiteit, mag null zijn
     * @param eigenaarEntiteit de eigenaar entiteit, zodat de bestaande entiteit verwijderd kan worden of de nieuwe entiteit toegevoegd kan worden
     * @param eigenaarEntiteitVeldnaam De naam van het veld van de eigenaar entiteit terug waarin de bestaande/nieuwe root entiteit gekoppeld is.
     */
    public DeltaRootEntiteitMatch(
            final RootEntiteit bestaandeRootEntiteit,
            final RootEntiteit nieuweRootEntiteit,
            final RootEntiteit eigenaarEntiteit,
            final String eigenaarEntiteitVeldnaam) {
        if (bestaandeRootEntiteit == null && nieuweRootEntiteit == null) {
            throw new IllegalStateException("De bestaande en nieuwe root eniteit mogen niet beide tegelijkertijd null zijn");
        }

        // Bestaande root entiteit kan een hibernate object zijn. Altijd proberen om de 'echte' entiteit op te slaan in
        // de match.
        this.bestaandeRootEntiteit = Entiteit.convertToPojo(bestaandeRootEntiteit);
        this.nieuweRootEntiteit = nieuweRootEntiteit;
        this.eigenaarEntiteit = eigenaarEntiteit;
        this.eigenaarEntiteitVeldnaam = eigenaarEntiteitVeldnaam;

        isIstStapel = bestaandeRootEntiteit instanceof Stapel || nieuweRootEntiteit instanceof Stapel;
    }

    /**
     * Geeft de oude root entiteit terug, of null.
     * @return de oude root entiteit of null
     */
    public RootEntiteit getBestaandeRootEntiteit() {
        return bestaandeRootEntiteit;
    }

    /**
     * Geeft de nieuwe root entiteit terug, of null.
     * @return de nieuwe root entiteit of null
     */
    public RootEntiteit getNieuweRootEntiteit() {
        return nieuweRootEntiteit;
    }

    /**
     * Geeft de eigenaar entiteit terug.
     * @return de eigenaar entiteit
     */
    public RootEntiteit getEigenaarEntiteit() {
        return eigenaarEntiteit;
    }

    /**
     * Geeft de naam van het veld van de eigenaar entiteit terug waarin de bestaande/nieuwe root entiteit gekoppeld is.
     * @return veldnaam
     */
    public String getEigenaarEntiteitVeldnaam() {
        return eigenaarEntiteitVeldnaam;
    }

    /**
     * Geeft aan of root entiteit nieuw toegevoegd moet worden aan de persoonslijst.
     * @return true als er geen bestaande, maar wel een nieuwe entiteit is
     */
    public boolean isDeltaRootEntiteitNieuw() {
        return bestaandeRootEntiteit == null && nieuweRootEntiteit != null;
    }

    /**
     * Geeft aan of root entiteit verwijderd moet worden van de persoonslijst.
     * @return true als er wel een bestaande, maar geen nieuwe entiteit is
     */
    public boolean isDeltaRootEntiteitVerwijderd() {
        return bestaandeRootEntiteit != null && nieuweRootEntiteit == null;
    }

    /**
     * Geeft aan of root entiteit (mogelijk) gewijzigd is.
     * @return true beide entiteiten niet null zijn
     */
    public boolean isDeltaRootEntiteitGewijzigd() {
        return bestaandeRootEntiteit != null && nieuweRootEntiteit != null;
    }

    /**
     * Geeft aan of deze match voor een IST-Stapel ({@link Stapel}) is.
     * @return true als het een match is voor een IST-stapel
     */
    public boolean isIstStapel() {
        return isIstStapel;
    }

    /**
     * Slaat de gevonden verschillen op bij deze match.
     * @param vergelijkerResultaat een {@link VergelijkerResultaat} met daarin de gevonden verschillen voor deze match.
     */
    public void setVergelijkerResultaat(final VergelijkerResultaat vergelijkerResultaat) {
        this.vergelijkerResultaat = vergelijkerResultaat;
    }

    /**
     * @return Geeft het {@link VergelijkerResultaat} terug met daarin de gevonden verschillen voor deze match
     */
    public VergelijkerResultaat getVergelijkerResultaat() {
        return vergelijkerResultaat;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeltaRootEntiteitMatch)) {
            return false;
        }
        final DeltaRootEntiteitMatch castOther = (DeltaRootEntiteitMatch) other;
        return new EqualsBuilder().append(bestaandeRootEntiteit, castOther.bestaandeRootEntiteit)
                .append(nieuweRootEntiteit, castOther.nieuweRootEntiteit)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bestaandeRootEntiteit).append(nieuweRootEntiteit).toHashCode();
    }

    @Override
    public String toString() {
        final RootEntiteit rootEntiteit = isDeltaRootEntiteitNieuw() ? nieuweRootEntiteit : bestaandeRootEntiteit;

        final String resultaat;
        if (rootEntiteit instanceof Betrokkenheid && eigenaarEntiteit instanceof Persoon) {
            resultaat = "Ik-Betrokkenheid (relatie)";
        } else if (rootEntiteit instanceof Betrokkenheid && eigenaarEntiteit instanceof Relatie) {
            resultaat = "gerelateerde betrokkenheid";
        } else if (rootEntiteit instanceof Persoon && eigenaarEntiteit instanceof Betrokkenheid) {
            resultaat = "gerelateerde persoon";
        } else {
            resultaat = rootEntiteit.getClass().getSimpleName();
        }

        return resultaat;
    }

    /**
     * @return Geeft aan of deze match voor de eigen persoon is.
     */
    boolean isEigenPersoon() {
        return bestaandeRootEntiteit instanceof Persoon && eigenaarEntiteit == null;
    }
}
