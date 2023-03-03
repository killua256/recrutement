import React from 'react'
import UserInfo from './user-info/UserInfo';
import UserPassword from './user-password/UserPassword';


const UserSettings = () => {


    return (
        <>
            <div className='m-4 p-4 rounded border border-slate-300'>
                <div className='text-xl text-gray-700 font-semibold underline underline-offset-1 m-4'>
                    Profile info
                </div>
                <UserInfo />
            </div>
            <div className='m-4 p-4 rounded border border-slate-300'>
                <div className='text-xl text-gray-700 font-semibold underline underline-offset-1 m-4'>
                    Change password
                </div>
                <UserPassword />
            </div>
        </>
    )
}

export default UserSettings