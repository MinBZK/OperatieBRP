/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.AbstractMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpGroepConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;

/**
 * Basis mutatie verwerker voor formele historie sets.
 * @param <L> lo3 categorie inhoud type
 * @param <G> brp groep type
 */
public abstract class AbstractFormeelMutatieVerwerker<L extends Lo3CategorieInhoud, G extends BrpGroepInhoud> extends AbstractMutatieVerwerker {
    private static final String VERWERK_ACTUELE_INHOUD = "verwerk({}): actueleInhoud -> {}";

    private final AbstractMapper<G> mapper;
    private final AbstractBrpGroepConverteerder<G, L> converteerder;
    private final HistorieNabewerking<L, G> historieNabewerking;
    private final GroepElement groepElement;
    //
    /*
     * squid:S1312 Loggers should be "private static final" and should share a naming convention
     *
     * False positive, dit is een abstract klasse waarin melding worden gelogged die betrekking hebben op de concrete subklasse. De subklasse geeft de juiste
     * logger mee in de constructor.
     */
    private final Logger logger;

    /**
     * Constructor.
     * @param mapper mapper
     * @param converteerder converteerder
     * @param attribuutConverteerder attributen converteerder
     * @param historieNabewerking historie nabewerking
     * @param groepElement groep element
     * @param logger logger
     */
    protected AbstractFormeelMutatieVerwerker(final AbstractMapper<G> mapper, final AbstractBrpGroepConverteerder<G, L> converteerder,
                                              final BrpAttribuutConverteerder attribuutConverteerder, final HistorieNabewerking<L, G> historieNabewerking,
                                              final GroepElement groepElement,
                                              final Logger logger) {
        super(attribuutConverteerder);
        this.mapper = mapper;
        this.converteerder = converteerder;
        this.historieNabewerking = historieNabewerking;
        this.groepElement = groepElement;
        this.logger = logger;
    }

    /**
     * Verwerk wijzigingen.
     * @param wijzigingen lo3 wijzigingen (output)
     * @param identiteitRecord identiteit record (van parent)
     * @param historieSet historie set
     * @param acties acties
     * @param objectSleutels object sleutels
     * @param onderzoekMapper onderzoek mapper
     */
    public final void verwerk(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final Collection<MetaRecord> historieSet,
                              final List<Long> acties, final List<Long> objectSleutels, final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerk({}): obv set", this.getClass().getSimpleName());

        final Collection<MetaRecord> relevanteHistorieSet = filterHistorieSet(historieSet, acties);

        final MetaRecord actueel = bepaalActueel(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper);
        final MetaRecord vervalAlsActueel;
        if (actueel == null) {
            vervalAlsActueel = bepaalVerval(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper, false);
        } else {
            vervalAlsActueel = null;
        }
        final MetaRecord verval = bepaalVerval(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper, true);

        final List<Long> voorkomenSleutels = bepaalVoorkomenSleutels(relevanteHistorieSet);
        verwerk(wijzigingen, identiteitRecord, actueel, vervalAlsActueel, verval, acties, objectSleutels, voorkomenSleutels, onderzoekMapper);
    }

    /**
     * Hook om een historie set te filteren voor een bepaalde mutatie verwerker.
     * @param historieSet historie set
     * @param acties acties
     * @return gefilterde historie set
     */
    
    protected Collection<MetaRecord> filterHistorieSet(final Collection<MetaRecord> historieSet, final List<Long> acties) {
        // Hook
        return historieSet;
    }

