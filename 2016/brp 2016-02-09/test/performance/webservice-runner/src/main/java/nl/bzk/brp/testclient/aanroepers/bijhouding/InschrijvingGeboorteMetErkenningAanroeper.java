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
import nl.bzk.brp.brp0200.ActieRegistratieGeboorteErkenningBijhouding;
import nl.bzk.brp.brp0200.ActieRegistratieIdentificatienummers;
import nl.bzk.brp.brp0200.ActieRegistratieNaamgebruik;
import nl.bzk.brp.brp0200.ActieRegistratieOuder;
import nl.bzk.brp.brp0200.AfstammingRegistreerGeboorteBijhouding;
import nl.bzk.brp.brp0200.ContainerAdministratieveHandelingGedeblokkeerdeMeldingen;
import nl.bzk.brp.brp0200.ContainerHandelingActiesGeboorteInNederlandMetErkenning;
import nl.bzk.brp.brp0200.ContainerPersoonGeslachtsnaamcomponenten;
import nl.bzk.brp.brp0200.ContainerPersoonVoornamen;
import nl.bzk.brp.brp0200.ContainerRelatieBetrokkenheden;
import nl.bzk.brp.brp0200.ContainerRelatieBetrokkenhedenAfstammingGeboorteErkenningBijhouding;
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
import nl.bzk.brp.brp0200.HandelingGeboorteInNederlandMetErkenning;
import nl.bzk.brp.brp0200.Ja;
import nl.bzk.brp.brp0200.JaNee;
import nl.bzk.brp.brp0200.JaNeeS;
import nl.bzk.brp.brp0200.JaS;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.ObjecttypeBerichtBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeFamilierechtelijkeBetrekking;
import nl.bzk.brp.brp0200.ObjecttypeFamilierechtelijkeBetrekkingGeboorteErkenningBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeGedeblokkeerdeMelding;
import nl.bzk.brp.brp0200.ObjecttypeKindBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeKindErkenningBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeOuderBijhouding;
import nl.bzk.brp.brp0200.ObjecttypeOuderWijzigingBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoon;
import nl.bzk.brp.brp0200.ObjecttypePersoonErkenningBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoonGeboorteBijhouding;
import nl.bzk.brp.brp0200.ObjecttypePersoonGeslachtsnaamcomponent;
import nl.bzk.brp.brp0200.ObjecttypePersoonVoornaam;
import nl.bzk.brp.brp0200.Ontleningstoelichting;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.RegelCode;
import nl.bzk.brp.brp0200.Scheidingsteken;
import nl.bzk.brp.brp0200.Volgnummer;
import nl.bzk.brp.brp0200.Voornaam;
import nl.bzk.brp.brp0200.Voorvoegsel;
import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.soapcommands.bijhouding.InschrijvingGeboorteCommand;
import nl.bzk.brp.testclient.util.DatumUtil;

/**
 * Bijhoudings aanroeper die een registratie geboorte met erkenning bijhoudings bericht kan creeeren en via de test
 * client afvuurt.
 */
public class InschrijvingGeboorteMetErkenningAanroeper extends AbstractAanroeper {

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
    private static final String     NAAM                               = "Elytis";
    private static final String     VOORVOEGSEL                        = null;
    private static final String     SCHEIDINGSTEKEN                    = null;
    private static final String     VOORNAAM                           = "Testertje";
    private static final BigInteger VOLGNUMMER                         = BigInteger.valueOf(1);
    private static final String     COMMUNICATIE_ID_KIND               = "kind-1";
    private static final String     COMMUNICATIE_ID_GESL_NAAM_COMP     = "geslnaamcomp-1";

    private static final Map<String, String> DEBLOKKADE_REGELS = new HashMap<>();

    private final ObjectFactory objectFactory = getObjectFactory();

    static {
        DEBLOKKADE_REGELS.put("BRBY0106", COMMUNICATIE_ID_GESL_NAAM_COMP);
        DEBLOKKADE_REGELS.put("BRBY0107", COMMUNICATIE_ID_KIND);
    }

    /**
     * Instantieert een nieuwe Inschrijving geboorte met erkenning aanroeper.
     *
     * @param eigenschappen eigenschappen
     */
    public InschrijvingGeboorteMetErkenningAanroeper(final Eigenschappen eigenschappen) {
        super(eigenschappen);
    }

