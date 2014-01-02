/*******************************************************************************
 * Copyright 2013 Mojave Innovations GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Mojave Innovations GmbH - initial API and implementation
 ******************************************************************************/
package org.entirej.applicationframework.rwt.renderers.block.definition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTSingleRecordBlockDefinitionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockItemDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevItemGroupDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevMainScreenItemDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevBlockRendererDefinition;


public class EJRWTBlockPreviewerCreator
{
    public List<EJDevItemRendererDefinitionControl> addBlockPreviewControl(EJDevBlockRendererDefinition blockRendererDef,
            EJDevBlockDisplayProperties blockDisplayProperties, Composite blockParent, FormToolkit toolkit)
    {
        List<EJDevItemRendererDefinitionControl> itemControls = new ArrayList<EJDevItemRendererDefinitionControl>();

        for (EJDevItemGroupDisplayProperties itemGroupProperties : blockDisplayProperties.getMainScreenItemGroupDisplayContainer()
                .getAllItemGroupDisplayProperties())
        {
            addItemGroup(blockParent, blockRendererDef, blockDisplayProperties, itemGroupProperties, toolkit, itemControls);
        }

        return itemControls;
    }

    private void addItemGroup(Composite parent, EJDevBlockRendererDefinition blockRendererDef, EJDevBlockDisplayProperties blockDisplayProperties,
            EJDevItemGroupDisplayProperties itemGroupProperties, FormToolkit toolkit, List<EJDevItemRendererDefinitionControl> itemControls)
    {
        Composite group = null;

        if (itemGroupProperties.dispayGroupFrame())
        {
            if (itemGroupProperties.getFrameTitle() == null)
            {
                group = new Composite(parent, SWT.SHADOW_NONE);
                group.setLayoutData(createItemGroupGridData(itemGroupProperties));
            }
            else
            {
                EJFrameworkExtensionProperties rendererProperties = itemGroupProperties.getRendererProperties();
                if (rendererProperties != null
                        && rendererProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE) != null
                        && !EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE_GROUP.equals(rendererProperties
                                .getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR_MODE)))
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
                    Section section = toolkit.createSection(parent, style);
                    section.setFont(parent.getFont());
                    section.setText(itemGroupProperties.getFrameTitle());
                    group = toolkit.createComposite(section);
                    section.setLayoutData(createItemGroupGridData(itemGroupProperties));
                    section.setClient(group);
                }
                else
                {
                    group = new Group(parent, SWT.SHADOW_ETCHED_IN);
                    ((Group) group).setText(itemGroupProperties.getFrameTitle());
                    group.setLayoutData(createItemGroupGridData(itemGroupProperties));
                }
            }
        }
        else
        {
            group = new Composite(parent, SWT.SHADOW_NONE);
            group.setLayoutData(createItemGroupGridData(itemGroupProperties));
        }

        // toolkit.adapt(group);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = itemGroupProperties.getNumCols();
        gridLayout.horizontalSpacing = 0;
        gridLayout.verticalSpacing = 0;
        group.setLayout(gridLayout);

        ItemCreator itemCreator = new ItemCreator();
        for (EJDevScreenItemDisplayProperties definition : itemGroupProperties.getAllItemDisplayProperties())
        {
            if (definition.isSpacerItem())
            {
                EJDevItemRendererDefinitionControl control = itemCreator.addSpacerItem(blockRendererDef, group, definition, toolkit);
                itemControls.add(control);
            }
            else
            {
                EJDevBlockItemDisplayProperties blockItemProperties = blockDisplayProperties.getBlockItemDisplayContainer().getItemProperties(
                        definition.getReferencedItemName());
                if (blockItemProperties == null)
                {
                    throw new IllegalStateException("Unable to retrrieve the blockItemProperties for: " + definition.getBlockName() + ":"
                            + definition.getBlockItemDisplayProperties().getName());
                }
                EJDevItemRendererDefinitionControl control = itemCreator.createItem(itemGroupProperties.getNumCols(), blockItemProperties,
                        (EJDevMainScreenItemDisplayProperties) definition, group, toolkit);
                itemControls.add(control);
            }
        }

        for (EJDevItemGroupDisplayProperties subGroup : itemGroupProperties.getChildItemGroupContainer().getAllItemGroupDisplayProperties())
        {
            addItemGroup(group, blockRendererDef, blockDisplayProperties, subGroup, toolkit, itemControls);
        }

        toolkit.paintBordersFor(group);
    }

    private class ItemCreator
    {
        public ItemCreator()
        {
        }

        public EJDevItemRendererDefinitionControl createItem(int cols, EJDevBlockItemDisplayProperties blockItemProperties,
                EJDevMainScreenItemDisplayProperties screenItemProperties, Composite blockCanvas, FormToolkit toolkit)
        {
            EJFrameworkExtensionProperties blockRequiredItemProperties = screenItemProperties.getBlockRendererRequiredProperties();

            EJDevItemRendererDefinitionControl _control = null;

            String labelPosition = blockRequiredItemProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.LABEL_POSITION_PROPERTY);
            String labelOrientation = blockRequiredItemProperties.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.LABEL_ORIENTATION_PROPERTY);

            if ((screenItemProperties.getLabel() != null && screenItemProperties.getLabel().trim().length() > 0))
            {
                if (EJRWTSingleRecordBlockDefinitionProperties.LABEL_POSITION_RIGHT_PROPERTY.equals(labelPosition))
                {
                    _control = addItem(blockCanvas, blockItemProperties, screenItemProperties, blockRequiredItemProperties, toolkit);
                    addLabel(blockCanvas, blockItemProperties, screenItemProperties, blockRequiredItemProperties, labelOrientation, toolkit);
                }
                else
                {

                    addLabel(blockCanvas, blockItemProperties, screenItemProperties, blockRequiredItemProperties, labelOrientation, toolkit);
                    _control = addItem(blockCanvas, blockItemProperties, screenItemProperties, blockRequiredItemProperties, toolkit);

                }
            }
            else
            {
                _control = addItem(blockCanvas, blockItemProperties, screenItemProperties, blockRequiredItemProperties, toolkit);
            }

            return _control;
        }

        private void addLabel(Composite canvas, EJDevBlockItemDisplayProperties blockItemProperites, EJDevMainScreenItemDisplayProperties screenItemProperties,
                EJFrameworkExtensionProperties blockRequiredItemProperties, String labelOrientation, FormToolkit toolkit)
        {

            if (blockItemProperites.getItemRendererDefinition() == null)
            {
                throw new IllegalStateException("Unable to retrieve the item renderer properties for item renderer: "
                        + blockItemProperites.getItemRendererName());
            }

            Control labelControl = blockItemProperites.getItemRendererDefinition().getLabelControl(screenItemProperties, canvas, toolkit);
            if (labelControl != null)
            {
                labelControl.setLayoutData(createBlockLableGridData(blockRequiredItemProperties));

                labletextAliment((Label) labelControl, labelOrientation);
            }
        }

        private void labletextAliment(Label label, String labelOrientation)
        {
            if (label == null)
                return;
            // label.setLayoutData(new GridData(GridData.FILL_BOTH));
            if (EJRWTSingleRecordBlockDefinitionProperties.LABEL_ORIENTATION_LEFT_PROPERTY.equals(labelOrientation))
            {
                label.setAlignment(SWT.LEFT);
            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.LABEL_ORIENTATION_RIGHT_PROPERTY.equals(labelOrientation))
            {
                label.setAlignment(SWT.RIGHT);
            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.LABEL_ORIENTATION_CENTER_PROPERTY.equals(labelOrientation))
            {
                label.setAlignment(SWT.CENTER);
            }
        }

        private EJDevItemRendererDefinitionControl addItem(Composite canvas, EJDevBlockItemDisplayProperties blockItemProperties,
                EJDevMainScreenItemDisplayProperties screenItemProperties, EJFrameworkExtensionProperties blockRequiredItemProperties, FormToolkit toolkit)
        {
            EJDevItemRendererDefinitionControl itemControl = blockItemProperties.getItemRendererDefinition().getItemControl(screenItemProperties, canvas,
                    toolkit);

            itemControl.getItemControl().setLayoutData(
                    createBlockItemGridData(blockRequiredItemProperties, itemControl.getItemControl(), itemControl.useFontDimensions()));

            return itemControl;
        }

        public EJDevItemRendererDefinitionControl addSpacerItem(EJDevBlockRendererDefinition blockRendererDef, Composite canvas,
                EJDevScreenItemDisplayProperties screenItemProperties, FormToolkit toolkit)
        {
            EJFrameworkExtensionProperties screenRequiredItemProperties;

            if (screenItemProperties instanceof EJDevMainScreenItemDisplayProperties)
            {
                screenRequiredItemProperties = ((EJDevMainScreenItemDisplayProperties) screenItemProperties).getBlockRendererRequiredProperties();
            }
            else
            {
                return null;
            }

            EJDevItemRendererDefinitionControl itemControl = blockRendererDef.getSpacerItemControl(screenItemProperties, canvas, toolkit);

            itemControl.getItemControl().setLayoutData(createBlockItemGridData(screenRequiredItemProperties, null, false));

            return itemControl;
        }

        private GridData createBlockLableGridData(EJFrameworkExtensionProperties blockRequiredItemProperties)
        {

            GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

            gridData.grabExcessHorizontalSpace = false;
            gridData.verticalSpan = blockRequiredItemProperties.getIntProperty(EJRWTSingleRecordBlockDefinitionProperties.MAIN_YSPAN_PROPERTY, 1);
            if (gridData.verticalSpan > 1
                    || blockRequiredItemProperties.getBooleanProperty(EJRWTSingleRecordBlockDefinitionProperties.MAIN_EXPAND_Y_PROPERTY, false))
            {
                gridData.verticalAlignment = SWT.TOP;
            }
            return gridData;
        }

        private GridData createBlockItemGridData(EJFrameworkExtensionProperties blockRequiredItemProperties, Control control, boolean useFontDimensions)
        {

            boolean grabExcessVerticalSpace = blockRequiredItemProperties.getBooleanProperty(EJRWTSingleRecordBlockDefinitionProperties.MAIN_EXPAND_Y_PROPERTY,
                    false);
            boolean grabExcessHorizontalSpace = blockRequiredItemProperties.getBooleanProperty(
                    EJRWTSingleRecordBlockDefinitionProperties.MAIN_EXPAND_X_PROPERTY, false);
            GridData gridData;
            if (grabExcessVerticalSpace && grabExcessHorizontalSpace)
                gridData = new GridData(GridData.FILL_BOTH);
            else if (!grabExcessVerticalSpace && grabExcessHorizontalSpace)
                gridData = new GridData(GridData.FILL_BOTH);
            else if (grabExcessVerticalSpace && !grabExcessHorizontalSpace)
                gridData = new GridData(GridData.FILL_VERTICAL);
            else
                gridData = new GridData(GridData.FILL_VERTICAL);
            gridData.horizontalSpan = blockRequiredItemProperties.getIntProperty(EJRWTSingleRecordBlockDefinitionProperties.MAIN_XSPAN_PROPERTY, 1);
            gridData.verticalSpan = blockRequiredItemProperties.getIntProperty(EJRWTSingleRecordBlockDefinitionProperties.MAIN_YSPAN_PROPERTY, 1);
            gridData.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
            gridData.grabExcessVerticalSpace = grabExcessVerticalSpace;

            int displayedWidth = blockRequiredItemProperties.getIntProperty(EJRWTSingleRecordBlockDefinitionProperties.DISPLAYED_WIDTH_PROPERTY, 0);
            int displayedHeight = blockRequiredItemProperties.getIntProperty(EJRWTSingleRecordBlockDefinitionProperties.DISPLAYED_HEIGHT_PROPERTY, 0);

            if (displayedWidth > 0)
            {
                if (useFontDimensions)
                {
                    float avgCharWidth = control == null ? 1 : getAvgCharWidth(control.getFont());
                    if (avgCharWidth > 0)
                    {
                        displayedWidth = (int) ((displayedWidth + 1) * avgCharWidth);
                    }
                }

                gridData.widthHint = displayedWidth;
            }
            if (displayedHeight > 0)
            {
                if (useFontDimensions)
                {
                    float avgCharHeight = control == null ? 1 : getCharHeight(control.getFont());
                    if (avgCharHeight > 0)
                    {
                        displayedHeight = (int) ((displayedHeight + 1) * avgCharHeight);
                    }
                }

                gridData.heightHint = displayedHeight;

            }

            return gridData;
        }

        public float getAvgCharWidth(Font font)
        {
            GC gc = new GC(Display.getDefault());
            try
            {

                gc.setFont(font);

                return gc.getFontMetrics().getAverageCharWidth();
            }
            finally
            {
                gc.dispose();
            }
        }

        public int getCharHeight(Font font)
        {
            if (font.getFontData().length > 0)
            {
                return font.getFontData()[0].getHeight();
            }
            return 13;
        }
    }

    GridData createItemGroupGridData(EJDevItemGroupDisplayProperties groupProperties)
    {
        GridData gridData = new GridData(GridData.FILL_BOTH);
        if (groupProperties.getWidth() > 0)
            gridData.widthHint = groupProperties.getWidth();
        if (groupProperties.getHeight() > 0)
            gridData.heightHint = groupProperties.getHeight();

        gridData.horizontalSpan = groupProperties.getXspan();
        gridData.verticalSpan = groupProperties.getYspan();
        gridData.grabExcessHorizontalSpace = groupProperties.canExpandHorizontally();
        gridData.grabExcessVerticalSpace = groupProperties.canExpandVertically();

        if (gridData.grabExcessHorizontalSpace)
            gridData.minimumWidth = groupProperties.getWidth();
        if (gridData.grabExcessVerticalSpace)
            gridData.minimumHeight = groupProperties.getHeight();

        return gridData;
    }

}
