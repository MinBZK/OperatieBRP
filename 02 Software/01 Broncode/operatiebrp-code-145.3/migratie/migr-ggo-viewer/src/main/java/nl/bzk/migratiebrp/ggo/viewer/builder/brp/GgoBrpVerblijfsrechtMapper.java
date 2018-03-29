/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpVerblijfsrechtMapper extends AbstractGgoBrpMapper<PersoonVerblijfsrechtHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpVerblijfsrechtMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                      final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonVerblijfsrechtHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        final GgoBrpValueConvert valueConvert = getGgoBrpValueConvert();
        valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_AANVANG_VERBLIJFSRECHT, brpInhoud.getDatumAanvangVerblijfsrecht());
        valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.AANDUIDING_VERBLIJFSRECHT, brpInhoud.getVerblijfsrecht());
        valueConvert.verwerkElement(
                voorkomen,
                GgoBrpElementEnum.DATUM_MEDEDELING_VERBLIJFSRECHT,
                brpInhoud.getDatumMededelingVerblijfsrecht());
        valueConvert.verwerkElement(
                voorkomen,
                GgoBrpElementEnum.DATUM_VOORZIEN_EINDE_VERBLIJFSRECHT,
                brpInhoud.getDatumVoorzienEindeVerblijfsrecht());
    }
}
