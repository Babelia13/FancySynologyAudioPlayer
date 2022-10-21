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

package com.babelia.fansyn.validations.traits

import com.babelia.fansyn.validations.ValidationStatus
import com.babelia.fansyn.validations.ValidationViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Trait for ViewModels that need confirm password validations.
 */
interface ConfirmPasswordValidationTrait {
    val confirmPasswordValidation: MutableStateFlow<ValidationViewData?>

    /**
     * Validates the confirmed password is the same as the password.
     * CONTRACT: emits result via [confirmPasswordValidation]
     */
    fun validate(password: String, confirmedPassword: String)

    @Suppress("UndocumentedPublicFunction")
    fun getConfirmPasswordValidation(): StateFlow<ValidationViewData?> = confirmPasswordValidation

}

/**
 * Implementation of [ConfirmPasswordValidationTrait].
 */
class ConfirmPasswordValidationTraitImpl : ConfirmPasswordValidationTrait {
    override val confirmPasswordValidation = MutableStateFlow<ValidationViewData?>(null)

    override fun validate(password: String, confirmedPassword: String) {
        confirmPasswordValidation.value = validateFrom(password, confirmedPassword)
    }

    private fun validateFrom(password: String, confirmedPassword: String): ValidationViewData =
        when {
            password.isBlank() -> ValidationViewData(ValidationStatus.Required)
            password != confirmedPassword -> ValidationViewData(ValidationStatus.PasswordsDoNotMatch)
            else -> ValidationViewData(ValidationStatus.Valid)
        }
}