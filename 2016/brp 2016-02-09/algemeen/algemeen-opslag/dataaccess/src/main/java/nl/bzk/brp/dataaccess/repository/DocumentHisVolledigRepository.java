/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring-data-jpa repository voor {@link nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl}.
 */
public interface DocumentHisVolledigRepository extends JpaRepository<DocumentHisVolledigImpl, Long> {

}
