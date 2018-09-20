/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RelatieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortMultiRealiteitRegel;

import org.springframework.stereotype.Component;

/**
 * Map BRP database betrokkenheden.
 */
// CHECKSTYLE:OFF - Fan-Out complexity
@Component
public final class BrpRelatieMapper {
    // CHECKSTYLE:ON

    @Inject
    private BrpRelatieInhoudMapper relatieMapper;
    @Inject
    private BrpIdentificatienummersMapper brpIdentificatienummersMapper;
    @Inject
    private BrpGeslachtsaanduidingMapper brpGeslachtsaanduidingMapper;
    @Inject
    private BrpGeboorteMapper brpGeboorteMapper;
    @Inject
    private BrpSamengesteldeNaamMapper brpSamengesteldeNaamMapper;
    @Inject
    private BrpOuderlijkGezagMapper brpOuderlijkGezagMapper;
    @Inject
    private BrpOuderMapper brpOuderMapper;
    @Inject
    private BrpMultiRealiteitMapper brpMultiRealiteitMapper;

    /**
     * Map de BRP database betrokkenheden naar het BRP conversiemodel.
     * 
     * @param ikBetrokkenheidSet
     *            de 'ik'-betrokkenheid vanuit de te converteren persoon
     * @param multiRealiteitSet
     *            de multi-realiteit regels die geldig zijn voor deze persoon (dus hoe deze persoon dus de relaties,
     *            betrokkenheden en gerelateerde personen ziet)
     * @return BRP conversie model
     */
    // CHECKSTYLE:OFF - Cyclomatic complexity - Komt door de loops voor verwerken MR; opdelen maakt het echter
    // onleesbaar
    public List<BrpRelatie> map(
    // CHECKSTYLE:ON
            final Set<Betrokkenheid> ikBetrokkenheidSet,
            final Set<MultiRealiteitRegel> multiRealiteitSet) {
        final List<BrpRelatie> result = new ArrayList<BrpRelatie>();

        for (final Betrokkenheid ikBetrokkenheid : ikBetrokkenheidSet) {
            final Relatie relatie = ikBetrokkenheid.getRelatie();

            final BrpSoortRelatieCode soortRelatieCode =
                    BrpMapperUtil.mapBrpSoortRelatieCode(relatie.getSoortRelatie());
            final BrpSoortBetrokkenheidCode rolCode =
                    BrpMapperUtil.mapBrpSoortBetrokkenheidCode(ikBetrokkenheid.getSoortBetrokkenheid());
            BrpStapel<BrpRelatieInhoud> relatieStapel = relatieMapper.map(relatie.getRelatieHistorieSet());

            // Verwerk multirealiteit op relatie
            if (multiRealiteitSet != null) {
                for (final MultiRealiteitRegel multiRealiteit : multiRealiteitSet) {
                    if (SoortMultiRealiteitRegel.RELATIE.equals(multiRealiteit.getSoortMultiRealiteitRegel())
                            && relatie.getId().equals(multiRealiteit.getRelatie().getId())) {
                        relatieStapel = brpMultiRealiteitMapper.verwerk(relatieStapel, multiRealiteit);
                    }
                }
            }

            if (BrpSoortRelatieCode.HUWELIJK == soortRelatieCode
                    || BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP == soortRelatieCode) {
                if (relatieStapel == null) {
                    // De volledige verbintenis wordt wordt, dus we voegen helemaal niets toe
                    continue;
                }
            }

            final List<BrpBetrokkenheid> betrokkenheden = new ArrayList<BrpBetrokkenheid>();
            for (final Betrokkenheid betrokkene : relatie.getBetrokkenheidSet()) {
                if (!betrokkene.getId().equals(ikBetrokkenheid.getId())) {
                    if (ikBetrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.OUDER
                            && betrokkene.getSoortBetrokkenheid() == SoortBetrokkenheid.OUDER) {
                        continue;
                    }

                    final BrpBetrokkenheid betrokkenheid =
                            mapBetrokkene(ikBetrokkenheid, betrokkene, multiRealiteitSet);
                    if (betrokkenheid == null) {
                        // De volledige betrokkenheid wordt ontkent, dus we voegen helemaal niets toe
                        continue;
                    }

                    betrokkenheden.add(betrokkenheid);
                }
            }

            if (betrokkenheden.size() > 0) {
                // Door MR kunnen alle betrokkenheden zijn ontkend
                result.add(new BrpRelatie(soortRelatieCode, rolCode, betrokkenheden, relatieStapel));
            }
        }

        return result;
    }

