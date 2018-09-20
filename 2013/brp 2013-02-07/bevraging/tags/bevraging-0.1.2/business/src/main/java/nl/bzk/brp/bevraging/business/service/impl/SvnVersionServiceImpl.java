/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.SvnVersionService;

import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


/**
 * Implementatie op basis van door maven gegenereerde version.xml. {@inheritDoc}
 */

@Service
public class SvnVersionServiceImpl implements SvnVersionService {

    private Map<SvnVersionEnum, String> svnVersion;

    private static final Logger         LOGGER = LoggerFactory.getLogger(SvnVersionServiceImpl.class);

    @Inject
    private ApplicationContext          ctx;

    /**
     * init methode.
     */
    @PostConstruct
    public void init() {
        Map<SvnVersionEnum, String> tempMap = new HashMap<SvnVersionEnum, String>();

        try {
            InputStream inputStream = ctx.getResource("classpath:version.xml").getInputStream();
            Document versionDocument = new SAXReader().read(inputStream);

            for (SvnVersionEnum key : SvnVersionEnum.values()) {
                String value = versionDocument.getRootElement().element(key.name()).getText();
                tempMap.put(key, value);
            }

        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            svnVersion = MapUtils.unmodifiableMap(tempMap);
        }

    }

    @Override
    public String getAppString() {
        return "BRP v" + svnVersion.get(SvnVersionEnum.pomversion) + " build "
            + svnVersion.get(SvnVersionEnum.svnversion) + " " + svnVersion.get(SvnVersionEnum.mavenbuildtimestamp);
    }

}
