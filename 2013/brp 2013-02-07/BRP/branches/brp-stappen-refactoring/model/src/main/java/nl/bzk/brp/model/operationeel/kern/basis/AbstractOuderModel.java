/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.basis.OuderBasis;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderschapGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;


/**
 * De betrokkenheid van een persoon in de rol van Ouder in een Familierechtelijke betrekking.
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
public abstract class AbstractOuderModel extends BetrokkenheidModel implements OuderBasis {

    @Embedded
    @JsonProperty
    private OuderOuderschapGroepModel     ouderschap;

    @Embedded
    @JsonProperty
    private OuderOuderlijkGezagGroepModel ouderlijkGezag;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractOuderModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     */
    public AbstractOuderModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, SoortBetrokkenheid.OUDER, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouder Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractOuderModel(final Ouder ouder, final RelatieModel relatie, final PersoonModel persoon) {
        super(ouder, relatie, persoon);
        if (ouder.getOuderschap() != null) {
            this.ouderschap = new OuderOuderschapGroepModel(ouder.getOuderschap());
        }
        if (ouder.getOuderlijkGezag() != null) {
            this.ouderlijkGezag = new OuderOuderlijkGezagGroepModel(ouder.getOuderlijkGezag());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderschapGroepModel getOuderschap() {
        return ouderschap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OuderOuderlijkGezagGroepModel getOuderlijkGezag() {
        return ouderlijkGezag;
    }

    /**
     * Zet Ouderschap van Ouder.
     *
     * @param ouderschap Ouderschap.
     */
    public void setOuderschap(final OuderOuderschapGroepModel ouderschap) {
        this.ouderschap = ouderschap;
    }

    /**
     * Zet Ouderlijk gezag van Ouder.
     *
     * @param ouderlijkGezag Ouderlijk gezag.
     */
    public void setOuderlijkGezag(final OuderOuderlijkGezagGroepModel ouderlijkGezag) {
        this.ouderlijkGezag = ouderlijkGezag;
    }

}
