/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service;

import nl.bzk.brp.model.Adres;
import nl.bzk.brp.repository.standard.AdresRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 *
 */
@Service("adresService")
@Transactional
public class AdresServiceImpl implements AdresService {

    @Inject
    private AdresRepository repository;

    @Override
    public Adres create(String straat) {

        return repository.create(straat);
    }
}
