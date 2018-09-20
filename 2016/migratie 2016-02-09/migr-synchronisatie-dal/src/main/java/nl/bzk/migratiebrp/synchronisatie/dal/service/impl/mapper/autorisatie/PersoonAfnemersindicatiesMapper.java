/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;

/**
 * Mapper naar database persoonafnemerindicaties.
 */
public final class PersoonAfnemersindicatiesMapper {

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final LeveringsautorisatieRepository leveringsautorisatieRepository;

    /**
     * Constructor.
     *
     * @param dynamischeStamtabelRepository
     *            repository om bv partijen te zoeken
     * @param leveringsautorisatieRepository
     *            repository om leveringsautorisaties te zoeken
     */
    public PersoonAfnemersindicatiesMapper(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final LeveringsautorisatieRepository leveringsautorisatieRepository)
    {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
    }

    /**
     * Map naar een database persoonafnemerindicaties entiteit van een BRP conversie model.
     *
     * @param persoon
     *            te koppelen persoon
     * @param brpAfnemersindicatie
     *            brp conversie model
     * @return persoonafnemerindicaties
     */
    public PersoonAfnemerindicatie mapVanMigratie(final Persoon persoon, final BrpAfnemersindicatie brpAfnemersindicatie) {
        final PersoonAfnemerindicatie resultaat;

        final Partij afnemer = dynamischeStamtabelRepository.findPartijByCode(brpAfnemersindicatie.getPartijCode().getWaarde());
        if (afnemer == null) {
            // Geen partij gevonden obv afnemersindicatie
            Foutmelding.logMeldingFout(
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, brpAfnemersindicatie.getAfnemersindicatieStapel()
                                                                                   .get(0)
                                                                                   .getActieInhoud()
                                                                                   .getLo3Herkomst()
                                                                                   .getStapel(), -1),
                    LogSeverity.ERROR,
                    SoortMeldingCode.AFN010,
                    null);
            resultaat = null;
        } else {
            final Leveringsautorisatie leveringsautorisatie = bepaalLeveringsautorisatie(afnemer);
            if (leveringsautorisatie == null) {
                // Geen leveringsautorisatie gevonden voor partij
                Foutmelding.logMeldingFout(
                    new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, brpAfnemersindicatie.getAfnemersindicatieStapel()
                                                                                       .get(0)
                                                                                       .getActieInhoud()
                                                                                       .getLo3Herkomst()
                                                                                       .getStapel(), -1),
                        LogSeverity.ERROR,
                        SoortMeldingCode.AFN011,
                        null);
                resultaat = null;
            } else {
                // Map afnemersindicatie
                resultaat = new PersoonAfnemerindicatie(persoon, afnemer, leveringsautorisatie);

                new PersoonAfnemerindicatieMapper(dynamischeStamtabelRepository).mapVanMigratie(
                    brpAfnemersindicatie.getAfnemersindicatieStapel(),
                    resultaat);
            }
        }

        return resultaat;
    }

    /**
     * Bepaal de meest recente leveringsautorisatie van de afnemer (ook als deze niet meer geldig is).
     *
     * @param afnemer
     *            afnemer
     * @return meest recente leveringsautorisatie
     */
    private Leveringsautorisatie bepaalLeveringsautorisatie(final Partij afnemer) {
        final PartijRol partijRol = dynamischeStamtabelRepository.getPartijRolByPartij(afnemer, Rol.AFNEMER);
        final List<Leveringsautorisatie> leveringsautorisaties = leveringsautorisatieRepository.findLeveringsautorisatiesVoorPartij(partijRol);
        if (leveringsautorisaties == null || leveringsautorisaties.isEmpty()) {
            return null;
        }

        Leveringsautorisatie resultaat = null;
        for (final Leveringsautorisatie kandidaat : leveringsautorisaties) {
            if (resultaat == null) {
                resultaat = kandidaat;
            } else if (resultaat.getDatumEinde() != null && kandidaat.getDatumEinde() == null) {
                resultaat = kandidaat;
            } else {
                final Integer datumIngang1 = resultaat.getDatumIngang();
                final Integer datumIngang2 = kandidaat.getDatumIngang();

                if (datumIngang1 < datumIngang2) {
                    resultaat = kandidaat;
                }
            }
        }
        return resultaat;
    }

}
