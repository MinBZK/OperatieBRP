/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.Collection;
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
 * Basis mutatie verwerker voor materiele historie sets.
 * @param <L> lo3 categorie inhoud type
 * @param <G> brp groep type
 */
public abstract class AbstractMaterieelMutatieVerwerker<L extends Lo3CategorieInhoud, G extends BrpGroepInhoud> extends AbstractMutatieVerwerker {

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
     * @param groepElement groep element
     * @param logger logger
     */
    protected AbstractMaterieelMutatieVerwerker(final AbstractMapper<G> mapper, final AbstractBrpGroepConverteerder<G, L> converteerder,
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

        final Collection<MetaRecord> relevanteHistorieSet = filterHistorieSet(historieSet);

        // Inhoud
        final MetaRecord actueel = bepaalActueel(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper);
        final MetaRecord beeindiging = bepaalBeeindiging(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper);
        final MetaRecord vervalAlsActueel;
        if ((actueel == null) && (beeindiging == null)) {
            vervalAlsActueel = bepaalVerval(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper, false);
        } else {
            vervalAlsActueel = null;
        }
        final MetaRecord verval = bepaalVerval(wijzigingen, identiteitRecord, relevanteHistorieSet, acties, onderzoekMapper, true);

        final List<Long> voorkomenSleutels = bepaalVoorkomenSleutels(relevanteHistorieSet);
        verwerk(wijzigingen, identiteitRecord, actueel, beeindiging, vervalAlsActueel, verval, acties, objectSleutels, voorkomenSleutels, onderzoekMapper);
    }

    /**
     * Hook om een historie set te filteren voor een bepaalde mutatie verwerker.
     * @param historieSet historie set
     * @return gefilterde historie set
     */
    protected Collection<MetaRecord> filterHistorieSet(final Collection<MetaRecord> historieSet) {
        // Hook
        return historieSet;
    }

