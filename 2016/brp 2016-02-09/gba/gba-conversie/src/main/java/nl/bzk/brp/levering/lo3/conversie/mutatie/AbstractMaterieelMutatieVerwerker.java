/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.List;
import nl.bzk.brp.levering.lo3.mapper.AbstractMaterieelMapper;
import nl.bzk.brp.levering.lo3.mapper.ActieHisVolledigLocator;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder;

/**
 * Basis mutatie verwerker voor materiele historie sets.
 *
 * @param <L>
 *            lo3 categorie inhoud type
 * @param <G>
 *            brp groep type
 * @param <H>
 *            historie type
 */
public abstract class AbstractMaterieelMutatieVerwerker<L extends Lo3CategorieInhoud, G extends BrpGroepInhoud, H extends MaterieelHistorisch & MaterieelVerantwoordbaar<ActieModel> & ModelIdentificeerbaar<? extends Number>>
        extends AbstractMutatieVerwerker
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final AbstractMaterieelMapper<?, H, G> mapper;
    private final BrpGroepConverteerder<G, L> converteerder;
    private final ElementEnum groepElement;

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     * @param groepElement
     *            groep element
     */
    protected AbstractMaterieelMutatieVerwerker(
        final AbstractMaterieelMapper<?, H, G> mapper,
        final BrpGroepConverteerder<G, L> converteerder,
        final ElementEnum groepElement)
    {
        this.mapper = mapper;
        this.converteerder = converteerder;
        this.groepElement = groepElement;
    }

    /**
     * Verwerk wijzigingen.
     *
     * @param wijzigingen
     *            lo3 wijzigingen (output)
     * @param historieSet
     *            historie set
     * @param acties
     *            acties
     * @param objectSleutels
     *            object sleutels
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actie locator
     */
    public final void verwerk(
        final Lo3Wijzigingen<L> wijzigingen,
        final MaterieleHistorieSet<H> historieSet,
        final List<Long> acties,
        final List<Long> objectSleutels,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        LOGGER.debug("verwerk({}): obv set", this.getClass().getSimpleName());

        final MaterieleHistorieSet<H> relevanteHistorieSet = filterHistorieSet(historieSet, acties);

        // Inhoud
        final H actueel = bepaalActueel(wijzigingen, relevanteHistorieSet, acties, onderzoekMapper, actieHisVolledigLocator);
        final H beeindiging = bepaalBeeindiging(wijzigingen, relevanteHistorieSet, acties, onderzoekMapper, actieHisVolledigLocator);
        final H vervalAlsActueel;
        if (actueel == null && beeindiging == null) {
            vervalAlsActueel = bepaalVerval(relevanteHistorieSet, acties, false);
        } else {
            vervalAlsActueel = null;
        }
        final H verval = bepaalVerval(relevanteHistorieSet, acties, true);

        final List<Long> voorkomenSleutels = bepaalVoorkomenSleutels(relevanteHistorieSet);
        verwerk(
            wijzigingen,
            actueel,
            beeindiging,
            vervalAlsActueel,
            verval,
            acties,
            objectSleutels,
            voorkomenSleutels,
            onderzoekMapper,
            actieHisVolledigLocator);
    }

    /**
     * Hook om een historie set te filteren voor een bepaalde mutatie verwerker.
     *
     * @param historieSet
     *            historie set
     * @param acties
     *            acties
     * @return gefilterde historie set
     */
    protected MaterieleHistorieSet<H> filterHistorieSet(final MaterieleHistorieSet<H> historieSet, final List<Long> acties) {
        // Hook
        return historieSet;
    }

    /**
     * Verwerk wijziging.
     *
     * @param wijzigingen
     *            lo3 wijzigingen (output)
     * @param actueel
     *            actuele record
     * @param beeindiging
     *            beeindigde record
     * @param vervalAlsActueel
     *            vervallen record dat gebruik kan worden voor het actuele record
     * @param verval
     *            vervallen record
     * @param acties
     *            acties
     * @param objectSleutels
     *            object sleutels
     * @param voorkomenSleutels
     *            voorkomen sleutels
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param actieHisVolledigLocator
     *            actie locator
     */
    public final void verwerk(
        final Lo3Wijzigingen<L> wijzigingen,
        final H actueel,
        final H beeindiging,
        final H vervalAlsActueel,
        final H verval,
        final List<Long> acties,
        final List<Long> objectSleutels,
        final List<Long> voorkomenSleutels,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        LOGGER.debug("verwerk({}): obv records", this.getClass().getSimpleName());
        Lo3Categorie<L> actueleInhoud = wijzigingen.getActueleInhoud();
        if (actueel != null) {
            LOGGER.debug("verwerk({}): actueel={}", this.getClass().getSimpleName(), actueel);
            actueleInhoud = verwerkVolledig(actueleInhoud, actueel, onderzoekMapper, actieHisVolledigLocator);
            LOGGER.debug("verwerk({}): actueel; actueleInhoud -> {}", this.getClass().getSimpleName(), actueleInhoud);
        } else if (beeindiging != null) {
            LOGGER.debug("verwerk({}): beeindiging={}", this.getClass().getSimpleName(), beeindiging);
            actueleInhoud = verwerkBeeindiging(actueleInhoud, beeindiging, onderzoekMapper, actieHisVolledigLocator);
            LOGGER.debug("verwerk({}): beeindiging; actueleInhoud -> {}", this.getClass().getSimpleName(), actueleInhoud);
        } else if (vervalAlsActueel != null) {
            LOGGER.debug("verwerk({}): vervalAlsActueel={}", this.getClass().getSimpleName(), vervalAlsActueel);
            actueleInhoud = verwerkVervalAlsActueel(actueleInhoud, vervalAlsActueel, onderzoekMapper, actieHisVolledigLocator);
            LOGGER.debug("verwerk({}): vervalAlsActueel; actueleInhoud -> {}", this.getClass().getSimpleName(), actueleInhoud);
        }
        wijzigingen.setActueleInhoud(actueleInhoud);

        Lo3Categorie<L> historischeInhoud = wijzigingen.getHistorischeInhoud();
        if (verval != null) {
            LOGGER.debug("verwerk({}): verval={}", this.getClass().getSimpleName(), verval);
            historischeInhoud = verwerkVolledig(historischeInhoud, verval, onderzoekMapper, actieHisVolledigLocator);
            LOGGER.debug("verwerk({}): historischeInhoud -> {}", this.getClass().getSimpleName(), historischeInhoud);
        }
        wijzigingen.setHistorischeInhoud(historischeInhoud);

        // Onderzoek, als er geen actueel record is, dan moeten we kijken of er onderzoeken zijn die met actie inhoud of
        // actie aanpassinggeldigheid
        // aan deze administratieve handeling gekoppeld zijn. Zo ja, dan is er alleen een onderzoek gestart.
        // Nota: als er een actueel record is, dan is via de onderzoek mapper ook het onderzoek aan die elementen
        // gekoppeld en dan is het onderzoek reeds verwerkt.
        // Nota: voor historische records geldt dit altijd.
        if (actueel == null && beeindiging == null && vervalAlsActueel == null) {
            final Lo3Onderzoek onderzoek = onderzoekMapper.bepaalActueelOnderzoek(acties, objectSleutels, voorkomenSleutels, groepElement);
            wijzigingen.setActueleOnderzoek(mergeOnderzoek(wijzigingen.getActueleOnderzoek(), onderzoek));
        }

        if (verval == null) {
            final Lo3Onderzoek onderzoek = onderzoekMapper.bepaalHistorischOnderzoek(acties, objectSleutels, voorkomenSleutels, groepElement);
            wijzigingen.setHistorischOnderzoek(mergeOnderzoek(wijzigingen.getHistorischeOnderzoek(), onderzoek));
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** CONVERTEREN ********************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Lo3Categorie<L> verwerkVolledig(
        final Lo3Categorie<L> lo3Categorie,
        final H brpHistorie,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        LOGGER.debug("verwerkVolledig: actieInhoud={}", brpHistorie.getVerantwoordingInhoud().getID());
        final BrpGroep<G> brpGroep = mapper.mapGroep(brpHistorie, onderzoekMapper, actieLocator);

        final L lo3Inhoud =
                verwerkInhoud(
                    lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud(),
                    brpGroep.getInhoud(),
                    brpHistorie,
                    onderzoekMapper);
        LOGGER.debug("verwerkVolledig: lo3Inhoud={}", lo3Inhoud);
        final Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieInhoud(), false);
        LOGGER.debug("verwerkVolledig: lo3Historie={}", lo3Historie);
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieInhoud());
        LOGGER.debug("verwerkVolledig: lo3Documentatie (geconverteerd)={}", lo3Documentatie);
        lo3Documentatie = verwerkInhoudInDocumentatie(brpHistorie, lo3Documentatie);
        LOGGER.debug("verwerkVolledig: lo3Documentatie (inhoud verwerkt)={}", lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());
        LOGGER.debug("verwerkVolledig: lo3Documentatie (rni gemerged)={}", lo3Documentatie);

        return new Lo3Categorie<>(lo3Inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Verwerk de inhoud.
     *
     * @param lo3Inhoud
     *            huidige lo3 inhoud
     * @param brpInhoud
     *            te verwerken brp inhoud
     * @param brpHistorie
     *            het brp historie record waarop brpInhoud is gebaseerd
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return nieuwe lo3 inhoud met daarin de brp inhoud verwerkt
     */
    protected L verwerkInhoud(final L lo3Inhoud, final G brpInhoud, final H brpHistorie, final OnderzoekMapper onderzoekMapper) {
        // Hook, voor alternatieve implementatie
        return converteerder.vulInhoud(lo3Inhoud, brpInhoud, null);
    }

    private Lo3Categorie<L> verwerkBeeindiging(
        final Lo3Categorie<L> lo3Categorie,
        final H actueel,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        LOGGER.debug("verwerkBeeindiging({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(actueel, onderzoekMapper, actieLocator);

        final L inhoud = verwerkBeeindiging(lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud(), brpGroep.getInhoud());
        final Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieGeldigheid(), true);
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieGeldigheid());
        lo3Documentatie = verwerkInhoudInDocumentatie(actueel, lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Verwerk de beeindiging. Deze methode dient overriden te worden als voor deze mutatie verwerker een beeindigd
     * record wel inhoudelijke gegevens moet bevatten (bijvoorbeeld reden verlies bij een beeindigd nationaliteit
     * record).
     *
     * @param lo3Inhoud
     *            huidige lo3inhoud
     * @param brpInhoud
     *            te verwerken beeindigde brp inhoud
     * @return nieuwe lo3 inhoud met daarin de beeindigde brp inhoud verwerkt
     */
    protected L verwerkBeeindiging(final L lo3Inhoud, final G brpInhoud) {
        // Hook
        return lo3Inhoud;
    }

    private Lo3Categorie<L> verwerkVervalAlsActueel(
        final Lo3Categorie<L> lo3Categorie,
        final H verval,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        LOGGER.debug("verwerkVervalAlsActueel({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(verval, onderzoekMapper, actieLocator);

        final L inhoud = lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud();
        final Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieVerval(), false);
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieVerval());
        lo3Documentatie = verwerkInhoudInDocumentatie(verval, lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Hook: verwerk inhoud in documentatie.
     *
     * @param brpHistorie
     *            brp historie
     * @param documentatie
     *            documentatie
     * @return (eventueel aangepaste) documentatie
     */
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final H brpHistorie, final Lo3Documentatie documentatie) {
        // Hook
        return documentatie;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** BEPALEN ************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Bepaal het actuele record (record waarbij de actie inhoud voorkomt in acties).
     *
     * @param wijzigingen
     *            lo3 wijzigingen
     * @param historieSet
     *            historie set
     * @param acties
     *            acties
     * @return het actuele record, kan null zijn
     */
    private H bepaalActueel(
        final Lo3Wijzigingen<L> wijzigingen,
        final MaterieleHistorieSet<H> historieSet,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        H result = null;
        for (final H historie : historieSet) {
            final ActieModel actieInhoud = historie.getVerantwoordingInhoud();
            if (actieInhoud != null) {
                if (acties.contains(actieInhoud.getID())) {
                    LOGGER.debug("actie {} is relevant gekoppeld als actie inhoud", actieInhoud.getID());
                    result = historie;
                } else {
                    LOGGER.debug("Registreer actie inhoud");
                    wijzigingen.registreerActieInhoud(actieInhoud, historie, verwerkVolledig(null, historie, onderzoekMapper, actieLocator));
                }
            }
        }
        return result;
    }

    /**
     * Bepaal het beeindigde record (record waarbij de actie aanpassing geldigheid voorkomt in acties).
     *
     * @param historieSet
     *            historie set
     * @param acties
     *            acties
     * @return het actuele record, kan null zijn
     */
    private H bepaalBeeindiging(
        final Lo3Wijzigingen<L> wijzigingen,
        final MaterieleHistorieSet<H> historieSet,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        for (final H historie : historieSet) {
            final ActieModel actieAanpassingGeldigheid = historie.getVerantwoordingAanpassingGeldigheid();
            if (actieAanpassingGeldigheid != null) {
                if (acties.contains(actieAanpassingGeldigheid.getID())) {
                    return historie;
                } else {
                    LOGGER.debug("Registreer actie aanpassing geldigheid");
                    wijzigingen.registreerActieAanpassingGeldigheid(
                        actieAanpassingGeldigheid,
                        historie,
                        verwerkBeeindiging(null, historie, onderzoekMapper, actieLocator));
                }
            }
        }
        return null;
    }

    /**
     * Bepaal het vervallen record (record waarbij de actie verval voorkomt in acties).
     *
     * @param historieSet
     *            historie set
     * @param acties
     *            acties
     * @return het vervallen record, kan null zijn
     */
    private H bepaalVerval(final MaterieleHistorieSet<H> historieSet, final List<Long> acties, final boolean gebruikVervalTbvLevMuts) {
        H resultaat = null;
        boolean resultaatHeeftAanpassingGeldigheid = true;
        for (final H historie : historieSet) {
            final ActieModel verantwoordingVerval = historie.getVerantwoordingVerval();
            if (verantwoordingVerval != null && acties.contains(verantwoordingVerval.getID())) {
                final ActieModel verantwoordingAanpassingGeldigheid = historie.getVerantwoordingAanpassingGeldigheid();
                if (resultaat == null) {
                    resultaat = historie;
                    resultaatHeeftAanpassingGeldigheid = verantwoordingAanpassingGeldigheid != null;
                } else {
                    if (resultaatHeeftAanpassingGeldigheid && verantwoordingAanpassingGeldigheid == null) {
                        resultaat = historie;
                        resultaatHeeftAanpassingGeldigheid = false;
                    }
                }
            }

            if (gebruikVervalTbvLevMuts && historie instanceof VerantwoordingTbvLeveringMutaties) {
                final ActieModel actieVervalTbvLevMuts = ((VerantwoordingTbvLeveringMutaties) historie).getVerantwoordingVervalTbvLeveringMutaties();

                if (actieVervalTbvLevMuts != null && acties.contains(actieVervalTbvLevMuts.getID())) {
                    final ActieModel verantwoordingAanpassingGeldigheid = historie.getVerantwoordingAanpassingGeldigheid();
                    if (resultaat == null) {
                        resultaat = historie;
                        resultaatHeeftAanpassingGeldigheid = verantwoordingAanpassingGeldigheid != null;
                    } else {
                        if (resultaatHeeftAanpassingGeldigheid && verantwoordingAanpassingGeldigheid == null) {
                            resultaat = historie;
                            resultaatHeeftAanpassingGeldigheid = false;
                        }
                    }
                }
            }
        }
        return resultaat;
    }
}
