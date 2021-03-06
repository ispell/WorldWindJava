/*
 * Copyright 2006-2009, 2017, 2020 United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 * 
 * The NASA World Wind Java (WWJ) platform is licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * NASA World Wind Java (WWJ) also contains the following 3rd party Open Source
 * software:
 * 
 *     Jackson Parser – Licensed under Apache 2.0
 *     GDAL – Licensed under MIT
 *     JOGL – Licensed under  Berkeley Software Distribution (BSD)
 *     Gluegen – Licensed under Berkeley Software Distribution (BSD)
 * 
 * A complete listing of 3rd Party software notices and licenses included in
 * NASA World Wind Java (WWJ)  can be found in the WorldWindJava-v2.2 3rd-party
 * notices and licenses PDF found in code directory.
 */

package gov.nasa.worldwind.ogc.kml;

import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwind.util.xml.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.*;

/**
 * Represents the KML <i>Create</i> element and provides access to its contents.
 *
 * @author tag
 * @version $Id: KMLCreate.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public class KMLCreate extends AbstractXMLEventParser implements KMLUpdateOperation
{
    protected List<KMLAbstractContainer> containers = new ArrayList<KMLAbstractContainer>();

    /**
     * Construct an instance.
     *
     * @param namespaceURI the qualifying namespace URI. May be null to indicate no namespace qualification.
     */
    public KMLCreate(String namespaceURI)
    {
        super(namespaceURI);
    }

    @Override
    protected void doAddEventContent(Object o, XMLEventParserContext ctx, XMLEvent event, Object... args)
        throws XMLStreamException
    {
        if (o instanceof KMLAbstractContainer)
            this.addContainer((KMLAbstractContainer) o);
        else
            super.doAddEventContent(o, ctx, event, args);
    }

    protected void addContainer(KMLAbstractContainer o)
    {
        this.containers.add(o);
    }

    public List<KMLAbstractContainer> getContainers()
    {
        return this.containers;
    }

    public void applyOperation(KMLRoot targetRoot)
    {
        for (KMLAbstractContainer container : this.containers)
        {
            String targetId = container.getTargetId();
            if (WWUtil.isEmpty(targetId))
                continue;

            Object o = targetRoot.getItemByID(targetId);
            if (o == null || !(o instanceof KMLAbstractContainer))
                continue;

            KMLAbstractContainer receivingContainer = (KMLAbstractContainer) o;

            for (KMLAbstractFeature feature : container.getFeatures())
            {
                receivingContainer.addFeature(feature);
            }
        }
    }
}
