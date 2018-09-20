/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonImmigratieGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonImmigratieGroep;


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
 */
public class PersoonImmigratieGroepBericht extends AbstractPersoonImmigratieGroepBericht implements
        PersoonImmigratieGroep
{

}
