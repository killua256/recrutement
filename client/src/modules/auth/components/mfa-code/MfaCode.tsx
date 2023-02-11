import { AuthContext } from '@contexts/index';
import authService from '@modules/auth/auth.service';
import storageService from '@shared/services/storage.service';
import { User } from '@shared/types';
import { dismissToast, toastError, toastLoading, toastSuccess } from '@utils/toast';
import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom';

const MfaCode = () => {
    const { login } = useContext(AuthContext)
    const navigate = useNavigate();
    const [mfaCode, setMfaCode] = useState('')
    const [loading, setLoading] = useState(false)

    const onChange = (e: any) => {
        setMfaCode(e.target.value)
    }

    const onLogin = (data: User) => {
        storageService.clearLoginCode()
        login(data)
        navigate('/')
    }


    const onSubmit = async (e: any) => {
        e.preventDefault();
        setLoading(true)
        toastLoading()
        const {response, success, error} = await authService.mfaAuth(mfaCode);
        if(success && response){
            dismissToast()
            onLogin(response)
        } else {
            toastError(error?.message)
        }
        setLoading(false)
    }

    const resendCode = async (e: any) => {
        e.preventDefault();
        setLoading(true)
        toastLoading()
        const code = storageService.getLoginCode()
        if(!code){
            setLoading(false)
            toastError("Unable to resend the code")
            return;
        }
        const {response, success, error} = await authService.resendMfaCode(
            storageService.getLoginCode()!
        );
        if(success && response){
            storageService.setLoginCode(response)
            toastSuccess("Code was sent successfully")
        } else {
            toastError(error?.message)
        }
        setLoading(false)
    }


    return (
        <div className="bg-slate-100 h-full flex flex-col gap-2 justify-center items-center pb-20">
            <div className="max-w-md w-full mx-auto flex flex-col items-center gap-4 py-2">
                <div className="text-2xl font-bold text-gray-800 text-center">
                    Enter your code
                </div>
            </div>
            <div className="max-w-md w-full mx-auto bg-white px-8 py-8 rounded-lg shadow-lg">

                <form action="" className="space-y-6" onSubmit={onSubmit}>
                    <div>
                        <label className="text-sm font-bold text-gray-600 block">
                            Code
                        </label>
                        <input type="text" name="mfaCode" onChange={onChange} value={mfaCode}
                            className="w-full p-2 border border-gray-300 rounded mt-1" />
                    </div>
                    <div className="flex flex-col justify-center">
                        <div>
                            <button type='button' onClick={resendCode} className="font-medium text-sm text-primary-500">
                                You did not receive your code ? Resend.
                            </button>
                        </div>
                    </div>
                    <div>
                        <button type="submit" disabled={loading} className={`w-full py-2 px-4 bg-primary-500 hover:bg-primary-600 
                        rounded-md text-white text-sm`}>
                            {loading ? 'Loading...' : 'SignIn'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default MfaCode