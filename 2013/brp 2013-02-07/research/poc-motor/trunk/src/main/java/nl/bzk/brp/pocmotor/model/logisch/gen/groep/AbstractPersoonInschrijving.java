/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ANummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Versienummer;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;


/**
 * Persoon.Inschrijving

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonInschrijving extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInschr"))
   protected Datum datumInschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "Versienr"))
   protected Versienummer versienummer;

   @ManyToOne
   @JoinColumn(name = "VorigePers")
   protected Persoon vorigePersoon;

   @ManyToOne
   @JoinColumn(name = "VolgendePers")
   protected Persoon volgendePersoon;

   //@AttributeOverride(name = "waarde", column = @Column(name = "VorigeBSN"))
   @Transient
   protected Burgerservicenummer vorigeBSN;

   //@AttributeOverride(name = "waarde", column = @Column(name = "VolgendeBSN"))
   @Transient
   protected Burgerservicenummer volgendeBSN;

   //@AttributeOverride(name = "waarde", column = @Column(name = "VorigeANr"))
   @Transient
   protected ANummer vorigeANummer;

   //@AttributeOverride(name = "waarde", column = @Column(name = "VolgendeANr"))
   @Transient
   protected ANummer volgendeANummer;


   public Datum getDatumInschrijving() {
      return datumInschrijving;
   }

   public void setDatumInschrijving(final Datum datumInschrijving) {
      this.datumInschrijving = datumInschrijving;
   }

   public Versienummer getVersienummer() {
      return versienummer;
   }

   public void setVersienummer(final Versienummer versienummer) {
      this.versienummer = versienummer;
   }

   public Persoon getVorigePersoon() {
      return vorigePersoon;
   }

   public void setVorigePersoon(final Persoon vorigePersoon) {
      this.vorigePersoon = vorigePersoon;
   }

   public Persoon getVolgendePersoon() {
      return volgendePersoon;
   }

   public void setVolgendePersoon(final Persoon volgendePersoon) {
      this.volgendePersoon = volgendePersoon;
   }

   public Burgerservicenummer getVorigeBSN() {
      return vorigeBSN;
   }

   public void setVorigeBSN(final Burgerservicenummer vorigeBSN) {
      this.vorigeBSN = vorigeBSN;
   }

   public Burgerservicenummer getVolgendeBSN() {
      return volgendeBSN;
   }

   public void setVolgendeBSN(final Burgerservicenummer volgendeBSN) {
      this.volgendeBSN = volgendeBSN;
   }

   public ANummer getVorigeANummer() {
      return vorigeANummer;
   }

   public void setVorigeANummer(final ANummer vorigeANummer) {
      this.vorigeANummer = vorigeANummer;
   }

   public ANummer getVolgendeANummer() {
      return volgendeANummer;
   }

   public void setVolgendeANummer(final ANummer volgendeANummer) {
      this.volgendeANummer = volgendeANummer;
   }



}
