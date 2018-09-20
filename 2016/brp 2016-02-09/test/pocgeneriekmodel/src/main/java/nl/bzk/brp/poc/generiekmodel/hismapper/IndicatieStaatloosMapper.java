/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Map;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpGroepVoorkomen;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementGroep;

/**
 * Created by dennis on 2/5/16.
 */
class IndicatieStaatloosMapper extends AbstractGroepMapper<HisPersoonIndicatieStaatloosModel, ObjectPersoonMapper> {

    @Override
    protected ElementGroep geefElementGroep() {
        return ElementGroep.PersoonIndicatieStaatloosGroep;
    }

    @Override
    public BrpGroep mapGroep(final ObjectPersoonMapper objectMapper, final BrpObject brpObject) {
        final BrpGroep groep = super.maakGroep(brpObject);
        if (objectMapper.getMappedObject().getIndicatieStaatloos() == null) {
            return null;
        }
        for (HisPersoonIndicatieStaatloosModel model : objectMapper.getMappedObject().getIndicatieStaatloos().getPersoonIndicatieHistorie()) {
            final BrpGroepVoorkomen voorkomen = maakVoorkomen(groep, model);
            voorkomen.setVoorkomensleutel(model.getID());
        }
        return groep;
    }

    @Override
    protected HistorieSet<HisPersoonIndicatieStaatloosModel> geefHistorieSet(final ObjectPersoonMapper objectMapper) {
        return objectMapper.getMappedObject().getIndicatieStaatloos().getPersoonIndicatieHistorie();
    }

    @Override
    protected Map<ElementAttribuut, Attribuut> mapAttributen(final HisPersoonIndicatieStaatloosModel model) {
        final Map<ElementAttribuut, Attribuut> map = super.mapAttributen(model);
        map.put(ElementAttribuut.PersoonIndicatieStaatloos, model.getWaarde());
        return map;
    }
}
