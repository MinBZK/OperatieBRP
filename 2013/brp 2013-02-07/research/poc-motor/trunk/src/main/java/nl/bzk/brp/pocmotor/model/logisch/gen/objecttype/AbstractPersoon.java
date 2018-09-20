/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.objecttype;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractDynamischObjectType;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAanschrijving;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAfgeleidAdministratief;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonBijhoudingsgemeente;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonBijhoudingsverantwoordelijkheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonGeboorte;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonGeslachtsaanduiding;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonIdentificatienummers;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonImmigratie;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonInschrijving;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonOverlijden;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonPersoonskaart;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonVerblijfsrecht;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonVoornaam;


/**
 * Persoon

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoon extends AbstractDynamischObjectType {

   // Groepen
   protected PersoonIdentiteit identiteit = new PersoonIdentiteit();

   protected PersoonIdentificatienummers identificatienummers;

   protected PersoonGeslachtsaanduiding geslachtsaanduiding;

   protected PersoonSamengesteldeNaam samengesteldeNaam;

   protected PersoonAanschrijving aanschrijving;

   protected PersoonGeboorte geboorte;

   protected PersoonOverlijden overlijden;

   protected PersoonVerblijfsrecht verblijfsrecht;

   //protected PersoonGezagDerde gezagDerde;

   //protected PersoonCuratele curatele;

   //protected PersoonUitsluitingNLKiesrecht uitsluitingNLKiesrecht;

   //protected PersoonEUVerkiezingen eUVerkiezingen;

   protected PersoonBijhoudingsverantwoordelijkheid bijhoudingsverantwoordelijkheid;

   //protected PersoonOpschorting opschorting;

   //protected PersoonStatenloos statenloos;

   protected PersoonBijhoudingsgemeente bijhoudingsgemeente;

   protected PersoonPersoonskaart persoonskaart;

   protected PersoonImmigratie immigratie;

   //protected PersoonVerstrekkingsbeperking verstrekkingsbeperking;

   //protected PersoonGeprivilegieerde geprivilegieerde;

   //protected PersoonVastgesteldNietNederlander vastgesteldNietNederlander;

   //protected PersoonBehandeldAlsNederlander behandeldAlsNederlander;

   //protected PersoonBelemmeringVerstrekkingReisdocument belemmeringVerstrekkingReisdocument;

   //protected PersoonBezitBuitenlandsReisdocument bezitBuitenlandsReisdocument;

   protected PersoonInschrijving inschrijving;

   protected PersoonAfgeleidAdministratief afgeleidAdministratief;



   // Sets
   @OneToMany
   @JoinColumn(name = "Pers")
   protected Set<PersoonAdres> adressen;

   @OneToMany
   @JoinColumn(name = "Pers")
   protected Set<PersoonVoornaam> voornamen;

   @OneToMany
   @JoinColumn(name = "Pers")
   protected Set<PersoonNationaliteit> nationaliteiten;

   @OneToMany
   @JoinColumn(name = "Pers")
   protected Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten;

   @OneToMany
   @JoinColumn(name = "Betrokkene")
   protected Set<Betrokkenheid> betrokkenheden;



   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Pers", sequenceName = "Kern.seq_Pers")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Pers")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new PersoonIdentiteit();
        }
        identiteit.setID(new PersoonID());
        identiteit.getID().setWaarde(id);
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

   public PersoonGeslachtsaanduiding getGeslachtsaanduiding() {
      return geslachtsaanduiding;
   }

   public void setGeslachtsaanduiding(final PersoonGeslachtsaanduiding geslachtsaanduiding) {
      this.geslachtsaanduiding = geslachtsaanduiding;
   }

   public PersoonSamengesteldeNaam getSamengesteldeNaam() {
      return samengesteldeNaam;
   }

   public void setSamengesteldeNaam(final PersoonSamengesteldeNaam samengesteldeNaam) {
      this.samengesteldeNaam = samengesteldeNaam;
   }

   public PersoonAanschrijving getAanschrijving() {
      return aanschrijving;
   }

   public void setAanschrijving(final PersoonAanschrijving aanschrijving) {
      this.aanschrijving = aanschrijving;
   }

   public PersoonGeboorte getGeboorte() {
      return geboorte;
   }

   public void setGeboorte(final PersoonGeboorte geboorte) {
      this.geboorte = geboorte;
   }

   public PersoonOverlijden getOverlijden() {
      return overlijden;
   }

   public void setOverlijden(final PersoonOverlijden overlijden) {
      this.overlijden = overlijden;
   }

   public PersoonVerblijfsrecht getVerblijfsrecht() {
      return verblijfsrecht;
   }

   public void setVerblijfsrecht(final PersoonVerblijfsrecht verblijfsrecht) {
      this.verblijfsrecht = verblijfsrecht;
   }
/*
   public PersoonGezagDerde getGezagDerde() {
      return gezagDerde;
   }

   public void setGezagDerde(final PersoonGezagDerde gezagDerde) {
      this.gezagDerde = gezagDerde;
   }

   public PersoonCuratele getCuratele() {
      return curatele;
   }

   public void setCuratele(final PersoonCuratele curatele) {
      this.curatele = curatele;
   }

   public PersoonUitsluitingNLKiesrecht getUitsluitingNLKiesrecht() {
      return uitsluitingNLKiesrecht;
   }

   public void setUitsluitingNLKiesrecht(final PersoonUitsluitingNLKiesrecht uitsluitingNLKiesrecht) {
      this.uitsluitingNLKiesrecht = uitsluitingNLKiesrecht;
   }

   public PersoonEUVerkiezingen getEUVerkiezingen() {
      return eUVerkiezingen;
   }

   public void setEUVerkiezingen(final PersoonEUVerkiezingen eUVerkiezingen) {
      this.eUVerkiezingen = eUVerkiezingen;
   }*/

   public PersoonBijhoudingsverantwoordelijkheid getBijhoudingsverantwoordelijkheid() {
      return bijhoudingsverantwoordelijkheid;
   }

   public void setBijhoudingsverantwoordelijkheid(final PersoonBijhoudingsverantwoordelijkheid bijhoudingsverantwoordelijkheid) {
      this.bijhoudingsverantwoordelijkheid = bijhoudingsverantwoordelijkheid;
   }
