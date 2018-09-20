/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOnderzoek;

/**
 * Het Onderzoek object gemodelleerd zoals dit in de Viewer aan de BRP kant wordt weergegeven.
 *
 * @author stefanr
 *
 */
public class GgoBrpOnderzoek implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final PersoonOnderzoek persoonOnderzoek;

    private Map<String, String> inhoud;
    private final Set<String> betrokkenVelden = new LinkedHashSet<>();

    /**
     * Constructor.
     *
     * @param persoonOnderzoek
     *            Het oorspronkelijke onderzoek uit het Entity-model, gebruikt als identificatie voor deze instantie.
     */
    public GgoBrpOnderzoek(final PersoonOnderzoek persoonOnderzoek) {
        this.persoonOnderzoek = persoonOnderzoek;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return the inhoud
     */
    public final Map<String, String> getInhoud() {
        return inhoud;
    }

    /**
     * Sets the inhoud.
     *
     * @param inhoud
     *            the inhoud to set
     */
    public final void setInhoud(final Map<String, String> inhoud) {
        this.inhoud = inhoud;
    }

    /**
     * Geef de waarde van betrokken velden.
     *
     * @return the betrokkenVelden
     */
    public final Set<String> getBetrokkenVelden() {
        return betrokkenVelden;
    }

    /**
     * @param betrokkenVeld
     *            the betrokkenVeld to add
     */
    public final void addBetrokkenVeld(final String betrokkenVeld) {
        betrokkenVelden.add(betrokkenVeld);
    }

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof GgoBrpOnderzoek && persoonOnderzoek.equals(((GgoBrpOnderzoek) obj).persoonOnderzoek);
    }

    @Override
    public final int hashCode() {
        return persoonOnderzoek.hashCode();
    }
}
