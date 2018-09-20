/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;


/**
 * .
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonSamengesteldeNaamGroepBericht extends AbstractGroepBericht implements
        PersoonSamengesteldeNaamGroepBasis
{

    private Predikaat              predikaat;
    private PredikaatCode          predikaatCode;
    private AdellijkeTitel         adellijkeTitel;
    private AdellijkeTitelCode     adellijkeTitelCode;
    private Voornaam               voornamen;
    private Voorvoegsel            voorvoegsel;
    private ScheidingsTeken        scheidingsteken;
    private GeslachtsnaamComponent geslachtsnaam;
    private JaNee                  indNamenreeksAlsGeslachtsNaam;
    private JaNee                  indAlgorithmischAfgeleid;

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
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

    @Override
    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
        return indNamenreeksAlsGeslachtsNaam;
    }

    @Override
    public JaNee getIndAlgorithmischAfgeleid() {
        return indAlgorithmischAfgeleid;
    }

    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    public void setVoornamen(final Voornaam voornamen) {
        this.voornamen = voornamen;
    }

    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public void setScheidingsteken(final ScheidingsTeken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public void setGeslachtsnaam(final GeslachtsnaamComponent geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public void setIndNamenreeksAlsGeslachtsNaam(final JaNee indNamenreeksAlsGeslachtsNaam) {
        this.indNamenreeksAlsGeslachtsNaam = indNamenreeksAlsGeslachtsNaam;
    }

    public void setIndAlgorithmischAfgeleid(final JaNee indAlgorithmischAfgeleid) {
        this.indAlgorithmischAfgeleid = indAlgorithmischAfgeleid;
    }

    public PredikaatCode getPredikaatCode() {
        return predikaatCode;
    }

    public AdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }
}
