/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtContext;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.apache.commons.lang.StringUtils;


/**
 * Context klasse die gebruikt wordt in de stappenverwerker om (tussen)resultaten door te geven naar de volgende
 * stap(pen).
 */
public class BijhoudingBerichtContext extends AbstractBerichtContext {

    private AdministratieveHandelingModel                                        administratieveHandeling;

    /**
     * De personen die niet in het bericht voorkomen,
     * maar toch aangemerkt moeten worden als bijgehouden persoon.
     */
    private final Set<PersoonHisVolledigImpl>                                        nietInBerichtMaarWelBijgehoudenPersonen;

    /**
     * De niet ingeschrevenen die niet in het bericht voorkomen, maar wel zijn aangemaakt vanwege een afleiding of
     * omzetting.
     */
    private final Set<PersoonHisVolledigImpl>                                        nietInBerichtMaarWelAangemaakteNietIngeschrevenen;

    /**
     * De personen die, ondanks dat ze in het bericht voorkomen, toch geen bijgehouden persoon zijn.
     */
    private final Set<PersoonHisVolledigImpl>                                        welInBerichtMaarNietBijgehoudenPersonen;

    /**
     * Per identificerende sleutel in het bericht een bijhoudings rootobject (Relatie of Persoon).
     * Dit zijn relaties of personen die al aanwezig zijn in de BRP en naar verwezen wordt d.m.v. een
     * identificerende sleutel.
     */
    private final Map</* IdentificerendeSleutel */String, HisVolledigRootObject> bestaandeBijhoudingsRootObjecten;

    /**
     * Per communicatie ID in het bericht een bijhoudings rootobject wat door de BRP nieuw is aangemaakt binnen
     * dezelfde bericht context. Dus alle relaties of personen die door de BRP nieuw zijn aangemaakt tijdens de bericht
     * verwerking. Zo kunnen deze elders worden opgehaald op basis van een referentieId.
     */
    private final Map</* communicatieID */String, HisVolledigRootObject>         aangemaakteBijhoudingsRootObjecten;

    /**
     * Per bestaande bijhoudings persoon worden ook alle onderzoeken opgeslagen.
     * Zo kunnen deze opgehaald worden op basis van een persoon id.
     */
    private final Map<Integer, List<HisOnderzoekModel>>                          persoonOnderzoeken;

    /**
     * De mapping tussen de acties uit het bericht en de acties die opgeslagen zijn in de database.
     */
    private final Map<ActieBericht, ActieModel>                                  actieMapping;

    /**
     * De bijgehouden documenten tijdens de verwerking. De communicatieId uit de bijgehoudenDocumenten in het bericht
     * is hier gemapped op het opgeslagen document.
     */
    private final Map</* communicatieId */String, DocumentModel>                 bijgehoudenDocumenten;

    private String xmlBericht;

    /**
     * De te locken ids die in de lockstap gebruikt zullen worden om database objecten te locken.
     */
    private final Collection<Integer>                                            lockingIds;

    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtenIds de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param partij de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNummer Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObjecten map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public BijhoudingBerichtContext(final BerichtenIds berichtenIds, final Partij partij,
        final String berichtReferentieNummer, final CommunicatieIdMap identificeerbareObjecten)
    {
        super(berichtenIds, new PartijAttribuut(partij), berichtReferentieNummer, identificeerbareObjecten);

        this.bestaandeBijhoudingsRootObjecten = new HashMap<>();
        this.aangemaakteBijhoudingsRootObjecten = new HashMap<>();
        this.nietInBerichtMaarWelBijgehoudenPersonen = new HashSet<>();
        this.nietInBerichtMaarWelAangemaakteNietIngeschrevenen = new HashSet<>();
        this.welInBerichtMaarNietBijgehoudenPersonen = new HashSet<>();
        this.persoonOnderzoeken = new HashMap<>();
        this.actieMapping = new HashMap<>();
        this.bijgehoudenDocumenten = new HashMap<>();
        this.lockingIds = new ArrayList<>();
    }

    /**
     * Retourneert een bijgehouden document behorende bij de communicatieId van het document uit het bericht.
     *
     * @param communicatieId communicatieId uit de bijgehouden documenten in het bericht.
     * @return opgeslagen document
     */
    public DocumentModel getBijgehoudenDocument(final String communicatieId) {
        return bijgehoudenDocumenten.get(communicatieId);
    }

