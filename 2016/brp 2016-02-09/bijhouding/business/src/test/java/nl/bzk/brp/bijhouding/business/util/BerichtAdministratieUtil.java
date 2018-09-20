/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

import nl.bzk.brp.bijhouding.business.excepties.OnbekendRootObjectExceptie;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenDocumentBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import org.apache.commons.lang.StringUtils;


/**
 * Jibx leest van xml en converteer java objecten naar en houdt een hashmap bij van alle identificeerbare groepen.
 * En aan het eind wordt deze gestopt in het bericht.
 * Deze stap wordt in de unit test uiteraard nooit uitgevoerd. Vandaar dat een uitility nodig is om deze hasmap te
 * vullen.
 *
 * Stap registreer en test of communicatieID, referentieID correct gebruikt worden.
 * Er wordt een hashmap aangemaakt met daarin alle gevonden communicatieID's en de daarbij behorende
 * identificeerbare objecten.
 *
 * Deze modules kunnen deze meldingen opleveren:
 * BRBY9901("Elk communicatieID in het bericht moet uniek zijn"),
 * BRBY9902("Elk referentieID in het request moet verwijzen naar een communicatieID in het request"),
 * BRBY9905("Een request mag geen keten- of zelfverwijzing bevatten."),
 * BRBY9906("Een referentieID moet naar een element in het request verwijzen met hetzelfde entiteittype"),
 *
 *
 *
 */
public final class BerichtAdministratieUtil {

    private BerichtAdministratieUtil() {
    }

    /**
     * Verrijk een Berichtbericht object; lees alle objecten
     * @param bericht
     */
    public static void verrijktBerichtMetIdentificeerbaarObjectIndex(final BijhoudingsBericht bericht) {
        if (bericht != null) {
            if (null == bericht.getCommunicatieIdMap()) {
                // heeft geen zetter, wordt normaal geinitialiseer door Jibx.
                bericht.setCommunicatieIdMap(new CommunicatieIdMap());
            }
            CommunicatieIdMap map = bericht.getCommunicatieIdMap();
            registreerAdministratie(map, bericht.getAdministratieveHandeling());
        }
    }

    /**
     * Registreer een administratieve handeling inc. alle objecten die daaronder vallen.
     * @param map de hashmap
     * @param admBericht het bericht
     */
    private static void registreerAdministratie(final CommunicatieIdMap map,
        final AdministratieveHandelingBericht admBericht)
    {
        registreerIdentificeerbaarObject(map, admBericht);
        if (admBericht != null) {
            registreerGedeblokkeerdeMeldingen(map, admBericht);
            registreerDocumenten(map, admBericht);
            if (admBericht.getActies() != null) {
                for (ActieBericht aB : admBericht.getActies()) {
                    registreerActie(map, aB);
                }
            }
        }
    }

    /**
     * Registreer de lijst van documenten die bij een administratieve handeling hoort.
     * @param map de hashmap
     * @param admBericht het bericht
     */
    private static void registreerDocumenten(final CommunicatieIdMap map,
        final AdministratieveHandelingBericht admBericht)
    {
        if (null != admBericht.getBijgehoudenDocumenten()) {
            for (AdministratieveHandelingBijgehoudenDocumentBericht docBericht : admBericht.getBijgehoudenDocumenten()) {
                registreerIdentificeerbaarObject(map, docBericht.getDocument());
            }
        }
    }

    /**
     * Registreer de lijst van gedeblokkeerde meldingen die bij een administratieve handeling hoort.
     * @param map de hashmap
     * @param admBericht het bericht
     */
    private static void registreerGedeblokkeerdeMeldingen(final CommunicatieIdMap map,
            final AdministratieveHandelingBericht admBericht)
    {
        if (null != admBericht.getGedeblokkeerdeMeldingen()) {
            for (AdministratieveHandelingGedeblokkeerdeMeldingBericht meldingBericht
                : admBericht.getGedeblokkeerdeMeldingen())
            {
                registreerIdentificeerbaarObject(map, meldingBericht.getGedeblokkeerdeMelding());
            }
        }
    }

