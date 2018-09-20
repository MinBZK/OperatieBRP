/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.Scheidingsteken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAanschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;


/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonAanschrijvingGroepBericht extends AbstractGroepBericht implements
        PersoonAanschrijvingGroepBasis
{

    private WijzeGebruikGeslachtsnaam gebruikGeslachtsnaam;
    private JaNee                     indAanschrijvenMetAdellijkeTitel;
    private JaNee                     indAanschrijvingAlgorthmischAfgeleid;
    private Predikaat                 predikaat;
    private PredikaatCode             predikaatCode;
    private Voornaam                  voornamen;
    private Voorvoegsel               voorvoegsel;
    private Scheidingsteken scheidingsteken;
    private Geslachtsnaamcomponent geslachtsnaam;
    private AdellijkeTitel            adellijkeTitel;
    private AdellijkeTitelCode        adellijkeTitelCode;

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

    public PredikaatCode getPredikaatCode() {
        return predikaatCode;
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
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public Geslachtsnaamcomponent getGeslachtsnaam() {
        return geslachtsnaam;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    public AdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }


    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    public void setPredikaatCode(final PredikaatCode predikaatCode) {
        this.predikaatCode = predikaatCode;
    }

    public void setGebruikGeslachtsnaam(final WijzeGebruikGeslachtsnaam gebruikGeslachtsnaam) {
        this.gebruikGeslachtsnaam = gebruikGeslachtsnaam;
    }

    public void setIndAanschrijvenMetAdellijkeTitel(final JaNee indAanschrijvenMetAdellijkeTitel) {
        this.indAanschrijvenMetAdellijkeTitel = indAanschrijvenMetAdellijkeTitel;
    }

    public void setIndAanschrijvingAlgorthmischAfgeleid(final JaNee indAanschrijvingAlgorthmischAfgeleid) {
        this.indAanschrijvingAlgorthmischAfgeleid = indAanschrijvingAlgorthmischAfgeleid;
    }

    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    public void setVoornamen(final Voornaam voornamen) {
        this.voornamen = voornamen;
    }

    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public void setGeslachtsnaam(final Geslachtsnaamcomponent geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public void setAdellijkeTitelCode(final AdellijkeTitelCode adellijkeTitelCode) {
        this.adellijkeTitelCode = adellijkeTitelCode;
    }

}
