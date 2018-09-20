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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.logisch.kern.NaamskeuzeOngeborenVruchtStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.NaamskeuzeOngeborenVruchtStandaardGroepBasis;


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
public abstract class AbstractNaamskeuzeOngeborenVruchtStandaardGroepModel implements
        NaamskeuzeOngeborenVruchtStandaardGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatNaamskeuzeOngeborenVrucht"))
    @JsonProperty
    private Datum datumNaamskeuzeOngeborenVrucht;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractNaamskeuzeOngeborenVruchtStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumNaamskeuzeOngeborenVrucht datumNaamskeuzeOngeborenVrucht van Standaard.
     */
    public AbstractNaamskeuzeOngeborenVruchtStandaardGroepModel(final Datum datumNaamskeuzeOngeborenVrucht) {
        this.datumNaamskeuzeOngeborenVrucht = datumNaamskeuzeOngeborenVrucht;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param naamskeuzeOngeborenVruchtStandaardGroep te kopieren groep.
     */
    public AbstractNaamskeuzeOngeborenVruchtStandaardGroepModel(
            final NaamskeuzeOngeborenVruchtStandaardGroep naamskeuzeOngeborenVruchtStandaardGroep)
    {
        this.datumNaamskeuzeOngeborenVrucht =
            naamskeuzeOngeborenVruchtStandaardGroep.getDatumNaamskeuzeOngeborenVrucht();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumNaamskeuzeOngeborenVrucht() {
        return datumNaamskeuzeOngeborenVrucht;
    }

}