    /**
     * Voeg een bijgehouden document toe behorende bij het communicatieId bij het bericht.
     *
     * @param communicatieId communicatieId uit het bericht
     * @param documentModel opgeslagen document
     */
    public void voegBijgehoudenDocumentToe(final String communicatieId, final DocumentModel documentModel) {
        bijgehoudenDocumenten.put(communicatieId, documentModel);
    }

    /**
     * Retourneert alle bijgehouden documenten.
     *
     * @return bijgehouden documenten
     */
    public Map<String, DocumentModel> getBijgehoudenDocumenten() {
        return Collections.unmodifiableMap(bijgehoudenDocumenten);
    }

    @Override
    public Long getResultaatId() {
        return getAdministratieveHandelingId();
    }

    /**
     * Voeg een persoon toe die niet in het bericht voorkomt, maar wel als bijgehouden telt.
     *
     * @param persoon persoon
     */
    public void voegNietInBerichtMaarWelBijgehoudenPersoonToe(final PersoonHisVolledigImpl persoon) {
        this.nietInBerichtMaarWelBijgehoudenPersonen.add(persoon);
    }

    /**
     * Voeg een niet ingeschrevene toe die niet in het bericht voorkomt, maar wel is aangemaakt.
     *
     * @param persoon persoon
     */
    public void voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(final PersoonHisVolledigImpl persoon) {
        if (persoon.getSoort().getWaarde() != SoortPersoon.NIET_INGESCHREVENE) {
            throw new IllegalArgumentException(
                    "Alleen niet ingeschrevene personen kunnen worden toegevoegd aan de context als zijnde nieuw "
                        + "aangemaakte niet-ingeschrevenen.");
        }
        this.nietInBerichtMaarWelAangemaakteNietIngeschrevenen.add(persoon);
    }

    /**
     * Voeg een persoon toe die wel in het bericht voorkomt, maar niet als bijgehouden telt.
     *
     * @param persoon persoon
     */
    public void voegWelInBerichtMaarNietBijgehoudenPersoonToe(final PersoonHisVolledigImpl persoon) {
        this.welInBerichtMaarNietBijgehoudenPersonen.add(persoon);
    }

    /**
     * Retourneert verzameling van de bijgehouden personen, dit zijn alle personen in
     * aangemaakteBijhoudingsRootObjecten, deze zijn immers aangemaakt. Tevens alle personen
     * in de lijst met bestaande bestaandeBijhoudingsRootObjecten, aangezien alle personen
     * in het bericht altijd bijgehouden worden.*
     * <p/>
     * * = Behalve als een persoon expliciet is opgegeven als wel in het bericht aanwezig, maar niet bijgehouden. Dit
     * zou bijvoorbeeld bij multi realiteit voor kunnen komen.
     * <p/>
     * Als laatste worden alle 'extra' bijgehouden personen meegenomen. Die komen niet in het bericht voor, maar tellen
     * wel als bijgehouden persoon. Dat kunnen bijvoorbeeld personen zijn die indirect geraakt worden (verbroken
     * relaties etc.) of juridisch bijgehouden personen.
     *
     * @param inclusiefNietIngeschrevenen indicatie om ook de niet ingeschrevenen op terug te geven
     * @return lijst van bijgehouden personen
     */
    public List<PersoonHisVolledigImpl> getBijgehoudenPersonen(final boolean inclusiefNietIngeschrevenen) {
        final List<PersoonHisVolledigImpl> personenDieBijgehoudenZijn = new ArrayList<>();

        // Aangemaakte personen zijn altijd bijgehouden.
        personenDieBijgehoudenZijn.addAll(vindAangemaaktePersonen(inclusiefNietIngeschrevenen));

        // Bestaande personen uit het bericht zijn altijd bijgehouden, behalve als expliciet 'afgemeld'.
        personenDieBijgehoudenZijn.addAll(vindBestaandePersonen(inclusiefNietIngeschrevenen));

        if (inclusiefNietIngeschrevenen) {
            personenDieBijgehoudenZijn.addAll(this.nietInBerichtMaarWelAangemaakteNietIngeschrevenen);
        }

        // Extra toegevoegde bijgehouden personen ook toevoegen.
        personenDieBijgehoudenZijn.addAll(this.nietInBerichtMaarWelBijgehoudenPersonen);

        return personenDieBijgehoudenZijn;
    }

