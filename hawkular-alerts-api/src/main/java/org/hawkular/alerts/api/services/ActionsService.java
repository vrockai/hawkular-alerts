/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.alerts.api.services;

import org.hawkular.alerts.api.model.action.Action;

/**
 * A interface used to send actions.
 *
 * Actions will be created inside of the alerts engine and will be delivered using a chain of ActionListener listeners.
 *
 * @author Jay Shaughnessy
 * @author Lucas Ponce
 */
public interface ActionsService {

    /**
     * Send a action to an internal queue.
     * Primary used by the alerts-engine implementation to send a action.
     *
     * @param action Action to send
     */
    void send(Action action);

    /**
     * Register a listener that will process asynchronously.
     *
     * @param listener the listener
     */
    void addListener(ActionListener listener);
}
