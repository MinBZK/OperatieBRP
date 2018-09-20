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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroepBasis;

/**
 * Gegevens over het geslacht van een persoon.
 *
 * Verplicht aanwezig bij persoon
 *
 * Beide vormen van historie: geslacht(saanduiding) kan in de werkelijkheid veranderen, dus materieel bovenop de formele
 * historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonGeslachtsaanduidingGroepModel implements PersoonGeslachtsaanduidingGroepBasis {

    @Embedded
    @AttributeOverride(name = GeslachtsaanduidingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Geslachtsaand"))
    @JsonProperty
    private GeslachtsaanduidingAttribuut geslachtsaanduiding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonGeslachtsaanduidingGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param geslachtsaanduiding geslachtsaanduiding van Geslachtsaanduiding.
     */
    public AbstractPersoonGeslachtsaanduidingGroepModel(final GeslachtsaanduidingAttribuut geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsaanduidingGroep te kopieren groep.
     */
    public AbstractPersoonGeslachtsaanduidingGroepModel(final PersoonGeslachtsaanduidingGroep persoonGeslachtsaanduidingGroep) {
        this.geslachtsaanduiding = persoonGeslachtsaanduidingGroep.getGeslachtsaanduiding();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsaanduidingAttribuut getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (geslachtsaanduiding != null) {
            attributen.add(geslachtsaanduiding);
        }
        return attributen;
    }

}
