/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisOuderOuderschapGroep;
import nl.bzk.brp.model.logisch.kern.OuderOuderschapGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_OuderOuderschap")
public class HisOuderOuderschapModel extends AbstractHisOuderOuderschapModel implements HisOuderOuderschapGroep {

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisOuderOuderschapModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param betrokkenheidHisVolledig instantie van A-laag klasse.
     * @param groep                    groep
     * @param historie                 historie
     * @param actieInhoud              actie inhoud
     */
    public HisOuderOuderschapModel(final BetrokkenheidHisVolledig betrokkenheidHisVolledig,
        final OuderOuderschapGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(betrokkenheidHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * @param betrokkenheid              betrokkenheid van HisOuderOuderschapModel
     * @param indicatieOuder             indicatieOuder van HisOuderOuderschapModel
     * @param indicatieOuderUitWieKindIsGeboren indicatieOuderUitWieKindIsGeboren van HisOuderOuderschapModel
     * @param actieInhoud                Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie                   De groep uit een bericht
     */
    public HisOuderOuderschapModel(final BetrokkenheidHisVolledig betrokkenheid, final JaAttribuut indicatieOuder,
        final JaNeeAttribuut indicatieOuderUitWieKindIsGeboren, final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        super(betrokkenheid, indicatieOuder, indicatieOuderUitWieKindIsGeboren, actieInhoud, historie);

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisOuderOuderschapModel(final AbstractHisOuderOuderschapModel kopie) {
        super(kopie);
    }

}
