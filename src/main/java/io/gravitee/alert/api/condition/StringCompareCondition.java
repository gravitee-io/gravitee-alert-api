/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.alert.api.condition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class StringCompareCondition extends AbstractCondition implements Filter {

    public enum Operator {
        EQUALS, NOT_EQUALS, STARTS_WITH, ENDS_WITH, CONTAINS, MATCHES
    }

    private final String property;

    private final Operator operator;

    private final String property2;

    private final boolean ignoreCase;

    @JsonCreator
    private StringCompareCondition(
            @JsonProperty(value = "property", required = true) String property,
            @JsonProperty(value = "operator", required = true) Operator operator,
            @JsonProperty(value = "property2", required = true) String property2,
            @JsonProperty(value = "ignoreCase") boolean ignoreCase) {
        super(Type.STRING_COMPARE);

        this.property = property;
        this.operator = operator;
        this.property2 = property2;
        this.ignoreCase = ignoreCase;
    }

    public static FilterBuilder equals(String property, String property2) {
        return equals(property, property2, false);
    }

    public static FilterBuilder equals(String property, String property2, boolean ignoreCase) {
        return new FilterBuilder(property, Operator.EQUALS, property2, ignoreCase);
    }

    public static FilterBuilder notEquals(String property, String property2) {
        return notEquals(property, property2, false);
    }

    public static FilterBuilder notEquals(String property, String property2, boolean ignoreCase) {
        return new FilterBuilder(property, Operator.NOT_EQUALS, property2, ignoreCase);
    }

    public static FilterBuilder endsWith(String property, String property2) {
        return new FilterBuilder(property, Operator.ENDS_WITH, property2, false);
    }

    public static FilterBuilder contains(String property, String property2) {
        return new FilterBuilder(property, Operator.CONTAINS, property2, false);
    }

    public static FilterBuilder matches(String property, String pattern) {
        return matches(property, pattern, false);
    }

    public static FilterBuilder matches(String property, String pattern, boolean ignoreCase) {
        return new FilterBuilder(property, Operator.MATCHES, pattern, ignoreCase);
    }

    public static StringCondition.FilterBuilder startsWith(String property, String pattern) {
        return new StringCondition.FilterBuilder(property, StringCondition.Operator.STARTS_WITH, pattern, false);
    }

    public static class FilterBuilder {

        private final String property;
        private final Operator operator;
        private final String property2;
        private final boolean ignoreCase;

        FilterBuilder(String property, Operator operator, String property2, boolean ignoreCase) {
            this.property = property;
            this.operator = operator;
            this.property2 = property2;
            this.ignoreCase = ignoreCase;
        }

        public StringCompareCondition build() {
            return new StringCompareCondition(property, operator, property2, ignoreCase);
        }
    }

    public String getProperty() {
        return property;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getProperty2() {
        return property2;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }
}
