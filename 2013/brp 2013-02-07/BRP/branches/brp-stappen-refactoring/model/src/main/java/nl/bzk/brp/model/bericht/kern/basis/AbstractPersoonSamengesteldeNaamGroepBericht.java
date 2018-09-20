/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonSamengesteldeNaamGroepBasis;


/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een
 * Persoon.
 *
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en
 * Geslachtsnaamcomponent van een Persoon. In uitzonderingssituaties is dat niet mogelijk.
 *
 * Verplicht aanwezig bij persoon
 *
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van
 * terugwerkende kracht (met andere woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht
 * gelden vanaf de geboorte').
 * RvdP 9 jan 2012
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonSamengesteldeNaamGroepBericht extends AbstractGroepBericht implements
        PersoonSamengesteldeNaamGroepBasis
{

    private JaNee           indicatieAlgoritmischAfgeleid;
    private JaNee           indicatieNamenreeks;
    private String          predikaatCode;
    private Predikaat       predikaat;
    private Voornamen       voornamen;
    private String          adellijkeTitelCode;
    private AdellijkeTitel  adellijkeTitel;
    private Voorvoegsel     voorvoegsel;
    private Scheidingsteken scheidingsteken;
    private Geslachtsnaam   geslachtsnaam;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieAlgoritmischAfgeleid() {
        return indicatieAlgoritmischAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     *
     *
     * @return
     */
    public String getPredikaatCode() {
        return predikaatCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voornamen getVoornamen() {
        return voornamen;
    }

    /**
     *
     *
     * @return
     */
    public String getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Geslachtsnaam getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Zet Algoritmisch afgeleid? van Samengestelde naam.
     *
     * @param indicatieAlgoritmischAfgeleid Algoritmisch afgeleid?.
     */
    public void setIndicatieAlgoritmischAfgeleid(final JaNee indicatieAlgoritmischAfgeleid) {
        this.indicatieAlgoritmischAfgeleid = indicatieAlgoritmischAfgeleid;
    }

    /**
     * Zet Namenreeks? van Samengestelde naam.
     *
     * @param indicatieNamenreeks Namenreeks?.
     */
    public void setIndicatieNamenreeks(final JaNee indicatieNamenreeks) {
        this.indicatieNamenreeks = indicatieNamenreeks;
    }

    /**
     *
     *
     * @param predikaatCode
     */
    public void setPredikaatCode(final String predikaatCode) {
        this.predikaatCode = predikaatCode;
    }

    /**
     * Zet Predikaat van Samengestelde naam.
     *
     * @param predikaat Predikaat.
     */
    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    /**
     * Zet Voornamen van Samengestelde naam.
     *
     * @param voornamen Voornamen.
     */
    public void setVoornamen(final Voornamen voornamen) {
        this.voornamen = voornamen;
    }

    /**
     *
     *
     * @param adellijkeTitelCode
     */
    public void setAdellijkeTitelCode(final String adellijkeTitelCode) {
        this.adellijkeTitelCode = adellijkeTitelCode;
    }

    /**
     * Zet Adellijke titel van Samengestelde naam.
     *
     * @param adellijkeTitel Adellijke titel.
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    /**
     * Zet Voorvoegsel van Samengestelde naam.
     *
     * @param voorvoegsel Voorvoegsel.
     */
    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Zet Scheidingsteken van Samengestelde naam.
     *
     * @param scheidingsteken Scheidingsteken.
     */
    public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Zet Geslachtsnaam van Samengestelde naam.
     *
     * @param geslachtsnaam Geslachtsnaam.
     */
    public void setGeslachtsnaam(final Geslachtsnaam geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

}