    private BrpBetrokkenheid mapBetrokkene(
            final Betrokkenheid ikBetrokkenheid,
            final Betrokkenheid betrokkene,
            final Set<MultiRealiteitRegel> multiRealiteitSet) {
        // Rol
        final BrpSoortBetrokkenheidCode rol =
                BrpMapperUtil.mapBrpSoortBetrokkenheidCode(betrokkene.getSoortBetrokkenheid());

        // TODO:
        // MR op persoon (aanname: alternatief persoon heeft alleen wijzigingen in formele periode dat MR geldig is)
        // if (multiRealiteitSet != null) {
        // for (final MultiRealiteitRegel multiRealiteit : multiRealiteitSet) {
        // if (SoortMultiRealiteitRegel.PERSOON.equals(multiRealiteit.getSoortMultiRealiteitRegel())
        // && betrokkenPersoon.getId().equals(multiRealiteit.getPersoon().getId())) {
        // final Persoon alternatiefPersoon = multiRealiteit.getMultiRealiteitPersoon();
        //
        // // De MR-regel verwerken in de huidige persoonstapels;
        // identificatienummersStapel =
        // brpMultiRealiteitMapper.verwerk(identificatienummersStapel, multiRealiteit);
        // geslachtsaanduidingStapel =
        // brpMultiRealiteitMapper.verwerk(geslachtsaanduidingStapel, multiRealiteit);
        // geboorteStapel = brpMultiRealiteitMapper.verwerk(geboorteStapel, multiRealiteit);
        // samengesteldeNaamStapel =
        // brpMultiRealiteitMapper.verwerk(samengesteldeNaamStapel, multiRealiteit);
        //
        // // De alternatieve persoon verwerken en toevoegen aan de stapels
        // identificatienummersStapel =
        // mergeStapels(identificatienummersStapel,
        // brpIdentificatienummersMapper.map(alternatiefPersoon.getPersoonIDHistorieSet()));
        // geslachtsaanduidingStapel =
        // mergeStapels(geslachtsaanduidingStapel,
        // brpGeslachtsaanduidingMapper.map(alternatiefPersoon
        // .getPersoonGeslachtsaanduidingHistorieSet()));
        // geboorteStapel =
        // mergeStapels(geboorteStapel,
        // brpGeboorteMapper.map(alternatiefPersoon.getPersoonGeboorteHistorieSet()));
        // samengesteldeNaamStapel =
        // mergeStapels(samengesteldeNaamStapel, brpSamengesteldeNaamMapper.map(alternatiefPersoon
        // .getPersoonSamengesteldeNaamHistorieSet()));
        // }
        // }
        // }

        // Betrokkenheid
        // @formatter:off
        final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel =
                brpOuderlijkGezagMapper.map(betrokkene.getBetrokkenheidOuderlijkGezagHistorieSet());
        
        BrpStapel<BrpOuderInhoud> ouderStapel;
        if (SoortBetrokkenheid.OUDER == ikBetrokkenheid.getSoortBetrokkenheid()) {
            ouderStapel = brpOuderMapper.map(ikBetrokkenheid.getBetrokkenheidOuderHistorieSet());
        } else {
           ouderStapel = brpOuderMapper.map(betrokkene.getBetrokkenheidOuderHistorieSet());
        }
            
        // @formatter:on

        BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
        BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
        BrpStapel<BrpGeboorteInhoud> geboorteStapel;
        BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;

        // Persoon
        final Persoon betrokkenPersoon = betrokkene.getPersoon();
        if (betrokkenPersoon == null) {
            // Dit kan bij een 'onbekende' ouder.
            identificatienummersStapel = null;
            geslachtsaanduidingStapel = null;
            geboorteStapel = null;
            samengesteldeNaamStapel = null;
        } else {
            identificatienummersStapel =
                    brpIdentificatienummersMapper.map(betrokkenPersoon.getPersoonIDHistorieSet());
            geslachtsaanduidingStapel =
                    brpGeslachtsaanduidingMapper.map(betrokkenPersoon.getPersoonGeslachtsaanduidingHistorieSet());
            geboorteStapel = brpGeboorteMapper.map(betrokkenPersoon.getPersoonGeboorteHistorieSet());
            samengesteldeNaamStapel =
                    brpSamengesteldeNaamMapper.map(betrokkenPersoon.getPersoonSamengesteldeNaamHistorieSet());
        }

        // MR - Betrokkenheid: ouder ontkent kind
        if (SoortBetrokkenheid.OUDER == ikBetrokkenheid.getSoortBetrokkenheid()) {
            if (multiRealiteitSet != null) {
                for (final MultiRealiteitRegel multiRealiteit : multiRealiteitSet) {
                    if (SoortMultiRealiteitRegel.BETROKKENHEID.equals(multiRealiteit.getSoortMultiRealiteitRegel())
                            && ikBetrokkenheid.getId().equals(multiRealiteit.getBetrokkenheid().getId())) {
                        identificatienummersStapel =
                                brpMultiRealiteitMapper.verwerk(identificatienummersStapel, multiRealiteit);
                        geslachtsaanduidingStapel =
                                brpMultiRealiteitMapper.verwerk(geslachtsaanduidingStapel, multiRealiteit);
                        geboorteStapel = brpMultiRealiteitMapper.verwerk(geboorteStapel, multiRealiteit);
                        samengesteldeNaamStapel =
                                brpMultiRealiteitMapper.verwerk(samengesteldeNaamStapel, multiRealiteit);

                        if (samengesteldeNaamStapel == null || samengesteldeNaamStapel.isEmpty()) {
                            return null;
                        }
                    }
                }
            }
        }

        // MR - Betrokkenheid: kind ontkent ouder
        if (SoortBetrokkenheid.KIND == ikBetrokkenheid.getSoortBetrokkenheid()) {
            if (multiRealiteitSet != null) {
                for (final MultiRealiteitRegel multiRealiteit : multiRealiteitSet) {
                    if (SoortMultiRealiteitRegel.BETROKKENHEID.equals(multiRealiteit.getSoortMultiRealiteitRegel())
                            && betrokkene.getId().equals(multiRealiteit.getBetrokkenheid().getId())) {
                        ouderStapel = brpMultiRealiteitMapper.verwerk(ouderStapel, multiRealiteit);

                        if (ouderStapel == null || ouderStapel.isEmpty()) {
                            return null;
                        }
                    }
                }
            }
        }

        return new BrpBetrokkenheid(rol, identificatienummersStapel, geslachtsaanduidingStapel, geboorteStapel,
                ouderlijkGezagStapel, samengesteldeNaamStapel, ouderStapel);
    }

