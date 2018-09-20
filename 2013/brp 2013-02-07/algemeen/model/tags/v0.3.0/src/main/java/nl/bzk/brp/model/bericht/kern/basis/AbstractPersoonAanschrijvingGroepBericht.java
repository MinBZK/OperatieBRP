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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAanschrijvingGroepBasis;


/**
 * 1. De gegevens over de wijze van aanschrijven (voornamen aanschrijving, voorvoegsel aanschrijving etc etc) worden -
 * gecontroleerd - redundant opgeslagen.
 * De reden hiervoor is dat er situaties zijn waarin een afnemer NIET geautoriseerd wordt voor de relaties die een
 * persoon heeft (en dus bijvoorbeeld NIET mag weten wie de partner is), terwijl de afnemer WEL geautoriseerd is voor de
 * gegevens over de aanschrijving, aangezien zij de persoon wel 'juist' moet kunnen aanschrijven.
 * Tot en met het operationele model zijn de gegevens (dus) redundant opgenomen; het is aan een DBA om te beslissen of
 * ook in het uiteindelijke technische model (dan wel de technische modellen) ook sprake is van redundantie, of dat
 * ��n-en-ander alleen wordt afgeleid.
 * Beslissing d.d. 21 juni 2011, RvdP
 * 2. De gegevens in de groep Aanschrijving lijken heel erg op de gegevens in de groep Samengestelde naam. De optie om
 * de groep Samengestelde naam uit te breiden met de gegevens uit de groep Aanschrijving (en deze laatste groep dus
 * eigenlijk te laten vervallen) is vervallen op grond van het volgende argument: het wijzigen van de Samengestelde
 * (formele!) naam is een formeel proces (o.a. de Koningin dient de wijziging goed te keuren). De wijziging van de
 * Aanschrijving is (veel!) minder formeel.
 * Beslissing d.d. 21 juni 2011, RvdP
 * 3. De groep Aanschrijving gedraagt zich m.b.t. historie in principe net zoals de groep samengestelde naam, en kent
 * dus beide vormen van historie.
 * RvdP 9 jan 2012
 * 4. De motivatie voor de groep aanschrijving is als volgt:
 * - Er is (vanuit afnemers) behoefte aan een ��nduidige vastlegging van de (wijze) van aanschrijving. Door stuurgroep
 * is daarom besloten om hiertoe een gegevensgroep op te nemen.
 * - Er zijn een aantal situaties die de aanschrijving moet aankunnen, zoals:
 * -- het (casu�stiek!) door de rechter bekrachtigde verzoek voor de weduwe om de naam van haar VOORlaatste partner te
 * mogen voeren.
 * -- de door de Hoge Raad van Adel erkende 'maatschappelijk gebruik' om de echtgenoot van de Baron met Barones aan te
 * duiden, ondanks het feit dat de echtgenote in kwestie zelf geen adellijke titel heeft.
 * Er is voor gekozen om het RESULTAAT hiervan vast te leggen (dus: 'adellijke titel te gebruiken bij aanschrijving'),
 * in plaats van situatie-specifieke indicaties ('indicatie gebruik titel partner' en 'indicatie gebruik naamgegevens
 * voorlaatste ex-partner').
 * RvdP 8 juni 2012.
 * 5. In geval van correcties e.d. is het bijhouden van de materi�le historie relatief complex, doordat meerdere
 * factoren de (algoritmische) afleiding kunnen be�nvloeden. Tegelijkertijd is er geen business case voor het willen
 * weten "wat had de goede aanschrijving geweest voor x dagen geleden met de kennis van vandaag"; of te wel FORMELE
 * HISTORIE volstaat. Om die reden is de historie aangepast tot alleen formele historie.
 * RvdP 26 oktober 2012.
 *
 *
 *
 */