/*
   public PersoonOpschorting getOpschorting() {
      return opschorting;
   }

   public void setOpschorting(final PersoonOpschorting opschorting) {
      this.opschorting = opschorting;
   }

   public PersoonStatenloos getStatenloos() {
      return statenloos;
   }

   public void setStatenloos(final PersoonStatenloos statenloos) {
      this.statenloos = statenloos;
   }*/

   public PersoonBijhoudingsgemeente getBijhoudingsgemeente() {
      return bijhoudingsgemeente;
   }

   public void setBijhoudingsgemeente(final PersoonBijhoudingsgemeente bijhoudingsgemeente) {
      this.bijhoudingsgemeente = bijhoudingsgemeente;
   }

   public PersoonPersoonskaart getPersoonskaart() {
      return persoonskaart;
   }

   public void setPersoonskaart(final PersoonPersoonskaart persoonskaart) {
      this.persoonskaart = persoonskaart;
   }

   public PersoonImmigratie getImmigratie() {
      return immigratie;
   }

   public void setImmigratie(final PersoonImmigratie immigratie) {
      this.immigratie = immigratie;
   }
/*
   public PersoonVerstrekkingsbeperking getVerstrekkingsbeperking() {
      return verstrekkingsbeperking;
   }

   public void setVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking verstrekkingsbeperking) {
      this.verstrekkingsbeperking = verstrekkingsbeperking;
   }

   public PersoonGeprivilegieerde getGeprivilegieerde() {
      return geprivilegieerde;
   }

   public void setGeprivilegieerde(final PersoonGeprivilegieerde geprivilegieerde) {
      this.geprivilegieerde = geprivilegieerde;
   }

   public PersoonVastgesteldNietNederlander getVastgesteldNietNederlander() {
      return vastgesteldNietNederlander;
   }

   public void setVastgesteldNietNederlander(final PersoonVastgesteldNietNederlander vastgesteldNietNederlander) {
      this.vastgesteldNietNederlander = vastgesteldNietNederlander;
   }

   /*public PersoonBehandeldAlsNederlander getBehandeldAlsNederlander() {
      return behandeldAlsNederlander;
   }

   public void setBehandeldAlsNederlander(final PersoonBehandeldAlsNederlander behandeldAlsNederlander) {
      this.behandeldAlsNederlander = behandeldAlsNederlander;
   }

   /*public PersoonBelemmeringVerstrekkingReisdocument getBelemmeringVerstrekkingReisdocument() {
      return belemmeringVerstrekkingReisdocument;
   }

   public void setBelemmeringVerstrekkingReisdocument(final PersoonBelemmeringVerstrekkingReisdocument belemmeringVerstrekkingReisdocument) {
      this.belemmeringVerstrekkingReisdocument = belemmeringVerstrekkingReisdocument;
   }

   public PersoonBezitBuitenlandsReisdocument getBezitBuitenlandsReisdocument() {
      return bezitBuitenlandsReisdocument;
   }

   public void setBezitBuitenlandsReisdocument(final PersoonBezitBuitenlandsReisdocument bezitBuitenlandsReisdocument) {
      this.bezitBuitenlandsReisdocument = bezitBuitenlandsReisdocument;
   }*/

   public PersoonInschrijving getInschrijving() {
      return inschrijving;
   }

   public void setInschrijving(final PersoonInschrijving inschrijving) {
      this.inschrijving = inschrijving;
   }

   public PersoonAfgeleidAdministratief getAfgeleidAdministratief() {
      return afgeleidAdministratief;
   }

   public void setAfgeleidAdministratief(final PersoonAfgeleidAdministratief afgeleidAdministratief) {
      this.afgeleidAdministratief = afgeleidAdministratief;
   }



   // Getters/Setters Sets
   public Set<PersoonAdres> getAdressen() {
      return adressen;
   }

   public void setAdressen(final Set<PersoonAdres> adressen) {
      this.adressen = adressen;
   }

   public Set<PersoonVoornaam> getVoornamen() {
      return voornamen;
   }

   public void setVoornamen(final Set<PersoonVoornaam> voornamen) {
      this.voornamen = voornamen;
   }

   public Set<PersoonNationaliteit> getNationaliteiten() {
      return nationaliteiten;
   }

   public void setNationaliteiten(final Set<PersoonNationaliteit> nationaliteiten) {
      this.nationaliteiten = nationaliteiten;
   }

   public Set<PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
      return geslachtsnaamcomponenten;
   }

   public void setGeslachtsnaamcomponenten(final Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten) {
      this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
   }

   public Set<Betrokkenheid> getBetrokkenheden() {
      return betrokkenheden;
   }

   public void setBetrokkenheden(final Set<Betrokkenheid> betrokkenheden) {
      this.betrokkenheden = betrokkenheden;
   }



}
