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
package io.gravitee.alert.api.trigger;

import io.gravitee.alert.api.trigger.command.Command;
import io.gravitee.alert.api.trigger.command.Handler;
import io.gravitee.common.component.LifecycleComponent;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface TriggerProvider extends LifecycleComponent<TriggerProvider> {

    void register(Trigger trigger);

    void unregister(Trigger trigger);

    default void addListener(Listener listener) {
    }

    interface Listener {

    }

    interface OnConnectionListener extends Listener {

        void doOnConnect();
    }

    interface OnDisconnectionListener extends Listener {

        void doOnDisconnect();
    }

    interface OnCommandListener extends Listener {

        void doOnCommand(Command command);
    }

    interface OnCommandResultListener extends Listener {

        <T> void doOnCommand(Command command, Handler<T> resultHandler);
    }
}
