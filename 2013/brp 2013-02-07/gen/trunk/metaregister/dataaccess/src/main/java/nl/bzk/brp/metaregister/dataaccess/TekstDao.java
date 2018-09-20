/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Tekst;
import org.springframework.stereotype.Repository;


/**
 * DAO voor het ophalen van {@link Tekst} elementen.
 */
@Repository
public class TekstDao extends AbstractJpaDao<Tekst> {

    @Override
    protected Class<Tekst> getModelClass() {
        return Tekst.class;
    }

    public Tekst getDefinitieForObject(final GeneriekElement element) {
        String template = "select t from %s t where t.element.id = %d AND t.soortTekst='DEF'";
        String qlString = String.format(template, getModelClassName(), element.getId());
        return getOptionalResult(qlString);
    }

    public Tekst getDefinitieToelichtingForObject(final GeneriekElement element) {
        String template = "select t from %s t where t.element.id = %d AND t.soortTekst='DEFT'";
        String qlString = String.format(template, getModelClassName(), element.getId());
        return getOptionalResult(qlString);
    }

    public Tekst getToelichtingVervolgForObject(final GeneriekElement element) {
        String template = "select t from %s t where t.element.id = %d AND t.soortTekst='CONT'";
        String qlString = String.format(template, getModelClassName(), element.getId());
        return getOptionalResult(qlString);
    }

    public String getJavaDocForObject(final GeneriekElement element) {
        String template = "select t from %s t where t.element.id = %d AND t.soortTekst='DEF'";
        String qlString = String.format(template, getModelClassName(), element.getId());
        return getOptionalTekst(qlString);
    }

    public String getFullJavaDocForObject(final GeneriekElement element) {
        String template = "select t from %s t where t.element.id = %d AND t.soortTekst='DEF'";
        String qlString = String.format(template, getModelClassName(), element.getId());
        String javaDoc = getOptionalTekst(qlString);
        Tekst extraDoc = getDefinitieToelichtingForObject(element);
        if (extraDoc != null) {
            javaDoc += extraDoc.getTekst();
        }
        return javaDoc;
    }

    private String getOptionalTekst(final String qlString) {
        String resultaat;
        Tekst tekst = getOptionalResult(qlString);
        if (tekst == null) {
            //BMRTODOCUMENT
            resultaat = ".";
        } else {
            resultaat = tekst.getTekst();
            if (!resultaat.endsWith(".")) {
                resultaat += ".";
            }
        }
        return resultaat;
    }

}
