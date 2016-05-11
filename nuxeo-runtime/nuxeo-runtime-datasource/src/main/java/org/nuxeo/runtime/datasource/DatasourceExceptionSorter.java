/*
 * (C) Copyright 2014, 2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 */
package org.nuxeo.runtime.datasource;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.tranql.connector.ExceptionSorter;

/**
 *
 *
 * @since 8.3
 */
public class DatasourceExceptionSorter implements ExceptionSorter {

    public enum Classcode {
        NoError("00"),
        Warning("01"),
        NoData("02"),
        DynamicSQLError("07"),
        ConnectionException("08"),
        TriggeredActionException("09"),
        FeatureNotSupported("0A"),
        InvalidTransactionInitiation("0B"),
        InvalidTargetTypeSpecification("0D"),
        InvalidSchemaNameListSpecification("0E"),
        LocatorException("0F"),
        ResignalWhenHandlerNotActive("0K"),
        InvalidGrantor("0L"),
        InvalidSqlInvokedProcedureReference("0M"),
        MappingError("0N"),
        InvalidRoleSpecification("0P"),
        InvalidTransformGroupNameSpecification("0S"),
        TargetTableDisagreesWithCursorSpecification("0T"),
        AttemptToAssignNonUpdatableColumn("0U"),
        AttemptToAssignToOrderingColumn("0V"),
        ProhibitedStatementEncouteredDuringTriggerExecution("0W"),
        DiagnosticsException("0Z"),
        XQuery("10"),
        CaseNotFoundInCaseStatement("20"),
        CardinalityViolation("21"),
        DataException("22"),
        IntegrityConstraintViolation("23"),
        InvalidCursorState("24"),
        InvalidTransactionState("25"),
        InvalidSQLStatementName("26"),
        TriggeredDataChangeViolation("27"),
        InvalidAuthorizationSpeciciation("28"),
        DependentPrivilegeDescriptorsAlreadyExsist("2B"),
        InvalidConnectionName("2E"),
        InvalidCharacterSetName("2C"),
        InvalidTransactionTermination("2D"),
        SqlRoutineException("2F"),
        InvalidSessionCollationSpecication("2H"),
        InvalidSqlStatementIdentifier("30"),
        InvalidSqlDescriptorName("33"),
        InvalidCursorName("34"),
        InvalidConditionNumber("35"),
        CursorSensivityException("36"),
        SyntaxError("37"),
        ExternalRoutineException("38"),
        ExternalRoutineInvocationException("39"),
        SavepointException("3B"),
        InvalidCatalogName("3D"),
        AmbiguousCursorName("3C"),
        InvalidSchemaName("3F"),
        TransactionRollback("40"),
        SyntaxErrorOrAccessRuleViolation("42"),
        WithCheckOptionViolation("44"),
        JavaErrors("46"),
        InvalidApplicationState("51"),
        InvalidOperandOrInconsistentSpecification("53"),
        SqlOrProductLimitExcedeed("54"),
        ObjectNotInPrerequisiteState("55"),
        MiscellaneoudSqlOrProductError("56"),
        ResourceNotAvailableOrOperatorIntervention("57"),
        SystemError("58"),
        CommonUtilitiesAndTools("5U"),
        RemoteDatabaseAccess("HZ");

        public String value;

        Classcode(String code) {
            value = code;
        }

    }

    public DatasourceExceptionSorter() {
        this(Classcode.ConnectionException.value, Classcode.RemoteDatabaseAccess.value, Classcode.SystemError.value);
    }

    public DatasourceExceptionSorter(String... codes) {
        for (String code : codes) {
            (code.length() == 2 ? fatalClasses : fatalCodes).add(code);
        }
    }

    final Set<String> fatalClasses = new HashSet<>();

    final Set<String> fatalCodes = new HashSet<>();

    @Override
    public boolean isExceptionFatal(Exception e) {
        if (e instanceof SQLException) {
            SQLException se = (SQLException) e;
            String statuscode = se.getSQLState();
            String classcode = statuscode.substring(0, 2);
            return checkClasscode(classcode) || checkStatuscode(statuscode);
        }
        return true;
    }

    @Override
    public boolean rollbackOnFatalException() {
        return true;
    }

    protected boolean checkClasscode(String classcode) {
        return fatalClasses.contains(classcode);
    }

    protected boolean checkStatuscode(String statuscode) {
        return fatalCodes.contains(statuscode);
    }

}
