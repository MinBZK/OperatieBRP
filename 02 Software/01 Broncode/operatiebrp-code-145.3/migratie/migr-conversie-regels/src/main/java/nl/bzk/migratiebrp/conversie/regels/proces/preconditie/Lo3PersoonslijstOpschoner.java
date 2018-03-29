/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Lo3LoggingUtil;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;

/**
 * PersoonLijstOpschoner.
 */
public final class Lo3PersoonslijstOpschoner {

    /**
     * Schoon de persoonlijst op aan de hand van de logging.
     *
     * Nota: LET OP! Gebruikt de Logging.context
     * @param persoonslijst op te schonen persoonslijst
     * @return opgeschoonde persoonlijst
     */
    public Lo3Persoonslijst opschonen(final Lo3Persoonslijst persoonslijst) {
        final Logging logging = Logging.getLogging();

        if (containsErrorLevel(logging)) {
            Lo3Persoonslijst opgeschoondePersoonslijst = opschonenPersoonslijst(persoonslijst, logging);

            // stap 3 Controleer of de categorie niet onjuist is en de persoon is opgeschort met
            // reden 'F'
            if (bestaatErrorInJuisteCategorieOpgeschorteFoutPL(opgeschoondePersoonslijst, logging)
                    && persoonslijst.getActueelAdministratienummer() != null
                    && !checkLogErrorsVoorActueleCategorieen(logging)) {
                // Maak een dummy persoon aan als de PL is afgevoerd en er na opschoon stappen 1 en 2 nog
                // preconditie fouten in de categolrie zitten.
                opgeschoondePersoonslijst = maakDummyPL(persoonslijst);
                zetErrorLogRegelsNaarRemoved(logging);
            }

            return opgeschoondePersoonslijst;
        } else {
            return persoonslijst;
        }
    }

