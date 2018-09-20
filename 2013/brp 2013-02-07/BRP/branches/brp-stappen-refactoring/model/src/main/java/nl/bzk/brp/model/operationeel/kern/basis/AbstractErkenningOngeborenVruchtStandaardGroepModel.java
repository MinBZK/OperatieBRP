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
import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVruchtStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.ErkenningOngeborenVruchtStandaardGroepBasis;


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
public abstract class AbstractErkenningOngeborenVruchtStandaardGroepModel implements
        ErkenningOngeborenVruchtStandaardGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatErkenningOngeborenVrucht"))
    @JsonProperty
    private Datum datumErkenningOngeborenVrucht;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractErkenningOngeborenVruchtStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumErkenningOngeborenVrucht datumErkenningOngeborenVrucht van Standaard.
     */
    public AbstractErkenningOngeborenVruchtStandaardGroepModel(final Datum datumErkenningOngeborenVrucht) {
        this.datumErkenningOngeborenVrucht = datumErkenningOngeborenVrucht;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param erkenningOngeborenVruchtStandaardGroep te kopieren groep.
     */
    public AbstractErkenningOngeborenVruchtStandaardGroepModel(
            final ErkenningOngeborenVruchtStandaardGroep erkenningOngeborenVruchtStandaardGroep)
    {
        this.datumErkenningOngeborenVrucht = erkenningOngeborenVruchtStandaardGroep.getDatumErkenningOngeborenVrucht();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumErkenningOngeborenVrucht() {
        return datumErkenningOngeborenVrucht;
    }

}
