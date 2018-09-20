/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpNaamgebruikMapper extends AbstractGgoBrpMapper<PersoonNaamgebruikHistorie> {
    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonNaamgebruikHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.VOORNAMEN, brpInhoud.getVoornamenNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.VOORVOEGSEL, brpInhoud.getVoorvoegselNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.SCHEIDINGSTEKEN, brpInhoud.getScheidingstekenNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.GESLACHTSNAAMSTAM, brpInhoud.getGeslachtsnaamstamNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.NAAMGEBRUIK, brpInhoud.getNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.INDICATIE_AFGELEID, brpInhoud.getIndicatieNaamgebruikAfgeleid());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.PREDICAAT, brpInhoud.getPredicaat());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.ADELLIJKE_TITEL, brpInhoud.getAdellijkeTitel());
    }
}
