/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonIndicatieStandaardGroepBasis;
import org.hibernate.annotations.Type;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonIndicatieStandaardGroepModel implements PersoonIndicatieStandaardGroepBasis {

    @Type(type = "Ja")
    @Column(name = "Waarde")
    @JsonProperty
    private Ja waarde;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonIndicatieStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param waarde waarde van Standaard.
     */
    public AbstractPersoonIndicatieStandaardGroepModel(final Ja waarde) {
        this.waarde = waarde;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIndicatieStandaardGroep te kopieren groep.
     */
    public AbstractPersoonIndicatieStandaardGroepModel(
            final PersoonIndicatieStandaardGroep persoonIndicatieStandaardGroep)
    {
        this.waarde = persoonIndicatieStandaardGroep.getWaarde();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ja getWaarde() {
        return waarde;
    }

}
