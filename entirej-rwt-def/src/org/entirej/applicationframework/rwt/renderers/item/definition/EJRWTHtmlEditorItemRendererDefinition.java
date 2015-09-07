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
package org.entirej.applicationframework.rwt.renderers.item.definition;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.entirej.framework.core.properties.definitions.EJPropertyDefinitionType;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinition;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionListener;
import org.entirej.framework.dev.properties.EJDevPropertyDefinition;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionGroup;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevItemRendererDefinition;

public class EJRWTHtmlEditorItemRendererDefinition implements EJDevItemRendererDefinition
{

    public static final String PROPERTY_CSS_KEY          = "CSS_KEY";
    public static final String PROPERTY_INLINE_KEY       = "INLINE";
    public static final String PROPERTY_PROFILE_KEY      = "PROFILE";
    public static final String PROPERTY_PROFILE_BASIC    = "Basic";
    public static final String PROPERTY_PROFILE_STANDARD = "Standard";
    public static final String PROPERTY_PROFILE_FULL     = "Full";

    public EJRWTHtmlEditorItemRendererDefinition()
    {
    }

    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.rwt.renderers.html.EJRWTHtmlEditorItemRenderer";
    }

    public boolean canExecuteActionCommand()
    {
        return true;
    }

    public void propertyChanged(EJPropertyDefinitionListener propertyDefListener, EJFrameworkExtensionProperties properties, String propertyName)
    {
        // no impl
    }

    public void loadValidValuesForProperty(EJFrameworkExtensionProperties frameworkExtensionProperties, EJPropertyDefinition propertyDefinition)
    {
        // no impl
    }

    public EJPropertyDefinitionGroup getItemPropertyDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Html Editor Item Renderer");
        mainGroup.setDescription("The html editor item will allow to edit content as html formatting");

        EJDevPropertyDefinition customCSSKey = new EJDevPropertyDefinition(PROPERTY_CSS_KEY, EJPropertyDefinitionType.STRING);
        customCSSKey.setLabel("Custom CSS Key");
        customCSSKey
                .setDescription("Indicates custom CSS key in project CSS file that can customize  item look and feel. Please refer to Entirej RWT CSS guide.");

        mainGroup.addPropertyDefinition(customCSSKey);

        EJDevPropertyDefinition inlineMode = new EJDevPropertyDefinition(PROPERTY_INLINE_KEY, EJPropertyDefinitionType.BOOLEAN);
        inlineMode.setLabel("Inline mode");
        inlineMode.setDescription("Indicates if this item should edit with inline toolbar");
        inlineMode.setDefaultValue("false");
        mainGroup.addPropertyDefinition(inlineMode);

        EJDevPropertyDefinition profile = new EJDevPropertyDefinition(PROPERTY_PROFILE_KEY, EJPropertyDefinitionType.STRING);
        profile.setLabel("Toolbar Profile");
        profile.addValidValue(PROPERTY_PROFILE_BASIC, PROPERTY_PROFILE_BASIC);
        profile.addValidValue(PROPERTY_PROFILE_STANDARD, PROPERTY_PROFILE_STANDARD);
        profile.addValidValue(PROPERTY_PROFILE_FULL, PROPERTY_PROFILE_FULL);
        profile.setDefaultValue(PROPERTY_PROFILE_STANDARD);
        mainGroup.addPropertyDefinition(profile);
        return mainGroup;
    }

    @Override
    public EJDevItemRendererDefinitionControl getItemControl(EJDevScreenItemDisplayProperties itemProperties, Composite parent, FormToolkit toolkit)
    {
        Text text = new Text(parent, SWT.NULL);
        text.setText("HTML EDITOR");
        text.setEditable(false);
        EJDevItemRendererDefinitionControl definitionControl = new EJDevItemRendererDefinitionControl(itemProperties, text);
        definitionControl.setUseFontDimensions(false);
        return definitionControl;

    }

    /**
     * Used to return the label widget for this item
     * <p>
     * If the widget does not display a label, then this method should do
     * nothing and <code>null</code> should be returned
     * 
     * @param parent
     *            The <code>Composite</code> upon which this widgets label will
     *            be displayed
     * @param screemDisplayProperties
     *            The display properties of this item
     * @param formToolkit
     *            The toolkit to use for the creation of the label widget
     * @return The label widget or <code>null</code> if this item displays no
     *         label
     */
    @Override
    public Control getLabelControl(EJDevScreenItemDisplayProperties itemProperties, Composite parent, FormToolkit toolkit)
    {
        String labelText = itemProperties.getLabel();
        Label label = new Label(parent, SWT.NULL);
        label.setText(labelText == null ? "" : labelText);
        return label;
    }

    public boolean isReadOnly()
    {
        return true;
    }
}
