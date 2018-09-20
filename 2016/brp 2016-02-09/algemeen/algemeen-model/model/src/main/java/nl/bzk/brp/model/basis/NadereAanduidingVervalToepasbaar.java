/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;

/**
 * Inteface voor historie classes die nadere aanduiding verval kunnen bevatten.
 */
public interface NadereAanduidingVervalToepasbaar {

    /**
     * Geeft nadere aanduiding vevrval.
     *
     * @return De nadere aanduiding vevrval.
     */
    NadereAanduidingVervalAttribuut getNadereAanduidingVerval();

    /**
     * Set de nadere aanduiding vevrval.
     *
     * @param nadereAanduidingVerval De nadere aanduiding vevrval.
     */
    void setNadereAanduidingVerval(NadereAanduidingVervalAttribuut nadereAanduidingVerval);

}
