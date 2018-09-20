/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.proces.UniqueSequence;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;

/**
 * LO3 Historie -> BRP Historie conversie.
 * 
 */
public abstract class Lo3HistorieConversieVariant {

    private static final long DATUMTIJDMILLIS_EEN_MINUUT = 100000;

    // CHECKSTYLE:OFF - Exposeren voor subclasses
    @Inject
    protected Lo3AttribuutConverteerder converteerder;

    // CHECKSTYLE:ON

    /**
     * Converteer de migratie groepen (BRP inhoud, LO3 historie) naar BRP groepen (BRP inhoud, BRP historie).
     * 
     * @param <T>
     *            groep inhoud type
     * @param lo3Groepen
     *            lijst van migratie groepen
     * @return lijst van brp groepen
     */
    public abstract <T extends BrpGroepInhoud> List<BrpGroep<T>> converteer(final List<MigratieGroep<T>> lo3Groepen);

    /**
     * Maak een actie obv de gegeven LO3 documentatie en historie.
     * 
     * @param documentatie
     *            LO3 documentatie
     * @param historie
     *            LO3 historie
     * @param lo3Herkomst
     *            LO3 herkomst
     * @return BRP actie
     */
    @Requirement(Requirements.CBA001_LB01)
    @Definitie({ Definities.DEF042, Definities.DEF043, Definities.DEF044 })
    final BrpActie maakActie(
            final Lo3Documentatie documentatie,
            final Lo3Historie historie,
            final Lo3Herkomst lo3Herkomst) {

        final BrpActie actie;
        final BrpDatumTijd datumTijdOpneming = historie.getDatumVanOpneming().converteerNaarBrpDatumTijd();
        final BrpHistorie brpHistorie = new BrpHistorie(null, null, datumTijdOpneming, null);

        if (isDocument(documentatie) || isAkte(documentatie)) {
            final BrpDocumentInhoud documentInhoud;
            final BrpPartijCode documentPartij;

            if (isDocument(documentatie)) {
                // DEF042 Er is een brondocument
                documentPartij =
                        converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(documentatie.getGemeenteDocument());

                documentInhoud =
                        new BrpDocumentInhoud(BrpSoortDocumentCode.DOCUMENT, null, null,
                                documentatie.getBeschrijvingDocument(), documentPartij);
            } else {
                // DEF043 Er is een akte
                documentPartij =
                        converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(documentatie.getGemeenteAkte());

                documentInhoud =
                        new BrpDocumentInhoud(BrpSoortDocumentCode.AKTE, null, documentatie.getNummerAkte(), null,
                                documentPartij);
            }

            // Actie inhoud wordt gezet tijdens de creatie van de BrpActie
            final BrpGroep<BrpDocumentInhoud> brpDocument =
                    new BrpGroep<BrpDocumentInhoud>(documentInhoud, brpHistorie, null, null, null);

            final List<BrpStapel<BrpDocumentInhoud>> documentStapels = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
            documentStapels.add(new BrpStapel<BrpDocumentInhoud>(Collections.singletonList(brpDocument)));

            if (documentatie.getExtraDocument() != null) {
                // Extra document toevoegen
                final BrpDocumentInhoud brpExtraDocumentInhoud =
                        new BrpDocumentInhoud(BrpSoortDocumentCode.MIGRATIEVOORZIENING, null, null,
                                getExtraDocumentOmschrijving(documentatie.getExtraDocument(),
                                        documentatie.getExtraDocumentInformatie()), BrpPartijCode.MIGRATIEVOORZIENING);

                final BrpGroep<BrpDocumentInhoud> brpExtraDocument =
                        new BrpGroep<BrpDocumentInhoud>(brpExtraDocumentInhoud, brpHistorie, null, null, null);
                documentStapels.add(new BrpStapel<BrpDocumentInhoud>(Collections.singletonList(brpExtraDocument)));
            }

            BrpDatumTijd datumTijdOntlening = null;
            if (documentatie.getDatumDocument() != null && documentatie.getDatumDocument().getDatum() != 0) {
                datumTijdOntlening = documentatie.getDatumDocument().converteerNaarBrpDatumTijd();
            }
            actie =
                    new BrpActie(documentatie.getId(), BrpSoortActieCode.CONVERSIE_GBA,
                            BrpPartijCode.MIGRATIEVOORZIENING, null, datumTijdOntlening, datumTijdOpneming,
                            documentStapels, 0, lo3Herkomst);

        } else {
            // DEF044 Er is geen brondocument en geen akte
            final List<BrpStapel<BrpDocumentInhoud>> documentStapels;
            if (documentatie.getExtraDocument() != null) {
                // Extra document toevoegen
                final BrpDocumentInhoud brpExtraDocumentInhoud =
                        new BrpDocumentInhoud(BrpSoortDocumentCode.MIGRATIEVOORZIENING, null, null,
                                getExtraDocumentOmschrijving(documentatie.getExtraDocument(),
                                        documentatie.getExtraDocumentInformatie()), BrpPartijCode.MIGRATIEVOORZIENING);

                final BrpGroep<BrpDocumentInhoud> brpExtraDocument =
                        new BrpGroep<BrpDocumentInhoud>(brpExtraDocumentInhoud, brpHistorie, null, null, null);

                documentStapels = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
                documentStapels.add(new BrpStapel<BrpDocumentInhoud>(Collections.singletonList(brpExtraDocument)));
            } else {
                documentStapels = null;
            }

            final Long documentatieId = documentatie == null ? UniqueSequence.next() : documentatie.getId();
            actie =
                    new BrpActie(documentatieId, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MIGRATIEVOORZIENING,
                            null, null, datumTijdOpneming, documentStapels, 0, lo3Herkomst);
        }

        return actie;
    }

