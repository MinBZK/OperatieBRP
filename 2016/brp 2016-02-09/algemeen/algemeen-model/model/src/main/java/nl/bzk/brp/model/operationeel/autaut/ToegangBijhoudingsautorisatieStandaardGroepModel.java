/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.logisch.autaut.ToegangBijhoudingsautorisatieStandaardGroep;

/**
 *
 *
 */
@Embeddable
public class ToegangBijhoudingsautorisatieStandaardGroepModel extends AbstractToegangBijhoudingsautorisatieStandaardGroepModel implements
        ToegangBijhoudingsautorisatieStandaardGroep
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected ToegangBijhoudingsautorisatieStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumIngang datumIngang van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van Standaard.
     */
    public ToegangBijhoudingsautorisatieStandaardGroepModel(
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd)
    {
        super(datumIngang, datumEinde, indicatieGeblokkeerd);
    }

}
