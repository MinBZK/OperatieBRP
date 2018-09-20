/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.dataaccess;

import java.util.List;

import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.SoortActieSoortAdmhnd;
import nl.bzk.brp.metaregister.model.Tekst;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;

/**
 * Dit is de (platform/generator onafhankelijke) interface voor een Data Access Object voor het BMR.
 * Onderstaande functies moeten een implementatie kennen willen de generatoren met behulp van de DAO hun werk doen.
 */
public interface BmrDao {

    /**
     * Retourneert een lijst van object typen uit het BMR.
     *
     * @return Lijst van object typen.
     */
    List<ObjectType> getObjectTypen();

    /**
     * Retourneert een lijst van object typen uit het BMR met de opgegeven filter.
     *
     * @param filter een filter object met daarin de criteria waaraan de gezochte elementen moeten voldoen.
     * @return Lijst van object typen die aan de filter voldoen.
     */
    List<ObjectType> getObjectTypen(final BmrElementFilterObject filter);

    /**
     * Retourneert de enumeratie lijst van tuples voor een bepaald attribuut type.
     * Dit is niet voor ieder attribuut type van toepassing. Het geldt alleen
     * voor attribuut types die het type zijn van een attribuut dat op
     * een statisch stamgegeven zit en 'Code' heet.
     *
     * @param attribuutType een attribuut type
     * @return de lijst van enumeratie waardes, kan leeg zijn
     */
    List<Tuple> getTupleEnumeratieVoorAttribuutType(final AttribuutType attribuutType);

    /**
     * Retourneert de enumeratie lijst van waarderegel waardes voor een bepaald alement (meestal een attribuut type).
     * Dit is niet voor ieder element, noch voor ieder attribuut type van toepassing. Het geldt alleen voor elementen
     * (en dan dus met name attribuut types) die een gelinkte systeemregel hebben met als soort regel
     * 'WB' = Waardebereik.
     *
     * @param element           een element (meestal een attribuut type)
     * @param filterOpInBericht exclude alle waarderegelwaardes met inBericht = 'N'
     * @param filterOpInCode    exclude alle waarderegelwaardes met inCode = 'N'
     * @return de lijst van waarderegel waardes, kan leeg zijn
     */
    List<WaarderegelWaarde> getWaardeEnumeratiesVoorElement(final GeneriekElement element,
                                                            boolean filterOpInBericht, boolean filterOpInCode);

    /**
     * Retourneert de teksten uit het BMR die bij een element horen. Deze teksten bevatten de beschrijving/omschrijving
     * en andere informatie aangande het element.
     *
     * @param element Element waarvoor tekst wordt opgevraagd.
     * @return de teksten (beschrijving, omschrijving en andere info) van een element, gesorteerd op volgnummer.
     */
    List<Tekst> getTekstenVoorElement(final GeneriekElement element);

    /**
     * Retourneert alle groepen die bij een objecttype horen.
     *
     * @param objectType Het objecttype waarvoor groepen worden gezocht.
     * @return Lijst van groepen behorend bij objectType.
     */
    List<Groep> getGroepenVoorObjectType(final ObjectType objectType);

    /**
     * Retourneert alle groepen die bij een objecttype horen of, optioneel, die bij een superobjecttype horen.
     *
     * @param objectType                    Het objecttype waarvoor groepen worden gezocht.
     * @param inclusiefGroepenUitSupertypes Indien TRUE dan worden ook groepen uit supertypes teruggegeven.
     * @return Lijst van groepen behorene bij objectType.
     */
    List<Groep> getGroepenVoorObjectType(final ObjectType objectType, final boolean inclusiefGroepenUitSupertypes);

    /**
     * Retourneert een lijst van groepen uit het BMR.
     *
     * @return Lijst van groepen.
     */
    List<Groep> getGroepen();

    /**
     * Retourneert alle groepen uit het BMR die een historie patroon kennen. Formeel of Formeel en Materieel.
     * De object typen waar de groepen bij horen zijn dynamische object typen en dynamische stamgegevens.
     *
     * @return Lijst van groepen die een historie patroon kennen.
     */
    List<Groep> getGroepenWaarvanHistorieWordtVastgelegd();

    /**
     * Retourneert een lijst van attributen behorende bij een groep.
     *
     * @param groep De groep waarvoor attributen worden gezocht.
     * @return Lijst van attributen behorende bij groep.
     */
    List<Attribuut> getAttributenVanGroep(final Groep groep);

    /**
     * Retourneert alle attributen die horen bij een object type.
     *
     * @param objectType Het object type waarvan de attributen worden opgevraagd.
     * @return Lijst van attributen.
     */
    List<Attribuut> getAttributenVanObjectType(final ObjectType objectType);


    /**
     * Retourneert een lijst van alle attribuut typen uit het BMR uit de logische laag.
     * Niet uit de operationele laag want dan krijg je dubbele attribuut typen.
     *
     * @return Lijst van attribuut typen.
     */
    List<AttribuutType> getAttribuutTypen();

    /**
     * Retourneert een lijst van alle basis typen uit het BMR voor een bepaald target platform.
     *
     * @param targetPlatform het target platform van de basis typen
     * @return Lijst van basis typen.
     */
    List<BasisType> getBasisTypen(final BmrTargetPlatform targetPlatform);

