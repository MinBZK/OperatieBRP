/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;

/**
 * Abstractie laag voor de decorators van Delta waar in methodes zijn geplaatst die nodig zijn binnen de verschillende
 * implementaties van de decorators.
 */
public abstract class AbstractDecorator {

    /**
     * Geeft de code van de {@link Gemeente} terug.
     * @param waarde de gemeente waarvan de code terug gegeven wordt
     * @return de code van de gemeente
     */
    protected final String getGemeenteWaarde(final Gemeente waarde) {
        return waarde != null ? waarde.getCode() : null;
    }

    /**
     * Geeft de code van het {@link LandOfGebied} terug.
     * @param waarde het land/gebied waarvan de code terug gegeven wordt
     * @return de code van het land/gebied
     */
    protected final String getLandOfGebiedWaarde(final LandOfGebied waarde) {
        return waarde != null ? waarde.getCode() : null;
    }

    /**
     * Geeft de code van de {@link RedenBeeindigingRelatie} terug.
     * @param waarde de reden beeindiging relatie waarvan de code terug gegeven wordt
     * @return de code van de reden beeindiging relatie
     */
    protected final Character getRedenEindeWaarde(final RedenBeeindigingRelatie waarde) {
        return waarde != null ? waarde.getCode() : null;
    }

    /**
     * Geeft de code van de {@link Partij} terug.
     * @param partij de partij waarvan de code terug gegeven wordt
     * @return de code van de partij
     */
    protected final String getPartijWaarde(final Partij partij) {
        return partij != null ? partij.getCode() : null;
    }

    /**
     * Geeft de code van de {@link SoortDocument} terug.
     * @param soortDocument het soort document waarvan de code terug gegeven wordt
     * @return de code van het soort document
     */
    protected final String getSoortDocumentWaarde(final SoortDocument soortDocument) {
        return soortDocument != null ? soortDocument.getNaam() : null;
    }

    /**
     * Geeft de code van de {@link SoortRelatie} terug.
     * @param soortRelatie het soort document waarvan de code terug gegeven wordt
     * @return de code van het soort document
     */
    protected final String getSoortRelatieWaarde(final SoortRelatie soortRelatie) {
        return soortRelatie != null ? soortRelatie.getCode() : null;
    }
}
