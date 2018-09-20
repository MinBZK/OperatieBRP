/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import javax.persistence.NoResultException;

import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tekst;

import org.springframework.stereotype.Repository;


@Repository
public class TekstDao extends AbstractJpaDao<Tekst> {

    @Override
    protected Class<Tekst> getModelClass() {
        return Tekst.class;
    }

    public Tekst getDefinitieForObject(final ObjectType objectType) {
        String template = "select e from %s e where e.element.id = %d AND soort='DEF'";
        String qlString = String.format(template, getModelClassName(), objectType.getId());
        Tekst tekst = getSingleResult(qlString);
        return tekst;
    }
    
    public Tekst getDefinitieToelichtingForObject(final ObjectType objectType) {
        String template = "select e from %s e where e.element.id = %d AND soort='DEFT'";
        String qlString = String.format(template, getModelClassName(), objectType.getId());
        Tekst tekst = getSingleResult(qlString);
        return tekst;
    }
    
    public Tekst getToelichtingVervolgForObject(final ObjectType objectType) {
        String template = "select e from %s e where e.element.id = %d AND soort='CONT'";
        String qlString = String.format(template, getModelClassName(), objectType.getId());
        Tekst tekst = getSingleResult(qlString);
        return tekst;
    }
    
    public String getJavaDocForObject(final ObjectType objectType) {
        String template = "select e from %s e where e.element.id = %d AND soort='DEF'";
        String qlString = String.format(template, getModelClassName(), objectType.getId());
        Tekst tekst = getSingleResult(qlString);
        return tekst.getTekst();
    }

    public String getFullJavaDocForObject(final ObjectType objectType) {
        String template = "select e from %s e where e.element.id = %d AND soort='DEF'";
        String qlString = String.format(template, getModelClassName(), objectType.getId());
        Tekst tekst = getSingleResult(qlString);
        String javaDoc = tekst.getTekst();
        try {
            javaDoc = javaDoc + getDefinitieToelichtingForObject(objectType).getTekst();
        } catch (NoResultException ex) {
            // Geen resultaat gevonden, is niet erg, ignore
        }
        try {
            javaDoc = javaDoc + getDefinitieToelichtingForObject(objectType).getTekst();
        } catch (NoResultException ex) {
            // Geen resultaat gevonden, is niet erg, ignore
        }
        return javaDoc;
    }

}
