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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonEUVerkiezingenID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * His Persoon EU verkiezingen

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonEUVerkiezingen extends AbstractFormeleHisTabel {

   @Transient
   protected His_PersoonEUVerkiezingenID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndDeelnEUVerkiezingen"))
   protected JaNee indicatieDeelnameEUVerkiezingen;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
   protected Datum datumAanleidingAanpassingDeelnameEUVerkiezing;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslEUKiesr"))
   protected Datum datumEindeUitsluitingEUKiesrecht;


   @Id
   @SequenceGenerator(name = "seq_His_PersEUVerkiezingen", sequenceName = "Kern.seq_His_PersEUVerkiezingen")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersEUVerkiezingen")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonEUVerkiezingenID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

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
