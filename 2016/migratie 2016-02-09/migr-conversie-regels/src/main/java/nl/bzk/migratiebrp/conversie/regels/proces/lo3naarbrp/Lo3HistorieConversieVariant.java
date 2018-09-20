/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.UniqueSequence;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.ConverteerderUtils;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.comparators.VariantPre050Pre056GroepenComparator;

/**
 * LO3 Historie -> BRP Historie conversie.
 *
 */
@SuppressWarnings("checkstyle:designforextension")
public abstract class Lo3HistorieConversieVariant {
    /**
     * Migratie groepen sorteer comparator alle historie conversie varianten.
     */
    private static final Comparator<TussenGroep<?>> GROEPEN_SORTEER_COMPARATOR = new GroepenSorteerComparator();

    /**
     * Alternatieve migratie groepen sorteer comparator alle historie conversie varianten.
     */
    private static final Comparator<TussenGroep<?>> ALTERNATIEVE_SORTEER_COMPARATOR = new VariantPre050Pre056GroepenComparator();

    private static final long DATUMTIJDMILLIS_EEN_MINUUT = 100000;

    @Inject
    private ConverteerderUtils utils;
    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Als de datum/tijd registratie al voorkomt binnen de BRP groepen dan wordt het tijdstip net zolang opgehoogd met 1
     * minuut tot wel een unieke combinatie ontstaat.
     *
     * @param <T>
     *            groep inhoud type
     * @param lo3Groep
     *            huidige groep (waarvan 86.10 datum van opneming genomen wordt)
     * @param brpGroepen
     *            brp groepen
     *
     * @return unieke datum/tijd registratie
     */
    protected static <T extends BrpGroepInhoud> BrpDatumTijd bepaalDatumTijdRegistratie(
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen)
    {
        return bepaalDatumTijdRegistratie(null, lo3Groep, brpGroepen);
    }

