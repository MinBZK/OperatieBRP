/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.OuderOuderschapGroepBasis;


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
public abstract class AbstractOuderOuderschapGroepBericht extends AbstractGroepBericht implements
        OuderOuderschapGroepBasis
{

    private Ja indicatieOuder;
    private Ja indicatieOuderUitWieKindIsVoortgekomen;

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

    /**
     * Zet Ouder? van Ouderschap.
     *
     * @param indicatieOuder Ouder?.
     */
    public void setIndicatieOuder(final Ja indicatieOuder) {
        this.indicatieOuder = indicatieOuder;
    }

    /**
     * Zet Ouder uit wie kind is voortgekomen? van Ouderschap.
     *
     * @param indicatieOuderUitWieKindIsVoortgekomen Ouder uit wie kind is voortgekomen?.
     */
    public void setIndicatieOuderUitWieKindIsVoortgekomen(final Ja indicatieOuderUitWieKindIsVoortgekomen) {
        this.indicatieOuderUitWieKindIsVoortgekomen = indicatieOuderUitWieKindIsVoortgekomen;
    }

}
