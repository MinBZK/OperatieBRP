/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import nl.bzk.brp.bijhouding.service.BhgAfstamming;
import nl.bzk.brp.brp0200.ActieRegistratieGeboorteBijhouding;
import nl.bzk.brp.brp0200.ActieRegistratieIdentificatienummers;
import nl.bzk.brp.brp0200.ActieRegistratieNaamgebruik;
import nl.bzk.brp.brp0200.ActieRegistratieNationaliteit;
import nl.bzk.brp.brp0200.AfstammingRegistreerGeboorteBijhouding;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingGedeblokkeerdeMeldingen;
import nl.bzk.brp.brp0200.ContainerHandelingActiesGeboorteInNederland;
import nl.bzk.brp.brp0200.ContainerPersoonGeslachtsnaamcomponenten;
import nl.bzk.brp.brp0200.ContainerPersoonNationaliteiten;
import nl.bzk.brp.brp0200.ContainerPersoonVoornamen;
import nl.bzk.brp.brp0200.ContainerRelatieBetrokkenheden;
import nl.bzk.brp.brp0200.DatumMetOnzekerheid;
import nl.bzk.brp.brp0200.GemeenteCode;
import nl.bzk.brp.brp0200.GeslachtsaanduidingCode;
import nl.bzk.brp.brp0200.GeslachtsaanduidingCodeS;
import nl.bzk.brp.brp0200.Geslachtsnaamstam;
import nl.bzk.brp.brp0200.GroepOuderOuderschap;
import nl.bzk.brp.brp0200.GroepPersoonGeboorte;
import nl.bzk.brp.brp0200.GroepPersoonGeslachtsaanduiding;
import nl.bzk.brp.brp0200.GroepPersoonIdentificatienummers;
import nl.bzk.brp.brp0200.GroepPersoonNaamgebruik;
import nl.bzk.brp.brp0200.GroepPersoonSamengesteldeNaam;
import nl.bzk.brp.brp0200.HandelingGeboorteInNederland;
import nl.bzk.brp.brp0200.Ja;
import nl.bzk.brp.brp0200.JaNee;
import nl.bzk.brp.brp0200.JaNeeS;
import nl.bzk.brp.brp0200.JaS;
import nl.bzk.brp.brp0200.Nationaliteitcode;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeFamilierechtelijkeBetrekking;
import nl.bzk.brp.brp0200.ObjecttypeGedeblokkeerdeMelding;
import nl.bzk.brp.brp0200.ObjecttypeKindBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeOuderBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonGeboorteBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.bzk.brp.brp0200.ObjecttypePersoonNationaliteit;
import nl.bzk.brp.brp0200.ObjecttypePersoonVoornaam;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.RedenVerkrijgingCode;
import nl.bzk.brp.brp0200.RegelCode;
import nl.bzk.brp.brp0200.Scheidingsteken;
import nl.bzk.brp.brp0200.Volgnummer;
import nl.bzk.brp.brp0200.Voornaam;
import nl.bzk.brp.brp0200.Voorvoegsel;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Constanten;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.InschrijvingGeboorteCommand;
import nl.bzk.brp.testclient.util.DatumUtil;
import org.apache.commons.lang.RandomStringUtils;


/**
 * Bijhoudings aanroeper die een registratie geboorte bijhoudings bericht kan creeeren en via de test client
 * afvuurt.
 */
public class InschrijvingGeboorteAanroeper extends AbstractAanroeper {

    // NB: Let op dat je bij het toevoegen van parameters, deze ook toevoegt in getParameterLijst()

    /** Parameter voor BSN van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_BSN                      = "BSN";
    /** Parameter voor naam van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_NAAM                     = "NAAM";
    /** Parameter voor voorvoegsel van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_VOORVOEGSEL              = "VOORVOEGSEL";
    /** Parameter voor scheidingsteken van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_SCHEIDINGSTEKEN          = "SCHEIDINGSTEKEN";
    /** Parameter voor BSN van de vader van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_BSN_OUDER1               = "BSN_OUDER1";
    /** Parameter voor BSN van de moeder van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_BSN_OUDER2               = "BSN_OUDER2";
    /** Parameter voor A-Nummer van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_ANUMMER                  = "ANUMMER";
    /** Parameter voor referentienummer van nieuw in te schrijven persoon. */
    public static final  String     PARAMETER_REFERENTIENUMMER         = "REFERENTIENUMMER";
    /** Parameter die aangeeft of er andere kinderen in zelfde huwelijk zitten met een andere naam dan de vader. */
    public static final  String     PARAMETER_KINDEREN_MET_ANDERE_NAAM = "KINDEREN_MET_ANDERE_NAAM";

