/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Format;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3InschrijvingFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Wijzigingen voor categorie 07: inschrijving.
 */
public final class Lo3WijzigingenCategorie07 extends Lo3Wijzigingen<Lo3InschrijvingInhoud> {

    private static final Lo3InschrijvingFormatter FORMATTER = new Lo3InschrijvingFormatter();

    private Lo3Categorie<Lo3InschrijvingInhoud> verificatie;

    /**
     * Default constructor.
     */
    public Lo3WijzigingenCategorie07() {
        super(Lo3CategorieEnum.CATEGORIE_07, FORMATTER);
    }

    @Override
    protected void laatsteActieInhoud(final Actie actieInhoud, final MetaRecord historie, final Lo3Categorie<Lo3InschrijvingInhoud> inhoud) {
        if (historie.getParentGroep().getGroepElement().equals(VerificatieMapper.GROEP_ELEMENT)) {
            verificatie = inhoud;
        }
    }

    /**
     * leegVerificatie.
     */
    void leegVerificatie() {
        verificatie = null;
    }

    @Override
    protected void laatsteActieAanpassingGeldigheid(final Actie actieInhoud, final MetaRecord historie, final Lo3Categorie<Lo3InschrijvingInhoud> inhoud) {
        if (historie.getParentGroep().getGroepElement().equals(VerificatieMapper.GROEP_ELEMENT)) {
            verificatie = null;

        }
    }

    @Override
    protected void vulDefaults(final Lo3CategorieWaarde actueleCategorie, final Lo3CategorieWaarde historischeCategorie) {
        vulDefaults(actueleCategorie);
        vulDefaults(historischeCategorie);
    }

    private void vulDefaults(final Lo3CategorieWaarde categorie) {
        if (categorie.getElement(Lo3ElementEnum.ELEMENT_7010) == null || "".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_7010))) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_7010, "0");
        }

        if (".".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_6720)) || "".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_6720))) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_6720, null);
        }

        categorie.addElement(Lo3ElementEnum.ELEMENT_8110, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8120, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8210, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8220, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8230, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8410, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8510, null);
        categorie.addElement(Lo3ElementEnum.ELEMENT_8610, null);

        if (verificatie != null) {
            if (isLeeg(categorie, Lo3ElementEnum.ELEMENT_7110)) {
                categorie.addElement(Lo3ElementEnum.ELEMENT_7110, Lo3Format.format(verificatie.getInhoud().getDatumVerificatie()));
            }
            if (isLeeg(categorie, Lo3ElementEnum.ELEMENT_7120)) {
                categorie.addElement(Lo3ElementEnum.ELEMENT_7120, Lo3Format.format(verificatie.getInhoud().getOmschrijvingVerificatie()));
            }
            if (isLeeg(categorie, Lo3ElementEnum.ELEMENT_8810)) {
                categorie.addElement(Lo3ElementEnum.ELEMENT_8810, Lo3Format.format(verificatie.getDocumentatie().getRniDeelnemerCode()));
            }
        }
    }

    private boolean isLeeg(final Lo3CategorieWaarde categorie, final Lo3ElementEnum element) {
        return categorie.getElement(element) == null || categorie.getElement(element).isEmpty();
    }
}
