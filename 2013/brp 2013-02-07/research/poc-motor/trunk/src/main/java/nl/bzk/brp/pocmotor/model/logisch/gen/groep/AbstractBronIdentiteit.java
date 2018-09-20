/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Document;


/**
 * Bron.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBronIdentiteit extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "Actie")
   protected BRPActie actie;

   @ManyToOne
   @JoinColumn(name = "Doc")
   protected Document document;


   public BRPActie getActie() {
      return actie;
   }

   public void setActie(final BRPActie actie) {
      this.actie = actie;
   }

   public Document getDocument() {
      return document;
   }

   public void setDocument(final Document document) {
      this.document = document;
   }



}
