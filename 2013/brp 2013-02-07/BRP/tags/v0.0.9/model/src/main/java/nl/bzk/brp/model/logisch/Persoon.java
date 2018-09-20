/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.Valid;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.logisch.groep.PersoonIdentiteit;


/**
 * Persoon.
 *
 */
public class Persoon implements RootObject {

    private PersoonIdentiteit                              identiteit               = new PersoonIdentiteit();

    @Valid
    private PersoonIdentificatienummers                    identificatienummers;

    @Valid
    private Set<PersoonAdres>                              adressen;

    private final SortedSet<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten =
                                                                                        new TreeSet<PersoonGeslachtsnaamcomponent>();

    private String                                         geslachtsNaam;

    @Valid
    private PersoonGeboorte                                persoonGeboorte;

    private final SortedSet<PersoonVoornaam>               persoonVoornamen         = new TreeSet<PersoonVoornaam>();

    private String                                         voornamen;

    private PersoonGeslachtsAanduiding                     persoonGeslachtsAanduiding;

    private final Map<SoortIndicatie, PersoonIndicatie>          indicaties               =
                                                                                        new TreeMap<SoortIndicatie, PersoonIndicatie>();

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

    public PersoonGeboorte getPersoonGeboorte() {
        return persoonGeboorte;
    }

    public void setPersoonGeboorte(final PersoonGeboorte persoonGeboorte) {
        this.persoonGeboorte = persoonGeboorte;
    }

    /**
     * De stam van de volledige geslachtsnaam.
     * <p>
     * Meestal algoritmisch afgeleid. Het algoritme bestaat eruit dat alle Geslachtsnaamcomponent achter elkaar worden
     * geplaatst, inclusief Voorvoegsels en Scheidingstekens (met uitzondering van die horend bij Volgnummer '1').
     * Eventuele adellijke titels die niet VOOR de gehele Geslachtsnaam worden geplaatst, worden hierbij opgenomen in
     * de
     * Geslachtsnaam, evenals predikaten die niet VOOR de voornamen worden gevoerd.
     * </p>
     * <p>
     * In uitzonderingssituaties is het niet algoritmisch afgeleid.
     * </p>
     *
     * @return de stam van de volledige geslachtsnaam.
     */
    public String getGeslachtsNaam() {
        StringBuilder resultaat = new StringBuilder();
        if (geslachtsNaam != null) {
            resultaat.append(geslachtsNaam);
        } else {
            for (PersoonGeslachtsnaamcomponent component : geslachtsnaamcomponenten) {
                if (component.getVolgnummer() > 1) {
                    resultaat.append(" ");
                }
                if (component.getVoorvoegsel() != null) {
                    resultaat.append(component.getVoorvoegsel()).append(" ");
                }
                resultaat.append(component.getNaam());
            }
        }
        return resultaat.toString();
    }

    public void setGeslachtsNaam(final String geslachtsNaam) {
        this.geslachtsNaam = geslachtsNaam;
    }

    public SortedSet<PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * De samenvoeging van alle exemplaren van Voornaam van een Persoon.
     * <p>
     * Meestal algoritmisch afgeleid. Het algoritme bestaat eruit dat de naamdelen van alle exemplaren van Voornaam van
     * een Persoon achter elkaar worden geplaatst, in de volgorde zoals bepaald door Volgnummer, en gescheiden door een
     * spatie. In uitzonderingsituaties is het niet algoritmisch afgeleid.
     * </p>
     *
     * @return De samenvoeging van alle exemplaren van Voornaam van een Persoon.
     */
    public String getVoornamen() {
        StringBuilder resultaat = new StringBuilder();
        if (voornamen != null) {
            resultaat.append(voornamen);
        } else {
            for (PersoonVoornaam voornaam : persoonVoornamen) {
                if (voornaam.getVolgnummer() > 1) {
                    resultaat.append(" ");
                }
                resultaat.append(voornaam.getNaam());
            }
        }
        return resultaat.toString();
    }

    public SortedSet<PersoonVoornaam> getPersoonVoornamen() {
        return persoonVoornamen;
    }

    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    public PersoonGeslachtsAanduiding getPersoonGeslachtsAanduiding() {
        return persoonGeslachtsAanduiding;
    }

    public void setPersoonGeslachtsAanduiding(final PersoonGeslachtsAanduiding persoonGeslachtsAanduiding) {
        this.persoonGeslachtsAanduiding = persoonGeslachtsAanduiding;
    }

    /**
     * Methode die een nieuw geslachtsnaam component toevoegt aan de huidige persoon op basis van een opgegeven naam en
     * voorvoegsel.
     *
     * @param naam de naam van de nieuwe geslachtsnaam component.
     * @param voorvoegsel het voorvoegsel van het nieuwe geslachtsnaam component.
     */
    public void voegToeGeslachtsnaamcomponent(final String naam, final String voorvoegsel) {
        PersoonGeslachtsnaamcomponent component = new PersoonGeslachtsnaamcomponent();
        component.setNaam(naam);
        component.setVoorvoegsel(voorvoegsel);
        voegToeGeslachtsnaamcomponent(component);
    }

