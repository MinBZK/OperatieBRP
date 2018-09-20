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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroepBasis;

/**
 * 1. De gegevens over naamgebruik (voornamen , voorvoegsel etc etc) worden - gecontroleerd - redundant opgeslagen. De
 * reden hiervoor is dat er situaties zijn waarin een afnemer NIET geautoriseerd wordt voor de relaties die een persoon
 * heeft (en dus bijvoorbeeld NIET mag weten wie de partner is), terwijl de afnemer WEL geautoriseerd is voor de
 * gegevens over het naamgebruik, aangezien zij de persoon wel 'juist' moet kunnen aanschrijven. Tot en met het
 * operationele model zijn de gegevens (dus) redundant opgenomen; het is aan een DBA om te beslissen of ook in het
 * uiteindelijke technische model (dan wel de technische modellen) ook sprake is van redundantie, of dat één-en-ander
 * alleen wordt afgeleid.
 *
 * 2. De gegevens in de groep Naamgebruik lijken heel erg op de gegevens in de groep Samengestelde naam. De optie om de
 * groep Samengestelde naam uit te breiden met de gegevens uit de groep Naamgebruik (en deze laatste groep dus eigenlijk
 * te laten vervallen) is vervallen op grond van het volgende argument: het wijzigen van de Samengestelde (formele!)
 * naam is een formeel proces (o.a. de Koning dient de wijziging goed te keuren). De wijziging van Naamgebruik is
 * (veel!) minder formeel.
 *
 * 3. De motivatie voor de groep aanschrijving is als volgt: - Er is (vanuit afnemers) behoefte aan een éénduidige
 * vastlegging van de (wijze) van aanschrijving. Door stuurgroep is daarom besloten om hiertoe een gegevensgroep op te
 * nemen. - Er zijn een aantal situaties die de aanschrijving moet aankunnen, zoals: -- het (casuïstiek!) door de
 * rechter bekrachtigde verzoek voor de weduwe om de naam van haar VOORlaatste partner te mogen voeren. -- de door de
 * Hoge Raad van Adel erkende 'maatschappelijk gebruik' om de echtgenoot van de Baron met Barones aan te duiden, ondanks
 * het feit dat de echtgenote in kwestie zelf geen adellijke titel heeft. Er is voor gekozen om het RESULTAAT hiervan
 * vast te leggen (dus: 'adellijke titel te gebruiken bij aanschrijving'), in plaats van situatie-specifieke indicaties
 * ('indicatie gebruik titel partner' en 'indicatie gebruik naamgegevens voorlaatste ex-partner').
 *
 * 4. In geval van correcties e.d. is het bijhouden van de materiële historie relatief complex, doordat meerdere
 * factoren de (algoritmische) afleiding kunnen beïnvloeden. Tegelijkertijd is er geen business case voor het willen
 * weten "wat had de goede aanschrijving geweest voor x dagen geleden met de kennis van vandaag"; of te wel FORMELE
 * HISTORIE volstaat. Om die reden is de historie aangepast tot alleen formele historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonNaamgebruikGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep, PersoonNaamgebruikGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3487;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3593, 3633, 3703, 3319, 6113, 3355, 3580, 3323);
    private NaamgebruikAttribuut naamgebruik;
    private JaNeeAttribuut indicatieNaamgebruikAfgeleid;
    private String predicaatNaamgebruikCode;
    private PredicaatAttribuut predicaatNaamgebruik;
    private VoornamenAttribuut voornamenNaamgebruik;
    private String adellijkeTitelNaamgebruikCode;
    private AdellijkeTitelAttribuut adellijkeTitelNaamgebruik;
    private VoorvoegselAttribuut voorvoegselNaamgebruik;
    private ScheidingstekenAttribuut scheidingstekenNaamgebruik;
    private GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik;

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamgebruikAttribuut getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * Retourneert Predicaat naamgebruik van Naamgebruik.
     *
     * @return Predicaat naamgebruik.
     */
    public String getPredicaatNaamgebruikCode() {
        return predicaatNaamgebruikCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PredicaatAttribuut getPredicaatNaamgebruik() {
        return predicaatNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoornamenAttribuut getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * Retourneert Adellijke titel naamgebruik van Naamgebruik.
     *
     * @return Adellijke titel naamgebruik.
     */
    public String getAdellijkeTitelNaamgebruikCode() {
        return adellijkeTitelNaamgebruikCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitelAttribuut getAdellijkeTitelNaamgebruik() {
        return adellijkeTitelNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoorvoegselAttribuut getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheidingstekenAttribuut getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Zet Naamgebruik van Naamgebruik.
     *
     * @param naamgebruik Naamgebruik.
     */
    public void setNaamgebruik(final NaamgebruikAttribuut naamgebruik) {
        this.naamgebruik = naamgebruik;
    }

    /**
     * Zet Naamgebruik afgeleid? van Naamgebruik.
     *
     * @param indicatieNaamgebruikAfgeleid Naamgebruik afgeleid?.
     */
    public void setIndicatieNaamgebruikAfgeleid(final JaNeeAttribuut indicatieNaamgebruikAfgeleid) {
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
    }

    /**
     * Zet Predicaat naamgebruik van Naamgebruik.
     *
     * @param predicaatNaamgebruikCode Predicaat naamgebruik.
     */
    public void setPredicaatNaamgebruikCode(final String predicaatNaamgebruikCode) {
        this.predicaatNaamgebruikCode = predicaatNaamgebruikCode;
    }

    /**
     * Zet Predicaat naamgebruik van Naamgebruik.
     *
     * @param predicaatNaamgebruik Predicaat naamgebruik.
     */
    public void setPredicaatNaamgebruik(final PredicaatAttribuut predicaatNaamgebruik) {
        this.predicaatNaamgebruik = predicaatNaamgebruik;
    }

    /**
     * Zet Voornamen naamgebruik van Naamgebruik.
     *
     * @param voornamenNaamgebruik Voornamen naamgebruik.
     */
    public void setVoornamenNaamgebruik(final VoornamenAttribuut voornamenNaamgebruik) {
        this.voornamenNaamgebruik = voornamenNaamgebruik;
    }

    /**
     * Zet Adellijke titel naamgebruik van Naamgebruik.
     *
     * @param adellijkeTitelNaamgebruikCode Adellijke titel naamgebruik.
     */
    public void setAdellijkeTitelNaamgebruikCode(final String adellijkeTitelNaamgebruikCode) {
        this.adellijkeTitelNaamgebruikCode = adellijkeTitelNaamgebruikCode;
    }

    /**
     * Zet Adellijke titel naamgebruik van Naamgebruik.
     *
     * @param adellijkeTitelNaamgebruik Adellijke titel naamgebruik.
     */
    public void setAdellijkeTitelNaamgebruik(final AdellijkeTitelAttribuut adellijkeTitelNaamgebruik) {
        this.adellijkeTitelNaamgebruik = adellijkeTitelNaamgebruik;
    }

    /**
     * Zet Voorvoegsel naamgebruik van Naamgebruik.
     *
     * @param voorvoegselNaamgebruik Voorvoegsel naamgebruik.
     */
    public void setVoorvoegselNaamgebruik(final VoorvoegselAttribuut voorvoegselNaamgebruik) {
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
    }

    /**
     * Zet Scheidingsteken naamgebruik van Naamgebruik.
     *
     * @param scheidingstekenNaamgebruik Scheidingsteken naamgebruik.
     */
    public void setScheidingstekenNaamgebruik(final ScheidingstekenAttribuut scheidingstekenNaamgebruik) {
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
    }

    /**
     * Zet Geslachtsnaamstam naamgebruik van Naamgebruik.
     *
     * @param geslachtsnaamstamNaamgebruik Geslachtsnaamstam naamgebruik.
     */
    public void setGeslachtsnaamstamNaamgebruik(final GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik) {
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (naamgebruik != null) {
            attributen.add(naamgebruik);
        }
        if (indicatieNaamgebruikAfgeleid != null) {
            attributen.add(indicatieNaamgebruikAfgeleid);
        }
        if (predicaatNaamgebruik != null) {
            attributen.add(predicaatNaamgebruik);
        }
        if (voornamenNaamgebruik != null) {
            attributen.add(voornamenNaamgebruik);
        }
        if (adellijkeTitelNaamgebruik != null) {
            attributen.add(adellijkeTitelNaamgebruik);
        }
        if (voorvoegselNaamgebruik != null) {
            attributen.add(voorvoegselNaamgebruik);
        }
        if (scheidingstekenNaamgebruik != null) {
            attributen.add(scheidingstekenNaamgebruik);
        }
        if (geslachtsnaamstamNaamgebruik != null) {
            attributen.add(geslachtsnaamstamNaamgebruik);
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
