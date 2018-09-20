/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 06: Overlijden.
 * 
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3OverlijdenPrecondities extends AbstractLo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3OverlijdenInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie055(stapel);
        controleerPreconditie056(stapel);

        for (final Lo3Categorie<Lo3OverlijdenInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3OverlijdenInhoud> categorie) {
        final Lo3OverlijdenInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 08: Overlijden
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP08, inhoud)) {
            controleerGroep08Overlijden(inhoud.getDatum(), inhoud.getGemeenteCode(), inhoud.getLandCode(), herkomst);
        }

        // Groep 88: RNI-deelnemer
        if (isRNIDeelnemerAanwezig(documentatie)) {
            controleerGroep88RNIDeelnemer(documentatie.getRniDeelnemerCode(), herkomst);
        }
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB002)
    @Preconditie({SoortMeldingCode.PRE009, SoortMeldingCode.PRE010, SoortMeldingCode.PRE026 })
    private void controleerGroep08Overlijden(
        final Lo3Datum datum,
        final Lo3GemeenteCode gemeenteCode,
        final Lo3LandCode landCode,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(datum, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE009, Lo3ElementEnum.ELEMENT_0810));
        controleerDatum(datum, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0810));

        controleerAanwezig(
            gemeenteCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_8220));

        controleerAanwezig(landCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE009, Lo3ElementEnum.ELEMENT_0830));
        controleerCode(landCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE010, Lo3ElementEnum.ELEMENT_0830));

        if (landCode != null && landCode.isOnbekend()) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB002, null);
        }

        controleerNederlandseGemeente(
            landCode,
            gemeenteCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE026, Lo3ElementEnum.ELEMENT_0830));
    }
}