    /**
     * Verwerk wijziging.
     * @param wijzigingen lo3 wijzigingen (output)
     * @param identiteitRecord identiteit record (van de parent)
     * @param actueel actuele record
     * @param vervalAlsActueel vervallen record dat gebruikt kan worden voor actuele documentatie
     * @param verval vervallen record
     * @param acties acties
     * @param objectSleutels object sleutels
     * @param voorkomenSleutels voorkomen sleutels
     * @param onderzoekMapper onderzoek mapper
     */
    //
    public final void verwerk(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final MetaRecord actueel,
                              final MetaRecord vervalAlsActueel, final MetaRecord verval, final List<Long> acties, final List<Long> objectSleutels,
                              final List<Long> voorkomenSleutels, final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerk({}): obv records", this.getClass().getSimpleName());
        Lo3Categorie<L> actueleInhoud = wijzigingen.getActueleInhoud();
        if (actueel != null) {
            logger.debug("verwerk({}): actueel={} {}", this.getClass().getSimpleName(), actueel.getVoorkomensleutel(), actueel.getAttributen());
            actueleInhoud = verwerkVolledig(actueleInhoud, identiteitRecord, actueel, onderzoekMapper);
            logger.debug(VERWERK_ACTUELE_INHOUD, this.getClass().getSimpleName(), actueleInhoud);
        } else if (vervalAlsActueel != null) {
            logger.debug("verwerk({}): vervalAlsActueel={} {}", this.getClass().getSimpleName(), vervalAlsActueel.getVoorkomensleutel(),
                    vervalAlsActueel.getAttributen());
            actueleInhoud = verwerkVervalAlsActueel(actueleInhoud, identiteitRecord, vervalAlsActueel, onderzoekMapper);
            logger.debug(VERWERK_ACTUELE_INHOUD, this.getClass().getSimpleName(), actueleInhoud);
        }
        wijzigingen.setActueleInhoud(actueleInhoud);

        Lo3Categorie<L> historischeInhoud = wijzigingen.getHistorischeInhoud();
        if (verval != null) {
            logger.debug("verwerk({}): verval={} {}", this.getClass().getSimpleName(), verval.getVoorkomensleutel(), verval.getAttributen());
            historischeInhoud = verwerkVolledig(historischeInhoud, identiteitRecord, verval, onderzoekMapper);
            logger.debug("verwerk({}): historischeInhoud -> {}", this.getClass().getSimpleName(), historischeInhoud);
        }
        wijzigingen.setHistorischeInhoud(historischeInhoud);

        // Onderzoek, als er geen actueel record is, dan moeten we kijken of er onderzoeken zijn die
        // met actie inhoud of
        // actie aanpassinggeldigheid
        // aan deze administratieve handeling gekoppeld zijn. Zo ja, dan is er alleen een onderzoek
        // gestart.
        // Nota: als er een actueel record is, dan is via de onderzoek mapper ook het onderzoek aan
        // die elementen
        // gekoppeld en dan is het onderzoek reeds verwerkt.
        // Nota: voor historische records geldt dit altijd.
        if ((actueel == null) && (vervalAlsActueel == null)) {
            final Lo3Onderzoek onderzoek = onderzoekMapper.bepaalActueelOnderzoek(acties, objectSleutels, voorkomenSleutels, groepElement);
            wijzigingen.setActueleOnderzoek(mergeOnderzoek(wijzigingen.getActueleOnderzoek(), onderzoek));
        }

        if (verval == null) {
            final Lo3Onderzoek onderzoek = onderzoekMapper.bepaalHistorischOnderzoek(acties, objectSleutels, voorkomenSleutels, groepElement);
            wijzigingen.setHistorischOnderzoek(mergeOnderzoek(wijzigingen.getHistorischeOnderzoek(), onderzoek));
        }
    }

    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *** CONVERTEREN
     * *********************************************************************************************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */

    private Lo3Categorie<L> verwerkVolledig(final Lo3Categorie<L> lo3Categorie, final MetaRecord identiteitRecord, final MetaRecord brpHistorie,
                                            final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerkVolledig({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(identiteitRecord, brpHistorie, onderzoekMapper);

        final L lo3Inhoud =
                converteerder.vulInhoud(lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud(), brpGroep.getInhoud(), null);
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieInhoud(), false);
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(lo3Categorie, brpGroep, lo3Historie);
        }
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieInhoud());
        lo3Documentatie = verwerkInhoudInDocumentatie(identiteitRecord, brpHistorie, lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(lo3Inhoud, lo3Documentatie, lo3Historie, null);
    }

