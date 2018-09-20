/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Verantwoording tbv de levering van mutaties.
 */
public interface VerantwoordingTbvLeveringMutaties extends FormeelVerantwoordbaar<ActieModel> {

	/**
	 * Geef de indicatie of dit voorkomen enkel bestaat tbv de levering van mutaties.
	 *
	 * @return indicatie of dit voorkomen enkel bestaat tbv de levering van mutaties
	 */
	JaAttribuut getIndicatieVoorkomenTbvLeveringMutaties();

	/**
	 * Zet de indicatie of dit voorkomen enkel bestaat tbv de levering van mutaties.
	 *
	 * @param indVoorkomenTbvLevMuts indicatie of dit voorkomen enkel bestaat tbv de levering van mutaties
	 */
	void setIndicatieVoorkomenTbvLeveringMutaties(JaAttribuut indVoorkomenTbvLevMuts);

	/**
	 * Geef de actie waardoor dit record is vervallen tbv de levering van mutaties. Indien dit gegevens is
	 * gevuld dient dit te worden gebruikt in plaats van de actie de staat geregistreerd bij actie verval.
	 *
	 * @return de actie waardoor dit record is vervallen
	 */
	ActieModel getVerantwoordingVervalTbvLeveringMutaties();

	/**
	 * Zet de actie waardoor dit record is vervallen tbv de levering van mutaties.
	 *
	 * @param actieVervalTbvLevMuts de actie waardoor dit record is vervallen
	 */
    void setVerantwoordingVervalTbvLeveringMutaties(ActieModel actieVervalTbvLevMuts);
}
