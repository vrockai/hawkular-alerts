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
package org.hawkular.alerts.engine.impl;

import org.hawkular.alerts.api.model.action.Action;
import org.hawkular.alerts.api.services.ActionsService;
import org.hawkular.alerts.api.services.ActionListener;
import org.hawkular.alerts.engine.log.MsgLogger;
import org.jboss.logging.Logger;

import javax.ejb.Singleton;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A memory implementation of {@link org.hawkular.alerts.api.services.ActionsService}.
 * It is intended only for early prototype phases.
 * It will be replaced for a proper implementation based on a persistence repository.
 *
 * @author Jay Shaughnessy
 * @author Lucas Ponce
 */
@Singleton
public class MemActionsServiceImpl implements ActionsService {
    private final MsgLogger msgLog = MsgLogger.LOGGER;
    private final Logger log = Logger.getLogger(MemActionsServiceImpl.class);

    Queue<Action> pending = new ConcurrentLinkedDeque<Action>();
    List<ActionListener> listeners = new CopyOnWriteArrayList<ActionListener>();

    public MemActionsServiceImpl() {
        log.debugf("Creating instance.");
    }

    @Override
    public void send(Action action) {
        if (action == null || action.getActionId() == null || action.getActionId().isEmpty()) {
            throw new IllegalArgumentException("Action must be not null");
        }
        pending.add(action);

        /*
            In this implementation we invoke listeners as soon as we receive an event.
            This can be modified per implementation basis adding asynchronously behaviour at this level.
         */
        for (ActionListener listener : listeners) {
            listener.process(action);
        }
    }

    @Override
    public void addListener(ActionListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("ActionListener must not be null");
        }
        listeners.add(listener);
        msgLog.infoActionListenerRegistered(listener.toString());
    }
}
