/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVoornaamStandaardGroepBasis;


/**
 * Vorm van historie: beiden. Motivatie: conform samengestelde naam kan een individuele voornaam in de loop van de tijd
 * (c.q.: in de werkelijkheid) veranderen, dus nog los van eventuele registratiefouten. Daarom dus beide vormen van
 * historie. RvdP 17 jan 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonVoornaamStandaardGroepModel implements PersoonVoornaamStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    @JsonProperty
    private Voornaam naam;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonVoornaamStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param naam naam van Standaard.
     */
    public AbstractPersoonVoornaamStandaardGroepModel(final Voornaam naam) {
        this.naam = naam;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVoornaamStandaardGroep te kopieren groep.
     */
    public AbstractPersoonVoornaamStandaardGroepModel(final PersoonVoornaamStandaardGroep persoonVoornaamStandaardGroep)
    {
        this.naam = persoonVoornaamStandaardGroep.getNaam();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voornaam getNaam() {
        return naam;
    }

}
