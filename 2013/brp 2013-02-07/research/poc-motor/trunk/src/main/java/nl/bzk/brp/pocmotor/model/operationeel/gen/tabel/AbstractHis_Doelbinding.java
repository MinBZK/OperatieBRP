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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_DoelbindingID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Populatiecriterium;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.TekstDoelbinding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Protocolleringsniveau;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Doelbinding;


/**
 * His Doelbinding

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Doelbinding extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_DoelbindingID id;

   @ManyToOne
   @JoinColumn(name = "Doelbinding")
   protected Doelbinding doelbinding;

   @Column(name = "Protocolleringsniveau")
   protected Protocolleringsniveau protocolleringsniveau;

   @AttributeOverride(name = "waarde", column = @Column(name = "TekstDoelbinding"))
   protected TekstDoelbinding tekstDoelbinding;

   @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
   protected Populatiecriterium populatiecriterium;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndVerstrbeperkingHonoreren"))
   protected JaNee indicatieVerstrekkingsbeperkingHonoreren;


   @Id
   @SequenceGenerator(name = "seq_His_Doelbinding", sequenceName = "AutAut.seq_His_Doelbinding")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Doelbinding")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_DoelbindingID();
      }
      id.setWaarde(value);
   }

   public Doelbinding getDoelbinding() {
      return doelbinding;
   }

   public void setDoelbinding(final Doelbinding doelbinding) {
      this.doelbinding = doelbinding;
   }

   public Protocolleringsniveau getProtocolleringsniveau() {
      return protocolleringsniveau;
   }

   public void setProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
      this.protocolleringsniveau = protocolleringsniveau;
   }

   public TekstDoelbinding getTekstDoelbinding() {
      return tekstDoelbinding;
   }

   public void setTekstDoelbinding(final TekstDoelbinding tekstDoelbinding) {
      this.tekstDoelbinding = tekstDoelbinding;
   }

   public Populatiecriterium getPopulatiecriterium() {
      return populatiecriterium;
   }

   public void setPopulatiecriterium(final Populatiecriterium populatiecriterium) {
      this.populatiecriterium = populatiecriterium;
   }

   public JaNee getIndicatieVerstrekkingsbeperkingHonoreren() {
      return indicatieVerstrekkingsbeperkingHonoreren;
   }

   public void setIndicatieVerstrekkingsbeperkingHonoreren(final JaNee indicatieVerstrekkingsbeperkingHonoreren) {
      this.indicatieVerstrekkingsbeperkingHonoreren = indicatieVerstrekkingsbeperkingHonoreren;
   }



}
