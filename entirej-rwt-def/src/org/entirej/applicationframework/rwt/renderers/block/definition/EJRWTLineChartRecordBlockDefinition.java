/*******************************************************************************
 * Copyright 2017 Mojave Innovations GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Mojave Innovations GmbH - initial API and implementation
 ******************************************************************************/
package org.entirej.applicationframework.rwt.renderers.block.definition;

import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTChartBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTSingleRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTInsertScreenRendererDefinition;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTQueryScreenRendererDefinition;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTUpdateScreenRendererDefinition;
import org.entirej.framework.core.properties.definitions.EJPropertyDefinitionType;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinition;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionListener;
import org.entirej.framework.core.properties.interfaces.EJMainScreenProperties;
import org.entirej.framework.dev.properties.EJDevPropertyDefinition;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionGroup;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionList;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevBlockRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevBlockRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevInsertScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevQueryScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevUpdateScreenRendererDefinition;

public class EJRWTLineChartRecordBlockDefinition implements EJDevBlockRendererDefinition
{
    public EJRWTLineChartRecordBlockDefinition()
    {
    }

    @Override
    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.rwt.renderers.chart.EJRWTLineChartRecordBlockRenderer";
    }

    @Override
    public boolean allowSpacerItems()
    {
        return false;
    }

    public void loadValidValuesForProperty(EJFrameworkExtensionProperties frameworkExtensionProperties, EJPropertyDefinition propertyDefinition)
    {
        // no impl
    }

    public void propertyChanged(EJPropertyDefinitionListener propertyDefListener, EJFrameworkExtensionProperties properties, String propertyName)
    {
        // no impl
    }

    public boolean useInsertScreen()
    {
        return true;
    }

    public boolean useQueryScreen()
    {
        return true;
    }

    public boolean useUpdateScreen()
    {
        return true;
    }

    @Override
    public EJDevInsertScreenRendererDefinition getInsertScreenRendererDefinition()
    {
        return new EJRWTInsertScreenRendererDefinition();
    }

    @Override
    public EJDevQueryScreenRendererDefinition getQueryScreenRendererDefinition()
    {
        return new EJRWTQueryScreenRendererDefinition();
    }

    @Override
    public EJDevUpdateScreenRendererDefinition getUpdateScreenRendererDefinition()
    {
        return new EJRWTUpdateScreenRendererDefinition();
    }

    public boolean allowMultipleItemGroupsOnMainScreen()
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.entirej.framework.renderers.IBlockRenderer#
     * getBlockPropertyDefinitionGroup()
     */
    public EJPropertyDefinitionGroup getBlockPropertyDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("LineChart-Record Block");
        
        
        EJDevPropertyDefinition relationItem = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.X_AXIS_COLUMN,
                EJPropertyDefinitionType.BLOCK_ITEM);
        relationItem.setLabel("X Axis");
        relationItem.setMandatory(true);

        EJDevPropertyDefinition animation = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.ANIMATION, EJPropertyDefinitionType.BOOLEAN);
        animation.setLabel("Animation");
        animation.setDefaultValue("true");
        
        EJDevPropertyDefinition showToolTips = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.SHOW_TOOLTIPS, EJPropertyDefinitionType.BOOLEAN);
        showToolTips.setLabel("Show ToolTips");
        showToolTips.setDefaultValue("true");
        
        EJDevPropertyDefinition scaleBeginAtZero = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.SCALE_BEGIN_AT_ZERO, EJPropertyDefinitionType.BOOLEAN);
        scaleBeginAtZero.setLabel("Scale Begin At Zero");
        scaleBeginAtZero.setDefaultValue("true");
        
        EJDevPropertyDefinition bezierCurve = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.BEZIER_CURVE, EJPropertyDefinitionType.BOOLEAN);
        bezierCurve.setLabel("Bezier Curve");
        bezierCurve.setDefaultValue("true");
        
        
        EJDevPropertyDefinition showFill = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.SHOW_FILL, EJPropertyDefinitionType.BOOLEAN);
        showFill.setLabel("Show Fill");
        showFill.setDefaultValue("true");
        
        EJDevPropertyDefinition scaleShowLabels = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.SCALE_SHOW_LABELS, EJPropertyDefinitionType.BOOLEAN);
        scaleShowLabels.setLabel("Scale Show Labels");
        scaleShowLabels.setDefaultValue("true");
        
        EJDevPropertyDefinition pointDotRadius = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.POINT_DOT_RADIUS, EJPropertyDefinitionType.INTEGER);
        pointDotRadius.setLabel("Point Dot Radius");
        pointDotRadius.setDefaultValue("3");
        
        EJDevPropertyDefinition strokeWidth = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.STROKE_WIDTH, EJPropertyDefinitionType.INTEGER);
        strokeWidth.setLabel("Stroke Width");
        strokeWidth.setDefaultValue("2");

        mainGroup.addPropertyDefinition(relationItem);
        mainGroup.addPropertyDefinition(animation);
        mainGroup.addPropertyDefinition(showToolTips);
        mainGroup.addPropertyDefinition(scaleBeginAtZero);
        mainGroup.addPropertyDefinition(bezierCurve);
        mainGroup.addPropertyDefinition(showFill);
        mainGroup.addPropertyDefinition(scaleShowLabels);
        mainGroup.addPropertyDefinition(pointDotRadius);
        mainGroup.addPropertyDefinition(strokeWidth);

        EJDevPropertyDefinitionGroup sectionGroup = new EJDevPropertyDefinitionGroup("TITLE_BAR");
        sectionGroup.setLabel("Title Bar");

        EJDevPropertyDefinition title = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_TITLE, EJPropertyDefinitionType.STRING);
        title.setLabel("Title");
        title.setDescription("Title Bar Caption.");

        EJDevPropertyDefinition showTitleBarExpanded = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_EXPANDED, EJPropertyDefinitionType.BOOLEAN);
        showTitleBarExpanded.setLabel("Title Bar Expanded");
        showTitleBarExpanded.setDescription("If selected, the renderer will display section title bar expanded by default.");
        showTitleBarExpanded.setDefaultValue("true");

        EJDevPropertyDefinition titleBarmode = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE, EJPropertyDefinitionType.STRING);
        titleBarmode.setLabel("Title Bar Mode");
        titleBarmode.setDescription("The Title Bar dispaly mode");
        titleBarmode.addValidValue(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_GROUP, "Group");
        titleBarmode.addValidValue(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_TWISTIE, "Twistie");
        titleBarmode.addValidValue(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_TREE_NODE, "Tree Node");
        titleBarmode.addValidValue(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_NO_EXPAND, "Not Expandable");
        // titleBarmode.setDefaultValue(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_GROUP);
        // titleBarmode.setMandatory(true);

        EJDevPropertyDefinitionList list = new EJDevPropertyDefinitionList(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_ACTIONS, "Actions");
        EJDevPropertyDefinition actionID = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_ACTION_ID, EJPropertyDefinitionType.ACTION_COMMAND);
        actionID.setLabel("Action Command");
        actionID.setDescription("Action command id for action processor.");
        actionID.setMandatory(true);

        EJDevPropertyDefinition actionImage = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_ACTION_IMAGE, EJPropertyDefinitionType.PROJECT_FILE);
        actionImage.setLabel("Action Image");
        actionImage.setDescription("Action image file from project path.");
        actionImage.setMandatory(true);

        EJDevPropertyDefinition actionName = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_ACTION_NAME, EJPropertyDefinitionType.STRING);
        actionName.setLabel("Action Name");
        actionName.setDescription("Action name");

        EJDevPropertyDefinition actionTooltip = new EJDevPropertyDefinition(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_ACTION_TOOLTIP, EJPropertyDefinitionType.STRING);
        actionTooltip.setLabel("Action Tooltip");
        actionTooltip.setDescription("Action tooltip.");

        list.addPropertyDefinition(actionID);
        list.addPropertyDefinition(actionImage);
        list.addPropertyDefinition(actionName);
        list.addPropertyDefinition(actionTooltip);

        sectionGroup.addPropertyDefinition(titleBarmode);
        sectionGroup.addPropertyDefinition(title);
        sectionGroup.addPropertyDefinitionList(list);

        sectionGroup.addPropertyDefinition(showTitleBarExpanded);
        mainGroup.addSubGroup(sectionGroup);

        return mainGroup;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.entirej.framework.renderers.IBlockRenderer#
     * getRequiredItemPropertiesDefinitionGroup()
     */
    public EJPropertyDefinitionGroup getItemPropertiesDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("LineChart-Record Block: Required Item Properties");

        EJDevPropertyDefinition visualAttribute = new EJDevPropertyDefinition(EJRWTChartBlockDefinitionProperties.VISUAL_ATTRIBUTE_PROPERTY, EJPropertyDefinitionType.VISUAL_ATTRIBUTE);
        visualAttribute.setLabel("Visual Attribute");
        visualAttribute.setDescription("The background, foreground and font attributes applied for screen item");
        visualAttribute.setMandatory(false);

        mainGroup.addPropertyDefinition(visualAttribute);

        return mainGroup;
    }

    @Override
    public EJPropertyDefinitionGroup getSpacerItemPropertiesDefinitionGroup()
    {
        // No spacers are available for a multi record block
        return null;
    }

    @Override
    public EJDevBlockRendererDefinitionControl addBlockControlToCanvas(EJMainScreenProperties mainScreenProperties, EJDevBlockDisplayProperties blockDisplayProperties, Composite parent, FormToolkit toolkit)
    {
        EJFrameworkExtensionProperties rendererProperties = blockDisplayProperties.getBlockRendererProperties();
        if (blockDisplayProperties != null)
        {
            rendererProperties = rendererProperties.getPropertyGroup(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR);
        }
        Composite layoutBody;
        if (rendererProperties != null && rendererProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE) != null
                && !EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_GROUP.equals(rendererProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE)))
        {
            int style = ExpandableComposite.TITLE_BAR;

            String mode = rendererProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE);
            if (EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_TWISTIE.equals(mode))
            {
                style = style | ExpandableComposite.TWISTIE;
            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_TREE_NODE.equals(mode))
            {
                style = style | ExpandableComposite.TREE_NODE;
            }
            if (rendererProperties.getBooleanProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_EXPANDED, true))
            {
                style = style | ExpandableComposite.EXPANDED;
            }
            String title = rendererProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_TITLE);
            Section section = toolkit.createSection(parent, style);
            if (title != null)
                section.setText(title);
            section.setFont(parent.getFont());
            section.setForeground(parent.getForeground());
            if (mainScreenProperties.getDisplayFrame())
            {

                layoutBody = new Group(section, SWT.NONE);
                layoutBody.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
                if (mainScreenProperties.getFrameTitle() != null)
                    ((Group) layoutBody).setText(mainScreenProperties.getFrameTitle());

            }
            else
            {
                layoutBody = toolkit.createComposite(section);
            }
            section.setClient(layoutBody);
        }
        else
        {

            if (mainScreenProperties.getDisplayFrame())
            {

                layoutBody = new Group(parent, SWT.NONE);
                if (mainScreenProperties.getFrameTitle() != null)
                    ((Group) layoutBody).setText(mainScreenProperties.getFrameTitle());

            }
            else
            {
                layoutBody = new Composite(parent, SWT.NONE);
            }
        }

        layoutBody.setLayout(new GridLayout(mainScreenProperties.getNumCols(), false));

        EJDevBlockRendererDefinitionControl control = addTreeTable(blockDisplayProperties, layoutBody, toolkit);

        return control;
    }

    private EJDevBlockRendererDefinitionControl addTreeTable(EJDevBlockDisplayProperties blockDisplayProperties, Composite client, FormToolkit toolkit)
    {

        Label label = new Label(client, SWT.NONE);

        label.setText("Line Chart");
        return new EJDevBlockRendererDefinitionControl(blockDisplayProperties, Collections.<EJDevItemRendererDefinitionControl> emptyList());
    }

    @Override
    public EJDevItemRendererDefinitionControl getSpacerItemControl(EJDevScreenItemDisplayProperties itemProperties, Composite parent, FormToolkit toolkit)
    {
        return null;
    }

    @Override
    public EJPropertyDefinitionGroup getItemGroupPropertiesDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
