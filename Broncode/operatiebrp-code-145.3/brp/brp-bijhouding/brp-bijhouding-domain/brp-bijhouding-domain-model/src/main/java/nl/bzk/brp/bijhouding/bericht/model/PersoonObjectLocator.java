/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.Locator.GERELATEERDE;
import static nl.bzk.brp.bijhouding.bericht.model.Locator.isIndicatie;
import static nl.bzk.brp.bijhouding.bericht.model.Locator.zoekGerelateerdePersonen;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;

/**
 * Deze class biedt de functionaliteit om o.b.v. een {@link Element} en database ID een object van een {@link Persoon} te vinden.
 */
public final class PersoonObjectLocator {

    private static final Map<Element, PersoonObjectLocator.ObjectLocator> LOCATORS = new HashMap<>();

    static {
        // a-laag entiteiten / groepen die meerdere keren kunnen voorkomen op een persoonslijst
        LOCATORS.put(Element.PERSOON_ADRES, (id, persoon) -> zoekInSet(id, persoon.getPersoonAdresSet()));
        LOCATORS.put(Element.PERSOON_GESLACHTSNAAMCOMPONENT, (id, persoon) -> zoekInSet(id, persoon.getPersoonGeslachtsnaamcomponentSet()));
        LOCATORS.put(Element.PERSOON_INDICATIE, (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet()));
        LOCATORS.put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG, (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                SoortIndicatie.DERDE_HEEFT_GEZAG.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_ONDERCURATELE, (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                SoortIndicatie.ONDER_CURATELE.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING,
                (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                        SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER,
                (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                        SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER,
                (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                        SoortIndicatie.BEHANDELD_ALS_NEDERLANDER.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT,
                (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                        SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT.equals(indicatie.getSoortIndicatie()))
                        .collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_STAATLOOS, (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                SoortIndicatie.STAATLOOS.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE,
                (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                        SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG,
                (id, persoon) -> zoekInSet(id, persoon.getPersoonIndicatieSet().stream().filter(indicatie ->
                        SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG.equals(indicatie.getSoortIndicatie())).collect(Collectors.toSet())));
        LOCATORS.put(Element.PERSOON_NATIONALITEIT, (id, persoon) -> zoekInSet(id, persoon.getPersoonNationaliteitSet()));
        LOCATORS.put(Element.PERSOON_REISDOCUMENT, (id, persoon) -> zoekInSet(id, persoon.getPersoonReisdocumentSet()));
        LOCATORS.put(Element.PERSOON_VERIFICATIE, (id, persoon) -> zoekInSet(id, persoon.getPersoonVerificatieSet()));
        LOCATORS.put(Element.PERSOON_VERSTREKKINGSBEPERKING, (id, persoon) -> zoekInSet(id, persoon.getPersoonVerstrekkingsbeperkingSet()));
        LOCATORS.put(Element.PERSOON_VOORNAAM, (id, persoon) -> zoekInSet(id, persoon.getPersoonVoornaamSet()));
        LOCATORS.put(Element.PERSOON_BUITENLANDSPERSOONSNUMMER, (id, persoon) -> zoekInSet(id, persoon.getPersoonBuitenlandsPersoonsnummerSet()));

        //relatie historie
        LOCATORS.put(Element.RELATIE, (id, persoon) -> zoekInSet(id, persoon.getRelaties()));
        LOCATORS.put(Element.HUWELIJK, (id, persoon) -> zoekInSet(id,
                persoon.getRelaties().stream().filter(relatie -> SoortRelatie.HUWELIJK.equals(relatie.getSoortRelatie())).collect(
                        Collectors.toSet())));
        LOCATORS.put(Element.GEREGISTREERDPARTNERSCHAP, (id, persoon) -> zoekInSet(id,
                persoon.getRelaties().stream().filter(relatie -> SoortRelatie.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatie())).collect(
                        Collectors.toSet())));
        LOCATORS.put(Element.FAMILIERECHTELIJKEBETREKKING, (id, persoon) -> zoekInSet(id,
                persoon.getRelaties().stream().filter(relatie -> SoortRelatie.FAMILIERECHTELIJKE_BETREKKING.equals(relatie.getSoortRelatie())).collect(
                        Collectors.toSet())));

        //betrokkenheid historie
        LOCATORS.put(Element.OUDER, (id, persoon) -> zoekInSet(id, persoon.getBetrokkenheidSet(SoortBetrokkenheid.OUDER)));
        LOCATORS.put(Element.KIND, (id, persoon) -> zoekInSet(id, persoon.getBetrokkenheidSet(SoortBetrokkenheid.KIND)));
        LOCATORS.put(Element.PARTNER, (id, persoon) -> zoekInSet(id, persoon.getBetrokkenheidSet(SoortBetrokkenheid.PARTNER)));

        //persoon
        LOCATORS.put(Element.PERSOON, PersoonObjectLocator::zoekPersoon);

        //uitzonder voor onbekende ouder omdat niet via de gerelateerde persoon gezocht kan worden
        LOCATORS.put(Element.GERELATEERDEOUDER, (id, persoon) -> zoekInSet(id, persoon.getActueleOuders()));
    }

    /**
     * Zoekt een object binnen de gegeven persoon waarvan het type overeenkomt met het gegeven element en waarvan de id gelijk is aan de gegeven id. Als het
     * element van het soort {@link SoortElement#ATTRIBUUT} of {@link SoortElement#GROEP} is dan
     * wordt gezocht met de bijbehorende objecttype en wanneer het element van het soort {@link SoortElement#OBJECTTYPE} is dan wordt met dit element gezocht.
     * @param element het element
     * @param id de id
     * @param persoon de persoon
     * @return het object of null als er geen object gevonden kan worden
     */
    public static RootEntiteit zoek(final Element element, final Long id, final Persoon persoon) {
        final RootEntiteit result;
        if (isUizonderingVoorOnbekendeOuder(element)) {
            result = bepaalLocator(element).zoek(id, persoon);
        } else if (element.getAliasVan() != null && element.name().startsWith(GERELATEERDE)) {
            result = zoek(element.getAliasVan(), id, zoekGerelateerdePersonen(element, persoon));
        } else if (element.getAliasVan() != null && !isIndicatie(element)) {
            result = zoek(element.getAliasVan(), id, persoon);
        } else if (SoortElement.GROEP.equals(element.getSoort()) || SoortElement.ATTRIBUUT.equals(element.getSoort())) {
            result = zoek(element.getObjecttype(), id, persoon);
        } else {
            result = bepaalLocator(element).zoek(id, persoon);
        }
        return result;
    }

    private static RootEntiteit zoek(final Element element, final long id, final Set<Persoon> personen) {
        RootEntiteit result = null;
        for (final Persoon persoon : personen) {
            result = zoek(element, id, persoon);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    private static ObjectLocator bepaalLocator(final Element element) {
        if (LOCATORS.containsKey(element)) {
            return LOCATORS.get(element);
        } else {
            return (id, persoon) -> null;
        }
    }

    private static RootEntiteit zoekInSet(final long id, final Set<? extends RootEntiteit> entiteiten) {
        for (final RootEntiteit entiteit : entiteiten) {
            if (id == entiteit.getId().longValue()) {
                return entiteit;
            }
        }
        return null;
    }

    private static RootEntiteit zoekPersoon(final long id, final Persoon persoon) {
        if (id == persoon.getId()) {
            if (persoon instanceof BijhoudingPersoon) {
                return ((BijhoudingPersoon) persoon).getDelegates().iterator().next();
            } else {
                return persoon;
            }
        } else {
            return null;
        }
    }

    private static boolean isUizonderingVoorOnbekendeOuder(final Element element) {
        return Element.GERELATEERDEOUDER.equals(element);
    }

    @FunctionalInterface
    private interface ObjectLocator {
        RootEntiteit zoek(final long id, final Persoon persoon);
    }
}
