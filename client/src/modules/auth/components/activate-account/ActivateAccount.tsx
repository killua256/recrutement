import authService from '@modules/auth/services/auth.service';
import { PageLoading } from '@shared/components'
import { toastError, toastSuccess } from '@utils/toast';
import React, { useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom';

const ActivateAccount = () => {
    const { token } = useParams()
    const navigate = useNavigate()

    const activate = async () => {
        if (!token) {
            toastError("Invalid activation link")
            return;
        }
        const { response, success, error } = await authService.activateAccount(token);
        if (success && response) {
            toastSuccess("Account activated successfully")
            navigate('/login')
        } else {
            toastError(error?.message)
        }
    }

    useEffect(() => {
        activate()
    }, [])
    return (
        <PageLoading />
    )
}

export default ActivateAccount