public abstract class AbstractPersoonAanschrijvingGroepBericht extends AbstractGroepBericht implements
        PersoonAanschrijvingGroepBasis
{

    private WijzeGebruikGeslachtsnaam naamgebruik;
    private JaNee                     indicatieTitelsPredikatenBijAanschrijven;
    private JaNee                     indicatieAanschrijvingAlgoritmischAfgeleid;
    private String                    predikaatAanschrijvingCode;
    private Predikaat                 predikaatAanschrijving;
    private Voornamen                 voornamenAanschrijving;
    private String                    adellijkeTitelAanschrijvingCode;
    private AdellijkeTitel            adellijkeTitelAanschrijving;
    private Voorvoegsel               voorvoegselAanschrijving;
    private Scheidingsteken           scheidingstekenAanschrijving;
    private Geslachtsnaam             geslachtsnaamAanschrijving;

    /**
     * {@inheritDoc}
     */
    @Override
    public WijzeGebruikGeslachtsnaam getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieTitelsPredikatenBijAanschrijven() {
        return indicatieTitelsPredikatenBijAanschrijven;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieAanschrijvingAlgoritmischAfgeleid() {
        return indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    /**
     * Retourneert Predikaat aanschrijving van Aanschrijving.
     *
     * @return Predikaat aanschrijving.
     */
    public String getPredikaatAanschrijvingCode() {
        return predikaatAanschrijvingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Predikaat getPredikaatAanschrijving() {
        return predikaatAanschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voornamen getVoornamenAanschrijving() {
        return voornamenAanschrijving;
    }

    /**
     * Retourneert Adellijke titel aanschrijving van Aanschrijving.
     *
     * @return Adellijke titel aanschrijving.
     */
    public String getAdellijkeTitelAanschrijvingCode() {
        return adellijkeTitelAanschrijvingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdellijkeTitel getAdellijkeTitelAanschrijving() {
        return adellijkeTitelAanschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Voorvoegsel getVoorvoegselAanschrijving() {
        return voorvoegselAanschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scheidingsteken getScheidingstekenAanschrijving() {
        return scheidingstekenAanschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Geslachtsnaam getGeslachtsnaamAanschrijving() {
        return geslachtsnaamAanschrijving;
    }

    /**
     * Zet Naamgebruik van Aanschrijving.
     *
     * @param naamgebruik Naamgebruik.
     */
    public void setNaamgebruik(final WijzeGebruikGeslachtsnaam naamgebruik) {
        this.naamgebruik = naamgebruik;
    }

    /**
     * Zet Titels predikaten bij aanschrijven? van Aanschrijving.
     *
     * @param indicatieTitelsPredikatenBijAanschrijven Titels predikaten bij aanschrijven?.
     */
    public void setIndicatieTitelsPredikatenBijAanschrijven(final JaNee indicatieTitelsPredikatenBijAanschrijven) {
        this.indicatieTitelsPredikatenBijAanschrijven = indicatieTitelsPredikatenBijAanschrijven;
    }

    /**
     * Zet Aanschrijving algoritmisch afgeleid? van Aanschrijving.
     *
     * @param indicatieAanschrijvingAlgoritmischAfgeleid Aanschrijving algoritmisch afgeleid?.
     */
    public void setIndicatieAanschrijvingAlgoritmischAfgeleid(final JaNee indicatieAanschrijvingAlgoritmischAfgeleid) {
        this.indicatieAanschrijvingAlgoritmischAfgeleid = indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    /**
     * Zet Predikaat aanschrijving van Aanschrijving.
     *
     * @param predikaatAanschrijvingCode Predikaat aanschrijving.
     */
    public void setPredikaatAanschrijvingCode(final String predikaatAanschrijvingCode) {
        this.predikaatAanschrijvingCode = predikaatAanschrijvingCode;
    }

    /**
     * Zet Predikaat aanschrijving van Aanschrijving.
     *
     * @param predikaatAanschrijving Predikaat aanschrijving.
     */
    public void setPredikaatAanschrijving(final Predikaat predikaatAanschrijving) {
        this.predikaatAanschrijving = predikaatAanschrijving;
    }

    /**
     * Zet Voornamen aanschrijving van Aanschrijving.
     *
     * @param voornamenAanschrijving Voornamen aanschrijving.
     */
    public void setVoornamenAanschrijving(final Voornamen voornamenAanschrijving) {
        this.voornamenAanschrijving = voornamenAanschrijving;
    }

    /**
     * Zet Adellijke titel aanschrijving van Aanschrijving.
     *
     * @param adellijkeTitelAanschrijvingCode Adellijke titel aanschrijving.
     */
    public void setAdellijkeTitelAanschrijvingCode(final String adellijkeTitelAanschrijvingCode) {
        this.adellijkeTitelAanschrijvingCode = adellijkeTitelAanschrijvingCode;
    }

    /**
     * Zet Adellijke titel aanschrijving van Aanschrijving.
     *
     * @param adellijkeTitelAanschrijving Adellijke titel aanschrijving.
     */
    public void setAdellijkeTitelAanschrijving(final AdellijkeTitel adellijkeTitelAanschrijving) {
        this.adellijkeTitelAanschrijving = adellijkeTitelAanschrijving;
    }

    /**
     * Zet Voorvoegsel aanschrijving van Aanschrijving.
     *
     * @param voorvoegselAanschrijving Voorvoegsel aanschrijving.
     */
    public void setVoorvoegselAanschrijving(final Voorvoegsel voorvoegselAanschrijving) {
        this.voorvoegselAanschrijving = voorvoegselAanschrijving;
    }

    /**
     * Zet Scheidingsteken aanschrijving van Aanschrijving.
     *
     * @param scheidingstekenAanschrijving Scheidingsteken aanschrijving.
     */
    public void setScheidingstekenAanschrijving(final Scheidingsteken scheidingstekenAanschrijving) {
        this.scheidingstekenAanschrijving = scheidingstekenAanschrijving;
    }

    /**
     * Zet Geslachtsnaam aanschrijving van Aanschrijving.
     *
     * @param geslachtsnaamAanschrijving Geslachtsnaam aanschrijving.
     */
    public void setGeslachtsnaamAanschrijving(final Geslachtsnaam geslachtsnaamAanschrijving) {
        this.geslachtsnaamAanschrijving = geslachtsnaamAanschrijving;
    }

}
