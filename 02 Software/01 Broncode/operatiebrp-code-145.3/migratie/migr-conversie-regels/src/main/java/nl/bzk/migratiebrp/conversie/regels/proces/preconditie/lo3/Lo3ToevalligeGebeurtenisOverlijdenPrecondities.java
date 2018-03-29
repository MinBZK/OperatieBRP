/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: overlijden.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */

public final class Lo3ToevalligeGebeurtenisOverlijdenPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisOverlijdenPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op categorie niveau.
     * @param categorie categorie
     */
    public void controleerCategorie(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (categorie == null) {
            return;
        }

        final Lo3OverlijdenInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 08: Overlijden
        controleerGroep8Overlijden(categorie, inhoud, herkomst);
        // Groep 81: Akte
        controleerGroep81Akte(categorie);
        // Groep 82: Document
        controleerGroep82Document(categorie);
        // Groep 83: Procedure
        controleerGroep83Procedure(categorie);
        // Groep 84: Onjuist
        controleerGroep84Onj(categorie);
        // Groep 85: Geldigheid
        controleerGroep85Geldigheid(categorie);
        // Groep 86: Geldigheid
        controleerGroep86Geldigheid(categorie);
        // Groep 88: RNI-deelnemer
        controleerGroep88RNIDeelnemer(categorie);
    }

    private void controleerGroep88RNIDeelnemer(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP88, categorie.getDocumentatie())) {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG003, null);
        }
    }

    private void controleerGroep86Geldigheid(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP86, categorie.getHistorie())) {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG009, null);
        }
    }

    private void controleerGroep85Geldigheid(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, categorie.getHistorie())) {
            controleerDatumNietOnbekend(
                    categorie.getHistorie().getIngangsdatumGeldigheid(),
                    Foutmelding.maakMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_8510));
        } else {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG050, null);
        }
    }

    private void controleerGroep84Onj(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP84, categorie.getHistorie())) {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG007, null);
        }
    }

    private void controleerGroep83Procedure(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP83, categorie.getOnderzoek())) {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG006, null);
        }
    }

    private void controleerGroep82Document(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP82, categorie.getDocumentatie())) {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG005, null);
        }
    }

    private void controleerGroep81Akte(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP81, categorie.getDocumentatie())) {
            controleerGroep81Akte(categorie.getDocumentatie(), categorie.getLo3Herkomst());
        } else {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG048, null);
        }
    }

    private void controleerGroep8Overlijden(final Lo3Categorie<Lo3OverlijdenInhoud> categorie, final Lo3OverlijdenInhoud inhoud, final Lo3Herkomst herkomst) {
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP08, inhoud)) {
            controleerGroep08Overlijden(inhoud.getDatum(), inhoud.getGemeenteCode(), inhoud.getLandCode(), herkomst);

            //
            controleerNederland(inhoud.getLandCode(), herkomst, Lo3ElementEnum.ELEMENT_0830);
        } else {
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG013, null);
        }
    }

}
