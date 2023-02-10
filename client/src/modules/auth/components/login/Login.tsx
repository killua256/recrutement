import React, { useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '@contexts/auth/AuthContext';
import authService from '../../auth.service';
import { SettingsContext } from '@contexts/settings/SettingsContext';
import { User } from '@shared/types';
import { toastError, toastSuccess } from '@utils/toast';
import storageService from '@shared/services/storage.service';

const Login = () => {
    const { login } = useContext(AuthContext)
    const { settings } = useContext(SettingsContext)
    const navigate = useNavigate();
    const [loginInfo, setLoginInfo] = useState({
        username: 'admin',
        password: 'password'
    })
    const [loading, setLoading] = useState(false)

    const { username, password } = loginInfo;

    const onChange = (e: any) => {
        const { name, value } = e.target;
        setLoginInfo({ ...loginInfo, [name]: value })
    }

    const onLogin = (data: User | number) => {
        if(typeof data == 'number'){
            storageService.setLoginCode(data)
            toastSuccess("Your login code was sent to your email")
            navigate('/login-mfa')
        } else {
            console.log(data)
            login(data)
            navigate('/')
        }
    }

    const onSubmit = async (e: any) => {
        e.preventDefault();
        setLoading(true)
        const {response, success, error} = await authService.authenticate(loginInfo);
        if(success && response){
            onLogin(response)
        } else {
            toastError(error?.message)
        }
        setLoading(false)
    }

    return (
        <div className="bg-slate-100 h-full flex flex-col gap-2 justify-center items-center pb-20">
            <div className="max-w-md w-full mx-auto flex flex-col items-center gap-4 py-2">
                    {/* <img src={appImage(settings?.logo!)} className="w-32 text-center" /> */}
                    <div className="text-2xl font-bold text-gray-800 text-center">
                        SignIn
                    </div>
                </div>
            <div className="max-w-md w-full mx-auto bg-white px-8 py-8 rounded-lg shadow-lg">
                
                <form action="" className="space-y-6" onSubmit={onSubmit}>
                    <div>
                        <label className="text-sm font-bold text-gray-600 block">
                            Email
                        </label>
                        <input type="text" name="username" onChange={onChange} value={username}
                            className="w-full p-2 border border-gray-300 rounded mt-1" />
                    </div>
                    <div>
                        <label className="text-sm font-bold text-gray-600 block">
                            Password
                        </label>
                        <input type="password" name="password" onChange={onChange} value={password}
                            className="w-full p-2 border border-gray-300 rounded mt-1" />
                    </div>
                    <div className="flex flex-col justify-center">
                        <div>
                            <a href="#" className="font-medium text-sm text-primary-500">
                                Forgot password ?
                            </a>
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

export default Login
