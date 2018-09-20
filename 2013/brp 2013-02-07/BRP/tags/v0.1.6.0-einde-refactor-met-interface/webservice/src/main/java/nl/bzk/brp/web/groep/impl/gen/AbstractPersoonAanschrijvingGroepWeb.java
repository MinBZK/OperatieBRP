/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonAanschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.statisch.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.web.AbstractGroepWeb;


/**
 * .
 *
 */
public abstract class AbstractPersoonAanschrijvingGroepWeb extends AbstractGroepWeb
    implements PersoonAanschrijvingGroepBasis
{

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
        return gebruikGeslachtsnaam;
    }

    @Override
    public JaNee getIndAanschrijvenMetAdellijkeTitel() {
        return indAanschrijvenMetAdellijkeTitel;
    }

    @Override
    public JaNee getIndAanschrijvingAlgorthmischAfgeleid() {
        return indAanschrijvingAlgorthmischAfgeleid;
    }

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public Voornaam getVoornamen() {
        return voornamen;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public ScheidingsTeken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public GeslachtsnaamComponent getGeslachtsnaam() {
        return geslachtsnaam;
    }
}
