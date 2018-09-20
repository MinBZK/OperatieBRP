/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.levering.lo3.mapper.ActieHisVolledigLocator;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.levering.lo3.util.HistorieSetUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3AanduidingOuder;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
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
     *
     * @param ouderWijziging
     *            lo3 ouder wijzigingen (output)
     * @param indicatieOuder
     *            indicatie ouder 1 of 2
     * @param gezagWijziging
     *            lo3 gezag wijzigingen (output)
     * @param acties
     *            acties
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actie locator
     * @param mijnBetrokkenheid
     *            mijn (kind) betrokkenheid
     * @param relatie
     *            familierechtelijke betrekking relatie
     * @param gerelateerdeBetrokkenheid
     *            gerelateerde (ouder) betrokkenheid
     */
    public void verwerk(
        final Lo3Wijzigingen<Lo3OuderInhoud> ouderWijziging,
        final LO3AanduidingOuder indicatieOuder,
        final Lo3Wijzigingen<Lo3GezagsverhoudingInhoud> gezagWijziging,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator,
        final KindHisVolledig mijnBetrokkenheid,
        final RelatieHisVolledig relatie,
        final OuderHisVolledig gerelateerdeBetrokkenheid)
    {
        LOGGER.debug("Verwerk ouder betrokkenheid (id={}, indouder={})", gerelateerdeBetrokkenheid.getID(), indicatieOuder);
        final HisBetrokkenheidModel actueleBetrokkenheidEntiteit = bepaalActueel(gerelateerdeBetrokkenheid.getBetrokkenheidHistorie(), acties);
        final HisBetrokkenheidModel historieBetrokkenheidEntiteit = bepaalVerval(gerelateerdeBetrokkenheid.getBetrokkenheidHistorie(), acties);

        final PersoonHisVolledig gerelateerdePersoon = gerelateerdeBetrokkenheid.getPersoon();

        if (historieBetrokkenheidEntiteit != null) {
            LOGGER.debug("Betrokkenheid is vervallen");
            // De betrokkenheid is gevonden als 'vervallen' record. Dit betekent dat de volledige betrokkenheid
            // gecorrigeerd is. Om dit als mutatie te leveren dienen alle gerelateerde 'actuele' groepen als historisch
            // te worden verwerkt. Als een groep historisch wordt verwerkt dan wordt voor verantwoording (groepen 8x) de
            // gekoppelde actie inhoud gebruikt.
            // Nota: het is niet relevant of er nu ook tegelijk een actie inhoud is gevonden.
            betrokkenheidMutatieVerwerker.verwerk(
                ouderWijziging,
                null,
                null,
                historieBetrokkenheidEntiteit,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            // Het kan mogelijk voorkomen dat dit ook de enige wijziging is die gevonden kan worden. Om dan de
            // gerelateerde verantwoording te kunnen leveren, dient de actie verval gebruikt te worden als
            // verantwoording (groepen 8x) in de actuele gegevens. We doen dit alleen als er geen andere actuele
            // gegevens zijn gevonden want dan komen die uit een andere actie inhoud (en dan zou die logischerwijs
            // hetzelfde moeten bevatten).
            if (ouderWijziging.getActueleInhoud() == null) {
                LOGGER.debug("Betrokkenheid is vervallen; verwerk ook als actueel");
                betrokkenheidMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    historieBetrokkenheidEntiteit,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
            }

            ouderschapMutatieVerwerker.verwerk(
                ouderWijziging,
                null,
                null,
                null,
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getOuderOuderschapHistorie()),
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            // getGezagMutatieVerwerker(indicatieOuder).verwerk(
            // gezagWijziging,
            // null,
            // null,
            // null,
            // HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getOuderOuderlijkGezagHistorie()),
            // acties,
            // Collections.<Long>emptyList(),
            // Collections.<Long>emptyList(),
            // onderzoekMapper,
            // actieHisVolledigLocator);

            if (gerelateerdePersoon != null) {
                geboorteMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    null,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeboorteHistorie()),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                identificatienummersMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    null,
                    null,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonIdentificatienummersHistorie()),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                samengesteldeNaamMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    null,
                    null,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonSamengesteldeNaamHistorie()),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                geslachtsaanduidingMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    null,
                    null,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorie()),
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
            } else {
                LOGGER.debug("Betrokkenheid is vervallen; punt ouder");
                // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
                ouderWijziging.setHistorischeInhoud(verwerkPuntOuder(ouderWijziging.getHistorischeInhoud()));
            }
        } else if (actueleBetrokkenheidEntiteit != null) {
            LOGGER.debug("Betrokkenheid is nieuw");
            // De betrokkenheid is gevonden als 'nieuw' record. Dit betekent dat de volledige betrokkenheid
            // nieuw is. Om dit als mutatie te leveren dienen alle gerelateerde 'actuele' groepen als actueel
            // te worden verwerkt.
            betrokkenheidMutatieVerwerker.verwerk(
                ouderWijziging,
                actueleBetrokkenheidEntiteit,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);

            ouderschapMutatieVerwerker.verwerk(
                ouderWijziging,
                HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getOuderOuderschapHistorie()),
                null,
                null,
                null,
                acties,
                Collections.<Long>emptyList(),
                Collections.<Long>emptyList(),
                onderzoekMapper,
                actieHisVolledigLocator);
            // getGezagMutatieVerwerker(indicatieOuder).verwerk(
            // gezagWijziging,
            // HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getOuderOuderlijkGezagHistorie()),
            // null,
            // null,
            // null,
            // acties,
            // Collections.<Long>emptyList(),
            // Collections.<Long>emptyList(),
            // onderzoekMapper,
            // actieHisVolledigLocator);

            if (gerelateerdePersoon != null) {
                geboorteMutatieVerwerker.verwerk(
                    ouderWijziging,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeboorteHistorie()),
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                identificatienummersMutatieVerwerker.verwerk(
                    ouderWijziging,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonIdentificatienummersHistorie()),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                samengesteldeNaamMutatieVerwerker.verwerk(
                    ouderWijziging,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonSamengesteldeNaamHistorie()),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                geslachtsaanduidingMutatieVerwerker.verwerk(
                    ouderWijziging,
                    HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorie()),
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
            } else {
                LOGGER.debug("Betrokkenheid is nieuw; punt ouder");
                // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
                ouderWijziging.setActueleInhoud(verwerkPuntOuder(ouderWijziging.getActueleInhoud()));
            }

        } else {
            LOGGER.debug("Betrokkenheid is niet geraakt");

            // De betrokkenheid is niet geraakt; bepaal het 'ouder zijn' nu op basis van de historie van ouderschap.
            final HisOuderOuderschapModel actueleOuderschapEntiteit = bepaalActueel(gerelateerdeBetrokkenheid.getOuderOuderschapHistorie(), acties);
            final HisOuderOuderschapModel beeindigdeOuderschapEntiteit = bepaalBeeindiging(gerelateerdeBetrokkenheid.getOuderOuderschapHistorie(), acties);
            final HisOuderOuderschapModel vervallenOuderschapEntiteit = bepaalVerval(gerelateerdeBetrokkenheid.getOuderOuderschapHistorie(), acties);

            if (actueleOuderschapEntiteit != null) {
                LOGGER.debug("Ouderschap is nieuw");
                // Het ouderschap is gevonden als 'nieuw' record. Dit betekent dat het ouderschap nieuw is. Om dit als
                // mutatie te leveren dienen alle gerelateerde 'actuele' groepen als actueel te worden verwerkt.
                ouderschapMutatieVerwerker.verwerk(
                    ouderWijziging,
                    actueleOuderschapEntiteit,
                    null,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
                // getGezagMutatieVerwerker(indicatieOuder).verwerk(
                // gezagWijziging,
                // HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getOuderOuderlijkGezagHistorie()),
                // null,
                // null,
                // null,
                // acties,
                // Collections.<Long>emptyList(),
                // Collections.<Long>emptyList(),
                // onderzoekMapper,
                // actieHisVolledigLocator);

                if (gerelateerdePersoon != null) {
                    geboorteMutatieVerwerker.verwerk(
                        ouderWijziging,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeboorteHistorie()),
                        null,
                        null,
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    identificatienummersMutatieVerwerker.verwerk(
                        ouderWijziging,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonIdentificatienummersHistorie()),
                        null,
                        null,
                        null,
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    samengesteldeNaamMutatieVerwerker.verwerk(
                        ouderWijziging,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonSamengesteldeNaamHistorie()),
                        null,
                        null,
                        null,
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    geslachtsaanduidingMutatieVerwerker.verwerk(
                        ouderWijziging,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorie()),
                        null,
                        null,
                        null,
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                } else {
                    LOGGER.debug("Ouderschap is nieuw; punt ouder");
                    // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
                    ouderWijziging.setActueleInhoud(verwerkPuntOuder(ouderWijziging.getActueleInhoud()));
                }
            } else if (beeindigdeOuderschapEntiteit != null) {
                LOGGER.debug("Ouderschap is beeindigd");
                // We hebben het ouderschap gevonden als 'beeindigd' record. Logischer wijs *MOET* er nu ook een
                // vervallen record aanwezig zijn waaruit we de historische gegevens halen.
                ouderschapMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    beeindigdeOuderschapEntiteit,
                    null,
                    null,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);
            }

            if (vervallenOuderschapEntiteit != null) {
                LOGGER.debug("Ouderschap is vervallen");
                // Het ouderschap is gevonden als 'vervallen' record. Dit betekent dat het volledig ouder zijn is
                // gecorrigeerd. Om dit als mutatie te leveren dienen alle gerelateerde 'actuele' groepen als
                // historisch te worden verwerkt. Als een groep historisch wordt verwerkt dan wordt voor verantwoording
                // (groepen 8x) de gekoppelde actie inhoud gebruikt.
                // Nota: het is niet relevant of er nu ook tegelijk een actie inhoud is gevonden.
                ouderschapMutatieVerwerker.verwerk(
                    ouderWijziging,
                    null,
                    null,
                    null,
                    vervallenOuderschapEntiteit,
                    acties,
                    Collections.<Long>emptyList(),
                    Collections.<Long>emptyList(),
                    onderzoekMapper,
                    actieHisVolledigLocator);

                // Het kan mogelijk voorkomen dat dit ook de enige wijziging is die gevonden kan worden. Om dan de
                // gerelateerde verantwoording te kunnen leveren, dient de actie verval gebruikt te worden als
                // verantwoording (groepen 8x) in de actuele gegevens. We doen dit alleen als er geen andere actuele
                // gegevens zijn gevonden want dan komen die uit een andere actie inhoud (en dan zou die logischerwijs
                // hetzelfde moeten bevatten).
                if (ouderWijziging.getActueleInhoud() == null) {
                    ouderschapMutatieVerwerker.verwerk(
                        ouderWijziging,
                        null,
                        null,
                        vervallenOuderschapEntiteit,
                        null,
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                }

                // getGezagMutatieVerwerker(indicatieOuder).verwerk(
                // gezagWijziging,
                // null,
                // null,
                // null,
                // HistorieSetUtil.getActueleRecord(gerelateerdeBetrokkenheid.getOuderOuderlijkGezagHistorie()),
                // acties,
                // Collections.<Long>emptyList(),
                // Collections.<Long>emptyList(),
                // onderzoekMapper,
                // actieHisVolledigLocator);

                if (gerelateerdePersoon != null) {
                    geboorteMutatieVerwerker.verwerk(
                        ouderWijziging,
                        null,
                        null,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeboorteHistorie()),
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    identificatienummersMutatieVerwerker.verwerk(
                        ouderWijziging,
                        null,
                        null,
                        null,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonIdentificatienummersHistorie()),
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    samengesteldeNaamMutatieVerwerker.verwerk(
                        ouderWijziging,
                        null,
                        null,
                        null,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonSamengesteldeNaamHistorie()),
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    geslachtsaanduidingMutatieVerwerker.verwerk(
                        ouderWijziging,
                        null,
                        null,
                        null,
                        HistorieSetUtil.getActueleRecord(gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorie()),
                        acties,
                        Collections.<Long>emptyList(),
                        Collections.<Long>emptyList(),
                        onderzoekMapper,
                        actieHisVolledigLocator);
                } else {
                    LOGGER.debug("Ouderschap is vervallen; punt ouder");
                    // Als er geen gekoppeld persoon is, dan is dit een 'punt' ouder.
                    ouderWijziging.setHistorischeInhoud(verwerkPuntOuder(ouderWijziging.getHistorischeInhoud()));
                }
            }

            if (actueleOuderschapEntiteit == null && beeindigdeOuderschapEntiteit == null && vervallenOuderschapEntiteit == null) {
                LOGGER.debug("Ouderschap is niet geraakt");
                // Geen veranderingen in 'ouderschap'; overige groepen van betrokkenheid en de groepen van
                // persoon gewoon verwerking voor veranderingen.

                if (gerelateerdePersoon != null) {
                    final List<Long> ouderObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(gerelateerdePersoon);

                    geboorteMutatieVerwerker.verwerk(
                        ouderWijziging,
                        gerelateerdePersoon.getPersoonGeboorteHistorie(),
                        acties,
                        ouderObjectSleutels,
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    identificatienummersMutatieVerwerker.verwerk(
                        ouderWijziging,
                        gerelateerdePersoon.getPersoonIdentificatienummersHistorie(),
                        acties,
                        ouderObjectSleutels,
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    samengesteldeNaamMutatieVerwerker.verwerk(
                        ouderWijziging,
                        gerelateerdePersoon.getPersoonSamengesteldeNaamHistorie(),
                        acties,
                        ouderObjectSleutels,
                        onderzoekMapper,
                        actieHisVolledigLocator);
                    geslachtsaanduidingMutatieVerwerker.verwerk(
                        ouderWijziging,
                        gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorie(),
                        acties,
                        ouderObjectSleutels,
                        onderzoekMapper,
                        actieHisVolledigLocator);
                }
            }
        }

        // Gezag
        final List<Long> betrokkenheidObjectSleutels = ObjectSleutelsHelper.bepaalObjectSleutels(gerelateerdeBetrokkenheid);
        getGezagMutatieVerwerker(indicatieOuder).verwerk(
            gezagWijziging,
            gerelateerdeBetrokkenheid.getOuderOuderlijkGezagHistorie(),
            acties,
            betrokkenheidObjectSleutels,
            onderzoekMapper,
            actieHisVolledigLocator);

        ouderGeslachtAdellijkeTitelPredikaatNabewerking.voerNabewerkingUit(ouderWijziging);
        LOGGER.debug("Betrokkenheid verwerkt");
    }

    private Lo3Categorie<Lo3OuderInhoud> verwerkPuntOuder(final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(categorie.getInhoud());
        builder.geslachtsnaam(new Lo3String("."));

        return new Lo3Categorie<Lo3OuderInhoud>(builder.build(), categorie.getDocumentatie(), categorie.getHistorie(), categorie.getLo3Herkomst());
    }

    private AbstractMaterieelMutatieVerwerker<Lo3GezagsverhoudingInhoud, ?, HisOuderOuderlijkGezagModel> getGezagMutatieVerwerker(
        final LO3AanduidingOuder indicatieOuder)
    {
        switch (indicatieOuder) {
            case OUDER1:
                return gezag1MutatieVerwerker;
            case OUDER2:
                return gezag2MutatieVerwerker;
            default:
                throw new IllegalArgumentException();

        }
    }
}
