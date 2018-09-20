/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import java.util.List;

public class Abonnement {

	private Integer id;
	private String populatiecriterium;
	private String abonnementStatusHis;
	private List<AbonnementGegevenselement> abonnementGegevenselementen;
	private List<AbonnementSoortBericht> abonnementSoortBerichten;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getPopulatiecriterium() {
		return populatiecriterium;
	}

	public void setPopulatiecriterium(final String populatiecriterium) {
		this.populatiecriterium = populatiecriterium;
	}

	public String getAbonnementStatusHis() {
		return abonnementStatusHis;
	}

	public void setAbonnementStatusHis(final String abonnementStatusHis) {
		this.abonnementStatusHis = abonnementStatusHis;
	}

	public List<AbonnementGegevenselement> getAbonnementGegevenselementen() {
		return abonnementGegevenselementen;
	}

	public void setAbonnementGegevenselementen(
			final List<AbonnementGegevenselement> abonnementGegevenselementen) {
		this.abonnementGegevenselementen = abonnementGegevenselementen;
	}

	public List<AbonnementSoortBericht> getAbonnementSoortBerichten() {
		return abonnementSoortBerichten;
	}

	public void setAbonnementSoortBerichten(
			final List<AbonnementSoortBericht> abonnementSoortBerichten) {
		this.abonnementSoortBerichten = abonnementSoortBerichten;
	}

}
