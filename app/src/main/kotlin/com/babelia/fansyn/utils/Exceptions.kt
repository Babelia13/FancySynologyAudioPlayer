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

package com.babelia.fansyn.utils

/**
 * Generic exceptions to use when returning [Resource] errors so we can type the different errors.
 */

/** App's domain [Exception]. **/
open class AppException(cause: String? = null) : Exception(cause)

/** App's domain [IllegalStateException]. */
open class IllegalStateAppException(cause: String? = null) : IllegalStateException(cause)

/** App's domain [IllegalArgumentException]. */
open class IllegalArgumentAppException(cause: String? = null) : IllegalArgumentException(cause)

/**
 * Exception thrown when asking for the list of [AudioStationAlbum]s.
 */
class AlbumsListNotFoundException(errorCode: String? = null)
    : SynologyApiRequestException("Unable to get albums list", errorCode)

/**
 * Exception thrown when a specific [AudioStationAlbum] can not be found in the retrieved challenges.
 */
class AlbumNotFoundException(albumName: String,
                             errorCode: String? = null)
    : SynologyApiRequestException("Not found album with name [$albumName]", errorCode)

/**
 * Exception thrown when the [Response] from a request to the Synology API is not successful.
 */
open class SynologyApiRequestException(reason: String? = null, code: String? = null)
    : AppException("$reason. Error code: $code")

/* Uncomment if Firebase Auth is used
/** Exception thrown when the Firebase user is not valid. **/
object UserAccountNotFoundException : AppException()

/** Exception thrown when the user profile data is not valid. **/
object UserProfileDataNotFoundException : AppException()
 */