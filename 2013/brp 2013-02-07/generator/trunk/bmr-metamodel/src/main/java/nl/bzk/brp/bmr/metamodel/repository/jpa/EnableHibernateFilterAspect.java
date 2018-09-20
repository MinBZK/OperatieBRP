/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository.jpa;

import javax.persistence.EntityManager;

import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.repository.ModelRepository;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Component;


/**
 * Activeert de Hibernate filters die ervoor zorgen dat alleen elementen van een bepaalde versie en van een bepaalde
 * logische laag gelezen worden.
 */
@Aspect
@Component
public class EnableHibernateFilterAspect {

    /**
     * Activeert de Hibernate filters die ervoor zorgen dat alleen elementen van een bepaalde versie en van een bepaalde
     * logische laag gelezen worden.
     *
     * @param pjp het {@link ProceedingJoinPoint} waaraan het target object wordt ontleend waar de Hibernate sessie uit
     *            gehaald wordt.
     * @return de return waarde van de oorspronkelijke methode die door dit aspect ge-wrapped wordt.
     * @throws Throwable als er ook maar iets onverwachts gebeurt.
     */
    @Around("execution(* nl.bzk.brp.bmr.metamodel.repository..*.*(..))")
    public Object enableFilter(final ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getTarget() instanceof ModelRepository) {
            if (Laag.huidigeLaag != null) {
                ModelRepository repository = (ModelRepository) pjp.getTarget();
                EntityManager em = repository.getEntityManager();
                Session session = (Session) em.getDelegate();
                session.enableFilter("ElementLaag").setParameter("laag", Laag.huidigeLaag);
            }
        }
        Object retVal = pjp.proceed();
        return retVal;
    }
}
