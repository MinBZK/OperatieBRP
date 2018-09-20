/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.lo3.conversie.OnderzoekUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Format;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpOnderzoekLo3;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * LO3 Wijzigingen.
 *
 * @param <L>
 *            lo3 categorie inhoud type
 */
public class Lo3Wijzigingen<L extends Lo3CategorieInhoud> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final BrpOnderzoekLo3 ONDERZOEK_VERWERKER = new BrpOnderzoekLo3();

    private Lo3Categorie<L> actueleInhoud;
    private Lo3Onderzoek actueleOnderzoek;
    private Lo3Categorie<L> historischeInhoud;
    private Lo3Onderzoek historischeOnderzoek;

    private final Lo3CategorieFormatter<L> formatter;

    private final Lo3CategorieWaarde actueleCategorie;
    private final Lo3CategorieWaarde historischeCategorie;

    private ActieModel laatsteActie;
    private Lo3Categorie<L> laatsteInhoud;
    private LaatsteActieType laatsteActieType;

    /**
     * Constructor.
     *
     * @param categorie
     *            categorie
     * @param formatter
     *            formatter
     */
    public Lo3Wijzigingen(final Lo3CategorieEnum categorie, final Lo3CategorieFormatter<L> formatter) {
        this.formatter = formatter;

        actueleCategorie = new Lo3CategorieWaarde(categorie, -1, 0);
        historischeCategorie = new Lo3CategorieWaarde(Lo3CategorieEnum.bepaalHistorischeCategorie(categorie, true), -1, 1);
    }

    /**
     * Registreer een actie inhoud (die in deze verwerking niet tot een wijziging heeft geleid).
     *
     * @param actieInhoud
     *            actie
     * @param historie
     *            historie
     * @param inhoud
     *            inhoud
     */
    public final void registreerActieInhoud(final ActieModel actieInhoud, final ModelIdentificeerbaar<?> historie, final Lo3Categorie<L> inhoud) {
        if (laatsteActie == null || !laatsteActie.getTijdstipRegistratie().na(actieInhoud.getTijdstipRegistratie())) {
            laatsteActie = actieInhoud;
            laatsteInhoud = inhoud;
            laatsteActieType = LaatsteActieType.INHOUD;
            laatsteActieInhoud(actieInhoud, historie, inhoud);
        }
    }

    /**
     * Zet actuele onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public final void setActueleOnderzoek(final Lo3Onderzoek onderzoek) {
        actueleOnderzoek = onderzoek;
    }

    /**
     * Geef het actuele onderzoek.
     *
     * @return actuele onderzoek (kan null zijn)
     */
    public final Lo3Onderzoek getActueleOnderzoek() {
        return actueleOnderzoek;
    }

    /**
     * Zet historisch onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public final void setHistorischOnderzoek(final Lo3Onderzoek onderzoek) {
        historischeOnderzoek = onderzoek;
    }

    /**
     * Geef het historische onderzoek.
     *
     * @return historische onderzoek (kan null zijn)
     */
    public final Lo3Onderzoek getHistorischeOnderzoek() {
        return historischeOnderzoek;
    }

    /**
     * Registreer laatste actie inhoud.
     *
     * @param actieInhoud
     *            actie inhoud
     * @param historie
     *            historie
     * @param inhoud
     *            inhoud
     */
    protected void laatsteActieInhoud(final ActieModel actieInhoud, final ModelIdentificeerbaar<?> historie, final Lo3Categorie<L> inhoud) {
        // Hook
    }

    /**
     * Registreer een actie aanpassing geldigheid (die in deze verwerking niet tot een wijziging heeft geleid).
     *
     * @param actieAanpassingGeldigheid
     *            actie
     * @param historie
     *            historie
     * @param inhoud
     *            inhoud
     */
    public final void registreerActieAanpassingGeldigheid(
        final ActieModel actieAanpassingGeldigheid,
        final ModelIdentificeerbaar<?> historie,
        final Lo3Categorie<L> inhoud)
    {
        if (laatsteActie == null || !laatsteActie.getTijdstipRegistratie().na(actieAanpassingGeldigheid.getTijdstipRegistratie())) {
            laatsteActie = actieAanpassingGeldigheid;
            laatsteInhoud = inhoud;
            laatsteActieType = LaatsteActieType.AANPASSING_GELDIGHEID;
            laatsteActieAanpassingGeldigheid(actieAanpassingGeldigheid, historie, inhoud);
        }
    }

    /**
     * Registreer laatste actie aanpassing geldigheid.
     *
     * @param actie
     *            actie
     * @param historie
     *            historie
     * @param inhoud
     *            inhoud
     */
    protected void laatsteActieAanpassingGeldigheid(final ActieModel actie, final ModelIdentificeerbaar<?> historie, final Lo3Categorie<L> inhoud) {
        // Hook
    }

    /**
     * Geef de actuele inhoud.
     *
     * @return actuele inhoud
     */
    public final Lo3Categorie<L> getActueleInhoud() {
        return actueleInhoud;
    }

    /**
     * Zet de actuele inhoud.
     *
     * @param actueleInhoud
     *            actuele inhoud
     */
    public final void setActueleInhoud(final Lo3Categorie<L> actueleInhoud) {
        this.actueleInhoud = actueleInhoud;
    }

    /**
     * Geef de historische inhoud.
     *
     * @return historische inhoud
     */
    public final Lo3Categorie<L> getHistorischeInhoud() {
        return historischeInhoud;
    }

    /**
     * Zet de historische inhoud.
     *
     * @param historischeInhoud
     *            historische inhoud
     */
    public final void setHistorischeInhoud(final Lo3Categorie<L> historischeInhoud) {
        this.historischeInhoud = historischeInhoud;
    }

    /**
     * Geef de actuele categorie waarde (gevuld na het aanroepen van {@link #format}).
     *
     * @return actuele categorie waarde.
     */
    public final Lo3CategorieWaarde geefActueleCategorie() {
        return actueleCategorie;
    }

    /**
     * Geef de historische categorie waarde (gevuld na het aanroepen van {@link #format}).
     *
     * @return historische categorie waarde.
     */
    public final Lo3CategorieWaarde geefHistorischeCategorie() {
        return historischeCategorie;
    }

    /**
     * Bevat wijzigingen.
     *
     * @return true, als de actuele of de historie categorie waarden bevat.
     */
    public final boolean bevatWijzigingen() {
        return !actueleCategorie.isEmpty() || !historischeCategorie.isEmpty();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** FORMAT ************************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void formatCategorie(final Lo3Categorie<L> categorie, final Lo3CategorieWaarde formatted) {
        final MutatieOutputter outputter = new MutatieOutputter(formatted);
        // Format inhoud
        formatter.format(categorie.getInhoud(), outputter);
        // Format onderzoek
        Lo3PersoonslijstFormatter.formatOnderzoek(categorie.getOnderzoek(), outputter);
        // Format documentatie
        Lo3PersoonslijstFormatter.formatDocumentatie(categorie.getDocumentatie(), outputter);
        // Format historie
        Lo3PersoonslijstFormatter.formatHistorie(categorie.getHistorie(), outputter);
    }

    private void formatCategorie(final Lo3Onderzoek onderzoek, final Lo3CategorieWaarde formatted) {
        final MutatieOutputter outputter = new MutatieOutputter(formatted);
        // Format onderzoek
        Lo3PersoonslijstFormatter.formatOnderzoek(onderzoek, outputter);
    }

    /**
     * (Verwerk onderzoeken,) formatteren (vul defaults en opschonen).
     */
    public final void format() {
        verwerkOnderzoek();

        // Formatteer inhoud
        if (actueleInhoud != null) {
            formatCategorie(actueleInhoud, actueleCategorie);
        }
        if (historischeInhoud != null) {
            formatCategorie(historischeInhoud, historischeCategorie);
        }

        // Defaults
        if (actueleCategorie.isGevuld() && !historischeCategorie.isGevuld() && laatsteActie != null) {
            final MutatieOutputter outputter = new MutatieOutputter(historischeCategorie);

            // Inhoud
            final L historischeInhoudObvLaatsteActie = bepaalHistorischeInhoudObvLaatsteActie(laatsteActie, laatsteInhoud, laatsteActieType);
            if (historischeInhoudObvLaatsteActie != null) {
                formatter.format(historischeInhoudObvLaatsteActie, outputter);
            }

            // Onderzoek
            Lo3PersoonslijstFormatter.formatOnderzoek(laatsteInhoud.getOnderzoek(), outputter);
            // Documentatie
            Lo3PersoonslijstFormatter.formatDocumentatie(laatsteInhoud.getDocumentatie(), outputter);
            // Historie
            Lo3PersoonslijstFormatter.formatHistorie(laatsteInhoud.getHistorie(), outputter);
        }

        // Vul RNI gegevens omdat deze 'altijd' geleverd moeten worden
        if (historischeInhoud == null) {
            vulRniVanuitLaatsteInhoud(laatsteInhoud, historischeCategorie);

            if (actueleInhoud == null) {
                vulRniVanuitLaatsteInhoud(laatsteInhoud, actueleCategorie);
            }
        }

        // Pure onderzoeks wijzigingen
        if (actueleInhoud == null && actueleOnderzoek != null) {
            formatCategorie(actueleOnderzoek, actueleCategorie);
        }
        if (historischeInhoud == null && historischeOnderzoek != null) {
            formatCategorie(historischeOnderzoek, historischeCategorie);
        }

        // Actueel kan nooit onjuist zijn.
        actueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        vulDefaults(actueleCategorie, historischeCategorie);

        opschonen();
    }

    private void vulRniVanuitLaatsteInhoud(final Lo3Categorie<L> inhoud, final Lo3CategorieWaarde categorie) {
        LOGGER.debug("vulRniVanuitLaatsteInhoud(categorie={})", categorie.getCategorie());
        if (inhoud == null) {
            return;
        }
        final MutatieOutputter outputter = new MutatieOutputter(categorie);

        final Lo3Documentatie documentatie = inhoud.getDocumentatie();
        if (documentatie != null) {
            outputter.element(Lo3ElementEnum.ELEMENT_8810, Lo3Format.format(documentatie.getRniDeelnemerCode()));
            outputter.element(Lo3ElementEnum.ELEMENT_8820, Lo3Format.format(documentatie.getOmschrijvingVerdrag()));
        }

        if (inhoud.getInhoud() instanceof Lo3InschrijvingInhoud) {
            final Lo3InschrijvingInhoud inschrijving = (Lo3InschrijvingInhoud) inhoud.getInhoud();

            outputter.element(Lo3ElementEnum.ELEMENT_7110, Lo3Format.format(inschrijving.getDatumVerificatie()));
            outputter.element(Lo3ElementEnum.ELEMENT_7120, Lo3Format.format(inschrijving.getOmschrijvingVerificatie()));
        }
    }

    /**
     * Bepaal historische inhoud obv de laatste actie.
     *
     * @param paramLaatsteActie
     *            laatste actie
     * @param paramLaatsteInhoud
     *            inhoud behorende bij de laatste actie
     * @param paramLaatsteActieType
     *            typering van de laatste actie
     * @return te gebruiken historische inhoud (null als er geen historische inhoud is)
     */
    protected L bepaalHistorischeInhoudObvLaatsteActie(
        final ActieModel paramLaatsteActie,
        final Lo3Categorie<L> paramLaatsteInhoud,
        final LaatsteActieType paramLaatsteActieType)
    {
        // Hook
        return null;
    }

    private void verwerkOnderzoek() {
        if (actueleInhoud != null) {
            actueleInhoud = verwerkOnderzoek(actueleInhoud, actueleCategorie.getCategorie());
        }
        if (historischeInhoud != null) {
            historischeInhoud = verwerkOnderzoek(historischeInhoud, historischeCategorie.getCategorie());
        }
    }

    private Lo3Categorie<L> verwerkOnderzoek(final Lo3Categorie<L> inhoud, final Lo3CategorieEnum categorie) {
        return new Lo3Categorie<L>(
            inhoud.getInhoud(),
            inhoud.getDocumentatie(),
            ONDERZOEK_VERWERKER.bepaalOnderzoekUitElementen(inhoud, categorie),
            inhoud.getHistorie(),
            inhoud.getLo3Herkomst());
    }

    private void opschonen() {
        if (actueleCategorie.isEmpty() && historischeCategorie.isEmpty()) {
            return;
        }

        LOGGER.debug("Opschonen (start):\nACT: {}\nHIS: {}", actueleCategorie, historischeCategorie);

        // Zorg ervoor dat alle elementen in beide categoriewaarden bestaan
        synchroonMakenElementen();
        LOGGER.debug("Opschonen (synchroon gemaakt):\nACT: {}\nHIS: {}", actueleCategorie, historischeCategorie);

        // Verwijder ongewijzigde elementen (behalve onderzoek en RNI)
        verwijderenOngewijzigdeElementen();
        LOGGER.debug("Opschonen (ongewijzigd verwijderd):\nACT: {}\nHIS: {}", actueleCategorie, historischeCategorie);

        // Bepalen of de onderzoek velden geleverd moeten worden
        if (!isOnderzoekGewijzigd() && !isRubriekInOnderzoekGewijzigd()) {
            verwijder(Lo3ElementEnum.ELEMENT_8310);
            verwijder(Lo3ElementEnum.ELEMENT_8320);
            verwijder(Lo3ElementEnum.ELEMENT_8330);
        }

        if (bevatAlleenOngewijzigdRni()) {
            verwijder(Lo3ElementEnum.ELEMENT_7110);
            verwijder(Lo3ElementEnum.ELEMENT_7120);
            verwijder(Lo3ElementEnum.ELEMENT_8810);
            verwijder(Lo3ElementEnum.ELEMENT_8820);
        }
        LOGGER.debug("Opschonen (klaar):\nACT: {}\nHIS: {}", actueleCategorie, historischeCategorie);
    }

    private boolean isOnderzoekGewijzigd() {
        return isGewijzigd(Lo3ElementEnum.ELEMENT_8310) || isGewijzigd(Lo3ElementEnum.ELEMENT_8320) || isGewijzigd(Lo3ElementEnum.ELEMENT_8330);
    }

    private boolean isRubriekInOnderzoekGewijzigd() {
        final String gegevensInOnderzoek = actueleCategorie.getElement(Lo3ElementEnum.ELEMENT_8310);
        if (gegevensInOnderzoek == null || "".equals(gegevensInOnderzoek)) {
            return false;
        }

        final List<String> rubrieken = new ArrayList<>();
        for (final Map.Entry<Lo3ElementEnum, String> elementEntry : actueleCategorie.getElementen().entrySet()) {
            rubrieken.add(actueleCategorie.getCategorie().getCategorie() + "." + elementEntry.getKey().getElementNummer(true));
        }

        return OnderzoekUtil.magOnderzoekWordenGeleverd(actueleCategorie.getCategorie().getCategorie(), gegevensInOnderzoek, rubrieken);
    }

    private boolean isGewijzigd(final Lo3ElementEnum element) {
        return !isGelijk(actueleCategorie.getElement(element), historischeCategorie.getElement(element));
    }

    private boolean isGelijk(final String dit, final String dat) {
        return dit == null ? dat == null : dit.equals(dat);
    }

    private boolean bevatAlleenOngewijzigdRni() {
        for (final Map.Entry<Lo3ElementEnum, String> entry : actueleCategorie.getElementen().entrySet()) {
            final Lo3ElementEnum element = entry.getKey();

            final boolean isRniElement = element.getGroep() == Lo3GroepEnum.GROEP71 || element.getGroep() == Lo3GroepEnum.GROEP88;
            final boolean isOngewijzigd = entry.getValue().equals(historischeCategorie.getElement(element));

            if (!isRniElement || !isOngewijzigd) {
                return false;
            }
        }

        return true;
    }

    private void verwijder(final Lo3ElementEnum element) {
        actueleCategorie.addElement(element, null);
        historischeCategorie.addElement(element, null);
    }

    /**
     * Vul defaults (hook).
     *
     * @param deActueleCategorie
     *            actuele categorie
     * @param deHistorischeCategorie
     *            historische categorie
     */
    protected void vulDefaults(final Lo3CategorieWaarde deActueleCategorie, final Lo3CategorieWaarde deHistorischeCategorie) {
        // Hook
    }

    /**
     * Zorg dat de actuele categorie en de historische categorie dezelfde elementen bevatten (indien 1 van de
     * categorieen een element niet bevat wordt de waarde "" toegevoegd).
     */
    private void synchroonMakenElementen() {
        for (final Lo3ElementEnum element : actueleCategorie.getElementen().keySet()) {
            if (!historischeCategorie.getElementen().containsKey(element)) {
                historischeCategorie.addElement(element, "");
            }
        }
        for (final Lo3ElementEnum element : historischeCategorie.getElementen().keySet()) {
            if (!actueleCategorie.getElementen().containsKey(element)) {
                actueleCategorie.addElement(element, "");
            }
        }
    }

    /**
     * Verwijder ongewijzigde elementen (behalve onderzoek (groep 83) en rni (groep 71 en 88)).
     */
    private void verwijderenOngewijzigdeElementen() {
        final List<Lo3ElementEnum> teVerwijderenElementen = new ArrayList<>();
        for (final Map.Entry<Lo3ElementEnum, String> element : actueleCategorie.getElementen().entrySet()) {
            if (element.getValue().equals(historischeCategorie.getElement(element.getKey()))) {
                teVerwijderenElementen.add(element.getKey());
            }
        }

        for (final Lo3ElementEnum element : teVerwijderenElementen) {
            if (element.getGroep() == Lo3GroepEnum.GROEP83 || element.getGroep() == Lo3GroepEnum.GROEP71 || element.getGroep() == Lo3GroepEnum.GROEP88) {
                continue;
            }

            verwijder(element);
        }
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("actueleCategorie", actueleCategorie)
                                                                          .append("historischeCategorie", historischeCategorie)
                                                                          .toString();
    }

    /**
     * Laatste actie type.
     */
    public static enum LaatsteActieType {
        /** Actie inhoud. */
        INHOUD,

        /** Actie aanpassing geldigheid. */
        AANPASSING_GELDIGHEID;
    }

}
