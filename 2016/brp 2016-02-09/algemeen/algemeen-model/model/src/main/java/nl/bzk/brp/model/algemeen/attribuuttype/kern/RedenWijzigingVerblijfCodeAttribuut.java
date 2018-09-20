/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Reden wijziging verblijf code.
 */
@Embeddable
public class RedenWijzigingVerblijfCodeAttribuut extends AbstractRedenWijzigingVerblijfCodeAttribuut {

    /**
     * Constante voor reden wijzinging adres.
     */
    public static final String                              PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING = "P";
    /**
     * Constante voor reden wijzinging adres.
     */
    public static final RedenWijzigingVerblijfCodeAttribuut PERSOON_REDEN_WIJZIGING_ADRES_CODE        = new RedenWijzigingVerblijfCodeAttribuut(
        PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING);

    /**
     * Constante voor reden wijziging adres.
     */
    public static final String                              PERSOON_REDEN_WIJZIGING_ADRES_AMBTSHALVE_CODE_STRING = "A";
    /**
     * Constante voor reden wijziging adres.
     */
    public static final RedenWijzigingVerblijfCodeAttribuut PERSOON_REDEN_WIJZIGING_ADRES_CODE_AMBTSHALVE_CODE   = new RedenWijzigingVerblijfCodeAttribuut(
        PERSOON_REDEN_WIJZIGING_ADRES_AMBTSHALVE_CODE_STRING);

    /**
     * Lege private constructor voor RedenWijzigingVerblijfCodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private RedenWijzigingVerblijfCodeAttribuut() {
        super();
    }

    /**
     * Constructor voor RedenWijzigingVerblijfCodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public RedenWijzigingVerblijfCodeAttribuut(final String waarde) {
        super(waarde);
    }

}
