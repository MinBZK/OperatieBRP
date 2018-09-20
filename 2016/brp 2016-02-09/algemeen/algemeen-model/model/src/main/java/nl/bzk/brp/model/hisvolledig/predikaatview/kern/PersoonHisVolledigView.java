/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HisVolledigComparatorFactory;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AdministratieveHandelingComparator;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Persoon.
 */
public final class PersoonHisVolledigView extends AbstractPersoonHisVolledigView implements PersoonHisVolledig {

    /**
     * Cache betrokkenheden.
     */
    private Set<BetrokkenheidHisVolledigView> gesorteerdeBetrokkenheden;
    private String                            objectSleutel;
    private String                            communicatieID;
    private boolean                           magLeveren;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonHisVolledig persoon
     * @param predikaat          predikaat
     */
    public PersoonHisVolledigView(final PersoonHisVolledig persoonHisVolledig, final Predicate predikaat) {
        this(persoonHisVolledig, predikaat, null);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonHisVolledig               persoon
     * @param predikaat                        predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public PersoonHisVolledigView(final PersoonHisVolledig persoonHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BetrokkenheidHisVolledigView> getBetrokkenheden() {
        if (gesorteerdeBetrokkenheden == null) {
            final Set<? extends BetrokkenheidHisVolledig> betrokkenheden = super.getBetrokkenheden();
            gesorteerdeBetrokkenheden = new TreeSet<>(HisVolledigComparatorFactory.getComparatorVoorBetrokkenheid());

            for (final BetrokkenheidHisVolledig betrokkenheid : betrokkenheden) {
                gesorteerdeBetrokkenheden.add((BetrokkenheidHisVolledigView) betrokkenheid);
            }
        }
        return gesorteerdeBetrokkenheden;
    }

    /**
     * Geeft de betrokkenheden terug die geleverd mogen worden. Method wordt aangeroepen door JiBx binding
     *
     * @return betrokkenheden voor leveren
     */
    public Set<BetrokkenheidHisVolledigView> getBetrokkenhedenVoorLeveren() {
        final Set<BetrokkenheidHisVolledigView> teLeverenBetrokkenheden = new LinkedHashSet<>();

        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigView.isMagLeveren()) {
                teLeverenBetrokkenheden.add(betrokkenheidHisVolledigView);
            }
        }

        return teLeverenBetrokkenheden;
    }

