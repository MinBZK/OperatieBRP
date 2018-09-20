/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.usr;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.impl.gen.AbstractPersoonNationaliteitMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonNationaliteit;

/**
 *
 */
@Entity
@Table(schema = "Kern", name = "persnation")
@SuppressWarnings("serial")
public class PersoonNationaliteitMdl extends AbstractPersoonNationaliteitMdl implements PersoonNationaliteit {
}
