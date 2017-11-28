/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.List;
import java.util.Map;

/**
 * Een bericht dat wordt verstuurd ter notificatie van een bijhouding.
 */
@XmlElement("bhg_sysVerwerkBijhoudingsplan")
public final class VerwerkBijhoudingsplanBerichtImpl extends AbstractBmrGroep implements VerwerkBijhoudingsplanBericht {

    private final StuurgegevensElement stuurgegevens;
    private final AdministratieveHandelingPlanElement administratieveHandelingPlan;

    /**
     * Maakt een VerwerkBijhoudingsplanBericht object.
     *
     * @param attributen                   de attributen
     * @param stuurgegevens                de stuurgegevens
     * @param administratieveHandelingPlan de administratieve handeling plan
     */
    public VerwerkBijhoudingsplanBerichtImpl(
            final Map<String, String> attributen,
            final StuurgegevensElement stuurgegevens,
            final AdministratieveHandelingPlanElement administratieveHandelingPlan) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(stuurgegevens, "stuurgegevens");
        ValidatieHelper.controleerOpNullWaarde(administratieveHandelingPlan, "administratieveHandelingPlan");
        this.stuurgegevens = stuurgegevens;
        this.administratieveHandelingPlan = administratieveHandelingPlan;
    }

    @Override
    public StuurgegevensElement getStuurgegevens() {
        return stuurgegevens;
    }

    @Override
    public AdministratieveHandelingPlanElement getAdministratieveHandelingPlan() {
        return administratieveHandelingPlan;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }
}
