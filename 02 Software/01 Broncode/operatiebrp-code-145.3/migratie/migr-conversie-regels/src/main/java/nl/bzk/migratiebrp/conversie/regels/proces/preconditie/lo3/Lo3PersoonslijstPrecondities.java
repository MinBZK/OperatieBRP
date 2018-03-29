/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor een volledige LO3 persoonslijst.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3PersoonslijstPrecondities extends AbstractLo3Precondities {

    private final Lo3PersoonPrecondities persoonPrecondities;
    private final Lo3OuderPrecondities ouderPrecondities;
    private final Lo3NationaliteitPrecondities nationaliteitPrecondities;
    private final Lo3HuwelijkPrecondities huwelijkPrecondities;
    private final Lo3OverlijdenPrecondities overlijdenPrecondities;
    private final Lo3InschrijvingPrecondities inschrijvingPrecondities;
    private final Lo3VerblijfplaatsPrecondities verblijfplaatsPrecondities;
    private final Lo3KindPrecondities kindPrecondities;
    private final Lo3VerblijfstitelPrecondities verblijfstitelPrecondities;
    private final Lo3GezagsverhoudingPrecondities gezagsverhoudingPrecondities;
    private final Lo3ReisdocumentPrecondities reisdocumentPrecondities;
    private final Lo3KiesrechtPrecondities kiesrechtPrecondities;

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    @Inject
    public Lo3PersoonslijstPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
        persoonPrecondities = new Lo3PersoonPrecondities(conversieTabelFactory);
        ouderPrecondities = new Lo3OuderPrecondities(conversieTabelFactory);
        kiesrechtPrecondities = new Lo3KiesrechtPrecondities(conversieTabelFactory);
        nationaliteitPrecondities = new Lo3NationaliteitPrecondities(conversieTabelFactory);
        huwelijkPrecondities = new Lo3HuwelijkPrecondities(conversieTabelFactory);
        overlijdenPrecondities = new Lo3OverlijdenPrecondities(conversieTabelFactory);
        inschrijvingPrecondities = new Lo3InschrijvingPrecondities(conversieTabelFactory);
        verblijfplaatsPrecondities = new Lo3VerblijfplaatsPrecondities(conversieTabelFactory);
        kindPrecondities = new Lo3KindPrecondities(conversieTabelFactory);
        verblijfstitelPrecondities = new Lo3VerblijfstitelPrecondities(conversieTabelFactory);
        gezagsverhoudingPrecondities = new Lo3GezagsverhoudingPrecondities(conversieTabelFactory);
        reisdocumentPrecondities = new Lo3ReisdocumentPrecondities(conversieTabelFactory);

    }

    /**
     * Voer de precondities voor een volledige persoonlijst uit.
     * @param persoonslijst persoonslijst
     */
    public void controleerPersoonslijst(final Lo3Persoonslijst persoonslijst) {
        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = persoonslijst.getPersoonStapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = persoonslijst.getOuder1Stapel();
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = persoonslijst.getOuder2Stapel();
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = persoonslijst.getVerblijfplaatsStapel();
        final Lo3String persoonAnummer;

        if (isEmptyOrNull(persoonStapel)) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, -1, -1), LogSeverity.ERROR, SoortMeldingCode.PRE047, null);
            persoonAnummer = null;
        } else {
            persoonAnummer = persoonStapel.getLo3ActueelVoorkomen().getInhoud().getANummer();
        }

        controleerPreconditieOuderAanwezig(ouder1Stapel, persoonslijst, SoortMeldingCode.PRE065, Lo3CategorieEnum.CATEGORIE_02);

        controleerPreconditieOuderAanwezig(ouder2Stapel, persoonslijst, SoortMeldingCode.PRE066, Lo3CategorieEnum.CATEGORIE_03);

        if (isEmptyOrNull(inschrijvingStapel)) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, -1, -1), LogSeverity.ERROR, SoortMeldingCode.PRE032, null);
        }

        if (isEmptyOrNull(verblijfplaatsStapel)) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, -1, -1), LogSeverity.ERROR, SoortMeldingCode.PRE033, null);
        }

        persoonPrecondities.controleerStapel(persoonStapel);
        ouderPrecondities.controleerStapel(ouder1Stapel, persoonAnummer);
        ouderPrecondities.controleerStapel(ouder2Stapel, persoonAnummer);
        nationaliteitPrecondities.controleerStapels(persoonslijst.getNationaliteitStapels());
        huwelijkPrecondities.controleerStapels(persoonslijst.getHuwelijkOfGpStapels(), persoonAnummer);
        overlijdenPrecondities.controleerStapel(persoonslijst.getOverlijdenStapel());
        inschrijvingPrecondities.controleerStapel(inschrijvingStapel);
        verblijfplaatsPrecondities.controleerStapel(verblijfplaatsStapel);
        kindPrecondities.controleerStapels(persoonslijst.getKindStapels(), persoonAnummer);
        verblijfstitelPrecondities.controleerStapel(persoonslijst.getVerblijfstitelStapel());
        gezagsverhoudingPrecondities.controleerStapel(persoonslijst.getGezagsverhoudingStapel());
        reisdocumentPrecondities.controleerStapels(persoonslijst.getReisdocumentStapels());
        kiesrechtPrecondities.controleerStapel(persoonslijst.getKiesrechtStapel());
    }

    private void controleerPreconditieOuderAanwezig(
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel,
            final Lo3Persoonslijst persoonslijst,
            final SoortMeldingCode soortMeldingCode,
            final Lo3CategorieEnum categorie) {
        if (isEmptyOrNull(ouderStapel)) {
            final Lo3RedenOpschortingBijhoudingCode opschortcode = getRedenOpschortingBijhoudingCode(persoonslijst);
            final Lo3GemeenteCode gemeenteInschrijvingCode = getGemeenteVanInschrijving(persoonslijst);
            final boolean opschortRedenRNIToegestaan =
                    opschortcode != null && (opschortcode.isFout() || opschortcode.isRNI() || opschortcode.isOverleden());
            if (!(AbstractLo3Element.equalsWaarde(Lo3GemeenteCode.RNI, gemeenteInschrijvingCode) && opschortRedenRNIToegestaan)) {
                Foutmelding.logMeldingFout(new Lo3Herkomst(categorie, -1, -1), LogSeverity.ERROR, soortMeldingCode, null);
            }
        }
    }

    private Lo3GemeenteCode getGemeenteVanInschrijving(final Lo3Persoonslijst persoonslijst) {
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = persoonslijst.getVerblijfplaatsStapel();
        return isEmptyOrNull(verblijfplaatsStapel) ? null : verblijfplaatsStapel.getLaatsteElement().getInhoud().getGemeenteInschrijving();
    }

    private Lo3RedenOpschortingBijhoudingCode getRedenOpschortingBijhoudingCode(final Lo3Persoonslijst persoonslijst) {
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = persoonslijst.getInschrijvingStapel();
        return isEmptyOrNull(inschrijvingStapel) ? null : inschrijvingStapel.getLaatsteElement().getInhoud().getRedenOpschortingBijhoudingCode();
    }

    private boolean isEmptyOrNull(final Lo3Stapel<? extends Lo3CategorieInhoud> categorie) {
        return categorie == null || categorie.isEmpty();
    }
}
