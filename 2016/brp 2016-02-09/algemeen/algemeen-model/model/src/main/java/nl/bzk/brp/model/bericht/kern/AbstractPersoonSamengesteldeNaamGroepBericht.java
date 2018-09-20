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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroepBasis;

/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een
 * Persoon.
 *
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en
 * Geslachtsnaamcomponent van een Persoon. In uitzonderingssituaties is dat niet mogelijk.
 *
 * De groep Samengestelde naam bevat de naam zoals die is opgebouwd uit de naamgegevens uit de groepen voornaam en
 * geslachtsnaamcomponent. Deze samengestelde gegevens hoeven bij het bijhouden van de groepen voornaam en
 * geslachtsnaamcomponent niet door de voor de bijhouding verantwoordelijke partij te worden ingevoerd. De centrale
 * voorzieningen stellen de gegevens uit de groep samengestelde naam op dat moment samen op basis van de groepen
 * voornaam en geslachtsnaamcomponent volgens het onderstaande voorschrift:
 *
 * Voornamen - de naam zoals opgenomen in de voornaam met volgnummer één, gevolgd de naam zoals opgenomen in de actuele
 * groep voornaam met volgnummer twee, enzovoort. De voornamen worden gescheiden door een spatie. Merk op dat de BRP is
 * voorbereid op het opnemen van voornamen als 'Jan Peter' of 'Aberto di Maria' of 'Wonder op aarde' als één enkele
 * voornaam; in de BRP is het namelijk niet nodig (om conform LO 3.x) de verschillende worden aan elkaar te plakken met
 * een koppelteken. Predicaat - het predicaat dat door de persoon gevoerd wordt voor diens voornaam. Dit komt overeen
 * met het predicaat van de eerste geslachtsnaamcomponent. Indien voor een persoon meerdere predikaten van toepassing
 * is, het predikaar dat voor de voornamen geplaatst mag worden. Adelijke titel - de adelijke titel zoals opgenomen in
 * geslachtsnaamcomponent met volgnummer gelijk aan '1'; Voorvoegsel - het voorvoegsel zoals opgenomen in de
 * geslachtsnaamcomponent met volgnummer gelijk aan '1'; Scheidingsteken - het scheidingsteken zoals opgenomen in de
 * geslachtsnaamcomponent met volgnummer '1'; Geslachtsnaam - bestaande uit de samenvoeging van alle
 * geslachtsnaamcomponenten, inclusief predikaten die niet voor de voornamen worden geplaatst. Ook eventuele adellijke
 * titels die niet voor de gehele geslachtsnaam wordt geplaatst, worden hierin opgenomen.
 *
 * Verplicht aanwezig bij persoon
 *
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van
 * terugwerkende kracht (met andere woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht
 * gelden vanaf de geboorte').
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonSamengesteldeNaamGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        PersoonSamengesteldeNaamGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3557;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3914, 3592, 1969, 3092, 1968, 3309, 3253, 3094);
    private JaNeeAttribuut indicatieAfgeleid;
    private JaNeeAttribuut indicatieNamenreeks;
    private String predicaatCode;
    private PredicaatAttribuut predicaat;
    private VoornamenAttribuut voornamen;
    private String adellijkeTitelCode;
    private AdellijkeTitelAttribuut adellijkeTitel;
    private VoorvoegselAttribuut voorvoegsel;
    private ScheidingstekenAttribuut scheidingsteken;
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Retourneert Predicaat van Samengestelde naam.
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
     * {@inheritDoc}
     */
    @Override
    public VoornamenAttribuut getVoornamen() {
        return voornamen;
    }

    /**
     * Retourneert Adellijke titel van Samengestelde naam.
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
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Zet Afgeleid? van Samengestelde naam.
     *
     * @param indicatieAfgeleid Afgeleid?.
     */
    public void setIndicatieAfgeleid(final JaNeeAttribuut indicatieAfgeleid) {
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    /**
     * Zet Namenreeks? van Samengestelde naam.
     *
     * @param indicatieNamenreeks Namenreeks?.
     */
    public void setIndicatieNamenreeks(final JaNeeAttribuut indicatieNamenreeks) {
        this.indicatieNamenreeks = indicatieNamenreeks;
    }

    /**
     * Zet Predicaat van Samengestelde naam.
     *
     * @param predicaatCode Predicaat.
     */
    public void setPredicaatCode(final String predicaatCode) {
        this.predicaatCode = predicaatCode;
    }

    /**
     * Zet Predicaat van Samengestelde naam.
     *
     * @param predicaat Predicaat.
     */
    public void setPredicaat(final PredicaatAttribuut predicaat) {
        this.predicaat = predicaat;
    }

    /**
     * Zet Voornamen van Samengestelde naam.
     *
     * @param voornamen Voornamen.
     */
    public void setVoornamen(final VoornamenAttribuut voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * Zet Adellijke titel van Samengestelde naam.
     *
     * @param adellijkeTitelCode Adellijke titel.
     */
    public void setAdellijkeTitelCode(final String adellijkeTitelCode) {
        this.adellijkeTitelCode = adellijkeTitelCode;
    }

    /**
     * Zet Adellijke titel van Samengestelde naam.
     *
     * @param adellijkeTitel Adellijke titel.
     */
    public void setAdellijkeTitel(final AdellijkeTitelAttribuut adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    /**
     * Zet Voorvoegsel van Samengestelde naam.
     *
     * @param voorvoegsel Voorvoegsel.
     */
    public void setVoorvoegsel(final VoorvoegselAttribuut voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Zet Scheidingsteken van Samengestelde naam.
     *
     * @param scheidingsteken Scheidingsteken.
     */
    public void setScheidingsteken(final ScheidingstekenAttribuut scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Zet Geslachtsnaamstam van Samengestelde naam.
     *
     * @param geslachtsnaamstam Geslachtsnaamstam.
     */
    public void setGeslachtsnaamstam(final GeslachtsnaamstamAttribuut geslachtsnaamstam) {
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieAfgeleid != null) {
            attributen.add(indicatieAfgeleid);
        }
        if (indicatieNamenreeks != null) {
            attributen.add(indicatieNamenreeks);
        }
        if (predicaat != null) {
            attributen.add(predicaat);
        }
        if (voornamen != null) {
            attributen.add(voornamen);
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
        if (geslachtsnaamstam != null) {
            attributen.add(geslachtsnaamstam);
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
