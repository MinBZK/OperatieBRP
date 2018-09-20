/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Map;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpGroepVoorkomen;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementAttribuut;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementGroep;

/**
 * Created by dennis on 2/5/16.
 */
class GroepAdresStandaardMapper extends AbstractGroepMapper<HisPersoonAdresModel, ObjectAdresMapper> {

    @Override
    protected ElementGroep geefElementGroep() {
        return ElementGroep.PersoonAdresStandaard;
    }

    @Override
    public BrpGroep mapGroep(final ObjectAdresMapper objectMapper, final BrpObject brpObject) {
        final BrpGroep groep = super.maakGroep(brpObject);
        for (HisPersoonAdresModel model : objectMapper.getMappedObject().getPersoonAdresHistorie()) {
            final BrpGroepVoorkomen voorkomen = maakVoorkomen(groep, model);
            voorkomen.setVoorkomensleutel(model.getID());
        }
        return groep;
    }

    @Override
    protected HistorieSet<HisPersoonAdresModel> geefHistorieSet(final ObjectAdresMapper objectMapper) {
        return objectMapper.getMappedObject().getPersoonAdresHistorie();
    }

    @Override
    public Map<ElementAttribuut, Attribuut> mapAttributen(final HisPersoonAdresModel model) {
        final Map<ElementAttribuut, Attribuut> map = super.mapAttributen(model);
        map.put(ElementAttribuut.PersoonAdresBuitenlandsAdresRegel1, model.getBuitenlandsAdresRegel1());
        return map;
    }


}
