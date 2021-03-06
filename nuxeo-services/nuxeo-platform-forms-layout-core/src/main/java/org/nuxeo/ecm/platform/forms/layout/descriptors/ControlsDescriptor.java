/*
 * (C) Copyright 2012 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Anahide Tchertchian
 */
package org.nuxeo.ecm.platform.forms.layout.descriptors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.common.xmap.annotation.XNodeMap;
import org.nuxeo.common.xmap.annotation.XObject;

/**
 * @since 5.7
 */
@XObject("controls")
public class ControlsDescriptor implements Serializable {

    private static final long serialVersionUID = 1L;

    @XNodeMap(value = "control", key = "@name", type = HashMap.class, componentType = String.class)
    Map<String, String> controls = new HashMap<String, String>();

    public Map<String, Serializable> getControls() {
        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.putAll(controls);
        return map;
    }
}
