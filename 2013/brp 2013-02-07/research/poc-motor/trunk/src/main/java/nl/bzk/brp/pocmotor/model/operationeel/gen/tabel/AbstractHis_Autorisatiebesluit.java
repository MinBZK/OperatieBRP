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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_AutorisatiebesluitID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Autorisatiebesluit;


/**
 * His Autorisatiebesluit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Autorisatiebesluit extends AbstractFormeleHisTabel {

   @Transient
   protected His_AutorisatiebesluitID id;

   @ManyToOne
   @JoinColumn(name = "Autorisatiebesluit")
   protected Autorisatiebesluit autorisatiebesluit;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndIngetrokken"))
   protected JaNee indicatieIngetrokken;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatBesluit"))
   protected Datum datumBesluit;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
   protected Datum datumIngang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;


   @Id
   @SequenceGenerator(name = "seq_His_Autorisatiebesluit", sequenceName = "AutAut.seq_His_Autorisatiebesluit")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Autorisatiebesluit")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_AutorisatiebesluitID();
      }
      id.setWaarde(value);
   }

   public Autorisatiebesluit getAutorisatiebesluit() {
      return autorisatiebesluit;
   }

   public void setAutorisatiebesluit(final Autorisatiebesluit autorisatiebesluit) {
      this.autorisatiebesluit = autorisatiebesluit;
   }

   public JaNee getIndicatieIngetrokken() {
      return indicatieIngetrokken;
   }

   public void setIndicatieIngetrokken(final JaNee indicatieIngetrokken) {
      this.indicatieIngetrokken = indicatieIngetrokken;
   }

   public Datum getDatumBesluit() {
      return datumBesluit;
   }

   public void setDatumBesluit(final Datum datumBesluit) {
      this.datumBesluit = datumBesluit;
   }

   public Datum getDatumIngang() {
      return datumIngang;
   }

   public void setDatumIngang(final Datum datumIngang) {
      this.datumIngang = datumIngang;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }



}
