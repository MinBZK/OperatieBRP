/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.OnderzoekAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.OnderzoekAfgeleidAdministratiefGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractOnderzoekAfgeleidAdministratiefGroepModel implements OnderzoekAfgeleidAdministratiefGroepBasis {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractOnderzoekAfgeleidAdministratiefGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param administratieveHandeling administratieveHandeling van Afgeleid administratief.
     */
    public AbstractOnderzoekAfgeleidAdministratiefGroepModel(final AdministratieveHandelingModel administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param onderzoekAfgeleidAdministratiefGroep te kopieren groep.
     */
    public AbstractOnderzoekAfgeleidAdministratiefGroepModel(final OnderzoekAfgeleidAdministratiefGroep onderzoekAfgeleidAdministratiefGroep) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

}