    /**
     * Verwerk wijziging.
     * @param wijzigingen lo3 wijzigingen (output)
     * @param identiteitRecord identiteit record (van parent)
     * @param actueelRecord actuele record
     * @param beeindigingRecord beeindigde record
     * @param vervalAlsActueelRecord vervallen record dat gebruik kan worden voor het actuele record
     * @param vervalRecord vervallen record
     * @param acties acties
     * @param objectSleutels object sleutels
     * @param voorkomenSleutels voorkomen sleutels
     * @param onderzoekMapper onderzoek mapper
     */
    //
    public final void verwerk(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final MetaRecord actueelRecord,
                              final MetaRecord beeindigingRecord, final MetaRecord vervalAlsActueelRecord, final MetaRecord vervalRecord,
                              final List<Long> acties,
                              final List<Long> objectSleutels, final List<Long> voorkomenSleutels, final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerk({}): obv records", this.getClass().getSimpleName());
        Lo3Categorie<L> actueleInhoud = wijzigingen.getActueleInhoud();
        if (actueelRecord != null) {
            logger.debug("verwerk({}): actueel={}", this.getClass().getSimpleName(), actueelRecord);
            actueleInhoud = verwerkVolledig(actueleInhoud, identiteitRecord, actueelRecord, onderzoekMapper);
            logger.debug("verwerk({}): actueel; actueleInhoud -> {}", this.getClass().getSimpleName(), actueleInhoud);
        } else if (beeindigingRecord != null) {
            logger.debug("verwerk({}): beeindiging={}", this.getClass().getSimpleName(), beeindigingRecord);
            actueleInhoud = verwerkBeeindiging(actueleInhoud, identiteitRecord, beeindigingRecord, onderzoekMapper);
            logger.debug("verwerk({}): beeindiging; actueleInhoud -> {}", this.getClass().getSimpleName(), actueleInhoud);
        } else if (vervalAlsActueelRecord != null) {
            logger.debug("verwerk({}): vervalAlsActueel={}", this.getClass().getSimpleName(), vervalAlsActueelRecord);
            actueleInhoud = verwerkVervalAlsActueel(actueleInhoud, identiteitRecord, vervalAlsActueelRecord, onderzoekMapper);
            logger.debug("verwerk({}): vervalAlsActueel; actueleInhoud -> {}", this.getClass().getSimpleName(), actueleInhoud);
        }
        wijzigingen.setActueleInhoud(actueleInhoud);

        Lo3Categorie<L> historischeInhoud = wijzigingen.getHistorischeInhoud();
        if (vervalRecord != null) {
            logger.debug("verwerk({}): verval={}", this.getClass().getSimpleName(), vervalRecord);
            historischeInhoud = verwerkVolledig(historischeInhoud, identiteitRecord, vervalRecord, onderzoekMapper);
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
        if ((actueelRecord == null) && (beeindigingRecord == null) && (vervalAlsActueelRecord == null)) {
            logger.debug("zoek onderzoek");
            final Lo3Onderzoek onderzoek = onderzoekMapper.bepaalActueelOnderzoek(acties, objectSleutels, voorkomenSleutels, groepElement);
            logger.debug("onderzoek : {}", onderzoek);
            wijzigingen.setActueleOnderzoek(mergeOnderzoek(wijzigingen.getActueleOnderzoek(), onderzoek));
        }

        if (vervalRecord == null) {
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

    private Lo3Categorie<L> verwerkVolledig(final Lo3Categorie<L> lo3Categorie, final MetaRecord identiteitRecord, final MetaRecord record,
                                            final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerkVolledig: actieInhoud={}", record.getActieInhoud().getId());
        final BrpGroep<G> brpGroep = mapper.mapGroep(identiteitRecord, record, onderzoekMapper);

        final L lo3Inhoud = verwerkInhoud(lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud(), brpGroep.getInhoud(),
                record, onderzoekMapper);
        logger.debug("verwerkVolledig: lo3Inhoud={}", lo3Inhoud);
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieInhoud(), false);
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(lo3Categorie, brpGroep, lo3Historie);
        }

        logger.debug("verwerkVolledig: lo3Historie={}", lo3Historie);
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieInhoud());
        logger.debug("verwerkVolledig: lo3Documentatie (geconverteerd)={}", lo3Documentatie);
        lo3Documentatie = verwerkInhoudInDocumentatie(lo3Documentatie);
        logger.debug("verwerkVolledig: lo3Documentatie (inhoud verwerkt)={}", lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());
        logger.debug("verwerkVolledig: lo3Documentatie (rni gemerged)={}", lo3Documentatie);

        return new Lo3Categorie<>(lo3Inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Verwerk de inhoud.
     * @param lo3Inhoud huidige lo3 inhoud
     * @param brpInhoud te verwerken brp inhoud
     * @param record het brp historie record waarop brpInhoud is gebaseerd
     * @param onderzoekMapper onderzoek mapper
     * @return nieuwe lo3 inhoud met daarin de brp inhoud verwerkt
     */
    
    protected L verwerkInhoud(final L lo3Inhoud, final G brpInhoud, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        // Hook, voor alternatieve implementatie
        return converteerder.vulInhoud(lo3Inhoud, brpInhoud, null);
    }

    private Lo3Categorie<L> verwerkBeeindiging(final Lo3Categorie<L> lo3Categorie, final MetaRecord identiteitRecord, final MetaRecord actueel,
                                               final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerkBeeindiging({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(identiteitRecord, actueel, onderzoekMapper);

        final L inhoud = verwerkBeeindiging(lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud(), brpGroep.getInhoud());
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieGeldigheid(), true);
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(lo3Categorie, brpGroep, lo3Historie);
        }

        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieGeldigheid());
        lo3Documentatie = verwerkInhoudInDocumentatie(lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Verwerk de beeindiging. Deze methode dient overriden te worden als voor deze mutatie
     * verwerker een beeindigd record wel inhoudelijke gegevens moet bevatten (bijvoorbeeld reden
     * verlies bij een beeindigd nationaliteit record).
     * @param lo3Inhoud huidige lo3inhoud
     * @param brpInhoud te verwerken beeindigde brp inhoud
     * @return nieuwe lo3 inhoud met daarin de beeindigde brp inhoud verwerkt
     */
    //
    /** brpInhoud wordt in de subclasses gebruikt */
    protected L verwerkBeeindiging(final L lo3Inhoud, final G brpInhoud) {
        // Hook
        return lo3Inhoud;
    }

    private Lo3Categorie<L> verwerkVervalAlsActueel(final Lo3Categorie<L> lo3Categorie, final MetaRecord identiteitRecord, final MetaRecord verval,
                                                    final OnderzoekMapper onderzoekMapper) {
        logger.debug("verwerkVervalAlsActueel({})", this.getClass().getName());
        final BrpGroep<G> brpGroep = mapper.mapGroep(identiteitRecord, verval, onderzoekMapper);

        final L inhoud = lo3Categorie == null ? converteerder.maakNieuweInhoud() : lo3Categorie.getInhoud();
        Lo3Historie lo3Historie = converteerHistorie(brpGroep.getHistorie(), brpGroep.getActieVerval(), false);
        if (historieNabewerking != null) {
            lo3Historie = historieNabewerking.bewerk(lo3Categorie, brpGroep, lo3Historie);
        }
        Lo3Documentatie lo3Documentatie = converteerDocument(brpGroep.getActieVerval());
        lo3Documentatie = verwerkInhoudInDocumentatie(lo3Documentatie);
        lo3Documentatie = mergeRni(lo3Documentatie, lo3Categorie == null ? null : lo3Categorie.getDocumentatie());

        return new Lo3Categorie<>(inhoud, lo3Documentatie, lo3Historie, null);
    }

    /**
     * Hook: verwerk inhoud in documentatie.
     * @param documentatie documentatie
     * @return (eventueel aangepaste) documentatie
     */
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final Lo3Documentatie documentatie) {
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
            if (actieInhoud != null) {
                if (acties.contains(actieInhoud.getId())) {
                    logger.debug("actie {} is relevant gekoppeld als actie inhoud", actieInhoud.getId());
                    result = historie;
                } else {
                    logger.debug("Registreer actie inhoud");
                    wijzigingen.registreerActieInhoud(actieInhoud, historie, verwerkVolledig(null, identiteitRecord, historie, onderzoekMapper));
                }
            }
        }
        return result;
    }

    /**
     * Bepaal het beeindigde record (record waarbij de actie aanpassing geldigheid voorkomt in
     * acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het actuele record, kan null zijn
     */
    private MetaRecord bepaalBeeindiging(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final Collection<MetaRecord> historieSet,
                                         final List<Long> acties, final OnderzoekMapper onderzoekMapper) {
        for (final MetaRecord historie : historieSet) {
            final Actie actieAanpassingGeldigheid = historie.getActieAanpassingGeldigheid();
            if (actieAanpassingGeldigheid != null) {
                if (acties.contains(actieAanpassingGeldigheid.getId())) {
                    return historie;
                } else {
                    logger.debug("Registreer actie aanpassing geldigheid");
                    wijzigingen.registreerActieAanpassingGeldigheid(actieAanpassingGeldigheid, historie,
                            verwerkBeeindiging(null, identiteitRecord, historie, onderzoekMapper));
                }
            }
        }
        return null;
    }

    /**
     * Bepaal het vervallen record (record waarbij de actie verval voorkomt in acties).
     * @param historieSet historie set
     * @param acties acties
     * @return het vervallen record, kan null zijn
     */
    private MetaRecord bepaalVerval(final Lo3Wijzigingen<L> wijzigingen, final MetaRecord identiteitRecord, final Collection<MetaRecord> historieSet,
                                    final List<Long> acties, final OnderzoekMapper onderzoekMapper, final boolean gebruikVervalTbvLevMuts) {
        final VervalGegevens vervalGegevens = new VervalGegevens();
        for (final MetaRecord historie : historieSet) {
            final Actie verantwoordingVerval = historie.getActieVerval();
            if (verantwoordingVerval != null) {
                if (acties.contains(verantwoordingVerval.getId())) {
                    final Actie verantwoordingAanpassingGeldigheid = historie.getActieAanpassingGeldigheid();
                    bepaalOfRecordIsVervallen(vervalGegevens, historie, verantwoordingAanpassingGeldigheid);
                } else {
                    wijzigingen.registreerActieVerval(verantwoordingVerval, historie,
                            verwerkVervalAlsActueel(null, identiteitRecord, historie, onderzoekMapper));
                }

                if (gebruikVervalTbvLevMuts) {
                    bepaalOfRecordIsVervallenTbvLevMuts(acties, vervalGegevens, historie);
                }
            }
        }
        return vervalGegevens.getMetaRecord();
    }

    private void bepaalOfRecordIsVervallenTbvLevMuts(final List<Long> acties, final VervalGegevens vervalGegevens,
                                                     final MetaRecord historie) {
        final Actie actieVervalTbvLevMuts = historie.getActieVervalTbvLeveringMutaties();

        if ((actieVervalTbvLevMuts != null) && acties.contains(actieVervalTbvLevMuts.getId())) {
            final Actie verantwoordingAanpassingGeldigheid = historie.getActieAanpassingGeldigheid();
            bepaalOfRecordIsVervallen(vervalGegevens, historie, verantwoordingAanpassingGeldigheid);
        }
    }

    private void bepaalOfRecordIsVervallen(final VervalGegevens vervalGegevens, final MetaRecord historie,
                                           final Actie verantwoordingAanpassingGeldigheid) {
        if (vervalGegevens.getMetaRecord() == null) {
            vervalGegevens.setMetaRecord(historie);
            vervalGegevens.setResultaatHeeftAanpassingGeldigheid(verantwoordingAanpassingGeldigheid != null);
        } else if (vervalGegevens.isResultaatHeeftAanpassingGeldigheid() && (verantwoordingAanpassingGeldigheid == null)) {
            vervalGegevens.setMetaRecord(historie);
            vervalGegevens.setResultaatHeeftAanpassingGeldigheid(false);
        }
    }

    private static class VervalGegevens {
        private MetaRecord metaRecord = null;
        private boolean resultaatHeeftAanpassingGeldigheid = true;

        private MetaRecord getMetaRecord() {
            return metaRecord;
        }

        private void setMetaRecord(final MetaRecord metaRecord) {
            this.metaRecord = metaRecord;
        }

        private boolean isResultaatHeeftAanpassingGeldigheid() {
            return resultaatHeeftAanpassingGeldigheid;
        }

        private void setResultaatHeeftAanpassingGeldigheid(final boolean resultaatHeeftAanpassingGeldigheid) {
            this.resultaatHeeftAanpassingGeldigheid = resultaatHeeftAanpassingGeldigheid;
        }
    }
}
