/*
 * (C) Copyright 2006-2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Tiago Cardoso <tcardoso@nuxeo.com>
 */
package org.nuxeo.ecm.platform.threed.transmissionFormat;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PropertyException;
import org.nuxeo.ecm.platform.threed.service.ThreeDService;
import org.nuxeo.runtime.api.Framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.nuxeo.ecm.platform.threed.ThreeDConstants.THREED_FACET;
import static org.nuxeo.ecm.platform.threed.ThreeDDocumentConstants.TRANSMISSIONS_PROPERTY;

/**
 * Backing bean for the Transmission Formats of an document.
 *
 * @since 8.4
 */
@Name("transmissionFormatActions")
@Scope(ScopeType.EVENT)
public class TransmissionFormatActions {

    public List<TransmissionFormatItem> getItems(DocumentModel doc) throws PropertyException {
        if (!doc.hasFacet(THREED_FACET)) {
            return Collections.emptyList();
        }
        int size = doc.getProperty(TRANSMISSIONS_PROPERTY).getValue(List.class).size();
        List<TransmissionFormatItem> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(new TransmissionFormatItem(doc, TRANSMISSIONS_PROPERTY, i));
        }
        return items;
    }

    public boolean isConvertingFormats(DocumentModel doc) {
        ThreeDService service = Framework.getLocalService(ThreeDService.class);
        return !service.getBatchProgress(doc.getRepositoryName(), doc.getId()).isUnknown();
    }

}