    private static final String     DOCUMENT_SOORT_NAAM                = "Geboorteakte";
    private static final String     GEMEENTECODE                       = "0364";
    private static final String     VOORNAAM                           = "Testertje";
    private static final String     NATIONALITEIT_NAAM                 = "0001";
    private static final String     REDEN_VERKRIJGING_NAAM             = "017";
    private static final BigInteger VOLGNUMMER                         = BigInteger.valueOf(1);
    private static final String     COMMUNICATIE_ID_KIND               = "kind-1";
    private static final String     COMMUNICATIE_ID_GESL_NAAM_COMP     = "geslnaamcomp-1";

    private static final Map<String, String> DEBLOKKADE_REGELS = new HashMap<>();

    private final ObjectFactory objectFactory = getObjectFactory();

    static {
//        DEBLOKKADE_REGELS.put("BRBY0106", COMMUNICATIE_ID_GESL_NAAM_COMP);
        DEBLOKKADE_REGELS.put("BRBY0107", COMMUNICATIE_ID_KIND);
    }

    /**
     * Instantieert een nieuwe Inschrijving geboorte aanroeper.
     *
     * @param eigenschappen eigenschappen
     */
    public InschrijvingGeboorteAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }

    /**
     * Instantieert een nieuwe inschrijving geboorte aanroeper.
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType bijhouding port type
     * @param parameterMap de parameter map
     * @throws Exception de exception
     */
    public InschrijvingGeboorteAanroeper(final Eigenschappen eigenschappen,
                                         final BhgAfstamming bijhoudingPortType,
                                         final Map<String, String> parameterMap)
        throws Exception
    {
        super(eigenschappen, bijhoudingPortType, parameterMap);
    }

    @Override
    public void fire() {
        final AfstammingRegistreerGeboorteBijhouding bijhouding =
            creeerAfstammingInschrijvingAangifteGeboorteBijhouding();
        final InschrijvingGeboorteCommand command = new InschrijvingGeboorteCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Creeer afstamming inschrijving aangifte geboorte bijhouding.
     *
     * @return de afstamming inschrijving aangifte geboorte bijhouding
     */
    public AfstammingRegistreerGeboorteBijhouding creeerAfstammingInschrijvingAangifteGeboorteBijhouding() {
        final boolean reedsKinderenAanwezig =
            Boolean.parseBoolean(getValueFromValueMap(PARAMETER_KINDEREN_MET_ANDERE_NAAM, Boolean.FALSE.toString()));

        final AfstammingRegistreerGeboorteBijhouding bijhouding =
            objectFactory.createAfstammingRegistreerGeboorteBijhouding();

        // Stuurgegevens en parameters.

        final Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(bijhouding, prevalidatie);

        // Administratieve Handeling
        final HandelingGeboorteInNederland handeling = objectFactory.createHandelingGeboorteInNederland();
        handeling.setCommunicatieID(nextCommunicatieId());
        handeling.setObjecttype(OBJECTTYPE_ADMINISTRATIEVE_HANDELING);

        bijhouding.setGeboorteInNederland(objectFactory.createObjecttypeBerichtGeboorteInNederland(handeling));

        // Zet Partij code
        final PartijCode partijCode = new PartijCode();
        partijCode.setValue(ORGANISATIE);
        handeling.setPartijCode(objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Geboorte");
        handeling.setToelichtingOntlening(
            objectFactory.createObjecttypeAdministratieveHandelingToelichtingOntlening(ontleningstoelichting));

        // Indien er kinderen reeds aanwezig zijn binnen het huwelijk, dan overrule voor naam.
        if (reedsKinderenAanwezig) {
            // Voeg overrules toe voor opgegeven regels
            final ContainerAdministratieveHandelingGedeblokkeerdeMeldingen meldingen =
                new ContainerAdministratieveHandelingGedeblokkeerdeMeldingen();
            handeling.setGedeblokkeerdeMeldingen(
                objectFactory.createObjecttypeAdministratieveHandelingGedeblokkeerdeMeldingen(meldingen));

            for (Map.Entry<String, String> gedeblokkeerdeRegel : DEBLOKKADE_REGELS.entrySet()) {
                final RegelCode regelCode = new RegelCode();
                regelCode.setValue(gedeblokkeerdeRegel.getKey());
                final ObjecttypeGedeblokkeerdeMelding melding = new ObjecttypeGedeblokkeerdeMelding();
                melding.setCommunicatieID(nextCommunicatieId());
                melding.setReferentieID(gedeblokkeerdeRegel.getValue());
                melding.setObjecttype(OBJECTTYPE_GEDEBLOKKEERDE_MELDING);
                melding.setRegelCode(objectFactory.createObjecttypeGedeblokkeerdeMeldingRegelCode(regelCode));
                meldingen.getGedeblokkeerdeMelding().add(melding);
            }
        }

        // Bijgehouden documenten
        final String documentCommunicatieID =
                setBijgehoudenDocumenten(handeling, partijCode, DOCUMENT_SOORT_NAAM);

        // Acties-element
        final ContainerHandelingActiesGeboorteInNederland acties = new ContainerHandelingActiesGeboorteInNederland();
        handeling.setActies(objectFactory.createHandelingGeboorteInNederlandActies(acties));

        voegActieRegistratieGeboorteToe(acties, documentCommunicatieID);

	voegActieRegistratieIdentificatienummersToe(acties, documentCommunicatieID);

        voegActieRegistratieAanschrijvingToe(acties, documentCommunicatieID);

        voegActieRegistratieNationaliteitToe(acties, documentCommunicatieID);

        return bijhouding;
    }



    /**
     * Voeg actie registratie identificatienummers toe.
     * @param acties de acties.
     * @param documentId document id
     */
    private void voegActieRegistratieIdentificatienummersToe(final ContainerHandelingActiesGeboorteInNederland acties,
						      final String documentId)
    {

	final ActieRegistratieIdentificatienummers registratieIdentificatienummers =
		new ActieRegistratieIdentificatienummers();
	registratieIdentificatienummers.setObjecttype(OBJECTTYPE_ACTIE);
	registratieIdentificatienummers.setCommunicatieID(nextCommunicatieId());
	acties.setRegistratieIdentificatienummers(
		objectFactory.createContainerHandelingActiesGeboorteInNederlandRegistratieIdentificatienummers(
			registratieIdentificatienummers));

	registratieIdentificatienummers.setObjecttype(OBJECTTYPE_ACTIE);
	registratieIdentificatienummers.setCommunicatieID(nextCommunicatieId());
	final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
	datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
	registratieIdentificatienummers.setDatumAanvangGeldigheid(
		objectFactory.createDatumAanvangGeldigheid(datumAanvangGeldigheid));

	final ObjecttypePersoon kind = new ObjecttypePersoon();
	kind.setObjecttype(OBJECTTYPE_PERSOON);
	kind.setReferentieID(COMMUNICATIE_ID_KIND);
	kind.setCommunicatieID(nextCommunicatieId());

	// Identificatienummers
        final GroepPersoonIdentificatienummers identificatienummers = new GroepPersoonIdentificatienummers();
        identificatienummers.setCommunicatieID(nextCommunicatieId());
        kind.getIdentificatienummers().add(identificatienummers);

	// BSN kind
	final String bsn = getValueFromValueMap(PARAMETER_BSN, null);
        identificatienummers.setBurgerservicenummer(BijhoudingUtil.maakBsnElement(bsn));

	// A-nummer Kind
        final String anr = getValueFromValueMap(PARAMETER_ANUMMER, null);
        if (anr != null) {
            identificatienummers.setAdministratienummer(BijhoudingUtil.maakAnrElement(anr));
        } else {
	    // Kan mooier, maar geen check in bijhouding tot op heden
	    identificatienummers.setAdministratienummer(BijhoudingUtil.maakAnrElement("6857364754"));
	}

	final JAXBElement<ObjecttypePersoon> kindJaxb = objectFactory.createObjecttypeActiePersoon(kind);
	registratieIdentificatienummers.setPersoon(kindJaxb);
    }

    /**
     * Voeg actie registratie nationaliteit toe.
     * @param acties de acties.
     * @param documentId document id
     */
    private void voegActieRegistratieNationaliteitToe(final ContainerHandelingActiesGeboorteInNederland acties,
            final String documentId)
    {
        // Registratie Nationaliteit
        final ActieRegistratieNationaliteit actieRegistratieNationaliteit =
            new ActieRegistratieNationaliteit();
        acties.getRegistratieNationaliteit().add(actieRegistratieNationaliteit);

        actieRegistratieNationaliteit.setCommunicatieID(nextCommunicatieId());
        actieRegistratieNationaliteit.setObjecttype(OBJECTTYPE_ACTIE);
        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
        actieRegistratieNationaliteit.setDatumAanvangGeldigheid(
                objectFactory.createDatumAanvangGeldigheid(datumAanvangGeldigheid));

        BijhoudingUtil.setBronnen(actieRegistratieNationaliteit, documentId, nextCommunicatieId(), nextCommunicatieId());
        // Persoon
        final ObjecttypePersoon persoonNationaliteit = new ObjecttypePersoon();
        actieRegistratieNationaliteit.setPersoon(objectFactory.createObjecttypeActiePersoon(persoonNationaliteit));

        persoonNationaliteit.setCommunicatieID(nextCommunicatieId());
        persoonNationaliteit.setObjecttype(OBJECTTYPE_PERSOON);
        persoonNationaliteit.setReferentieID(COMMUNICATIE_ID_KIND);

        // Nationaliteiten
        final ContainerPersoonNationaliteiten nationaliteiten = new ContainerPersoonNationaliteiten();
        final ObjecttypePersoonNationaliteit nationaliteit = new ObjecttypePersoonNationaliteit();
        nationaliteit.setCommunicatieID(nextCommunicatieId());
        nationaliteit.setObjecttype(OBJECTTYPE_PERSOONNATIONALITEIT);
        nationaliteiten.getNationaliteit().add(nationaliteit);
        persoonNationaliteit.setNationaliteiten(objectFactory.createObjecttypePersoonNationaliteiten(nationaliteiten));

        // Nationaliteit / Code
        final Nationaliteitcode nationaliteitcode = new Nationaliteitcode();
        nationaliteitcode.setValue(NATIONALITEIT_NAAM);
        nationaliteit.setNationaliteitCode(
            objectFactory.createObjecttypePersoonNationaliteitNationaliteitCode(nationaliteitcode));

        // Nationaliteit / Reden Verkrijging
        final RedenVerkrijgingCode redenVerkrijgingCode = new RedenVerkrijgingCode();
        redenVerkrijgingCode.setValue(REDEN_VERKRIJGING_NAAM);
        nationaliteit.setRedenVerkrijgingCode(
            objectFactory.createObjecttypePersoonNationaliteitRedenVerkrijgingCode(redenVerkrijgingCode));
    }

    /**
     * Voeg acties registratie aanschrijving toe.
     * @param acties de acties.
     * @param documentId document id
     */
    private void voegActieRegistratieAanschrijvingToe(final ContainerHandelingActiesGeboorteInNederland acties,
            final String documentId)
    {
        // Registratie Aanschrijving
        final ActieRegistratieNaamgebruik actieRegistratieNaamgebruik = new ActieRegistratieNaamgebruik();
        acties.setRegistratieNaamgebruik(actieRegistratieNaamgebruik);

        actieRegistratieNaamgebruik.setCommunicatieID(nextCommunicatieId());
        actieRegistratieNaamgebruik.setObjecttype(OBJECTTYPE_ACTIE);
        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
        actieRegistratieNaamgebruik.setDatumAanvangGeldigheid(
                objectFactory.createDatumAanvangGeldigheid(datumAanvangGeldigheid));

        BijhoudingUtil.setBronnen(actieRegistratieNaamgebruik, documentId, nextCommunicatieId(), nextCommunicatieId());

        // Persoon
        final ObjecttypePersoon persoonAanschrijving = new ObjecttypePersoon();
        actieRegistratieNaamgebruik.setPersoon(objectFactory.createObjecttypeActiePersoon(persoonAanschrijving));

        persoonAanschrijving.setCommunicatieID(nextCommunicatieId());
        persoonAanschrijving.setObjecttype(OBJECTTYPE_PERSOON);
        persoonAanschrijving.setReferentieID(COMMUNICATIE_ID_KIND);

        // Groep Aanschrijving
        final GroepPersoonNaamgebruik naamgebruik = new GroepPersoonNaamgebruik();
        naamgebruik.setCommunicatieID(nextCommunicatieId());
        persoonAanschrijving.getNaamgebruik().add(naamgebruik);

        // Indicatie Algoritmisch Afgeleid
        final JaNee indicatieAlgoritmischAfgeleid = new JaNee();
        indicatieAlgoritmischAfgeleid.setValue(JaNeeS.J);
        naamgebruik.setIndicatieAfgeleid(
		objectFactory.createGroepPersoonNaamgebruikIndicatieAfgeleid(indicatieAlgoritmischAfgeleid));
    }

    /**
     * Voeg actie registratie geboorte toe aan acties.
     * @param acties de acties
     * @param documentId het id van het document.
     */
    private void voegActieRegistratieGeboorteToe(final ContainerHandelingActiesGeboorteInNederland acties,
        final String documentId)
    {
        // Registratie geboorte actie
        final ActieRegistratieGeboorteBijhouding actieRegistratieGeboorte = new ActieRegistratieGeboorteBijhouding();
        acties.setRegistratieGeboorte(actieRegistratieGeboorte);

        actieRegistratieGeboorte.setCommunicatieID(nextCommunicatieId());
        actieRegistratieGeboorte.setObjecttype(OBJECTTYPE_ACTIE);

        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
        actieRegistratieGeboorte.setDatumAanvangGeldigheid(objectFactory
                .createDatumAanvangGeldigheid(datumAanvangGeldigheid));

        // Bronnen
        BijhoudingUtil.setBronnen(actieRegistratieGeboorte, documentId, nextCommunicatieId(), nextCommunicatieId());

        // Familierechtelijke Betrekking
        final ObjecttypeFamilierechtelijkeBetrekking familierechtelijkeBetrekking =
                new ObjecttypeFamilierechtelijkeBetrekking();
        actieRegistratieGeboorte.setFamilierechtelijkeBetrekking(
                objectFactory.createObjecttypeActieFamilierechtelijkeBetrekking(familierechtelijkeBetrekking));

        familierechtelijkeBetrekking.setCommunicatieID(nextCommunicatieId());
        familierechtelijkeBetrekking.setObjecttype(OBJECTTYPE_RELATIE);

        // Betrokkenheden
        final ContainerRelatieBetrokkenheden betrokkenheden = new ContainerRelatieBetrokkenheden();
        familierechtelijkeBetrekking
                .setBetrokkenheden(objectFactory.createObjecttypeRelatieBetrokkenheden(betrokkenheden));

        voegKindBetrokkenheidToeAanRelatie(betrokkenheden);

        // Ouders
        String bsnVader = getValueFromValueMap(PARAMETER_BSN_OUDER1, null);
        if (bsnVader != null) {
            //System.out.println("bsnVader=" + bsnVader);
            voegOuderToe(betrokkenheden, bsnVader);
        }

        String bsnMoeder = getValueFromValueMap(PARAMETER_BSN_OUDER2, null);
        if (bsnMoeder != null) {
            //System.out.println("bsnMoeder=" + bsnMoeder);
            final ObjecttypeOuderBijhouding moeder =
                    voegOuderToe(betrokkenheden, bsnMoeder);
            // geeft de moeder Ouderschap
            zetMoederDeFlagOUWKIV(moeder);
        }
    }

    /**
     * Voeg ouder toe.
     *
     * @param betrokkenheden betrokkenheden
     * @param technischeSleutel technische sleutel
     * @return objecttype ouder bijhouding
     */
    private ObjecttypeOuderBijhouding voegOuderToe(final ContainerRelatieBetrokkenheden betrokkenheden,
                                                   final String technischeSleutel)
    {
        // Ouder
        final ObjecttypeOuderBijhouding ouderBetrokkenheid = new ObjecttypeOuderBijhouding();
        ouderBetrokkenheid.setCommunicatieID(nextCommunicatieId());
        ouderBetrokkenheid.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        betrokkenheden.getErkennerOrInstemmerOrKind().add(ouderBetrokkenheid);

        // Persoon
	final ObjecttypePersoon persoon = objectFactory.createObjecttypePersoon();
        persoon.setCommunicatieID(nextCommunicatieId());
        persoon.setObjecttype(OBJECTTYPE_PERSOON);
        persoon.setObjectSleutel(technischeSleutel);
        ouderBetrokkenheid.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(persoon));
        return ouderBetrokkenheid;
    }

    /**
     * Zet moeder de flag OUWKIV (ouder uit wie kind is voortgekomen).
     *
     * @param ouder ouder
     */
    private void zetMoederDeFlagOUWKIV(final ObjecttypeOuderBijhouding ouder) {
        // Ouderschap
        final GroepOuderOuderschap ouderschap = new GroepOuderOuderschap();
        ouderschap.setCommunicatieID(nextCommunicatieId());
        ouder.getOuderschap().add(ouderschap);

        // Indicatie Ouder Uit Wie Kind is Voortgekomen
        final Ja ja = new Ja();
        ja.setValue(JaS.J);
        ouderschap.setIndicatieAdresgevendeOuder(
                objectFactory.createGroepOuderOuderschapIndicatieAdresgevendeOuder(ja));
    }

    /**
     * Voeg een kind betrokkenheid toe aan de relatie.
     *
     * @param betrokkenheden container met betrokkenheden
     */
    private void voegKindBetrokkenheidToeAanRelatie(final ContainerRelatieBetrokkenheden betrokkenheden)
    {
        // Kind
        final ObjecttypeKindBijhouding kindBetrokkenheid = new ObjecttypeKindBijhouding();
        kindBetrokkenheid.setCommunicatieID(nextCommunicatieId());
        kindBetrokkenheid.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        betrokkenheden.getErkennerOrInstemmerOrKind().add(kindBetrokkenheid);

        // Persoon
        final ObjecttypePersoonGeboorteBijhouding kind = objectFactory.createObjecttypePersoonGeboorteBijhouding();
        final JAXBElement<ObjecttypePersoon> persoonElement = objectFactory.createObjecttypeBetrokkenheidPersoon(kind);
        kindBetrokkenheid.setPersoon(persoonElement);

        kind.setCommunicatieID(COMMUNICATIE_ID_KIND);
        kind.setObjecttype(OBJECTTYPE_PERSOON);

        // Samengestelde naam
        final GroepPersoonSamengesteldeNaam samengesteldeNaam = new GroepPersoonSamengesteldeNaam();
        samengesteldeNaam.setCommunicatieID(nextCommunicatieId());
        kind.getSamengesteldeNaam().add(samengesteldeNaam);

        // Indicatie namenreeks
        final JaNee indicatieNamenreeks = new JaNee();
        indicatieNamenreeks.setValue(JaNeeS.N);
        samengesteldeNaam.setIndicatieNamenreeks(
                objectFactory.createGroepPersoonSamengesteldeNaamIndicatieNamenreeks(indicatieNamenreeks));

        // Geboorte
        final GroepPersoonGeboorte geboorte = new GroepPersoonGeboorte();
        geboorte.setCommunicatieID(nextCommunicatieId());
        kind.getGeboorte().add(geboorte);

        // Geboorte datum en gemeente (gebdatum moet gelijk zijn aan dat aanv geld van actie)
        final DatumMetOnzekerheid datumPersoonGeboorte = new DatumMetOnzekerheid();
        datumPersoonGeboorte.setValue(DatumUtil.vandaagXmlString());
        geboorte.setDatum(objectFactory.createGroepPersoonGeboorteDatum(datumPersoonGeboorte));
        final GemeenteCode gemeenteCode = new GemeenteCode();
        gemeenteCode.setValue(GEMEENTECODE);
        geboorte.setGemeenteCode(objectFactory.createGroepPersoonGeboorteGemeenteCode(gemeenteCode));

        // Geslachtsaanduiding
        final GroepPersoonGeslachtsaanduiding geslachtsaanduiding = new GroepPersoonGeslachtsaanduiding();
        geslachtsaanduiding.setCommunicatieID(nextCommunicatieId());
        kind.getGeslachtsaanduiding().add(geslachtsaanduiding);

        // Geslachtsaanduiding code
        final GeslachtsaanduidingCode code = new GeslachtsaanduidingCode();
        code.setValue(GeslachtsaanduidingCodeS.M);
        geslachtsaanduiding.setCode(objectFactory.createGroepPersoonGeslachtsaanduidingCode(code));

        // Voornamen
        final ContainerPersoonVoornamen voornamen = new ContainerPersoonVoornamen();
        final ObjecttypePersoonVoornaam voornaam = new ObjecttypePersoonVoornaam();
        voornaam.setCommunicatieID(nextCommunicatieId());
        voornaam.setObjecttype(OBJECTTYPE_PERSOONVOORNAAM);
        voornamen.getVoornaam().add(voornaam);
        kind.setVoornamen(objectFactory.createObjecttypePersoonVoornamen(voornamen));

        // Voornaam
        // probeer voornaam anders te zetten, omdat anders de melding personalia bestaat al.
        final Voornaam naamVoornaam = new Voornaam();
        naamVoornaam.setValue(VOORNAAM + RandomStringUtils.randomNumeric(Constanten.DRIE));
        voornaam.setNaam(objectFactory.createObjecttypePersoonVoornaamNaam(naamVoornaam));

        // Volgnummer
        final Volgnummer volgnummerVoornaam = new Volgnummer();
        volgnummerVoornaam.setValue(VOLGNUMMER);
        voornaam.setVolgnummer(objectFactory.createObjecttypePersoonVoornaamVolgnummer(volgnummerVoornaam));

        // Geslachtsnaamcomponenten
        final ContainerPersoonGeslachtsnaamcomponenten geslachtsnaamcomponenten =
                new ContainerPersoonGeslachtsnaamcomponenten();
        final ObjecttypePersoonGeslachtsnaamcomponent geslachtsnaamcomponent =
                new ObjecttypePersoonGeslachtsnaamcomponent();
        geslachtsnaamcomponent.setCommunicatieID(COMMUNICATIE_ID_GESL_NAAM_COMP);
        geslachtsnaamcomponent.setObjecttype(OBJECTTYPE_PERSOONGESLNAAMCOMP);
        geslachtsnaamcomponenten.getGeslachtsnaamcomponent().add(geslachtsnaamcomponent);
        kind.setGeslachtsnaamcomponenten(
                objectFactory.createObjecttypePersoonGeslachtsnaamcomponenten(geslachtsnaamcomponenten));

        final String voorvoegselString = getValueFromValueMap(PARAMETER_VOORVOEGSEL, null);

        if (voorvoegselString != null) {
            final Voorvoegsel voorvoegsel = new Voorvoegsel();
            voorvoegsel.setValue(voorvoegselString);
            geslachtsnaamcomponent.setVoorvoegsel(
                    objectFactory.createObjecttypePersoonGeslachtsnaamcomponentVoorvoegsel(voorvoegsel));
        }

        final String scheidingstekenString = getValueFromValueMap(PARAMETER_SCHEIDINGSTEKEN, null);
        if (scheidingstekenString != null) {
            final Scheidingsteken scheidingsteken = new Scheidingsteken();
            scheidingsteken.setValue(scheidingstekenString);
            geslachtsnaamcomponent.setScheidingsteken(
                    objectFactory.createObjecttypePersoonGeslachtsnaamcomponentScheidingsteken(scheidingsteken));
        }

        // Naam
        String geslnaam = getValueFromValueMap(PARAMETER_NAAM, null);
        if (geslnaam != null) {
	    final Geslachtsnaamstam naamGeslachtsnaamcomponent = new Geslachtsnaamstam();
            naamGeslachtsnaamcomponent.setValue(geslnaam);
            geslachtsnaamcomponent.setStam(
                    objectFactory.createObjecttypePersoonGeslachtsnaamcomponentStam(
                            naamGeslachtsnaamcomponent));
        }
    }

    /**
     * Zet de stuurgegevens en parameters op het bijhoudingsbericht.
     *
     * @param objecttypeBerichtBijhouding het bijhoudingsbericht.
     * @param prevalidatie prevalidatie
     */
    protected void zetStuurgegevensEnParameters(final ObjecttypeBerichtBijhouding objecttypeBerichtBijhouding,
                                                final Boolean prevalidatie)
    {
        zetStuurgegevens(objecttypeBerichtBijhouding);
        objecttypeBerichtBijhouding.setParameters(BijhoudingUtil.maakBerichtParameters(prevalidatie));
    }

    @Override
    protected List<String> getParameterLijst() {
        return Arrays.asList(PARAMETER_REFERENTIENUMMER, PARAMETER_PREVALIDATIE, PARAMETER_BSN,
            PARAMETER_ANUMMER, PARAMETER_NAAM, PARAMETER_BSN_OUDER1, PARAMETER_BSN_OUDER2, PARAMETER_VOORVOEGSEL,
            PARAMETER_SCHEIDINGSTEKEN, PARAMETER_KINDEREN_MET_ANDERE_NAAM);
    }

    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_GEBOORTE;
    }
}
