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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Preconditie controles voor categorie 06: Overlijden.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3OverlijdenPrecondities extends AbstractLo3Precondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3OverlijdenPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
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

}