    /**
     * Registreer de lijst van acties en alles wat daaronder hoort die bij een administratieve handeling hoort.
     * @param map de hashmap
     * @param actieBericht de actie
     */
    private static void registreerActie(final CommunicatieIdMap map, final ActieBericht actieBericht)
    {
        registreerIdentificeerbaarObject(map, actieBericht);
        if (null != actieBericht.getBronnen()) {
            for (ActieBronBericht aB : actieBericht.getBronnen()) {
                registreerIdentificeerbaarObject(map, aB.getDocument());
            }
        }
        RootObject rO = actieBericht.getRootObject();
        if (rO instanceof PersoonBericht) {
            registreerPersoon(map, (PersoonBericht) rO);
        } else if (rO instanceof RelatieBericht) {
            // huwelijk en registratieGeboorte.
            registreerRelatie(map, (RelatieBericht) rO);
        } else {
            throw new OnbekendRootObjectExceptie("Onbekende rootObject " + rO);
        }
    }

    /**
     * Registreer de lijst van relatie en alles wat daaronder hoort.
     * @param map de hashmap
     * @param relatieBericht de relatie
     */
    private static void registreerRelatie(final CommunicatieIdMap map,
            final RelatieBericht relatieBericht)
    {
        registreerIdentificeerbaarObject(map, relatieBericht);

        if (null != relatieBericht.getBetrokkenheden()) {
            for (BetrokkenheidBericht bB : relatieBericht.getBetrokkenheden()) {
                registreerIdentificeerbaarObject(map, bB);
                if (bB instanceof OuderBericht) {
                    OuderBericht oB = (OuderBericht) bB;
                    registreerIdentificeerbaarObject(map, oB.getOuderlijkGezag());
                    registreerIdentificeerbaarObject(map, oB.getOuderschap());
                }

                if (null != bB.getPersoon()) {
                    registreerPersoon(map, bB.getPersoon());
                }
            }
        }
    }


