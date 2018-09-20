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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BetrokkenheidID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Ja;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Relatie;


/**
 * Betrokkenheid

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBetrokkenheid extends AbstractTabel {

   @Transient
   protected BetrokkenheidID id;

   @ManyToOne
   @JoinColumn(name = "Relatie")
   protected Relatie relatie;

   @Column(name = "Rol")
   protected SoortBetrokkenheid rol;

   @ManyToOne
   @JoinColumn(name = "Betrokkene")
   protected Persoon betrokkene;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOuder"))
   protected Ja indicatieOuder;

   @AttributeOverride(name = "waarde", column = @Column(name = "OuderStatusHis"))
   protected StatusHistorie ouderStatusHis;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOuderHeeftGezag"))
   protected JaNee indicatieOuderHeeftGezag;

   @AttributeOverride(name = "waarde", column = @Column(name = "OuderlijkGezagStatusHis"))
   protected StatusHistorie ouderlijkGezagStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Betr", sequenceName = "Kern.seq_Betr")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Betr")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new BetrokkenheidID();
      }
      id.setWaarde(value);
   }

   public Relatie getRelatie() {
      return relatie;
   }

   public void setRelatie(final Relatie relatie) {
      this.relatie = relatie;
   }

   public SoortBetrokkenheid getRol() {
      return rol;
   }

   public void setRol(final SoortBetrokkenheid rol) {
      this.rol = rol;
   }

   public Persoon getBetrokkene() {
      return betrokkene;
   }

   public void setBetrokkene(final Persoon betrokkene) {
      this.betrokkene = betrokkene;
   }

   public Ja getIndicatieOuder() {
      return indicatieOuder;
   }

   public void setIndicatieOuder(final Ja indicatieOuder) {
      this.indicatieOuder = indicatieOuder;
   }

   public StatusHistorie getOuderStatusHis() {
      return ouderStatusHis;
   }

   public void setOuderStatusHis(final StatusHistorie ouderStatusHis) {
      this.ouderStatusHis = ouderStatusHis;
   }

   public JaNee getIndicatieOuderHeeftGezag() {
      return indicatieOuderHeeftGezag;
   }

   public void setIndicatieOuderHeeftGezag(final JaNee indicatieOuderHeeftGezag) {
      this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
   }

   public StatusHistorie getOuderlijkGezagStatusHis() {
      return ouderlijkGezagStatusHis;
   }

   public void setOuderlijkGezagStatusHis(final StatusHistorie ouderlijkGezagStatusHis) {
      this.ouderlijkGezagStatusHis = ouderlijkGezagStatusHis;
   }



}
