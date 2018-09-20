/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroepBasis;

/**
 * Vorm van historie: beiden. Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele
 * geslachtsnaamcomponent (bijv. die met volgnummer 1 voor persoon X) in de loop van de tijd veranderen, dus nog los van
 * eventuele registratiefouten. Er is dus óók sprake van materiële historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        PersoonGeslachtsnaamcomponentStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3598;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3117, 3118, 3030, 3069, 3025);
    private String predicaatCode;
    private PredicaatAttribuut predicaat;
    private String adellijkeTitelCode;
    private AdellijkeTitelAttribuut adellijkeTitel;
    private VoorvoegselAttribuut voorvoegsel;
    private ScheidingstekenAttribuut scheidingsteken;
    private GeslachtsnaamstamAttribuut stam;

    /**
     * Retourneert Predicaat van Standaard.
     *
     * @return Predicaat.
     */
    public String getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PredicaatAttribuut getPredicaat() {
        return predicaat;
    }

    /**
     * Retourneert Adellijke titel van Standaard.
     *
     * @return Adellijke titel.
     */
    public String getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitelAttribuut getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsnaamstamAttribuut getStam() {
        return stam;
    }

    /**
     * Zet Predicaat van Standaard.
     *
     * @param predicaatCode Predicaat.
     */
    public void setPredicaatCode(final String predicaatCode) {
        this.predicaatCode = predicaatCode;
    }

    /**
     * Zet Predicaat van Standaard.
     *
     * @param predicaat Predicaat.
     */
    public void setPredicaat(final PredicaatAttribuut predicaat) {
        this.predicaat = predicaat;
    }

    /**
     * Zet Adellijke titel van Standaard.
     *
     * @param adellijkeTitelCode Adellijke titel.
     */
    public void setAdellijkeTitelCode(final String adellijkeTitelCode) {
        this.adellijkeTitelCode = adellijkeTitelCode;
    }

    /**
     * Zet Adellijke titel van Standaard.
     *
     * @param adellijkeTitel Adellijke titel.
     */
    public void setAdellijkeTitel(final AdellijkeTitelAttribuut adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    /**
     * Zet Voorvoegsel van Standaard.
     *
     * @param voorvoegsel Voorvoegsel.
     */
    public void setVoorvoegsel(final VoorvoegselAttribuut voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Zet Scheidingsteken van Standaard.
     *
     * @param scheidingsteken Scheidingsteken.
     */
    public void setScheidingsteken(final ScheidingstekenAttribuut scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Zet Stam van Standaard.
     *
     * @param stam Stam.
     */
    public void setStam(final GeslachtsnaamstamAttribuut stam) {
        this.stam = stam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (predicaat != null) {
            attributen.add(predicaat);
        }
        if (adellijkeTitel != null) {
            attributen.add(adellijkeTitel);
        }
        if (voorvoegsel != null) {
            attributen.add(voorvoegsel);
        }
        if (scheidingsteken != null) {
            attributen.add(scheidingsteken);
        }
        if (stam != null) {
            attributen.add(stam);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
