/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.logisch.kern.OuderOuderschapGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractOuderOuderschapGroepModel;


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
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class OuderOuderschapGroepModel extends AbstractOuderOuderschapGroepModel implements OuderOuderschapGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected OuderOuderschapGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuder indicatieOuder van Ouderschap.
     * @param ouderUitWieKindIsVoortgekomen ouderUitWieKindIsVoortgekomen van Ouderschap.
     */
    public OuderOuderschapGroepModel(final Ja indicatieOuder, final Ja ouderUitWieKindIsVoortgekomen) {
        super(indicatieOuder, ouderUitWieKindIsVoortgekomen);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouderOuderschapGroep te kopieren groep.
     */
    public OuderOuderschapGroepModel(final OuderOuderschapGroep ouderOuderschapGroep) {
        super(ouderOuderschapGroep);
    }

}
