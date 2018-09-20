/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De verstrekkingsbeperking zoals die voor een in de BRP gekende partij of een in een gemeentelijke verordening
 * benoemde derde voor de persoon van toepassing is.
 *
 * De formele historie van de identiteit groep is geen 'echte' formele historie maar een 'bestaandsperiode'-patroon op
 * formele historie; een verstrekkingsbeperking kan niet stoppen en weer herleven, het is dan een nieuwe
 * verstrekkingsbeperking die niets met de vorige te maken heeft. Feitelijk was er geen his_ tabel nodig en hadden de
 * verantwoordingsvelden (inclusief tijdstippen) direct op de tabel kunnen komen.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonVerstrekkingsbeperkingBasis extends BrpObject {

    /**
     * Retourneert Persoon van Persoon \ Verstrekkingsbeperking.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Partij van Persoon \ Verstrekkingsbeperking.
     *
     * @return Partij.
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Omschrijving derde van Persoon \ Verstrekkingsbeperking.
     *
     * @return Omschrijving derde.
     */
    OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde();

    /**
     * Retourneert Gemeente verordening van Persoon \ Verstrekkingsbeperking.
     *
     * @return Gemeente verordening.
     */
    PartijAttribuut getGemeenteVerordening();

}
