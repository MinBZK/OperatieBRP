/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * Mapper naar een BrpAfnemersindicaties object.
 */
public final class BrpAfnemersindicatiesMapper {

    /**
     * Map van de database entiteiten naar een BrpAfnemersindicaties object.
     *
     * @param persoon
     *            persoon waarvoor de afnemers indicaties zijn
     * @param afnemersindicaties
     *            lijst met afnemers indicaties
     * @return BrpAfnemersindicaties object, null als persoon null is
     */
    public BrpAfnemersindicaties mapNaarMigratie(final Persoon persoon, final List<PersoonAfnemerindicatie> afnemersindicaties) {
        if (persoon == null) {
            return null;
        }

        final Long administratienummer = persoon.getAdministratienummer();
        final List<BrpAfnemersindicatie> brpAfnemersindicaties = new ArrayList<>();
        if (afnemersindicaties != null) {
            for (final PersoonAfnemerindicatie afnemersindicatie : afnemersindicaties) {
                brpAfnemersindicaties.add(mapNaarMigratie(afnemersindicatie));
            }
        }

        return new BrpAfnemersindicaties(administratienummer, brpAfnemersindicaties);
    }

    /**
     * Map een enkele afnemersindicatie uit de database naar een BrpAfnemersindicatie object.
     *
     * @param afnemersindicatie
     *            afnemersindicatie uit de database
     * @return BrpAfnemersindicatie object
     */
    private BrpAfnemersindicatie mapNaarMigratie(final PersoonAfnemerindicatie afnemersindicatie) {
        // Map basis
        final BrpStapel<BrpAfnemersindicatieInhoud> afnemersindicatieStapel =
                new BrpAfnemersindicatieMapper().map(afnemersindicatie.getPersoonAfnemerindicatieHistorieSet(), null);

        // Map de leveringsautorisatie (zoals gebruikt bij het lezen van afnemersindicaties)
        final String leveringautorisatie = mapLeveringautorisatie(afnemersindicatie.getLeveringsautorisatie());

        // Partij
        final BrpPartij partij = new BrpPartijMapper().mapPartij(afnemersindicatie.getAfnemer(), null);
        final BrpPartijCode partijCode = partij.getPartijCode();

        return new BrpAfnemersindicatie(partijCode, afnemersindicatieStapel, leveringautorisatie);
    }

    /**
     * Leveringsautorisatie word voor het lezen van afnemersindicaties bepaald op de naam.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     * @return identificatie (tbv lezen en testen afnemersindicaties)
     */
    private String mapLeveringautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        return leveringsautorisatie == null ? null : leveringsautorisatie.getNaam();
    }
}
