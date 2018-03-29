/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpNaamgebruikMapper extends AbstractGgoBrpMapper<PersoonNaamgebruikHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpNaamgebruikMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                   final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonNaamgebruikHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.VOORNAMEN, brpInhoud.getVoornamenNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.VOORVOEGSEL, brpInhoud.getVoorvoegselNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.SCHEIDINGSTEKEN, brpInhoud.getScheidingstekenNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.GESLACHTSNAAMSTAM, brpInhoud.getGeslachtsnaamstamNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.NAAMGEBRUIK, brpInhoud.getNaamgebruik());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.INDICATIE_AFGELEID, brpInhoud.getIndicatieNaamgebruikAfgeleid());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.PREDICAAT, brpInhoud.getPredicaat());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.ADELLIJKE_TITEL, brpInhoud.getAdellijkeTitel());
    }
}
