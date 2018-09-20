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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PartijRolID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Rol;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * Partij \ Rol

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPartijRol extends AbstractTabel {

   @Transient
   protected PartijRolID id;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;

   @Column(name = "Rol")
   protected Rol rol;

   @AttributeOverride(name = "waarde", column = @Column(name = "PartijRolStatusHis"))
   protected StatusHistorie partijRolStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PartijRol", sequenceName = "Kern.seq_PartijRol")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PartijRol")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new PartijRolID();
      }
      id.setWaarde(value);
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }

   public Rol getRol() {
      return rol;
   }

   public void setRol(final Rol rol) {
      this.rol = rol;
   }

   public StatusHistorie getPartijRolStatusHis() {
      return partijRolStatusHis;
   }

   public void setPartijRolStatusHis(final StatusHistorie partijRolStatusHis) {
      this.partijRolStatusHis = partijRolStatusHis;
   }



}
