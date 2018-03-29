/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor categorie 10: Verblijfstitel.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3VerblijfstitelPrecondities extends AbstractLo3Precondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3VerblijfstitelPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3VerblijfstitelInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3VerblijfstitelInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3VerblijfstitelInhoud> categorie) {
        final Lo3VerblijfstitelInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP39, inhoud)) {
            controleerGroep39Verblijfstitel(
                    inhoud.getAanduidingVerblijfstitelCode(),
                    inhoud.getDatumEindeVerblijfstitel(),
                    inhoud.getDatumAanvangVerblijfstitel(),
                    herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        if (isAkteAanwezig(documentatie)) {
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (isDocumentAanwezig(documentatie)) {
            controleerGroep82Document(
                    documentatie.getGemeenteDocument(),
                    documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(),
                    herkomst);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep39Verblijfstitel(
            final Lo3AanduidingVerblijfstitelCode aanduidingVerblijfstitelCode,
            final Lo3Datum datumEindeVerblijfstitel,
            final Lo3Datum ingangsdatumVerblijfstitel,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(
                aanduidingVerblijfstitelCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE012, Lo3ElementEnum.ELEMENT_3910));
        controleerCode(
                aanduidingVerblijfstitelCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3910));

        controleerDatum(
                datumEindeVerblijfstitel,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3920));

        controleerAanwezig(
                ingangsdatumVerblijfstitel,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE012, Lo3ElementEnum.ELEMENT_3930));
        controleerDatum(
                ingangsdatumVerblijfstitel,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_3930));
    }

}
