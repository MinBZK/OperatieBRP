/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperking;


/**
 * De verstrekkingsbeperking zoals die voor een in de BRP gekende partij of een in een gemeentelijke verordening benoemde derde voor de persoon van
 * toepassing is.
 */
public final class PersoonVerstrekkingsbeperkingBericht extends AbstractPersoonVerstrekkingsbeperkingBericht implements
    BrpObject, BerichtEntiteit, MetaIdentificeerbaar, PersoonVerstrekkingsbeperking
{

}
