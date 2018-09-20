/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;

/**
 * Logisch gegevens model object voor Betrokkenheid.
 */
public class Betrokkenheid {

    private Relatie relatie;
    private SoortBetrokkenheid soortBetrokkenheid;
    private Persoon betrokkene;
    private Boolean indOuder;
    private Integer datumAanvangOuderschap;
    private Boolean indOuderHeeftGezag;

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(final Relatie relatie) {
        this.relatie = relatie;
    }

    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return soortBetrokkenheid;
    }

    public void setSoortBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid) {
        this.soortBetrokkenheid = soortBetrokkenheid;
    }

    public Persoon getBetrokkene() {
        return betrokkene;
    }

    public void setBetrokkene(final Persoon betrokkene) {
        this.betrokkene = betrokkene;
    }

    public Boolean isIndOuder() {
        return indOuder;
    }

    public void setIndOuder(final Boolean indOuder) {
        this.indOuder = indOuder;
    }

    public Integer getDatumAanvangOuderschap() {
        return datumAanvangOuderschap;
    }

    public void setDatumAanvangOuderschap(final Integer datumAanvangOuderschap) {
        this.datumAanvangOuderschap = datumAanvangOuderschap;
    }

    public Boolean isIndOuderHeeftGezag() {
        return indOuderHeeftGezag;
    }

    public void setIndOuderHeeftGezag(final Boolean indOuderHeeftGezag) {
        this.indOuderHeeftGezag = indOuderHeeftGezag;
    }
}
