/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 04: Nationaliteit.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3NationaliteitPrecondities extends AbstractLo3Precondities {

    /**
     * Controleer alle stapels.
     *
     * @param stapels
     *            stapels
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        controleerPreconditie082(stapels);
        controleerPreconditie093(stapels);
        controleerPreconditie103(stapels);
        controleerPreconditie104(stapels);

        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer preconditie 82.
     *
     * @param stapels
     *            De stapels die gecontroleerd moeten worden.
     */
    @Preconditie(SoortMeldingCode.PRE082)
    private void controleerPreconditie082(final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels) {
        final Set<String> nationaliteiten = new HashSet<>();

        // Doorloop alle stapels.
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            final Set<String> natInStapel = new HashSet<>();
            // Doorloop alle categorieen in de stapel
            for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
                if (categorie.getHistorie().isOnjuist()) {
                    continue;
                }
                final Lo3NationaliteitCode code = categorie.getInhoud().getNationaliteitCode();
                if (Validatie.isElementGevuld(code)) {
                    if (nationaliteiten.contains(code.getWaarde())) {
                        // preconditie 82
                        Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE082, null);
                    }
                    natInStapel.add(categorie.getInhoud().getNationaliteitCode().getWaarde());
                }
            }
            nationaliteiten.addAll(natInStapel);
        }
    }

    /**
     * Controleer preconditie 93.
     *
     * @param stapels
     *            De stapels die gecontroleerd moeten worden.
     */
    @Preconditie(SoortMeldingCode.PRE093)
    private void controleerPreconditie093(final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels) {
        final Map<Lo3NationaliteitCode, Set<Integer>> nationaliteitStapels = new HashMap<>();

        // Doorloop alle stapels en bepaal nationaliteiten per stapel.
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            // Doorloop alle categorieen in de stapel
            for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
                final Integer stapelNr = categorie.getLo3Herkomst().getStapel();
                final Lo3NationaliteitCode code = categorie.getInhoud().getNationaliteitCode();
                if (Validatie.isElementGevuld(code)) {
                    final Lo3NationaliteitCode codeZonderOnderzoek = Lo3NationaliteitCode.zonderOnderzoek(code);
                    if (!nationaliteitStapels.containsKey(codeZonderOnderzoek)) {
                        nationaliteitStapels.put(codeZonderOnderzoek, new HashSet<Integer>());
                    }
                    nationaliteitStapels.get(codeZonderOnderzoek).add(stapelNr);
                }
            }
        }

        // Doorloop onjuiste rijen van alle stapels en check of de nationaliteit ook in
        // andere stapels voorkomt.
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            // Doorloop alle categorieen in de stapel
            for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
                final Lo3NationaliteitCode code = categorie.getInhoud().getNationaliteitCode();
                if (Validatie.isElementGevuld(code) && categorie.getHistorie().isOnjuist()) {
                    final Lo3NationaliteitCode codeZonderOnderzoek = Lo3NationaliteitCode.zonderOnderzoek(code);
                    if (nationaliteitStapels.get(codeZonderOnderzoek).size() > 1) {
                        // Als deze nationaliteit in meer dan 1 stapel voorkomt log dan preconditie 93 fout.
                        Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE093, null);
                    }
                }
            }
        }
    }

    /*
     * Cyclomatic complexity is 11. Methode is niet op te knippen zonder dat het onduidelijk wordt wat er precies
     * gebeurd.
     */
    @Preconditie(SoortMeldingCode.PRE103)
    private void controleerPreconditie103(final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels) {
        Lo3Stapel<Lo3NationaliteitInhoud> controleStapel = null;
        boolean bevatControleBijzonderNederlanderschap = false;

        // Doorloop alle stapels.
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            // Doorloop alle categorieen in de stapel
            for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
                if (categorie.getHistorie().isOnjuist()) {
                    continue;
                }

                final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
                final Lo3NationaliteitCode code = inhoud.getNationaliteitCode();
                final boolean bevatNLNationaliteitCode = AbstractLo3Element.equalsWaarde(Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE, code);
                final boolean bevatAanduidingBijzonderNLschap = Validatie.isElementGevuld(inhoud.getAanduidingBijzonderNederlandschap());

                if (controleStapel == null) {
                    if (bevatAanduidingBijzonderNLschap || bevatNLNationaliteitCode) {
                        controleStapel = stapel;
                        bevatControleBijzonderNederlanderschap = bevatAanduidingBijzonderNLschap;
                    }
                } else {
                    if (!controleStapel.equals(stapel)
                        && (bevatControleBijzonderNederlanderschap ? bevatNLNationaliteitCode : bevatAanduidingBijzonderNLschap))
                    {
                        Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE103, null);
                    }
                }
            }
        }
    }

    @Preconditie(SoortMeldingCode.PRE104)
    private void controleerPreconditie104(final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels) {
        final Set<Integer> bijzonderNederlanderschapStapels = new HashSet<>();
        final Set<Integer> nederlandseNationliteitStapels = new HashSet<>();

        bepaalNederlanderschapStapels(stapels, bijzonderNederlanderschapStapels, nederlandseNationliteitStapels);

        // Doorloop onjuiste rijen van alle stapels.
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            // Doorloop alle categorieen in de stapel
            for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
                final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
                final Lo3NationaliteitCode code = inhoud.getNationaliteitCode();
                final Lo3AanduidingBijzonderNederlandschap bijzonderNederlandschap = inhoud.getAanduidingBijzonderNederlandschap();
                final boolean isOnjuist = categorie.getHistorie().isOnjuist();
                final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
                final Integer stapelNr = herkomst.getStapel();

                if (isOnjuist) {
                    if (Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE.equalsWaarde(code)
                        && stapelSetBevatNietAlleenStapelNr(bijzonderNederlanderschapStapels, stapelNr))
                    {
                        Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE104, null);
                    }
                    if (Validatie.isElementGevuld(bijzonderNederlandschap) && stapelSetBevatNietAlleenStapelNr(nederlandseNationliteitStapels, stapelNr)) {
                        Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE104, null);
                    }
                }
            }
        }
    }

    private void bepaalNederlanderschapStapels(
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> stapels,
        final Set<Integer> bijzonderNederlanderschapStapels,
        final Set<Integer> nederlandseNationliteitStapels)
    {
        // Doorloop alle stapels
        for (final Lo3Stapel<Lo3NationaliteitInhoud> stapel : stapels) {
            // Doorloop alle categorieen in de stapel
            for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
                final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
                final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
                final Lo3NationaliteitCode code = inhoud.getNationaliteitCode();
                if (Validatie.isElementGevuld(inhoud.getAanduidingBijzonderNederlandschap())) {
                    bijzonderNederlanderschapStapels.add(herkomst.getStapel());
                }
                if (Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE.equalsWaarde(code)) {
                    nederlandseNationliteitStapels.add(herkomst.getStapel());
                }
            }
        }
    }

    private boolean stapelSetBevatNietAlleenStapelNr(final Set<Integer> stapelSet, final Integer stapelNr) {
        return stapelSet.size() == 1 && !stapelSet.contains(stapelNr) || stapelSet.size() > 1;
    }

    @Preconditie({SoortMeldingCode.PRE109, SoortMeldingCode.PRE110 })
    private void controleerPreconditie109en110(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        boolean heeftAndereNationaliteit = false;
        boolean heeftBijzonderNederlandschap = false;
        boolean heeftJuistBijzonderNederlandschap = false;

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
            if (bevatAndereNationaliteit(categorie)) {
                heeftAndereNationaliteit = true;
            }

            if (Validatie.isElementGevuld(categorie.getInhoud().getAanduidingBijzonderNederlandschap())) {
                heeftBijzonderNederlandschap = true;
                if (!categorie.getHistorie().isOnjuist()) {
                    heeftJuistBijzonderNederlandschap = true;
                }
            }
        }

        controleerCategorienOpPreconditie109en110(stapel, heeftAndereNationaliteit, heeftBijzonderNederlandschap, heeftJuistBijzonderNederlandschap);
    }

    private void controleerCategorienOpPreconditie109en110(
        final Lo3Stapel<Lo3NationaliteitInhoud> stapel,
        final boolean heeftAndereNationaliteit,
        final boolean heeftBijzonderNederlandschap,
        final boolean heeftJuistBijzonderNederlandschap)
    {
        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel.getCategorieen()) {
            // preconditie 109
            if (bevatAndereNationaliteit(categorie)) {
                if (!categorie.getHistorie().isOnjuist() && heeftJuistBijzonderNederlandschap) {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE109, Lo3ElementEnum.ELEMENT_0510);
                }

                // preconditie 110 op andere nationaliteit
                if (categorie.getHistorie().isOnjuist() && heeftBijzonderNederlandschap) {
                    Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE110, Lo3ElementEnum.ELEMENT_0510);
                }
            }

            // preconditie 110 op bijzondere nl schap
            if (Validatie.isElementGevuld(categorie.getInhoud().getAanduidingBijzonderNederlandschap())
                && categorie.getHistorie().isOnjuist()
                && heeftAndereNationaliteit)
            {
                Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE110, Lo3ElementEnum.ELEMENT_6510);
            }
        }
    }

    private boolean bevatAndereNationaliteit(final Lo3Categorie<Lo3NationaliteitInhoud> categorie) {
        return Validatie.isElementGevuld(categorie.getInhoud().getNationaliteitCode())
               && !Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE.equalsWaarde(categorie.getInhoud().getNationaliteitCode());
    }

    /**
     * Controleer precondities op stapel niveau.
     *
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie051(stapel);
        controleerPreconditie055(stapel);
        controleerPreconditie109en110(stapel);
        controleerPreconditie112(stapel);

        for (final Lo3Categorie<Lo3NationaliteitInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     *
     * @param categorie
     *            categorie
     */
    @Preconditie({SoortMeldingCode.PRE023, SoortMeldingCode.PRE083, SoortMeldingCode.PRE105, SoortMeldingCode.PRE106 })
    void controleerCategorie(final Lo3Categorie<Lo3NationaliteitInhoud> categorie) {
        /* cyclomatic complexity is 14. Komt door de nieuwe precondities (105/106). Is niet complex. */
        final Lo3NationaliteitInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep05Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP05, inhoud);
        final boolean groep63Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP63, inhoud);
        final boolean groep64Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP64, inhoud);
        final boolean groep65Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP65, inhoud);

        controleeerCategorieOpPre106(inhoud, herkomst, groep05Aanwezig, groep64Aanwezig, groep65Aanwezig);
        controleeerCategorieOpPre083(inhoud, herkomst, groep05Aanwezig, groep63Aanwezig, groep65Aanwezig);
        controleeerCategorieOpPre023(inhoud, herkomst, groep05Aanwezig, groep63Aanwezig, groep64Aanwezig, groep65Aanwezig);
        controleeerCategorieOpPre105(inhoud, herkomst, groep05Aanwezig, groep64Aanwezig, groep65Aanwezig);

        // Documentatie Groep 81, Groep 82 en Groep 88
        controleerDocumentatie(categorie, herkomst);
    }

    private void controleeerCategorieOpPre105(
        final Lo3NationaliteitInhoud inhoud,
        final Lo3Herkomst herkomst,
        final boolean groep05Aanwezig,
        final boolean groep64Aanwezig,
        final boolean groep65Aanwezig)
    {
        if (groep65Aanwezig) {
            controleerGroep65BijzonderNederlanderschap(inhoud.getAanduidingBijzonderNederlandschap(), herkomst);

            if (groep05Aanwezig || groep64Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE105, null);
            }
        }
    }

    private void controleeerCategorieOpPre023(
        final Lo3NationaliteitInhoud inhoud,
        final Lo3Herkomst herkomst,
        final boolean groep05Aanwezig,
        final boolean groep63Aanwezig,
        final boolean groep64Aanwezig,
        final boolean groep65Aanwezig)
    {
        if (groep64Aanwezig) {
            controleerGroep64VerliesNederlanderschap(inhoud.getRedenVerliesNederlandschapCode(), herkomst);

            if (groep05Aanwezig || groep63Aanwezig || groep65Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE023, null);
            }
        }
    }

    private void controleeerCategorieOpPre083(
        final Lo3NationaliteitInhoud inhoud,
        final Lo3Herkomst herkomst,
        final boolean groep05Aanwezig,
        final boolean groep63Aanwezig,
        final boolean groep65Aanwezig)
    {
        if (groep63Aanwezig) {
            controleerGroep63VerkrijgingNederlanderschap(inhoud.getRedenVerkrijgingNederlandschapCode(), herkomst);
            if (!groep05Aanwezig && !groep65Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE083, null);
            }
        }
    }

    private void controleeerCategorieOpPre106(
        final Lo3NationaliteitInhoud inhoud,
        final Lo3Herkomst herkomst,
        final boolean groep05Aanwezig,
        final boolean groep64Aanwezig,
        final boolean groep65Aanwezig)
    {
        if (groep05Aanwezig) {
            controleerGroep05Nationaliteit(inhoud.getNationaliteitCode(), herkomst);
            if (groep64Aanwezig || groep65Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE106, null);
            }
        }
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB012)
    private void controleerDocumentatie(final Lo3Categorie<Lo3NationaliteitInhoud> categorie, final Lo3Herkomst herkomst) {
        final Lo3Documentatie documentatie = categorie.getDocumentatie();

        if (isAkteAanwezig(documentatie)) {
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (isDocumentAanwezig(documentatie)) {
            final Lo3String beschrijvingDocument = documentatie.getBeschrijvingDocument();
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(), beschrijvingDocument, herkomst);

            if (documentatie.bevatAanduidingGeprivilegieerde()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB012, null);
            }
        }
        if (isRNIDeelnemerAanwezig(documentatie)) {
            controleerGroep88RNIDeelnemer(documentatie.getRniDeelnemerCode(), herkomst);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerPreconditie051(final Lo3Stapel<Lo3NationaliteitInhoud> stapel) {
        Lo3Categorie<Lo3NationaliteitInhoud> laatstGevondenVoorkomen = null;
        for (final Lo3Categorie<Lo3NationaliteitInhoud> voorkomen : stapel) {
            if (Validatie.isElementGevuld(voorkomen.getInhoud().getNationaliteitCode())) {
                if (laatstGevondenVoorkomen == null
                    || AbstractLo3Element.equalsWaarde(
                        laatstGevondenVoorkomen.getInhoud().getNationaliteitCode(),
                        voorkomen.getInhoud().getNationaliteitCode()))
                {
                    laatstGevondenVoorkomen = voorkomen;
                } else {
                    // probeer de fout aan een onjuiste rij te koppelen, zodat dit opgeschoond kan worden
                    Lo3Categorie<Lo3NationaliteitInhoud> onjuisteRij =
                            filterOnjuisteRij(stapel, laatstGevondenVoorkomen.getInhoud().getNationaliteitCode());

                    if (onjuisteRij == null) {
                        onjuisteRij = filterOnjuisteRij(stapel, voorkomen.getInhoud().getNationaliteitCode());
                    }

                    final Lo3Herkomst herkomstFoutieveRij = onjuisteRij != null ? onjuisteRij.getLo3Herkomst() : laatstGevondenVoorkomen.getLo3Herkomst();
                    Foutmelding.logMeldingFout(herkomstFoutieveRij, LogSeverity.ERROR, SoortMeldingCode.PRE051, null);
                    break;
                }
            }
        }
    }

    private Lo3Categorie<Lo3NationaliteitInhoud> filterOnjuisteRij(
        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitVoorkomens,
        final Lo3NationaliteitCode lo3NationaliteitCode)
    {
        Lo3Categorie<Lo3NationaliteitInhoud> result = null;

        for (final Lo3Categorie<Lo3NationaliteitInhoud> nationaliteitVoorkomen : nationaliteitVoorkomens) {
            if (nationaliteitVoorkomen.getHistorie().isOnjuist()
                && AbstractLo3Element.equalsWaarde(lo3NationaliteitCode, nationaliteitVoorkomen.getInhoud().getNationaliteitCode()))
            {
                result = nationaliteitVoorkomen;
                break;
            }
        }
        return result;
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep05Nationaliteit(final Lo3NationaliteitCode nationaliteitCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(
            nationaliteitCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0510));
        controleerCode(nationaliteitCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0510));
    }

    private void controleerGroep63VerkrijgingNederlanderschap(
        final Lo3RedenNederlandschapCode redenVerkrijgingNederlandschapCode,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(
            redenVerkrijgingNederlandschapCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_6310));
        controleerCode(
            redenVerkrijgingNederlandschapCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_6310),
            true);
    }

    private void controleerGroep64VerliesNederlanderschap(final Lo3RedenNederlandschapCode redenVerliesNederlandschapCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(
            redenVerliesNederlandschapCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_6410));
        controleerCode(
            redenVerliesNederlandschapCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_6410),
            false);
    }

    private void controleerGroep65BijzonderNederlanderschap(
        final Lo3AanduidingBijzonderNederlandschap aanduidingBijzonderNederlandschap,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(
            aanduidingBijzonderNederlandschap,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_6510));
        Lo3PreconditieEnumCodeChecks.controleerCode(
            aanduidingBijzonderNederlandschap,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_6510));
    }

}
