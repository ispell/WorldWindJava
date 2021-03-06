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

package gov.nasa.worldwindx.applications.worldwindow.features;

import gov.nasa.worldwindx.applications.worldwindow.core.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author tag
 * @version $Id: OpenFile.java 1171 2013-02-11 21:45:02Z dcollins $
 */
public class OpenFile extends AbstractOpenResourceFeature
{
    public OpenFile(Registry registry)
    {
        super("Open File...", Constants.FEATURE_OPEN_FILE, null, registry);
    }

    @Override
    public void initialize(Controller controller)
    {
        super.initialize(controller);

        WWMenu fileMenu = (WWMenu) this.getController().getRegisteredObject(Constants.FILE_MENU);
        if (fileMenu != null)
            fileMenu.addMenu(this.getFeatureID());
    }

    @Override
    protected void doActionPerformed(ActionEvent actionEvent)
    {
        JFileChooser fc = this.getController().getFileChooser();
        fc.setDialogTitle("Open File");
        fc.setMultiSelectionEnabled(false);

        try
        {
            int status = fc.showOpenDialog(this.getController().getFrame());
            if (status == JFileChooser.APPROVE_OPTION)
            {
                this.runOpenThread(fc.getSelectedFile());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        fc.setDialogTitle("");
        fc.setMultiSelectionEnabled(true);

        super.doActionPerformed(actionEvent);
    }
}
