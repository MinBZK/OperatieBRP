/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.util;

import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.administratie.CommunicatieIdMap;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingDocumentBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
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
import nl.bzk.brp.model.logisch.kern.ActieBron;
import org.apache.commons.lang.StringUtils;
import org.springframework.test.util.ReflectionTestUtils;


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
//    private static final Logger  LOGGER = LoggerFactory.getLogger(BerichtAdministratieUtil.class);

    private BerichtAdministratieUtil() {
    }

    /**
     * Verrijk een Berichtbericht object; lees alle objecten
     * @param bericht
     */
    public static void verrijktBerichtMetIdentificeerbaarObjectIndex(final AbstractBijhoudingsBericht bericht) {
        if (bericht != null) {
            if (null == bericht.getIdentificeerbaarObjectIndex()) {
                // heeft geen zetter, wordt normaal geinitialiseer door Jibx.
                ReflectionTestUtils.setField(bericht, "identificeerbaarObjectIndex", new CommunicatieIdMap());
            }
            CommunicatieIdMap map = bericht.getIdentificeerbaarObjectIndex();
            registreerAdministratie(map, bericht.getAdministratieveHandeling());
        }
    }

    /**
     * Registreer een administratieve handeling inc. alle objecten die daaronder vallen.
     * @param map de hashmap
     * @param admBericht het bericht
     * @param dubbeleIds als er iets fout gaat
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
     * @param dubbeleIds als er iets fout gaat
     */
    private static void registreerDocumenten(final CommunicatieIdMap map,
        final AdministratieveHandelingBericht admBericht)
    {
        if (null != admBericht.getBijgehoudenDocumenten()) {
            for (AdministratieveHandelingDocumentBericht docBericht : admBericht.getBijgehoudenDocumenten()) {
                registreerIdentificeerbaarObject(map, docBericht);
            }
        }
    }

    /**
     * Registreer de lijst van gedeblokkeerde meldingen die bij een administratieve handeling hoort.
     * @param map de hashmap
     * @param admBericht het bericht
     * @param dubbeleIds als er iets fout gaat
     */
    private static void registreerGedeblokkeerdeMeldingen(final CommunicatieIdMap map,
            final AdministratieveHandelingBericht admBericht)
    {
        if (null != admBericht.getGedeblokkeerdeMeldingen()) {
            for (AdministratieveHandelingGedeblokkeerdeMeldingBericht meldingBericht
                : admBericht.getGedeblokkeerdeMeldingen())
            {
                registreerIdentificeerbaarObject(map, meldingBericht);
            }
        }
    }

    /**
     * Registreer de lijst van acties en alles wat daaronder hoort die bij een administratieve handeling hoort.
     * @param map de hashmap
     * @param actieBericht de actie
     * @param dubbeleIds als er iets fout gaat
     */
    private static void registreerActie(final CommunicatieIdMap map, final ActieBericht actieBericht)
    {
        registreerIdentificeerbaarObject(map, actieBericht);
        if (null != actieBericht.getBronnen()) {
            for (ActieBron aB : actieBericht.getBronnen()) {
                registreerIdentificeerbaarObject(map, (ActieBronBericht) aB);
            }
        }
        if (null != actieBericht.getRootObjecten()) {
            for (RootObject rO : actieBericht.getRootObjecten()) {
                if (rO instanceof PersoonBericht) {
                    registreerPersoon(map, (PersoonBericht) rO);
                } else if (rO instanceof RelatieBericht) {
                    // huwelijk en registratieGeboorte.
                    registreerRelatie(map, (RelatieBericht) rO);
                } else {
                    throw new RuntimeException("Onbekende rootObject " + rO);
                }
            }
        }
    }

    /**
     * Registreer de lijst van relatie en alles wat daaronder hoort.
     * @param map de hashmap
     * @param relatieBericht de relatie
     * @param dubbeleIds als er iets fout gaat
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
//                } else if (bB instanceof KindBericht) {
//                    KindBericht kB = (KindBericht)bB;
//                } else if (bB instanceof PartnerBericht) {
//                    PartnerBericht pB = (PartnerBericht)bB;
                }
                // misschien in de toekomst, moet hier ook extra dingen komen (afh. per type relatie).
                // bB.getRelatie().

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
     * @param dubbeleIds als er iets fout gaat
     */
    private static void registreerPersoon(final CommunicatieIdMap map,
        final PersoonBericht persoonBericht)
    {
        // TODO bolie: dit moet anders, misschien kan Jibx alle identificeerbare objecten ook toeveoegd/bijhouden in
        // een hashmap in de jibx context en deze later ophalen.
        registreerIdentificeerbaarObject(map, persoonBericht);
        registreerIdentificeerbaarObject(map, persoonBericht.getAanschrijving());
        registreerIdentificeerbaarObject(map, persoonBericht.getAfgeleidAdministratief());
        registreerIdentificeerbaarObject(map, persoonBericht.getBijhoudingsaard());
        registreerIdentificeerbaarObject(map, persoonBericht.getBijhoudingsgemeente());
        registreerIdentificeerbaarObject(map, persoonBericht.getBijzondereVerblijfsrechtelijkePositie());
        registreerIdentificeerbaarObject(map, persoonBericht.getEUVerkiezingen());
        registreerIdentificeerbaarObject(map, persoonBericht.getGeboorte());
        registreerIdentificeerbaarObject(map, persoonBericht.getGeslachtsaanduiding());
        registreerIdentificeerbaarObject(map, persoonBericht.getIdentificatienummers());
        registreerIdentificeerbaarObject(map, persoonBericht.getImmigratie());
        registreerIdentificeerbaarObject(map, persoonBericht.getInschrijving());
        registreerIdentificeerbaarObject(map, persoonBericht.getOpschorting());
        registreerIdentificeerbaarObject(map, persoonBericht.getOverlijden());
        registreerIdentificeerbaarObject(map, persoonBericht.getPersoonskaart());
        registreerIdentificeerbaarObject(map, persoonBericht.getSamengesteldeNaam());
        registreerIdentificeerbaarObject(map, persoonBericht.getUitsluitingNLKiesrecht());
        registreerIdentificeerbaarObject(map, persoonBericht.getVerblijfstitel());

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
        final Identificeerbaar ident)
    {
        if (null != ident && StringUtils.isNotBlank(ident.getCommunicatieID())) {
            map.put(ident);
        }
    }


    public static InschrijvingGeboorteBericht bouwGeboorteBericht() {
        InschrijvingGeboorteBericht bericht = new InschrijvingGeboorteBericht();
        return bericht;
    }
}
