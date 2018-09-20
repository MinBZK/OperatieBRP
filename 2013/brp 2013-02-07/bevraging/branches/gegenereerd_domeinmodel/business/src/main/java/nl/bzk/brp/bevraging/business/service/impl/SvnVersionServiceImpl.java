/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.service.SvnVersionService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


/**
 * Implementatie op basis van door maven gegenereerde version.xml.
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
                if (versionDocument.getRootElement().element(key.name()) != null) {
                    String value = versionDocument.getRootElement().element(key.name().toUpperCase()).getText();
                    tempMap.put(key, value);
                }
            }

        } catch (IOException e) {
            LOGGER.error("Fout bij het inlezen van het versie bestand: versie zal niet goed geinitialiseerd zijn", e);
        } catch (DocumentException e) {
            LOGGER.error("Fout bij het inlezen van het versie bestand: versie zal niet goed geinitialiseerd zijn", e);
        } finally {
            svnVersion = Collections.unmodifiableMap(tempMap);
        }
    }

    @Override
    public String getAppString() {
        final String appString;
        if (svnVersion.isEmpty()) {
            appString = "BRP Bevraging - versie onbekend";
        } else {
            appString =
                "BRP Bevraging " + svnVersion.get(SvnVersionEnum.POMVERSION) + ", gebouwd op "
                    + svnVersion.get(SvnVersionEnum.MAVENBUILDTIMESTAMP);
        }
        return appString;
    }

}