    /**
     * Als dezelfde combinatie van Datum aanvang geldigheid en datum/tijd registratie al voorkomt binnen de BRP groepen
     * dan wordt het tijdstip net zolang opgehoogd met 1 minuut tot wel een unieke combinatie ontstaat.
     *
     * @param <T>
     *            groep inhoud type
     * @param aanvangGeldigheid
     *            datum aanvang geldigheid voor de controle
     * @param lo3Groep
     *            huidige groep (waarvan 86.10 datum van opneming genomen wordt)
     * @param brpGroepen
     *            brp groepen
     *
     * @return unieke datum/tijd registratie
     */
    protected static <T extends BrpGroepInhoud> BrpDatumTijd bepaalDatumTijdRegistratie(
        final BrpDatum aanvangGeldigheid,
        final TussenGroep<T> lo3Groep,
        final List<BrpGroep<T>> brpGroepen)
    {
        final BrpDatumTijd datumTijdRegistratie = BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming());
        return updateDatumTijdRegistratie(aanvangGeldigheid, datumTijdRegistratie, brpGroepen);
    }

    /**
     * Als dezelfde combinatie van Datum aanvang geldigheid en datum/tijd registratie al voorkomt binnen de BRP groepen
     * dan wordt het tijdstip net zolang opgehoogd met 1 minuut tot wel een unieke combinatie ontstaat.
     *
     * @param <T>
     *            groep inhoud type
     * @param aanvangGeldigheid
     *            datum aanvang geldigheid voor de controle (mag null zijn)
     * @param datumTijdRegistratie
     *            initiele datumtijd registratie
     * @param brpGroepen
     *            brp groepen
     * @return unieke datum/tijd registratie
     */
    protected static <T extends BrpGroepInhoud> BrpDatumTijd updateDatumTijdRegistratie(
        final BrpDatum aanvangGeldigheid,
        final BrpDatumTijd datumTijdRegistratie,
        final List<BrpGroep<T>> brpGroepen)
    {

        BrpDatumTijd datumTijd = datumTijdRegistratie;

        while (bestaatAanvangRegistratieCombinatie(aanvangGeldigheid, datumTijd, brpGroepen)) {
            datumTijd = BrpDatumTijd.fromDatumTijdMillis(datumTijd.getWaarde() + DATUMTIJDMILLIS_EEN_MINUUT, datumTijd.getOnderzoek());
        }
        return datumTijd;
    }

    private static boolean bestaatAanvangRegistratieCombinatie(
        final BrpDatum aanvangGeldigheid,
        final BrpDatumTijd datumTijdRegistratie,
        final List<? extends BrpGroep<?>> brpGroepen)
    {
        for (final BrpGroep<?> brpGroep : brpGroepen) {
            final BrpHistorie historie = brpGroep.getHistorie();

            if (AbstractBrpAttribuutMetOnderzoek.equalsWaarde(aanvangGeldigheid, historie.getDatumAanvangGeldigheid())
                && AbstractBrpAttribuutMetOnderzoek.equalsWaarde(datumTijdRegistratie, historie.getDatumTijdRegistratie()))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Converteer de migratie groepen (BRP inhoud, LO3 historie) naar BRP groepen (BRP inhoud, BRP historie), met de
     * standaard comparator voor de conversie variant.
     *
     * @param <T>
     *            groep inhoud type
     * @param lo3Groepen
     *            lijst van migratie groepen
     * @param actieCache
     *            De cache voor al aangemaakte acties
     * @return lijst van brp groepen
     */
    public final <T extends BrpGroepInhoud> List<BrpGroep<T>> converteer(final List<TussenGroep<T>> lo3Groepen, final Map<Long, BrpActie> actieCache) {
        Collections.sort(lo3Groepen, GROEPEN_SORTEER_COMPARATOR);

        return converteerGesorteerdeGroepen(lo3Groepen, actieCache);
    }

    /**
     * Converteer de migratie groepen (BRP inhoud, LO3 historie) naar BRP groepen (BRP inhoud, BRP historie), gesorteerd
     * volgens de alternatieve historie volgorde.
     *
     * @param <T>
     *            groep inhoud type
     * @param lo3Groepen
     *            lijst van migratie groepen
     * @param actieCache
     *            De cache voor al aangemaakte acties
     * @return lijst van brp groepen
     */
    public final <T extends BrpGroepInhoud> List<BrpGroep<T>> converteerAlternatief(
        final List<TussenGroep<T>> lo3Groepen,
        final Map<Long, BrpActie> actieCache)
    {
        Collections.sort(lo3Groepen, ALTERNATIEVE_SORTEER_COMPARATOR);

        // Forceer dat de meest actuele Lo3 groep (laagste voorkomen) als laatste in de gesorteerde lijst staat.
        final int actueelIndex = bepaalActueleGroepIndex(lo3Groepen);
        final TussenGroep<T> actueel = lo3Groepen.remove(actueelIndex);
        lo3Groepen.add(actueel);

        return converteerGesorteerdeGroepen(lo3Groepen, actieCache);
    }

    private <T extends BrpGroepInhoud> List<BrpGroep<T>> converteerGesorteerdeGroepen(
        final List<TussenGroep<T>> lo3Groepen,
        final Map<Long, BrpActie> actieCache)
    {
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();
        final List<TussenGroep<T>> tussenGroepen = new ArrayList<>();

        // Voeg de conversie sortering informatie toe aan de Lo3 groepen
        int conversieSorteerIndex = 1;
        for (final TussenGroep<T> lo3Groep : lo3Groepen) {
            final Lo3Herkomst lo3Herkomst = lo3Groep.getLo3Herkomst();
            final TussenGroep<T> gesorteerdeGroep =
                    new TussenGroep<>(
                        lo3Groep.getInhoud(),
                        lo3Groep.getHistorie(),
                        lo3Groep.getDocumentatie(),
                        new Lo3Herkomst(lo3Herkomst.getCategorie(), lo3Herkomst.getStapel(), lo3Herkomst.getVoorkomen(), conversieSorteerIndex),
                        lo3Groep.isAfsluitendeGroep(),
                        lo3Groep.isOorsprongVoorkomenLeeg());
            conversieSorteerIndex += 1;
            tussenGroepen.add(gesorteerdeGroep);
        }

        // Voer de conversie naar Brp groepen uit
        for (final TussenGroep<T> tussenGroep : tussenGroepen) {
            final BrpGroep<T> brpGroep = converteerLo3Groep(tussenGroep, tussenGroepen, brpGroepen, actieCache);
            if (brpGroep != null) {
                brpGroepen.add(brpGroep);
            }
        }

        return doeNabewerking(brpGroepen);
    }

    /**
     * Converteer een enkele migratie groep (BRP inhoud, LO3 historie) naar een BRP groep (BRP inhoud, BRP historie).
     *
     *
     * @param lo3Groep
     *            de te converteren LO3 groep
     * @param lo3Groepen
     *            lijst van migratie groepen
     * @param brpGroepen
     *            lijst van al aangemaakte BRP groepen
     * @param actieCache
     *            De cache voor al aangemaakte acties
     * @param <T>
     *            groep inhoud type
     * @return de nieuwe BRP groep, of null als de LO3 wordt overgeslagen
     */
    protected abstract <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
        TussenGroep<T> lo3Groep,
        List<TussenGroep<T>> lo3Groepen,
        List<BrpGroep<T>> brpGroepen,
        Map<Long, BrpActie> actieCache);

    /**
     * Doe benodigde na-bewerking van de BRP groepen (BRP inhoud, BRP historie).
     *
     *
     * @param brpGroepen
     *            de BRP groepen
     * @param <T>
     *            groep inhoud type
     * @return de na-bewerkte BRP groepen
     */
    protected abstract <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> brpGroepen);

    /**
     * Maak een actie obv de gegeven LO3 documentatie en historie.
     *
     *
     * @param documentatie
     *            LO3 documentatie
     * @param historie
     *            LO3 historie
     * @param lo3Herkomst
     *            LO3 herkomst
     * @param actieCache
     *            De cache voor al aangemaakte acties
     * @return BRP actie
     */
    @Requirement(Requirements.CBA001_LB01)
    @Definitie({Definities.DEF042, Definities.DEF043, Definities.DEF044, Definities.DEF081, Definities.DEF082 })
    final BrpActie maakActie(
        final Lo3Documentatie documentatie,
        final Lo3Historie historie,
        final Lo3Herkomst lo3Herkomst,
        final Map<Long, BrpActie> actieCache)
    {
        return maakActie(documentatie, historie, lo3Herkomst, actieCache, BrpSoortActieCode.CONVERSIE_GBA);
    }

    /**
     * Maak een actie obv de gegeven basis actie.
     *
     *
     * @param basisActie
     *            De basis actie
     * @param actieCache
     *            De cache voor al aangemaakte acties
     * @param soortActie
     *            de soort actie
     * @return BRP actie
     */
    @Requirement(Requirements.CBA001_LB01)
    @Definitie({Definities.DEF042, Definities.DEF043, Definities.DEF044 })
    final BrpActie maakActie(final BrpActie basisActie, final Map<Long, BrpActie> actieCache, final BrpSoortActieCode soortActie) {
        final BrpActie actie;
        final BrpActie cacheActie = actieCache.get(basisActie.getId());
        if (BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(soortActie)
            && !BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(cacheActie.getSoortActieCode()))
        {
            actie = new BrpActie.Builder(cacheActie).soortActieCode(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE).build();
            actieCache.put(basisActie.getId(), actie);
        } else {
            actie = basisActie;
        }

        return actie;
    }

    /**
     * Maak een actie obv de gegeven LO3 documentatie en historie.
     *
     * @param documentatie
     *            LO3 documentatie
     * @param historie
     *            LO3 historie
     * @param lo3Herkomst
     *            LO3 herkomst
     * @param actieCache
     *            De cache voor al aangemaakte acties
     * @param soortActie
     *            de soort actie
     * @return BRP actie
     */
    @Requirement(Requirements.CBA001_LB01)
    @Definitie({Definities.DEF042, Definities.DEF043, Definities.DEF044, Definities.DEF081, Definities.DEF082 })
    final BrpActie maakActie(
        final Lo3Documentatie documentatie,
        final Lo3Historie historie,
        final Lo3Herkomst lo3Herkomst,
        final Map<Long, BrpActie> actieCache,
        final BrpSoortActieCode soortActie)
    {

        final BrpActie actie;
        final BrpDatumTijd datumTijdOpneming = BrpDatumTijd.fromLo3Datum(historie.getDatumVanOpneming());
        final BrpHistorie brpHistorie = new BrpHistorie(null, null, datumTijdOpneming, null, null);

        final boolean isDocument = documentatie != null && documentatie.isDocument();
        final boolean isAkte = documentatie != null && documentatie.isAkte();
        final boolean isRniDeelnemer = documentatie != null && documentatie.isRniDeelnemer();
        final boolean isUitCategorie07 = Lo3CategorieEnum.CATEGORIE_07.equals(lo3Herkomst.getCategorie());

        final List<BrpActieBron> actieBronnen = new ArrayList<>();
        final BrpPartijCode partij;

        if (isRniDeelnemer && !isUitCategorie07) {
            partij = converteerder.converteerRNIDeelnemer(documentatie.getRniDeelnemerCode());
            if (documentatie.getOmschrijvingVerdrag() != null) {
                actieBronnen.add(new BrpActieBron(null, converteerder.converteerString(documentatie.getOmschrijvingVerdrag())));
            }
        } else {
            partij = BrpPartijCode.MIGRATIEVOORZIENING;
        }

        if (isDocument || isAkte) {
            final long actieId = documentatie.getId();

            if (actieCache.containsKey(actieId)) {
                final BrpActie cacheActie = actieCache.get(actieId);
                if (BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(soortActie)
                    && !BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE.equals(cacheActie.getSoortActieCode()))
                {
                    actie = new BrpActie.Builder(cacheActie).soortActieCode(BrpSoortActieCode.CONVERSIE_GBA_MATERIELE_HISTORIE).build();
                } else {
                    actie = cacheActie;
                }
            } else {
                final BrpDocumentInhoud documentInhoud = utils.maakDocumentInhoud(documentatie);

                // Actie inhoud wordt gezet tijdens de creatie van de BrpActie
                final BrpGroep<BrpDocumentInhoud> brpDocument = new BrpGroep<>(documentInhoud, brpHistorie, null, null, null);

                final BrpStapel<BrpDocumentInhoud> documentStapel = new BrpStapel<>(Collections.singletonList(brpDocument));

                actieBronnen.add(new BrpActieBron(documentStapel, null));

                final BrpDatum datumOntlening = isAkte ? null : BrpDatum.fromLo3Datum(documentatie.getDatumDocument());

                actie = new BrpActie(actieId, soortActie, partij, datumTijdOpneming, datumOntlening, actieBronnen, 0, lo3Herkomst);
            }
        } else {
            // DEF044 Er is geen brondocument en geen akte
            final Long documentatieId = documentatie == null ? UniqueSequence.next() : documentatie.getId();
            actie =
                    new BrpActie(
                        documentatieId,
                        soortActie,
                        partij,
                        datumTijdOpneming,
                        null,
                        actieBronnen.isEmpty() ? null : actieBronnen,
                        0,
                        lo3Herkomst);
        }

        actieCache.put(actie.getId(), actie);
        return actie;
    }

    /**
     * Bepaal de volgende groep juiste groep. Met name gebruikt om de groep te vinden waarmee de huidige groep wordt
     * beeindigd. Alleen als de huidige groep de actuele groep is dan zal hier null uit komen. De huidige sortering van
     * de lijst LO3 groepen wordt gebruikt.
     *
     * @param huidigeLo3Groep
     *            De huidige groep
     * @param lo3Groepen
     *            De gesorteerde lijst met groepen
     * @param <T>
     *            groep inhoud type
     * @return De gevonden volgende groep, of null als de huidige groep de actuele groep is
     */
    protected final <T extends BrpGroepInhoud> TussenGroep<T> bepaalVolgendeJuisteGroep(
        final TussenGroep<T> huidigeLo3Groep,
        final List<TussenGroep<T>> lo3Groepen)
    {

        TussenGroep<T> resultaat;

        if (isActueleGroep(huidigeLo3Groep, lo3Groepen)) {
            resultaat = null;
        } else {
            final Lo3Datum huidigeIngangsdatumGeldigheid = huidigeLo3Groep.getHistorie().getIngangsdatumGeldigheid();

            final List<TussenGroep<T>> zelfdeIngangGeldigheid = filterJuistEnIngangGeldigheid(huidigeIngangsdatumGeldigheid, lo3Groepen);
            resultaat = bepaalVolgdendeGroep(huidigeLo3Groep, zelfdeIngangGeldigheid);

            if (resultaat == null) {
                resultaat = bepaalEerstvolgendeJuisteGroep(huidigeIngangsdatumGeldigheid, lo3Groepen);
            }

            if (resultaat == null) {
                resultaat = lo3Groepen.get(bepaalActueleGroepIndex(lo3Groepen));
                // Als de gevonden tussen groep onjuist is, dan kan dit nooit het gewenste resultaat zijn.
                resultaat = resultaat.getHistorie().isOnjuist() ? null : resultaat;
            }
        }

        return resultaat;
    }

    /**
     * Bepaal de actuele groep.
     *
     * @param lo3Groepen
     *            De gesorteerde lijst met groepen
     * @param <T>
     *            groep inhoud type
     * @return De gevonden volgende groep, of null als de huidige groep de actuele groep is
     */
    protected final <T extends BrpGroepInhoud> TussenGroep<T> bepaalActueleGroep(final List<TussenGroep<T>> lo3Groepen) {

        final TussenGroep<T> resultaat;

        final int index = bepaalActueleGroepIndex(lo3Groepen);
        if (index >= 0) {
            resultaat = lo3Groepen.get(index);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Bepaal of de huidige groep de actuele groep is met betrekking tot een lijst groepen door het voorkomen uit de Lo3
     * herkomst te vergelijken. De gegeven groep is actueel als het voorkomen van die groep lager is of gelijk aan het
     * voorkomen van alle groepen in de lijst LO3 groepen.
     *
     * @param huidigeLo3Groep
     *            De groep waarvan bepaald moet worden of deze actueel is binnen de gegeven lijst groepen.
     * @param lo3Groepen
     *            De volledige lijst groepen.
     * @param <T> extends BrpGroepInhoud
     * @return boolean true als actueel
     *
     */
    protected <T extends BrpGroepInhoud> boolean isActueleGroep(
        /*
         * Not designed for extension - Er is een aangepaste versie nodig voor sommige specifieke Brp groepen, waarvoor
         * deze algemene implementatie uitgebreid moet worden.
         */
        final TussenGroep<T> huidigeLo3Groep, final List<TussenGroep<T>> lo3Groepen)
    {
        for (final TussenGroep<?> tussenGroep : lo3Groepen) {
            if (huidigeLo3Groep.getLo3Herkomst().getVoorkomen() > tussenGroep.getLo3Herkomst().getVoorkomen()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Filter de lijst LO3 groepen tot alleen juiste groepen met de gegeven ingangsdatum geldigheid. De relatieve
     * volgorde blijft bewaard.
     */
    private <T extends BrpGroepInhoud> List<TussenGroep<T>> filterJuistEnIngangGeldigheid(
        final Lo3Datum huidigeIngangsdatumGeldigheid,
        final List<TussenGroep<T>> lo3Groepen)
    {
        final List<TussenGroep<T>> resultaat = new ArrayList<>();
        for (final TussenGroep<T> tussenGroep : lo3Groepen) {
            if (!tussenGroep.getHistorie().isOnjuist()
                && AbstractLo3Element.equalsWaarde(tussenGroep.getHistorie().getIngangsdatumGeldigheid(), huidigeIngangsdatumGeldigheid))
            {
                resultaat.add(tussenGroep);
            }
        }
        return resultaat;
    }

    /**
     * Bepaal of er een volgende groep bestaat na de huidige groep in de lijst.
     */
    private <T extends BrpGroepInhoud> TussenGroep<T> bepaalVolgdendeGroep(final TussenGroep<T> huidigeLo3Groep, final List<TussenGroep<T>> lo3Groepen) {
        int huidigeIndex = -1;
        for (int i = 0; i < lo3Groepen.size(); i++) {
            if (lo3Groepen.get(i) == huidigeLo3Groep) {
                huidigeIndex = i;
            }
        }

        return huidigeIndex >= 0 && huidigeIndex + 1 < lo3Groepen.size() ? lo3Groepen.get(huidigeIndex + 1) : null;
    }

    /**
     * Bepaal de eerstvolgdende juiste groep met een grotere ingangsdatum geldigheid.
     */
    private <T extends BrpGroepInhoud> TussenGroep<T> bepaalEerstvolgendeJuisteGroep(
        final Lo3Datum huidigeIngangsdatumGeldigheid,
        final List<TussenGroep<T>> lo3Groepen)
    {

        TussenGroep<T> resultaat = null;

        for (final TussenGroep<T> lo3Groep : lo3Groepen) {
            if (lo3Groep.getHistorie().isOnjuist()) {
                continue;
            }

            final Lo3Datum lo3RijGeldigheid = lo3Groep.getHistorie().getIngangsdatumGeldigheid();

            if (lo3RijGeldigheid.compareTo(huidigeIngangsdatumGeldigheid) > 0
                && (resultaat == null || resultaat.getHistorie().getIngangsdatumGeldigheid().compareTo(lo3RijGeldigheid) > 0))
            {
                resultaat = lo3Groep;
            }
        }

        return resultaat;
    }

    /**
     * Bepaal de actuele groep van de lijst LO3 groepen. Dit gebeurd door de groep met het laagste voorkomen in de Lo3
     * herkomst te selecteren.
     */
    private <T extends BrpGroepInhoud> int bepaalActueleGroepIndex(final List<TussenGroep<T>> lo3Groepen) {
        for (int index = 0; index < lo3Groepen.size(); index++) {
            if (isActueleGroep(lo3Groepen.get(index), lo3Groepen)) {
                return index;
            }
        }
        // Kan hier alleen komen als de lijst groepen leeg is.
        return -1;
    }

    /**
     * Maak de NadereAanduidingVerval waarde op basis van de gegeven historie.
     *
     * @param lo3Historie
     *            de historie
     * @return de NadereAanduidingVerval waarde
     */
    protected final BrpCharacter maakNadereAanduidingVerval(final Lo3Historie lo3Historie) {
        final Lo3IndicatieOnjuist indicatieOnjuist = lo3Historie.getIndicatieOnjuist();

        if (indicatieOnjuist == null) {
            return null;
        } else {
            final boolean isOnjuist = indicatieOnjuist.isInhoudelijkGevuld();
            final Character waarde = isOnjuist ? 'O' : null;
            final Lo3Onderzoek onderzoek = indicatieOnjuist.getOnderzoek();
            return BrpCharacter.wrap(waarde, onderzoek);
        }
    }

    /**
     * Vind 'Lo3' tussengroep op basis van herkomst.
     *
     * @param lo3Groepen
     *            De lijst van Lo3 groepen.
     * @param herkomst
     *            De herkomst waarop wordt gezocht.
     * @param <T>
     *            De BrpgroepInhoud.
     * @return De gevonden groep of null.
     */
    protected final <T extends BrpGroepInhoud> TussenGroep<T> vindLo3GroepBijHerkomst(final List<TussenGroep<T>> lo3Groepen, final Lo3Herkomst herkomst) {
        for (final TussenGroep<T> lo3Groep : lo3Groepen) {
            if (lo3Groep.getLo3Herkomst().equals(herkomst)) {
                return lo3Groep;
            }
        }
        return null;
    }

    /**
     * Sorteer de migratie groepen.
     *
     * Sorteer alle LO3-rijen (juist en onjuist) op basis van 86.10 datum van opneming en daarbinnen op 85.10
     * Ingangsdatum geldigheid. Deze worden doorlopen van oud naar nieuw. Als er meerdere rijen zijn die deze zelfde
     * 86.10 Datum van opneming en 85.10 Ingangsdatum geldigheid hebben, dan moeten eerst de onjuiste rijen doorlopen
     * worden, en daarna de juiste rijen. Zijn er vervolgens nog rijen waarbij zowel 86.10, 85.10 gelijk is en ze zijn
     * beiden juist dan wel onjuist, dan wordt de relatieve volgorde (van onder naar boven uit de aangeleverde
     * PL/bericht) van deze rijen gehanteerd.
     */
    private static class GroepenSorteerComparator implements Comparator<TussenGroep<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final TussenGroep<?> arg0, final TussenGroep<?> arg1) {
            int resultaat;

            // Sorteer op 86.10 datum van opneming van oud naar nieuw
            resultaat = arg0.getHistorie().getDatumVanOpneming().compareTo(arg1.getHistorie().getDatumVanOpneming());

            // Sorteer op 85.10 ingangsdatum geldigheid van oud naar nieuw
            if (resultaat == 0) {
                if (arg0.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                    resultaat = 1;
                } else {
                    resultaat = arg0.getHistorie().getIngangsdatumGeldigheid().compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());
                }
            }

            // sorteer onjuist voor juist
            if (resultaat == 0) {
                final boolean onjuist0 = arg0.getHistorie().isOnjuist();
                final boolean onjuist1 = arg1.getHistorie().isOnjuist();

                if (onjuist0 && !onjuist1) {
                    resultaat = -1;
                } else if (!onjuist0 && onjuist1) {
                    resultaat = 1;
                }
            }

            // sorteer op lo3 volgorde, hoger voorkomen nummer betekend ouder, van oud naar nieuw
            if (resultaat == 0) {
                resultaat = arg1.getLo3Herkomst().getVoorkomen() - arg0.getLo3Herkomst().getVoorkomen();
            }

            return resultaat;
        }
    }
}
