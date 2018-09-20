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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_BetrokkenheidOuderlijkGezagID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid;


/**
 * His Betrokkenheid Ouderlijk gezag

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_BetrokkenheidOuderlijkGezag extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_BetrokkenheidOuderlijkGezagID id;

   @ManyToOne
   @JoinColumn(name = "Betr")
   protected Betrokkenheid betrokkenheid;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOuderHeeftGezag"))
   protected JaNee indicatieOuderHeeftGezag;


   @Id
   @SequenceGenerator(name = "seq_His_BetrOuderlijkGezag", sequenceName = "Kern.seq_His_BetrOuderlijkGezag")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_BetrOuderlijkGezag")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_BetrokkenheidOuderlijkGezagID();
      }
      id.setWaarde(value);
   }

   public Betrokkenheid getBetrokkenheid() {
      return betrokkenheid;
   }

   public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
      this.betrokkenheid = betrokkenheid;
   }

   public JaNee getIndicatieOuderHeeftGezag() {
      return indicatieOuderHeeftGezag;
   }

   public void setIndicatieOuderHeeftGezag(final JaNee indicatieOuderHeeftGezag) {
      this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
   }



}
