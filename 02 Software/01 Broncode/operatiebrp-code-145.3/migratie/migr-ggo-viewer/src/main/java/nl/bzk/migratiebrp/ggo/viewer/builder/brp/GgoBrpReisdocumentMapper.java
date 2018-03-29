/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import java.util.Set;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpReisdocumentMapper extends AbstractGgoBrpMapper<PersoonReisdocumentHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpReisdocumentMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                    final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final PersoonReisdocumentHistorie brpInhoud, final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.SOORT,
                brpInhoud.getPersoonReisdocument().getSoortNederlandsReisdocument());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.NUMMER, brpInhoud.getNummer());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_INGANG_DOCUMENT, brpInhoud.getDatumIngangDocument());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_UITGIFTE, brpInhoud.getDatumUitgifte());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.AUTORITEIT_VAN_AFGIFTE, brpInhoud.getAutoriteitVanAfgifte());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_EINDE_DOCUMENT, brpInhoud.getDatumEindeDocument());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.DATUM_INHOUDING_OF_VERMISSING,
                brpInhoud.getDatumInhoudingOfVermissing());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.AANDUIDING_INHOUDING_OF_VERMISSING,
                brpInhoud.getAanduidingInhoudingOfVermissingReisdocument());
    }

    @Override
    public final String bepaalStapelOmschrijving(final Set<PersoonReisdocumentHistorie> brpStapel) {
        for (final PersoonReisdocumentHistorie historie : brpStapel) {
            if (historie.getPersoonReisdocument() != null && historie.getPersoonReisdocument().getSoortNederlandsReisdocument() != null) {
                return historie.getPersoonReisdocument().getSoortNederlandsReisdocument().getCode();
            }
        }

        return "";
    }
}