    private List<PersoonHisVolledigImpl> vindAangemaaktePersonen(final boolean inclusiefNietIngeschrevenen) {
        final List<PersoonHisVolledigImpl> result = new ArrayList<>();
        for (final HisVolledigRootObject rootObject : aangemaakteBijhoudingsRootObjecten.values()) {
            if (rootObject instanceof PersoonHisVolledig) {
                final PersoonHisVolledigImpl persoon = (PersoonHisVolledigImpl) rootObject;
                if (inclusiefNietIngeschrevenen || persoon.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE) {
                    result.add(persoon);
                }
            }
        }
        return result;
    }

    private List<PersoonHisVolledigImpl> vindBestaandePersonen(final boolean inclusiefNietIngeschrevenen) {
        final List<PersoonHisVolledigImpl> result = new ArrayList<>();
        for (final HisVolledigRootObject rootObject : bestaandeBijhoudingsRootObjecten.values()) {
            if (rootObject instanceof PersoonHisVolledig
                && !this.welInBerichtMaarNietBijgehoudenPersonen.contains(rootObject))
            {
                final PersoonHisVolledigImpl persoon = (PersoonHisVolledigImpl) rootObject;
                if (inclusiefNietIngeschrevenen || persoon.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE) {
                    result.add(persoon);
                }
            }
        }
        return result;
    }

    /**
     * Haalt bijgehouden personen op.
     *
     * @return bijgehouden personen als lijst met persoon his volledig objecten
     */
    public List<PersoonHisVolledigImpl> getBijgehoudenPersonen() {
        return getBijgehoudenPersonen(false);
    }

    /**
     * Haalt de actie mapping op.
     *
     * @return de actie mapping
     */
    public Map<ActieBericht, ActieModel> getActieMapping() {
        return actieMapping;
    }

    /**
     * Voeg een actie mapping toe.
     *
     * @param actieBericht de actie uit het bericht
     * @param actieModel de actie, zoals opgeslagen in de database
     */
    public void voegActieMappingToe(final ActieBericht actieBericht, final ActieModel actieModel) {
        this.actieMapping.put(actieBericht, actieModel);
    }

    /**
     * Geeft de administratieve handeling.
     * @return de administratieve handeling
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Geef de administratieve Handeling Id van de opgeslagen administratieve handeling.
     * Kan null terug geven als de opgeslagen adm.hand. ook null is.
     *
     * @return de administratieve Handeling Id van de opgeslagen administratieve handeling.
     */
    public Long getAdministratieveHandelingId() {
        Long administratieveHandelingId = null;
        if (this.administratieveHandeling != null) {
            administratieveHandelingId = this.administratieveHandeling.getID();
        }
        return administratieveHandelingId;
    }

    /**
     * Sla de opgeslagen administratieve handeling op, zodat deze in andere stappen gebruikt kan worden.
     *
     * @param administratieveHandeling de opgeslagen administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingModel administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Voegt een bijhoudings rootobject toe wat al bestaat in de BRP en waarnaar verwezen wordt via een technische
     * sleutel in het bericht.
     *
     * @param identificerendeSleutel indentificerende sleutel verwijzing naar het object in het bericht
     * @param rootObject het rootobject uit de BRP database.
     */
    public void voegBestaandBijhoudingsRootObjectToe(final String identificerendeSleutel,
            final HisVolledigRootObject rootObject)
    {
        bestaandeBijhoudingsRootObjecten.put(identificerendeSleutel, rootObject);
    }

    /**
     * Retourneert het bijhoudings rootobject met de opgegeven technische sleutel. Met deze functie kunnen dus BRP
     * rootobjecten, die in de database staan, worden opgevraagd waarnaar gerefereerd wordt in het bericht.
     *
     * @param identificerendeSleutel identificerende sleutel uit het bericht
     * @return HisVolledig rootobject uit de database.
     */
    public HisVolledigRootObject getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel(
            final String identificerendeSleutel)
    {
        return bestaandeBijhoudingsRootObjecten.get(identificerendeSleutel);
    }

