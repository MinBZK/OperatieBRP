/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.FoutmeldingUtil;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

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
     * Valideer de inhoud voor iedere van de meegegeven istStapels.
     * 
     * @param stapels
     *            de lijst van de te valideren IST-stapels
     */
    protected static void valideerIstInhoud(final List<? extends BrpStapel<? extends AbstractBrpIstGroepInhoud>> stapels) {
        if (stapels == null) {
            return;
        }
        valideerInhoud(stapels);
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
     * Valideer dat als het gaat om geprivilegieerde er ook een actuele nationaliteit is.
     * 
     * @param bijzondereVerblijfsrechtelijkePositieIndicatieStapel
     *            de te valideren bijzondereVerblijfsrechtelijkePositieIndicatie stapel, of null
     * @param nationaliteitStapels
     *            de nationaliteitstapels
     */
    @Preconditie(SoortMeldingCode.PRE017)
    public static void valideerGeprivilegieerde(
        final BrpStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud> bijzondereVerblijfsrechtelijkePositieIndicatieStapel,
        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels)
    {
        if (bijzondereVerblijfsrechtelijkePositieIndicatieStapel != null && bijzondereVerblijfsrechtelijkePositieIndicatieStapel.bevatActueel()) {
            for (final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
                if (nationaliteitStapel.bevatActueel()) {
                    return;
                }
            }
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE017, bijzondereVerblijfsrechtelijkePositieIndicatieStapel.getActueel().getInhoud());
        }
    }

    /**
     * Valideert dat er altijd een actuele geslachtsnaam is in de samengesteldenaam stapel.
     * 
     * @param samengesteldeNaamStapel
     *            de te valideren samengestelde naam stapel
     */
    @Preconditie(SoortMeldingCode.PRE043)
    public static void valideerActueleGeslachtsnaam(final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {
        if (samengesteldeNaamStapel == null || !samengesteldeNaamStapel.bevatActueel()) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE043, BrpSamengesteldeNaamInhoud.class);
        }
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
    @Preconditie(SoortMeldingCode.PRE058)
    public static void valideerBehandeldAlsNederlanderVastgesteldNietNederlander(
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> behandeldAlsNederlanderIndicatieStapel,
        final BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud> vastgesteldNietNederlanderIndicatieStapel)
    {
        if (behandeldAlsNederlanderIndicatieStapel == null || vastgesteldNietNederlanderIndicatieStapel == null) {
            return;
        }
        final List<BrpHistorie> geldigheidBehandeld = bepaalGeldigheid(behandeldAlsNederlanderIndicatieStapel);
        final List<BrpHistorie> geldigheidVastgesteld = bepaalGeldigheid(vastgesteldNietNederlanderIndicatieStapel);
        for (final BrpHistorie behandeldHistorie : geldigheidBehandeld) {
            for (final BrpHistorie vastgesteldHistorie : geldigheidVastgesteld) {
                if (behandeldHistorie.geldigheidOverlapt(vastgesteldHistorie) && !(behandeldHistorie.isVervallen() || vastgesteldHistorie.isVervallen())) {
                    // als de geldigheid elkaar overlapt, en er is niet tenminste 1 van de groepen vervallen, zijn de
                    // groepen dus gelijktijdig geldig
                    FoutmeldingUtil.gooiValidatieExceptie(
                        SoortMeldingCode.PRE058,
                        behandeldAlsNederlanderIndicatieStapel.getActueel().getInhoud(),
                        vastgesteldNietNederlanderIndicatieStapel.getActueel().getInhoud());
                }
            }

        }
    }

    private static List<BrpHistorie> bepaalGeldigheid(final BrpStapel<? extends AbstractBrpIndicatieGroepInhoud> indicatieStapel) {
        final List<BrpHistorie> geldigheid = new ArrayList<>();
        for (final BrpGroep<? extends AbstractBrpIndicatieGroepInhoud> brpGroep : indicatieStapel) {
            if (brpGroep.getInhoud().heeftIndicatie()) {
                geldigheid.add(brpGroep.getHistorie());
            }
        }
        return geldigheid;
    }
}