    /**
     * Bepaal het basis type voor een attribuut type in combinatie met een platform.
     * Zie de implementatie voor de details achter het proces.
     * De methode geeft een paar terug van basis type en type impl objecten,
     * zodat de extra informatie in de type impl gebruikt kan worden waar nodig.
     *
     * @param attribuutType het attribuut type
     * @param platform      het doel platform
     * @return het basis type dat hiervoor gebruikt moet worden, in combinatie met de type impl die hiermee gelinkt is
     */
    BasisTypeOverrulePaar getBasisTypeVoorAttribuutType(
            final AttribuutType attribuutType, final BmrTargetPlatform platform);

    /**
     * Retourneert alle dynamische stamgegeven object typen uit het BMR, deze hebben het kenmerk soort_inhoud = 'S'.
     *
     * @return Lijst van object typen.
     */
    List<ObjectType> getDynamischeStamgegevensObjectTypen();

    /**
     * Retourneert alle statische stamgegeven object typen uit het BMR, deze hebben het kenmerk soort_inhoud = 'X'.
     *
     * @return Lijst van object typen.
     */
    List<ObjectType> getStatischeStamgegevensObjectTypen();

    /**
     * Retourneert het attribuut type van het attribuut.
     *
     * @param attr Het attribuut waar het type van wordt gezocht.
     * @return Attribuut type van attribuut.
     */
    AttribuutType getAttribuutTypeVoorAttribuut(final Attribuut attr);

    /**
     * Dit is een generieke functie om een element op te vragen. Dit element kan van elk soort zijn.
     *
     * @param id    Id van het op te vragen element.
     * @param clazz Het java type van het element; attribuut, object type, tuple enz.
     * @param <T>   Het type wat geretourneerd wordt, dit afhankelijk van het java type wat met de clazz param wordt
     *              aangegeven.
     * @return Een subinstantie van een element; dus attribuut, object type, tuple enz.
     */
    <T extends GeneriekElement> T getElement(final Integer id, final Class<T> clazz);

    /**
     * Retourneert het object type met een bepaald ident_code waarde.
     *
     * @param identCode De ident_code waarde.
     * @return Object type dat als ident_code identcode heeft.
     */
    ObjectType getObjectTypeMetIdentCode(final String identCode);

    /**
     * Retourneert attributen in andere object typen die van het type objecttype zijn.
     * Let op: deze methode filtert ook meteen op de attributen die een inverse associatie ident code hebben.
     *
     * @param objectType Het object type dat inverse in andere object typen zit.
     * @return Lijst van attributen die in verschillende object typen zitten.
     */
    List<Attribuut> getInverseAttributenVoorObjectType(final ObjectType objectType);

    /**
     * Retourneert voor deze groep het object type in het operationeel model van het BMR dat de tegenhanger is.
     * De tegenhanger wordt op basis van de kolom "SyncId" opgehaald.
     *
     * @param groep De groep uit het logische model waar de operationele tegenhanger van wordt gezocht.
     * @return Object type uit het operationele model.
     */
    ObjectType getOperationeelModelObjectTypeVoorGroep(Groep groep);

    /**
     * Haalt de attributen op die de logische identiteit bepalen van een object type.
     *
     * @param objectType Het object type.
     * @return Lijst van atributen die de logische identiteit bepalen.
     */
    List<Attribuut> getLogischeIdentiteitAttributenVoorObjectType(ObjectType objectType);

    /**
     * Haalt het stamgegeven object type op dat als attribuut in objecttype een discriminator attribuut is.
     *
     * @param objectType Het object type dat discriminator waardes kent.
     * @return Stamgegeven object type.
     */
    ObjectType getDiscriminatorObjectType(ObjectType objectType);

    /**
     * Haalt de mogelijke combinaties op tussen soort actie en soort administratieve handeling.
     *
     * @param soortAdmHndl Soort administratieve handeling.
     * @return Lijst met combinaties.
     */
    List<SoortActieSoortAdmhnd> getSoortActiesVoorSoortAdministratieveHandeling(Tuple soortAdmHndl);

    /**
     * Haalt alle attributen op van het object type waarvan het attribuuttype "StatusHistorie" is.
     * Omdat deze attributen alleen in het operationeel model zitten wordt de syncid van het object type
     * gebruikt om hetzelfde object type in het operationele model te vinden.
     *
     * @param objectType Object type waar we status historie velden van zoeken.
     * @return Lijst met attributen van het type StatusHistorie.
     */
    List<Attribuut> getStatusHistorieAttributenVanObjectType(ObjectType objectType);

    /**
     * Retourneert alle elementen met dezelfde syncid.
     *
     * @param syncid de syncid.
     * @return de lijst van elementen met opgegeven syncid.
     */
    List<GeneriekElement> getElementenVoorSyncId(int syncid);

    /**
     * Haal het element van het juiste type op uit het BMR
     * met het meegegeven syncid, uit de betreffende laag.
     * Als het element niet bestaat, wordt null terug gegeven.
     *
     * @param syncid het sync id
     * @param laag de laag (logisch of operationeel)
     * @param type het element type
     * @param <T> het type element
     * @return het gevraagde object of null
     */
    <T extends GeneriekElement> T getElementVoorSyncIdVanLaag(int syncid, BmrLaag laag, Class<T> type);

    /**
     * Geeft voor een bepaalde groep het bijbehorende status his attribuut (als dat bestaat).
     *
     * @param groep de groep
     * @return het status his attribuut of null
     */
    Attribuut getStatusHisAttribuutVoorGroep(final Groep groep);

}
