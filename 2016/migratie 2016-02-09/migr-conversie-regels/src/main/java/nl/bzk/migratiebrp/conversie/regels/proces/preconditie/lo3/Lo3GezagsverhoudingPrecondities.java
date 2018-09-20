/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 11: Gezagsverhouding.
 * 
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3GezagsverhoudingPrecondities extends AbstractLo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3GezagsverhoudingInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);
        controleerPreconditie112(stapel);

        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorie) {
        final Lo3GezagsverhoudingInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep32Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP32, inhoud);
        final boolean groep33Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP33, inhoud);

        if (groep32Aanwezig) {
            controleerGroep32GezagMinderjarige(inhoud.getIndicatieGezagMinderjarige(), herkomst);
        }
        if (groep33Aanwezig) {
            controleerGroep33Curatele(inhoud.getIndicatieCurateleregister(), herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final boolean akteAanwezig = isAkteAanwezig(documentatie);
        final boolean documentAanwezig = isDocumentAanwezig(documentatie);

        if (akteAanwezig) {
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (documentAanwezig) {
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

    private void controleerGroep32GezagMinderjarige(final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(
            indicatieGezagMinderjarige,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE078, null));
    }

    private void controleerGroep33Curatele(final Lo3IndicatieCurateleregister indicatieCurateleregister, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(
            indicatieCurateleregister,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_3310));
    }
}