    @Override
    public boolean heeftBetrokkenhedenVoorLeveren() {
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : getBetrokkenheden()) {
            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            if (relatie.isMagLeveren()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean heeftBetrokkenheden() {
        return !getBetrokkenheden().isEmpty();
    }

    /**
     * Controleert of er groep elementen zijn die geleverd mogen worden. Maw: alle groepen worden afgelopen om te checken of de onderliggende attributen
     * geleverd mogen worden. Als tenminste 1 attribuut geleverd mag worden geeft deze methode true terug.
     *
     * @return Boolean true als er groepen zijn die geleverd mogen worden, anders false.
     */
    private boolean heeftGroepElementenDieGeleverdMogenWorden() {
        return !getAlleHistorieRecords().isEmpty();
    }

    /**
     * Geeft een lijst van alle HistrieEntiteit elementen vanuit de betrokkenheden. Dit zijn de elementen van de betrokkenheid, de bijbehorende relatie en
     * hoofdgroepen van de betrokken personen.
     *
     * @return De lijst van HistorieEntiteit elementen.
     */
    private List<HistorieEntiteit> getAlleHistorieRecordsVanuitBetrokkenheden() {
        final List<HistorieEntiteit> resultaat = new ArrayList<>();
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : getBetrokkenheden()) {
            if (betrokkenheidHisVolledig instanceof KindHisVolledig) {
                geefAlleHistorieRecordsVoorKindBetrokkenheid(resultaat, betrokkenheidHisVolledig);
            } else if (betrokkenheidHisVolledig instanceof OuderHisVolledig) {
                geefAlleHistorieRecordsVoorOuderBetrokkenheid(resultaat, (OuderHisVolledig) betrokkenheidHisVolledig);
            } else if (betrokkenheidHisVolledig instanceof PartnerHisVolledig) {
                geefAlleHistorieRecordsVoorPartnerBetrokkenheid(resultaat, betrokkenheidHisVolledig);
            }
        }
        return resultaat;
    }

    /**
     * Geeft alle historierecords voor een partner-betrokkenheid.
     *
     * @param resultaat                De historie-records.
     * @param betrokkenheidHisVolledig De betrokkenheid.
     */
    private void geefAlleHistorieRecordsVoorPartnerBetrokkenheid(final List<HistorieEntiteit> resultaat,
        final BetrokkenheidHisVolledig betrokkenheidHisVolledig)
    {
        resultaat.addAll(betrokkenheidHisVolledig.getRelatie().getRelatieHistorie().getHistorie());
        for (final BetrokkenheidHisVolledig betrokkene : betrokkenheidHisVolledig.getRelatie().getBetrokkenheden()) {
            if (betrokkene instanceof PartnerHisVolledig) {
                final PersoonHisVolledigView partnerPersoon = (PersoonHisVolledigView) betrokkene.getPersoon();
                final boolean betrokkenPersoonIsNietPersoonZelf = partnerPersoon != null && !partnerPersoon.getID().equals(getID());
                if (betrokkenPersoonIsNietPersoonZelf) {
                    resultaat.addAll(partnerPersoon.getIdentificerendeHistorieRecords());
                }
            }
        }
    }

    /**
     * Geeft alle historierecords voor een ouder-betrokkenheid.
     *
     * @param resultaat                De historie-records.
     * @param betrokkenheidHisVolledig De betrokkenheid.
     */
    private void geefAlleHistorieRecordsVoorOuderBetrokkenheid(final List<HistorieEntiteit> resultaat, final OuderHisVolledig betrokkenheidHisVolledig) {
        resultaat.addAll(betrokkenheidHisVolledig.getOuderOuderlijkGezagHistorie().getHistorie());
        resultaat.addAll(betrokkenheidHisVolledig.getOuderOuderschapHistorie().getHistorie());
        for (final BetrokkenheidHisVolledig betrokkenheidInRelatie : betrokkenheidHisVolledig.getRelatie().getBetrokkenheden()) {
            if (betrokkenheidInRelatie instanceof KindHisVolledig) {
                final PersoonHisVolledigView kindPersoon =
                    (PersoonHisVolledigView) betrokkenheidInRelatie.getPersoon();
                final boolean betrokkenPersoonIsNietPersoonZelf = kindPersoon != null && !kindPersoon.getID().equals(getID());
                if (betrokkenPersoonIsNietPersoonZelf) {
                    resultaat.addAll(kindPersoon.getIdentificerendeHistorieRecords());
                }
            }
        }
    }

    /**
     * Geeft alle historierecords voor een kind-betrokkenheid.
     *
     * @param resultaat                De historie-records.
     * @param betrokkenheidHisVolledig De betrokkenheid.
     */
    private void geefAlleHistorieRecordsVoorKindBetrokkenheid(final List<HistorieEntiteit> resultaat,
        final BetrokkenheidHisVolledig betrokkenheidHisVolledig)
    {
        for (final BetrokkenheidHisVolledig betrokkene : betrokkenheidHisVolledig.getRelatie().getBetrokkenheden()) {
            if (betrokkene instanceof OuderHisVolledig) {
                final OuderHisVolledig ouderHisVolledig = (OuderHisVolledig) betrokkene;
                resultaat.addAll(ouderHisVolledig.getOuderOuderlijkGezagHistorie().getHistorie());
                resultaat.addAll(ouderHisVolledig.getOuderOuderschapHistorie().getHistorie());

                final PersoonHisVolledigView ouderPersoon =
                    (PersoonHisVolledigView) ouderHisVolledig.getPersoon();
                final boolean betrokkenPersoonIsNietPersoonZelf = ouderPersoon != null && !ouderPersoon.getID().equals(getID());
                if (betrokkenPersoonIsNietPersoonZelf) {
                    resultaat.addAll(ouderPersoon.getIdentificerendeHistorieRecords());
                }
            }
        }
    }

    /**
     * Geeft alle HistorieEntiteit elementen vanuit de lijsten van deze persoon.
     *
     * @return De lijst van historie entiteit elementen.
     */
    private List<HistorieEntiteit> getAlleHistorieRecordsVanuitLijsten() {
        final List<HistorieEntiteit> resultaat = new ArrayList<>();
        for (final PersoonAdresHisVolledig adresHisVolledig : getAdressen()) {
            resultaat.addAll(adresHisVolledig.getPersoonAdresHistorie().getHistorie());
        }
        for (final PersoonGeslachtsnaamcomponentHisVolledig geslachtsnaamcomponent : getGeslachtsnaamcomponenten()) {
            resultaat.addAll(geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorie().getHistorie());
        }
        for (final PersoonIndicatieHisVolledig indicatieHisVolledig : getIndicaties()) {
            resultaat.addAll(indicatieHisVolledig.getPersoonIndicatieHistorie().getHistorie());
        }
        for (final PersoonNationaliteitHisVolledig nationaliteitHisVolledig : getNationaliteiten()) {
            resultaat.addAll(nationaliteitHisVolledig.getPersoonNationaliteitHistorie().getHistorie());
        }
        for (final PersoonReisdocumentHisVolledig reisdocumentHisVolledig : getReisdocumenten()) {
            resultaat.addAll(reisdocumentHisVolledig.getPersoonReisdocumentHistorie().getHistorie());
        }
        for (final PersoonVoornaamHisVolledig voornaamHisVolledig : getVoornamen()) {
            resultaat.addAll(voornaamHisVolledig.getPersoonVoornaamHistorie().getHistorie());
        }
        for (final PersoonVerificatieHisVolledig verificatieHisVolledig : getVerificaties()) {
            resultaat.addAll(verificatieHisVolledig.getPersoonVerificatieHistorie().getHistorie());
        }
        return resultaat;
    }

    /**
     * Controleert of de persoon lijstelementen heeft die geleverd mogen worden.
     *
     * @return Boolean true als er tenminste 1 onderliggend attribuut is dat geleverd mag worden, anders false.
     */
    private boolean heeftLijstElementenDieGeleverdMogenWorden() {
        return !getAlleHistorieRecordsVanuitLijsten().isEmpty();
    }

    /**
     * Geeft een lijst van écht alle his elementen van een persoon. Dit zijn alle groepen (identificatienrs, etc), alle lijst-groepen (adressen, etc), alle
     * betrokkenheden (ouderschap, etc), relaties (huwelijk, etc) en identificerende groepen van betrokken personen plus alle onderzoeken. Kortom: alle
     * elementen die eventueel in een leverbericht terecht kunnen komen.
     *
     * @return De totale lijst van his elementen.
     */
    public List<HistorieEntiteit> getTotaleLijstVanHisElementenOpPersoonsLijst() {
        final List<HistorieEntiteit> resultaat = new ArrayList<>(getAlleHistorieRecords());
        resultaat.addAll(getAlleHistorieRecordsVanuitBetrokkenheden());
        resultaat.addAll(getAlleHistorieRecordsVanuitLijsten());
        resultaat.addAll(getAlleHistorieRecordsVanuitOnderzoeken());
        return resultaat;
    }


    /**
     * Controleert of een persoon geleverd mag worden. Hierbij wordt gekeken of er tenminste 1 attribuut is dat geleverd mag worden binnen de groepen van
     * de persoon, de groepen binnen de lijsten van der persoon of de betrokkenheden van de persoon (en diens ondeliggende relatie/personen).
     *
     * @return Boolean true als de persoon geleverd mag worden, anders false.
     */
    public boolean isMagGeleverdWorden() {
        return heeftGroepElementenDieGeleverdMogenWorden() || heeftLijstElementenDieGeleverdMogenWorden()
            || heeftBetrokkenhedenVoorLeveren();
    }

    /**
     * Geeft de object sleutel terug.
     *
     * @return objectSleutel
     */
    public String getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * Zet de objectSleutel.
     *
     * @param objectSleutel de te zetten sleutel.
     */
    public void setObjectSleutel(final String objectSleutel) {
        this.objectSleutel = objectSleutel;
    }

    /**
     * Wordt gebruikt door Jibx voor betrokkene personen. Let wel op, bij de betrokkenen kijken we altijd naar de actuele stand in de historie.
     *
     * @return true indien er een actuele indicatie verstrekkingsbeperking is.
     */
    public boolean heeftVolledigeVerstrekkingsbeperking() {
        boolean resultaat = false;
        for (final PersoonIndicatieHisVolledig<?> persoonIndicatie : getIndicaties()) {
            if (SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING == persoonIndicatie.getSoort().getWaarde()
                && persoonIndicatie.getPersoonIndicatieHistorie().getActueleRecord() != null)
            {
                resultaat = true;
            }
        }
        return resultaat;
    }

    /**
     * Retourneert het actuele voorkomen van de groep verstrekkingsbeperking.
     *
     * @return actuele voorkomen van de groep verstrekkingsbeperking.
     */
    public HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel getIndicatieVolledigeVerstrekkingsbeperkingActueleRecord() {
        HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel resultaat = null;
        for (final PersoonIndicatieHisVolledig<?> persoonIndicatie : getIndicaties()) {
            if (SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING == persoonIndicatie.getSoort().getWaarde()) {
                resultaat =
                    (HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel) persoonIndicatie
                        .getPersoonIndicatieHistorie().getActueleRecord();
            }
        }
        return resultaat;
    }

    @Override
    public KindHisVolledigView getKindBetrokkenheid() {
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigView instanceof KindHisVolledigView) {
                return (KindHisVolledigView) betrokkenheidHisVolledigView;
            }
        }
        return null;
    }

    @Override
    public Set<OuderHisVolledigView> getOuderBetrokkenheden() {
        final Set<OuderHisVolledigView> resultaat = new TreeSet<>(HisVolledigComparatorFactory.getComparatorVoorBetrokkenheid());
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigView instanceof OuderHisVolledigView) {
                resultaat.add((OuderHisVolledigView) betrokkenheidHisVolledigView);
            }
        }
        return resultaat;
    }

    @Override
    public Set<PartnerHisVolledigView> getPartnerBetrokkenheden() {
        final Set<PartnerHisVolledigView> resultaat = new TreeSet<>(HisVolledigComparatorFactory.getComparatorVoorBetrokkenheid());
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigView instanceof PartnerHisVolledigView) {
                resultaat.add((PartnerHisVolledigView) betrokkenheidHisVolledigView);
            }
        }
        return resultaat;
    }

    /**
     * Deze methode is overschreven vanuit de abstracte ivm de sortering.
     *
     * @return De gesorteerde lijst van indicaties.
     */
    @Override
    public Set<PersoonIndicatieHisVolledigView> getIndicaties() {
        final Set<PersoonIndicatieHisVolledigView> gesorteerdeIndicaties = new TreeSet<>(HisVolledigComparatorFactory.getComparatorVoorPersoonIndicatie());

        for (final PersoonIndicatieHisVolledig indicatieHisVolledig : super.getIndicaties()) {
            gesorteerdeIndicaties.add((PersoonIndicatieHisVolledigView) indicatieHisVolledig);
        }
        return gesorteerdeIndicaties;
    }

    /**
     * @return Retourneert alle historie records van niet identificerende groepen
     * @see #getAlleHistorieRecords()
     * @see #getIdentificerendeHistorieRecords()
     */
    public Set<HistorieEntiteit> getNietIdentificerendeHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();

        resultaat.addAll(getPersoonInschrijvingHistorie().getHistorie());
        resultaat.addAll(getPersoonNummerverwijzingHistorie().getHistorie());
        resultaat.addAll(getPersoonBijhoudingHistorie().getHistorie());
        resultaat.addAll(getPersoonOverlijdenHistorie().getHistorie());
        resultaat.addAll(getPersoonNaamgebruikHistorie().getHistorie());
        resultaat.addAll(getPersoonMigratieHistorie().getHistorie());
        resultaat.addAll(getPersoonVerblijfsrechtHistorie().getHistorie());
        resultaat.addAll(getPersoonUitsluitingKiesrechtHistorie().getHistorie());
        resultaat.addAll(getPersoonDeelnameEUVerkiezingenHistorie().getHistorie());
        resultaat.addAll(getPersoonPersoonskaartHistorie().getHistorie());
        resultaat.addAll(getPersoonAfgeleidAdministratiefHistorie().getHistorie());
        return resultaat;
    }

    /**
     * @return Retourneert alle historie records van identificerende groepen.
     * @see #getAlleHistorieRecords()
     * @see #getNietIdentificerendeHistorieRecords()
     */
    public Set<HistorieEntiteit> getIdentificerendeHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        resultaat.addAll(getPersoonIdentificatienummersHistorie().getHistorie());
        resultaat.addAll(getPersoonSamengesteldeNaamHistorie().getHistorie());
        resultaat.addAll(getPersoonGeboorteHistorie().getHistorie());
        resultaat.addAll(getPersoonGeslachtsaanduidingHistorie().getHistorie());
        return resultaat;
    }

    public String getCommunicatieID() {
        return communicatieID;
    }

    public void setCommunicatieID(final String communicatieID) {
        this.communicatieID = communicatieID;
    }

    @Override
    public void voegPredikaatToe(final Predicate predikaat) {
        // Zet gesorteerdeBetrokkenheden op null om de cache van de betrokkenheden te invalideren. In de cache zitten namelijk nog de oude predikaten.
        // getBetrokkenheden() zet de nieuwe predikaten op de betrokken personen.
        gesorteerdeBetrokkenheden = null;

        super.voegPredikaatToe(predikaat);
    }

    /**
     * Voeg een predikaat toe waarbij je geen rekening houdt met de gecachede betrokkenheden.
     *
     * @param predikaat predikaat
     */
    public void voegPredikaatToeZonderBetrokkenhedenReset(final Predicate predikaat) {
        super.voegPredikaatToe(predikaat);
    }

    /**
     * Test method voor jibx om te bepalen of deze persoon administratieve handelingen heeft voor de verantwoording.
     *
     * @return true indien er administratieve handelingen zijn, anders false.
     */
    public boolean heeftAdministratieveHandelingenVoorLeveren() {
        if (getAdministratieveHandelingen() != null) {
            for (final AdministratieveHandelingHisVolledig administratieveHandeling : getAdministratieveHandelingen()) {
                if (administratieveHandeling.isMagGeleverdWorden()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AdministratieveHandelingHisVolledig> getAdministratieveHandelingen() {
        final List<AdministratieveHandelingHisVolledig> resultaat = new ArrayList<>(persoon.getAdministratieveHandelingen().size());
        for (final AdministratieveHandelingHisVolledig administratieveHandelingHisVolledig : persoon.getAdministratieveHandelingen()) {
            resultaat.add(new AdministratieveHandelingHisVolledigView(administratieveHandelingHisVolledig, getPredikaat()));
        }
        Collections.sort(resultaat, new AdministratieveHandelingComparator());
        return resultaat;
    }

    @Override
    public Set<HuwelijkGeregistreerdPartnerschapHisVolledigView> getHuwelijkGeregistreerdPartnerschappen() {
        final Set<HuwelijkGeregistreerdPartnerschapHisVolledigView> hgps = new HashSet<>();
        for (final PartnerHisVolledigView partnerHisVolledig : getPartnerBetrokkenheden()) {
            hgps.add((HuwelijkGeregistreerdPartnerschapHisVolledigView) partnerHisVolledig.getRelatie());
        }

        return hgps;
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return getPersoon().getVerwerkingssoort();
    }

    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        getPersoon().setVerwerkingssoort(verwerkingssoort);
    }

    public boolean isMagLeveren() {
        return magLeveren;
    }

    public void setMagLeveren(final boolean magLeveren) {
        this.magLeveren = magLeveren;
    }

    /**
     * Geeft de persoonhisvolledig terug waarop de view gebaseerd is.
     *
     * @return persoon
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * Heeft persoon onderzoekshistorie. Gebruikt voor levering binding.
     *
     * @return of de persoon onderzoekshistorie heeft die geleverd moet worden.
     * @brp.bedrijfregels R1563
     */
    @Regels(Regel.R1563)
    public boolean heeftOnderzoekshistorieVoorLeveren() {
        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : getOnderzoeken()) {
            if (((PersoonOnderzoekHisVolledigView) persoonOnderzoekHisVolledig).heeftGegevensInOnderzoek() && !persoonOnderzoekHisVolledig.getOnderzoek()
                .getOnderzoekHistorie().isLeeg())
            {
                return true;
            }
        }
        return false;
    }

    private Collection<HistorieEntiteit> getAlleHistorieRecordsVanuitOnderzoeken() {
        final List<HistorieEntiteit> resultaat = new ArrayList<>();
        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : getOnderzoeken()) {
            if (persoonOnderzoekHisVolledig.getOnderzoek() != null) {
                final FormeleHistorieSet<HisOnderzoekModel> onderzoekHistorie = persoonOnderzoekHisVolledig.getOnderzoek().getOnderzoekHistorie();
                resultaat.addAll(onderzoekHistorie.getHistorie());
            }
        }
        return resultaat;
    }

    /**
     * Returns a list of all "Objecten" van de persoon.
     *
     * @return List<ElementIdentificeerbaar>
     */
    public List<ElementIdentificeerbaar> lijstVanObjecten() {
        final List<ElementIdentificeerbaar> resultaat = new ArrayList<>();
        resultaat.addAll(this.getAdressen());
        resultaat.addAll(this.getNationaliteiten());
        resultaat.addAll(this.getBetrokkenheden());
        resultaat.addAll(this.getGeslachtsnaamcomponenten());
        resultaat.addAll(this.getHuwelijkGeregistreerdPartnerschappen());
        resultaat.addAll(this.getBetrokkenhedenVoorLeveren());
        resultaat.addAll(this.getOuderBetrokkenheden());
        resultaat.addAll(this.getPartnerBetrokkenheden());
        resultaat.addAll(this.getReisdocumenten());
        resultaat.addAll(this.getVoornamen());
        resultaat.addAll(this.getVerificaties());
        resultaat.add(this);
        return resultaat;
    }
}
