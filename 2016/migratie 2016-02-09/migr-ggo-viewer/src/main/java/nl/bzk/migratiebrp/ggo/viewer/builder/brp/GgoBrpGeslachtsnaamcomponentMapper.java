/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.Set;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import org.springframework.stereotype.Component;

/**
 * @see {nl.bzk.migratiebrp.ggo.viewer.mapper.GgoBrpMapper<T>}
 */
@Component
public class GgoBrpGeslachtsnaamcomponentMapper extends AbstractGgoBrpMapper<PersoonGeslachtsnaamcomponentHistorie> {
    @Override
    public final void verwerkInhoud(
        final GgoBrpVoorkomen voorkomen,
        final PersoonGeslachtsnaamcomponentHistorie brpInhoud,
        final GgoBrpGroepEnum brpGroepEnum)
    {
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.VOORVOEGSEL, brpInhoud.getVoorvoegsel());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.SCHEIDINGSTEKEN, brpInhoud.getScheidingsteken());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.STAM, brpInhoud.getStam());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.PREDICAAT, brpInhoud.getPredicaat());
        getGgoBrpValueConvert().verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.ADELLIJKE_TITEL, brpInhoud.getAdellijkeTitel());
        getGgoBrpValueConvert().verwerkElement(
            voorkomen,
            brpGroepEnum,
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