    /**
     * Voegt het opgegeven geslachtsnaam component toe aan de huidige persoon. Indien er reeds een geslachtsnaam
     * component is met hetzelfde volgnummer dan zal er een exceptie worden gegooid.
     *
     * @param geslachtsnaamcomponent het geslachtsnaam component dat dient te worden toegevoegd.
     */
    private void voegToeGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent) {
        geslachtsnaamcomponent.setVolgnummer(geslachtsnaamcomponenten.size() + 1);
        geslachtsnaamcomponent.setPersoon(this);
        if (!geslachtsnaamcomponenten.add(geslachtsnaamcomponent)) {
            throw new IllegalStateException(
                "Een geslachtsnaamcomponent met hetzelfde volgnummer komt al voor bij deze persoon");
        }
    }

    /**
     * Voegt een voornaam toe aan de huidige persoon.
     *
     * @param naam de toe te voegen voornaam.
     */
    public void voegToeVoornaam(final String naam) {
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setNaam(naam);
        voegToeVoornaam(voornaam);
    }

    /**
     * Voegt de opgegeven voornaam toe aan de huidige persoon.
     *
     * @param voornaam de toe te voegen voornaam.
     */
    private void voegToeVoornaam(final PersoonVoornaam voornaam) {
        voornaam.setVolgnummer(persoonVoornamen.size() + 1);
        voornaam.setPersoon(this);
        if (!persoonVoornamen.add(voornaam)) {
            throw new IllegalStateException("Een voornaam met hetzelfde volgnummer komt al voor bij deze persoon");
        }
    }

    public Map<SoortIndicatie, PersoonIndicatie> getIndicaties() {
        return Collections.unmodifiableMap(indicaties);
    }

    /**
     * Zet de verstrekkings beperking naar de opgegeven waarde.
     * @param waarde de waarde voor de verstrekkingsbeperking.
     */
    public void setVerstrekkingsBeperking(final Boolean waarde) {
        setIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, waarde);
    }

    /**
     * Retourneert de waarde van de verstrekkingsbeperking indicatie.
     * @return de waarde van de verstrekkingsbeperking indicatie.
     */
    public Boolean getVerstrekkingsBeperking() {
        return getIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING);
    }

    /**
     * Zet de indicatie van de opgegeven soort naar de opgegeven waarde. Indien er reeds een indicatie van de opgegeven
     * soort aanwezig is, dan zal deze worden aangepast naar de nieuwe waarde.
     *
     * @param soortIndicatie de soort van de indicatie die gezet moet worden.
     * @param waarde de waarde waarnaar de indicatie gezet moet worden.
     */
    private void setIndicatie(final SoortIndicatie soortIndicatie, final Boolean waarde) {
        PersoonIndicatie huidigeIndicatie = indicaties.get(soortIndicatie);
        if (huidigeIndicatie == null) {
            huidigeIndicatie = new PersoonIndicatie();
            huidigeIndicatie.setPersoon(this);
            huidigeIndicatie.setSoort(soortIndicatie);
            voegToePersoonIndicatie(huidigeIndicatie);
            indicaties.put(soortIndicatie, huidigeIndicatie);
        }
        huidigeIndicatie.setWaarde(waarde);
    }

    /**
     * Voegt de opgegeven indicatie toe aan de huidige persoon.
     *
     * @param indicatie de indicatie die toegevoegd dient te worden.
     */
    public void voegToePersoonIndicatie(final PersoonIndicatie indicatie) {
        indicaties.put(indicatie.getSoort(), indicatie);
        indicatie.setPersoon(this);
    }

    /**
     * Verwijdert de opgegeven indicatie bij deze persoon.
     *
     * @param indicatie de indicatie die verwijderd dient te worden.
     */
    public void verwijderPersoonIndicatie(final PersoonIndicatie indicatie) {
        indicaties.remove(indicatie.getSoort());
        indicatie.setPersoon(null);
    }

    /**
     * Retourneert de waarde van de indicatie op deze persoon van de opgegeven soort. Indien de gezochte indicatie voor
     * deze persoon niet bestaat, zal <code>null</code> worden geretourneerd.
     *
     * @param soortIndicatie de soort indicatie die geretourneerd dient te worden.
     * @return de waarde van de indicatie op deze persoon waarom verzocht is.
     */
    private Boolean getIndicatie(final SoortIndicatie soortIndicatie) {
        Boolean resultaat;

        PersoonIndicatie indicatie = indicaties.get(soortIndicatie);
        if (indicatie != null) {
            resultaat = indicatie.getWaarde();
        } else {
            resultaat = null;
        }
        return resultaat;
    }
}
