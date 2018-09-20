/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import javax.validation.Valid;

import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.validatie.constraint.Datum;

/** Logisch gegevens model object voor Betrokkenheid. */
public class Betrokkenheid extends AbstractIdentificerendeGroep {

    private Relatie            relatie;
    private SoortBetrokkenheid soortBetrokkenheid;
    @Valid
    private Persoon            betrokkene;

    // dit is eigenlijk alleen maar geldig indien type is Ouder
    // en hoort volgens de xml in een apartegroep (ouderschap).
    // omdat deze valt onder deze groep, heeft het geen verzendendId.
    private Boolean            indOuder;
    @Datum
    private Integer            datumAanvangOuderschap;
    private Boolean            indAdresGevend;
    private Boolean            indOuderHeeftGezag;

    public Relatie getRelatie() {
        return this.relatie;
    }

    public void setRelatie(final Relatie relatie) {
        this.relatie = relatie;
    }

    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return this.soortBetrokkenheid;
    }

    public void setSoortBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid) {
        this.soortBetrokkenheid = soortBetrokkenheid;
    }

    public Persoon getBetrokkene() {
        return this.betrokkene;
    }

    public void setBetrokkene(final Persoon betrokkene) {
        this.betrokkene = betrokkene;
    }

    public Boolean isIndOuder() {
        return this.indOuder;
    }

    public void setIndOuder(final Boolean indOuder) {
        this.indOuder = indOuder;
    }

    public Integer getDatumAanvangOuderschap() {
        return this.datumAanvangOuderschap;
    }

    public void setDatumAanvangOuderschap(final Integer datumAanvangOuderschap) {
        this.datumAanvangOuderschap = datumAanvangOuderschap;
    }

    public Boolean isIndOuderHeeftGezag() {
        return this.indOuderHeeftGezag;
    }

    public void setIndOuderHeeftGezag(final Boolean indOuderHeeftGezag) {
        this.indOuderHeeftGezag = indOuderHeeftGezag;
    }

    /**
     * Boolean waarde die aangeeft of deze betrokkenheid de groep 'ouderschap' heeft of niet.
     * @return waarde die aangeeft of deze betrokkenheid de groep 'ouderschap' heeft of niet.
     */
    public boolean heeftOuderschapGroep() {
        return isOuder() && this.datumAanvangOuderschap != null;
    }

    /**
     * Boolean waarde die aangeeft of deze betrokkenheid de groep 'ouderlijkgezag' heeft of niet.
     * @return waarde die aangeeft of deze betrokkenheid de groep 'ouderlijkgezag' heeft of niet.
     */
    public boolean heeftOuderlijkGezagGroep() {
        return isOuder() && this.indOuderHeeftGezag != null;
    }

    /**
     * Retourneert een indicatie of de betrokkenheid een 'kind' betrokkenheid is; van de soort
     * {@link SoortBetrokkenheid#KIND} is.
     *
     * @return een indicatie of de betrokkenheid een 'kind' betrokkenheid is.
     */
    public boolean isKind() {
        return this.soortBetrokkenheid.equals(SoortBetrokkenheid.KIND);
    }

    /**
     * Retourneert een indicatie of de betrokkenheid een 'ouder' betrokkenheid is; van de soort
     * {@link SoortBetrokkenheid#OUDER} is.
     *
     * @return een indicatie of de betrokkenheid een 'ouder' betrokkenheid is.
     */
    public boolean isOuder() {
        return this.soortBetrokkenheid.equals(SoortBetrokkenheid.OUDER);
    }

    /**
     * Retourneert een indicatie of de betrokkenheid een 'partner' betrokkenheid is; van de soort
     * {@link SoortBetrokkenheid#PARTNER} is.
     *
     * @return een indicatie of de betrokkenheid een 'partner' betrokkenheid is.
     */
    public boolean isPartner() {
        return this.soortBetrokkenheid.equals(SoortBetrokkenheid.PARTNER);
    }

    public Boolean isIndAdresGevend() {
        return this.indAdresGevend;
    }
}
