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

package com.babelia.fansyn.utils.extensions

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/**
 * Convert a data class object to a JSON string.
 */
inline fun <reified T> Moshi.toJson(objectData: T): String {
    val jsonAdapter: JsonAdapter<T> = this.adapter(T::class.java)
    return jsonAdapter.toJson(objectData)
}

/**
 * Convert a JSON string to a data class object.
 */
inline fun <reified T> Moshi.fromJson(jsonValue: String): T? {
    val jsonAdapter: JsonAdapter<T> = this.adapter(T::class.java)
    return jsonAdapter.fromJson(jsonValue)
}