    /**
     * Instantieert een nieuwe bijhouding aanroeper.
     *
     *
     * @param eigenschappen de eigenschappen
     * @param bijhoudingPortType bijhouding port type
     * @param parameterMap de parameter map  @throws Exception de exception
     * @throws Exception exception
     */
    public InschrijvingGeboorteMetErkenningAanroeper(final Eigenschappen eigenschappen,
                                                     final BhgAfstamming bijhoudingPortType,
                                                     final Map<String, String> parameterMap) throws Exception
    {
        super(eigenschappen, bijhoudingPortType, parameterMap);
    }

    @Override
    public void fire() {
        final AfstammingRegistreerGeboorteBijhouding bijhouding =
                creeerAfstammingRegistreerGeboorteBijhouding();
        final InschrijvingGeboorteCommand command = new InschrijvingGeboorteCommand(bijhouding);
        roepAan(command);
    }

    /**
     * Maakt het bijhoudings bericht aan.
     * @return Bijhoudings bericht JAXB class.
     */
    public AfstammingRegistreerGeboorteBijhouding creeerAfstammingRegistreerGeboorteBijhouding()
    {
        final boolean reedsKinderenAanwezig =
            Boolean.parseBoolean(getValueFromValueMap(PARAMETER_KINDEREN_MET_ANDERE_NAAM, Boolean.FALSE.toString()));

        final AfstammingRegistreerGeboorteBijhouding bijhouding =
            objectFactory.createAfstammingRegistreerGeboorteBijhouding();

        // Stuurgegevens en parameters.
        Boolean prevalidatie = getValueFromValueMap(PARAMETER_PREVALIDATIE, "").equalsIgnoreCase("J");
        zetStuurgegevensEnParameters(bijhouding, prevalidatie);

        // Administratieve Handeling
        final HandelingGeboorteInNederlandMetErkenning handeling =
            objectFactory.createHandelingGeboorteInNederlandMetErkenning();
        handeling.setCommunicatieID(nextCommunicatieId());
        handeling.setObjecttype(OBJECTTYPE_ADMINISTRATIEVE_HANDELING);
        bijhouding.setGeboorteInNederlandMetErkenning(
                objectFactory.createObjecttypeBerichtGeboorteInNederlandMetErkenning(handeling));


        // Zet Partij code
        final PartijCode partijCode = new PartijCode();
        partijCode.setValue(ORGANISATIE);
        handeling.setPartijCode(objectFactory.createObjecttypeAdministratieveHandelingPartijCode(partijCode));

        // Zet toelichting
        final Ontleningstoelichting ontleningstoelichting = new Ontleningstoelichting();
        ontleningstoelichting.setValue("Geboorte met erkenning");
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
        String documentCommunicatieID = super.setBijgehoudenDocumenten(handeling, partijCode, DOCUMENT_SOORT_NAAM);

        // Acties-element
        final ContainerHandelingActiesGeboorteInNederlandMetErkenning acties =
            new ContainerHandelingActiesGeboorteInNederlandMetErkenning();
        final JAXBElement<ContainerHandelingActiesGeboorteInNederlandMetErkenning> containerActies =
                objectFactory.createHandelingGeboorteInNederlandMetErkenningActies(acties);
        handeling.setActies(containerActies);

        //Registratie geboorte
        String familieRechtelijkeBetrekkingCommunicatieId =
                voegActieRegistratieGeboorteToe(acties, reedsKinderenAanwezig, documentCommunicatieID);

	voegActieRegistratieIdentificatienummersToe(acties, documentCommunicatieID);

        //Registratie erkenning
        voegActieRegistratieErkenningToe(acties, documentCommunicatieID, familieRechtelijkeBetrekkingCommunicatieId);

        //Actie registratie aanschrijving.
        voegActieRegistratieAanschrijvingToe(acties, documentCommunicatieID);

        return bijhouding;
    }

