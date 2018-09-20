/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonEUVerkiezingenGroep;


/**
 * Gegevens over de eventuele deelname aan EU verkiezingen.
 *
 * EU-burgers die in een andere lidstaat wonen hebben het recht om daar te stemmen. EU burgers van andere lidstaten, die
 * in Nederland wonen, en gebruik willen maken van hun stemrecht, doen daartoe een verzoek.
 *
 * Vorm van historie: conform 'NL verkiezingen' zou een materi�le tijdslijn wel betekenis hebben, maar ontbreekt de
 * business case om deze vast te leggen. Om die reden wordt de materi�le tijdslijn onderdrukt, en is in de BRP alleen de
 * formele tijdslijn vastgelegd. Zie ook NL verkiezingen.
 *
 *
 *
 */
public class PersoonEUVerkiezingenGroepBericht extends AbstractPersoonEUVerkiezingenGroepBericht implements
        PersoonEUVerkiezingenGroep
{

}
