/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

public class AbonnementGegevenselement {
	private Integer id;
	private String gegevenselement;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getGegevenselement() {
		return gegevenselement;
	}

	public void setGegevenselement(final String gegevenselement) {
		this.gegevenselement = gegevenselement;
	}

}
