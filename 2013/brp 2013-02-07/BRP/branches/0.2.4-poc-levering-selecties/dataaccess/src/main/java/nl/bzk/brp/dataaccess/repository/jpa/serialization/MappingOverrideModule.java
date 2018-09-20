/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;

import nl.bzk.brp.model.basis.AbstractAttribuutType;

/**
 * Jackson module voor het registreren van <code>Mixin</code>s die de mapping specificeren.
 */
public class MappingOverrideModule extends SimpleModule {


    public MappingOverrideModule() {
        super("MappingOverrideModule", new Version(0,1,0,null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(AbstractDynamischObjectType.class, GebruikAttributenMixin.class);
        context.setMixInAnnotations(AbstractGroep.class, GebruikAttributenMixin.class);
        context.setMixInAnnotations(PersoonModel.class, PersoonModelMixin.class);
        context.setMixInAnnotations(RelatieModel.class, PersoonModelMixin.class);

        context.setMixInAnnotations(PersoonAdresModel.class, NegeerPersoonMixin.class);
        context.setMixInAnnotations(PersoonIndicatieModel.class, NegeerPersoonMixin.class);
        context.setMixInAnnotations(PersoonVoornaamModel.class, NegeerPersoonMixin.class);
        context.setMixInAnnotations(PersoonNationaliteitModel.class, NegeerPersoonMixin.class);
        context.setMixInAnnotations(PersoonGeslachtsnaamcomponentModel.class, NegeerPersoonMixin.class);
        context.setMixInAnnotations(BetrokkenheidModel.class, NegeerPersoonMixin.class);

        context.setMixInAnnotations(RelatieModel.class, PlatteSerializatieMixin.class);

    }
}
