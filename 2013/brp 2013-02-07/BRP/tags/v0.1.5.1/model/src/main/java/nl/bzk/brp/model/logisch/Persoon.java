/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.logisch.groep.PersoonAfgeleidAdministratief;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsGemeente;
import nl.bzk.brp.model.logisch.groep.PersoonBijhoudingsVerantwoordelijke;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;
import nl.bzk.brp.model.logisch.groep.PersoonInschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.groep.PersoonOverlijden;
import nl.bzk.brp.model.logisch.groep.PersoonRedenOpschorting;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeAanschrijving;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.CollectieMetUniekeWaarden;


/** Persoon. */
public class Persoon implements RootObject {

    private PersoonIdentiteit                         identiteit               = new PersoonIdentiteit();

    @Valid
    private PersoonIdentificatienummers               identificatienummers;

    @Valid
    private Set<PersoonAdres>                         adressen;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC002)
    private final List<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten =
                                                                                   new ArrayList<PersoonGeslachtsnaamcomponent>();
    @Valid
    private PersoonGeboorte                           geboorte;

    // @Valid
    private PersoonSamengesteldeNaam                  samengesteldenaam;

    // @Valid
    private PersoonSamengesteldeAanschrijving         samengesteldeAanschrijving;
    // @Valid
    private PersoonOverlijden                         overlijden;

    // @Valid
    private PersoonBijhoudingsVerantwoordelijke       bijhoudingVerantwoordelijke;

    // @Valid
    private PersoonBijhoudingsGemeente                bijhoudingGemeente;

    @Valid
    private PersoonAfgeleidAdministratief             afgeleidAdministratief;

    // @Valid
    private PersoonRedenOpschorting                   redenOpschorting;

    @CollectieMetUniekeWaarden(code = MeldingCode.INC001)
    private final List<PersoonVoornaam>               persoonVoornamen         = new ArrayList<PersoonVoornaam>();

    private PersoonGeslachtsAanduiding                persoonGeslachtsAanduiding;

    private final SortedSet<PersoonIndicatie>         indicaties               = new TreeSet<PersoonIndicatie>();

    @CollectieMetUniekeWaarden(code = MeldingCode.INC003)
    private final List<PersoonNationaliteit>          nationaliteiten          = new ArrayList<PersoonNationaliteit>();

    private List<Betrokkenheid>                       betrokkenheden           = new ArrayList<Betrokkenheid>();

    private PersoonInschrijving                       inschrijving;

    /**
     * Geeft de id terug.
     *
     * @return null wanneer de persoon geen PersoonIdentiteit heeft
     */
    public Long getId() {
        if (identiteit != null) {
            return identiteit.getId();
        }
        return null;
    }

    /**
     * Maak een PersoonIdentiteit en zet de id.
     *
     * @param id PersoonIdentiteit id
     */
    public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new PersoonIdentiteit();
        }
        identiteit.setId(id);
    }

    public PersoonIdentiteit getIdentiteit() {
        return identiteit;
    }

    public void setIdentiteit(final PersoonIdentiteit identiteit) {
        this.identiteit = identiteit;
    }

    public PersoonIdentificatienummers getIdentificatienummers() {
        return identificatienummers;
    }

    public void setIdentificatienummers(final PersoonIdentificatienummers identificatienummers) {
        this.identificatienummers = identificatienummers;
    }

    public Set<PersoonAdres> getAdressen() {
        return adressen;
    }

    public void setAdressen(final Set<PersoonAdres> adressen) {
        this.adressen = adressen;
    }

    public PersoonGeboorte getGeboorte() {
        return geboorte;
    }

    public void setGeboorte(final PersoonGeboorte persoonGeboorte) {
        geboorte = persoonGeboorte;
    }

    public final PersoonOverlijden getOverlijden() {
        return overlijden;
    }

    public void setOverlijden(final PersoonOverlijden persoonOverlijden) {
        overlijden = persoonOverlijden;
    }

    public List<PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * Voeg toe een geslachtsnaam component van een persoon aan de lijst.
     *
     * @param component het persoonGeslachtsnaamcomponent
     */
    public void voegGeslachtsnaamcomponentToe(final PersoonGeslachtsnaamcomponent component) {
        if (component != null && !geslachtsnaamcomponenten.contains(component)) {
            geslachtsnaamcomponenten.add(component);
        }
    }

    public List<PersoonVoornaam> getPersoonVoornamen() {
        return persoonVoornamen;
    }

    /**
     * Voeg toe een voornaam (object) aan een persoon.
     *
     * @param persoonVoornaam het object
     */
    public void voegPersoonVoornaamToe(final PersoonVoornaam persoonVoornaam) {
        // moet add() gebruiken omdat de list is final
        if ((persoonVoornaam != null) && !persoonVoornamen.contains(persoonVoornaam)) {
            persoonVoornamen.add(persoonVoornaam);
        }
    }

    public PersoonGeslachtsAanduiding getPersoonGeslachtsAanduiding() {
        return persoonGeslachtsAanduiding;
    }

    public void setPersoonGeslachtsAanduiding(final PersoonGeslachtsAanduiding persoonGeslachtsAanduiding) {
        this.persoonGeslachtsAanduiding = persoonGeslachtsAanduiding;
    }

    /**
     * Retourneert een (niet aanpasbare, gesorteerde) set van de indicaties op de persoon.
     *
     * @return set van de indicaties op de persoon.
     */
    public SortedSet<PersoonIndicatie> getIndicaties() {
        return Collections.unmodifiableSortedSet(indicaties);
    }

    /**
     * Voegt de opgegeven indicatie toe aan de huidige persoon.
     *
     * @param indicatie de indicatie die toegevoegd dient te worden.
     */
    public void voegPersoonIndicatieToe(final PersoonIndicatie indicatie) {
        indicaties.add(indicatie);
    }

    /**
     * Voegt alle opgegeven persoon indicaties toe aan de lijst van indicaties.
     *
     * @param persoonIndicaties de lijst van persoon indicaties die toegevoegd dienen te worden.
     */
    public void voegPersoonIndicatiesToe(final SortedSet<PersoonIndicatie> persoonIndicaties) {
        indicaties.addAll(persoonIndicaties);
    }

    /**
     * Voeg een persoonIndicatie toe.
     *
     * @param persoonIndicatie de persoonIndicatie
     */
    public void voegPersoonIndicatiesToe(final PersoonIndicatie persoonIndicatie) {
        indicaties.add(persoonIndicatie);
    }

    /**
     * Retourneert de waarde van de indicatie op deze persoon van de opgegeven soort. Indien de gezochte indicatie voor
     * deze persoon niet bestaat, zal <code>null</code> worden geretourneerd.
     *
     * @param soortIndicatie de soort indicatie die geretourneerd dient te worden.
     * @return de waarde van de indicatie op deze persoon waarom verzocht is.
     */
    public Boolean getIndicatieWaarde(final SoortIndicatie soortIndicatie) {
        Boolean resultaat = null;

        for (PersoonIndicatie indicatie : indicaties) {
            if (indicatie.getSoort() == soortIndicatie) {
                resultaat = indicatie.getWaarde();
                break;
            }
        }

        return resultaat;
    }

    /**
     * Geeft de waarde terug van de Indicatie VerstrekkingsBeperking.
     *
     * @return waarde van de verstrekkingsbeperking
     */
    public PersoonIndicatie getIndicatiesVerstrekkingsbeperking() {
        PersoonIndicatie resultaat = null;

        for (PersoonIndicatie indicatie : indicaties) {
            if (indicatie.getSoort() == SoortIndicatie.VERSTREKKINGSBEPERKING) {
                resultaat = indicatie;
                break;
            }
        }

        return resultaat;
    }

    /**
     * Geeft aan of er indicaties zijn of niet.
     *
     * @return boolean die aangeeft of er indicaties zijn of niet.
     */
    public boolean indicatiesAanwezig() {
        return !indicaties.isEmpty();
    }

    /**
     * Geeft aan of de VERSTREKKINGSBEPERKING indicatie aanwezig is.
     *
     * @return true als de indicatie aanwezig is in de lijst van indicaties
     */
    public boolean indicatiesVerstrekkingsbeperkingAanwezig() {
        boolean resultaat = false;
        for (PersoonIndicatie ind : indicaties) {
            if (ind.getSoort() == SoortIndicatie.VERSTREKKINGSBEPERKING) {
                resultaat = true;
                break;
            }
        }

        return resultaat;
    }

    public List<PersoonNationaliteit> getNationaliteiten() {
        return nationaliteiten;
    }

    /** @return the bijhoudingVerantwoordelijke */
    public PersoonBijhoudingsVerantwoordelijke getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    /** @param bijhoudingVerantwoordelijke the bijhoudingVerantwoordelijke to set */
    public void setBijhoudingVerantwoordelijke(final PersoonBijhoudingsVerantwoordelijke bijhoudingVerantwoordelijke) {
        this.bijhoudingVerantwoordelijke = bijhoudingVerantwoordelijke;
    }

    /** @return the bijhoudingGemeente */
    public PersoonBijhoudingsGemeente getBijhoudingGemeente() {
        return bijhoudingGemeente;
    }

    /** @param bijhoudingGemeente the bijhoudingGemeente to set */
    public void setBijhoudingGemeente(final PersoonBijhoudingsGemeente bijhoudingGemeente) {
        this.bijhoudingGemeente = bijhoudingGemeente;
    }

    /** @return the afgeleidAdministratief */
    public PersoonAfgeleidAdministratief getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    /** @param afgeleidAdministratief the afgeleidAdministratief to set */
    public void setAfgeleidAdministratief(final PersoonAfgeleidAdministratief afgeleidAdministratief) {
        this.afgeleidAdministratief = afgeleidAdministratief;
    }

    /** @return the redenOpschorting */
    public PersoonRedenOpschorting getRedenOpschorting() {
        return redenOpschorting;
    }

    /** @param redenOpschorting the redenOpschorting to set */
    public void setRedenOpschorting(final PersoonRedenOpschorting redenOpschorting) {
        this.redenOpschorting = redenOpschorting;
    }

    /** @return the samengesteldenaam */
    public PersoonSamengesteldeNaam getSamengesteldenaam() {
        return samengesteldenaam;
    }

    /** @param samengesteldenaam the samengesteldenaam to set */
    public void setSamengesteldenaam(final PersoonSamengesteldeNaam samengesteldenaam) {
        this.samengesteldenaam = samengesteldenaam;
    }

    /** @return the samengesteldeAanschrijving */
    public PersoonSamengesteldeAanschrijving getSamengesteldeAanschrijving() {
        return samengesteldeAanschrijving;
    }

    /** @param samengesteldeAanschrijving the samengesteldeAanschrijving to set */
    public void setSamengesteldeAanschrijving(final PersoonSamengesteldeAanschrijving samengesteldeAanschrijving) {
        this.samengesteldeAanschrijving = samengesteldeAanschrijving;
    }

    /** @return the inschrijving */
    public PersoonInschrijving getInschrijving() {
        return inschrijving;
    }

    /** @param inschrijving the inschrijving to set */
    public void setInschrijving(final PersoonInschrijving inschrijving) {
        this.inschrijving = inschrijving;
    }

    public List<Betrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Haalt de kind betrokkenheid uit de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     *
     * @return Kind betrokkenheid.
     */
    public Betrokkenheid getKindBetrokkenHeid() {
        for (Betrokkenheid betrokkenheid : betrokkenheden) {
            if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.KIND) {
                return betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Retourneert alle ouder betrokkenhden uit de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     *
     * @return Ouder betrokkenheden.
     */
    public List<Betrokkenheid> getOuderBetrokkenheden() {
        List<Betrokkenheid> ouderBetrokkenheden = new ArrayList<Betrokkenheid>();
        for (Betrokkenheid betrokkenheid : betrokkenheden) {
            if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.OUDER) {
                ouderBetrokkenheden.add(betrokkenheid);
            }
        }
        return ouderBetrokkenheden;
    }

    /**
     * Voegt een kind betrokkenheid toe aan de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     *
     * @param betr De toe te voegen betrokkenheid.
     */
    public void voegKindBetrokkenHeidToe(final Betrokkenheid betr) {
        if (betrokkenheden == null) {
            betrokkenheden = new ArrayList<Betrokkenheid>();
        }
        betrokkenheden.add(betr);
    }

    /**
     * Voegt ouder betrokkenheden toe aan de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     *
     * @param betr De toe te voegen betrokkenheden.
     */
    public void voegOuderBetrokkenhedenToe(final List<Betrokkenheid> betr) {
        if (betrokkenheden == null) {
            betrokkenheden = new ArrayList<Betrokkenheid>();
        }
        betrokkenheden.addAll(betr);
    }

    /**
     * Retourneert alle partner betrokkenhden uit de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     *
     * @return Partner betrokkenheden.
     */
    public List<Betrokkenheid> getPartnerBetrokkenHeden() {
        List<Betrokkenheid> partnerBetrokkenheden = new ArrayList<Betrokkenheid>();
        for (Betrokkenheid betrokkenheid : betrokkenheden) {
            if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.PARTNER) {
                partnerBetrokkenheden.add(betrokkenheid);
            }
        }
        return partnerBetrokkenheden;
    }

    /**
     * Voegt partner betrokkenheden toe aan de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     *
     * @param betr De toe te voegen betrokkenheden.
     */
    public void voegPartnerBetrokkenHedenToe(final List<Betrokkenheid> betr) {
        if (betrokkenheden == null) {
            betrokkenheden = new ArrayList<Betrokkenheid>();
        }
        betrokkenheden.addAll(betr);
    }

    // ToDo: Onderstaande vier test methodes zouden niet in het logisch model moeten zitten, maar ergens anders
    /**
     * Methode die aangeeft of de persoon over voornamen beschikt of niet. Wordt gebruikt door JiBX voor het al dan
     * niet tonen van het betreffende wrapper element.
     * @return of er voornamen zijn of niet.
     */
    public boolean heeftVoornamen() {
        return !(persoonVoornamen == null || persoonVoornamen.isEmpty());
    }

    /**
     * Methode die aangeeft of de persoon over voornamen beschikt of niet. Wordt gebruikt door JiBX voor het al dan
     * niet tonen van het betreffende wrapper element.
     * @return of er voornamen zijn of niet.
     */
    public boolean heeftNationaliteiten() {
        return !(nationaliteiten == null || nationaliteiten.isEmpty());
    }

    /**
     * Methode die aangeeft of de persoon over voornamen beschikt of niet. Wordt gebruikt door JiBX voor het al dan
     * niet tonen van het betreffende wrapper element.
     * @return of er voornamen zijn of niet.
     */
    public boolean heeftGeslachtsnaamComponenten() {
        return !(geslachtsnaamcomponenten == null || geslachtsnaamcomponenten.isEmpty());
    }

    /**
     * Methode die aangeeft of de persoon over betrokkenheden beschikt of niet. Wordt gebruikt door JiBX voor het al dan
     * niet tonen van het betreffende wrapper element.
     * @return of er betrokkenheden zijn of niet.
     */
    public boolean heeftBetrokkenheden() {
        return !(betrokkenheden == null || betrokkenheden.isEmpty());
    }
}
