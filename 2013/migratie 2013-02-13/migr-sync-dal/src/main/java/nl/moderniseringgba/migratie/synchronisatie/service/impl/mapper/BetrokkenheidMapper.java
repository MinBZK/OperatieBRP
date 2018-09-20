/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Deze mapper mapt een betrokkenheid object uit het migratie model op de betrokkenheid uit het operationele
 * gegevensmodel van de BRP.
 */
public final class BetrokkenheidMapper {

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final BRPActieFactory brpActieFactory;

    /**
     * Maakt een BetrokkenheidMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public BetrokkenheidMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.brpActieFactory = brpActieFactory;
    }

    /**
     * Mapt de betrokkenheid uit het BRP-migratiemodel naar het BRP-datamodel en voegt deze toe op de meegegeven
     * Persoon.
     * 
     * @param migratieBetrokkenheid
     *            De betrokkenheid uit het BRP-migratiemodel. Mag wel null zijn, in dit geval heeft een aanroep van de
     *            methode mapVanMigratie geen wijziging op de meegegeven topLevelEntiteit tot gevolg.
     * @param relatie
     *            De relatie waaraan de betrokkenheid wordt toegevoegd. Mag niet null zijn.
     */
    public void mapVanMigratie(final BrpBetrokkenheid migratieBetrokkenheid, final Relatie relatie) {
        if (relatie == null) {
            throw new NullPointerException("relatie mag niet null zijn");
        }
        if (migratieBetrokkenheid != null) {
            final Betrokkenheid betrokkenheid = new Betrokkenheid();
            betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid
                    .parseCode(migratieBetrokkenheid.getRol().getCode()));

            final Persoon gerelateerdePersoon = mapGerelateerdePersoon(migratieBetrokkenheid);
            if (gerelateerdePersoon != null) {
                gerelateerdePersoon.addBetrokkenheid(betrokkenheid);
            }
            mapBetrokkenheidHistorie(migratieBetrokkenheid, betrokkenheid);

            relatie.addBetrokkenheid(betrokkenheid);
        }
    }

    private Persoon mapGerelateerdePersoon(final BrpBetrokkenheid migratieBetrokkenheid) {
        if (isPuntOuder(migratieBetrokkenheid)) {
            return null;
        }
        final Persoon result = new Persoon();
        result.setSoortPersoon(SoortPersoon.NIET_INGESCHREVENE);

        mapPersoonHistorieStatus(migratieBetrokkenheid, result);
        if (migratieBetrokkenheid.getIdentificatienummersStapel() != null
                && migratieBetrokkenheid.getIdentificatienummersStapel().getMeestRecenteElement().getInhoud()
                        .getAdministratienummer() != null) {
            result.setAdministratienummer(new BigDecimal(migratieBetrokkenheid.getIdentificatienummersStapel()
                    .getMeestRecenteElement().getInhoud().getAdministratienummer()));
        }

        // ids
        new PersoonIDMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                migratieBetrokkenheid.getIdentificatienummersStapel(), result);

        // samengesteldenaam
        new PersoonSamengesteldeNaamMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                migratieBetrokkenheid.getSamengesteldeNaamStapel(), result);

        // geboorte
        new PersoonGeboorteMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                migratieBetrokkenheid.getGeboorteStapel(), result);

        // geslachtsaanduiding
        new PersoonGeslachtsaanduidingMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                migratieBetrokkenheid.getGeslachtsaanduidingStapel(), result);

        return result;
    }

    private boolean isPuntOuder(final BrpBetrokkenheid migratieBetrokkenheid) {
        if (BrpSoortBetrokkenheidCode.OUDER.equals(migratieBetrokkenheid.getRol())) {
            return migratieBetrokkenheid.getSamengesteldeNaamStapel() == null;
        }
        return false;
    }

    private void mapBetrokkenheidHistorie(
            final BrpBetrokkenheid migratieBetrokkenheid,
            final Betrokkenheid betrokkenheid) {
        if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()) {
            // ouder
            new BetrokkenheidOuderMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                    migratieBetrokkenheid.getOuderStapel(), betrokkenheid);
        } else {
            betrokkenheid.setOuderStatusHistorie(HistorieStatus.bepaalHistorieStatus(null));
        }

        // ouderlijk gezag
        new BetrokkenheidOuderlijkGezagMapper(dynamischeStamtabelRepository, brpActieFactory).mapVanMigratie(
                migratieBetrokkenheid.getOuderlijkGezagStapel(), betrokkenheid);
    }

    private void mapPersoonHistorieStatus(
            final BrpBetrokkenheid migratieBetrokkenheid,
            final Persoon gerelateerdePersoon) {
        gerelateerdePersoon.setIdentificatienummersStatusHistorie(HistorieStatus
                .bepaalHistorieStatus(migratieBetrokkenheid.getIdentificatienummersStapel()));
        gerelateerdePersoon.setSamengesteldeNaamStatusHistorie(HistorieStatus
                .bepaalHistorieStatus(migratieBetrokkenheid.getSamengesteldeNaamStapel()));
        gerelateerdePersoon.setGeboorteStatusHistorie(HistorieStatus.bepaalHistorieStatus(migratieBetrokkenheid
                .getGeboorteStapel()));
        gerelateerdePersoon.setGeslachtsaanduidingStatusHistorie(HistorieStatus
                .bepaalHistorieStatus(migratieBetrokkenheid.getGeslachtsaanduidingStapel()));
    }
}
