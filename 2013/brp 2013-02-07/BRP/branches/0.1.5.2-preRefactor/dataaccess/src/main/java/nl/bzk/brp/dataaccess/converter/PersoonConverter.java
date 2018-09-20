/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import java.util.Collections;
import java.util.HashSet;

import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonIndicatie;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonAfgeleidAdministratief;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsVerantwoordelijke;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;
import nl.bzk.brp.model.logisch.groep.PersoonInschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonOverlijden;
import nl.bzk.brp.model.logisch.groep.PersoonRedenOpschorting;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeAanschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonIndicatie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.util.ObjectUtil;


// TODO Controlleer welke groepen verplicht zijn maar de gegevens in de database optioneel zijn.
// https://www.modernodam.nl/jira/browse/SIERRA-56
/** Converteert objecten van operationeel model naar logisch model. in dit geval gaat het om persoon. */
public final class PersoonConverter {

    // private static final Logger LOGGER = LoggerFactory.getLogger(PersoonConverter.class);

    /** default constructor. */
    private PersoonConverter() {

    }

    /**
     * Stel samen de groep identifictienummers {@link PersoonIdentificatienummers}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyPersoonIdentificatienummers(final Persoon persoon,
            final PersistentPersoon persistentPersoon, final PersoonConverterConfiguratie configuratie)
    {
        if (configuratie.isConverteerbaar(PersoonConverterGroep.IDENTIFICATIE_NUMMERS)) {
            // datumAanvangGeldigheid (van C laag)
            // datumEindeGeldigheid (van C laag)
            PersoonIdentificatienummers pId = new PersoonIdentificatienummers();
            pId.setBurgerservicenummer(persistentPersoon.getBurgerservicenummer());
            pId.setAdministratienummer(persistentPersoon.getANummer());
            persoon.setIdentificatienummers(pId);
        }
    }

    /**
     * Stel samen de groep samengesteldeNaam {@link PersoonSamengesteldeNaam}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copySamengesteldeNaam(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        // Letop: we testen alleen of de geslachtsnaam, algoritmisch afgeleid en namenreeks als geslachtsnaam
        // ingevuld zijn voor de groep, omdat volgens de xsd dit element gevuld MOET zijn als de groep bestaat.
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.SAMENGESTELDE_NAAM))
            && (!ObjectUtil.isAllEmpty(persistentPersoon.getGeslachtsNaam()
                    , persistentPersoon.getIndAlgoritmischAfgeleid()
                    , persistentPersoon.getIndReeksAlsGeslachtnaam()
            )))
        {
            PersoonSamengesteldeNaam psgn = new PersoonSamengesteldeNaam();
            psgn.setAdellijkeTitel(persistentPersoon.getAdellijkeTitel());
            psgn.setGeslachtsnaam(persistentPersoon.getGeslachtsNaam());
            psgn.setIndAlgoritmischAfgeleid(ObjectUtil.converteerNaarFalse(persistentPersoon
                    .getIndAlgoritmischAfgeleid()));
            psgn.setIndNamenreeksAlsGeslachtsnaam(ObjectUtil.converteerNaarFalse(persistentPersoon
                    .getIndReeksAlsGeslachtnaam()));
            psgn.setPredikaat(persistentPersoon.getPredikaat());
            psgn.setScheidingsTeken(persistentPersoon.getScheidingsTeken());
            psgn.setVoornamen(persistentPersoon.getVoornaam());
            psgn.setVoorvoegsel(persistentPersoon.getVoorvoegsel());
            persoon.setSamengesteldenaam(psgn);
        }
    }

    /**
     * Stel samen de groep voornamen {@link PersistentPersoonVoornaam}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyVoornamen(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if (configuratie.isConverteerbaar(PersoonConverterGroep.VOORNAMEN)
            && (persistentPersoon.getPersoonVoornamen() != null))
        {
            for (PersistentPersoonVoornaam ppv : persistentPersoon.getPersoonVoornamen()) {
                PersoonVoornaam pv = new PersoonVoornaam();
                pv.setVolgnummer(ppv.getVolgnummer());
                pv.setNaam(ppv.getNaam());
                persoon.voegPersoonVoornaamToe(pv);
            }
            // sorteer op persoon, volgnr, ...
            Collections.sort(persoon.getPersoonVoornamen());
        }
    }

    /**
     * Stel samen de groep persoon identiteit {@link PersoonIdentiteit}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     */
    private static void copyPersoonIdentiteit(final Persoon persoon, final PersistentPersoon persistentPersoon) {
        // hier hebben we geen flag voor nodig. altijd converteren.
        PersoonIdentiteit identiteit = new PersoonIdentiteit();
        identiteit.setId(persistentPersoon.getId());
        identiteit.setSoort(persistentPersoon.getSoortPersoon());
        persoon.setIdentiteit(identiteit);
    }

