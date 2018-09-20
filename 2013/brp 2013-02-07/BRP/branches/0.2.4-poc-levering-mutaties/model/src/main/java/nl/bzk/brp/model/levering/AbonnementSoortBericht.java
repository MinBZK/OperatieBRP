/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;

public class AbonnementSoortBericht {
	private Integer id;
	private Soortbericht soortBericht;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Soortbericht getSoortBericht() {
		return soortBericht;
	}

	public void setSoortBericht(final Soortbericht soortBericht) {
		this.soortBericht = soortBericht;
	}

}
