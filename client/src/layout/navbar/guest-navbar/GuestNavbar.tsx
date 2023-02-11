import { AppLink } from '@shared/components'
import React from 'react'

const GuestNavbar = () => {
    return (
        <div className='flex items-center gap-4'>
            <AppLink to='/signup'>
                Signup
            </AppLink>
            <div className='-mx-3 w-[1px] h-10 bg-gray-600'></div>
            <AppLink to='/login'>
                SignIn
            </AppLink>
        </div>
    )
}

export default GuestNavbar