/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import nl.bzk.brp.metaregister.model.Groep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public abstract class AbstractGroepGenerator extends AbstractJavaGenerator<Groep> {

    @Autowired(required = true)
    private GroepDao groepDao;

    protected Collection<Groep> getGroepen() {
        //Retourneer alleen groepen die geen identiteits groep zijn.
        return Collections2.filter(groepDao.getAll(), new Predicate<Groep>() {
            @Override
            public boolean apply(@Nullable final Groep groep) {
                return !groep.getNaam().equals("Identiteit");
            }
        });
    }

}
