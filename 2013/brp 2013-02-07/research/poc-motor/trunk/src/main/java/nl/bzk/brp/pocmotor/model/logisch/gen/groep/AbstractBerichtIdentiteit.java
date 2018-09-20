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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BerichtID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Berichtdata;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Richting;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Bericht;


/**
 * Bericht.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBerichtIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected BerichtID iD;

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


   public BerichtID getID() {
      return iD;
   }

   public void setID(final BerichtID iD) {
      this.iD = iD;
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
