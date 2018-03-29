/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;

/**
 * Deze class helpt bij het maken van een BrpAutorisatie. De verplichte argumenten moeten in de contructor worden
 * meegegeven en optionele argumenten kunnen via method-chaining worden toegevoegd.
 */
public final class BrpLeveringsautorisatieBuilder {

    private BrpPartijCode partij;
    private BrpBoolean indicatieVerstrekkingsbeperkingMogelijk;
    private List<BrpLeveringsautorisatie> leveringsautorisatieLijst;

    /**
     * Maak lege builder.
     */
    public BrpLeveringsautorisatieBuilder() {
        // lege builder.
    }

    /**
     * Maak builder gevuld met stapels uit autorisatie.
     * @param autorisatie autorisatie
     */
    public BrpLeveringsautorisatieBuilder(final BrpAutorisatie autorisatie) {
        partij = autorisatie.getPartij();
        indicatieVerstrekkingsbeperkingMogelijk = autorisatie.getIndicatieVerstrekkingsbeperkingMogelijk();
        leveringsautorisatieLijst = autorisatie.getLeveringsAutorisatieLijst();
    }

    /**
     * @return een nieuwe BrpAutorisatie object o.b.v. de parameters van deze builder
     */
    public BrpAutorisatie build() {
        return new BrpAutorisatie(partij, indicatieVerstrekkingsbeperkingMogelijk, leveringsautorisatieLijst);
    }

    /**
     * Voegt de partij toe aan deze autorisatie builder.
     * @param param de partij, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpLeveringsautorisatieBuilder partij(final BrpPartijCode param) {
        partij = param;
        return this;
    }

    /**
     * Voegt de indicatie verstrekkingsbeperking mogelijk toe aan deze autorisatie builder.
     * @param param de indicatie verstrekkingsbeperking mogelijk, mag null zijn
     * @return het BrpPersoonslijstBuilder object
     */
    public BrpLeveringsautorisatieBuilder partij(final BrpBoolean param) {
        indicatieVerstrekkingsbeperkingMogelijk = param;
        return this;
    }

    /**
     * Voegt de leveringautorisatieLijst toe aan deze autorisatie builder.
     * @param param de leveringautorisatieLijst, mag null zijn
     * @return het builder object
     */
    public BrpLeveringsautorisatieBuilder leveringsautorisatie(final List<BrpLeveringsautorisatie> param) {
        leveringsautorisatieLijst = param;
        return this;
    }
}
