/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_AutorisatiebesluitBijhoudingsautorisatiebesluitID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Toestand;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Autorisatiebesluit;


/**
 * His Autorisatiebesluit Bijhoudingsautorisatiebesluit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_AutorisatiebesluitBijhoudingsautorisatiebesluit extends AbstractFormeleHisTabel {

   @Transient
   protected His_AutorisatiebesluitBijhoudingsautorisatiebesluitID id;

   @ManyToOne
   @JoinColumn(name = "Autorisatiebesluit")
   protected Autorisatiebesluit autorisatiebesluit;

   @Column(name = "Toestand")
   protected Toestand toestand;


   @Id
   @SequenceGenerator(name = "seq_His_AutorisatiebesluitBijhau", sequenceName = "AutAut.seq_His_AutorisatiebesluitBijhau")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_AutorisatiebesluitBijhau")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_AutorisatiebesluitBijhoudingsautorisatiebesluitID();
      }
      id.setWaarde(value);
   }

   public Autorisatiebesluit getAutorisatiebesluit() {
      return autorisatiebesluit;
   }

   public void setAutorisatiebesluit(final Autorisatiebesluit autorisatiebesluit) {
      this.autorisatiebesluit = autorisatiebesluit;
   }

   public Toestand getToestand() {
      return toestand;
   }

   public void setToestand(final Toestand toestand) {
      this.toestand = toestand;
   }



}
