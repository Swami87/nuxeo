/*
 * (C) Copyright 2007 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Nuxeo - initial API and implementation
 *
 * $Id$
 */

package org.nuxeo.ecm.directory;

import org.nuxeo.ecm.directory.api.DirectoryService;
import org.nuxeo.runtime.api.Framework;

/**
 * Implementation of common Reference logic.
 *
 * @author ogrisel
 */
public abstract class AbstractReference implements Reference {

    protected DirectoryServiceImpl directoryService;

    protected String sourceDirectoryName;

    protected Directory sourceDirectory;

    protected String targetDirectoryName;

    protected Directory targetDirectory;

    protected String fieldName;

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Directory getSourceDirectory() throws DirectoryException {
        if (sourceDirectory == null) {
            sourceDirectory = getDirectoryService().getDirectory(sourceDirectoryName);
        }
        return sourceDirectory;
    }

    @Override
    public void setSourceDirectoryName(String sourceDirectoryName) {
        sourceDirectory = null;
        this.sourceDirectoryName = sourceDirectoryName;
    }

    @Override
    public Directory getTargetDirectory() throws DirectoryException {
        if (targetDirectory == null) {
            targetDirectory = getDirectoryService().getDirectory(targetDirectoryName);
        }
        return targetDirectory;
    }

    @Override
    public void setTargetDirectoryName(String targetDirectoryName) {
        targetDirectory = null;
        this.targetDirectoryName = targetDirectoryName;
    }

    protected DirectoryServiceImpl getDirectoryService() {
        if (directoryService == null) {
            directoryService = (DirectoryServiceImpl) Framework.getRuntime().getComponent(DirectoryService.NAME);
        }
        return directoryService;
    }

    /**
     * @since 5.6
     */
    @Override
    public AbstractReference clone() {
        AbstractReference clone = newInstance();
        clone.sourceDirectoryName = sourceDirectoryName;
        clone.targetDirectoryName = targetDirectoryName;
        clone.fieldName = fieldName;
        return clone;
    }

    /**
     * Override to instantiate sub class, used in {@link #clone()} method
     *
     * @since 5.6
     */
    protected abstract AbstractReference newInstance();

}
