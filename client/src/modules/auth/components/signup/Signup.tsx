import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import authService from '../../services/auth.service';
import logo from '@assets/logo.png'
import { AppLink } from '@shared/components';
import { toastError, toastLoading, toastSuccess } from '@utils/toast';

const Signup = () => {
    const navigate = useNavigate();
    const [signupInfo, setSignupInfo] = useState({
        username: '',
        email: '',
        firstname: '',
        lastname: '',
        password: '',
        confirmPassword: ''
    })
    const [loading, setLoading] = useState(false)

    const {
        username,
        email,
        firstname,
        lastname,
        password,
        confirmPassword
    } = signupInfo;

    const onChange = (e: any) => {
        const { name, value } = e.target;
        setSignupInfo({ ...signupInfo, [name]: value })
    }

    const onSubmit = async (e: any) => {
        e.preventDefault();
        setLoading(true)
        toastLoading()
        const {response, success, error} = await authService.signup(signupInfo);
        if(success && response){
            toastSuccess('You signed up successfully, please check your email and activate your account')
            navigate('/')
        } else {
            toastError(error?.message)
        }
        setLoading(false)
    }
    return (
        <div className="bg-slate-100 h-full flex flex-col justify-center">
            <div className="max-w-md w-full mx-auto flex flex-col items-center">
                {/* <img src={logo} className="w-32 text-center" /> */}
                <div className="text-2xl font-bold text-gray-800 text-center">
                    Signup
                </div>
            </div>
            <div className="max-w-[90vw] w-max mx-auto my-4 bg-white p-8 rounded-lg shadow-lg">
                <form className="space-y-6" onSubmit={onSubmit}>
                    <div className='w-full flex flex-wrap items-center justify-around gap-4'>
                        <div className='md:w-[350px] w-full '>
                            <label className="text-sm font-bold text-gray-600 block">
                                Username
                            </label>
                            <input type="text" name="username" onChange={onChange} value={username}
                                className="w-full p-2 border border-gray-300 rounded mt-1" />
                        </div>
                        <div className='md:w-[350px] w-full '>
                            <label className="text-sm font-bold text-gray-600 block">
                                Email
                            </label>
                            <input type="email" name="email" onChange={onChange} value={email}
                                className="w-full p-2 border border-gray-300 rounded mt-1" />
                        </div>
                    </div>

                    <div className='w-full flex flex-wrap items-center justify-around gap-4'>
                        <div className='md:w-[350px] w-full '>
                            <label className="text-sm font-bold text-gray-600 block">
                                Firstname
                            </label>
                            <input type="text" name="firstname" onChange={onChange} value={firstname}
                                className="w-full p-2 border border-gray-300 rounded mt-1" />
                        </div>
                        <div className='md:w-[350px] w-full '>
                            <label className="text-sm font-bold text-gray-600 block">
                                Lastname
                            </label>
                            <input type="text" name="lastname" onChange={onChange} value={lastname}
                                className="w-full p-2 border border-gray-300 rounded mt-1" />
                        </div>
                    </div>

                    <div className='w-full flex flex-wrap items-center justify-around gap-4'>

                        <div className='md:w-[350px] w-full '>
                            <label className="text-sm font-bold text-gray-600 block">
                                Password
                            </label>
                            <input type="password" name="password" onChange={onChange} value={password}
                                className="w-full p-2 border border-gray-300 rounded mt-1" />
                        </div>
                        <div className='md:w-[350px] w-full '>
                            <label className="text-sm font-bold text-gray-600 block">
                                Confirm Password
                            </label>
                            <input type="password" name="confirmPassword" onChange={onChange} value={confirmPassword}
                                className="w-full p-2 border border-gray-300 rounded mt-1" />
                        </div>
                    </div>

                    <div className="w-full flex items-center justify-between flex-col-reverse md:flex-row md:items-start gap-4">
                        <div>
                            <AppLink to="/login">
                                You already have an account? Login.
                            </AppLink>
                        </div>
                        <div className="w-full md:w-auto">
                            <button type="submit"
                                className="w-full md:w-auto py-2 px-4 bg-primary-500 hover:bg-primary-600 
                        rounded-md text-white text-sm shadow-md">
                                Signup
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Signup