    /**
     * Registreer de lijst van persoon en alles wat daaronder hoort.
     * @param map de hashmap
     * @param persoonBericht de persoon
     */
    private static void registreerPersoon(final CommunicatieIdMap map,
        final PersoonBericht persoonBericht)
    {
        // TODO bolie: dit moet anders, misschien kan Jibx alle identificeerbare objecten ook toeveoegd/bijhouden in
        // een hashmap in de jibx context en deze later ophalen.

        registreerIdentificeerbaarObject(map, persoonBericht);
        registreerIdentificeerbaarObject(map, persoonBericht.getNaamgebruik());
        registreerIdentificeerbaarObject(map, persoonBericht.getAfgeleidAdministratief());
        registreerIdentificeerbaarObject(map, persoonBericht.getBijhouding());
        if (persoonBericht.getIndicaties() != null) {
            registreerIdentificeerbaarObject(map, persoonBericht.getIndicatieBijzondereVerblijfsrechtelijkePositie());
        }
        registreerIdentificeerbaarObject(map, persoonBericht.getDeelnameEUVerkiezingen());
        registreerIdentificeerbaarObject(map, persoonBericht.getGeboorte());
        registreerIdentificeerbaarObject(map, persoonBericht.getGeslachtsaanduiding());
        registreerIdentificeerbaarObject(map, persoonBericht.getIdentificatienummers());
        registreerIdentificeerbaarObject(map, persoonBericht.getMigratie());
        registreerIdentificeerbaarObject(map, persoonBericht.getInschrijving());
        registreerIdentificeerbaarObject(map, persoonBericht.getOverlijden());
        registreerIdentificeerbaarObject(map, persoonBericht.getPersoonskaart());
        registreerIdentificeerbaarObject(map, persoonBericht.getSamengesteldeNaam());
        registreerIdentificeerbaarObject(map, persoonBericht.getUitsluitingKiesrecht());
        registreerIdentificeerbaarObject(map, persoonBericht.getVerblijfsrecht());

        if (null != persoonBericht.getAdressen()) {
            for (PersoonAdresBericht paB : persoonBericht.getAdressen()) {
                registreerIdentificeerbaarObject(map, paB);
                // paB.getStandaard() is GEEN identificeerbaarObject, althans niet in het model/bericht
            }
        }
        if (null != persoonBericht.getVoornamen()) {
            for (PersoonVoornaamBericht pvB : persoonBericht.getVoornamen()) {
                registreerIdentificeerbaarObject(map, pvB);
                // pvB.getStandaard() is GEEN identificeerbaarObject, althans niet in het model/bericht
            }
        }
        if (null != persoonBericht.getGeslachtsnaamcomponenten()) {
            for (PersoonGeslachtsnaamcomponentBericht pgnB : persoonBericht.getGeslachtsnaamcomponenten()) {
                registreerIdentificeerbaarObject(map, pgnB);
                // pgnB.getStandaard() is GEEN identificeerbaarObject, althans niet in het model/bericht
            }
        }
        if (null != persoonBericht.getNationaliteiten()) {
            for (PersoonNationaliteitBericht pnB : persoonBericht.getNationaliteiten()) {
                registreerIdentificeerbaarObject(map, pnB);
                // pnB.getStandaard() is GEEN identificeerbaarObject, althans niet in het model/bericht
            }
        }
        if (null != persoonBericht.getIndicaties()) {
            for (PersoonIndicatieBericht piB : persoonBericht.getIndicaties()) {
                registreerIdentificeerbaarObject(map, piB);
                // piB.getStandaard() is GEEN identificeerbaarObject, althans niet in het model/bericht
            }
        }
        if (null != persoonBericht.getReisdocumenten()) {
            for (PersoonReisdocumentBericht prB : persoonBericht.getReisdocumenten()) {
                registreerIdentificeerbaarObject(map, prB);
                // prB.getStandaard() is GEEN identificeerbaarObject, althans niet in het model/bericht
            }
        }

        if (null != persoonBericht.getBetrokkenheden()) {
            for (BetrokkenheidBericht bB: persoonBericht.getBetrokkenheden()) {
                registreerIdentificeerbaarObject(map, bB);
                registreerIdentificeerbaarObject(map, bB.getRelatie());
                for (BetrokkenheidBericht relbB : bB.getRelatie().getBetrokkenheden()) {
                    if (relbB != bB && bB.getPersoon() != null) {
                        registreerIdentificeerbaarObject(map, relbB);
                        registreerPersoon(map, relbB.getPersoon());
                    }
                }
                if (bB instanceof OuderBericht) {
                    OuderBericht oB = (OuderBericht) bB;
                    registreerIdentificeerbaarObject(map, oB.getOuderlijkGezag());
                    registreerIdentificeerbaarObject(map, oB.getOuderschap());
//                } else if (bB instanceof KindBericht) {
//                    KindBericht kB = (KindBericht)bB;
//                } else if (bB instanceof PartnerBericht) {
//                    PartnerBericht pB = (PartnerBericht)bB;
                }
                // misschien in de toekomst, moet hier ook extra dingen komen (afh. per type relatie).
                // bB.getRelatie().

            }
        }
    }

    /**
     * Registreer een indetificeerbare object in de hashmap, check if deze niet leeg of null is.
     * @param map de hashmap
     * @param ident het object
     */
    private static void registreerIdentificeerbaarObject(final CommunicatieIdMap map,
        final BerichtIdentificeerbaar ident)
    {
        if (null != ident && StringUtils.isNotBlank(ident.getCommunicatieID())) {
            map.put(ident);
        }
    }


    public static RegistreerGeboorteBericht bouwGeboorteBericht() {
        RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        return bericht;
    }
}
