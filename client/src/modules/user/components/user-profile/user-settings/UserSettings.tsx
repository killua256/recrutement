import React, { BaseSyntheticEvent, useContext, useEffect, useState } from 'react'
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import { AuthContext } from '@contexts/index'
import userService from '@modules/user/services/user.service'
import userProfileSchema from '@modules/user/validation/user-profile-schema'
import { Button, ToggleBtn } from '@shared/components'
import { User } from '@shared/types'
import { toastError, toastLoading, toastSuccess } from '@utils/toast'
import ValidInput from '@shared/components/ValidInput';


const UserSettings = () => {
    const { user, login } = useContext(AuthContext)
    const formOptions = { resolver: yupResolver(userProfileSchema) };

    const {
        register,
        handleSubmit,
        reset,
        watch,
        formState: { errors },
    } = useForm<User>(formOptions);
    const currentValue = watch();

    const onUserUpdate = async (data: User, e: BaseSyntheticEvent | undefined) => {
        e?.preventDefault()
        toastLoading()
        const { response, success } =
            await userService.update(user?.id!, data);
        if (success && response) {
            login({...user, ...response})
            toastSuccess("Profile data updated successfully")
        } else {
            toastError("Updating profile data failed")
        }
    }

    useEffect(() => {
        const {
            username,
            email,
            firstname,
            lastname,
            mfaenabled
        } = user!
        reset({
            username,
            email,
            firstname,
            lastname,
            mfaenabled
        })
    }, [])

    const onMfaChange = (data: boolean) => {
        reset({ ...currentValue, mfaenabled: data })
    }


    return (
        <form className='my-4' onSubmit={handleSubmit(onUserUpdate, (errors) => console.log(errors))}>
            <div className="my-4 gap-6 grid sm:grid-cols-2">

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        Username
                    </label>
                    <ValidInput register={register('username')}
                        errors={errors.username} />
                </div>

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        Email
                    </label>
                    <ValidInput type='email' register={register('email')}
                        errors={errors.email} />
                </div>

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        First name
                    </label>
                    <ValidInput register={register('firstname')}
                        errors={errors.firstname} />
                </div>

                <div>
                    <label className="text-sm font-bold text-gray-600 block">
                        Last name
                    </label>
                    <ValidInput register={register('lastname')}
                        errors={errors.lastname} />
                </div>

                <ToggleBtn enabled={currentValue?.mfaenabled!}
                    setEnabled={onMfaChange}
                    label="Enable multi-factor authentication" />
            </div>

            <div className='flex items-center justify-end'>
                <Button submit >
                    Save
                </Button>
            </div>
        </form>
    )
}

export default UserSettings