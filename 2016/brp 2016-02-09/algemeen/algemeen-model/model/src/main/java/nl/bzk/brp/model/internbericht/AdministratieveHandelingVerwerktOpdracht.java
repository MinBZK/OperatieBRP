/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.internbericht;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;


/**
 * Opdracht voor het publiceren van een verwerkte administratieve handeling.
 */
public final class AdministratieveHandelingVerwerktOpdracht {

    @JsonProperty
    private Long administratieveHandelingId;

    @JsonProperty
    private PartijCodeAttribuut partijCode;

    @JsonProperty
    private List<Integer> bijgehoudenPersoonIds;

    /**
     * private constructor aanwezig ivm Json deserialisatie.
     */
    private AdministratieveHandelingVerwerktOpdracht() {
    }

    /**
     * Constructor met alle waarden voor een opdracht.
     *
     * @param administratieveHandelingId de administratieve handeling id
     * @param partijCode                 partijcode
     * @param bijgehoudenPersoonIds      bijgehouden personen
     */
    public AdministratieveHandelingVerwerktOpdracht(final Long administratieveHandelingId,
        final PartijCodeAttribuut partijCode, final List<Integer> bijgehoudenPersoonIds)
    {
        this.administratieveHandelingId = administratieveHandelingId;
        this.partijCode = partijCode;
        this.bijgehoudenPersoonIds = bijgehoudenPersoonIds;
    }

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    public PartijCodeAttribuut getPartijCode() {
        return partijCode;
    }

    public List<Integer> getBijgehoudenPersoonIds() {
        return bijgehoudenPersoonIds;
    }
}
