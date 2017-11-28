/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.AbstractOuderGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderGeboorteMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderGeslachtsaanduidingMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderOuderschapMapper;
import nl.bzk.brp.levering.lo3.mapper.OuderSamengesteldeNaamMapper;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AanduidingOuder visitor.
 */
@Component
public final class OuderVisitor extends AbstractRelatieVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private OuderGeboorteMutatieVerwerker geboorteMutatieVerwerker;
    @Autowired
    private OuderIdentificatienummersMutatieVerwerker identificatienummersMutatieVerwerker;
    @Autowired
    private OuderSamengesteldeNaamMutatieVerwerker samengesteldeNaamMutatieVerwerker;
    @Autowired
    private OuderGeslachtsaanduidingMutatieVerwerker geslachtsaanduidingMutatieVerwerker;
    @Autowired
    private OuderOuderschapMutatieVerwerker ouderschapMutatieVerwerker;
    @Autowired
    private Ouder1GezagMutatieVerwerker gezag1MutatieVerwerker;
    @Autowired
    private Ouder2GezagMutatieVerwerker gezag2MutatieVerwerker;
    @Autowired
    private OuderBetrokkenheidMutatieVerwerker betrokkenheidMutatieVerwerker;

    @Autowired
    private OuderGeslachtAdellijkeTitelPredikaatNabewerking ouderGeslachtAdellijkeTitelPredikaatNabewerking;

    /**
     * Verwerk de wijzigingen in een ouder.
     * @param ouderWijziging lo3 ouder wijzigingen (output)
     * @param indicatieOuder indicatie ouder 1 of 2
     * @param gezagWijziging lo3 gezag wijzigingen (output)
     * @param acties acties
     * @param onderzoekMapper onderzoek mapper
     * @param mijnKindBetrokkenheid mijn (kind) betrokkenheid
     * @param gerelateerdeOuderBetrokkenheid gerelateerde (ouder) betrokkenheid
     */
    public void verwerk(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final AanduidingOuder indicatieOuder,
                        final Lo3Wijzigingen<Lo3GezagsverhoudingInhoud> gezagWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                        final MetaObject mijnKindBetrokkenheid, final MetaObject gerelateerdeOuderBetrokkenheid) {
        LOGGER.debug("Verwerk ouder betrokkenheid (id={}, indouder={})", gerelateerdeOuderBetrokkenheid.getObjectsleutel(), indicatieOuder);
        final MetaRecord actueleBetrokkenheidEntiteit =
                bepaalActueel(MetaModelUtil.getRecords(mijnKindBetrokkenheid, PersoonslijstMapper.KIND_GROEP_ELEMENT), acties);
        final MetaRecord historieBetrokkenheidEntiteit =
                bepaalVerval(MetaModelUtil.getRecords(mijnKindBetrokkenheid, PersoonslijstMapper.KIND_GROEP_ELEMENT), acties);
        final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_IDENTITEIT_GROEP_ELEMENT);

        if (historieBetrokkenheidEntiteit != null) {
            betrokkenheidIsVervallen(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderBetrokkenheid, mijnKindBetrokkenheid);
        } else if (actueleBetrokkenheidEntiteit != null) {
            betrokkenheidIsNieuw(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderBetrokkenheid, mijnKindBetrokkenheid);
        } else {
            betrokkenheidIsNietGeraakt(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderBetrokkenheid);
        }

        // Gezag
        verwerkGezag(indicatieOuder, gezagWijziging, acties, onderzoekMapper, gerelateerdeOuderBetrokkenheid, gerelateerdeOuderBetrokkenheidIdentiteit);

        ouderGeslachtAdellijkeTitelPredikaatNabewerking.voerNabewerkingUit(ouderWijziging);
        LOGGER.debug("Betrokkenheid verwerkt");
    }

    private void betrokkenheidIsVervallen(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                          final MetaObject gerelateerdeOuderBetrokkenheid, final MetaObject mijnKindBetrokkenheid) {
        LOGGER.debug("Betrokkenheid is vervallen");
        final MetaRecord historieBetrokkenheidEntiteit =
                bepaalVerval(MetaModelUtil.getRecords(mijnKindBetrokkenheid, PersoonslijstMapper.KIND_GROEP_ELEMENT), acties);

        final MetaObject gerelateerdeOuderPersoon =
                MetaModelUtil.getObject(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_ELEMENT);

        final MetaRecord mijnKindBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(mijnKindBetrokkenheid, PersoonslijstMapper.KIND_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord gerelateerdeOuderPersoonIdentiteit = gerelateerdeOuderPersoon == null ? null
                : MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderPersoon, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_IDENTITEIT_GROEP_ELEMENT);
        // De betrokkenheid is gevonden als 'vervallen' record. Dit betekent dat de volledige
        // betrokkenheid
        // gecorrigeerd is. Om dit als mutatie te leveren dienen alle gerelateerde 'actuele'
        // groepen als historisch
        // te worden verwerkt. Als een groep historisch wordt verwerkt dan wordt voor
        // verantwoording (groepen 8x) de
        // gekoppelde actie inhoud gebruikt.
        // Nota: het is niet relevant of er nu ook tegelijk een actie inhoud is gevonden.
        betrokkenheidMutatieVerwerker.verwerk(ouderWijziging, mijnKindBetrokkenheidIdentiteit, null, null, historieBetrokkenheidEntiteit, acties,
                Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);

        // Het kan mogelijk voorkomen dat dit ook de enige wijziging is die gevonden kan worden.
        // Om dan de
        // gerelateerde verantwoording te kunnen leveren, dient de actie verval gebruikt te
        // worden als
        // verantwoording (groepen 8x) in de actuele gegevens. We doen dit alleen als er geen
        // andere actuele
        // gegevens zijn gevonden want dan komen die uit een andere actie inhoud (en dan zou die
        // logischerwijs
        // hetzelfde moeten bevatten).
        if (ouderWijziging.getActueleInhoud() == null) {
            LOGGER.debug("Betrokkenheid is vervallen; verwerk ook als actueel");
            betrokkenheidMutatieVerwerker.verwerk(ouderWijziging, mijnKindBetrokkenheidIdentiteit, null, historieBetrokkenheidEntiteit, null, acties,
                    Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
        }

        ouderschapMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderBetrokkenheidIdentiteit, null, null, null,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderBetrokkenheid, OuderOuderschapMapper.GROEP_ELEMENT)), acties,
                Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);

        if (gerelateerdeOuderPersoon != null) {
            geboorteMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeboorteMapper.GROEP_ELEMENT)), acties,
                    Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderIdentificatienummersMapper.GROEP_ELEMENT)),
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderSamengesteldeNaamMapper.GROEP_ELEMENT)), acties,
                    Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeslachtsaanduidingMapper.GROEP_ELEMENT)),
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
        } else {
            LOGGER.debug("Betrokkenheid is vervallen; punt ouder");
            // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
            ouderWijziging.setHistorischeInhoud(verwerkPuntOuder(ouderWijziging.getHistorischeInhoud()));
        }
    }

    private void betrokkenheidIsNieuw(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                      final MetaObject gerelateerdeOuderBetrokkenheid, final MetaObject mijnKindBetrokkenheid) {
        LOGGER.debug("Betrokkenheid is nieuw");
        final MetaRecord actueleBetrokkenheidEntiteit =
                bepaalActueel(MetaModelUtil.getRecords(mijnKindBetrokkenheid, PersoonslijstMapper.KIND_GROEP_ELEMENT), acties);
        final MetaObject gerelateerdeOuderPersoon =
                MetaModelUtil.getObject(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_ELEMENT);

        final MetaRecord mijnKindBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(mijnKindBetrokkenheid, PersoonslijstMapper.KIND_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord gerelateerdeOuderPersoonIdentiteit = gerelateerdeOuderPersoon == null ? null
                : MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderPersoon, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_IDENTITEIT_GROEP_ELEMENT);

        // De betrokkenheid is gevonden als 'nieuw' record. Dit betekent dat de volledige
        // betrokkenheid
        // nieuw is. Om dit als mutatie te leveren dienen alle gerelateerde 'actuele' groepen
        // als actueel
        // te worden verwerkt.
        betrokkenheidMutatieVerwerker.verwerk(ouderWijziging, mijnKindBetrokkenheidIdentiteit, actueleBetrokkenheidEntiteit, null, null, acties,
                Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);

        ouderschapMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderBetrokkenheidIdentiteit,
                MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderBetrokkenheid, AbstractOuderGezagMapper.GROEP_ELEMENT)), null,
                null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);

        if (gerelateerdeOuderPersoon != null) {
            geboorteMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeboorteMapper.GROEP_ELEMENT)), null, null,
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderIdentificatienummersMapper.GROEP_ELEMENT)), null,
                    null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderSamengesteldeNaamMapper.GROEP_ELEMENT)), null,
                    null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeslachtsaanduidingMapper.GROEP_ELEMENT)), null,
                    null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
        } else {
            LOGGER.debug("Betrokkenheid is nieuw; punt ouder");
            // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
            ouderWijziging.setActueleInhoud(verwerkPuntOuder(ouderWijziging.getActueleInhoud()));
        }
    }

    private void betrokkenheidIsNietGeraakt(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                            final MetaObject gerelateerdeOuderBetrokkenheid) {
        LOGGER.debug("Betrokkenheid is niet geraakt");
        // De betrokkenheid is niet geraakt; bepaal het 'ouder zijn' nu op basis van de historie
        // van ouderschap.
        final MetaObject gerelateerdeOuderPersoon =
                MetaModelUtil.getObject(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_ELEMENT);
        final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit =
                MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderBetrokkenheid, PersoonslijstMapper.GERELATEERDE_OUDER_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord gerelateerdeOuderPersoonIdentiteit = gerelateerdeOuderPersoon == null ? null
                : MetaModelUtil.getIdentiteitRecord(gerelateerdeOuderPersoon, PersoonslijstMapper.GERELATEERDE_OUDER_PERSOON_IDENTITEIT_GROEP_ELEMENT);
        final MetaRecord actueleOuderschapEntiteit =
                bepaalActueel(MetaModelUtil.getRecords(gerelateerdeOuderBetrokkenheid, OuderOuderschapMapper.GROEP_ELEMENT), acties);
        final MetaRecord beeindigdeOuderschapEntiteit =
                bepaalBeeindiging(MetaModelUtil.getRecords(gerelateerdeOuderBetrokkenheid, OuderOuderschapMapper.GROEP_ELEMENT), acties);
        final MetaRecord vervallenOuderschapEntiteit =
                bepaalVerval(MetaModelUtil.getRecords(gerelateerdeOuderBetrokkenheid, OuderOuderschapMapper.GROEP_ELEMENT), acties);

        if ((actueleOuderschapEntiteit != null) && (vervallenOuderschapEntiteit == null)) {
            ouderschapIsNieuw(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderPersoon, gerelateerdeOuderBetrokkenheidIdentiteit,
                    gerelateerdeOuderPersoonIdentiteit, actueleOuderschapEntiteit);
        } else if (beeindigdeOuderschapEntiteit != null) {
            ouderschapIsBeeindigd(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderBetrokkenheidIdentiteit, beeindigdeOuderschapEntiteit);
        }

        if ((vervallenOuderschapEntiteit != null) && (actueleOuderschapEntiteit == null)) {
            ouderschapIsVervallen(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderPersoon, gerelateerdeOuderBetrokkenheidIdentiteit,
                    gerelateerdeOuderPersoonIdentiteit, vervallenOuderschapEntiteit);
        }


        if (isOuderschapNietGeraakt(actueleOuderschapEntiteit, beeindigdeOuderschapEntiteit, vervallenOuderschapEntiteit)) {
            ouderschapNietGeraakt(ouderWijziging, acties, onderzoekMapper, gerelateerdeOuderPersoon, gerelateerdeOuderPersoonIdentiteit);
        }
    }

    private boolean isOuderschapNietGeraakt(final MetaRecord actueleOuderschapEntiteit,
                                            final MetaRecord beeindigdeOuderschapEntiteit,
                                            final MetaRecord vervallenOuderschapEntiteit) {
        boolean ouderschapLeeg = actueleOuderschapEntiteit == null;
        ouderschapLeeg = ouderschapLeeg && beeindigdeOuderschapEntiteit == null;
        ouderschapLeeg = ouderschapLeeg && vervallenOuderschapEntiteit == null;
        boolean ouderschapAanwezig = actueleOuderschapEntiteit != null;
        ouderschapAanwezig = ouderschapAanwezig && vervallenOuderschapEntiteit != null;
        return ouderschapLeeg || ouderschapAanwezig;
    }
    private void ouderschapNietGeraakt(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                       final MetaObject gerelateerdeOuderPersoon, final MetaRecord gerelateerdeOuderPersoonIdentiteit) {
        LOGGER.debug("Ouderschap is niet geraakt");
        // Geen veranderingen in 'ouderschap'; overige groepen van betrokkenheid en de
        // groepen van
        // persoon gewoon verwerking voor veranderingen.

        if (gerelateerdeOuderPersoon != null) {
            final List<Long> ouderObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(gerelateerdeOuderPersoon);

            geboorteMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeboorteMapper.GROEP_ELEMENT), acties, ouderObjectSleutels,
                    onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderIdentificatienummersMapper.GROEP_ELEMENT), acties, ouderObjectSleutels,
                    onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderSamengesteldeNaamMapper.GROEP_ELEMENT), acties, ouderObjectSleutels,
                    onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeslachtsaanduidingMapper.GROEP_ELEMENT), acties, ouderObjectSleutels,
                    onderzoekMapper);
        }
    }

    private void ouderschapIsVervallen(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                       final MetaObject gerelateerdeOuderPersoon, final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit,
                                       final MetaRecord gerelateerdeOuderPersoonIdentiteit, final MetaRecord vervallenOuderschapEntiteit) {
        LOGGER.debug("Ouderschap is vervallen");
        // Het ouderschap is gevonden als 'vervallen' record. Dit betekent dat het volledig
        // ouder zijn is
        // gecorrigeerd. Om dit als mutatie te leveren dienen alle gerelateerde 'actuele'
        // groepen als
        // historisch te worden verwerkt. Als een groep historisch wordt verwerkt dan wordt
        // voor verantwoording
        // (groepen 8x) de gekoppelde actie inhoud gebruikt.
        // Nota: het is niet relevant of er nu ook tegelijk een actie inhoud is gevonden.
        ouderschapMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderBetrokkenheidIdentiteit, null, null, null, vervallenOuderschapEntiteit,
                acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);

        // Het kan mogelijk voorkomen dat dit ook de enige wijziging is die gevonden kan
        // worden. Om dan de
        // gerelateerde verantwoording te kunnen leveren, dient de actie verval gebruikt te
        // worden als
        // verantwoording (groepen 8x) in de actuele gegevens. We doen dit alleen als er
        // geen andere actuele
        // gegevens zijn gevonden want dan komen die uit een andere actie inhoud (en dan zou
        // die logischerwijs
        // hetzelfde moeten bevatten).
        if (ouderWijziging.getActueleInhoud() == null) {
            ouderschapMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderBetrokkenheidIdentiteit, null, null, vervallenOuderschapEntiteit, null,
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
        }

        if (gerelateerdeOuderPersoon != null) {
            geboorteMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeboorteMapper.GROEP_ELEMENT)), acties,
                    Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderIdentificatienummersMapper.GROEP_ELEMENT)),
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderSamengesteldeNaamMapper.GROEP_ELEMENT)),
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit, null, null, null,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeslachtsaanduidingMapper.GROEP_ELEMENT)),
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
        } else {
            LOGGER.debug("Ouderschap is vervallen; punt ouder");
            // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
            ouderWijziging.setHistorischeInhoud(verwerkPuntOuder(ouderWijziging.getHistorischeInhoud()));
        }
    }

    private void ouderschapIsBeeindigd(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                       final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit, final MetaRecord beeindigdeOuderschapEntiteit) {
        LOGGER.debug("Ouderschap is beeindigd");
        // We hebben het ouderschap gevonden als 'beeindigd' record. Logischer wijs *MOET*
        // er nu ook een
        // vervallen record aanwezig zijn waaruit we de historische gegevens halen.
        ouderschapMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderBetrokkenheidIdentiteit, null, beeindigdeOuderschapEntiteit, null, null,
                acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
    }

    private void ouderschapIsNieuw(final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging, final List<Long> acties, final OnderzoekMapper onderzoekMapper,
                                   final MetaObject gerelateerdeOuderPersoon, final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit,
                                   final MetaRecord gerelateerdeOuderPersoonIdentiteit, final MetaRecord actueleOuderschapEntiteit) {
        LOGGER.debug("Ouderschap is nieuw");
        // Het ouderschap is gevonden als 'nieuw' record. Dit betekent dat het ouderschap
        // nieuw is. Om dit als
        // mutatie te leveren dienen alle gerelateerde 'actuele' groepen als actueel te
        // worden verwerkt.
        ouderschapMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderBetrokkenheidIdentiteit, actueleOuderschapEntiteit, null, null, null,
                acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);

        if (gerelateerdeOuderPersoon != null) {
            geboorteMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeboorteMapper.GROEP_ELEMENT)), null, null,
                    acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            identificatienummersMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderIdentificatienummersMapper.GROEP_ELEMENT)),
                    null, null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            samengesteldeNaamMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderSamengesteldeNaamMapper.GROEP_ELEMENT)),
                    null, null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
            geslachtsaanduidingMutatieVerwerker.verwerk(ouderWijziging, gerelateerdeOuderPersoonIdentiteit,
                    MetaModelUtil.getActueleRecord(MetaModelUtil.getRecords(gerelateerdeOuderPersoon, OuderGeslachtsaanduidingMapper.GROEP_ELEMENT)),
                    null, null, null, acties, Collections.<Long>emptyList(), Collections.<Long>emptyList(), onderzoekMapper);
        } else {
            LOGGER.debug("Ouderschap is nieuw; punt ouder");
            // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
            ouderWijziging.setActueleInhoud(verwerkPuntOuder(ouderWijziging.getActueleInhoud()));
        }
    }

    private void verwerkGezag(final AanduidingOuder indicatieOuder, final Lo3Wijzigingen<Lo3GezagsverhoudingInhoud> gezagWijziging, final List<Long> acties,
                              final OnderzoekMapper onderzoekMapper, final MetaObject gerelateerdeOuderBetrokkenheid,
                              final MetaRecord gerelateerdeOuderBetrokkenheidIdentiteit) {
        final List<Long> betrokkenheidObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(gerelateerdeOuderBetrokkenheid);
        getGezagMutatieVerwerker(indicatieOuder).verwerk(gezagWijziging, gerelateerdeOuderBetrokkenheidIdentiteit,
                MetaModelUtil.getRecords(gerelateerdeOuderBetrokkenheid, AbstractOuderGezagMapper.GROEP_ELEMENT), acties, betrokkenheidObjectSleutels,
                onderzoekMapper);
    }

    private Lo3Categorie<Lo3OuderInhoud> verwerkPuntOuder(final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(categorie.getInhoud());
        builder.geslachtsnaam(new Lo3String("."));

        return new Lo3Categorie<>(builder.build(), categorie.getDocumentatie(), categorie.getHistorie(), categorie.getLo3Herkomst());
    }

    private AbstractMaterieelMutatieVerwerker<Lo3GezagsverhoudingInhoud, ?> getGezagMutatieVerwerker(final AanduidingOuder indicatieOuder) {
        switch (indicatieOuder) {
            case OUDER_1:
                return gezag1MutatieVerwerker;
            case OUDER_2:
                return gezag2MutatieVerwerker;
            default:
                throw new IllegalArgumentException();

        }
    }
}
