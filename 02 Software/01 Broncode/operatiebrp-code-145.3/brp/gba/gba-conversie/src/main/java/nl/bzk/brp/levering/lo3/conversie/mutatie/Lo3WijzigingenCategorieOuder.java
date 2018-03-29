/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3OuderFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Wijzigingen voor categorie 02/03: ouder.
 */
public final class Lo3WijzigingenCategorieOuder extends Lo3Wijzigingen<Lo3OuderInhoud> {

    private static final Lo3OuderFormatter FORMATTER = new Lo3OuderFormatter();

    private Lo3CategorieWaarde istVoorkomen;

    /**
     * Default constructor.
     * @param categorie categorie
     */
    public Lo3WijzigingenCategorieOuder(final Lo3CategorieEnum categorie) {
        super(categorie, FORMATTER);
    }

    /**
     * Registreer oudste voorkomen uit IST-stapel.
     * @param oudsteVoorkomenUitIstStapel IST-stapel
     */
    public void registreerOudsteVoorkomenUitIstStapel(final Lo3CategorieWaarde oudsteVoorkomenUitIstStapel) {
        this.istVoorkomen = oudsteVoorkomenUitIstStapel;
    }

    @Override
    protected void vulDefaults(final Lo3CategorieWaarde deActueleCategorie, final Lo3CategorieWaarde deHistorischeCategorie) {
        // Als de actuele categorie wel waarden bevat en de historische categorie niet, dan zitten we op een cornercase.
        // Dan moet de historische categorie worden gevuld vanuit het oudste voorkomen van de ist-stapel
        if (!deActueleCategorie.isEmpty() && deHistorischeCategorie.isEmpty() && istVoorkomen != null) {
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8110, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8110));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8120, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8120));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8210, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8210));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8220, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8220));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8230, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8230));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8310, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8310));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8320, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8320));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8330, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8330));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8410, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8410));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8510, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8510));
            deHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8610, istVoorkomen.getElement(Lo3ElementEnum.ELEMENT_8610));
        }
    }

}
