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
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoekAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPartijOnderzoekStandaardGroepModel implements PartijOnderzoekStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = SoortPartijOnderzoekAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rol"))
    @JsonProperty
    private SoortPartijOnderzoekAttribuut rol;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPartijOnderzoekStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param rol rol van Standaard.
     */
    public AbstractPartijOnderzoekStandaardGroepModel(final SoortPartijOnderzoekAttribuut rol) {
        this.rol = rol;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param partijOnderzoekStandaardGroep te kopieren groep.
     */
    public AbstractPartijOnderzoekStandaardGroepModel(final PartijOnderzoekStandaardGroep partijOnderzoekStandaardGroep) {
        this.rol = partijOnderzoekStandaardGroep.getRol();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortPartijOnderzoekAttribuut getRol() {
        return rol;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (rol != null) {
            attributen.add(rol);
        }
        return attributen;
    }

}
