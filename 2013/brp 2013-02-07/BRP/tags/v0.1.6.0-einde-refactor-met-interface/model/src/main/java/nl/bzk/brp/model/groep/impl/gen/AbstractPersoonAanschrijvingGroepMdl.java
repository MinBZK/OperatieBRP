/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonAanschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.statisch.WijzeGebruikGeslachtsnaam;


/**
 * .
 *
 */
public abstract class AbstractPersoonAanschrijvingGroepMdl extends AbstractGroep implements PersoonAanschrijvingGroepBasis {

    private WijzeGebruikGeslachtsnaam        gebruikGeslachtsnaam;
    private JaNee       indAanschrijvenMetAdellijkeTitel;
    private JaNee       indAanschrijvingAlgorthmischAfgeleid;
    private Predikaat   predikaat;
    private Voornaam    voornamen;
    private Voorvoegsel voorvoegsel;
    private ScheidingsTeken scheidingsteken;
    private GeslachtsnaamComponent geslachtsnaam;


    @Override
    public WijzeGebruikGeslachtsnaam getGebruikGeslachtsnaam() {
        return this.gebruikGeslachtsnaam;
    }

    @Override
    public JaNee getIndAanschrijvenMetAdellijkeTitel() {
        return this.indAanschrijvenMetAdellijkeTitel;
    }

    @Override
    public JaNee getIndAanschrijvingAlgorthmischAfgeleid() {
        return this.indAanschrijvingAlgorthmischAfgeleid;
    }

    @Override
    public Predikaat getPredikaat() {
        return this.predikaat;
    }

    @Override
    public Voornaam getVoornamen() {
        return this.voornamen;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return this.voorvoegsel;
    }

    @Override
    public ScheidingsTeken getScheidingsteken() {
        return this.scheidingsteken;
    }

    @Override
    public GeslachtsnaamComponent getGeslachtsnaam() {
        return this.geslachtsnaam;
    }
}
