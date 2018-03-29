/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Deze mapper mapt een betrokkenheid object uit het migratie model op de betrokkenheid uit het operationele
 * gegevensmodel van de BRP.
 */
public final class BetrokkenheidMapper {

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final BRPActieFactory brpActieFactory;
    private final OnderzoekMapper onderzoekMapper;

    /**
     * Maakt een BetrokkenheidMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public BetrokkenheidMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.brpActieFactory = brpActieFactory;
        this.onderzoekMapper = onderzoekMapper;
    }

    /**
     * Mapt de betrokkenheid uit het BRP-migratiemodel naar het BRP-datamodel en voegt deze toe op de meegegeven
     * Persoon.
     * @param migratieBetrokkenheid De betrokkenheid uit het BRP-migratiemodel. Mag wel null zijn, in dit geval heeft een aanroep van de methode mapVanMigratie
     * geen wijziging op de meegegeven topLevelEntiteit tot gevolg.
     * @param relatie De relatie waaraan de betrokkenheid wordt toegevoegd. Mag niet null zijn.
     * @param objecttype het objecttype van de gerelateerde persoon. Null als het om de 'eigen' persoon gaat.
     */
    public void mapVanMigratie(final BrpBetrokkenheid migratieBetrokkenheid, final Relatie relatie, final Element objecttype) {
        if (relatie == null) {
            throw new NullPointerException("relatie mag niet null zijn");
        }
        if (migratieBetrokkenheid != null) {
            final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.parseCode(migratieBetrokkenheid.getRol().getWaarde()), relatie);
            relatie.addBetrokkenheid(betrokkenheid);
            mapAanduidingOuder(migratieBetrokkenheid, betrokkenheid);
            mapGerelateerdePersoon(migratieBetrokkenheid, betrokkenheid, objecttype);
            mapBetrokkenheidHistorie(migratieBetrokkenheid, betrokkenheid, objecttype);
        }
    }

    private void mapAanduidingOuder(final BrpBetrokkenheid migratieBetrokkenheid, final Betrokkenheid betrokkenheid) {
        Lo3AanduidingOuder aanduidingOuder = null;
        if (migratieBetrokkenheid.getOuderStapel() != null) {
            final Lo3CategorieEnum categorie = migratieBetrokkenheid.getOuderStapel().get(0).getActieInhoud().getLo3Herkomst().getCategorie();
            if (categorie.equals(Lo3CategorieEnum.CATEGORIE_02) || categorie.equals(Lo3CategorieEnum.CATEGORIE_52)) {
                aanduidingOuder = new Lo3AanduidingOuder(AanduidingOuder.OUDER_1, betrokkenheid);
            } else if (categorie.equals(Lo3CategorieEnum.CATEGORIE_03) || categorie.equals(Lo3CategorieEnum.CATEGORIE_53)) {
                aanduidingOuder = new Lo3AanduidingOuder(AanduidingOuder.OUDER_2, betrokkenheid);
            }
        }
        betrokkenheid.setAanduidingOuder(aanduidingOuder);
    }

    private void mapGerelateerdePersoon(final BrpBetrokkenheid migratieBetrokkenheid, final Betrokkenheid betrokkenheid, final Element objecttype) {
        if (isPuntOuder(migratieBetrokkenheid)) {
            return;
        }

        final Persoon gerelateerde = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        gerelateerde.addBetrokkenheid(betrokkenheid);

        // ids
        final PersoonIDMapper persoonIDMapper = new PersoonIDMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonIDMapper.mapVanMigratie(migratieBetrokkenheid.getIdentificatienummersStapel(), gerelateerde, objecttype);

        // samengesteldenaam
        final PersoonSamengesteldeNaamMapper persoonSamengesteldeNaamMapper =
                new PersoonSamengesteldeNaamMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonSamengesteldeNaamMapper.mapVanMigratie(migratieBetrokkenheid.getSamengesteldeNaamStapel(), gerelateerde, objecttype);

        // geboorte
        final PersoonGeboorteMapper persoonGeboorteMapper = new PersoonGeboorteMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonGeboorteMapper.mapVanMigratie(migratieBetrokkenheid.getGeboorteStapel(), gerelateerde, objecttype);

        // geslachtsaanduiding
        final PersoonGeslachtsaanduidingMapper persoonGeslachtsaanduidingMapper =
                new PersoonGeslachtsaanduidingMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        persoonGeslachtsaanduidingMapper.mapVanMigratie(migratieBetrokkenheid.getGeslachtsaanduidingStapel(), gerelateerde, objecttype);
    }

    private boolean isPuntOuder(final BrpBetrokkenheid migratieBetrokkenheid) {
        return BrpSoortBetrokkenheidCode.OUDER.equals(migratieBetrokkenheid.getRol()) && migratieBetrokkenheid.getSamengesteldeNaamStapel() == null;
    }

    private void mapBetrokkenheidHistorie(final BrpBetrokkenheid migratieBetrokkenheid, final Betrokkenheid betrokkenheid, final Element objecttype) {
        if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()) {
            // ouder
            new BetrokkenheidOuderMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper).mapVanMigratie(
                    migratieBetrokkenheid.getOuderStapel(),
                    betrokkenheid, objecttype);
        }

        // ouderlijk gezag
        final BetrokkenheidOuderlijkGezagMapper betrokkenheidOuderlijkGezagMapper =
                new BetrokkenheidOuderlijkGezagMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        betrokkenheidOuderlijkGezagMapper.mapVanMigratie(migratieBetrokkenheid.getOuderlijkGezagStapel(), betrokkenheid, objecttype);

        // identiteit
        final BetrokkenheidIdentiteitMapper betrokkenheidIdentiteitMapper =
                new BetrokkenheidIdentiteitMapper(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
        betrokkenheidIdentiteitMapper.mapVanMigratie(migratieBetrokkenheid.getIdentiteitStapel(), betrokkenheid, objecttype);
    }
}