    /**
     * Stel samen de groep geslachtsaanduiding {@link PersoonGeslachtsAanduiding}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyPersoonGeslachtAanduiding(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.GESLACHTS_AANDUIDING))
            && (null != persistentPersoon.getGeslachtsAanduiding()))
        {
            persoon.setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
            persoon.getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(persistentPersoon.getGeslachtsAanduiding());
        }
    }

    /**
     * Stel samen de groep aanschrijving {@link PersoonSamengesteldeAanschrijving}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyAanschrijving(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        // Letop: groep aanschrijving moet een indicatie hebben en een geslachtsnaam
        if (configuratie.isConverteerbaar(PersoonConverterGroep.AANSCHRIJVING)
            && (!ObjectUtil.isAllEmpty(persistentPersoon.getAanschrijvingGeslachtnaam()
                , persistentPersoon.getIndAanschrijvingAlgoritmischAfgeleid()
            )))
        {
            PersoonSamengesteldeAanschrijving psga = new PersoonSamengesteldeAanschrijving();

            psga.setGeslachtsnaam(ObjectUtil.converteerNaarEmpty(persistentPersoon.getAanschrijvingGeslachtnaam()));
            psga.setIndAanschrijvingMetAdellijkeTitels(persistentPersoon.getIndAanschrijvingMetAdellijkeTitels());
            psga.setIndAlgoritmischAfgeleid(ObjectUtil.converteerNaarFalse(persistentPersoon
                    .getIndAanschrijvingAlgoritmischAfgeleid()));
            psga.setPredikaat(persistentPersoon.getAanschrijvingPredikaat());
            psga.setScheidingsTeken(persistentPersoon.getAanschrijvingScheidingteken());
            psga.setVoornamen(persistentPersoon.getAanschrijvingVoornamen());
            psga.setVoorvoegsel(persistentPersoon.getAanschrijvingVoorvoegsel());
            psga.setWijzeGebruikGeslachtsnaam(persistentPersoon.getAanschrijvingWijzeGebruikGeslachtsnaam());
            persoon.setSamengesteldeAanschrijving(psga);
        }
    }

    /**
     * Stel samen de groep geboorte {@link PersoonGeboorte}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyGeboorte(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        // Letop: we testen de datum EN land ingevuld zij voor de groep, omdat volgens de xsd
        // deze elementen gevuld MOETEN zijn als de groep bestaat.
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.GEBOORTE))
            && (null != persistentPersoon.getDatumGeboorte() && null != persistentPersoon.getLandGeboorte()))
        {
            PersoonGeboorte persGeb = new PersoonGeboorte();
            persGeb.setDatumGeboorte(persistentPersoon.getDatumGeboorte());
            persGeb.setGemeenteGeboorte(persistentPersoon.getGemeenteGeboorte());
            persGeb.setLandGeboorte(persistentPersoon.getLandGeboorte());
            persGeb.setWoonplaatsGeboorte(persistentPersoon.getWoonplaatsGeboorte());
            persGeb.setBuitenlandsePlaats(persistentPersoon.getBuitenlandGeboortePlaats());
            persGeb.setBuitenlandseRegio(persistentPersoon.getBuitenlandGeboorteRegio());
            persGeb.setOmschrijvingLocatie(persistentPersoon.getOmschrijvingGeboorteLocatie());
            persoon.setGeboorte(persGeb);
        }
    }

    /**
     * Stel samen de groep overlijden {@link PersoonOverlijden}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyOverlijden(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        // Letop: we testen de datum EN land ingevuld zij voor de groep, omdat volgens de xsd
        // deze elementen gevuld MOETEN zijn als de groep bestaat.
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.OVERLIJDEN))
            && (null != persistentPersoon.getDatumOverlijden() && null != persistentPersoon.getLandOverlijden()))
        {
            PersoonOverlijden po = new PersoonOverlijden();
            po.setBuitenlandsePlaats(persistentPersoon.getBuitenlandOverlijdenPlaats());
            po.setBuitenlandseRegio(persistentPersoon.getBuitenlandOverlijdenRegio());
            po.setDatumOverlijden(persistentPersoon.getDatumOverlijden());
            po.setGemeenteOverlijden(persistentPersoon.getGemeenteOverljden());
            po.setLandOverlijden(persistentPersoon.getLandOverlijden());
            po.setOmschrijvingLocatie(persistentPersoon.getOmschrijvingOverlijdenLocatie());
            po.setWoonplaatsOverlijden(persistentPersoon.getWoonplaatsOverlijden());
            persoon.setOverlijden(po);
        }
    }

    /**
     * Stel samen de groep bijhouding sverantwoordelijkheden {@link PersoonBijhoudingsVerantwoordelijke}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyBijhoudingsverantwoordelijkheid(final Persoon persoon,
            final PersistentPersoon persistentPersoon, final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.BIJHOUDING_VERANTWOORDELIJKE))
            && (null != persistentPersoon.getVerantwoordelijke()))
        {
            // TODO Datum bijhoudingsverantwoordelijkheid
            PersoonBijhoudingsVerantwoordelijke bhv = new PersoonBijhoudingsVerantwoordelijke();
            bhv.setVerantwoordelijke(persistentPersoon.getVerantwoordelijke());
            persoon.setBijhoudingVerantwoordelijke(bhv);
        }
    }

    /**
     * Stel samen de groep reden tot opschorting {@link PersoonRedenOpschorting}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyRedenOpschorting(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.OPSCHORTING))
            && (!ObjectUtil.isAllEmpty(persistentPersoon.getRedenOpschortingBijhouding())))
        {
            PersoonRedenOpschorting pro = new PersoonRedenOpschorting();
            pro.setRedenOpschortingBijhouding(persistentPersoon.getRedenOpschortingBijhouding());
            persoon.setRedenOpschorting(pro);
        }
    }

    /**
     * Stel samen de groep bijhouding gemeente {@link PersoonBijhoudingsGemeente}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyBijhoudingsgemeente(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.BIJHOUDING_GEMEENTE))
            && (!ObjectUtil.isAllEmpty(persistentPersoon.getBijhoudingsGemeente(),
                    persistentPersoon.getBijhoudingsGemeenteDatumInschrijving(),
                    persistentPersoon.getBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig())))
        {
            PersoonBijhoudingsGemeente bGem = new PersoonBijhoudingsGemeente();
            bGem.setGemeente(persistentPersoon.getBijhoudingsGemeente());
            bGem.setDatumInschrijving(persistentPersoon.getBijhoudingsGemeenteDatumInschrijving());
            bGem.setIndOnverwerktDocumentAanwezig(persistentPersoon
                    .getBijhoudingsGemeenteIndicatieOnverwerktDocumentAanwezig());
            persoon.setBijhoudingGemeente(bGem);
        }
    }

    /**
     * Stel samen de groep inschrijving {@link PersoonInschrijving} .
     * Let op, er moet nog uitgewerkt worden hoe we de vorig en volgend persoon ophalen.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyInschrijving(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        // bij inschrijving is volgens de xsd de datum en versie nr veplicht.
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.INSCHRIJVING))
            && (!ObjectUtil.isAllEmpty(persistentPersoon.getInschrijvingDatum(),
                    persistentPersoon.getInschrijvingVersienummer())))
        {
            // we doen hier nog niets mee, want we moeten 2 complete persistentpersonen ophalen voor zijn bsn en anr
            PersoonInschrijving persoonInschrijving = new PersoonInschrijving();
            persoonInschrijving.setDatumInschrijving(persistentPersoon.getInschrijvingDatum());
            persoonInschrijving.setVersienummer(persistentPersoon.getInschrijvingVersienummer());
            // TODO: setVolgendAnr(null); setVolgendBsn(null); setVorigAnr(null); setVorigBsn(null);
            persoon.setInschrijving(persoonInschrijving);
        }
    }

    /**
     * Stel samen de groep afgeleid adminitratief {@link PersoonAfgeleidAdministratief} .
     * Let op, er moet nog uitgewerkt worden hoe we de vorig en volgend persoon ophalen.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyAfgeleidAdministratief(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE))
            && (!ObjectUtil.isAllEmpty(persistentPersoon.getLaatstGewijzigd(),
                    persistentPersoon.getIndGegevensInOnderzoek())))
        {
            PersoonAfgeleidAdministratief paa = new PersoonAfgeleidAdministratief();
            paa.setLaatstGewijzigd(persistentPersoon.getLaatstGewijzigd());
            paa.setIndGegevensInOnderzoek(persistentPersoon.getIndGegevensInOnderzoek());
            persoon.setAfgeleidAdministratief(paa);
        }
    }

    /**
     * Stel samen de groep geslachtsnaam componenten {@link PersoonGeslachtsnaamcomponent} .
     * Let op, er moet nog uitgewerkt worden hoe we de vorig en volgend persoon ophalen.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyGeslachtsnaamcomponenten(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.GESLACHTSNAAM_COMPONENTEN))
            && (persistentPersoon.getPersoonGeslachtsnaamcomponenten() != null))
        {
            for (PersistentPersoonGeslachtsnaamcomponent comp : persistentPersoon.getPersoonGeslachtsnaamcomponenten())
            {
                PersoonGeslachtsnaamcomponent pgnc = new PersoonGeslachtsnaamcomponent();
                pgnc.setAdellijkeTitel(comp.getAdellijkeTitel());
                pgnc.setNaam(comp.getNaam());
                pgnc.setPredikaat(comp.getPredikaat());
                pgnc.setScheidingsTeken(comp.getScheidingsteken());
                pgnc.setVolgnummer(comp.getVolgnummer());
                pgnc.setVoorvoegsel(comp.getVoorvoegsel());
                persoon.voegGeslachtsnaamcomponentToe(pgnc);
            }
            // sorteer op persoon, volgnr, ...
            Collections.sort(persoon.getGeslachtsnaamcomponenten());
        }
    }

    /**
     * Stel samen de groep nationaliteiten @link PersoonNationaliteit}.
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyNationaliteiten(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        // we gaan vanuit dat persoon al een lege set heeft met PersoonNationaliteit(en).
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.NATIONALITEIT))
            && (persistentPersoon.getNationaliteiten() != null))
        {
            for (PersistentPersoonNationaliteit persistentPersoonNationaliteit : persistentPersoon.getNationaliteiten())
            {
                persoon.getNationaliteiten().add(
                        NationaliteitConverter.converteerOperationeelNaarLogisch(persistentPersoonNationaliteit));
            }
        }
    }

    /**
     * Stel samen de groep adressen .
     *
     * @param persoon de logische object waarin het geplaatst dient te worden
     * @param persistentPersoon het operationele object
     * @param configuratie de configuratie
     */
    private static void copyAdressen(final Persoon persoon, final PersistentPersoon persistentPersoon,
            final PersoonConverterConfiguratie configuratie)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.ADRESSEN))
            && (persistentPersoon.getAdressen() != null && !persistentPersoon.getAdressen().isEmpty()))
        {
            persoon.setAdressen(new HashSet<PersoonAdres>());
            for (PersistentPersoonAdres persistentPersoonAdres : persistentPersoon.getAdressen()) {
                persoon.getAdressen().add(
                        PersoonAdresConverter.converteerOperationeelNaarLogisch(persistentPersoonAdres));
            }
            // eventuele optimalisatie: als lijst groter is dan 1, dan sorteren.
        }
    }

    /**
     * Kopieert betrokkenheden en relaties. Deze functie navigeert voor elke betrokkenheid die persistentPersoon heeft
     * naar de relatie, omdat relatie als root node fungeert. Vervolgens wordt vanaf relatie alles gekopieerd tot aan
     * de betrokkenheid en persoon. Van gerelateerde personen wordt geen diepe kopie gemaakt, m.a.w. de betrokkenheden
     * en relaties daarvan worden NIET gekopieerd omdat je dan een stackOverflow error krijgt.
     *
     * @param persoon De resulterende persoon die de kopie krijgt.
     * @param persistentPersoon De te kopieren persoon.
     * @param configuratie De configuratie voor de converter.
     * @param limiteerGroepenInBetrokkenheden Parameter die aangeeft of alle groepen van de betrokkenheden moeten worden
     * gequeried en gekopieerd. Indien true, dan worden ALLEEN de groepen identificatienummers en afgeleidadministratief
     * meegenomen.
     */
    private static void copyBetrokkenheden(final Persoon persoon, final PersistentPersoon persistentPersoon,
                                           final PersoonConverterConfiguratie configuratie,
                                           final boolean limiteerGroepenInBetrokkenheden)
    {
        if ((configuratie.isConverteerbaar(PersoonConverterGroep.BETROKKENHEDEN))
            && (persistentPersoon.getBetrokkenheden() != null))
        {
            // NAvigeer naar de root node van elke betrokkenheid, root node is relatie.
            // Kopieer dan vanaf de rootnode.
            for (PersistentBetrokkenheid pbetrokkenheid : persistentPersoon.getBetrokkenheden()) {
                PersistentRelatie relatieRootNode = pbetrokkenheid.getRelatie();
                Relatie relatie = new Relatie();
                relatie.setDatumAanvang(relatieRootNode.getDatumAanvang());
                relatie.setBuitenlandsePlaatsAanvang(relatieRootNode.getBuitenlandsePlaatsAanvang());
                relatie.setBuitenlandsePlaatsEinde(relatieRootNode.getBuitenlandsePlaatsEinde());
                relatie.setBuitenlandseRegioAanvang(relatieRootNode.getBuitenlandseRegioAanvang());
                relatie.setBuitenlandseRegioEinde(relatieRootNode.getBuitenlandseRegioEinde());
                relatie.setDatumEinde(relatieRootNode.getDatumEinde());
                relatie.setGemeenteAanvang(relatieRootNode.getGemeenteAanvang());
                relatie.setGemeenteEinde(relatieRootNode.getGemeenteEinde());
                relatie.setLandAanvang(relatieRootNode.getLandAanvang());
                relatie.setLandEinde(relatieRootNode.getLandEinde());
                relatie.setOmschrijvingLocatieAanvang(relatieRootNode.getOmschrijvingLocatieAanvang());
                relatie.setOmschrijvingLocatieEinde(relatieRootNode.getOmschrijvingLocatieEinde());
                relatie.setPlaatsAanvang(relatieRootNode.getPlaatsAanvang());
                relatie.setPlaatsEinde(relatieRootNode.getPlaatsEinde());
                relatie.setRedenBeeindigingRelatie(relatieRootNode.getRedenBeeindigingRelatie());
                relatie.setSoortRelatie(relatieRootNode.getSoortRelatie());
                for (PersistentBetrokkenheid persistentBetrokkenheid : relatieRootNode.getBetrokkenheden()) {
                    Betrokkenheid betrokkenheid = new Betrokkenheid();
                    if (persistentBetrokkenheid.getBetrokkene().getId().equals(persistentPersoon.getId())) {
                        betrokkenheid.setBetrokkene(persoon);
                        persoon.getBetrokkenheden().add(betrokkenheid);
                    } else {
                        final Persoon betrokkene;
                        if (limiteerGroepenInBetrokkenheden) {
                            //Indien betrokkene niet ingeschreven is, dan alleen de volgende groepen kopieren:
                            // - Geboorte
                            // - Samengestelde naam
                            // - GeslachtsAanduiding
                            final PersistentPersoon persistentBetrokkene = persistentBetrokkenheid.getBetrokkene();
                            final PersoonConverterConfiguratie subConfig = new PersoonConverterConfiguratie();
                            if (SoortPersoon.NIET_INGESCHREVENE == persistentBetrokkene.getSoortPersoon()) {
                                subConfig.getGroepen().add(PersoonConverterGroep.GEBOORTE);
                                subConfig.getGroepen().add(PersoonConverterGroep.SAMENGESTELDE_NAAM);
                                subConfig.getGroepen().add(PersoonConverterGroep.GESLACHTS_AANDUIDING);
                            } else {
                                //Indien betrokkene wel ingeschreven is, dan alleen de volgende groepen kopieren:
                                // - Identificatie nummers
                                // - Afgeleid administratief.
                                subConfig.getGroepen().add(PersoonConverterGroep.IDENTIFICATIE_NUMMERS);
                                subConfig.getGroepen().add(PersoonConverterGroep.AFGELEIDE_ADMINISTRATIE);
                            }

                            betrokkene =
                                    converteerOperationeelNaarLogisch(persistentBetrokkene, false,
                                            subConfig);
                        } else {
                            betrokkene =
                                    converteerOperationeelNaarLogisch(persistentBetrokkenheid.getBetrokkene(), false,
                                            configuratie);
                        }
                        betrokkenheid.setBetrokkene(betrokkene);
                    }
                    betrokkenheid.setIndOuder(persistentBetrokkenheid.isIndOuder());
                    betrokkenheid.setDatumAanvangOuderschap(persistentBetrokkenheid.getDatumAanvangOuderschap());
                    betrokkenheid.setIndOuderHeeftGezag(persistentBetrokkenheid.isIndOuderHeeftGezag());
                    betrokkenheid.setSoortBetrokkenheid(persistentBetrokkenheid.getSoortBetrokkenheid());
                    betrokkenheid.setRelatie(relatie);
                    if (relatie.getBetrokkenheden() == null) {
                        relatie.setBetrokkenheden(new HashSet<Betrokkenheid>());
                    }
                    relatie.getBetrokkenheden().add(betrokkenheid);
                }
            }
        }
    }

    /**
     * @param persistentPersoon De te kopieren persoon.
     * @param configuratie De configuratie voor de converter.
     * @param inclusiefBetrokkenheidEnRelatie Indien true, kopieert ook betrokkenheden en relaties.
     * @param limiteerGroepenInBetrokkenheden Parameter die aangeeft of alle groepen van de betrokkenheden moeten worden
     * gequeried en gekopieerd. Indien true, dan worden ALLEEN de groepen identificatienummers en afgeleidadministratief
     * meegenomen.
     * @return De geconverteerde persoon.
     */
    public static Persoon converteerOperationeelNaarLogisch(final PersistentPersoon persistentPersoon,
            final boolean inclusiefBetrokkenheidEnRelatie, final PersoonConverterConfiguratie configuratie,
            final boolean limiteerGroepenInBetrokkenheden)
    {
        Persoon persoon = null;
        if (persistentPersoon != null) {
            persoon = new Persoon();
            // 1-1 relatie groepen
            copyPersoonIdentiteit(persoon, persistentPersoon);
            copyPersoonIdentificatienummers(persoon, persistentPersoon, configuratie);
            copyPersoonGeslachtAanduiding(persoon, persistentPersoon, configuratie);
            copySamengesteldeNaam(persoon, persistentPersoon, configuratie);
            copyAanschrijving(persoon, persistentPersoon, configuratie);
            copyGeboorte(persoon, persistentPersoon, configuratie);
            copyOverlijden(persoon, persistentPersoon, configuratie);
            copyBijhoudingsverantwoordelijkheid(persoon, persistentPersoon, configuratie);
            copyBijhoudingsgemeente(persoon, persistentPersoon, configuratie);
            copyInschrijving(persoon, persistentPersoon, configuratie);
            copyAfgeleidAdministratief(persoon, persistentPersoon, configuratie);
            copyRedenOpschorting(persoon, persistentPersoon, configuratie);
            // copyPersoonskaart(persoon, persistentPersoon);
            // copyUitsluitingNLKiesrecht(persoon, persistentPersoon);
            // copyEUVerkiezingen(persoon, persistentPersoon);
            // copyImmigratie(persoon, persistentPersoon);
            if (inclusiefBetrokkenheidEnRelatie) {
                copyBetrokkenheden(persoon, persistentPersoon, configuratie, limiteerGroepenInBetrokkenheden);
            }

            // 1-n relatie
            copyVoornamen(persoon, persistentPersoon, configuratie);
            copyGeslachtsnaamcomponenten(persoon, persistentPersoon, configuratie);
            copyNationaliteiten(persoon, persistentPersoon, configuratie);
            copyAdressen(persoon, persistentPersoon, configuratie);

            // indicaties
            if (persistentPersoon.getPersoonIndicaties() != null
                && configuratie.isConverteerbaar(PersoonConverterGroep.INDICATIES))
            {
                for (PersistentPersoonIndicatie persistentPersoonIndicatie : persistentPersoon.getPersoonIndicaties()) {
                    PersoonIndicatie indicatie = new PersoonIndicatie();
                    indicatie.setPersoon(persoon);
                    indicatie.setSoort(persistentPersoonIndicatie.getSoort());
                    indicatie.setWaarde(persistentPersoonIndicatie.getWaarde());
                    persoon.voegPersoonIndicatieToe(indicatie);
                }
            }
        }
        return persoon;
    }

    /**
     * Converteert een Operationeel persoon model instantie ({@link PersistentPersoon}) naar een logisch persoon
     * model instantie ({@link Persoon}).
     *
     * @param persistentPersoon de operationeel model instantie van een persoon.
     * @param inclusiefBetrokkenheidEnRelatie Of de betrokkenheid en relaties ook moeten worden gekopieerd.
     *            Pas op met het gebruik van deze parameter en recursie!
     * @param configuratie de configuratie
     * @return de logisch model instantie van de opgegeven persoon (of null als persistentPersoon is null).
     */
    public static Persoon converteerOperationeelNaarLogisch(final PersistentPersoon persistentPersoon,
            final boolean inclusiefBetrokkenheidEnRelatie, final PersoonConverterConfiguratie configuratie)
    {
        return converteerOperationeelNaarLogisch(persistentPersoon,
                inclusiefBetrokkenheidEnRelatie, configuratie, false);
    }

    /**
     * Converteert een Operationeel persoon model instantie ({@link PersistentPersoon}) naar een logisch persoon
     * model instantie ({@link Persoon}).
     *
     * @param persistentPersoon de operationeel model instantie van een persoon.
     * @param inclusiefBetrokkenheidEnRelatie Of de betrokkenheid en relaties ook moeten worden gekopieerd.
     *            Pas op met het gebruik van deze parameter en recursie!
     * @return de logisch model instantie van de opgegeven persoon (of null als persistentPersoon is null).
     */
    public static Persoon converteerOperationeelNaarLogisch(final PersistentPersoon persistentPersoon,
            final boolean inclusiefBetrokkenheidEnRelatie)
    {
        return converteerOperationeelNaarLogisch(persistentPersoon, inclusiefBetrokkenheidEnRelatie,
                new PersoonConverterConfiguratie());
    }

}