    /**
     * HisVolledig rootobjecten die tijdes de berichtverwerking worden aangemaakt in de BRP database moeten via deze
     * functie aan de context worden toegevoegd. Zo komen deze objecten beschikbaar voor acties verderop in hetzelfde
     * bericht. Zo kan op basis van een referentieID elders verderop in een actie de HisVolledigRootObject worden
     * opgevraagd.
     *
     * @param communicatieID communicatie id van het object in het bericht wat door de BRP in de database wordt
     *            aangemaakt.
     * @param rootObject Het rootobject zelf wat door de BRP is aangemaakt in de database.
     */
    public void voegAangemaaktBijhoudingsRootObjectToe(final String communicatieID,
            final HisVolledigRootObject rootObject)
    {
        if (aangemaakteBijhoudingsRootObjecten.get(communicatieID) != null) {
            throw new IllegalArgumentException("Aangemaakte bijhoudingsobject met communicatie ID: " + communicatieID
                + " bestaat al.");
        }
        aangemaakteBijhoudingsRootObjecten.put(communicatieID, rootObject);
    }

    /**
     * Haal op basis van een communicatie ID een door de BRP aangemaakte rootobject op. Acties die verwijzen
     * naar een rootobject elders in het bericht wat door de BRP is aangemaakt, verwijzen naar dat object met een
     * identificerende sleutel.
     * Deze functie kan gebruikt worden om dat object uit de database op te vragen uit de context.
     *
     * @param communicatieID communicatie ID van het root object.
     * @return HisVolledig rootobject.
     */
    public HisVolledigRootObject getAangemaakteBijhoudingsRootObjectMetCommunicatieID(final String communicatieID) {
        return aangemaakteBijhoudingsRootObjecten.get(communicatieID);
    }

    public Map<String, HisVolledigRootObject> getAangemaakteBijhoudingsRootObjecten() {
        return Collections.unmodifiableMap(aangemaakteBijhoudingsRootObjecten);
    }

    /**
     * Retourneert alle nieuw aangemaakte niet ingeschrevenen.
     *
     * @return alle nieuw aangemaakte niet ingeschrevenen.
     */
    public Set<? extends HisVolledigRootObject> getNietInBerichtMaarWelAangemaakteNietIngeschrevenen() {
        return Collections.unmodifiableSet(nietInBerichtMaarWelAangemaakteNietIngeschrevenen);
    }

    /**
     * Retourneert een niet te wijzigen collectie van de relatie/personen die vóór de bijhouding al bekend waren in de
     * BRP.
     *
     * @return Map met technische sleutels die verwijzen naar een RootObject (Persoon of relatie)
     */
    public Map<String, HisVolledigRootObject> getBestaandeBijhoudingsRootObjecten() {
        return Collections.unmodifiableMap(bestaandeBijhoudingsRootObjecten);
    }

    /**
     * Haal de onderzoeken van een persoon op.
     * Dit kan alleen van personen die ook voorkomen in de 'bestaandeBijhoudingsRootObjecten'.
     *
     * @param persoonId het persoon id
     * @return de onderzoeken van een persoon.
     */
    public List<HisOnderzoekModel> getPersoonOnderzoeken(final Integer persoonId) {
        return this.persoonOnderzoeken.get(persoonId);
    }

    /**
     * Voeg de onderzoeken van een persoon toe.
     *
     * @param id het persoon id
     * @param onderzoeken de onderzoeken
     */
    public void voegOnderzoekenToeVoorPersoon(final Integer id, final List<HisOnderzoekModel> onderzoeken) {
        this.persoonOnderzoeken.put(id, onderzoeken);
    }

    /**
     * Zoekt het his volledig root object dat hoort bij het bericht root object.
     * Er zijn 3 mogelijkheden:
     * - met technische sleutel, bestaande root object
     * - met referentie id, nieuw aangemaakt root object
     * - met communicatie id, nieuw aangemaakt root object
     *
     * @param berichtRootObject het bericht root object
     * @return het gevonden his volledig root object, of null indien niets gevonden
     */
    public HisVolledigRootObject zoekHisVolledigRootObject(final BerichtRootObject berichtRootObject) {
        final String identificerendeSleutel;
        if (berichtRootObject instanceof PersoonBericht) {
            identificerendeSleutel = ((PersoonBericht) berichtRootObject).getIdentificerendeSleutel();
        } else {
            identificerendeSleutel = berichtRootObject.getObjectSleutel();
        }

        HisVolledigRootObject hisVolledigResultaat = null;
        if (StringUtils.isNotBlank(identificerendeSleutel)) {
            // Eerste mogelijkheid: het root object heeft een identificerende sleutel, dus is aanwezig als
            // bestaand root object of aangemaakte root object
            hisVolledigResultaat =
                this.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel(identificerendeSleutel);

            if (hisVolledigResultaat == null) {
                hisVolledigResultaat =
                    this.getAangemaakteBijhoudingsRootObjectMetCommunicatieID(identificerendeSleutel);
            }
        } else if (StringUtils.isNotBlank(berichtRootObject.getReferentieID())) {
            // Tweede mogelijkheid: het root object heeft een referentie id, dus is nieuw en al eerder
            // in een andere actie gedefinieerd. Haal het op als aangemaakt root object via communicatie id.
            hisVolledigResultaat =
                this.getAangemaakteBijhoudingsRootObjectMetCommunicatieID(berichtRootObject.getReferentieID());
        } else if (StringUtils.isNotBlank(berichtRootObject.getCommunicatieID())) {
            // Derde mogelijkheid: het root object heeft een communicatie id, dus is nieuw en wordt in deze
            // actie gedefinieerd. Haal het op als aangemaakt root object via communicatie id.
            hisVolledigResultaat =
                this.getAangemaakteBijhoudingsRootObjectMetCommunicatieID(berichtRootObject.getCommunicatieID());
        }
        return hisVolledigResultaat;
    }


