/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.hismodelattribuutaccess;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;


/**
 * Aspect dat een joinpoint definieert wat aangeroepen dient te worden als er een attribuut wordt opgevraagd uit een C/D laag entity. Oftewel een
 * AbstractHisXXX entity. Het opgevraagde attribuut wordt ge-administreerd in een {@link HisModelAttribuutAccessAdministratie} object dat per request wordt
 * bijgehouden.
 */
@Aspect
public final class HisModelAttribuutAccessAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private HisModelAttribuutAccessAdministratie administratie;

    /**
     * Methode die aangeroepen wordt voor de his model getter.
     *
     * @param joinPoint the join point
     */
    @Before("execution(* nl.bzk.brp.model.operationeel.kern.AbstractHis*.get*())")
    public void voorHisModelGetter(final JoinPoint joinPoint) {
        // Check eerst of er wel een administratie object aanwezig is. Dit wordt in principe
        // door de spring configuratie in het BRP project ge-inject, maar zou ook afwezig kunnen zijn
        // in het geval van een unit test of het gebruik van het model project buiten de BRP service om.
        // Check tevens of de administratie wel 'actief' is, dat wil zeggen, ontvankelijk voor registraties.
        // Dat laatste om te voorkomen dat er registraties gebeuren op ongewenste momenten.
        if (this.administratie != null && this.administratie.isActief()) {
            if (joinPoint.getSignature() instanceof MethodSignature
                && joinPoint.getTarget() instanceof ModelIdentificeerbaar)
            {
                final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                final ModelIdentificeerbaar<?> target = (ModelIdentificeerbaar<?>) joinPoint.getTarget();

                final AttribuutAccessor attribuutAccessor =
                    AnnotationUtils.findAnnotation(methodSignature.getMethod(), AttribuutAccessor.class);
                final GroepAccessor groepAccessor = AnnotationUtils.findAnnotation(target.getClass(), GroepAccessor.class);
                if (attribuutAccessor != null && groepAccessor != null) {
                    final int attribuutDbObjectId = attribuutAccessor.dbObjectId();
                    final int groepDbObjectId = groepAccessor.dbObjectId();

                    final Long voorkomenId = geefIdAlsLong(target);

                    // Als er een datum aanvang geldigheid op dit object staat, registreer dat dan ook.
                    final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid = geefDatumAanvangGeldigheid(target);
                    // Geef de gevonden info nu door aan de administratie.
                    this.administratie.attribuutGeraakt(groepDbObjectId, voorkomenId, attribuutDbObjectId,
                        datumAanvangGeldigheid);
                }
            } else {
                LOGGER.error("Onverwacht en niet toegestane lokatie van JoinPoint gedetecteerd: Signature was "
                        + "van type {} en target class was {}.", joinPoint.getSignature().getClass().getName(),
                    joinPoint
                        .getTarget().getClass().getName());
            }
        }
    }

    /**
     * Geeft de datum aanvang geldigheid, indien van toepassing.
     *
     * @param target De target.
     * @return De datum aanvang geldigheid.
     */
    private DatumEvtDeelsOnbekendAttribuut geefDatumAanvangGeldigheid(final ModelIdentificeerbaar<?> target) {
        DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid = null;
        if (target instanceof MaterieleHistorie) {
            datumAanvangGeldigheid = ((MaterieleHistorie) target).getDatumAanvangGeldigheid();
        }
        return datumAanvangGeldigheid;
    }

    /**
     * Geeft het id als long, of null als deze leeg is.
     *
     * @param target De target.
     * @return Het id.
     */
    private Long geefIdAlsLong(final ModelIdentificeerbaar<?> target) {
        final Long voorkomenId;
        if (target.getID() != null) {
            voorkomenId = target.getID().longValue();
        } else {
            voorkomenId = null;
        }
        return voorkomenId;
    }

    /**
     * Zet het object ({@link HisModelAttribuutAccessAdministratie} instantie) waarin de administratie van geraakte attributen wordt bijgehouden.
     *
     * @param administratie het administratie object waarin de geraakte attributen worden bijgehouden.
     */
    public void setAdministratie(final HisModelAttribuutAccessAdministratie administratie) {
        this.administratie = administratie;
    }
}
