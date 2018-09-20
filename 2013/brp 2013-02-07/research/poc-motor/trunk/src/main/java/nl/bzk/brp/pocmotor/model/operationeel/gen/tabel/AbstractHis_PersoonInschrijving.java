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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonInschrijvingID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Versienummer;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * His Persoon Inschrijving

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonInschrijving extends AbstractFormeleHisTabel {

   @Transient
   protected His_PersoonInschrijvingID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

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


   @Id
   @SequenceGenerator(name = "seq_His_PersInschr", sequenceName = "Kern.seq_His_PersInschr")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersInschr")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonInschrijvingID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

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



}
