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
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;


/**
 * Autorisatiebesluit.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAutorisatiebesluitStandaard extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "IndIngetrokken"))
   protected JaNee indicatieIngetrokken;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatBesluit"))
   protected Datum datumBesluit;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
   protected Datum datumIngang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;


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
