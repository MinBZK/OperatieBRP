/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.logisch.kern.OuderOuderschapGroep;
import nl.bzk.brp.model.logisch.kern.basis.OuderOuderschapGroepBasis;
import org.hibernate.annotations.Type;


/**
 * Vorm van historie: beiden. Reden: alhoewel zeldzaam, is het denkbaar dat een ouder eerst betrokken is in een
 * familierechtelijke betrekking met een kind, daarna ouder 'af' wordt (bijvoorbeeld door adoptie), en later, door
 * herroeping van de adoptie, weer 'ouder aan'. Volgens de HUP 3.7 dient dan als datum ingang van de familierechtelijke
 * betrekking de datum te worden genomen waarop de herroeping definitief is. Anders gezegd: er is een TWEEDE
 * betrokkenheid van dezelfde ouder in dezelfde fam.recht.betrekking. Dit is opgelost door de groep 'beiden' te maken,
 * EN de attributen datum aanvang geldigheid/einde geldigheid uit het LGM te verwijderen.
 * RvdP 13 feb 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractOuderOuderschapGroepModel implements OuderOuderschapGroepBasis {

    @Type(type = "Ja")
    @Column(name = "IndOuder")
    @JsonProperty
    private Ja indicatieOuder;

    @Transient
    @JsonProperty
    private Ja indicatieOuderUitWieKindIsVoortgekomen;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractOuderOuderschapGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuder indicatieOuder van Ouderschap.
     * @param indicatieOuderUitWieKindIsVoortgekomen indicatieOuderUitWieKindIsVoortgekomen van Ouderschap.
     */
    public AbstractOuderOuderschapGroepModel(final Ja indicatieOuder, final Ja indicatieOuderUitWieKindIsVoortgekomen) {
        this.indicatieOuder = indicatieOuder;
        this.indicatieOuderUitWieKindIsVoortgekomen = indicatieOuderUitWieKindIsVoortgekomen;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouderOuderschapGroep te kopieren groep.
     */
    public AbstractOuderOuderschapGroepModel(final OuderOuderschapGroep ouderOuderschapGroep) {
        this.indicatieOuder = ouderOuderschapGroep.getIndicatieOuder();
        this.indicatieOuderUitWieKindIsVoortgekomen = ouderOuderschapGroep.getIndicatieOuderUitWieKindIsVoortgekomen();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ja getIndicatieOuder() {
        return indicatieOuder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ja getIndicatieOuderUitWieKindIsVoortgekomen() {
        return indicatieOuderUitWieKindIsVoortgekomen;
    }

}
