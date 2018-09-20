/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaamcomponent;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;


/**
 * Vorm van historie: beiden.
 * Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele geslachtsnaamcomponent (bijv. die met
 * volgnummer 1 voor persoon X) in de loop van de tijd veranderen, dus nog los van eventuele registratiefouten.
 * Er is dus ��k sprake van materi�le historie.
 * RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonGeslachtsnaamcomponentStandaardGroepBasis
{

    private String                 predikaatCode;
    private Predikaat              predikaat;
    private String                 adellijkeTitelCode;
    private AdellijkeTitel         adellijkeTitel;
    private Voorvoegsel            voorvoegsel;
    private Scheidingsteken        scheidingsteken;
    private Geslachtsnaamcomponent naam;

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
    public Geslachtsnaamcomponent getNaam() {
        return naam;
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
     * Zet Predikaat van Standaard.
     *
     * @param predikaat Predikaat.
     */
    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
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
     * Zet Adellijke titel van Standaard.
     *
     * @param adellijkeTitel Adellijke titel.
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    /**
     * Zet Voorvoegsel van Standaard.
     *
     * @param voorvoegsel Voorvoegsel.
     */
    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Zet Scheidingsteken van Standaard.
     *
     * @param scheidingsteken Scheidingsteken.
     */
    public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Zet Naam van Standaard.
     *
     * @param naam Naam.
     */
    public void setNaam(final Geslachtsnaamcomponent naam) {
        this.naam = naam;
    }

}
