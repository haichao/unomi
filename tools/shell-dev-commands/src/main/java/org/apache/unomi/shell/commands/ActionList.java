/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.unomi.shell.commands;

import org.apache.commons.lang3.StringUtils;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.unomi.api.actions.ActionType;
import org.apache.unomi.api.services.DefinitionsService;
import org.apache.unomi.common.DataTable;

import java.util.ArrayList;
import java.util.Collection;

@Command(scope = "unomi", name = "action-list", description = "This will list all the actions deployed in the Apache Unomi Context Server")
@Service
public class ActionList extends ListCommandSupport {

    @Reference
    DefinitionsService definitionsService;

    @Override
    protected String[] getHeaders() {
        return new String[] {
                "Id",
                "Name",
                "System tags"
        };
    }

    @Override
    protected DataTable buildDataTable() {
        Collection<ActionType> allActions = definitionsService.getAllActionTypes();

        DataTable dataTable = new DataTable();

        for (ActionType actionType : allActions) {
            ArrayList<Comparable> rowData = new ArrayList<>();
            rowData.add(actionType.getItemId());
            rowData.add(actionType.getMetadata().getName());
            rowData.add(StringUtils.join(actionType.getMetadata().getSystemTags(), ","));
            dataTable.addRow(rowData.toArray(new Comparable[rowData.size()]));
        }

        dataTable.sort(new DataTable.SortCriteria(1, DataTable.SortOrder.ASCENDING));
        return dataTable;
    }

}
