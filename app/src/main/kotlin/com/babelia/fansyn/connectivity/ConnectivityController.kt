/*
 *   Copyright 2022 Babelia
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.babelia.fansyn.connectivity

import mini.Task

/**
 * Interface that connectivity controllers must comply to in order to work.
 */
interface ConnectivityController {

    /**
     * Start emitting connectivity changes.
     */
    fun startListeningConnectivityChanges(): Task

    /**
     * Stop emitting connectivity changes.
     */
    fun stopListeningConnectivityChanges()

    /**
     * Check if the device has Internet connection.
     */
    fun hasInternetConnection(): Boolean

}