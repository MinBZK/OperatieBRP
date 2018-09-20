/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.brp.levering.lo3.mapper.AbstractFormeelMapper;
import nl.bzk.brp.levering.lo3.mapper.ActieHisVolledigLocator;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
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
 * Basis mutatie verwerker voor formele historie sets.
 *
 * @param <L>
 *            lo3 categorie inhoud type
 * @param <G>
 *            brp groep type
 * @param <H>
 *            historie type
 */
public abstract class AbstractFormeelMutatieVerwerker<L extends Lo3CategorieInhoud, G extends BrpGroepInhoud, H extends FormeelHistorisch & FormeelVerantwoordbaar<ActieModel> & ModelIdentificeerbaar<?>>
        extends AbstractMutatieVerwerker
{
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String VERWERK_ACTUELE_INHOUD = "verwerk({}): actueleInhoud -> {}";

    private final AbstractFormeelMapper<?, H, G> mapper;
    private final BrpGroepConverteerder<G, L> converteerder;
    private final HistorieNabewerking<G> historieNabewerking;
    private final ElementEnum groepElement;

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     * @param historieNabewerking
     *            historie nabewerking
     * @param groepElement
     *            groep element
     */
    protected AbstractFormeelMutatieVerwerker(
        final AbstractFormeelMapper<?, H, G> mapper,
        final BrpGroepConverteerder<G, L> converteerder,
        final HistorieNabewerking<G> historieNabewerking,
        final ElementEnum groepElement)
    {
        this.mapper = mapper;
        this.converteerder = converteerder;
        this.historieNabewerking = historieNabewerking;
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
        final FormeleHistorieSet<H> historieSet,
        final List<Long> acties,
        final List<Long> objectSleutels,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        LOGGER.debug("verwerk({}): obv set", this.getClass().getSimpleName());

        final FormeleHistorieSet<H> relevanteHistorieSet = filterHistorieSet(historieSet, acties);

        final H actueel = bepaalActueel(wijzigingen, relevanteHistorieSet, acties, onderzoekMapper, actieHisVolledigLocator);
        final H vervalAlsActueel;
        if (actueel == null) {
            vervalAlsActueel = bepaalVerval(relevanteHistorieSet, acties, false);
        } else {
            vervalAlsActueel = null;
        }
        final H verval = bepaalVerval(relevanteHistorieSet, acties, true);

        final List<Long> voorkomenSleutels = bepaalVoorkomenSleutels(relevanteHistorieSet);
        verwerk(wijzigingen, actueel, vervalAlsActueel, verval, acties, objectSleutels, voorkomenSleutels, onderzoekMapper, actieHisVolledigLocator);
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
    protected FormeleHistorieSet<H> filterHistorieSet(final FormeleHistorieSet<H> historieSet, final List<Long> acties) {
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
     * @param vervalAlsActueel
     *            vervallen record dat gebruikt kan worden voor actuele documentatie
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
            LOGGER.debug(VERWERK_ACTUELE_INHOUD, this.getClass().getSimpleName(), actueleInhoud);
        } else if (vervalAlsActueel != null) {
            LOGGER.debug("verwerk({}): vervalAlsActueel={}", this.getClass().getSimpleName(), vervalAlsActueel);
            actueleInhoud = verwerkVervalAlsActueel(actueleInhoud, vervalAlsActueel, onderzoekMapper, actieHisVolledigLocator);
            LOGGER.debug(VERWERK_ACTUELE_INHOUD, this.getClass().getSimpleName(), actueleInhoud);
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
        if (actueel == null && vervalAlsActueel == null) {
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
        LOGGER.debug("verwerkVolledig({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(brpHistorie, onderzoekMapper, actieLocator);

        final L lo3Inhoud =
                converteerder.vulInhoud(lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud(), brpGroep.getInhoud(), null);
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieInhoud(), false);
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(brpGroep, lo3Historie);
        }
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieInhoud());
        lo3Documentatie = verwerkInhoudInDocumentatie(brpHistorie, lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(lo3Inhoud, lo3Documentatie, lo3Historie, null);
    }

    private Lo3Categorie<L> verwerkVervalAlsActueel(
        final Lo3Categorie<L> lo3Categorie,
        final H verval,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        LOGGER.debug("verwerkVervalAlsActueel({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(verval, onderzoekMapper, actieLocator);

        final L lo3Inhoud = lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud();
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieVerval(), false);
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(brpGroep, lo3Historie);
        }
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieVerval());
        lo3Documentatie = verwerkInhoudInDocumentatie(verval, lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(lo3Inhoud, lo3Documentatie, lo3Historie, null);
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
        final FormeleHistorieSet<H> historieSet,
        final List<Long> acties,
        final OnderzoekMapper onderzoekMapper,
        final ActieHisVolledigLocator actieLocator)
    {
        H result = null;
        for (final H historie : historieSet) {
            final ActieModel actieInhoud = historie.getVerantwoordingInhoud();
            if (actieInhoud != null && acties.contains(actieInhoud.getID())) {
                result = historie;
            } else {
                LOGGER.debug("Registreer actie inhoud");
                wijzigingen.registreerActieInhoud(actieInhoud, historie, verwerkVolledig(null, historie, onderzoekMapper, actieLocator));
            }
        }
        return result;
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
    private H bepaalVerval(final FormeleHistorieSet<H> historieSet, final List<Long> acties, final boolean gebruikVervalTbvLevMuts) {
        final List<H> resultaat = new ArrayList<>();
        for (final H historie : historieSet) {
            final ActieModel verantwoordingVerval = historie.getVerantwoordingVerval();
            if (verantwoordingVerval != null && acties.contains(verantwoordingVerval.getID())) {
                resultaat.add(historie);
            }

            if (gebruikVervalTbvLevMuts && historie instanceof VerantwoordingTbvLeveringMutaties) {
                final ActieModel actieVervalTbvLevMuts = ((VerantwoordingTbvLeveringMutaties) historie).getVerantwoordingVervalTbvLeveringMutaties();
                if (actieVervalTbvLevMuts != null) {
                    resultaat.add(historie);
                }
            }
        }

        final H eindResultaat;
        if (resultaat.isEmpty()) {
            eindResultaat = null;
        } else if (resultaat.size() == 1) {
            eindResultaat = resultaat.get(0);
        } else {
            eindResultaat = bepaalMeestRelevanteVervallenRecord(resultaat);
        }

        return eindResultaat;
    }

    /**
     * Bepaal het meest relevante vervallen record.
     *
     * @param vervallen
     *            vervallen records
     * @return het meest relevante vervallen record
     */
    protected H bepaalMeestRelevanteVervallenRecord(final List<H> vervallen) {
        Collections.sort(vervallen, new TsRegComparator());
        return vervallen.get(vervallen.size() - 1);
    }

    /**
     * Sorteert op basis van tijdstip registratie (meeste recente ts reg komt als eerst in de lijst).
     */
    private static final class TsRegComparator implements Comparator<FormeelHistorisch>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final FormeelHistorisch arg0, final FormeelHistorisch arg1) {
            return arg0.getFormeleHistorie().getTijdstipRegistratie().compareTo(arg1.getFormeleHistorie().getTijdstipRegistratie());
        }
    }

}
