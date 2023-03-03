import React, { BaseSyntheticEvent, useContext, useEffect, useState } from 'react'
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import { AuthContext } from '@contexts/index'
import userService from '@modules/user/services/user.service'
import changePasswordSchema from '@modules/user/validation/change-password-schema';
import { Button, ToggleBtn } from '@shared/components'
import { User } from '@shared/types'
import { toastError, toastLoading, toastSuccess } from '@utils/toast'
import ValidInput from '@shared/components/ValidInput';
import { ChangePasswordReq } from '@modules/user/models/change-password';
import { useTranslation } from 'react-i18next';

const UserPassword = () => {
    const [loading, setLoading] = useState<boolean>(false)
    const formOptions = { resolver: yupResolver(changePasswordSchema) };
    const { t } = useTranslation()

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm<ChangePasswordReq>(formOptions);

    const onChangePassword = async (data: ChangePasswordReq, e: BaseSyntheticEvent | undefined) => {
        e?.preventDefault()
        setLoading(true)
        toastLoading()
        const { response, success } =
            await userService.changePassword(data);
        if (success && response) {
            toastSuccess("Password updated successfully")
        } else {
            toastError("Changing password failed")
        }
        setLoading(false)
    }

    return (
        <form className='my-4' onSubmit={handleSubmit(onChangePassword)}>
            <div className="my-4 gap-6 grid sm:grid-cols-2">

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        {t('currentPassword')}
                    </label>
                    <ValidInput type='password' register={register('currentPassword')}
                        errors={errors.currentPassword} />
                </div>

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        {t('newPassword')}
                    </label>
                    <ValidInput type='password' register={register('newPassword')}
                        errors={errors.newPassword} />
                </div>

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        {t('confirmPassword')}
                    </label>
                    <ValidInput type='password' register={register('confirmPassword')}
                        errors={errors.confirmPassword} />
                </div>

            </div>

            <div className='flex items-center justify-end'>
                <Button submit disabled={loading} >
                    Change
                </Button>
            </div>
        </form>
    )
}

export default UserPassword