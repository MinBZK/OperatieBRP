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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;

/**
 * Deze class biedt de functionaliteit om o.b.v. een {@link Element} en database ID een voorkomen van een {@link Persoon} te vinden.
 */
public final class PersoonVoorkomenLocator {

    private static final Map<Element, VoorkomenLocator> LOCATORS = new HashMap<>();

    static {
        // a-laag entiteiten / groepen die meerdere keren kunnen voorkomen op een persoonslijst
        LOCATORS.put(Element.PERSOON_ADRES_STANDAARD, (id, persoon) -> zoekInAdresSet(id, persoon.getPersoonAdresSet()));
        LOCATORS.put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD,
                (id, persoon) -> zoekInGeslachtsnaamComponentSet(id, persoon.getPersoonGeslachtsnaamcomponentSet()));
        LOCATORS.put(Element.PERSOON_INDICATIE_STANDAARD, (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), null));
        LOCATORS.put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.DERDE_HEEFT_GEZAG));
        LOCATORS.put(Element.PERSOON_INDICATIE_ONDERCURATELE_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.ONDER_CURATELE));
        LOCATORS.put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING));
        LOCATORS.put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER));
        LOCATORS.put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.BEHANDELD_ALS_NEDERLANDER));
        LOCATORS.put(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(),
                        SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT));
        LOCATORS.put(Element.PERSOON_INDICATIE_STAATLOOS_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.STAATLOOS));
        LOCATORS.put(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
        LOCATORS.put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_STANDAARD,
                (id, persoon) -> zoekInIndicatieSet(id, persoon.getPersoonIndicatieSet(), SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG));
        LOCATORS.put(Element.PERSOON_NATIONALITEIT_STANDAARD, (id, persoon) -> zoekInNationaliteitSet(id, persoon.getPersoonNationaliteitSet()));
        LOCATORS.put(Element.PERSOON_REISDOCUMENT_STANDAARD, (id, persoon) -> zoekInReisdocumentSet(id, persoon.getPersoonReisdocumentSet()));
        LOCATORS.put(Element.PERSOON_VERIFICATIE_STANDAARD, (id, persoon) -> zoekInVerificatieSet(id, persoon.getPersoonVerificatieSet()));
        LOCATORS.put(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT,
                (id, persoon) -> zoekInVerstrekkingsbeperkingSet(id, persoon.getPersoonVerstrekkingsbeperkingSet()));
        LOCATORS.put(Element.PERSOON_VOORNAAM_STANDAARD, (id, persoon) -> zoekInVoornaamSet(id, persoon.getPersoonVoornaamSet()));
        LOCATORS.put(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD,
                (id, persoon) -> zoekInBuitenlandsPersoonsnummerSet(id, persoon.getPersoonBuitenlandsPersoonsnummerSet()));

        // c/d-laag entiteiten / groepen die maar 1 keer kunnen voorkomen op een persoonslijst
        LOCATORS.put(Element.PERSOON_SAMENGESTELDENAAM, (id, persoon) -> zoekInSet(id, persoon.getPersoonSamengesteldeNaamHistorieSet()));
        LOCATORS.put(Element.PERSOON_NAAMGEBRUIK, (id, persoon) -> zoekInSet(id, persoon.getPersoonNaamgebruikHistorieSet()));
        LOCATORS.put(Element.PERSOON_DEELNAMEEUVERKIEZINGEN, (id, persoon) -> zoekInSet(id, persoon.getPersoonDeelnameEuVerkiezingenHistorieSet()));
        LOCATORS.put(Element.PERSOON_GEBOORTE, (id, persoon) -> zoekInSet(id, persoon.getPersoonGeboorteHistorieSet()));
        LOCATORS.put(Element.PERSOON_GESLACHTSAANDUIDING, (id, persoon) -> zoekInSet(id, persoon.getPersoonGeslachtsaanduidingHistorieSet()));
        LOCATORS.put(Element.PERSOON_IDENTIFICATIENUMMERS, (id, persoon) -> zoekInSet(id, persoon.getPersoonIDHistorieSet()));
        LOCATORS.put(Element.PERSOON_BIJHOUDING, (id, persoon) -> zoekInSet(id, persoon.getPersoonBijhoudingHistorieSet()));
        LOCATORS.put(Element.PERSOON_MIGRATIE, (id, persoon) -> zoekInSet(id, persoon.getPersoonMigratieHistorieSet()));
        LOCATORS.put(Element.PERSOON_INSCHRIJVING, (id, persoon) -> zoekInSet(id, persoon.getPersoonInschrijvingHistorieSet()));
        LOCATORS.put(Element.PERSOON_NUMMERVERWIJZING, (id, persoon) -> zoekInSet(id, persoon.getPersoonNummerverwijzingHistorieSet()));
        LOCATORS.put(Element.PERSOON_OVERLIJDEN, (id, persoon) -> zoekInSet(id, persoon.getPersoonOverlijdenHistorieSet()));
        LOCATORS.put(Element.PERSOON_PERSOONSKAART, (id, persoon) -> zoekInSet(id, persoon.getPersoonPersoonskaartHistorieSet()));
        LOCATORS.put(Element.PERSOON_VERBLIJFSRECHT, (id, persoon) -> zoekInSet(id, persoon.getPersoonVerblijfsrechtHistorieSet()));
        LOCATORS.put(Element.PERSOON_UITSLUITINGKIESRECHT, (id, persoon) -> zoekInSet(id, persoon.getPersoonUitsluitingKiesrechtHistorieSet()));
        LOCATORS.put(Element.PERSOON_AFGELEIDADMINISTRATIEF, (id, persoon) -> zoekInSet(id, persoon.getPersoonAfgeleidAdministratiefHistorieSet()));

        //relatie historie
        LOCATORS.put(Element.RELATIE_STANDAARD, (id, persoon) -> zoekInRelatieSet(id, persoon.getRelaties()));

        //betrokkenheid historie
        LOCATORS.put(Element.OUDER_OUDERLIJKGEZAG, (id, persoon) -> zoekInOuderlijkGezagSet(id, persoon.getBetrokkenheidSet(SoortBetrokkenheid.OUDER)));
        LOCATORS.put(Element.OUDER_OUDERSCHAP, (id, persoon) -> zoekInOuderschapSet(id, persoon.getBetrokkenheidSet(SoortBetrokkenheid.OUDER)));

        //uitzonder voor onbekende ouder omdat niet via de gerelateerde persoon gezocht kan worden
        LOCATORS.put(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG, (id, persoon) -> zoekInOuderlijkGezagSet(id, persoon.getActueleOuders()));
        LOCATORS.put(Element.GERELATEERDEOUDER_OUDERSCHAP, (id, persoon) -> zoekInOuderschapSet(id, persoon.getActueleOuders()));
    }

    /**
     * Zoekt een voorkomen binnen de gegeven persoon waarvan het type overeenkomt met het gegeven element en waarvan de id gelijk is aan de gegeven id. Als het
     * element van het soort {@link SoortElement#OBJECTTYPE} is dan wordt null geretourneerd als het element van het soort {@link SoortElement#ATTRIBUUT} is dan
     * wordt gezocht met de bijbehorende groep en wanneer het element van het soort {@link SoortElement#GROEP} is dan wordt met dit element gezocht.
     * @param element het element
     * @param id de id
     * @param persoon de persoon
     * @return het voorkomen of null als er geen voorkomen gevonden kan worden
     */
    public static FormeleHistorie zoek(final Element element, final long id, final Persoon persoon) {
        if (SoortElement.OBJECTTYPE.equals(element.getSoort())) {
            return null;
        }
        final FormeleHistorie result;
        if (isUizonderingVoorOnbekendeOuder(element)) {
            result = bepaalLocator(element).zoek(id, persoon);
        } else if (SoortElement.ATTRIBUUT.equals(element.getSoort())) {
            result = zoek(element.getGroep(), id, persoon);
        } else if (element.name().startsWith(GERELATEERDE) && element.getAliasVan() != null) {
            result = zoek(element.getAliasVan(), id, zoekGerelateerdePersonen(element, persoon));
        } else if (element.getAliasVan() != null && !isIndicatie(element)) {
            result = zoek(element.getAliasVan(), id, persoon);
        } else {
            result = bepaalLocator(element).zoek(id, persoon);
        }
        return result;
    }

    private static FormeleHistorie zoek(final Element element, final long id, final Set<Persoon> personen) {
        FormeleHistorie result = null;
        for (final Persoon persoon : personen) {
            result = zoek(element, id, persoon);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    private static VoorkomenLocator bepaalLocator(final Element element) {
        if (LOCATORS.containsKey(element)) {
            return LOCATORS.get(element);
        } else {
            return (id, persoon) -> null;
        }
    }

    private static FormeleHistorie zoekInSet(final long id, final Set<? extends FormeleHistorie> voorkomens) {
        for (final FormeleHistorie voorkomen : voorkomens) {
            if (id == voorkomen.getId().longValue()) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInAdresSet(final long id, final Set<PersoonAdres> adressen) {
        for (final PersoonAdres adres : adressen) {
            final FormeleHistorie voorkomen = zoekInSet(id, adres.getPersoonAdresHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInGeslachtsnaamComponentSet(final long id, final Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten) {
        for (final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent : geslachtsnaamcomponenten) {
            final FormeleHistorie voorkomen = zoekInSet(id, geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInIndicatieSet(final long id, final Set<PersoonIndicatie> indicaties, final SoortIndicatie soortIndicatie) {
        for (final PersoonIndicatie indicatie : indicaties) {
            final FormeleHistorie voorkomen = zoekInSet(id, indicatie.getPersoonIndicatieHistorieSet());
            if (voorkomen != null && (soortIndicatie == null || soortIndicatie.equals(indicatie.getSoortIndicatie()))) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInNationaliteitSet(final long id, final Set<PersoonNationaliteit> nationaliteiten) {
        for (final PersoonNationaliteit nationaliteit : nationaliteiten) {
            final FormeleHistorie voorkomen = zoekInSet(id, nationaliteit.getPersoonNationaliteitHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInReisdocumentSet(final long id, final Set<PersoonReisdocument> reisdocumenten) {
        for (final PersoonReisdocument reisdocument : reisdocumenten) {
            final FormeleHistorie voorkomen = zoekInSet(id, reisdocument.getPersoonReisdocumentHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInVerificatieSet(final long id, final Set<PersoonVerificatie> verificaties) {
        for (final PersoonVerificatie verificatie : verificaties) {
            final FormeleHistorie voorkomen = zoekInSet(id, verificatie.getPersoonVerificatieHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInVerstrekkingsbeperkingSet(final long id, final Set<PersoonVerstrekkingsbeperking> verstrekkingsbeperkingen) {
        for (final PersoonVerstrekkingsbeperking verstrekkingsbeperking : verstrekkingsbeperkingen) {
            final FormeleHistorie voorkomen = zoekInSet(id, verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInVoornaamSet(final long id, final Set<PersoonVoornaam> voornamen) {
        for (final PersoonVoornaam voornaam : voornamen) {
            final FormeleHistorie voorkomen = zoekInSet(id, voornaam.getPersoonVoornaamHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInBuitenlandsPersoonsnummerSet(final long id, final Set<PersoonBuitenlandsPersoonsnummer> buitenlandsPersoonsnummers) {
        for (final PersoonBuitenlandsPersoonsnummer buitenlandsPersoonsnummer : buitenlandsPersoonsnummers) {
            final FormeleHistorie voorkomen = zoekInSet(id, buitenlandsPersoonsnummer.getPersoonBuitenlandsPersoonsnummerHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInOuderlijkGezagSet(final long id, final Set<Betrokkenheid> betrokkenheden) {
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            final FormeleHistorie voorkomen = zoekInSet(id, betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInOuderschapSet(final long id, final Set<Betrokkenheid> betrokkenheden) {
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            final FormeleHistorie voorkomen = zoekInSet(id, betrokkenheid.getBetrokkenheidOuderHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static FormeleHistorie zoekInRelatieSet(final long id, final Set<Relatie> relaties) {
        for (final Relatie relatie : relaties) {
            final FormeleHistorie voorkomen = zoekInSet(id, relatie.getRelatieHistorieSet());
            if (voorkomen != null) {
                return voorkomen;
            }
        }
        return null;
    }

    private static boolean isUizonderingVoorOnbekendeOuder(final Element element) {
        return Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.equals(element) || Element.GERELATEERDEOUDER_OUDERSCHAP.equals(element);
    }

    @FunctionalInterface
    private interface VoorkomenLocator {
        FormeleHistorie zoek(final long id, final Persoon persoon);
    }
}
