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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BerichtID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Berichtdata;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Richting;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Bericht;


/**
 * Bericht

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBericht extends AbstractTabel {

   @Transient
   protected BerichtID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Data"))
   protected Berichtdata data;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsOntv"))
   protected DatumTijd datumTijdOntvangst;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsVerzenden"))
   protected DatumTijd datumTijdVerzenden;

   @ManyToOne
   @JoinColumn(name = "AntwoordOp")
   protected Bericht antwoordOp;

   @Column(name = "Richting")
   protected Richting richting;


   @Id
   @SequenceGenerator(name = "seq_Ber", sequenceName = "Ber.seq_Ber")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Ber")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new BerichtID();
      }
      id.setWaarde(value);
   }

   public Berichtdata getData() {
      return data;
   }

   public void setData(final Berichtdata data) {
      this.data = data;
   }

   public DatumTijd getDatumTijdOntvangst() {
      return datumTijdOntvangst;
   }

   public void setDatumTijdOntvangst(final DatumTijd datumTijdOntvangst) {
      this.datumTijdOntvangst = datumTijdOntvangst;
   }

   public DatumTijd getDatumTijdVerzenden() {
      return datumTijdVerzenden;
   }

   public void setDatumTijdVerzenden(final DatumTijd datumTijdVerzenden) {
      this.datumTijdVerzenden = datumTijdVerzenden;
   }

   public Bericht getAntwoordOp() {
      return antwoordOp;
   }

   public void setAntwoordOp(final Bericht antwoordOp) {
      this.antwoordOp = antwoordOp;
   }

   public Richting getRichting() {
      return richting;
   }

   public void setRichting(final Richting richting) {
      this.richting = richting;
   }



}
