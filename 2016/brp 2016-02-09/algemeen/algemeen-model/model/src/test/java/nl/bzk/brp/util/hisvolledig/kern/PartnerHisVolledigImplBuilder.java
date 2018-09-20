/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;

/**
 * Builder klasse voor Partner.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PartnerHisVolledigImplBuilder {

    private PartnerHisVolledigImpl hisVolledigImpl;
    private ActieModel verantwoording;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param relatie relatie van Partner.
     * @param persoon persoon van Partner.
     */
    public PartnerHisVolledigImplBuilder(final RelatieHisVolledigImpl relatie, final PersoonHisVolledigImpl persoon) {
        this.hisVolledigImpl = new PartnerHisVolledigImpl(relatie, persoon);
    }

    /**
     * Verantwoording voor de betrokkenheid historie.
     *
     * @param actie verantwoording voor de historie records
     * @return de betrokkenheid builder.
     */
    public PartnerHisVolledigImplBuilder metVerantwoording(final ActieModel actie) {
        this.verantwoording = actie;
        return this;
    }

    /**
     * Bouw het his volledig betrokkenheid object. Relatie, persoon, verantwoording kunnen null zijn. Dit stelt de
     * ontwikkelaar in staat specifieke scenario's als 'ontbrekende/onbekende ouder' te ondersteunen.
     *
     * @return het his volledig object
     */
    public PartnerHisVolledigImpl build() {
        if (this.hisVolledigImpl.getRelatie() != null) {
            hisVolledigImpl.getRelatie().getBetrokkenheden().add(hisVolledigImpl);
        }
        if (this.hisVolledigImpl.getPersoon() != null) {
            hisVolledigImpl.getPersoon().getBetrokkenheden().add(hisVolledigImpl);
        }
        if (this.verantwoording != null) {
            hisVolledigImpl.getBetrokkenheidHistorie().voegToe(new HisBetrokkenheidModel(hisVolledigImpl, this.verantwoording));
        }
        return hisVolledigImpl;
    }

}
