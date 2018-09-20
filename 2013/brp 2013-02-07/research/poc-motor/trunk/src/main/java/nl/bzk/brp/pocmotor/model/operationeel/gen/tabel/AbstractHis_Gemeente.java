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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Gemeentecode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_GemeenteID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * His Gemeente

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Gemeente extends AbstractFormeleHisTabel {

   @Transient
   protected His_GemeenteID id;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;

   @ManyToOne
   @JoinColumn(name = "VoortzettendeGem")
   protected Partij voortzettendeGemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "Gemcode"))
   protected Gemeentecode gemeentecode;

   @ManyToOne
   @JoinColumn(name = "OnderdeelVan")
   protected Partij onderdeelVan;


   @Id
   @SequenceGenerator(name = "seq_His_Gem", sequenceName = "Kern.seq_His_Gem")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Gem")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_GemeenteID();
      }
      id.setWaarde(value);
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }

   public Partij getVoortzettendeGemeente() {
      return voortzettendeGemeente;
   }

   public void setVoortzettendeGemeente(final Partij voortzettendeGemeente) {
      this.voortzettendeGemeente = voortzettendeGemeente;
   }

   public Gemeentecode getGemeentecode() {
      return gemeentecode;
   }

   public void setGemeentecode(final Gemeentecode gemeentecode) {
      this.gemeentecode = gemeentecode;
   }

   public Partij getOnderdeelVan() {
      return onderdeelVan;
   }

   public void setOnderdeelVan(final Partij onderdeelVan) {
      this.onderdeelVan = onderdeelVan;
   }



}