    private void zetErrorLogRegelsNaarRemoved(final Logging logging) {
        // Zet alle logregels met severity Error naar Removed
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError()) {
                regel.setSeverity(LogSeverity.SUPPRESSED);
            }
        }
    }

    /**
     * Om een Dummy-PL te mogen maken, mogen er geen preconditie/structuur fouten in categorie 01 (persoon), 07
     * (inschrijving) of 08 (verblijfplaats) zijn.
     * @param logging logging met alle logregels
     * @return true als er preconditie of structuurfouten zijn gevonden in categorie 01, 07 of 08.
     */
    private boolean checkLogErrorsVoorActueleCategorieen(final Logging logging) {
        boolean resultaat = false;
        for (final LogRegel regel : logging.getRegels()) {
            final Lo3CategorieEnum categorie = regel.getLo3Herkomst().getCategorie();
            final boolean dummyCatInError =
                    categorie.equals(Lo3CategorieEnum.CATEGORIE_01)
                            || categorie.equals(Lo3CategorieEnum.CATEGORIE_07)
                            || categorie.equals(Lo3CategorieEnum.CATEGORIE_08);
            if (regel.hasSeverityLevelError() && dummyCatInError) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    private boolean containsErrorLevel(final Logging logging) {
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError()) {
                return true;
            }
        }
        return false;
    }

    private Lo3Persoonslijst opschonenPersoonslijst(final Lo3Persoonslijst persoonslijst, final Logging logging) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(opschonenStapel(persoonslijst.getPersoonStapel(), logging));
        builder.ouder1Stapel(opschonenStapel(persoonslijst.getOuder1Stapel(), logging));
        builder.ouder2Stapel(opschonenStapel(persoonslijst.getOuder2Stapel(), logging));
        builder.nationaliteitStapels(opschonenStapels(persoonslijst.getNationaliteitStapels(), logging));
        builder.huwelijkOfGpStapels(opschonenStapels(persoonslijst.getHuwelijkOfGpStapels(), logging));
        builder.overlijdenStapel(opschonenStapel(persoonslijst.getOverlijdenStapel(), logging));
        builder.inschrijvingStapel(opschonenStapel(persoonslijst.getInschrijvingStapel(), logging));
        builder.verblijfplaatsStapel(opschonenStapel(persoonslijst.getVerblijfplaatsStapel(), logging));
        builder.kindStapels(opschonenStapels(persoonslijst.getKindStapels(), logging));
        builder.verblijfstitelStapel(opschonenStapel(persoonslijst.getVerblijfstitelStapel(), logging));
        builder.gezagsverhoudingStapel(opschonenStapel(persoonslijst.getGezagsverhoudingStapel(), logging));
        builder.reisdocumentStapels(opschonenStapels(persoonslijst.getReisdocumentStapels(), logging));
        builder.kiesrechtStapel(opschonenStapel(persoonslijst.getKiesrechtStapel(), logging));

        return builder.build();
    }

    private <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> opschonenStapels(final List<Lo3Stapel<T>> stapels, final Logging logging) {
        final List<Lo3Stapel<T>> result = new ArrayList<>();

        for (final Lo3Stapel<T> stapel : stapels) {
            final Lo3Stapel<T> opgeschoondeStapel = opschonenStapel(stapel, logging);

            if (opgeschoondeStapel != null) {
                result.add(opgeschoondeStapel);
            }
        }

        return result;
    }

    private <T extends Lo3CategorieInhoud> Lo3Stapel<T> opschonenStapel(final Lo3Stapel<T> stapel, final Logging logging) {
        if (stapel == null) {
            return null;
        }

        final List<Lo3Categorie<T>> geschoondeStapelInhoud = new ArrayList<>();

        // Opschonen stap 1
        // Controleer of er precondities op de onjuiste rij zitten. Indien waar, verwijder deze onjuiste rijen
        for (final Lo3Categorie<T> categorie : stapel) {
            final boolean preconditiesTriggered = heeftPreconditiesOpOnjuisteRij(categorie, logging);
            if (!preconditiesTriggered) {
                geschoondeStapelInhoud.add(categorie);
            }
        }

        // Controleer of de precondities 50 en/of 56 op de juiste rijen zit. Indien waar, verwijder alle onjuiste rijen
        opschonenPrecondities50En56(geschoondeStapelInhoud);

        // Opschonen stap 2
        // Controleer of er precondities op juiste lege rijen zit en de stapel verder leeg is. Indien waar, kan de hele
        // stapel verwijderd worden.
        if (bestaatErrorInLegeJuisteCategorie(geschoondeStapelInhoud, logging) && alleJuisteCategorienZijnLeeg(geschoondeStapelInhoud)) {
            // Als er een fout in een lege juiste categorie is en alle juiste voorkomens zijn leeg,
            // dan kan de hele stapel vervallen.
            logRemoved(stapel, logging);
            geschoondeStapelInhoud.clear();
        }

        final Lo3Stapel<T> resultaat;
        if (geschoondeStapelInhoud.isEmpty()) {
            // Lege stapel => alle meldingen onderdrukken, aangezien deze niet meer van toepassing zijn.
            onderdrukPre112VoorLegeStapel(logging, stapel);
            resultaat = null;
        } else {
            resultaat = new Lo3Stapel<>(geschoondeStapelInhoud);
        }

        return resultaat;
    }

    private <T extends Lo3CategorieInhoud> void onderdrukPre112VoorLegeStapel(final Logging logging, final Lo3Stapel<T> stapel) {
        for (final LogRegel regel : logging.getRegels()) {
            final Lo3Categorie<T> actueleCat = stapel.getLo3ActueelVoorkomen();
            if (actueleCat != null
                    && actueleCat.getLo3Herkomst().equalsCategorieStapelOnly(regel.getLo3Herkomst())
                    && SoortMeldingCode.PRE112.equals(regel.getSoortMeldingCode())) {
                regel.setSeverity(LogSeverity.SUPPRESSED);
            }
        }
    }

    private <T extends Lo3CategorieInhoud> void opschonenPrecondities50En56(final List<Lo3Categorie<T>> stapel) {
        boolean preconditieTriggered = false;
        final Set<LogRegel> logRegels = Lo3LoggingUtil.getLogRegels(SoortMeldingCode.PRE050, SoortMeldingCode.PRE056);
        for (final LogRegel regel : logRegels) {
            final Lo3Herkomst logHerkomst = regel.getLo3Herkomst();
            // Loop door de stapel om te bepalen of de herkomst in deze stapel voorkomt
            for (final Lo3Categorie<T> categorie : stapel) {
                if (logHerkomst.equals(categorie.getLo3Herkomst())) {
                    preconditieTriggered = true;
                    regel.setSeverity(LogSeverity.SUPPRESSED);
                    break;
                }
            }
        }

        // Preconditie 50 of 56 is getriggered. Opschonen door alle onjuiste rijen weg te gooien
        if (preconditieTriggered) {
            final List<Lo3Categorie<T>> juisteRijen = new ArrayList<>();
            boolean alleenLegeRijen = true;
            for (final Lo3Categorie<T> rij : stapel) {
                if (!rij.getHistorie().isOnjuist()) {
                    if (!rij.getInhoud().isLeeg() && alleenLegeRijen) {
                        alleenLegeRijen = false;
                    }
                    juisteRijen.add(rij);
                }
            }
            stapel.clear();
            stapel.addAll(alleenLegeRijen ? Collections.emptyList() : juisteRijen);
        }
    }

    private Lo3Persoonslijst maakDummyPL(final Lo3Persoonslijst persoonslijst) {
        final Lo3Persoonslijst dummyPersoonslijst;
        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = maakDummyPersoonInhoud(persoonslijst);
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = maakDummyInschrijvingInhoud(persoonslijst);

        // Lo3Historie obv datumtijdstempel
        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving = inschrijvingStapel.getLaatsteElement();
        final Lo3InschrijvingInhoud inhoud = lo3Inschrijving.getInhoud();
        final Lo3Datum lo3Datumstempel = BrpDatumTijd.fromLo3Datumtijdstempel(inhoud.getDatumtijdstempel()).converteerNaarLo3Datum();
        final Lo3Historie inschrijvingFormeleHistorie = new Lo3Historie(null, null, lo3Datumstempel);

        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = maakDummyVerblijfplaatsInhoud(persoonslijst, inschrijvingFormeleHistorie);

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(persoonStapel);
        builder.inschrijvingStapel(inschrijvingStapel);
        builder.verblijfplaatsStapel(verblijfplaatsStapel);
        dummyPersoonslijst = builder.build();
        dummyPersoonslijst.setIsDummyPl(true);

        return dummyPersoonslijst;
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> maakDummyVerblijfplaatsInhoud(
            final Lo3Persoonslijst persoonslijst,
            final Lo3Historie inschrijvingFormeleHistorie) {
        final Lo3VerblijfplaatsInhoud verblijfplaatsBronInhoud;
        if (persoonslijst.getVerblijfplaatsStapel() != null && !persoonslijst.getVerblijfplaatsStapel().isEmpty()) {
            verblijfplaatsBronInhoud = persoonslijst.getVerblijfplaatsStapel().getLaatsteElement().getInhoud();
        } else {
            verblijfplaatsBronInhoud = new Lo3VerblijfplaatsInhoud();
        }
        final Lo3Datum datumInschrijvingGemeente =
                verblijfplaatsBronInhoud.getDatumInschrijving() != null ? verblijfplaatsBronInhoud.getDatumInschrijving() : new Lo3Datum(0);
        final Lo3GemeenteCode gemeenteInschrijving;
        if (verblijfplaatsBronInhoud.getGemeenteInschrijving() != null) {
            gemeenteInschrijving = verblijfplaatsBronInhoud.getGemeenteInschrijving();
        } else {
            gemeenteInschrijving = Lo3GemeenteCode.ONBEKEND;
        }
        final Lo3VerblijfplaatsInhoud lo3VerblijfplaatsInhoud =
                new Lo3VerblijfplaatsInhoud.Builder().gemeenteInschrijving(gemeenteInschrijving).datumInschrijving(datumInschrijvingGemeente).build();
        final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaatsCat =
                new Lo3Categorie<>(lo3VerblijfplaatsInhoud, null, inschrijvingFormeleHistorie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0));
        return new Lo3Stapel<>(Collections.singletonList(verblijfplaatsCat));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> maakDummyInschrijvingInhoud(final Lo3Persoonslijst persoonslijst) {
        final Lo3InschrijvingInhoud inschrijvingBronInhoud = persoonslijst.getInschrijvingStapel().getLaatsteElement().getInhoud();
        final Lo3Datum datumOpschorting;
        if (inschrijvingBronInhoud.getDatumOpschortingBijhouding() != null) {
            datumOpschorting = inschrijvingBronInhoud.getDatumOpschortingBijhouding();
        } else {
            datumOpschorting = new Lo3Datum(0);
        }
        final Lo3RedenOpschortingBijhoudingCode redenOpschorting = inschrijvingBronInhoud.getRedenOpschortingBijhoudingCode();
        final Lo3Datum datumInschrijving =
                inschrijvingBronInhoud.getDatumEersteInschrijving() != null ? inschrijvingBronInhoud.getDatumEersteInschrijving() : new Lo3Datum(0);
        final Lo3Integer versieNummer = inschrijvingBronInhoud.getVersienummer();
        final Lo3Datumtijdstempel datumtijdStempel =
                inschrijvingBronInhoud.getDatumtijdstempel() != null ? inschrijvingBronInhoud.getDatumtijdstempel() : new Lo3Datumtijdstempel(0L);

        final Lo3InschrijvingInhoud lo3InschrijvingInhoud =
                new Lo3InschrijvingInhoud(
                        null,
                        datumOpschorting,
                        redenOpschorting,
                        datumInschrijving,
                        null,
                        null,
                        null,
                        null,
                        versieNummer,
                        datumtijdStempel,
                        null);
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijvingCat =
                new Lo3Categorie<>(
                        lo3InschrijvingInhoud,
                        null,
                        persoonslijst.getInschrijvingStapel().getLaatsteElement().getHistorie(),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0));
        return new Lo3Stapel<>(Collections.singletonList(inschrijvingCat));
    }

    private Lo3Stapel<Lo3PersoonInhoud> maakDummyPersoonInhoud(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonInhoud lo3PersoonInhoud =
                new Lo3PersoonInhoud(
                        Lo3String.wrap(persoonslijst.getActueelAdministratienummer()),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        final Lo3Categorie<Lo3PersoonInhoud> persoonCat =
                new Lo3Categorie<>(
                        lo3PersoonInhoud,
                        null,
                        persoonslijst.getPersoonStapel().getLaatsteElement().getHistorie(),
                        new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        return new Lo3Stapel<>(Collections.singletonList(persoonCat));
    }

    private <T extends Lo3CategorieInhoud> boolean bestaatErrorInLegeJuisteCategorie(final List<Lo3Categorie<T>> stapelInhoud, final Logging logging) {
        for (final LogRegel regel : logging.getRegels()) {
            for (final Lo3Categorie<T> categorie : stapelInhoud) {
                if (regel.hasSeverityLevelError()
                        && regel.getLo3Herkomst().equals(categorie.getLo3Herkomst())
                        && !categorie.getHistorie().isOnjuist()
                        && categorie.getInhoud().isLeeg()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean bestaatErrorInJuisteCategorieOpgeschorteFoutPL(final Lo3Persoonslijst persoonslijst, final Logging logging) {
        final boolean isOpgeschorteFoutPL =
                persoonslijst.getInschrijvingStapel() != null
                        && persoonslijst.getInschrijvingStapel()
                        .getLaatsteElement()
                        .getInhoud()
                        .getRedenOpschortingBijhoudingCode() != null
                        && persoonslijst.getInschrijvingStapel()
                        .getLaatsteElement()
                        .getInhoud()
                        .getRedenOpschortingBijhoudingCode()
                        .isFout();

        return isOpgeschorteFoutPL && isErrorInJuisteCategorie(persoonslijst, logging);
    }

    private boolean isErrorInJuisteCategorie(final Lo3Persoonslijst persoonslijst, final Logging logging) {
        boolean bestaatError;

        bestaatError = isErrorInJuisteCategorie(persoonslijst.getPersoonStapel(), Lo3CategorieEnum.CATEGORIE_01, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getOuder1Stapel(), Lo3CategorieEnum.CATEGORIE_02, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getOuder2Stapel(), Lo3CategorieEnum.CATEGORIE_03, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getNationaliteitStapels(), Lo3CategorieEnum.CATEGORIE_04, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getHuwelijkOfGpStapels(), Lo3CategorieEnum.CATEGORIE_05, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getOverlijdenStapel(), Lo3CategorieEnum.CATEGORIE_06, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getInschrijvingStapel(), Lo3CategorieEnum.CATEGORIE_07, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getVerblijfplaatsStapel(), Lo3CategorieEnum.CATEGORIE_08, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getKindStapels(), Lo3CategorieEnum.CATEGORIE_09, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getVerblijfstitelStapel(), Lo3CategorieEnum.CATEGORIE_10, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getGezagsverhoudingStapel(), Lo3CategorieEnum.CATEGORIE_11, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getReisdocumentStapels(), Lo3CategorieEnum.CATEGORIE_12, logging);
        bestaatError = bestaatError || isErrorInJuisteCategorie(persoonslijst.getKiesrechtStapel(), Lo3CategorieEnum.CATEGORIE_13, logging);

        return bestaatError;
    }

    private <T extends Lo3CategorieInhoud> boolean isErrorInJuisteCategorie(
            final List<Lo3Stapel<T>> stapels,
            final Lo3CategorieEnum categorieEnum,
            final Logging logging) {
        boolean isError = false;
        if (stapels != null) {
            for (final Lo3Stapel<T> stapel : stapels) {
                isError |= isErrorInJuisteCategorie(stapel, categorieEnum, logging);
            }
        }

        return isError;
    }

    private <T extends Lo3CategorieInhoud> boolean isErrorInJuisteCategorie(
            final Lo3Stapel<T> stapel,
            final Lo3CategorieEnum categorieEnum,
            final Logging logging) {
        boolean resultaat = false;
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError()) {
                if (stapel == null) {
                    resultaat = regel.getLo3Herkomst().getCategorie().equals(categorieEnum);
                } else {
                    for (final Lo3Categorie<T> categorie : stapel) {
                        if (regel.hasSeverityLevelError()
                                && regel.getLo3Herkomst().equals(categorie.getLo3Herkomst())
                                && !categorie.getHistorie().isOnjuist()
                                && !categorie.getInhoud().isLeeg()) {
                            resultaat = true;
                            break;
                        }
                    }
                }
            }
        }

        return resultaat;
    }

    private <T extends Lo3CategorieInhoud> boolean alleJuisteCategorienZijnLeeg(final List<Lo3Categorie<T>> stapel) {
        boolean alleJuisteCategorienZijnLeeg = true;

        for (final Lo3Categorie<T> categorie : stapel) {
            if (!categorie.getHistorie().isOnjuist() && !categorie.getInhoud().isLeeg()) {
                alleJuisteCategorienZijnLeeg = false;
                break;
            }
        }

        return alleJuisteCategorienZijnLeeg;
    }

    private <T extends Lo3CategorieInhoud> void logRemoved(final Lo3Stapel<T> stapel, final Logging logging) {
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError()) {
                final Lo3Herkomst regelHerkomst = regel.getLo3Herkomst();

                for (final Lo3Categorie<T> categorie : stapel) {
                    if (regelHerkomst.equals(categorie.getLo3Herkomst())) {
                        regel.setSeverity(LogSeverity.SUPPRESSED);
                    }
                }
            }
        }
    }

    private <T extends Lo3CategorieInhoud> boolean heeftPreconditiesOpOnjuisteRij(final Lo3Categorie<T> categorie, final Logging logging) {
        // Als dit om een onjuiste rij gaat dan skippen we alleen deze en zetten we de LogSeverity naar
        // SUPPRESSED.
        boolean result = false;
        for (final LogRegel regel : logging.getRegels()) {
            if (regel.hasSeverityLevelError() && regel.getLo3Herkomst().equals(categorie.getLo3Herkomst()) && categorie.getHistorie().isOnjuist()) {
                regel.setSeverity(LogSeverity.SUPPRESSED);
                result = true;
            }
        }
        return result;
    }
}