    /**
     * Voeg actie registratie aanschrijving toe.
     * @param acties de acties.
     * @param documentCommunicatieID document communicatie iD
     */
    private void voegActieRegistratieAanschrijvingToe(
            final ContainerHandelingActiesGeboorteInNederlandMetErkenning acties,
            final String documentCommunicatieID)
    {
         // Registratie Aanschrijving
        final ActieRegistratieNaamgebruik actieRegistratieNaamgebruik =
            new ActieRegistratieNaamgebruik();
        acties.setRegistratieNaamgebruik(actieRegistratieNaamgebruik);

        actieRegistratieNaamgebruik.setCommunicatieID(nextCommunicatieId());
        actieRegistratieNaamgebruik.setObjecttype(OBJECTTYPE_ACTIE);
        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
        actieRegistratieNaamgebruik.setDatumAanvangGeldigheid(
                objectFactory.createDatumAanvangGeldigheid(datumAanvangGeldigheid));

        // Bronnen
        BijhoudingUtil.setBronnen(actieRegistratieNaamgebruik, documentCommunicatieID, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Persoon

        final ObjecttypePersoon persoonNaamgebruik = new ObjecttypePersoon();
        actieRegistratieNaamgebruik.setPersoon(objectFactory.createObjecttypeActiePersoon(persoonNaamgebruik));

        persoonNaamgebruik.setCommunicatieID(nextCommunicatieId());
        persoonNaamgebruik.setObjecttype(OBJECTTYPE_PERSOON);
        persoonNaamgebruik.setReferentieID(COMMUNICATIE_ID_KIND);

        // Groep Aanschrijving
        final GroepPersoonNaamgebruik aanschrijving = new GroepPersoonNaamgebruik();
        aanschrijving.setCommunicatieID(nextCommunicatieId());
        persoonNaamgebruik.getNaamgebruik().add(aanschrijving);

        // Indicatie Algoritmisch Afgeleid
        final JaNee indicatieAlgoritmischAfgeleid = new JaNee();
        indicatieAlgoritmischAfgeleid.setValue(JaNeeS.J);
        aanschrijving.setIndicatieAfgeleid(
                objectFactory.createGroepPersoonNaamgebruikIndicatieAfgeleid(indicatieAlgoritmischAfgeleid));
    }

    /**
     * Voeg actie registratie erkenning toe.
     * @param acties de acties.
     * @param documentCommunicatieID document communicatie iD
     * @param familieRechtelijkeBetrekkingCommunicatieId familie rechtelijke betrekking communicatie id
     */
    private void voegActieRegistratieErkenningToe(
            final ContainerHandelingActiesGeboorteInNederlandMetErkenning acties,
            final String documentCommunicatieID, final String familieRechtelijkeBetrekkingCommunicatieId)
    {
        ActieRegistratieOuder registratieOuder =
                objectFactory.createActieRegistratieOuder();
        acties.setRegistratieOuder(registratieOuder);
        registratieOuder.setObjecttype(OBJECTTYPE_ACTIE);
        registratieOuder.setCommunicatieID(nextCommunicatieId());

        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
        registratieOuder.setDatumAanvangGeldigheid(
                objectFactory.createDatumAanvangGeldigheid(datumAanvangGeldigheid));

        BijhoudingUtil.setBronnen(registratieOuder, documentCommunicatieID, nextCommunicatieId(), nextCommunicatieId());

        // Familierechtelijke Betrekking
        final ObjecttypeFamilierechtelijkeBetrekking familierechtelijkeBetrekking =
                new ObjecttypeFamilierechtelijkeBetrekking();
        familierechtelijkeBetrekking.setReferentieID(familieRechtelijkeBetrekkingCommunicatieId);
        registratieOuder.setFamilierechtelijkeBetrekking(
                objectFactory.createObjecttypeActieFamilierechtelijkeBetrekking(familierechtelijkeBetrekking));

        // Betrokkenheden
        final ContainerRelatieBetrokkenheden betrokkenheden =
                new ContainerRelatieBetrokkenheden();
        familierechtelijkeBetrekking
                .setBetrokkenheden(objectFactory.createObjecttypeRelatieBetrokkenheden(betrokkenheden));
        familierechtelijkeBetrekking.setCommunicatieID(nextCommunicatieId());
        familierechtelijkeBetrekking.setObjecttype(OBJECTTYPE_RELATIE);

        //Kind voor erkenning
        ObjecttypeKindErkenningBijhouding kindBetr = objectFactory.createObjecttypeKindErkenningBijhouding();
        kindBetr.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        kindBetr.setCommunicatieID(nextCommunicatieId());
        betrokkenheden.getErkennerOrInstemmerOrKind().add(kindBetr);

        ObjecttypePersoonErkenningBijhouding kindPersoon = objectFactory.createObjecttypePersoonErkenningBijhouding();
        kindPersoon.setObjecttype(OBJECTTYPE_PERSOON);
        kindPersoon.setCommunicatieID(nextCommunicatieId());
        kindPersoon.setReferentieID(COMMUNICATIE_ID_KIND);
        kindBetr.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(kindPersoon));

        //Ouder
        // Ouder 2
        final ObjecttypeOuderWijzigingBijhouding ouderBetrokkenheid2 = new ObjecttypeOuderWijzigingBijhouding();
        ouderBetrokkenheid2.setCommunicatieID(nextCommunicatieId());
        ouderBetrokkenheid2.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        betrokkenheden.getErkennerOrInstemmerOrKind().add(ouderBetrokkenheid2);

        // Persoon
	final ObjecttypePersoon ouder2 = objectFactory.createObjecttypePersoon();
        ouder2.setCommunicatieID(nextCommunicatieId());
        ouder2.setObjecttype(OBJECTTYPE_PERSOON);
        ouder2.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN_OUDER1, null));
        ouderBetrokkenheid2.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(ouder2));
    }

    /**
     * Voeg actie registratie geboorte toe.
     * @param acties de acties.
     * @param reedsKinderenAanwezig boolean die aangeeft of er eventueel broertjes of zusjes zijn.
     * @param documentCommunicatieID document communicatie iD
     * @return communicatie id van fam rechtelijke betrekking
     */
    private String voegActieRegistratieGeboorteToe(
            final ContainerHandelingActiesGeboorteInNederlandMetErkenning acties,
            final boolean reedsKinderenAanwezig, final String documentCommunicatieID)
    {
        final ActieRegistratieGeboorteErkenningBijhouding actieRegistratieGeboorteErkenning =
                new ActieRegistratieGeboorteErkenningBijhouding();
        acties.setRegistratieGeboorte(actieRegistratieGeboorteErkenning);

        actieRegistratieGeboorteErkenning.setCommunicatieID(nextCommunicatieId());
        actieRegistratieGeboorteErkenning.setObjecttype(OBJECTTYPE_ACTIE);

        final DatumMetOnzekerheid datumAanvangGeldigheid = new DatumMetOnzekerheid();
        datumAanvangGeldigheid.setValue(DatumUtil.vandaagXmlString());
        actieRegistratieGeboorteErkenning.setDatumAanvangGeldigheid(
                objectFactory.createDatumAanvangGeldigheid(datumAanvangGeldigheid));

        BijhoudingUtil.setBronnen(actieRegistratieGeboorteErkenning, documentCommunicatieID, nextCommunicatieId(),
                                  nextCommunicatieId());

        // Familierechtelijke Betrekking
        final ObjecttypeFamilierechtelijkeBetrekkingGeboorteErkenningBijhouding familierechtelijkeBetrekking =
                new ObjecttypeFamilierechtelijkeBetrekkingGeboorteErkenningBijhouding();
        actieRegistratieGeboorteErkenning.setFamilierechtelijkeBetrekking(
                objectFactory.createObjecttypeActieFamilierechtelijkeBetrekking(familierechtelijkeBetrekking));

        final String familieRechtelijkeBetrekkingCommId = nextCommunicatieId();
        familierechtelijkeBetrekking.setCommunicatieID(familieRechtelijkeBetrekkingCommId);
        familierechtelijkeBetrekking.setObjecttype(OBJECTTYPE_RELATIE);

        // Betrokkenheden
        final ContainerRelatieBetrokkenheden betrokkenheden =
                new ContainerRelatieBetrokkenhedenAfstammingGeboorteErkenningBijhouding();
        familierechtelijkeBetrekking
                .setBetrokkenheden(objectFactory.createObjecttypeRelatieBetrokkenheden(betrokkenheden));

        //Kind
        voegKindBetrokkenheidToeAanRelatie(betrokkenheden, reedsKinderenAanwezig);

        //Ouder, 1 ouder omdat de andere ouder in de erkenning actie zit.
        // Ouder 1
        final ObjecttypeOuderBijhouding ouderBetrokkenheid1 = new ObjecttypeOuderBijhouding();
        ouderBetrokkenheid1.setCommunicatieID(nextCommunicatieId());
        ouderBetrokkenheid1.setObjecttype(OBJECTTYPE_BETROKKENHEID);
        betrokkenheden.getErkennerOrInstemmerOrKind().add(ouderBetrokkenheid1);

        // Persoon
	    final ObjecttypePersoon ouder1 = objectFactory.createObjecttypePersoon();
        ouder1.setCommunicatieID(nextCommunicatieId());
        ouder1.setObjecttype(OBJECTTYPE_PERSOON);
        ouder1.setObjectSleutel(getValueFromValueMap(PARAMETER_BSN_OUDER2, null));
	    ouderBetrokkenheid1.setPersoon(objectFactory.createObjecttypeBetrokkenheidPersoon(ouder1));

        // Ouderschap
        final GroepOuderOuderschap ouderschap1 = new GroepOuderOuderschap();
        ouderschap1.setCommunicatieID(nextCommunicatieId());
        ouderBetrokkenheid1.getOuderschap().add(ouderschap1);
        Ja ja = objectFactory.createJa();
        ja.setValue(JaS.J);
        ouderschap1.setIndicatieAdresgevendeOuder(
                objectFactory.createGroepOuderOuderschapIndicatieAdresgevendeOuder(ja));

        return familieRechtelijkeBetrekkingCommId;
    }

    /**
     * Voeg actie registratie identificatienummers toe.
     * @param acties de acties.
     * @param documentId document id
     */
    private void voegActieRegistratieIdentificatienummersToe(
	    final ContainerHandelingActiesGeboorteInNederlandMetErkenning acties,
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
     * Voeg een kind betrokkenheid toe aan de relatie.
     *
     * @param betrokkenheden container met betrokkenheden
     * @param reedsKinderenAanwezig of er al kinderen aanwezig zijn in de huidige relatie van Man en Vrouw.
     */
    private void voegKindBetrokkenheidToeAanRelatie(
            final ContainerRelatieBetrokkenheden betrokkenheden,
            final boolean reedsKinderenAanwezig)
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

        // Geboorte datum en gemeente
        final DatumMetOnzekerheid datumGeboorte = new DatumMetOnzekerheid();
        datumGeboorte.setValue(DatumUtil.vandaagXmlString());
        geboorte.setDatum(objectFactory.createGroepPersoonGeboorteDatum(datumGeboorte));
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
        final Voornaam naamVoornaam = new Voornaam();
        naamVoornaam.setValue(VOORNAAM);
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

        // Scheidingsteken en Voorvoegsel
        // Let op: Om te voorkomen dat BRAL0212 afgaat vullen we ze of beiden in of geen van beiden in.
        if (getValueFromValueMap(PARAMETER_VOORVOEGSEL, VOORVOEGSEL) != null
                && getValueFromValueMap(PARAMETER_SCHEIDINGSTEKEN, SCHEIDINGSTEKEN) != null)
        {
            final Voorvoegsel voorvoegsel = new Voorvoegsel();
            voorvoegsel.setValue(getValueFromValueMap(PARAMETER_VOORVOEGSEL, VOORVOEGSEL));
            geslachtsnaamcomponent
                    .setVoorvoegsel(
                            objectFactory.createObjecttypePersoonGeslachtsnaamcomponentVoorvoegsel(voorvoegsel));

            final Scheidingsteken scheidingsteken = new Scheidingsteken();
            scheidingsteken.setValue(getValueFromValueMap(PARAMETER_SCHEIDINGSTEKEN, SCHEIDINGSTEKEN));
            geslachtsnaamcomponent.setScheidingsteken(
                    objectFactory.createObjecttypePersoonGeslachtsnaamcomponentScheidingsteken(scheidingsteken));
        }

        // Naam
        final Geslachtsnaamstam naamGeslachtsnaamcomponent = new Geslachtsnaamstam();
        if (reedsKinderenAanwezig) {
            naamGeslachtsnaamcomponent.setValue(getValueFromValueMap(PARAMETER_NAAM, NAAM) + "Anders");
        } else {
            naamGeslachtsnaamcomponent.setValue(getValueFromValueMap(PARAMETER_NAAM, NAAM));
        }
        geslachtsnaamcomponent.setStam(
                objectFactory.createObjecttypePersoonGeslachtsnaamcomponentStam(naamGeslachtsnaamcomponent));
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
                             PARAMETER_ANUMMER, PARAMETER_NAAM, PARAMETER_BSN_OUDER1, PARAMETER_BSN_OUDER2,
                             PARAMETER_VOORVOEGSEL,
                             PARAMETER_SCHEIDINGSTEKEN, PARAMETER_KINDEREN_MET_ANDERE_NAAM);
    }
    @Override
    public BERICHT getBericht() {
        return BERICHT.BIJHOUDING_GEBOORTE_MET_ERKENNING;
    }
}
