/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;

import java.util.Set;

import javax.inject.Inject;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
public class GgoBrpGeslachtsnaamcomponentMapper extends AbstractGgoBrpMapper<PersoonGeslachtsnaamcomponentHistorie> {

    /**
     * Constructor voor mapper implementatie.
     * @param ggoBrpGegevensgroepenBuilder gegevens groepen builder
     * @param ggoBrpActieBuilder actie builder
     * @param ggoBrpOnderzoekBuilder onderzoek builder
     * @param ggoBrpValueConvert value converter
     */
    @Inject
    public GgoBrpGeslachtsnaamcomponentMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                              final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        super(ggoBrpGegevensgroepenBuilder, ggoBrpActieBuilder, ggoBrpOnderzoekBuilder, ggoBrpValueConvert);
    }

    @Override
    public final void verwerkInhoud(
            final GgoBrpVoorkomen voorkomen,
            final PersoonGeslachtsnaamcomponentHistorie brpInhoud,
            final GgoBrpGroepEnum brpGroepEnum) {
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.VOORVOEGSEL, brpInhoud.getVoorvoegsel());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.SCHEIDINGSTEKEN, brpInhoud.getScheidingsteken());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.STAM, brpInhoud.getStam());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.PREDICAAT, brpInhoud.getPredicaat());
        getGgoBrpValueConvert().verwerkElement(voorkomen, GgoBrpElementEnum.ADELLIJKE_TITEL, brpInhoud.getAdellijkeTitel());
        getGgoBrpValueConvert().verwerkElement(
                voorkomen,
                GgoBrpElementEnum.VOLGNUMMER,
                brpInhoud.getPersoonGeslachtsnaamcomponent().getVolgnummer());
    }

    @Override
    protected final String bepaalStapelOmschrijving(final Set<PersoonGeslachtsnaamcomponentHistorie> brpStapel) {
        for (final PersoonGeslachtsnaamcomponentHistorie historie : brpStapel) {
            if (historie.getPersoonGeslachtsnaamcomponent() != null) {
                return String.valueOf(historie.getPersoonGeslachtsnaamcomponent().getVolgnummer());
            }
        }

        return "";
    }
}