    //
    // private <T extends BrpGroepInhoud> BrpStapel<T> mergeStapels(
    // final BrpStapel<T> stapelEen,
    // final BrpStapel<T> stapelTwee) {
    // final List<BrpGroep<T>> groepen = new ArrayList<BrpGroep<T>>();
    // if (stapelEen != null) {
    // groepen.addAll(stapelEen.getGroepen());
    // }
    // if (stapelTwee != null) {
    // groepen.addAll(stapelTwee.getGroepen());
    // }
    //
    // return groepen.isEmpty() ? null : new BrpStapel<T>(groepen);
    //
    // }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpRelatieInhoudMapper extends BrpMapper<RelatieHistorie, BrpRelatieInhoud> {

        @Override
        protected BrpRelatieInhoud mapInhoud(final RelatieHistorie historie) {
            // @formatter:off
            return new BrpRelatieInhoud(BrpMapperUtil.mapDatum(historie.getDatumAanvang()),
                    BrpMapperUtil.mapBrpGemeenteCode(historie.getGemeenteAanvang()),
                    BrpMapperUtil.mapBrpPlaatsCode(historie.getWoonplaatsAanvang()),
                    historie.getBuitenlandsePlaatsAanvang(), historie.getBuitenlandseRegioAanvang(),
                    BrpMapperUtil.mapBrpLandCode(historie.getLandAanvang()),
                    historie.getOmschrijvingLocatieAanvang(), BrpMapperUtil.mapBrpRedenEindeRelatieCode(historie
                            .getRedenBeeindigingRelatie()), BrpMapperUtil.mapDatum(historie.getDatumEinde()),
                    BrpMapperUtil.mapBrpGemeenteCode(historie.getGemeenteEinde()),
                    BrpMapperUtil.mapBrpPlaatsCode(historie.getWoonplaatsEinde()),
                    historie.getBuitenlandsePlaatsEinde(), historie.getBuitenlandseRegioEinde(),
                    BrpMapperUtil.mapBrpLandCode(historie.getLandEinde()), historie.getOmschrijvingLocatieEinde());
            // @formatter:on
        }
    }

}
