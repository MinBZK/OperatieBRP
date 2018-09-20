/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsaanduidingGroepBasis;


/**
 * Gegevens over het geslacht van een Persoon.
 *
 * Verplicht aanwezig bij persoon
 *
 * Beide vormen van historie: geslacht(saanduiding) kan in de werkelijkheid veranderen, dus materieel bovenop de formele
 * historie.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonGeslachtsaanduidingGroepModel implements PersoonGeslachtsaanduidingGroepBasis {

    @Enumerated
    @Column(name = "Geslachtsaand")
    @JsonProperty
    private Geslachtsaanduiding geslachtsaanduiding;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonGeslachtsaanduidingGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param geslachtsaanduiding geslachtsaanduiding van Geslachtsaanduiding.
     */
    public AbstractPersoonGeslachtsaanduidingGroepModel(final Geslachtsaanduiding geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsaanduidingGroep te kopieren groep.
     */
    public AbstractPersoonGeslachtsaanduidingGroepModel(
            final PersoonGeslachtsaanduidingGroep persoonGeslachtsaanduidingGroep)
    {
        this.geslachtsaanduiding = persoonGeslachtsaanduidingGroep.getGeslachtsaanduiding();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

}
