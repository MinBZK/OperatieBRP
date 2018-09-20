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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroep;
import nl.bzk.brp.model.logisch.kern.basis.OuderOuderlijkGezagGroepBasis;


/**
 * Gegevens met betrekking tot het ouderlijk gezag.
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
public abstract class AbstractOuderOuderlijkGezagGroepModel implements OuderOuderlijkGezagGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndOuderHeeftGezag"))
    @JsonProperty
    private JaNee indicatieOuderHeeftGezag;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractOuderOuderlijkGezagGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuderHeeftGezag indicatieOuderHeeftGezag van Ouderlijk gezag.
     */
    public AbstractOuderOuderlijkGezagGroepModel(final JaNee indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouderOuderlijkGezagGroep te kopieren groep.
     */
    public AbstractOuderOuderlijkGezagGroepModel(final OuderOuderlijkGezagGroep ouderOuderlijkGezagGroep) {
        this.indicatieOuderHeeftGezag = ouderOuderlijkGezagGroep.getIndicatieOuderHeeftGezag();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

}
