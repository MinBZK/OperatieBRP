/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.soa.esb.samples.quickstart.webservicemtom.webservice;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.DataHandler;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Heiko Braun <heiko.braun@jboss.com>
 * @since 22-Sep-2006
 */
public class XOPSupport {

   public static byte[] getBytesFromFile(File file) throws IOException
   {
      InputStream is = new FileInputStream(file);

      long length = file.length();
      byte[] bytes = new byte[(int)length];

      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
      {
         offset += numRead;
      }

      is.close();
      return bytes;
   }

   public static Image createTestImage(File imgFile)
   {
      Image image = null;
      try
      {
         URL url = imgFile.toURL();

         image = null;
         try
         {
            image = Toolkit.getDefaultToolkit().createImage(url);
         }
         catch (Throwable th)
         {
            //log.warn("Cannot create Image: " + th);
         }
      }
      catch (MalformedURLException e)
      {
         throw new RuntimeException(e);
      }

      return image;
   }

   public static StreamSource createTestSource()
   {
      return new StreamSource(new ByteArrayInputStream("<some><nestedXml/></some>".getBytes()));
   }

   public static DataHandler createDataHandler(File imgFile)
   {
      try
      {
         URL url = imgFile.toURL();
         return new DataHandler(url);
      }
      catch (MalformedURLException e)
      {
         throw new RuntimeException(e);
      }
   }
}
