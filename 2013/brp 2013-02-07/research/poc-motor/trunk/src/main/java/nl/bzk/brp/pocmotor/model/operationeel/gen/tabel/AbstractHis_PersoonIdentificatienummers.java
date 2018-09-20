/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ANummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonIdentificatienummersID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * His Persoon Identificatienummers

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonIdentificatienummers extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonIdentificatienummersID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "BSN"))
   protected Burgerservicenummer burgerservicenummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "ANr"))
   protected ANummer administratienummer;


   @Id
   @SequenceGenerator(name = "seq_His_PersIDs", sequenceName = "Kern.seq_His_PersIDs")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersIDs")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonIdentificatienummersID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Burgerservicenummer getBurgerservicenummer() {
      return burgerservicenummer;
   }

   public void setBurgerservicenummer(final Burgerservicenummer burgerservicenummer) {
      this.burgerservicenummer = burgerservicenummer;
   }

   public ANummer getAdministratienummer() {
      return administratienummer;
   }

   public void setAdministratienummer(final ANummer administratienummer) {
      this.administratienummer = administratienummer;
   }



}
