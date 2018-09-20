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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Populatiecriterium;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.TekstDoelbinding;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Protocolleringsniveau;


/**
 * Doelbinding.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDoelbindingStandaard extends AbstractGroep {

   @Column(name = "Protocolleringsniveau")
   protected Protocolleringsniveau protocolleringsniveau;

   @AttributeOverride(name = "waarde", column = @Column(name = "TekstDoelbinding"))
   protected TekstDoelbinding tekstDoelbinding;

   @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
   protected Populatiecriterium populatiecriterium;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndVerstrbeperkingHonoreren"))
   protected JaNee indicatieVerstrekkingsbeperkingHonoreren;


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
