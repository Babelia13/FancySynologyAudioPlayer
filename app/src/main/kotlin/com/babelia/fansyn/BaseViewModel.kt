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

package com.babelia.fansyn

import android.app.Application
import mini.android.FluxViewModel
import mini.kodein.android.FluxTypedViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

/**
 * Base ViewModel which implements [DIAware].
 */
abstract class BaseViewModel(app: Application) : FluxViewModel(), DIAware {
    override val di by closestDI(app)
}

/**
 * Base [TypedViewModel] which implements [DIAware].
 */
abstract class BaseTypedViewModel<T>(app: Application, params: T) : FluxTypedViewModel<T>(params), DIAware {
    override val di by closestDI(app)
}