    /**
     * Retourneert alle de berichtversie van alle niet ingeschreven personen met meerdere betrokkenheden.
     * @param alleBerichtPersonen een lijst van alle berichtpersonen
     * @return de lijst van berichtentiteiten
     */
    public List<BerichtEntiteit> getNietIngeschrevenBerichtPersonenMetMeerdereBetrokkenheden(final List<PersoonBericht> alleBerichtPersonen)
    {
        final List<PersoonHisVolledig> nietIngeschrevenenMetMeerdereBetrokkenheden = getNietIngeschrevenenMetMeerdereBetrokkenheden();

        return getNietIngeschrevenBerichtPersonenMetMeerdereBetrokkenheden(nietIngeschrevenenMetMeerdereBetrokkenheden, alleBerichtPersonen);
    }

    private List<BerichtEntiteit> getNietIngeschrevenBerichtPersonenMetMeerdereBetrokkenheden(
        final List<PersoonHisVolledig> nietIngeschrevenenMetMeerdereBetrokkenheden, final List<PersoonBericht> alleBerichtPersonen)
    {
        final List<BerichtEntiteit> berichtNietIngeschrevenenMetMeerdereBetrokkenheden = new ArrayList<>();
        for (PersoonHisVolledig persoonHisVolledig : nietIngeschrevenenMetMeerdereBetrokkenheden) {
            berichtNietIngeschrevenenMetMeerdereBetrokkenheden.add(
                this.zoekBerichtPersoonInContext(alleBerichtPersonen, persoonHisVolledig));
        }
        return berichtNietIngeschrevenenMetMeerdereBetrokkenheden;
    }

    private List<PersoonHisVolledig> getNietIngeschrevenenMetMeerdereBetrokkenheden() {
        final List<PersoonHisVolledig> nietIngeschrevenenMetMeerdereBetrokkenheden = new ArrayList<>();
        for (final PersoonHisVolledig persoonHisVolledig : getBijgehoudenPersonen(true)) {
            if (persoonHisVolledig.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE
                && persoonHisVolledig.getBetrokkenheden().size() > 1)
            {
                nietIngeschrevenenMetMeerdereBetrokkenheden.add(persoonHisVolledig);
            }
        }
        return nietIngeschrevenenMetMeerdereBetrokkenheden;
    }

    /**
     * Zoek het oorspronkelijke persoon bericht voor de persoon his volledig.
     *
     * @param alleBerichtPersonen alle personen uit het bericht
     * @param persoonHisVolledig de persoon his volledig
     * @return de persoon bericht, indien gevonden, anders null
     */
    private BerichtEntiteit zoekBerichtPersoonInContext(final List<PersoonBericht> alleBerichtPersonen,
        final PersoonHisVolledig persoonHisVolledig)
    {
        for (final PersoonBericht persoonBericht : alleBerichtPersonen) {
            final HisVolledigRootObject eenHisVolledig = zoekHisVolledigRootObject(persoonBericht);
            if (persoonHisVolledig.equals(eenHisVolledig)) {
                return persoonBericht;
            }
        }
        return null;
    }



    @Override
    public String getXmlBericht() {
        return xmlBericht;
    }

    @Override
    public void setXmlBericht(final String xmlBericht) {
        this.xmlBericht = xmlBericht;
    }

    @Override
    public Collection<Integer> getLockingIds() {
        return lockingIds;
    }

    @Override
    public LockingMode getLockingMode() {
        return LockingMode.EXCLUSIVE;
    }
}