    private Lo3Categorie<L> verwerkVervalAlsActueel(final Lo3Categorie<L> lo3Categorie, final MetaRecord identiteitRecord, final MetaRecord verval,
                                                    final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerkVervalAlsActueel({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(identiteitRecord, verval, onderzoekMapper);

        final L lo3Inhoud = lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud();
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieVerval(), false);
        lo3Historie = new Lo3Historie(null, lo3Historie.getIngangsdatumGeldigheid(), lo3Historie.getDatumVanOpneming());
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(lo3Categorie, brpGroep, lo3Historie);
        }
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieVerval());
        lo3Documentatie = verwerkInhoudInDocumentatie(identiteitRecord, verval, lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(lo3Inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Hook: verwerk inhoud in documentatie.
     * @param identiteitRecord identiteit record (van de parent)
     * @param record brp record
     * @param documentatie documentatie
     * @return (eventueel aangepaste) documentatie
     */
    
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final MetaRecord identiteitRecord, final MetaRecord record, final Lo3Documentatie documentatie) {
        // Hook
        return documentatie;
    }

    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *** BEPALEN
     * *********************************************************************************************
     * ****
     */
    /*
     * *********************************************************************************************
     * ****************
     */
    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Bepaal het actuele record (record waarbij de actie inhoud voorkomt in acties).
     * @param wijzigingen lo3 wijzigingen
     * @param historieSet historie set
     * @param acties acties
     * @return het actuele record, kan null zijn
     */
    private MetaRecord bepaalActueel(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final Collection<MetaRecord> historieSet,
                                     final List<Long> acties, final OnderzoekMapper onderzoekMapper) {
        MetaRecord result = null;
        for (final MetaRecord historie : historieSet) {
            final Actie actieInhoud = historie.getActieInhoud();
            if ((actieInhoud != null) && acties.contains(actieInhoud.getId())) {
                result = historie;
            } else {
                logger.debug("Registreer actie inhoud");
                wijzigingen.registreerActieInhoud(actieInhoud, historie, verwerkVolledig(null, identiteitRecord, historie, onderzoekMapper));
            }
        }
        return result;
    }

    /**
     * Bepaal het vervallen record (record waarbij de actie verval voorkomt in acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het vervallen record, kan null zijn
     */
    private MetaRecord bepaalVerval(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final Collection<MetaRecord> historieSet,
                                    final List<Long> acties, final OnderzoekMapper onderzoekMapper, final boolean gebruikVervalTbvLevMuts) {
        final List<MetaRecord> resultaat = new ArrayList<>();
        for (final MetaRecord historie : historieSet) {
            final Actie verantwoordingVerval = historie.getActieVerval();
            if (verantwoordingVerval != null) {
                if (acties.contains(verantwoordingVerval.getId())) {
                    resultaat.add(historie);
                } else {
                    wijzigingen.registreerActieVerval(verantwoordingVerval, historie,
                            verwerkVervalAlsActueel(null, identiteitRecord, historie, onderzoekMapper));
                }
            }

            if (gebruikVervalTbvLevMuts) {
                final Actie actieVervalTbvLevMuts = historie.getActieVervalTbvLeveringMutaties();
                if ((actieVervalTbvLevMuts != null) && acties.contains(actieVervalTbvLevMuts.getId())) {
                    resultaat.add(historie);
                }
            }
        }

        final MetaRecord eindResultaat;
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
     * @param vervallen vervallen records
     * @return het meest relevante vervallen record
     */
    
    protected MetaRecord bepaalMeestRelevanteVervallenRecord(final List<MetaRecord> vervallen) {
        Collections.sort(vervallen, new TsRegComparator());
        return vervallen.get(vervallen.size() - 1);
    }

    /**
     * Sorteert op basis van tijdstip registratie (meeste recente ts reg komt als eerst in de
     * lijst).
     */
    private static final class TsRegComparator implements Comparator<MetaRecord>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final MetaRecord arg0, final MetaRecord arg1) {
            return arg0.getTijdstipRegistratie().compareTo(arg1.getTijdstipRegistratie());
        }
    }

}
