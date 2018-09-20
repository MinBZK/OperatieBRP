/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeprivilegieerdeIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;

/**
 * Validator voor de BRP persoonslijst.
 * 
 */
public final class BrpPersoonslijstValidator {

    private BrpPersoonslijstValidator() {
        throw new AssertionError("BrpPersoonslijstValidator mag niet worden geïnstantieerd");
    }

    /**
     * Valideer de inhoud voor ieder van de meegegeven stapels.
     * 
     * @param stapels
     *            de lijst van de te valideren stapels
     */
    protected static void valideerInhoud(final List<? extends BrpStapel<?>> stapels) {
        for (final BrpStapel<?> stapel : stapels) {
            valideerInhoud(stapel);
        }
    }

    /**
     * Valideer de inhoud van de meegegeven stapel.
     * 
     * @param stapel
     *            de te valideren stapel
     */
    protected static void valideerInhoud(final BrpStapel<?> stapel) {
        if (stapel != null) {
            stapel.valideer();
        }
    }

    /**
     * Valideer de geprivilegieerde indicatie tegen de nationaliteiten.
     * 
     * @param geprivilegieerdeIndicatieStapel
     *            de te valideren geprivilegieerde stapel, of null
     * @param nationaliteitStapels
     *            de nationaliteitstapels
     */
    @Preconditie(Precondities.PRE017)
    public static void valideerGeprivilegieerde(
            final BrpStapel<BrpGeprivilegieerdeIndicatieInhoud> geprivilegieerdeIndicatieStapel,
            final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        if (geprivilegieerdeIndicatieStapel != null && geprivilegieerdeIndicatieStapel.bevatActueel()) {
            for (final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
                if (nationaliteitStapel.bevatActueel()) {
                    return;
                }
            }
            FoutmeldingUtil.gooiValidatieExceptie(
                    "Er is een actuele geprivilegieerde indicatie, maar geen actuele nationaliteit",
                    Precondities.PRE017);
        }

    }

    /**
     * Valideer dat is ieder van de meegegeven stapels een actuele groep voorkomt. Omdat binnen ieder van deze groepen
     * de geslachtsnaam verplicht is, wordt gevalideerd dat er een actuele geslachtsnaam is.
     * 
     * @param geslachtsnaamcomponentStapels
     *            de te valideren geslachtsnaamcomponent stapels
     * @param aanschrijvingStapel
     *            de te valideren aanschrijving stapel
     * @param samengesteldeNaamStapel
     *            de te valideren samengestelde naam stapel
     */
    @Preconditie(Precondities.PRE043)
    public static void valideerActueleGeslachtsnaam(
            final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> geslachtsnaamcomponentStapels,
            final BrpStapel<BrpAanschrijvingInhoud> aanschrijvingStapel,
            final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {
        // boolean nietActueleStapel = false;
        // for (final BrpStapel<BrpGeslachtsnaamcomponentInhoud> brpStapel : geslachtsnaamcomponentStapels) {
        // if (!brpStapel.bevatActueel()) {
        // nietActueleStapel = true;
        // break;
        // }
        // }
        // if (nietActueleStapel) {
        // FoutmeldingUtil.gooiValidatieExceptie("Geslachtsnaamcomponent: Geen actuele groep gevonden",
        // Precondities.PRE043);
        // }
        // if (aanschrijvingStapel == null || !aanschrijvingStapel.bevatActueel()) {
        // FoutmeldingUtil.gooiValidatieExceptie("Aanschrijving: Geen actuele groep gevonden", Precondities.PRE043);
        // }
        // if (samengesteldeNaamStapel == null || !samengesteldeNaamStapel.bevatActueel()) {
        // FoutmeldingUtil.gooiValidatieExceptie("Samengestelde naam: Geen actuele groep gevonden",
        // Precondities.PRE043);
        // }

    }

    /**
     * Valideer dat de indicaties 'Behandeld als Nederlander' en 'Vastgesteld niet Nederlander' niet gelijktijdig geldig
     * zijn.
     * 
     * @param behandeldAlsNederlanderIndicatieStapel
     *            de te valideren 'Behandeld als Nederlander' stapel
     * @param vastgesteldNietNederlanderIndicatieStapel
     *            de te valideren 'Vastgesteld niet Nederlander' stapel
     */
    @Preconditie(Precondities.PRE058)
    public static void valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
            final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
            final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel) {
        if (behandeldAlsNederlanderIndicatieStapel == null || vastgesteldNietNederlanderIndicatieStapel == null) {
            return;
        }
        final List<BrpHistorie> geldigheidBehandeld = bepaalGeldigheid(behandeldAlsNederlanderIndicatieStapel);
        final List<BrpHistorie> geldigheidVastgesteld = bepaalGeldigheid(vastgesteldNietNederlanderIndicatieStapel);
        for (final BrpHistorie behandeldHistorie : geldigheidBehandeld) {
            for (final BrpHistorie vastgesteldHistorie : geldigheidVastgesteld) {
                if (behandeldHistorie.geldigheidOverlapt(vastgesteldHistorie)
                        && !(behandeldHistorie.isVervallen() || vastgesteldHistorie.isVervallen())) {
                    // als de geldigheid elkaar overlapt, en er is niet tenminste 1 van de groepen vervallen, zijn de
                    // groepen dus gelijktijdig geldig
                    FoutmeldingUtil.gooiValidatieExceptie(
                            "'Behandeld als Nederlander' en 'Vastgesteld niet Nederlander' zijn gelijktijdig geldig",
                            Precondities.PRE058);
                }
            }

        }
    }

    private static List<BrpHistorie> bepaalGeldigheid(
            final BrpStapel<? extends BrpIndicatieGroepInhoud> indicatieStapel) {
        final List<BrpHistorie> geldigheid = new ArrayList<BrpHistorie>();
        for (final BrpGroep<? extends BrpIndicatieGroepInhoud> brpGroep : indicatieStapel) {
            if (brpGroep.getInhoud().getHeeftIndicatie()) {
                geldigheid.add(brpGroep.getHistorie());
            }
        }
        return geldigheid;
    }
}
