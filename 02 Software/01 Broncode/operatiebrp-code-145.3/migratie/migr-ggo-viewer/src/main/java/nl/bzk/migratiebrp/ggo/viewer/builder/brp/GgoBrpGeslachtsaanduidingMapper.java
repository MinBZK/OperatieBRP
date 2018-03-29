/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import java.util.Map;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpGeslachtsaanduidingMapper extends AbstractGgoBrpMapper<PersoonGeslachtsaanduidingHistorie> {
    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpGeslachtsaanduidingMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                           final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonGeslachtsaanduidingHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        verwerkInhoud(voorkomen.getInhoud(), brpInhoud);
    }

    /**
     * Uitgebreidere aanroep tbv het verwerken van 'meer' en 'samenvatting'.
     * @param ggoInhoud De Map<String,String> waaraan de inhoud wordt toegevoegd.
     * @param brpInhoud De inhoud van de bron-BRP-groep.
     */
    public final void verwerkInhoud(final Map<String, String> ggoInhoud, final PersoonGeslachtsaanduidingHistorie brpInhoud) {
        getGgoBrpValueConvert().verwerkElement(ggoInhoud, GgoBrpElementEnum.GESLACHTSAANDUIDING, brpInhoud.getGeslachtsaanduiding());
    }
}
