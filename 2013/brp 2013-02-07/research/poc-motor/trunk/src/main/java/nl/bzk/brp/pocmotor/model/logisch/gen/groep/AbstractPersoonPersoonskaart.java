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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Gemeente;


/**
 * Persoon.Persoonskaart

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonPersoonskaart extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "GemPK")
   protected Gemeente gemeentePersoonskaart;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndPKVolledigGeconv"))
   protected JaNee indicatiePersoonskaartVolledigGeconverteerd;


   public Gemeente getGemeentePersoonskaart() {
      return gemeentePersoonskaart;
   }

   public void setGemeentePersoonskaart(final Gemeente gemeentePersoonskaart) {
      this.gemeentePersoonskaart = gemeentePersoonskaart;
   }

   public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
      return indicatiePersoonskaartVolledigGeconverteerd;
   }

   public void setIndicatiePersoonskaartVolledigGeconverteerd(final JaNee indicatiePersoonskaartVolledigGeconverteerd) {
      this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
   }



}
