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
 * Persoon.EU verkiezingen

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonEUVerkiezingen extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "IndDeelnEUVerkiezingen"))
   protected JaNee indicatieDeelnameEUVerkiezingen;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
   protected Datum datumAanleidingAanpassingDeelnameEUVerkiezing;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslEUKiesr"))
   protected Datum datumEindeUitsluitingEUKiesrecht;


   public JaNee getIndicatieDeelnameEUVerkiezingen() {
      return indicatieDeelnameEUVerkiezingen;
   }

   public void setIndicatieDeelnameEUVerkiezingen(final JaNee indicatieDeelnameEUVerkiezingen) {
      this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
   }

   public Datum getDatumAanleidingAanpassingDeelnameEUVerkiezing() {
      return datumAanleidingAanpassingDeelnameEUVerkiezing;
   }

   public void setDatumAanleidingAanpassingDeelnameEUVerkiezing(final Datum datumAanleidingAanpassingDeelnameEUVerkiezing) {
      this.datumAanleidingAanpassingDeelnameEUVerkiezing = datumAanleidingAanpassingDeelnameEUVerkiezing;
   }

   public Datum getDatumEindeUitsluitingEUKiesrecht() {
      return datumEindeUitsluitingEUKiesrecht;
   }

   public void setDatumEindeUitsluitingEUKiesrecht(final Datum datumEindeUitsluitingEUKiesrecht) {
      this.datumEindeUitsluitingEUKiesrecht = datumEindeUitsluitingEUKiesrecht;
   }



}
