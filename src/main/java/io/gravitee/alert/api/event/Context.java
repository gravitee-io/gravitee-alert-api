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
package io.gravitee.alert.api.event;

import java.util.HashMap;

/**
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
public class Context extends HashMap<String, String> {

    private static final long serialVersionUID = -6481239128541033372L;

    public static final String CONTEXT_NODE_ID = "node.id";
    public static final String CONTEXT_NODE_HOSTNAME = "node.hostname";
    public static final String CONTEXT_NODE_APPLICATION = "node.application";
    public static final String CONTEXT_TENANT = "tenant";
    public static final String CONTEXT_INSTALLATION = "installation";
//    public static final String CONTEXT_GATEWAY_PORT = "gateway.port";


}