    @Definitie(Definities.DEF042)
    private static boolean isDocument(final Lo3Documentatie documentatie) {
        return documentatie != null && documentatie.isDocument();
    }

    @Definitie(Definities.DEF043)
    private static boolean isAkte(final Lo3Documentatie documentatie) {
        return documentatie != null && documentatie.isAkte();
    }

    private static String getExtraDocumentOmschrijving(
            final Lo3Documentatie.ExtraDocument extraDocument,
            final Integer extraDocumentInformatie) {
        switch (extraDocument) {
            case OUDER:
                return BrpDocumentInhoud.EXTRA_DOCUMENT_OUDER_OMSCHRIJVING + extraDocumentInformatie;
            case HUWELIJK:
                return BrpDocumentInhoud.EXTRA_DOCUMENT_VERBINTENIS_OMSCHRIJVING + extraDocumentInformatie;
            default:
                throw new IllegalStateException("Onbekend type extra document: " + extraDocument);
        }
    }

    /**
     * 86.10 Datum van opneming. Hieraan moet een tijdstip toegvoegd worden; hiervoor wordt 1 uur 's nachts gebruikt.
     * Als dezelfde combinatie van Datum aanvang geldigheid (null equals null in dit geval) en datumtijd registratie al
     * voorkomt binnen de BRP groepen dan wordt het tijdstip net zolang opgehoogd met 1 minuut tot wel een unieke
     * combinatie ontstaat.
     * 
     * @param <T>
     *            groep inhoud type
     * @param aanvangGeldigheid
     *            datum aanvang geldigheid voor de controle (mag null zijn)
     * @param lo3Groep
     *            huidige groep (waarvan 86.10 datum van opneming genomen wordt)
     * @param brpGroepen
     *            brp groepen
     * 
     * @return datumtijd regstratie
     */
    protected static final <T extends BrpGroepInhoud> BrpDatumTijd bepaalDatumTijdRegistratie(
            final BrpDatum aanvangGeldigheid,
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {
        final BrpDatumTijd datumTijdRegistratie =
                BrpDatumTijd.fromDatum(lo3Groep.getHistorie().getDatumVanOpneming().getDatum());

        return bepaalDatumTijdRegistratie(aanvangGeldigheid, datumTijdRegistratie, brpGroepen);
    }

    /**
     * Bepaal de unieke datumtijd registratie. De initiele datumtijd registratie wordt steeds opgehoogd met 1 minuut als
     * deze als bestaat in de lijst van brp groepen.
     * 
     * @param <T>
     *            groep inhoud type
     * @param aanvangGeldigheid
     *            datum aanvang geldigheid voor de controle (mag null zijn)
     * @param datumTijdRegistratie
     *            initiele datumtijd registratie
     * @param brpGroepen
     *            brp groepen
     * @return unieke datum tijd registratie
     */
    protected static final <T extends BrpGroepInhoud> BrpDatumTijd bepaalDatumTijdRegistratie(
            final BrpDatum aanvangGeldigheid,
            final BrpDatumTijd datumTijdRegistratie,
            final List<BrpGroep<T>> brpGroepen) {

        BrpDatumTijd datumTijd = datumTijdRegistratie;

        while (bestaatAanvangRegistratieCombinatie(aanvangGeldigheid, datumTijd, brpGroepen)) {
            datumTijd = BrpDatumTijd.fromDatumTijdMillis(datumTijd.getDatumTijdMillis() + DATUMTIJDMILLIS_EEN_MINUUT);
        }

        return datumTijd;

    }

    private static boolean bestaatAanvangRegistratieCombinatie(
            final BrpDatum aanvangGeldigheid,
            final BrpDatumTijd datumTijdRegistratie,
            final List<? extends BrpGroep<?>> brpGroepen) {
        for (final BrpGroep<?> brpGroep : brpGroepen) {
            final BrpHistorie historie = brpGroep.getHistorie();

            if (equals(aanvangGeldigheid, historie.getDatumAanvangGeldigheid())
                    && datumTijdRegistratie.equals(historie.getDatumTijdRegistratie())) {
                return true;
            }

        }

        return false;
    }

    private static boolean equals(final BrpDatum datum1, final BrpDatum datum2) {
        return datum1 == null && datum2 == null || datum1 != null && datum1.equals(datum2);

    }

}
