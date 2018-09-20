/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RolAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BerichtParametersGroepBasis extends Groep {

    /**
     * Retourneert Verwerkingswijze van Parameters.
     *
     * @return Verwerkingswijze.
     */
    VerwerkingswijzeAttribuut getVerwerkingswijze();

    /**
     * Retourneert Bezien vanuit Persoon van Parameters.
     *
     * @return Bezien vanuit Persoon.
     */
    SleutelwaardetekstAttribuut getBezienVanuitPersoon();

    /**
     * Retourneert Rol van Parameters.
     *
     * @return Rol.
     */
    RolAttribuut getRol();

    /**
     * Retourneert Soort synchronisatie van Parameters.
     *
     * @return Soort synchronisatie.
     */
    SoortSynchronisatieAttribuut getSoortSynchronisatie();

    /**
     * Retourneert Peilmoment materieel selectie van Parameters.
     *
     * @return Peilmoment materieel selectie.
     */
    DatumAttribuut getPeilmomentMaterieelSelectie();

    /**
     * Retourneert Peilmoment materieel resultaat van Parameters.
     *
     * @return Peilmoment materieel resultaat.
     */
    DatumAttribuut getPeilmomentMaterieelResultaat();

    /**
     * Retourneert Peilmoment formeel resultaat van Parameters.
     *
     * @return Peilmoment formeel resultaat.
     */
    DatumTijdAttribuut getPeilmomentFormeelResultaat();

    /**
     * Retourneert Stamgegeven van Parameters.
     *
     * @return Stamgegeven.
     */
    StamgegevenAttribuut getStamgegeven();

}
