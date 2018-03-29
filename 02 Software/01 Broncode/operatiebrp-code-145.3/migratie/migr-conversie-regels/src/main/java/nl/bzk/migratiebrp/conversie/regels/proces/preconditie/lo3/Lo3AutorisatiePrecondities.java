/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor categorie 35: Autorisaties.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3AutorisatiePrecondities extends AbstractLo3Precondities {

    private boolean legeEinddatumVerwerkt;

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3AutorisatiePrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op container niveau.
     * @param autorisatie autorisatie
     */
    public void controleerAutorisatie(final Lo3Autorisatie autorisatie) {
        legeEinddatumVerwerkt = false;
        controleerVerplichteVelden(autorisatie);
        controleerStapel(autorisatie.getAutorisatieStapel());
    }

    /**
     * Controleert of alle verplichte velden van de autorisatie wel gevuld zijn.
     * @param autorisatie De te controleren autorisatie.
     */
    private void controleerVerplichteVelden(final Lo3Autorisatie autorisatie) {

        controleerAanwezig(Lo3String.wrap(autorisatie.getAfnemersindicatie()),
                Foutmelding.maakMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, -1, -1), LogSeverity.ERROR, SoortMeldingCode.AUT001, null));
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3AutorisatieInhoud> stapel) {

        for (final Lo3Categorie<Lo3AutorisatieInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleert de categorie.
     * @param categorie De te controleren categorie.
     */
    private void controleerCategorie(final Lo3Categorie<Lo3AutorisatieInhoud> categorie) {

        final Lo3AutorisatieInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        controleerAanwezig(
                Lo3String.wrap(inhoud.getAfnemernaam()),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT004, null));

        controleerAanwezig(
                Lo3Integer.wrap(inhoud.getVerstrekkingsbeperking()),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT005, null));

        final Lo3Datum datumIngang = inhoud.getDatumIngang();

        controleerAanwezig(datumIngang, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT003, null));

        controleerDatum(datumIngang, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT003, null));

        controleerDatumNietOnbekend(datumIngang, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT002, null));

        final Lo3Datum datumEinde = inhoud.getDatumEinde();

        if (datumEinde == null && legeEinddatumVerwerkt) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT012, null);
        } else if (datumEinde == null) {
            legeEinddatumVerwerkt = true;
        } else {
            controleerDatumNietOnbekend(datumEinde, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT011, null));
        }

        if (datumIngang != null && datumEinde != null && datumEinde.compareTo(datumIngang) < 0) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.AUT006, null);
        }

    }
}
