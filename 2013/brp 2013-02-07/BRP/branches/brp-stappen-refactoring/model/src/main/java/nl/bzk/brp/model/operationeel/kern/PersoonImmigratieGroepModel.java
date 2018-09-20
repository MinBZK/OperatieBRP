/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonImmigratieGroepModel;


/**
 * Vorm van historie: beiden. Motivatie: je kunt vaker immigreren. Alleen vastleggen van materi�le tijdsaspecten is dus
 * niet voldoende: je moet meerdere (niet overlappende maar wel gaten hebbende) perioden kunnen aanwijzen waarin je
 * 'geimmigreerd bent'. (Logischerwijs is de datum einde geldigheid immigratie gelijk aan de datum ingang emigratie.) In
 * de praktijk zal de datum immigratie 'dicht' bij de datum liggen waarop de immigratie geregistreerd wordt; dit kan
 * echter afwijken. Om die reden materi�le historie vastgelegd NAAST de formele historie.
 *
 * De datum ingang geldigheid komt normaliter overeen met de datum vestiging in Nederland; de laatste is (ook) opgenomen
 * omdat hier vanuit migratie verschillende waarden in kunnen staan.
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
public class PersoonImmigratieGroepModel extends AbstractPersoonImmigratieGroepModel implements PersoonImmigratieGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonImmigratieGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param landVanwaarGevestigd landVanwaarGevestigd van Immigratie.
     * @param datumVestigingInNederland datumVestigingInNederland van Immigratie.
     */
    public PersoonImmigratieGroepModel(final Land landVanwaarGevestigd, final Datum datumVestigingInNederland) {
        super(landVanwaarGevestigd, datumVestigingInNederland);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonImmigratieGroep te kopieren groep.
     */
    public PersoonImmigratieGroepModel(final PersoonImmigratieGroep persoonImmigratieGroep) {
        super(persoonImmigratieGroep);
    }

}
