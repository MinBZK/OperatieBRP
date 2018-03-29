/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummerHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import javax.inject.Inject;

/**
 * Implementatie voor de groep Buitenlands Persoonsnummer.
 */
public final class GgoBrpBuitenlandsPersoonsnummerMapper extends AbstractGgoBrpMapper<PersoonBuitenlandsPersoonsnummerHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    protected GgoBrpBuitenlandsPersoonsnummerMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder,
                                                    final GgoBrpActieBuilder ggoBrpActieBuilder, final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder,
                                                    final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonBuitenlandsPersoonsnummerHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        final PersoonBuitenlandsPersoonsnummer buitenlandsPersoonsnummer = brpInhoud.getPersoonBuitenlandsPersoonsnummer();
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.NUMMER, buitenlandsPersoonsnummer.getNummer());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.AUTORITEIT_VAN_AFGIFTE_BUITENLANDS_PERSOONSNUMMER,
                buitenlandsPersoonsnummer.getAutoriteitAfgifteBuitenlandsPersoonsnummer());
    }
}
